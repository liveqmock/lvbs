package com.daishumovie.api.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.daishumovie.base.dto.app.CheckNewDto;
import com.daishumovie.base.dto.app.InitDto;
import com.daishumovie.base.dto.user.DeviceSettings;
import com.daishumovie.base.enums.db.CommentReportProblem;
import com.daishumovie.base.enums.db.ReportProblem;
import com.daishumovie.base.enums.db.UserReportProblem;
import com.daishumovie.base.model.Header;
import com.daishumovie.base.model.LocalData;
import com.daishumovie.base.model.Response;
import com.daishumovie.dao.mapper.smallbronze.DsmAppVersionMapper;
import com.daishumovie.dao.mapper.smallbronze.DsmUserSettingsMapper;
import com.daishumovie.dao.model.DsmAppVersion;
import com.daishumovie.dao.model.DsmAppVersionExample;
import com.daishumovie.dao.model.DsmUserSettings;
import com.daishumovie.dao.model.DsmUserSettingsExample;
import com.daishumovie.utils.VersionUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * APP相关的接口如初始化和升级等
 * 
 * @author wangjiyue
 *
 */
@RestController("AppController")
@RequestMapping("/app")
@Slf4j
public class AppController {
	@Value("${app.autoplayUsingWifi}")
	private Integer autoplayUsingWifi;
	@Value("${app.apiPrefix}")
	private String apiPrefix;
	@Value("${app.maxVideoSize}")
	private Integer maxVideoSize;
	@Value("${app.downloadPage}")
	private String downloadPage;

	@Autowired
	private DsmAppVersionMapper dsmAppVersionMapper;
	
	@Autowired
	private DsmUserSettingsMapper dsmUserSettingsMapper; 
	
	@Autowired
    private StringRedisTemplate redisTemplate;

	@RequestMapping("/init")
	public Response<InitDto> init() throws Exception {
		Response<InitDto> resp = new Response<>();
		InitDto result = new InitDto();
		result.setApiPrefix(apiPrefix);
		result.setAutoplayUsingWifi(autoplayUsingWifi);
		result.setMaxVideoSize(maxVideoSize);
		result.setTopicProblemList(ReportProblem.values());
		result.setCommentProblemList(CommentReportProblem.values());
		result.setUserProblemList(UserReportProblem.values());
		result.setDownloadPage(downloadPage);
		Response<CheckNewDto> response = checkNew();
		result.setCheckStatus(response.getResult().getStatus());
		try {
			DsmUserSettingsExample example = new DsmUserSettingsExample();
			example.setLimit(1);
			DsmUserSettingsExample.Criteria criteria = example.createCriteria();
			Header header = LocalData.HEADER.get();
			criteria.andAppIdEqualTo(Integer.valueOf(header.getAppId()));
			criteria.andDidEqualTo(header.getDid());
			List<DsmUserSettings> deviceSetting = dsmUserSettingsMapper.selectByExample(example);
			DeviceSettings settingDto = new DeviceSettings();
			if (deviceSetting.size() > 0) {
				DsmUserSettings dsmUserSettings = deviceSetting.get(0);
				settingDto.setAllowBulletScreen(dsmUserSettings.getAllowBulletScreen());
				settingDto.setAutoplay(dsmUserSettings.getAutoplay());
				settingDto.setReceiveNotification(dsmUserSettings.getReceiveNotification());
			}
			result.setDeviceSettings(settingDto);
		} catch (Exception e) {
			log.error("查询设备相关设置异常", e);
		}
		resp.setResult(result);
		return resp;
	}

	/**
	 * 检查更新
	 */
	@RequestMapping("/checkNew")
	public Response<CheckNewDto> checkNew() throws Exception {

	    Header header = LocalData.HEADER.get();
	    String version = header.getVersion();
        Header.OsEnum osEnum = header.getOsEnum();
        DsmAppVersionExample example = new DsmAppVersionExample();
        example.setOrderByClause("build desc");
        example.setLimit(1);
        DsmAppVersionExample.Criteria criteria = example.createCriteria();
        criteria.andPlatEqualTo(osEnum.getOs());

        List<DsmAppVersion> dsmAppVersions = dsmAppVersionMapper.selectByExample(example);

        if(dsmAppVersions.isEmpty()){
            log.error("数据库没有版本信息");
            return new Response<>(new CheckNewDto());
        }

        DsmAppVersion dsmAppVersion = dsmAppVersions.get(0);
        String versionNew = dsmAppVersion.getVersionNum();
        String minVersion = dsmAppVersion.getMinVersion();//废弃版本

        CheckNewDto dto = new CheckNewDto();
        BeanUtils.copyProperties(dsmAppVersion, dto);


        int diffMin = VersionUtil.compareVersion(version, minVersion);

        //小于等于废弃  必须更新
        if(diffMin <= 0){
            dto.setStatus(3);
            return new Response<>(dto);
        }

        int diffNew = VersionUtil.compareVersion(version, versionNew);

        //小于最新版本  提示更新
        if(diffNew < 0){
            dto.setStatus(2);
            return new Response<>(dto);
        }

        //最新版本 不需要更新
        dto.setStatus(1);
        return new Response<>(dto);
    }

}
