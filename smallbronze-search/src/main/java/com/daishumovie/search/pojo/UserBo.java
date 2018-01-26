package com.daishumovie.search.pojo;

import io.searchbox.annotations.JestId;
import lombok.Data;
import lombok.ToString;

/**
 * @author Cheng Yufei
 * @create 2017-11-03 15:24
 **/
@Data
@ToString
public class UserBo {

    @JestId
    private Integer uid;

    private Integer appId;

    private Integer sex;

    private Integer followNum;

    private Integer fansNum;

    private Integer publishCount;

    private String nickName;
}
