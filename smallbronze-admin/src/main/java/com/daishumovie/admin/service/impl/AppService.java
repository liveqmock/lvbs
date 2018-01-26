package com.daishumovie.admin.service.impl;

import com.daishumovie.admin.dto.DsmAppVersionDto;
import com.daishumovie.admin.dto.paginate.ParamDto;
import com.daishumovie.admin.dto.paginate.ReturnDto;
import com.daishumovie.admin.service.IAppService;
import com.daishumovie.base.enums.db.App;
import com.daishumovie.base.model.Response;
import com.daishumovie.dao.mapper.smallbronze.DsmAppVersionMapper;
import com.daishumovie.dao.model.DsmAppVersion;
import com.daishumovie.dao.model.DsmAppVersionExample;
import com.daishumovie.utils.Page;
import com.daishumovie.utils.StringUtil;
import com.google.common.collect.Lists;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by feiFan.gou on 2017/5/12 14:08.
 */
@Log4j
@Service
public class AppService implements IAppService {

    @Autowired
    private DsmAppVersionMapper dsmAppVersionMapper;


    @Override
    public ReturnDto<DsmAppVersionDto> getDsmAppVersionByConditions(ParamDto param, Integer appId,String version_num, Integer plat) {

        DsmAppVersionExample example = new DsmAppVersionExample();
        example.setLimit(param.limit());
        example.setOffset(param.offset());
        example.setOrderByClause("update_time desc");
        DsmAppVersionExample.Criteria criteria = example.createCriteria();
        if (!StringUtil.isEmpty(version_num))
            criteria.andVersionNumLike(version_num);
        if (plat != null)
            criteria.andPlatEqualTo(plat);
        if (appId != null){
            criteria.andAppIdEqualTo(appId);
        }
        Long total = dsmAppVersionMapper.countByExample(example);
        List<DsmAppVersionDto> dtolist = Lists.newArrayList();
        if (total >0){
            List<DsmAppVersion> list = dsmAppVersionMapper.selectByExample(example);
            if (list != null && list.size() >0){
                for(DsmAppVersion dsmAppVersion : list){
                    DsmAppVersionDto dto = new DsmAppVersionDto();
                    BeanUtils.copyProperties(dsmAppVersion,dto);
                    dto.setAppName(App.get(dto.getAppId()).getAppName());
                    dtolist.add(dto);
                }
            }
        }
        Page<DsmAppVersionDto> page = param.page();
        page.setItems(dtolist);
        page.setTotal(total.intValue());
        return new ReturnDto<>(page);
    }

    @Override
    public Response save(DsmAppVersion record) {

        DsmAppVersionExample example = new DsmAppVersionExample();
        example.setOrderByClause(" build desc");
        DsmAppVersionExample.Criteria criteria = example.createCriteria();
        criteria.andVersionNumEqualTo(record.getVersionNum());
        criteria.andPlatEqualTo(record.getPlat());
//        int build = VersionUtil.getAppBuild(record.getVersionNum());
//        criteria.andBuildEqualTo(build);
//        record.setBuild(build);
        List<DsmAppVersion> list = dsmAppVersionMapper.selectByExample(example);
        if (list != null && list.size() >0){
            return new Response<>("该版本已经存在");
        }
        DsmAppVersionExample maxexample = new DsmAppVersionExample();
        maxexample.setOrderByClause(" build desc");
        List<DsmAppVersion> maplist = dsmAppVersionMapper.selectByExample(maxexample);
        if (maplist != null && maplist.size() >0){
            record.setBuild(maplist.get(0).getBuild()+1);
        }else{
            record.setBuild(1000);
        }

        int flag = dsmAppVersionMapper.insertSelective(record);
        Response<String> resp = new Response<>();
        if (flag > 0)
            return resp;
        else
            resp.setResult("更新失败");
        return resp;
    }

    @Override
    public Response update(DsmAppVersion record) {
//        int build = VersionUtil.getAppBuild(record.getVersionNum());
//        record.setBuild(build);
        int flag = dsmAppVersionMapper.updateByPrimaryKeySelective(record);
        Response<String> resp = new Response<>();
        if (flag > 0)
            return resp;
        else
            resp.setResult("更新失败");
        return resp;
    }

    @Override
    public DsmAppVersionDto getDsmAppVersionDtoById(Integer id) {
        if (id == null) return null;

        DsmAppVersion dsmAppVersion = dsmAppVersionMapper.selectByPrimaryKey(id);
        DsmAppVersionDto dto = null;
        if (dsmAppVersion != null){
            dto = new DsmAppVersionDto();
            BeanUtils.copyProperties(dsmAppVersion,dto);
        }
        return dto;
    }

    @Override
    public List<DsmAppVersion> getDsmAppVersionList(Integer appId,Integer plat) {
        DsmAppVersionExample example = new DsmAppVersionExample();
        example.setOrderByClause(" build desc");
        DsmAppVersionExample.Criteria criteria = example.createCriteria();
        criteria.andPlatEqualTo(plat);
        if (null != appId){
            criteria.andAppIdEqualTo(appId);
        }
        return dsmAppVersionMapper.selectByExample(example);
    }
}
