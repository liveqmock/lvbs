package com.daishumovie.dao.model.auth;


import com.daishumovie.utils.StringUtil;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


public class ResourceEntity implements Serializable {
    
	private Long id;

    private String uuid;

    private String parentUuid;

    private String parentsUuids;

    private String name;

    private Integer type;

    private String path;

    private Integer status;

    private Date createTime;

    private Date updateTime;

    private String descn;
    
    private List<ResourceEntity> children;
    
    public String getPathWithUrlSeparator() {
        return "";
    }

    public List<ResourceEntity> getChildren() {
		return children;
	}

	public void setChildren(List<ResourceEntity> children) {
		this.children = children;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getParentUuid() {
        return parentUuid;
    }

    public void setParentUuid(String parentUuid) {
        this.parentUuid = parentUuid;
    }

    public String getParentsUuids() {
        return parentsUuids;
    }

    public void setParentsUuids(String parentsUuids) {
        this.parentsUuids = parentsUuids;
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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
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
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ResourceEntity)) return false;

        ResourceEntity that = (ResourceEntity) o;

        if (!uuid.equals(that.uuid)) return false;

        return true;
    }

    @Override
    public int hashCode() {

        if (StringUtil.isEmpty(uuid)) {
            return StringUtil.uuid().hashCode();
        }
        return uuid.hashCode();
    }
}