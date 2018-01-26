<%--
  Created by IntelliJ IDEA.
  User: feifan.gou
  Date: 2017/9/27
  Time: 17:16
  To change this template use File | Settings | File Templates.
  视频发布列表
--%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/WEB-INF/pages/inc/meta.inc" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <%@ include file="/WEB-INF/pages/inc/head.inc" %>
    <%@ include file="/WEB-INF/pages/common/style/index.css.jsp" %>
    <%@ include file="/WEB-INF/pages/authority/button/buttonAuthStyle.jsp" %>
    <%@ include file="/WEB-INF/pages/common/page/zoom.jsp" %>
    <%--<script type="text/javascript" charset="UTF-8" src="/plugin/group-select/js/bootstrap.min.js"></script>--%>
    <%--<script type="text/javascript" charset="UTF-8" src="/plugin/group-select/js/bootstrap-select.js"></script>--%>
    <link rel="stylesheet" charset="UTF-8" href="https://cdn.bootcss.com/bootstrap/2.3.2/css/bootstrap.min.css" />
    <link rel="stylesheet" charset="UTF-8" href="http://cdn.bootcss.com/font-awesome/4.6.3/css/font-awesome.min.css">
    <link rel="stylesheet" charset="UTF-8" type="text/css" href="/plugin/combo/css/htmleaf-demo.css">
    <link rel="stylesheet" charset="UTF-8" href="/plugin/combo/css/comboselect.css" type="text/css">
    <link rel="stylesheet" charset="UTF-8" href="/plugin/combo/css/demo.css" />
    <script type="text/javascript" src="/plugin/combo/js/comboselect.min.js" ></script>
    <script type="text/javascript" src="/plugin/combo/js/b.comboselect.js" ></script>

    <%@ include file="/WEB-INF/pages/common/js/videos/publish/index.js.jsp"%>
</head>
<body>
<%@ include file="/WEB-INF/pages/common/page/confirm.jsp"%>
<%@ include file="/WEB-INF/pages/common/page/loading.jsp"%>
<%@ include file="/WEB-INF/pages/common/page/prompt.jsp"%>
<%@ include file="/WEB-INF/pages/videos/publish/publish.jsp"%>
<span class="publish_time"> <%-- 时间框--%>
   发布时间： <input name="publishTime" id="publish_time" onclick="WdatePicker({
                    dateFmt: 'yyyy-MM-dd',
                    skin:'blueFresh',
                    onpicked:function() {
                          renderTable();
                    }
                });">
</span>
<span class="publish_status">
    发布状态：
        <select name="publishStatus"  id="publish_status" >
            <%--<option  value = "">全部</option>--%>
            <c:forEach items="${publish_status}" var="status">
                <c:if test="${status.value != 3}">
                    <option value="${status.value}">${status.name}</option>
                </c:if>
            </c:forEach>
        </select>
</span>
<span class="video_id">
    视频ID：<input name="videoId" id="id"/>
</span>
<span class="video_id">
    标题：<input name="title" id="video_title"/>
</span>
<span class="publish_time">
    渠道：
        <select name="channelId" id="channelId" >
            <option  value = "">全部</option>
            <c:forEach items="${channel_list}" var="channel">
                <option value="${channel.id}">${channel.name}</option>
            </c:forEach>
        </select>
</span>
<span class="publish_status">
    上传人：
        <select name="uploader" id="uploader" >
            <option  value = "">全部</option>
            <c:forEach items="${admin_list}" var="admin">
                <option value="${admin.id}">${admin.realName}</option>
            </c:forEach>
        </select>
</span>

<span><button class="operation_btn" onclick="renderTable()">查询</button></span>
<div class="l-loading" id="page_loading"></div>
<div class="l-clear"></div>
<div id="publish_list"></div>
</body>
</html>
