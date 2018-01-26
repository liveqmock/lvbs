package com.daishumovie.base.dto;

import java.util.Date;

import lombok.Data;

@Data
public class TitleContentTimestamp {
	private String title;
	
	private String content;
	
	private Date time;
}
