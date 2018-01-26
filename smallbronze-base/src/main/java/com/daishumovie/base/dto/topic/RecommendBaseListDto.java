package com.daishumovie.base.dto.topic;

import java.util.List;

import com.daishumovie.base.dto.BaseListDto;
import com.daishumovie.base.dto.PageInDto;

import lombok.Getter;

@Getter
public class RecommendBaseListDto<T> extends BaseListDto<T> {

	private Integer bucketId;

	private static final long serialVersionUID = 8230093233979401783L;

	public RecommendBaseListDto(List<T> list, PageInDto pageInDto, Integer bucketId) {
		super(list, pageInDto);
		this.bucketId = bucketId;
	}
}
