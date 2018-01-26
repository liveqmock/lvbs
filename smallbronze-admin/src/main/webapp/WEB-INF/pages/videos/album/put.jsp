<%--
  Created by IntelliJ IDEA.
  User: feifan.gou
  Date: 2017/8/28
  Time: 10:34
  To change this template use File | Settings | File Templates.
    向合辑中放入视频
--%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/WEB-INF/pages/inc/meta.inc" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <%@ include file="/WEB-INF/pages/inc/head.inc" %>
    <%@ include file="/WEB-INF/pages/common/style/index.css.jsp" %>
    <%@ include file="/WEB-INF/pages/common/style/video/album/put.css.jsp" %>
    <script type="text/javascript" charset="UTF-8" src="/js/jquery-plugin/jquery.dragsort-0.5.2.min.js"></script>
    <%@ include file="/WEB-INF/pages/common/js/videos/album/put.js.jsp"%>
</head>
<body>
<%@ include file="/WEB-INF/pages/common/page/loading.jsp"%>
<%@ include file="/WEB-INF/pages/common/page/prompt.jsp"%>
<%@ include file="/WEB-INF/pages/common/page/confirm.jsp"%>
<div id="album_layout">
    <div position="center">
        <input id="topic_id" type="hidden" value="${topic_ids}" name="topicIds"/>
        <span>
                发布时间：<input class="create_time" name="createTime" id="create_time" onclick="WdatePicker({
                    dateFmt: 'yyyy-MM-dd',
                    skin:'blueFresh',
                    onpicked:function() {
                          loadingData();
                    }
                });"/>
            </span>
        <span>
                发布人：<input class="user_name" name="username" id="user_name"/>
            </span>
        <span>
            视频ID：<input placeholder="多个请用空格分开" class="query_video_id" name="ids" id="id"/>
        </span>
        <span>
            频道：
            <select name="channelId" id="channel">
                <option value="">全部</option>
                <c:forEach items="${channel_list}" var="channel">
                    <option value="${channel.id}">${channel.name}</option>
                </c:forEach>
            </select>
        </span>
        <span><button class="operation_btn" onclick="loadingData()">查询</button></span>
        <span><button class="operation_btn" onclick="history.back()">返回</button></span>
        <div class="l-loading" id="page_loading"></div>
        <div class="l-clear"></div>
        <div id="album_list"></div>
    </div>
    <div position="right" title="已选择的视频">
        <input type="hidden" value="${album_id}" id="album_id">
        <span>
            <button id="submit_album" class="operation_btn">提交</button>
        </span>
        <div class="data_div">

        </div>
    </div>
</div>
</body>
</html>

