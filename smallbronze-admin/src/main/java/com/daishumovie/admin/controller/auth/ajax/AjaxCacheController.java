package com.daishumovie.admin.controller.auth.ajax;

import com.daishumovie.admin.dto.paginate.ParamDto;
import com.daishumovie.admin.dto.paginate.ReturnDto;
import com.daishumovie.base.dto.IdName;
import com.daishumovie.dao.cache.MyBatisRedisCache;
import com.daishumovie.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by feiFan.gou on 2017/9/11 16:42.
 */
@RestController
@RequestMapping("/ajax/cache/")
public class AjaxCacheController {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @RequestMapping(value = "paginate", method = RequestMethod.POST)
    public ReturnDto<IdName> paginate(ParamDto param) {
        List<IdName> list = new ArrayList<>();
        for (String s : MyBatisRedisCache.set) {
            list.add(new IdName(s,null));
        }
        Page<IdName> page = param.page();
        page.setTotal(list.size());
        page.setItems(list);
        return new ReturnDto<>(page);
    }

    @RequestMapping("/clear")
    public boolean list(@RequestParam String id) throws IOException {
        MyBatisRedisCache myBatisRedisCache = new MyBatisRedisCache(id);
        myBatisRedisCache.clear();
        return true;
    }
    @RequestMapping("/clearAll")
    public boolean clearAll() throws IOException {

        for (String id : MyBatisRedisCache.set) {
            MyBatisRedisCache myBatisRedisCache = new MyBatisRedisCache(id);
            myBatisRedisCache.clear();
        }

        return true;
    }
}
