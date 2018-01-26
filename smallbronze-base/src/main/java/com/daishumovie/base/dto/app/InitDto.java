package com.daishumovie.base.dto.app;

import java.io.Serializable;

import com.daishumovie.base.dto.user.DeviceSettings;
import com.daishumovie.base.enums.db.CommentReportProblem;
import com.daishumovie.base.enums.db.ReportProblem;
import com.daishumovie.base.enums.db.UserReportProblem;

import lombok.Data;

@Data
public class InitDto implements Serializable {

	private static final long serialVersionUID = -7194473899152290379L;

	private Integer autoplayUsingWifi;
	/**
	 * API接口前缀
	 */
	private String apiPrefix;
	/**
	 * 视频文件上传最大字节数
	 */
	private Integer maxVideoSize;

	private Integer checkStatus;

	private ReportProblem[] topicProblemList;

	private CommentReportProblem[] commentProblemList;

	private UserReportProblem[] userProblemList;

	private DeviceSettings deviceSettings;
	
	private String downloadPage;
}
