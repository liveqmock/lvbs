package com.daishumovie.base.dto.album;

import com.daishumovie.base.dto.BaseListDto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class AlbumDto<T> extends BaseListDto<T> {

	private static final long serialVersionUID = 4826831845926937164L;
	/**
	 * 封面
	 */
	private String cover;
	/**
	 * 格式化的时间
	 */
	private String time;
	/**
	 * 回复数
	 */
	private String replyNum;
	/**
	 * 是否关注
	 */
	private Integer hasFollow;
	/**
	 * 专辑id
	 */
	private Integer id;

	/**
	 * 合辑视频数量
	 */
	private Integer count;
	/**
	 * 状态
	 */
	private Integer status;
}
