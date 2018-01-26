package com.daishumovie.api.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.daishumovie.base.enums.db.AuditStatus;
import com.daishumovie.base.enums.db.RespStatusEnum;
import com.daishumovie.base.enums.db.UserCommentTargetType;
import com.daishumovie.base.enums.db.YesNoEnum;
import com.daishumovie.base.model.Header;
import com.daishumovie.base.model.LocalData;
import com.daishumovie.base.model.Response;
import com.daishumovie.dao.mapper.smallbronze.DsmUserMapper;
import com.daishumovie.dao.mapper.smallbronze.SbActivityMapper;
import com.daishumovie.dao.mapper.smallbronze.SbTopicAlbumMapper;
import com.daishumovie.dao.mapper.smallbronze.SbTopicMapper;
import com.daishumovie.dao.mapper.smallbronze.SbUserCommentMapper;
import com.daishumovie.dao.model.DsmUser;
import com.daishumovie.dao.model.SbActivity;
import com.daishumovie.dao.model.SbTopic;
import com.daishumovie.dao.model.SbTopicAlbum;
import com.daishumovie.dao.model.SbUserComment;
import com.daishumovie.utils.FastJsonUtils;
import com.daishumovie.utils.sensitiveword.SensitivewordUtil;

/**
 * @author zhuruisong on 2017/4/1
 * @since 1.0
 */
@Service
public class CommentService {

	@Autowired
	private SbUserCommentMapper sbUserCommentMapper;

	@Autowired
	private SbTopicMapper sbTopicMapper;

	@Autowired
	private SbActivityMapper activityMapper;

	@Autowired
	private DsmUserMapper dsmUserMapper;

	@Autowired
	private SbTopicAlbumMapper albumMapper;

	/**
	 * 保存评论
	 *
	 * @param content
	 *            评论内容
	 * @return Response
	 */
	public Response<String> save(Integer targetId, Integer targetType, Integer pid, Integer pcid, String content,
								 String imgList, Integer videoId) {

		DsmUser user = FastJsonUtils.toBean(LocalData.USER_JSON.get(), DsmUser.class);
		DsmUser userByUid = dsmUserMapper.selectByPrimaryKey(user.getUid());
		if (YesNoEnum.YES.getCode().intValue() != userByUid.getIsReplyAuth()) {
			return new Response<>(RespStatusEnum.TOPIC_NOT_AUTH);
		}

		if (StringUtils.isBlank(content) && StringUtils.isBlank(imgList) && videoId == null) {
			return new Response<>(RespStatusEnum.COMMENT_BLANK_CONTENT);
		}

		if (content.length() > 150) {
			return new Response<>(RespStatusEnum.PARAM_FAIL);
		}

		if (StringUtils.isNotBlank(imgList)) {
			Object[] objects = FastJsonUtils.toArray(imgList);
			if (objects.length > 9) {
				return new Response<>(RespStatusEnum.PARAM_FAIL);
			}
		}

		UserCommentTargetType targetTypeEnum = UserCommentTargetType.get(targetType);
		if (targetTypeEnum == null) {
			return new Response<>(RespStatusEnum.PARAM_FAIL);
		}
		switch (targetTypeEnum) {
			case TOPIC:
				SbTopic sbTopic = sbTopicMapper.selectByPrimaryKey(targetId);
				if (sbTopic == null) {
					return new Response<>(RespStatusEnum.PARAM_FAIL);
				}
				break;
			case ACTIVITY:
				SbActivity sbActivity = activityMapper.selectByPrimaryKey(targetId);
				if (sbActivity == null) {
					return new Response<>(RespStatusEnum.PARAM_FAIL);
				}
				break;
			case ALBUM:
				SbTopicAlbum album = albumMapper.selectByPrimaryKey(targetId);
				if (album == null) {
					return new Response<>(RespStatusEnum.PARAM_FAIL);
				}
				break;
			default:
				break;
		}

		boolean containtSensitiveWord = SensitivewordUtil.isContaintSensitiveWord(content,
				SensitivewordUtil.minMatchTYpe);
		if (containtSensitiveWord) {
			return new Response<>(RespStatusEnum.PARAM_SENSITIVE_WORD);
		}

		Header header = LocalData.HEADER.get();
		SbUserComment sbUserComment = new SbUserComment();
		sbUserComment.setTargetId(targetId);
		sbUserComment.setTargetType(targetType);
		sbUserComment.setPid(pid);

		sbUserComment.setAppId(Integer.valueOf(header.getAppId()));
		sbUserComment.setContent(content);
		sbUserComment.setImgList(imgList);
		sbUserComment.setVideoId(videoId);
		sbUserComment.setUid(user.getUid());
		// 敏感字过滤后设置为机审已通过；
		sbUserComment.setAuditStatus(AuditStatus.MACHINE_AUDIT_PASS.getValue());

		if (pcid != null) {
			SbUserComment targetComment = sbUserCommentMapper.selectByPrimaryKey(pcid);
			if (targetComment == null) {
				return new Response<>(RespStatusEnum.COMMENT_NOT_EXIST);
			}
			int puid = targetComment.getUid();
			sbUserComment.setPuid(puid);
			sbUserComment.setPcid(pcid);
		}

		sbUserCommentMapper.insertSelective(sbUserComment);
		plusMinusTargetRelyNum(sbUserComment, true);
		return new Response<>();

	}


	public Response<String> del(Integer commentId) {
		SbUserComment sbUserComment = sbUserCommentMapper.selectByPrimaryKey(commentId);
		if (sbUserComment == null) {
			return new Response<>(RespStatusEnum.COMMENT_NOT_EXIST);
		}
		Header header = LocalData.HEADER.get();
		if (!header.getAppId().equals(sbUserComment.getAppId().toString())) {
			return new Response<>(RespStatusEnum.COMMENT_NOT_EXIST);
		}

		DsmUser user = FastJsonUtils.toBean(LocalData.USER_JSON.get(), DsmUser.class);
		if (!sbUserComment.getUid().equals(user.getUid())) {
			return new Response<>(RespStatusEnum.COMMENT_DEL_NOT_AUTH);
		}
		if (sbUserComment.getStatus().equals(YesNoEnum.NO.getCode())) {
			return new Response<>();
		}
		SbUserComment record = new SbUserComment();
		record.setId(commentId);
		record.setStatus(YesNoEnum.NO.getCode());
		sbUserCommentMapper.updateByPrimaryKeySelective(record);
		plusMinusTargetRelyNum(sbUserComment, false);
		return new Response<>();
	}

	public void plusMinusTargetRelyNum(SbUserComment sbUserComment, boolean isPlus) {
		if(isPlus) {
			UserCommentTargetType targetType = UserCommentTargetType.get(sbUserComment.getTargetType());
			switch (targetType) {
				case TOPIC:
					sbTopicMapper.selfPlusMinusByPrimaryKey("reply_num", "+", 1, sbUserComment.getTargetId());
					break;
				case ACTIVITY:
					activityMapper.selfPlusMinusByPrimaryKey("reply_num", "+", 1, sbUserComment.getTargetId());
					break;
				case ALBUM:
					albumMapper.selfPlusMinusByPrimaryKey("reply_num", "+", 1, sbUserComment.getTargetId());
					break;
				default:
					break;
			}
		} else {
			UserCommentTargetType targetType = UserCommentTargetType.get(sbUserComment.getTargetType());
			switch (targetType) {
				case TOPIC:
					sbTopicMapper.selfPlusMinusByPrimaryKey("reply_num", "-", 1, sbUserComment.getTargetId());
					break;
				case ACTIVITY:
					activityMapper.selfPlusMinusByPrimaryKey("reply_num", "-", 1, sbUserComment.getTargetId());
					break;
				case ALBUM:
					albumMapper.selfPlusMinusByPrimaryKey("reply_num", "-", 1, sbUserComment.getTargetId());
					break;
				default:
					break;
			}
		}
	}

	public boolean isExist(Integer commentId){
//
		return false;
	}
}
