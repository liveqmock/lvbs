package com.daishumovie.base.dto.share;

import com.daishumovie.base.dto.BaseListDto;
import lombok.Getter;
import lombok.Setter;

/**
 * @author zhuruisong on 2017/10/23
 * @since 1.0
 */
@Setter
@Getter
public class ActivifyDto<T> extends BaseListDto<T> {

    /**
     * 其他描述；
     */
    private String remark;
    /**
     * 封面
     */
    private String cover;
    /**
     * 格式化的时间
     */
    private String time;
    /**
     * 回复数
     */
    private Integer replyNum;
    /**
     * 是否关注
     */
    private Integer hasFollow;
    /**
     * 专辑id
     */
    private Integer id;

    /**
     * 合辑视频数量
     */
    private Integer count;
}
