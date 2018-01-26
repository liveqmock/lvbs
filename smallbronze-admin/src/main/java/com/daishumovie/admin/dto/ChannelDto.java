package com.daishumovie.admin.dto;


import com.daishumovie.dao.model.SbChannel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * Created by feiFan.gou on 2017/9/7 17:06.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ChannelDto extends SbChannel {

    private List<ChannelDto> children;
}
