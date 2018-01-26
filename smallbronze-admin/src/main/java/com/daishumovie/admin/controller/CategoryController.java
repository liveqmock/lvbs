package com.daishumovie.admin.controller;

import com.daishumovie.base.enums.db.CategoryType;
import com.daishumovie.dao.mapper.smallbronze.SbCommonCategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by feiFan.gou on 2017/8/31 18:21.
 * 素材分类同步相关接口
 */
@Controller
@RequestMapping("/category/")
public class CategoryController extends BaseController {

    @Autowired
    private SbCommonCategoryMapper categoryMapper;

    @RequestMapping(value = "index", method = RequestMethod.GET)
    public ModelAndView index() {

        ModelAndView view = new ModelAndView();
        view.setViewName("material/category/index");
        view.addObject("type_list", CategoryType.values());
        return view;
    }

    @RequestMapping(value = "initAdd", method = RequestMethod.GET)
    public ModelAndView initAdd() {

        ModelAndView view = new ModelAndView();
        view.setViewName("material/category/add");
        view.addObject("type_list", CategoryType.values());
        return view;
    }

    @RequestMapping(value = "initEdit",  method = RequestMethod.GET)
    public ModelAndView initEdit(Integer id) {

        ModelAndView view = new ModelAndView();
        view.setViewName("material/category/edit");
        view.addObject("type_list", CategoryType.values());
        view.addObject("category", categoryMapper.selectByPrimaryKey(id));
        return view;
    }
}
