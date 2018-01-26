package com.daishumovie.api.controller.v1;

import com.daishumovie.api.service.ChannelService;
import com.daishumovie.api.service.TopicListService;
import com.daishumovie.base.dto.BaseListDto;
import com.daishumovie.base.dto.PageInDto;
import com.daishumovie.base.dto.category.ChannelDto;
import com.daishumovie.base.dto.topic.TopicDto;
import com.daishumovie.base.enums.front.PageSource;
import com.daishumovie.base.model.Header;
import com.daishumovie.base.model.LocalData;
import com.daishumovie.base.model.Response;
import com.daishumovie.base.model.Header.OsEnum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Cheng Yufei
 * @create 2017-09-09 14:41
 **/
@RestController
@RequestMapping("/v1")
public class ChannelController {

    @Autowired
    private ChannelService channelService;

    @Autowired
    private TopicListService topicListService;

    /**
     * 获取分类
     *
     * @return
     */
    @RequestMapping(value = "/getClassificationList", method = RequestMethod.GET)
    public Response<List<ChannelDto>> getClassificationList(@RequestHeader(value = "appId", required = false) Integer appId) {

        return channelService.getClassificationList(appId);
    }

    /**
     * 获取频道
     *
     * @param id        热门时传0
     * @param pageIndex
     * @return
     */
    @RequestMapping(value = "/getChannelList/{id:\\d+}/{pageIndex}", method = RequestMethod.GET)
    public Response<BaseListDto<ChannelDto>> getChannelList(@PathVariable Integer id, @PathVariable Integer pageIndex, @RequestHeader(value = "appId", required = false) Integer appId) {

        PageInDto pageInDto = new PageInDto(pageIndex, 10);
        Header header = LocalData.HEADER.get();
        Integer uid = header.getUid();
        return channelService.getChannelList(id, uid, pageInDto, appId);
    }

    /**
     * 频道详情 顶部
     *
     * @param id
     * @param appId
     * @return
     */
    @RequestMapping(value = "/channelDetailTop/{id:\\d+}", method = RequestMethod.GET)
    public Response<ChannelDto> channelDetailTop(@PathVariable Integer id, @RequestHeader(value = "appId", required = false) Integer appId) {

        Header header = LocalData.HEADER.get();
        Integer uid = header.getUid();
        return channelService.getChannelDetailTop(id, uid, appId);
    }

    /**
     * 频道详情 下部
     *
     * @param id
     * @param appId
     * @return
     */
    @RequestMapping(value = "/channelDetailBottom/{id:\\d+}/{type}/{pageIndex}", method = RequestMethod.GET)
    public Response channelDetailBottom(@PathVariable Integer id, @PathVariable Integer pageIndex, @PathVariable("type") Integer type,
                                                @RequestHeader(value = "appId", required = false) Integer appId) {

        Header header = LocalData.HEADER.get();
        Integer uid = header.getUid();
        PageInDto pageInDto = new PageInDto(pageIndex, 10);

        List<TopicDto> topicDtos = topicListService.getTopicByPage(pageInDto, appId, id, uid, type, LocalData.HEADER.get().getOsEnum() == OsEnum.IOS, PageSource.TOPIC_LIST);
        BaseListDto<TopicDto> baseListDto = new BaseListDto<TopicDto>();
        baseListDto.setList(topicDtos);
        baseListDto.setPageIndex(pageInDto.getPageIndex());
        baseListDto.setHasNext(topicDtos.size() < pageInDto.getPageSize() ? 0 : 1);
        return new Response(baseListDto);

    }
    
	/**
	 * 获取所有末级频道
	 *
	 * source=1 返回推荐； 其他 不返回推荐
	 * @return
	 */
	@RequestMapping(value = "/finalStageChannels/{source}", method = RequestMethod.GET)
	public Response<BaseListDto<ChannelDto>> finalStageChannels(@RequestHeader(value = "appId") Integer appId,
			@PathVariable Integer source) {
		return channelService.getChannelList(appId, source);
	}
}
