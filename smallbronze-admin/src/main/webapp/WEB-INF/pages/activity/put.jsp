<%--
  Created by IntelliJ IDEA.
  User: feifan.gou
  Date: 2017/8/28
  Time: 10:34
  To change this template use File | Settings | File Templates.
    向活动中放入视频
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/WEB-INF/pages/inc/meta.inc" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <%@ include file="/WEB-INF/pages/inc/head.inc" %>
    <%@ include file="/WEB-INF/pages/common/style/index.css.jsp" %>
    <%@ include file="/WEB-INF/pages/common/style/activity/put.css.jsp" %>
    <link rel="stylesheet" charset="UTF-8" href="/plugin/paging/css/animate.css"/>
    <link rel="stylesheet" charset="UTF-8" href="/plugin/paging/css/style.css"/>
</head>
<body>
<%@ include file="/WEB-INF/pages/common/page/loading.jsp" %>
<%@ include file="/WEB-INF/pages/common/page/prompt.jsp" %>
<%@ include file="/WEB-INF/pages/common/page/confirm.jsp" %>
<input type="hidden" id="activity_id" value="${activity_id}">
<div id="put_topic_layout">
    <div position="center">
        <div id="resultBox"></div>

        <div id="paging">
            <div class="first">首页</div>
            <div class="prev">上一页</div>
            <ul class="list"></ul>
            <div class="go">
                <input type="text" placeholder="Goto">
                <button>GO</button>
            </div>
            <div class="next">下一页</div>
            <div class="last">尾页</div>
        </div>
    </div>
    <div position="right" title="添加视频">
        <div class="search">
            视频ID: <input id="topic_id" type="number" placeholder="回车也可以查询哦">
            <button id="search_topic" class="blue_btn">查询</button>
        </div>
        <div id="add_data"></div>
        <button class="blue_btn" id="add_topic">添加视频</button>
        <button class="blue_btn" id="return_list" onclick="history.back()">返回活动列表</button>

    </div>
    <%@ include file="/WEB-INF/pages/common/js/activity/put.js.jsp" %>
    <script type="text/javascript" charset="UTF-8" src="/plugin/paging/js/paging.js"></script>
    <script type="text/javascript" charset="UTF-8" src="/plugin/paging/js/data.js"></script>
</body>
</html>

