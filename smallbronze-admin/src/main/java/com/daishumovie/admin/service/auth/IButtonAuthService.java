package com.daishumovie.admin.service.auth;

import com.daishumovie.admin.dto.ButtonDto;
import com.daishumovie.dao.model.auth.OtButtonAuthority;

import java.util.List;

/**
 * Created by feiFan.gou on 2017/7/14 14:11.
 */
public interface IButtonAuthService {

    List<ButtonDto> buttonList(String resourceId, Long roleId);

    void update(OtButtonAuthority authority);

    void delete(Integer id);

    List<ButtonDto> menuList(Long roleId);

    void save(Long roleId, Integer[] buttonIds);
}
