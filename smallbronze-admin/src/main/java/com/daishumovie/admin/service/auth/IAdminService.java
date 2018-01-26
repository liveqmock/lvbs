package com.daishumovie.admin.service.auth;


import com.daishumovie.dao.model.auth.AdminEntity;
import com.daishumovie.utils.Page;

import java.util.List;
import java.util.Map;
import java.util.Set;


public interface IAdminService {

    /**
     * 获取admin.
     *
     * @param username
     * @return
     */
    AdminEntity getAdmin(String username);

    /**
     * 创建admin
     *
     * @param entity
     * @return
     */
    AdminEntity save(AdminEntity entity);

    /**
     * 删除admin.
     *
     * @param entity
     * @return
     */
    boolean delAdmin(AdminEntity entity);

    /**
     * 根据id取得admin
     *
     * @param id
     * @return
     */
    AdminEntity get(long id);

    /**
     * 更新用户信息
     *
     * @param entity
     * @return
     */
    boolean update(AdminEntity entity);
    
    /**
     * 按条件分页查询admin
     *
     * @param page
     * @param conditions
     * @return
     */
    Page<AdminEntity> queryAdminByPage(Page<AdminEntity> page, Map conditions);

    Map<Integer, String> userNameMap(Set<Long> userIdSet);

    List<Integer> userIdListByNameLike(String name);
}
