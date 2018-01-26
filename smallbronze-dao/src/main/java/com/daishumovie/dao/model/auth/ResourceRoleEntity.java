package com.daishumovie.dao.model.auth;


import java.io.Serializable;

public class ResourceRoleEntity implements Serializable {
    private Long id;

    private Long roleId;

    private String resourceUuid;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getResourceUuid() {
        return resourceUuid;
    }

    public void setResourceUuid(String resourceUuid) {
        this.resourceUuid = resourceUuid;
    }
}