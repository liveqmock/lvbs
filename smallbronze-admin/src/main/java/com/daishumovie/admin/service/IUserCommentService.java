package com.daishumovie.admin.service;

import com.daishumovie.admin.dto.SbUserCommentDto;
import com.daishumovie.admin.dto.paginate.ParamDto;
import com.daishumovie.admin.dto.paginate.ReturnDto;
import com.daishumovie.dao.model.SbUserComment;

import java.util.Map;
import java.util.Set;

/**
 * Created by feiFan.gou on 2017/8/28 15:32.
 */
public interface IUserCommentService {


    ReturnDto<SbUserCommentDto> paginate(ParamDto param, String name,Integer type, String createTime, Integer status, Integer auditStatus,Integer appId);

    SbUserCommentDto findUserCommentById(Integer commentId);

    Map<Integer, SbUserComment> getUserCommentLists(Set<Integer> contentIdSet);

    void pass(Integer operatorId, String ...ids);

    void fail(Integer operatorId,String auditDesc,String ...ids);

    void update(SbUserComment record);
}
