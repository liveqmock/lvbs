package com.daishumovie.api.controller.v1;

import java.util.List;

import com.daishumovie.base.dto.KeyValue;
import com.daishumovie.base.enums.db.RespStatusEnum;
import com.daishumovie.utils.sensitiveword.SensitivewordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.daishumovie.api.service.CommentListService;
import com.daishumovie.api.service.CommentService;
import com.daishumovie.base.dto.BaseListDto;
import com.daishumovie.base.dto.PageInDto;
import com.daishumovie.base.dto.comment.CommentDto;
import com.daishumovie.base.enums.db.UserCommentTargetType;
import com.daishumovie.base.enums.front.PageSource;
import com.daishumovie.base.model.Header;
import com.daishumovie.base.model.LocalData;
import com.daishumovie.base.model.Response;

@RestController("v1.CommentController")
@RequestMapping("/v1/comment")
public class CommentController {

	@Autowired
	private CommentListService commentListService;
	@Autowired
	private CommentService commentService;

	/**
	 * 话题回复列表
	 *
	 * @param type
	 * @param pageIndex
	 * @return
	 */
	@RequestMapping(value = "/replyList/{topicId}/{type}/{pageIndex}")
	public Response<BaseListDto<CommentDto>> topicReplyList(@PathVariable("topicId") Integer topicId,
															@PathVariable("type") Integer type, @PathVariable("pageIndex") Integer pageIndex,
															@RequestHeader(value = "appId", required = false) Integer appId) {

		Response<BaseListDto<CommentDto>> resp = new Response<BaseListDto<CommentDto>>();
		BaseListDto<CommentDto> baseDto = new BaseListDto<>();
		PageInDto pageInfo = new PageInDto(pageIndex, 10);

		List<CommentDto> topicList = commentListService.getCommentByPage(pageInfo, appId, topicId, UserCommentTargetType.TOPIC.getValue(), null, null, type,
				PageSource.REPLY_LIST);
		baseDto.setList(topicList);
		baseDto.setPageIndex(pageIndex);
		baseDto.setHasNext(topicList.size() >= pageInfo.getPageSize() ? 1 : 0);
		resp.setResult(baseDto);
		return resp;
	}

	/**
	 * 回复的评论列表
	 *
	 * @param type
	 * @param pageIndex
	 * @return
	 */
	@RequestMapping(value = "/commentList/{pCommentId}/{type}/{pageIndex}")
	public Response<BaseListDto<CommentDto>> commentReplyList(@PathVariable("pCommentId") Integer pCommentId,
															  @PathVariable("type") Integer type, @PathVariable("pageIndex") Integer pageIndex,
															  @RequestHeader(value = "appId", required = false) Integer appId) {

		Response<BaseListDto<CommentDto>> resp = new Response<BaseListDto<CommentDto>>();
		BaseListDto<CommentDto> baseDto = new BaseListDto<>();
		PageInDto pageInfo = new PageInDto(pageIndex, 10);

		List<CommentDto> topicList = commentListService.getCommentByPage(pageInfo, appId, null, UserCommentTargetType.TOPIC.getValue(), pCommentId, null, type,
				PageSource.COMMENT_LIST);

		baseDto.setList(topicList);
		baseDto.setPageIndex(pageIndex);
		baseDto.setHasNext(topicList.size() >= pageInfo.getPageSize() ? 1 : 0);
		resp.setResult(baseDto);
		return resp;

	}

	/**
	 * 回复详情 顶部信息
	 * @param commentId
	 * @param appId
	 * @return
	 */
	@RequestMapping(value = "/commentReplyDetail/{commentId}")
	public Response commentReplyDetail(@PathVariable Integer commentId,@RequestHeader(value = "appId", required = false) Integer appId) {
		Header header = LocalData.HEADER.get();
		Integer uid = header.getUid();
		return commentListService.commentReplyDetailTop(commentId,uid,appId);
	}

	/**
	 * 话题回复列表(新版简化 9/20 )
	 * @param pageIndex
	 * @return
	 */
	@RequestMapping(value = "/commentList/{topicId}/{pageIndex}")
	public Response<BaseListDto<CommentDto>> commentList(@PathVariable("topicId") Integer topicId,
														 @PathVariable("pageIndex") Integer pageIndex,
														 @RequestHeader(value = "appId", required = false) Integer appId) {

		Response<BaseListDto<CommentDto>> resp = new Response<BaseListDto<CommentDto>>();
		BaseListDto<CommentDto> baseDto = new BaseListDto<>();
		PageInDto pageInfo = new PageInDto(pageIndex, 10);

		List<CommentDto> topicList = commentListService.getCommentList(topicId, UserCommentTargetType.TOPIC.getValue(),appId,pageInfo);

		baseDto.setList(topicList);
		baseDto.setPageIndex(pageIndex);
		baseDto.setHasNext(topicList.size() >= pageInfo.getPageSize() ? 1 : 0);
		resp.setResult(baseDto);
		return resp;
	}

	@RequestMapping("/del")
	public Response<String> del(@RequestParam Integer commentId){

		return commentService.del(commentId);
	}

	/**
	 * 主题评论/回复评论
	 * @since 1.1.0
	 * @param targetId
	 * @param targetType
	 * @param pid
	 * @param content
	 * @param imgList
	 * @param videoId
	 * @return
	 */
	@RequestMapping(value="/add", method = RequestMethod.POST)
	public Response add(@RequestParam Integer targetId, @RequestParam Integer targetType,
						@RequestParam(required = false) Integer pid,
						@RequestParam(required = false) Integer pcid,
						@RequestParam(required = false)  String content,
						@RequestParam(required = false)  String imgList,
						@RequestParam(required = false)  Integer videoId){

		return commentService.save(targetId, targetType, pid, pcid, content, imgList, videoId);
	}

	/**
	 * 通用回复列表(新版简化 10/20 )
	 * @since 1.1.0
	 * @param pageIndex
	 * @return
	 */
	@RequestMapping(value = "/commentListNew/{targetType}/{targetId}/{pageIndex}")
	public Response<BaseListDto<CommentDto>> commentListNew(@PathVariable("targetType") Integer targetType,
															@PathVariable("targetId") Integer targetId, @PathVariable("pageIndex") Integer pageIndex,
															@RequestHeader(value = "appId") Integer appId) {

		Response<BaseListDto<CommentDto>> resp = new Response<BaseListDto<CommentDto>>();
		BaseListDto<CommentDto> baseDto = new BaseListDto<>();
		PageInDto pageInfo = new PageInDto(pageIndex, 10);

		List<CommentDto> topicList = commentListService.getCommentList(targetId, targetType, appId, pageInfo);

		baseDto.setList(topicList);
		baseDto.setPageIndex(pageIndex);
		baseDto.setHasNext(topicList.size() >= pageInfo.getPageSize() ? 1 : 0);
		resp.setResult(baseDto);
		return resp;
	}

	@RequestMapping("/isContaintSensitiveWord")
	public Response<KeyValue> isContaintSensitiveWord(String content){
		boolean containtSensitiveWord = SensitivewordUtil.isContaintSensitiveWord(content, SensitivewordUtil.minMatchTYpe);
		KeyValue kv = new KeyValue();
		kv.setValue(containtSensitiveWord?"1":"0");
		return new Response<>(kv);
	}

}