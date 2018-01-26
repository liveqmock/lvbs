package com.daishumovie.admin.service.auth;





import com.daishumovie.dao.model.auth.ResourceEntity;

import java.util.Collection;
import java.util.List;
import java.util.Map;


/**
 * 资源服务接口
 */
public interface IDataResourceService {

    ResourceEntity get(long id);

    ResourceEntity getByUuid(String uuid);

    List<ResourceEntity> queryByCondition(Map conditions);

    ResourceEntity save(ResourceEntity entity);

    boolean update(ResourceEntity entity);

    void delete(ResourceEntity entity);

    /**
     * 查询自身及其祖先节点，选择带路径的
     * @param uuids
     * @return
     */
    List<ResourceEntity> queryResourceLinkWithPath(Collection<String> uuids);

    /**
     * 查询 uuids 中 类型为菜单的资源
     * @param uuids
     * @return
     */
    List<ResourceEntity> queryMenuEntities(Collection<String> uuids);

    public List<ResourceEntity> queryRoleResource(Long id);

    List<ResourceEntity> queryByUsername(String username);
}
