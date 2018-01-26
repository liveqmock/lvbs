package com.daishumovie.base.dto.share;

import java.io.Serializable;
import java.util.List;

import com.daishumovie.base.dto.album.AlbumDto;
import com.daishumovie.base.dto.album.IdTitleImage;

import lombok.Data;

@Data
public class ShareAlbumDto implements Serializable {
	private static final long serialVersionUID = 569600641520966916L;

	private AlbumDto<IdTitleImage> album;

	/**
	 * 分享详情的底部广告列表；
	 */
	private List<ShareAd> adList;

	private List<AlbumDto<IdTitleImage>> moreAlbumList;
	
	private Integer hasMoreAlbum;
	
	private Integer hasMoreTopic;
}
