package com.daishumovie.base.dto.user;

import lombok.Data;

import java.io.Serializable;

/**
 * @author zhuruisong on 2017/9/18
 * @since 1.0
 */
@Data
public class UserRecordDto  implements Serializable {
    private Integer appId;

    private Integer uid;

    /**
     * 频道id
     */
    private Integer channelId;

    private Integer topicId;
}
