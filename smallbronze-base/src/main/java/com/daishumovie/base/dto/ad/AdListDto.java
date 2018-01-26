package com.daishumovie.base.dto.ad;

import lombok.Data;

import java.util.List;

/**
 * @author zhuruisong on 2017/10/27
 * @since 1.0
 */
@Data
public class AdListDto {

    private List<SbAdDto> list;
    private Integer duration;
}
