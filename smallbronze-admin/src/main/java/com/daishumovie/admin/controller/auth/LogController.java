package com.daishumovie.admin.controller.auth;

import com.daishumovie.base.enums.db.OperationObject;
import com.daishumovie.base.enums.db.OperationResult;
import com.daishumovie.base.enums.db.OperationType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by feiFan.gou on 2017/9/11 16:14.
 */
@RequestMapping("/log/")
@Controller
public class LogController {

    @RequestMapping(value = "index", method = RequestMethod.GET)
    public ModelAndView index() {

        ModelAndView view = new ModelAndView("admin/log/index");
        view.addObject("operation_type", OperationType.values());
        view.addObject("operation_result", OperationResult.values());
        view.addObject("operation_object", OperationObject.values());
        return view;
    }
}
