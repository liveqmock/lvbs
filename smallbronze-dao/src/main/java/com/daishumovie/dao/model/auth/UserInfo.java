package com.daishumovie.dao.model.auth;


import com.daishumovie.dao.model.auth.enums.EnumRoleType;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UserInfo implements Serializable {

    private AdminEntity admin;
    private Set<RoleEntity> roles;
    private Set<ResourceEntity> resources;
    private ResourceEntity menu;
    List<Integer> authorityIds;
    public UserInfo(AdminEntity admin) {
        this.admin = admin;
        roles = new HashSet<>();
        resources = new HashSet<>();
    }
    public String getRealName(){
        return admin.getRealName();
    }

    public String getUsername() {
        return admin.getUsername();
    }

    public AdminEntity getAdmin() {
        return admin;
    }

    public void setAdmin(AdminEntity admin) {
        this.admin = admin;
    }

    public Set<ResourceEntity> getResources() {
        return resources;
    }

    public void setResources(Set<ResourceEntity> resources) {
        this.resources = resources;
    }

    public boolean hasResources() {
        return resources.isEmpty();
    }

    public Set<RoleEntity> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleEntity> roles) {
        this.roles = roles;
    }

    public ResourceEntity getMenu() {
        return menu;
    }

    public void setMenu(ResourceEntity menu) {
        this.menu = menu;
    }

    /**
     * 判断是否某一类型的角色
     * @param roleType 角色类型
     * @return
     */
    public boolean containRoleType(EnumRoleType roleType) {
        for (RoleEntity role : roles) {
            if (role.getType() == roleType.getIndex())
                return true;
        }
        return false;
    }

	public List<Integer> getAuthorityIds() {
		return authorityIds;
	}

	public void setAuthorityIds(List<Integer> authorityIds) {
		this.authorityIds = authorityIds;
	}



}
