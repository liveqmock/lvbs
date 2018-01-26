package com.daishumovie.api.controller.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.daishumovie.api.service.AlbumService;
import com.daishumovie.base.dto.BaseListDto;
import com.daishumovie.base.dto.PageInDto;
import com.daishumovie.base.dto.album.AlbumDto;
import com.daishumovie.base.dto.album.IdTitleImage;
import com.daishumovie.base.model.Response;

@RestController
@RequestMapping("/v1/album")
public class AlbumController {

	@Autowired
	private AlbumService albumService;

	/**
	 * 合辑列表接口
	 * 
	 * @param pageIndex
	 * @param appId
	 * @return
	 */
	@RequestMapping(value = "/list/{pageIndex}", method = { RequestMethod.GET })
	public Response<BaseListDto<AlbumDto<IdTitleImage>>> getAlbumList(@PathVariable Integer pageIndex,
			@RequestHeader(value = "appId") Integer appId) {
		PageInDto pageInDto = new PageInDto(pageIndex, 6);
		return albumService.getAlbumList(appId, pageInDto);
	}

	/**
	 * 合辑详情接口
	 * 
	 * @param albumId
	 * @param appId
	 * @return
	 */
	@RequestMapping(value = "/detail/{albumId}", method = { RequestMethod.GET })
	public Response<AlbumDto<IdTitleImage>> getAlbumDetail(@PathVariable Integer albumId,
			@RequestHeader(value = "appId") Integer appId) {
		return albumService.getAlbumDetail(albumId, appId);
	}
}
