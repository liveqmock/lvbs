package com.daishumovie.api.service;

import com.daishumovie.base.dto.BaseListDto;
import com.daishumovie.base.dto.PageInDto;
import com.daishumovie.base.dto.category.ChannelDto;
import com.daishumovie.base.dto.user.UserFollowDto;
import com.daishumovie.base.enums.db.RespStatusEnum;
import com.daishumovie.base.enums.db.YesNoEnum;
import com.daishumovie.base.enums.db.UserPraiseTargetType;
import com.daishumovie.base.enums.db.UserPraiseType;
import com.daishumovie.base.model.Response;
import com.daishumovie.dao.mapper.smallbronze.DsmUserMapper;
import com.daishumovie.dao.mapper.smallbronze.SbChannelHotMapper;
import com.daishumovie.dao.mapper.smallbronze.SbChannelMapper;
import com.daishumovie.dao.mapper.smallbronze.SbUserPraiseMapper;
import com.daishumovie.dao.model.*;
import com.daishumovie.utils.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Cheng Yufei
 * @create 2017-09-09 14:43
 **/
@Service
public class ChannelService {

    @Autowired
    private SbChannelMapper sbChannelMapper;

    @Autowired
    private SbChannelHotMapper sbChannelHotMapper;

    @Autowired
    private SbUserPraiseMapper sbUserPraiseMapper;

    @Autowired
    private DsmUserMapper dsmUserMapper;

    public Response<List<ChannelDto>> getClassificationList(Integer appId) {
        try {
            List<ChannelDto> channelDtos = new ArrayList<>();
            SbChannelExample example = new SbChannelExample();
            SbChannelExample.Criteria criteria = example.createCriteria();
            criteria.andPidEqualTo(0);
            if (null != appId) {
                criteria.andAppIdEqualTo(appId);
            }
            criteria.andStatusEqualTo(YesNoEnum.YES.getCode());
            List<SbChannel> sbChannels = sbChannelMapper.selectByExample(example);
            if (CollectionUtils.isNullOrEmpty(sbChannels)) {
                return new Response<>(channelDtos);
            }
            for (SbChannel channel : sbChannels) {
                ChannelDto channelDto = new ChannelDto();
                channelDto.setId(channel.getId());
                channelDto.setName(channel.getName());
                channelDto.setUrl(channel.getUrl());
                channelDtos.add(channelDto);
            }
            return new Response<>(channelDtos);
        } catch (Exception e) {
            return new Response<>(RespStatusEnum.ERROR);
        }
    }


    public Response<BaseListDto<ChannelDto>> getChannelList(Integer id, Integer uid, PageInDto pageInfo, Integer appId) {
        try {
            List<ChannelDto> channelDtos = new ArrayList<>();

            BaseListDto<ChannelDto> baseListDto = new BaseListDto<ChannelDto>();
            baseListDto.setList(channelDtos);
            baseListDto.setPageIndex(pageInfo.getPageIndex());
            baseListDto.setHasNext(0);

            SbChannelExample example = new SbChannelExample();
            example.setOffset(pageInfo.getOffset());
            example.setLimit(pageInfo.getLimit());
            example.setOrderByClause("follow_num desc");
            SbChannelExample.Criteria criteria = example.createCriteria();
            if (0 != id) {
                //非 热门
                criteria.andPidEqualTo(id);
            } else {
                //热门
                List<Integer> idList = new ArrayList<>();
                SbChannelHotExample hotExample = new SbChannelHotExample();
                SbChannelHotExample.Criteria hotCriterria = hotExample.createCriteria();
                hotCriterria.andStatusEqualTo(YesNoEnum.YES.getCode());
                List<SbChannelHot> channelHots = sbChannelHotMapper.selectByExample(hotExample);
                if (CollectionUtils.isNullOrEmpty(channelHots)) {
                    return new Response<>(baseListDto);
                }
                for (SbChannelHot hot : channelHots) {
                    idList.add(hot.getChannelId());
                }
                criteria.andIdIn(idList);
            }
            if (null != appId) {
                criteria.andAppIdEqualTo(appId);
            }
            criteria.andStatusEqualTo(YesNoEnum.YES.getCode());
            List<SbChannel> sbChannels = sbChannelMapper.selectByExample(example);
            if (CollectionUtils.isNullOrEmpty(sbChannels)) {
                return new Response<>(baseListDto);
            }
            List<Long> followStatus = new ArrayList<>();
            if (null != uid) {
                followStatus = getFollowStatus(uid);
            }
            for (SbChannel channel : sbChannels) {
                ChannelDto channelDto = new ChannelDto();
                channelDto.setId(channel.getId());
                channelDto.setName(channel.getName());
                channelDto.setUrl(channel.getUrl());
                channelDto.setFollowNum(String.valueOf(channel.getFollowNum()));
                if (null != uid && !CollectionUtils.isNullOrEmpty(followStatus)) {
                    channelDto.setIsFollow(followStatus.contains(channel.getId().longValue()) == true ? 1 : 0);
                } else {
                    channelDto.setIsFollow(0);
                }
                channelDtos.add(channelDto);
            }

            baseListDto.setHasNext(channelDtos.size() < pageInfo.getPageSize() ? 0 : 1);
            return new Response<>(baseListDto);
        } catch (Exception e) {
            return new Response<>(RespStatusEnum.ERROR);
        }
    }

    public Response<ChannelDto> getChannelDetailTop(Integer id, Integer uid, Integer appId) {
        try {
            SbChannelExample example = new SbChannelExample();
            example.setLimit(1);
            SbChannelExample.Criteria criteria = example.createCriteria();
            criteria.andIdEqualTo(id);
            if (null != appId) {
                criteria.andAppIdEqualTo(appId);
            }
            criteria.andStatusEqualTo(YesNoEnum.YES.getCode());
            List<SbChannel> channels = sbChannelMapper.selectByExample(example);
            ChannelDto channelDto = new ChannelDto();
            if (CollectionUtils.isNullOrEmpty(channels)) {
                return new Response<>(channelDto);
            }
            List<Long> follows = new ArrayList<>();
            if (null != uid) {
                follows = getFollowStatus(uid);
            }

            channelDto.setId(id);
            channelDto.setName(channels.get(0).getName());
            channelDto.setUrl(channels.get(0).getUrl());
            channelDto.setRemarks(channels.get(0).getRemarks());
            channelDto.setFollowNum(String.valueOf(channels.get(0).getFollowNum()));
            if (null != uid && !CollectionUtils.isNullOrEmpty(follows)) {
                channelDto.setIsFollow(follows.contains(id.longValue()) == true ? 1 : 0);
                clearUnReadNum(id,uid);
            }else{
                channelDto.setIsFollow(0);
            }
            //获取前 5个关注用户
            List<UserFollowDto> userFollowDtos = new ArrayList<>();
            List<Integer> followUsers = getFollowUsers(id);
            if (!CollectionUtils.isNullOrEmpty(followUsers)) {
                DsmUserExample userExample = new DsmUserExample();
                DsmUserExample.Criteria userCriteria = userExample.createCriteria();
                userCriteria.andUidIn(followUsers);
                userCriteria.andStatusEqualTo(YesNoEnum.YES.getCode());
                List<DsmUser> dsmUsers = dsmUserMapper.selectByExample(userExample);
                Map<Integer, DsmUser> userMap = com.daishumovie.utils.CollectionUtils.convert2Map(dsmUsers, "getUid");
                for (Integer followUser : followUsers) {
                    if(null == userMap.get(followUser)){
                        continue;
                    }
                    UserFollowDto userFollowDto = new UserFollowDto();
                    userFollowDto.setId(userMap.get(followUser).getUid());
                    userFollowDto.setAvatar(userMap.get(followUser).getAvatar());
                    userFollowDtos.add(userFollowDto);
                }
            }
            channelDto.setUserFollowDtoList(userFollowDtos);
            return new Response<>(channelDto);
        } catch (Exception e) {
            return new Response<>(RespStatusEnum.ERROR);
        }
    }


    public List<Long> getFollowStatus(Integer uid) {
        SbUserPraiseExample example = new SbUserPraiseExample();
        SbUserPraiseExample.Criteria criteria = example.createCriteria();
        criteria.andTargetTypeEqualTo(UserPraiseTargetType.CHANNEL.getValue());
        criteria.andTypeEqualTo(UserPraiseType.FOLLOW.getValue());
        criteria.andUidEqualTo(uid);
        criteria.andStatusEqualTo(YesNoEnum.YES.getCode());
        List<SbUserPraise> praises = sbUserPraiseMapper.selectByExample(example);
        List<Long> targetIds = new ArrayList<>();
        if (!CollectionUtils.isNullOrEmpty(praises)) {
            for (SbUserPraise praise : praises) {
                targetIds.add(praise.getTargetId());
            }
        }
        return targetIds;
    }

    public List<Integer> getFollowUsers(Integer id){
        SbUserPraiseExample example = new SbUserPraiseExample();
        example.setLimit(5);
        example.setOrderByClause("create_time desc");
        SbUserPraiseExample.Criteria criteria = example.createCriteria();
        criteria.andTargetTypeEqualTo(UserPraiseTargetType.CHANNEL.getValue());
        criteria.andTypeEqualTo(UserPraiseType.FOLLOW.getValue());
        criteria.andTargetIdEqualTo(id.longValue());
        criteria.andStatusEqualTo(YesNoEnum.YES.getCode());
        List<SbUserPraise> praises = sbUserPraiseMapper.selectByExample(example);
        List<Integer> uidList = new ArrayList<>();
        if (!CollectionUtils.isNullOrEmpty(praises)) {
            for (SbUserPraise praise : praises) {
                uidList.add(praise.getUid());
            }
        }
        return uidList;
    }

    public void clearUnReadNum(Integer id,Integer uid) {
        SbUserPraiseExample example = new SbUserPraiseExample();
        example.setLimit(1);
        SbUserPraiseExample.Criteria criteria = example.createCriteria();
        criteria.andTargetTypeEqualTo(UserPraiseTargetType.CHANNEL.getValue());
        criteria.andTypeEqualTo(UserPraiseType.FOLLOW.getValue());
        criteria.andStatusEqualTo(YesNoEnum.YES.getCode());
        criteria.andTargetIdEqualTo(id.longValue());
        criteria.andUidEqualTo(uid);
        SbUserPraise userPraise = new SbUserPraise();
        userPraise.setUnReadNum(0);
        sbUserPraiseMapper.updateByExampleSelective(userPraise, example);
    }
    
	public Response<BaseListDto<ChannelDto>> getChannelList(Integer appId, Integer source) {
		try {
			List<ChannelDto> channelDtos = new ArrayList<>();

			BaseListDto<ChannelDto> baseListDto = new BaseListDto<ChannelDto>();
			baseListDto.setList(channelDtos);
			baseListDto.setHasNext(0);

			SbChannelExample example = new SbChannelExample();
			// 在小铜人项目中，follow_num干预排序用；
			example.setOrderByClause("follow_num desc");
			SbChannelExample.Criteria criteria = example.createCriteria();
			// 取所有一级分类；
			criteria.andPidEqualTo(0);
			criteria.andAppIdEqualTo(appId);
			criteria.andStatusEqualTo(YesNoEnum.YES.getCode());
			List<SbChannel> sbChannels = sbChannelMapper.selectByExample(example);
			if (CollectionUtils.isNullOrEmpty(sbChannels)) {
				return new Response<>(baseListDto);
			}
			if (source.intValue() == 1) {
				ChannelDto recChannelDto = new ChannelDto();
				recChannelDto.setId(0);
				recChannelDto.setName("推荐");
				channelDtos.add(recChannelDto);
			}
			for (SbChannel channel : sbChannels) {
				ChannelDto channelDto = new ChannelDto();
				channelDto.setId(channel.getId());
				channelDto.setName(channel.getName());
				channelDtos.add(channelDto);
			}

			return new Response<>(baseListDto);
		} catch (Exception e) {
			return new Response<>(RespStatusEnum.ERROR);
		}
	}

}
