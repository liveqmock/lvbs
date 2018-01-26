package com.daishumovie.admin.controller;

import com.daishumovie.admin.service.IMaterialService;
import com.daishumovie.base.enums.db.App;
import com.daishumovie.base.enums.db.CategoryType;
import com.daishumovie.dao.mapper.smallbronze.SbMaterialContentMapper;
import com.daishumovie.dao.model.SbMaterialCategoryApp;
import com.daishumovie.utils.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Created by feiFan.gou on 2017/8/28 10:23.
 * 素材管理，同步请求相关接口
 */
@Controller
@RequestMapping("/material/")
public class MaterialController extends BaseController {

    @Autowired
    private IMaterialService materialService;
    @Autowired
    private SbMaterialContentMapper materialMapper;

    @RequestMapping(value = "index", name = "素材列表页", method = RequestMethod.GET)
    public ModelAndView index(@RequestParam("category_type") Integer categoryType) {

        ModelAndView view = new ModelAndView();
        view.setViewName("material/index");
        view.addObject("app_list", App.values());
        if (null == categoryType) { //缺省值为1
            categoryType = 1;
        }
        view.addObject("category_type", categoryType);
        return view;
    }

    @RequestMapping(value = "initAdd", method = RequestMethod.GET)
    public ModelAndView initAdd(Integer type) {

        ModelAndView view = new ModelAndView();
        view.setViewName("material/add");
        view.addObject("app_list", App.values());
        view.addObject("category_type",type);
        view.addObject("category_name", CategoryType.get(type).getName());
        view.addObject("category_list",materialService.categoryType(type));
        return view;
    }

    @RequestMapping(value = "initEdit", method = RequestMethod.GET)
    public ModelAndView initEdit(Integer type, Integer id) {

        ModelAndView view = new ModelAndView();
        view.setViewName("material/edit");
        view.addObject("app_list", App.values());
        view.addObject("category_type",type);
        view.addObject("category_name", CategoryType.get(type).getName());
        view.addObject("category_list",materialService.categoryType(type));
        view.addObject("material", materialMapper.selectByPrimaryKey(id));
        List<SbMaterialCategoryApp> relList = materialService.getRelListByMaterial(id);
        view.addObject("rel", CollectionUtils.isNullOrEmpty(relList) ? null : relList.get(0));
        return view;
    }
}
