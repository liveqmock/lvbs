package com.daishumovie.admin.service.impl;

import com.daishumovie.admin.dto.UserDto;
import com.daishumovie.admin.dto.paginate.ParamDto;
import com.daishumovie.admin.dto.paginate.ReturnDto;
import com.daishumovie.admin.exception.ResultException;
import com.daishumovie.admin.service.IUserService;
import com.daishumovie.admin.service.auth.IAdminService;
import com.daishumovie.base.enums.db.CommonStatus;
import com.daishumovie.base.enums.db.Whether;
import com.daishumovie.dao.mapper.smallbronze.DsmUserMapper;
import com.daishumovie.dao.model.DsmUser;
import com.daishumovie.dao.model.DsmUserExample;
import com.daishumovie.dao.model.auth.enums.ErrMsg;
import com.daishumovie.utils.CollectionUtils;
import com.daishumovie.utils.DateUtil;
import com.daishumovie.utils.Page;
import com.daishumovie.utils.StringUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by feiFan.gou on 2017/9/6 15:24.
 */
@Service
public class UserService implements IUserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private DsmUserMapper userMapper;

    @Autowired
    private IAdminService adminService;

    @Override
    public ReturnDto<UserDto> paginate(ParamDto param, DsmUser paramUser, String registerTime) {

        try {
            DsmUserExample example = condition(paramUser, registerTime);
            Long total = userMapper.countByExample(example);
            List<UserDto> dtoList = Lists.newArrayList();
            if (total > 0) {
                example.setLimit(param.limit());
                example.setOffset(param.offset());
                example.setOrderByClause("update_time desc");
                List<DsmUser> userList = userMapper.selectByExample(example);
                if (!CollectionUtils.isNullOrEmpty(userList)) {
                    Set<Long> adminIdSet = Sets.newHashSet();
                    userList.forEach(user -> {
                        UserDto dto = new UserDto();
                        BeanUtils.copyProperties(user, dto);
                        if (null != user.getOperatorId()) {
                            adminIdSet.add(Long.valueOf(user.getOperatorId()));
                        }
                        dtoList.add(dto);
                    });
                    Map<Integer, String> adminNameMap = adminService.userNameMap(adminIdSet);
                    dtoList.forEach(dto -> {
                        if (null != dto.getOperatorId()) {
                            dto.setOperatorName(adminNameMap.get(dto.getOperatorId()));
                        }
                    });
                }
            }
            Page<UserDto> page = param.page();
            page.setTotal(total.intValue());
            page.setItems(dtoList);
            return new ReturnDto<>(page);
        } catch (Exception e) {
            LOGGER.info("paginate app user error --- >" + e.getMessage(), e);
            return new ReturnDto<>(null);
        }
    }


    @Override
    public void replyComment(Integer uid, Integer operatorId) {

        try {
            if (null == uid) {
                throw new ResultException(ErrMsg.param_error);
            }
            DsmUser user = userMapper.selectByPrimaryKey(uid);
            if (null == user) {
                throw new ResultException("用户不存在");
            }
            user.setIsReplyAuth(Whether.invert(user.getIsReplyAuth()));
            user.setUpdateTime(new Date());
            user.setOperatorId(operatorId);
            userMapper.updateByPrimaryKeySelective(user);
        } catch (ResultException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.info("reply comment error ---> " + e.getMessage(), e);
            throw new ResultException();
        }
    }

    @Override
    public void publishTopic(Integer uid, Integer operatorId) {

        try {
            if (null == uid) {
                throw new ResultException(ErrMsg.param_error);
            }
            DsmUser user = userMapper.selectByPrimaryKey(uid);
            if (null == user) {
                throw new ResultException("用户不存在");
            }
            user.setIsTopicAuth(Whether.invert(user.getIsTopicAuth()));
            user.setUpdateTime(new Date());
            user.setOperatorId(operatorId);
            userMapper.updateByPrimaryKeySelective(user);
        } catch (ResultException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.info("publish topic error ---> " + e.getMessage(), e);
            throw new ResultException();
        }
    }

    @Override
    public Map<String, Object> getFictitiousUsers(String name, int pageNumber, int pageSize) {

        Map<String, Object> result = Maps.newHashMap();
        DsmUserExample example = new DsmUserExample();
        DsmUserExample.Criteria criteria = example.createCriteria();
        criteria.andNickNameLike(StringUtil.sqlLike(name));
        criteria.andFictitiousEqualTo(CommonStatus.normal.getValue());
        Long total = userMapper.countByExample(example);
        List<DsmUser> userList = Lists.newArrayList();
        if (total > 0) {
            example.setOffset((pageNumber - 1) * pageSize);
            example.setLimit(pageSize);
            userList = userMapper.selectByExample(example);
        }
        result.put("list", userList);
        result.put("totalRow", total.intValue());
        return result;
    }

    @Override
    public Map<Integer, DsmUser> userMapByUid(Set<Integer> userIdSet) {

        Map<Integer, DsmUser> map = Maps.newHashMap();
        if (!CollectionUtils.isNullOrEmpty(userIdSet)) {
            DsmUserExample example = new DsmUserExample();
            DsmUserExample.Criteria criteria = example.createCriteria();
            criteria.andUidIn(new ArrayList<>(userIdSet));
            List<DsmUser> list = userMapper.selectByExample(example);
            for (DsmUser user : list){
                if (!map.containsKey(user.getUid()))
                    map.put(user.getUid(),user);
            }
        }
        return map;
    }

    @Override
    public UserDto getUser(Integer userId) {
        DsmUser user = userMapper.selectByPrimaryKey(userId);
        UserDto dto = new UserDto();
        if (user != null)
            BeanUtils.copyProperties(user,dto);
        return dto;
    }

    @Override
    public void update(DsmUser record) {
        if (record != null && record.getUid() != null)
            userMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public List<Integer> getUidByNameLike(String name) {

        List<Integer> uidList = Lists.newArrayList();
        if (StringUtil.isNotEmpty(name)) {
            DsmUserExample example = new DsmUserExample();
            DsmUserExample.Criteria criteria = example.createCriteria();
            criteria.andNickNameLike(StringUtil.sqlLike(name));
            List<DsmUser> userList = userMapper.selectByExample(example);
            userList.forEach(user -> uidList.add(user.getUid()));
        }
        return uidList;
    }

    @Override
    public Map<String, Object> fictitiousUserList(String name,int pageNumber,int pageSize) {

        Map<String, Object> result = Maps.newHashMap();
        try {
            List<DsmUser> userList = Lists.newArrayList();
            DsmUserExample example = new DsmUserExample();
            DsmUserExample.Criteria criteria = example.createCriteria();
            criteria.andNickNameLike(StringUtil.sqlLike(name));
            criteria.andFictitiousEqualTo(Whether.yes.getValue());
            Long total = userMapper.countByExample(example);
            if (total > 0) {
                example.setOffset((pageNumber - 1) * pageSize);
                example.setLimit(pageSize);
                example.setOrderByClause("create_time desc");
                userList = userMapper.selectByExample(example);
                if (!CollectionUtils.isNullOrEmpty(userList)) {
                    userList.forEach(user -> user.setIntroduce(user.getNickName() + "（发布量："+user.getPublishCount()+"）"));
                }
            }
            result.put("list", userList);
            result.put("totalRow", total);
        } catch (Exception e) {
            LOGGER.info("fictitiousUserList error " + e.getMessage(), e);
        }
        return result;
    }

    private DsmUserExample condition(DsmUser user, String registerTime) {

        DsmUserExample example = new DsmUserExample();
        DsmUserExample.Criteria criteria = example.createCriteria();
        if (null != user) {
            if (StringUtil.isNotEmpty(user.getNickName())) {
                criteria.andNickNameLike(StringUtil.sqlLike(user.getNickName()));
            }
            if (StringUtil.isNotEmpty(user.getMobile())) {
                criteria.andMobileLike(StringUtil.sqlLike(user.getMobile()));
            }
            if (null != user.getIsTopicAuth()) {
                criteria.andIsTopicAuthEqualTo(user.getIsTopicAuth());
            }
            if (null != user.getIsReplyAuth()) {
                criteria.andIsReplyAuthEqualTo(user.getIsReplyAuth());
            }
            if (null != user.getFictitious()) {
                criteria.andFictitiousEqualTo(user.getFictitious());
            }
        }
        if (StringUtil.isNotEmpty(registerTime)) {
            criteria.andCreateTimeGreaterThanOrEqualTo(DateUtil.todayZeroClock(registerTime));
        }
        return example;
    }
}
