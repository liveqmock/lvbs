package com.daishumovie.base.dto.user;

import lombok.Data;

/**
 * @author Cheng Yufei
 * @create 2017-09-22 10:28
 **/
@Data
public class UserInfoEdit {

    private Integer uid;

    private String nickName;

    private String avatar;

    private String introduce;

    //1男 2女 0未知
    private Integer sex;


}


