package com.daishumovie.base.dto.category;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class CategoryDto implements Serializable {

	private static final long serialVersionUID = 4295178230950667602L;

	private Integer id;
	
	private String name;
	
	private String url;
	/**
	 * 该频道发言用户别名；
	 */
	private String aliasName;
}
