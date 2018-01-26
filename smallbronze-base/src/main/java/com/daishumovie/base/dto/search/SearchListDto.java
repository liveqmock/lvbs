package com.daishumovie.base.dto.search;

import java.util.List;

import com.daishumovie.base.dto.BaseListDto;
import com.daishumovie.base.dto.PageInDto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class SearchListDto<T> extends BaseListDto<T> {

	private static final long serialVersionUID = -994329276729756173L;

	public SearchListDto(List<T> list, PageInDto pageInDto) {
		super(list, pageInDto);
	}

	public SearchListDto() {
	}

	private String userCount;

	private String albumCount;

	private String topicCount;
}
