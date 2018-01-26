package com.daishumovie.admin.service;

import com.daishumovie.admin.dto.UserDto;
import com.daishumovie.admin.dto.paginate.ParamDto;
import com.daishumovie.admin.dto.paginate.ReturnDto;
import com.daishumovie.dao.model.DsmUser;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by feiFan.gou on 2017/9/6 15:07.
 */
public interface IUserService {

    ReturnDto<UserDto> paginate(ParamDto param, DsmUser user, String registerTime);

    void replyComment(Integer uid, Integer operatorId);

    void publishTopic(Integer uid, Integer operatorId);

    Map<String, Object> getFictitiousUsers(String name, int pageNumber, int pageSize);

    Map<Integer, DsmUser> userMapByUid(Set<Integer> userIdSet);

    UserDto getUser(Integer userId);

    void update(DsmUser record);

    List<Integer> getUidByNameLike(String name);

    /**
     * 获取虚拟用户
     * @return
     */
    Map<String, Object> fictitiousUserList(String name,int pageNumber,int pageSize);


}
