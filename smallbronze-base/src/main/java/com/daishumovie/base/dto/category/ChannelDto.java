package com.daishumovie.base.dto.category;

import com.daishumovie.base.dto.user.UserFollowDto;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author Cheng Yufei
 * @create 2017-09-09 16:18
 **/
@Data
public class ChannelDto implements Serializable {

    private static final long serialVersionUID = 1998325644007743157L;

    private Integer id;

    private String name;

    private String url;

    private String remarks;

    private String followNum;

    // 1 已关注  0未关注
    private Integer isFollow;
    /**
     * 关注频道未读话题个数；
     */
    private String unReadNum;

    List<UserFollowDto> userFollowDtoList;
}
