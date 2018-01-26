ALTER TABLE `sb_user_sign_up`
	ADD COLUMN `mobile` VARCHAR(11) NULL COMMENT '参与活动手机号码' AFTER `activity_id`,
	ADD COLUMN `num` INT NULL COMMENT '参与活动序号' AFTER `mobile`,
	ADD COLUMN `bonus` INT NULL COMMENT '奖品' AFTER `num`;
ALTER TABLE `sb_user_sign_up`
	ADD COLUMN `nickname` VARCHAR(50) NULL DEFAULT NULL COMMENT '参与者昵称' AFTER `mobile`;
ALTER TABLE `sb_user_sign_up`
	ADD COLUMN `did` VARCHAR(50) NULL DEFAULT NULL COMMENT '参与设备id' AFTER `nickname`,
	ADD INDEX `did_activity_id` (`did`, `activity_id`);
alter table dsm_multipartupload add column `video_id` INT(11) NULL DEFAULT NULL COMMENT '合并完成视频ID' after upload_id;