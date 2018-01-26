package com.daishumovie.base.dto;

import lombok.Data;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

@Data
public class IdNameType implements Serializable {

	private static final long serialVersionUID = 6997625548202755008L;

	private Long id;

	private String name;

	private Integer type;

	public IdNameType(Long id, String name, Integer type) {
		this.id = id;
		this.name = name;
		this.type = type;
	}
	
	public IdNameType(){
		
	}
}
