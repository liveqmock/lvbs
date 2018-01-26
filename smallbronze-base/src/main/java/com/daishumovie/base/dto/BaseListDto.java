package com.daishumovie.base.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class BaseListDto<T> implements Serializable {

	private static final long serialVersionUID = 8725100709437382660L;

	private List<T> list;

	private Integer hasNext;

	private Integer pageIndex;

	private String title;

	private String subtitle;

	public BaseListDto() {
	}

	public BaseListDto(List<T> list, PageInDto pageInDto) {
		this.list = list;
		this.pageIndex = pageInDto.getPageIndex();
		if (list.isEmpty() || list.size() < pageInDto.getPageSize()) {
			hasNext = 0;
			return;
		}
		hasNext = 1;
	}

	public void setList(List<T> list, PageInDto pageInDto) {
		this.list = list;
		this.pageIndex = pageInDto.getPageIndex();
		if (list.isEmpty() || list.size() < pageInDto.getPageSize()) {
			hasNext = 0;
			return;
		}
		hasNext = 1;
	}
}
