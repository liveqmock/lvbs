package com.daishumovie.api.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.daishumovie.base.dto.BaseListDto;
import com.daishumovie.base.dto.PageInDto;
import com.daishumovie.base.dto.topic.BarrageDto;
import com.daishumovie.base.enums.db.RespStatusEnum;
import com.daishumovie.base.model.Response;
import com.daishumovie.dao.mapper.smallbronze.SbVideoBarrageMapper;
import com.daishumovie.dao.mapper.smallbronze.SbVideoMapper;
import com.daishumovie.dao.model.SbVideoBarrage;
import com.daishumovie.dao.model.SbVideoBarrageExample;
import com.daishumovie.utils.CollectionUtils;

/**
 * @author Cheng Yufei
 * @create 2017-09-12 10:54
 **/
@Service
public class BarrageService {


    @Autowired
    private SbVideoBarrageMapper sbVideoBarrageMapper;

    @Autowired
    private SbVideoMapper sbVideoMapper;

    @Transactional
    public Response<String> save(Integer videoId, String content, Integer time, Integer appId,Integer uid) {

        try {
            SbVideoBarrage barrage = new SbVideoBarrage();
            barrage.setVideoId(videoId);
            barrage.setContent(content);
            barrage.setTimeMillisecond(time);
            barrage.setUid(uid);
            if (null != appId) {
                barrage.setAppId(appId);
            }
            sbVideoBarrageMapper.insertSelective(barrage);

            //增加弹幕数
            sbVideoMapper.selfPlusMinusByPrimaryKey("barrage_num","+",1,videoId);

            return new Response<>();
        } catch (Exception e) {
            return new Response<>(RespStatusEnum.ERROR);
        }
    }

    public Response<BaseListDto<BarrageDto>> getBarrageList(Integer videoId, Integer appId, PageInDto pageInDto) {

        List<BarrageDto> barrageDtoList = new ArrayList<>();
        BaseListDto<BarrageDto> baseListDto = new BaseListDto<BarrageDto>();
        baseListDto.setList(barrageDtoList);
        baseListDto.setPageIndex(pageInDto.getPageIndex());
        baseListDto.setHasNext(0);

        SbVideoBarrageExample example = new SbVideoBarrageExample();
        example.setOffset(pageInDto.getOffset());
        example.setLimit(pageInDto.getLimit());
        example.setOrderByClause("time_millisecond asc");
        SbVideoBarrageExample.Criteria criteria = example.createCriteria();
        criteria.andVideoIdEqualTo(videoId);
        if (null != appId) {
            criteria.andAppIdEqualTo(appId);
        }
        List<SbVideoBarrage> sbVideoBarrages = sbVideoBarrageMapper.selectByExample(example);
        if (CollectionUtils.isNullOrEmpty(sbVideoBarrages)) {
            return new Response<>(baseListDto);
        }
        for (SbVideoBarrage videoBarrage : sbVideoBarrages) {
            BarrageDto barrageDto = new BarrageDto();
            BeanUtils.copyProperties(videoBarrage, barrageDto);
            barrageDtoList.add(barrageDto);
        }
        baseListDto.setHasNext(barrageDtoList.size() < pageInDto.getPageSize() ? 0 : 1);
        return new Response<>(baseListDto);

    }
}
