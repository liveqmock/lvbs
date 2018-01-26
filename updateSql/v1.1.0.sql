CREATE TABLE `dsm_multipartupload` (
	`id` INT(11) NOT NULL AUTO_INCREMENT,
	`app_id` INT(10) NOT NULL DEFAULT '2000',
	`upload_id` VARCHAR(40) NOT NULL COMMENT 'MultipartUpload事件的ID' COLLATE 'utf8mb4_unicode_ci',
	`uid` INT(11) NOT NULL COMMENT '用户id',
	`name` VARCHAR(255) NOT NULL COMMENT '名称' COLLATE 'utf8mb4_unicode_ci',
	`location` VARCHAR(255) NULL DEFAULT NULL COMMENT '本地路径' COLLATE 'utf8mb4_unicode_ci',
	`url` VARCHAR(255) NULL DEFAULT NULL COMMENT '阿里云路径' COLLATE 'utf8mb4_unicode_ci',
	`etag` VARCHAR(40) NULL DEFAULT NULL COMMENT '完成上传创建Object生成' COLLATE 'utf8mb4_unicode_ci',
	`part_num` INT(11) NOT NULL COMMENT '分块数',
	`status` SMALLINT(4) NOT NULL DEFAULT '0' COMMENT '-1 删除、中止  0 未传完 1 已传完',
	`create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	`modify_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY (`id`)
)
COLLATE='utf8mb4_unicode_ci'
ENGINE=InnoDB;
CREATE TABLE `dsm_multipartupload_part` (
	`id` INT(11) NOT NULL AUTO_INCREMENT,
	`app_id` INT(10) NOT NULL DEFAULT '2000',
	`upload_id` VARCHAR(40) NOT NULL COMMENT 'MultipartUpload事件的ID' COLLATE 'utf8mb4_unicode_ci',
	`uid` INT(11) NOT NULL COMMENT '用户id',
	`part_number` INT(11) NOT NULL,
	`etag` VARCHAR(40) NOT NULL COMMENT 'part数据的MD5值' COLLATE 'utf8mb4_unicode_ci',
	`location` VARCHAR(255) NOT NULL COMMENT 'part上传URL' COLLATE 'utf8mb4_unicode_ci',
	`status` SMALLINT(4) NOT NULL DEFAULT '0' COMMENT '-1 删除、中止  0 未传完 1 已传完',
	`create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'part 上传时间',
	`modify_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY (`id`)
)
COLLATE='utf8mb4_unicode_ci'
ENGINE=InnoDB;
CREATE TABLE `dsm_config` (
	`id` INT(11) NOT NULL AUTO_INCREMENT,
	`name` VARCHAR(64) NOT NULL COMMENT '配置名称' COLLATE 'utf8mb4_unicode_ci',
	`code` VARCHAR(32) NOT NULL COMMENT '配置key' COLLATE 'utf8mb4_unicode_ci',
	`type` ENUM('string','number','decimal','date','time','date_period','time_period','date_time_period','json') NOT NULL DEFAULT 'string' COMMENT '配置类型[string：字符串,number：数字,decimal：小数,date：日期,time：时间,date_period：日期段,time_period：时间段,date_time_period:日期时间段,json：json格式的数据]' COLLATE 'utf8mb4_unicode_ci',
	`value` VARCHAR(512) NOT NULL COMMENT '配置值，多个值用"|"分隔' COLLATE 'utf8mb4_unicode_ci',
	`remark` VARCHAR(512) NULL DEFAULT '' COMMENT '备注' COLLATE 'utf8mb4_unicode_ci',
	`creator` INT(10) NOT NULL,
	`modifier` INT(11) NULL DEFAULT '0' COMMENT '最后修改人',
	`create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	`modify_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	PRIMARY KEY (`id`),
	UNIQUE INDEX `unique_code` (`code`)
)
COMMENT='常量配置表'
COLLATE='utf8mb4_unicode_ci'
ENGINE=InnoDB;
INSERT INTO `dsm_config` (`id`, `name`, `code`, `type`, `value`, `remark`, `creator`, `modifier`, `create_time`, `modify_time`) VALUES (1, 'threshold', 'threshold', 'number', '1000', '活动详情页话题排序显示阀值', 1, 1, '2017-10-19 17:03:55', '2017-10-26 18:54:42');

CREATE TABLE `sb_activity` (
	`id` INT(10) NOT NULL AUTO_INCREMENT,
	`title` VARCHAR(50) NOT NULL COMMENT '标题' COLLATE 'utf8mb4_unicode_ci',
	`topic` VARCHAR(32) NOT NULL COMMENT '活动话题#xxx#' COLLATE 'utf8mb4_unicode_ci',
	`description` VARCHAR(128) NOT NULL DEFAULT '' COMMENT '活动描述' COLLATE 'utf8mb4_unicode_ci',
	`cover` VARCHAR(255) NOT NULL COMMENT '活动封面' COLLATE 'utf8mb4_unicode_ci',
	`thumb_cover` VARCHAR(255) NOT NULL COMMENT '活动缩略图' COLLATE 'utf8mb4_unicode_ci',
	`type` INT(11) NOT NULL COMMENT '活动类型 1 投稿',
	`status` INT(10) NOT NULL DEFAULT '0' COMMENT '活动状态 0：未开始 1：预热中 2：进行中 3：已结束',
	`whether_online` INT(1) NOT NULL DEFAULT '1' COMMENT '是否上线 0: 否 1: 是',
	`instruction` VARCHAR(1000) NOT NULL COMMENT '活动规则 富文本类型' COLLATE 'utf8mb4_unicode_ci',
	`pre_time` DATETIME NULL DEFAULT NULL COMMENT '活动预热时间',
	`start_time` DATETIME NOT NULL COMMENT '活动开始时间',
	`end_time` DATETIME NOT NULL COMMENT '活动结束时间',
	`reply_num` INT(10) NOT NULL DEFAULT '0' COMMENT '回复数',
	`app_id` INT(10) NOT NULL DEFAULT '2000',
	`operator_id` INT(10) NOT NULL COMMENT '创建活动用户id',
	`modifier` INT(11) NOT NULL COMMENT '修改人',
	`create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	`modify_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
	PRIMARY KEY (`id`)
)
COMMENT='活动'
COLLATE='utf8mb4_unicode_ci'
ENGINE=InnoDB;
CREATE TABLE `sb_topic_album` (
	`id` INT(11) NOT NULL AUTO_INCREMENT,
	`app_id` INT(11) NOT NULL DEFAULT '2000',
	`title` VARCHAR(50) NOT NULL COMMENT '标题',
	`subtitle` VARCHAR(255) NOT NULL COMMENT '备注',
	`status` INT(11) NOT NULL DEFAULT '1' COMMENT '在线状态 0 待发布 1 已发布 2 已下线',
	`cover` VARCHAR(255) NOT NULL COMMENT '专辑封面',
	`topic_ids` VARCHAR(255) NULL DEFAULT NULL COMMENT ',分割',
	`reply_num` INT(11) NOT NULL DEFAULT '0' COMMENT '回复数',
	`publish_time` TIMESTAMP NULL DEFAULT NULL COMMENT '发布时间，发布后有值，未发布时为空',
	`operator_id` INT(11) NOT NULL DEFAULT '0' COMMENT '运营人员id',
	`modifier_id` INT(11) NULL DEFAULT NULL COMMENT '更新人ID',
	`create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	`modify_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
	PRIMARY KEY (`id`)
)
COMMENT='话题专辑'
COLLATE='utf8mb4_general_ci'
ENGINE=InnoDB;
CREATE TABLE `sb_user_watch_history` (
	`id` INT(11) NOT NULL AUTO_INCREMENT,
	`app_id` INT(11) NOT NULL DEFAULT '2000',
	`uid` INT(11) NULL DEFAULT NULL,
	`did` VARCHAR(64) NOT NULL,
	`topic_id` INT(11) NOT NULL COMMENT '话题id',
	`video_id` INT(11) NOT NULL,
	`watch_times` INT(11) NOT NULL DEFAULT '1',
	`status` INT(11) NOT NULL DEFAULT '1',
	`create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	`modify_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	PRIMARY KEY (`id`)
)
COMMENT='用户播放视频历史'
COLLATE='utf8_general_ci'
ENGINE=InnoDB;
CREATE TABLE `sb_tj_channel_day_report` (
	`id` INT(10) NOT NULL AUTO_INCREMENT,
	`channel_id` INT(10) NOT NULL COMMENT '渠道id',
	`play_pv` INT(10) NOT NULL DEFAULT '0' COMMENT '播放次数',
	`play_uv` INT(10) NOT NULL DEFAULT '0' COMMENT '播放用户数',
	`five_second_pv` FLOAT(10,2) NOT NULL DEFAULT '0.00' COMMENT '小于5秒的播放总量',
	`five_ninety_pv` FLOAT(10,2) NOT NULL DEFAULT '0.00' COMMENT '大于5秒-小于90%的播放总量',
	`greater_ninety_pv` FLOAT(10,2) NOT NULL DEFAULT '0.00' COMMENT '大于90%的播放总量',
	`play_t_time` INT(10) NOT NULL DEFAULT '0' COMMENT '总播放时长',
	`comments` INT(10) NOT NULL DEFAULT '0' COMMENT '评论数',
	`praise_num` INT(10) NOT NULL DEFAULT '0' COMMENT '点赞数',
	`share_num` INT(10) NOT NULL DEFAULT '0' COMMENT '分享率',
	`barrage_num` INT(10) NOT NULL DEFAULT '0' COMMENT '弹幕数',
	`t` DATETIME NOT NULL COMMENT '日期',
	`create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
	PRIMARY KEY (`id`),
	UNIQUE INDEX `channel_id_t` (`channel_id`, `t`)
)
COMMENT='渠道维度统计'
COLLATE='utf8mb4_unicode_ci'
ENGINE=InnoDB;
CREATE TABLE `sb_tj_channel_hour_report` (
	`id` INT(10) NOT NULL AUTO_INCREMENT,
	`channel_id` INT(10) NOT NULL COMMENT '渠道id',
	`play_pv` INT(10) NOT NULL DEFAULT '0' COMMENT '播放次数',
	`play_uv` INT(10) NOT NULL DEFAULT '0' COMMENT '播放用户数',
	`five_second_pv` FLOAT(10,2) NOT NULL DEFAULT '0.00' COMMENT '小于5秒的播放总量',
	`five_ninety_pv` FLOAT(10,2) NOT NULL DEFAULT '0.00' COMMENT '大于5秒-小于90%的播放总量',
	`greater_ninety_pv` FLOAT(10,2) NOT NULL DEFAULT '0.00' COMMENT '大于90%的播放总量',
	`play_t_time` INT(10) NOT NULL DEFAULT '0' COMMENT '总播放时长',
	`comments` INT(10) NOT NULL DEFAULT '0' COMMENT '评论数',
	`praise_num` INT(10) NOT NULL DEFAULT '0' COMMENT '点赞数',
	`share_num` INT(10) NOT NULL DEFAULT '0' COMMENT '分享率',
	`barrage_num` INT(10) NOT NULL DEFAULT '0' COMMENT '弹幕数',
	`t` DATETIME NOT NULL COMMENT '日期',
	`create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
	PRIMARY KEY (`id`),
	UNIQUE INDEX `channel_id_t` (`channel_id`, `t`)
)
COMMENT='渠道维度统计'
COLLATE='utf8mb4_unicode_ci'
ENGINE=InnoDB;
CREATE TABLE `sb_tj_video_day_report` (
	`id` INT(10) NOT NULL AUTO_INCREMENT,
	`video_id` INT(10) NOT NULL COMMENT '视频id',
	`channel_id` INT(10) NOT NULL COMMENT '渠道id',
	`play_pv` INT(10) NOT NULL DEFAULT '0' COMMENT '播放次数',
	`play_uv` INT(10) NOT NULL DEFAULT '0' COMMENT '播放用户数',
	`five_second_pv` FLOAT(10,2) NOT NULL DEFAULT '0.00' COMMENT '小于5秒的播放总量',
	`five_ninety_pv` FLOAT(10,2) NOT NULL DEFAULT '0.00' COMMENT '大于5秒-小于90%的播放总量',
	`greater_ninety_pv` FLOAT(10,2) NOT NULL DEFAULT '0.00' COMMENT '大于90%的播放总量',
	`play_t_time` INT(10) NOT NULL DEFAULT '0' COMMENT '总播放时长',
	`comments` INT(10) NOT NULL DEFAULT '0' COMMENT '评论数',
	`praise_num` INT(10) NOT NULL DEFAULT '0' COMMENT '点赞数',
	`share_num` INT(10) NOT NULL DEFAULT '0' COMMENT '分享数',
	`barrage_num` INT(10) NOT NULL DEFAULT '0' COMMENT '弹幕数',
	`t` DATETIME NOT NULL COMMENT '日期',
	`create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
	PRIMARY KEY (`id`),
	UNIQUE INDEX `video_id_channel_id_t` (`video_id`, `channel_id`, `t`)
)
COMMENT='视频维度统计'
COLLATE='utf8mb4_unicode_ci'
ENGINE=InnoDB;
CREATE TABLE `sb_tj_video_hour_report` (
	`id` INT(10) NOT NULL AUTO_INCREMENT,
	`video_id` INT(10) NOT NULL COMMENT '视频id',
	`channel_id` INT(10) NOT NULL COMMENT '渠道id',
	`play_pv` INT(10) NOT NULL DEFAULT '0' COMMENT '播放次数',
	`play_uv` INT(10) NOT NULL DEFAULT '0' COMMENT '播放用户数',
	`five_second_pv` FLOAT(10,2) NOT NULL DEFAULT '0.00' COMMENT '小于5秒的播放总量',
	`five_ninety_pv` FLOAT(10,2) NOT NULL DEFAULT '0.00' COMMENT '大于5秒-小于90%的播放总量',
	`greater_ninety_pv` FLOAT(10,2) NOT NULL DEFAULT '0.00' COMMENT '大于90%的播放总量',
	`play_t_time` INT(10) NOT NULL DEFAULT '0' COMMENT '总播放时长',
	`comments` INT(10) NOT NULL DEFAULT '0' COMMENT '评论数',
	`praise_num` INT(10) NOT NULL DEFAULT '0' COMMENT '点赞数',
	`share_num` INT(10) NOT NULL DEFAULT '0' COMMENT '分享数',
	`barrage_num` INT(10) NOT NULL DEFAULT '0' COMMENT '弹幕数',
	`t` DATETIME NOT NULL COMMENT '日期',
	`create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
	PRIMARY KEY (`id`),
	UNIQUE INDEX `video_id_channel_id_t` (`video_id`, `channel_id`, `t`)
)
COMMENT='视频维度统计'
COLLATE='utf8mb4_unicode_ci'
ENGINE=InnoDB;
CREATE TABLE `dsm_push_token` (
	`did` VARCHAR(50) NOT NULL COLLATE 'utf8mb4_unicode_ci',
	`token` VARCHAR(128) NOT NULL COLLATE 'utf8mb4_unicode_ci',
	`app_id` INT(10) NOT NULL,
	`registration_id` VARCHAR(50) NULL DEFAULT NULL COMMENT '极光推送的设备唯一性标识' COLLATE 'utf8mb4_unicode_ci',
	`os` INT(1) NOT NULL COMMENT '1:ios 2:Android',
	`uid` INT(11) NULL DEFAULT NULL,
	`create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	`modify_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	PRIMARY KEY (`did`)
)
COMMENT='推送TOKEN记录'
COLLATE='utf8mb4_unicode_ci'
ENGINE=InnoDB;
CREATE TABLE `sb_push_task` (
	`id` INT(10) NOT NULL AUTO_INCREMENT,
	`alert` VARCHAR(64) NOT NULL COMMENT '推送文案' COLLATE 'utf8mb4_unicode_ci',
	`platform` INT(2) NOT NULL COMMENT '推送平台 0:ios 1:android 2:all',
	`way` INT(10) NOT NULL COMMENT '推送方式 0:立即推送 1:定时推送',
	`push_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '推送时间（推送为定时推送时候有值）',
	`target_type` INT(10) NOT NULL DEFAULT '0' COMMENT '跳转类型 1 话题/视频 2 用户 3 APP首页 4 WebUrl 5 活动 6 合辑',
	`target_id` VARCHAR(256) NOT NULL COMMENT '视频id' COLLATE 'utf8mb4_unicode_ci',
	`status` INT(10) NOT NULL DEFAULT '0' COMMENT '推送状态 0:待推送 1:推送成功 2:推动失败 3:已取消',
	`input_json` VARCHAR(512) NULL DEFAULT '' COMMENT '请求极光参数json' COLLATE 'utf8mb4_unicode_ci',
	`output_json` VARCHAR(512) NULL DEFAULT '' COMMENT '极光返回结果json' COLLATE 'utf8mb4_unicode_ci',
	`report_output_json` VARCHAR(1024) NULL DEFAULT '' COMMENT '请求极光报告返回结果的json' COLLATE 'utf8mb4_unicode_ci',
	`msg_id` VARCHAR(32) NULL DEFAULT NULL COMMENT '极光推送返回的msg_id，请求report时候用。' COLLATE 'utf8mb4_unicode_ci',
	`ios_received_count` INT(11) NOT NULL DEFAULT '0' COMMENT 'IOS成功接收量',
	`ios_click_times` INT(11) NULL DEFAULT '0' COMMENT 'IOS点击量',
	`android_received_count` INT(11) NULL DEFAULT '0' COMMENT '安卓成功接收量',
	`android_click_times` INT(11) NULL DEFAULT '0' COMMENT '安卓点击量',
	`pusher_id` INT(10) NOT NULL COMMENT '创建推送任务用户id',
	`modifier` INT(10) NULL DEFAULT NULL COMMENT '更新者',
	`create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	`modify_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
	PRIMARY KEY (`id`)
)
COMMENT='推送历史表'
COLLATE='utf8mb4_unicode_ci'
ENGINE=InnoDB;
CREATE TABLE `sb_tj_base_data` (
	`id` INT(10) NOT NULL AUTO_INCREMENT,
	`t` DATETIME NOT NULL COMMENT '时间（天）',
	`ip` VARCHAR(15) NOT NULL COMMENT '请求来源ip' COLLATE 'utf8mb4_unicode_ci',
	`os` INT(10) NOT NULL COMMENT '手机操作系统 1 IOS 2Android',
	`os_version` VARCHAR(10) NOT NULL COMMENT '操作系统版本号 对应 操作系统类型' COLLATE 'utf8mb4_unicode_ci',
	`did` VARCHAR(40) NOT NULL COMMENT '设备唯一号（设备id）' COLLATE 'utf8mb4_unicode_ci',
	`device` VARCHAR(30) NOT NULL COMMENT '手机种类' COLLATE 'utf8mb4_unicode_ci',
	`app_version` VARCHAR(10) NOT NULL COMMENT '应用版本号' COLLATE 'utf8mb4_unicode_ci',
	`screen` VARCHAR(10) NOT NULL COMMENT '手机屏幕大小' COLLATE 'utf8mb4_unicode_ci',
	`network_type` INT(2) NOT NULL COMMENT '联网方式1 wifi 2 2G 3 2G 4 4G',
	`c_type` INT(10) NOT NULL COMMENT '日志类型 1 喜欢视频 2 取消喜欢视频 3 视频发送弹幕 4 对视频发送评论 5 删除视频评论 6 视频播放 7 分享 8 推送视频',
	`target_type` INT(10) NULL DEFAULT NULL COMMENT '跳转类型 根据c_type=8 1视频详情 2 用户主页3app首页4web_view 5活动详情 6 专辑',
	`target` VARCHAR(100) NULL DEFAULT NULL COMMENT '根据跳转类型对象决定具体存储什么内容' COLLATE 'utf8mb4_unicode_ci',
	`c_channel_id` INT(10) NULL DEFAULT NULL COMMENT '当前渠道id',
	`t_channel_id` INT(10) NULL DEFAULT NULL COMMENT '目标渠道id',
	`video_id` INT(10) NOT NULL COMMENT '视频id 对应业务表中video表id',
	`activity_id` INT(10) NULL DEFAULT NULL COMMENT '活动或者专辑',
	`user_Id` INT(10) NULL DEFAULT NULL COMMENT '用户id',
	`play_source` INT(10) NOT NULL COMMENT '播放来源 1首页 2 关注页 3 我的首页 4 他人主页 5 喜欢 6 视频详情页',
	`current_p_time` INT(10) NULL DEFAULT '0' COMMENT '当前播放时长',
	`on_off` INT(10) NOT NULL DEFAULT '0' COMMENT '开关 0 关 1开',
	`create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY (`id`)
)
COMMENT='从日志清洗后数据'
COLLATE='utf8mb4_unicode_ci'
ENGINE=InnoDB;
CREATE TABLE `sb_user_sign_up` (
	`id` INT(11) NOT NULL AUTO_INCREMENT,
	`uid` INT(11) NOT NULL,
	`activity_id` INT(11) NOT NULL,
	`create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY (`id`),
	UNIQUE INDEX `uid_activity_id` (`uid`, `activity_id`)
)
COMMENT='活动报名'
COLLATE='utf8mb4_unicode_ci'
ENGINE=InnoDB;
DROP TABLE `sb_ad`;
CREATE TABLE `sb_ad` (
	`id` INT(10) NOT NULL AUTO_INCREMENT,
	`orders` INT(10) NOT NULL DEFAULT '0' COMMENT '如果是轮播广告 orders 不为空',
	`name` VARCHAR(50) NOT NULL COMMENT '广告名称',
	`ad_cover` VARCHAR(255) NULL DEFAULT '' COMMENT '广告封面图',
	`target_type` INT(11) NOT NULL COMMENT '跳转类型 1 话题/视频 2 用户 3 APP首页 4 WebUrl 5 活动 6 合辑',
	`target` VARCHAR(255) NOT NULL COMMENT '广告跳转地址',
	`start_time` DATETIME NOT NULL COMMENT '广告开始时间',
	`end_time` DATETIME NOT NULL COMMENT '广告结束时间',
	`creator_id` INT(11) NULL DEFAULT NULL COMMENT '创建者id',
	`creator_name` VARCHAR(50) NULL DEFAULT NULL COMMENT '创建者名称',
	`status` INT(11) NOT NULL DEFAULT '0' COMMENT '状态 0删除 1 正常',
	`ad_type` INT(11) NOT NULL DEFAULT '0' COMMENT '广告类型 0 启动广告 1 轮播广告',
	`duration` INT(11) NULL DEFAULT '0' COMMENT '如果是启动广告,则为启动广告显示时长，轮播广告时为轮播广告间隔切换时间',
	`create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	`updateTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
	PRIMARY KEY (`id`)
)
COMMENT='广告位'
COLLATE='utf8_general_ci'
ENGINE=InnoDB;
ALTER TABLE `sb_topic` 
   ADD COLUMN `orders` INT(11) NOT NULL DEFAULT '0' COMMENT '排序' AFTER `activity_id`;
ALTER TABLE `dsm_user` 
   ADD COLUMN `like_album_num` INT(11) NOT NULL DEFAULT '0' COMMENT '合辑喜欢数' AFTER `like_num`;
ALTER TABLE `sb_user_comment`
	ALTER `topic_id` DROP DEFAULT;
ALTER TABLE `sb_user_comment`
	CHANGE COLUMN `topic_id` `target_id` INT(10) NOT NULL COMMENT '话题id' AFTER `app_id`,
	ADD COLUMN `target_type` INT(10) NOT NULL DEFAULT '1' COMMENT '1 话题 2频道 3用户 4回复 5 合辑 6 活动' AFTER `target_id`;