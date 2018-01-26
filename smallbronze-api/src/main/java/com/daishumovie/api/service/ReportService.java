package com.daishumovie.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.daishumovie.base.enums.db.ReportType;
import com.daishumovie.base.enums.db.RespStatusEnum;
import com.daishumovie.base.enums.db.YesNoEnum;
import com.daishumovie.base.model.Response;
import com.daishumovie.dao.mapper.smallbronze.SbUserCommentMapper;
import com.daishumovie.dao.mapper.smallbronze.SbUserReportMapper;
import com.daishumovie.dao.model.SbUserComment;
import com.daishumovie.dao.model.SbUserReport;
import com.daishumovie.dao.model.SbUserReportExample;
import com.daishumovie.utils.CollectionUtils;

/**
 * @author Cheng Yufei
 * @create 2017-09-19 13:51
 **/
@Service
public class ReportService {

    @Autowired
    private SbUserReportMapper sbUserReportMapper;

    @Autowired
    private SbUserCommentMapper sbUserCommentMapper;

    @Autowired
	private CommentService commentService;



    @Transactional
    public Response<String> save(Integer contentId, Integer type, Integer uid, Integer appId, Integer problemId) {
        try {
            SbUserReport report = findOne(contentId, type, uid, appId);
            if (null == report) {
                SbUserReport sbUserReport = new SbUserReport();
                sbUserReport.setContentId(contentId);
                sbUserReport.setType(type);
                sbUserReport.setProblem(problemId);
                sbUserReport.setUid(uid);
                if (null != appId) {
                    sbUserReport.setAppId(appId);
                }

                if (type.equals(ReportType.COMMENT.getCode())) {
                    SbUserComment sbUserComment = sbUserCommentMapper.selectByPrimaryKey(contentId);
                    Integer commentStatus = sbUserComment.getStatus();
                    if (YesNoEnum.TEMPORARY.getCode() == commentStatus) {
                        return new Response<>(RespStatusEnum.COMMENT_ALREADY_REPORT);
                    }

                    //修改评论状态
                    SbUserComment comment = new SbUserComment();
                    comment.setId(contentId);
                    comment.setStatus(YesNoEnum.TEMPORARY.getCode());
                    sbUserCommentMapper.updateByPrimaryKeySelective(comment);
                    //修改目标评论数 -1
                    commentService.plusMinusTargetRelyNum(sbUserComment, false);
                }
                sbUserReportMapper.insertSelective(sbUserReport);
            }
            return new Response<>();
        } catch (Exception e) {
            return new Response<>(RespStatusEnum.ERROR);
        }

    }

    public SbUserReport findOne(Integer contentId, Integer type, Integer uid, Integer appId) {
        SbUserReportExample example = new SbUserReportExample();
        example.setLimit(1);
        SbUserReportExample.Criteria criteria = example.createCriteria();
        criteria.andContentIdEqualTo(contentId);
        criteria.andTypeEqualTo(type);
        if (null != uid) {
            criteria.andUidEqualTo(uid);
        }
        if (null != appId) {
            criteria.andAppIdEqualTo(appId);
        }
        criteria.andStatusEqualTo(YesNoEnum.NO.getCode());
        List<SbUserReport> sbUserReports = sbUserReportMapper.selectByExample(example);
        return CollectionUtils.isNullOrEmpty(sbUserReports) ? null : sbUserReports.get(0);
    }
}
