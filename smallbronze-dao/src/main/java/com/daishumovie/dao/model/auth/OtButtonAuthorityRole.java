package com.daishumovie.dao.model.auth;

import java.io.Serializable;

/**
 * @author 
 */
public class OtButtonAuthorityRole implements Serializable {
    private Integer id;

    /**
     * 对应ot_roles
     */
    private Long roleId;

    /**
     * 对应ot_button_authority
     */
    private Integer buttonAuthorityId;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Integer getButtonAuthorityId() {
        return buttonAuthorityId;
    }

    public void setButtonAuthorityId(Integer buttonAuthorityId) {
        this.buttonAuthorityId = buttonAuthorityId;
    }
}