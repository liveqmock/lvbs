package com.daishumovie.dao.model.auth;

import java.io.Serializable;
import java.util.Date;

public class RoleEntity implements Serializable {
    
	private Long id;

    private String name;

    private Integer type;

    private Integer status;

    private Date createTime;

    private Date updateTime;

    private String descn;

    private String departmentIdStr;
    
    private String typeStr;

    private boolean including;



	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isIncluding() {
        return including;
    }

    public void setIncluding(boolean including) {
        this.including = including;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getDescn() {
        return descn;
    }

    public void setDescn(String descn) {
        this.descn = descn;
    }

	public String getDepartmentIdStr() {
		return departmentIdStr;
	}

	public void setDepartmentIdStr(String departmentIdStr) {
		this.departmentIdStr = departmentIdStr;
	}

	public String getTypeStr() {
		return typeStr;
	}

	public void setTypeStr(String typeStr) {
		this.typeStr = typeStr;
	}
}