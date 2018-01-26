package com.daishumovie.base.enums.front;

import lombok.Getter;

/**
 * Created by feiFan.gou on 2017/9/12 15:32.
 * normal	正常图片，无色情
 * sexy	性感图片
 * porn	色情图片
 */
@Getter
public enum PornLabel {

    normal("正常图片，无色情"), sexy("性感图片"), porn("色情图片");

    private String name;

    PornLabel(String name) {

        this.name = name;
    }

    public static PornLabel get(String value) {

        if (null == value || value.trim().equals("")) {
            for (PornLabel label : PornLabel.values()) {
                if (label.name().equals(value)) {
                    return label;
                }
            }
        }
        return null;
    }
}
