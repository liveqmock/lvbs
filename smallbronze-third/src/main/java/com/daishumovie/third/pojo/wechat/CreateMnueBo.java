package com.daishumovie.third.pojo.wechat;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * @author zhuruisong on 2017/3/28
 * @since 1.0
 */
@Setter
@Getter
public class CreateMnueBo implements Serializable {


    private List<ParentButton> button;

    private Matchrule matchrule;
}
