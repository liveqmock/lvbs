package com.daishumovie.admin.service.impl;

import com.daishumovie.admin.constant.Configuration;
import com.daishumovie.admin.dto.AdDto;
import com.daishumovie.admin.dto.paginate.ParamDto;
import com.daishumovie.admin.dto.paginate.ReturnDto;
import com.daishumovie.admin.exception.ResultException;
import com.daishumovie.admin.service.IAdService;
import com.daishumovie.admin.service.auth.IAdminService;
import com.daishumovie.base.enums.db.AdTargetType;
import com.daishumovie.base.enums.db.AuditStatus;
import com.daishumovie.base.enums.db.Whether;
import com.daishumovie.dao.mapper.smallbronze.SbActivityMapper;
import com.daishumovie.dao.mapper.smallbronze.SbAdMapper;
import com.daishumovie.dao.mapper.smallbronze.SbTopicAlbumMapper;
import com.daishumovie.dao.mapper.smallbronze.SbTopicMapper;
import com.daishumovie.dao.model.*;
import com.daishumovie.dao.model.auth.enums.ErrMsg;
import com.daishumovie.utils.DateUtil;
import com.daishumovie.utils.OSSClientUtil;
import com.daishumovie.utils.Page;
import com.daishumovie.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author Cheng Yufei
 * @create 2017-10-25 14:26
 **/
@Service
@Slf4j
public class AdService extends BaseService implements IAdService {

    @Autowired
    private SbAdMapper adMapper;

    @Autowired
    private IAdminService adminService;

    @Autowired
    private SbTopicMapper topicMapper;

    @Autowired
    private SbActivityMapper activityMapper;

    @Autowired
    private SbTopicAlbumMapper albumMapper;

    @Override
    public ReturnDto<AdDto> paginate(ParamDto param, String name, String createTime, Integer status, Integer orders) {

        try {
            List<AdDto> dtos = new ArrayList<>();
            SbAdExample example = new SbAdExample();
            SbAdExample.Criteria criteria = example.createCriteria();
            if (StringUtils.isNotBlank(name)) {
                criteria.andNameLike(StringUtil.sqlLike(name));
            }
            if (null != status) {
                criteria.andStatusEqualTo(status);
            }
            if (null != orders) {
                criteria.andOrdersEqualTo(orders);
            }
            if (StringUtil.isNotEmpty(createTime)) {
                criteria.andCreateTimeGreaterThanOrEqualTo(DateUtil.todayZeroClock(createTime));
            }
            Long count = adMapper.countByExample(example);
            if (count > 0) {
                example.setOffset(param.offset());
                example.setLimit(param.limit());
                example.setOrderByClause("create_time desc");

                List<SbAd> adList = adMapper.selectByExample(example);

                Set<Long> ids = new HashSet<>();
                adList.forEach(ad -> {
                    AdDto dto = new AdDto();
                    BeanUtils.copyProperties(ad, dto);
                    dtos.add(dto);
                    if (null != dto.getCreatorId()) {
                        ids.add(dto.getCreatorId().longValue());
                    }

                });
                Map<Integer, String> adminNameMap = adminService.userNameMap(ids);
                dtos.forEach(dto -> {
                    Integer operator = dto.getCreatorId();
                    if (null != operator && adminNameMap.containsKey(operator)) {
                        dto.setCreatorName(adminNameMap.get(operator));
                    }
                });
            }

            Page<AdDto> page = param.page();
            page.setTotal(count.intValue());
            page.setItems(dtos);
            return new ReturnDto<>(page);
        } catch (Exception e) {
            log.error("查询广告异常信息", e);
            return new ReturnDto<>(null);
        }

    }

    @Override
    public void save(SbAd ad, Integer operatorId) {
        try {
            validate(ad, false);
            handelCover(ad);
            ad.setCreatorId(operatorId);
            ad.setStatus(Whether.yes.getValue());
            ad.setDuration(3);
            adMapper.insertSelective(ad);
        } catch (ResultException e) {
            throw e;
        } catch (Exception e) {
            log.error("保存广告异常信息", e);
            throw new ResultException();
        }
    }

    @Override
    public void update(SbAd ad, Integer operatorId) {
        validate(ad, true);
        try {
            handelCover(ad);
            adMapper.updateByPrimaryKeySelective(ad);
        } catch (Exception e) {
            log.error("修改广告异常信息", e);
            throw new ResultException();
        }
    }

    @Override
    public void delete(Integer id, Integer operatorId) {
        if (null == id) {
            throw new ResultException(ErrMsg.param_error);
        }
        try {
            SbAd ad = adMapper.selectByPrimaryKey(id);
            if (null == ad) {
                throw new ResultException("广告不存在");
            }
            ad.setStatus(Whether.no.getValue());
            adMapper.updateByPrimaryKeySelective(ad);
        } catch (ResultException e) {
            throw e;
        } catch (Exception e) {
            printException("ad", "delete", e);
            throw new ResultException();
        }
    }

    @Override
    protected void specificVerify(Object model, boolean isUpdate) {

        SbAd ad = (SbAd) model;

        if (isUpdate) {
            if (null == ad.getId()) {
                throw new ResultException(ErrMsg.param_error);
            }
            SbAd dbChannel = adMapper.selectByPrimaryKey(ad.getId());
            if (null == dbChannel) {
                throw new ResultException("活动不存在");
            }
        }
        Integer targetType = ad.getTargetType();
        String target = ad.getTarget();
        if (AdTargetType.topic.getValue().equals(targetType)) {
            SbTopicExample topicExample = new SbTopicExample();
            topicExample.setLimit(1);
            SbTopicExample.Criteria topicCriteria = topicExample.createCriteria();
            topicCriteria.andIdEqualTo(Integer.valueOf(target))
                    .andStatusEqualTo(Whether.yes.getValue())
                    .andAuditStatusEqualTo(AuditStatus.MAN_AUDIT_PASS.getValue());
            if ( topicMapper.countByExample(topicExample) == 0) {
                throw new ResultException("话题无效");
            }
        } else if (AdTargetType.activity.getValue().equals(targetType)) {
            SbActivityExample activityExample = new SbActivityExample();
            activityExample.setLimit(1);
            activityExample.createCriteria().andIdEqualTo(Integer.valueOf(target))
                    .andWhetherOnlineEqualTo(Whether.yes.getValue());
            if (activityMapper.countByExample(activityExample) == 0) {
                throw new ResultException("活动无效");
            }
        } else if (AdTargetType.album.getValue().equals(targetType)) {
            SbTopicAlbumExample albumExample = new SbTopicAlbumExample();
            albumExample.setLimit(1);
            albumExample.createCriteria().andIdEqualTo(Integer.valueOf(target))
                    .andStatusEqualTo(Whether.yes.getValue());
            if (albumMapper.countByExample(albumExample) == 0) {
                throw new ResultException("合辑无效");
            }
        }

        if (StringUtil.isEmpty(ad.getAdCover())) {
            throw new ResultException("广告封面图不能为空");
        }
        if (null == ad.getOrders()) {
            throw new ResultException("广告位置不能为空");
        } /*else {
            Integer orders = ad.getOrders();
            if (orders != 0) {
                SbAdExample example = new SbAdExample();
                example.setOrderByClause("end_time desc");
                example.setLimit(1);
                SbAdExample.Criteria criteria = example.createCriteria();
                criteria.andStatusEqualTo(Whether.yes.getValue());
                criteria.andAdTypeEqualTo(AdType.Carousel.getValue());
                criteria.andOrdersEqualTo(orders);
                List<SbAd> sbAds = adMapper.selectByExample(example);
                Integer id = ad.getId();
                if (!CollectionUtils.isNullOrEmpty(sbAds)) {
                    if (null != id) {
                        SbAd sbAd = adMapper.selectByPrimaryKey(id);
                        if(!(ad.getStartTime().equals(sbAd.getStartTime()) && ad.getEndTime().equals(sbAd.getEndTime()))){
                            example.clear();
                            example.createCriteria()
                                    .andStartTimeLessThanOrEqualTo(ad.getStartTime())
                                    .andEndTimeGreaterThanOrEqualTo(ad.getStartTime());
                            example.or().andEndTimeGreaterThanOrEqualTo(ad.getEndTime())
                                    .andStartTimeLessThanOrEqualTo(ad.getEndTime());
                            example.or().andStartTimeGreaterThanOrEqualTo(ad.getStartTime());
                            example.or().andEndTimeLessThanOrEqualTo(ad.getEndTime());
                            example.or().andStartTimeGreaterThanOrEqualTo(ad.getStartTime())
                                    .andEndTimeLessThanOrEqualTo(ad.getEndTime());

                            if (adMapper.countByExample(example) > 0) {
                                throw new ResultException("时间范围有重叠，重新选择");
                            }
                            if (ad.getStartTime().before(sbAds.get(0).getEndTime())) {
                                throw new ResultException("开始时间不正确，该广告位已有的最晚时间为:" + DateUtil.BASIC.format(sbAds.get(0).getEndTime()));
                            }
                        }
                    }else{
                        if (ad.getStartTime().before(sbAds.get(0).getEndTime())) {
                            throw new ResultException("开始时间不正确，该广告位已有的最晚时间为:" + DateUtil.BASIC.format(sbAds.get(0).getEndTime()));
                        }
                    }
                }
            }
        }*/
        if (null == ad.getTargetType()) {
            throw new ResultException("广告跳转类型不能为空");
        }
        if (StringUtil.isEmpty(ad.getTarget())) {
            throw new ResultException("广告封跳转地址不能为空");
        }
        if (null == ad.getStartTime()) {
            throw new ResultException("广告开始时间不能为空");
        }
        if (null == ad.getEndTime()) {
            throw new ResultException("广告结束时间不能为空");
        }

    }

    private void handelCover(SbAd ad) throws Exception {

        String cover = StringUtil.trim(ad.getAdCover());
        if (cover.length() > 0) {
            if (cover.contains(Configuration.temp_path)) { //重新上传的
                cover = new OSSClientUtil(Configuration.INSTANCE.endpoint).upload(cover, OSSClientUtil.upload_type.ad);
                ad.setAdCover(cover);
            }
        }
    }
}
