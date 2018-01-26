package com.daishumovie.api.controller.v1;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.daishumovie.base.dto.ad.AdListDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.daishumovie.base.dto.BaseListDto;
import com.daishumovie.base.dto.PageInDto;
import com.daishumovie.base.dto.ad.SbAdDto;
import com.daishumovie.base.enums.db.YesNoEnum;
import com.daishumovie.base.model.Response;
import com.daishumovie.dao.mapper.smallbronze.SbAdMapper;
import com.daishumovie.dao.model.SbAd;
import com.daishumovie.dao.model.SbAdExample;

/**
 * @author zhuruisong on 2017/10/19
 * @since 1.0
 */
@RestController
public class AdController {

	@Autowired
	SbAdMapper sbAdMapper;

	@RequestMapping("v1/ad/list/{type}")
	public Response<AdListDto> getAdList(@PathVariable int type) {

		SbAdExample example = new SbAdExample();
//		example.setLimit(6);
		example.setOrderByClause("orders, start_time DESC");
		SbAdExample.Criteria criteria = example.createCriteria();
		criteria.andAdTypeEqualTo(type);
		criteria.andStatusEqualTo(YesNoEnum.YES.getCode());
		Date currDate = new Date();
		criteria.andStartTimeLessThanOrEqualTo(currDate);
		criteria.andEndTimeGreaterThan(currDate);

		List<SbAd> sbAdList = sbAdMapper.selectByExample(example);

		if(sbAdList.isEmpty()){
			return new Response<>();
		}

		List<Integer> orders = new ArrayList<>();
		List<SbAdDto> list = sbAdList.stream()
				.filter(sbAd -> !orders.contains(sbAd.getOrders())
				).map(sbAd -> {
					orders.add(sbAd.getOrders());
					SbAdDto sbAdDto = new SbAdDto();
					BeanUtils.copyProperties(sbAd, sbAdDto);
					return sbAdDto;
				}).collect(Collectors.toList());

		AdListDto adListDto = new AdListDto();
		adListDto.setList(list);
		adListDto.setDuration(sbAdList.get(0).getDuration());
		return new Response<>(adListDto);

	}
}
