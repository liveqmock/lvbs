package com.daishumovie.admin.controller.auth;

import com.daishumovie.admin.controller.BaseController;
import com.daishumovie.base.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Set;

/**
 * @author zhuruisong on 2017/4/8
 * @since 1.0
 */
@Controller
@RequestMapping("/cache")
public class CacheController extends BaseController {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @RequestMapping("/index")
    public ModelAndView page(){

        return new ModelAndView("/admin/cache/index");
    }

    @RequestMapping("/flush/list")
    @CacheEvict(value = {Constants.CACHEABLE_LIST_C, Constants.CACHEABLE_LIST_T, Constants.CACHEABLE_LIST_L}, allEntries = true)
    public boolean flushList() {
        return true;
    }

    @RequestMapping("/get")
    public String get(@RequestParam String key) {
        return redisTemplate.opsForValue().get(key);
    }

    @RequestMapping("/keys")
    public Set<String> keys(@RequestParam String pattern) {
        return redisTemplate.keys(pattern);
    }


}
