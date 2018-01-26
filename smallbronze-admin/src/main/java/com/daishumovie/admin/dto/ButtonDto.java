package com.daishumovie.admin.dto;

import com.daishumovie.dao.model.auth.OtButtonAuthority;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by feiFan.gou on 2017/7/14 14:22.
 */
@Getter
@Setter
public class ButtonDto extends OtButtonAuthority {

    private List<ButtonDto> children;

    private String uuid;

    private boolean has = false;
}
