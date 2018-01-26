package com.daishumovie.admin.annotation;

import com.daishumovie.base.enums.db.OperationObject;
import com.daishumovie.base.enums.db.OperationType;

import java.lang.annotation.*;

/**
 * Created by feiFan.gou on 2017/9/9 18:43.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface LogConfig {

    boolean required() default true;

    OperationObject object();

    OperationType type();
}
