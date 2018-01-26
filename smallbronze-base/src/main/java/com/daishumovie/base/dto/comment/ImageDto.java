package com.daishumovie.base.dto.comment;

import java.io.Serializable;

import lombok.Data;

@Data
public class ImageDto implements Serializable {

	private static final long serialVersionUID = -2806472057379224885L;

	private String oriUrl;

	private String dimension;
}
