package com.daishumovie.utils;


import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public final class Page<T> implements Serializable{
	private int total;
	private int pageIndex;
	private int maxPage;
	private int pageSize;
	private List<T> items = new ArrayList<>();
	private Integer hasNext;

	public Page(int pageIndex, int pageSize) {
		this.pageIndex = pageIndex;
		this.pageSize =pageSize;
	}

	public Page(int pageIndex, int pageSize, int total) {
		setPageIndex(pageIndex);
		setPageSize(pageSize);
		setTotal(total);
	}

	public int getFromIndex() {
		return ((getPageIndex() - 1) * this.pageSize);
	}

	public int getTotal() {
		return this.total;
	}

	public final void setTotal(int total) {
		if (this.pageSize <= 0) {
			this.total = total;
			this.maxPage = 1;
		} else {
			this.total = total;
			this.maxPage = ((total % this.pageSize == 0) ? total / this.pageSize : total / this.pageSize + 1);

			if (0 == this.maxPage)
				this.maxPage = 1;
		}
	}


}