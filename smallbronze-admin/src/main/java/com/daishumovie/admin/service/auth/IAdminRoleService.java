package com.daishumovie.admin.service.auth;



import com.daishumovie.dao.model.auth.AdminRoleEntity;

import java.util.List;

/**
 * 管理员-角色关联操作
 */
public interface IAdminRoleService {

    AdminRoleEntity save(AdminRoleEntity entity);

    boolean delete(AdminRoleEntity entity);

    /**
     * 根据管理员id 和 角色 id 查找关联
     * @param adminId   如果不限定该条件，设为 null
     * @param roleId  如果不限定该条件，设为 null
     * @return
     */
    List<AdminRoleEntity> query(Long adminId, Long roleId);
}
