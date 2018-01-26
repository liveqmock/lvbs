<%--
  Created by IntelliJ IDEA.
  User: feifan.gou
  Date: 2017/8/28
  Time: 11:15
  To change this template use File | Settings | File Templates.
  话题审核列表
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/WEB-INF/pages/inc/meta.inc" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <%@ include file="/WEB-INF/pages/inc/head.inc" %>

    <%@ include file="/WEB-INF/pages/authority/button/buttonAuthStyle.jsp" %>
    <%@ include file="/WEB-INF/pages/common/page/zoom.jsp" %>
    <%@ include file="/WEB-INF/pages/common/js/statist/video/coll_index.js.jsp"%>
<body style="padding:5px; overflow:hidden;">
<span style="float:left;margin-left:10px;">开始时间:
    <tip></tip><input type="text" name="createTime" id="createTime" value="${startT}"  onclick="WdatePicker({
                    dateFmt: 'yyyy-MM-dd',
                    skin:'blueFresh'
                })">
    </span>
<span style="float:left;margin-left:10px;">结束时间:
    <tip></tip><input type="text" name="endTime" id="endTime" value="${endT}" onclick="WdatePicker({
                    dateFmt: 'yyyy-MM-dd',
                    skin:'blueFresh'
                })">
    </span>
<span>
    视频ID：<input name="videoId" id="videoId"/>
</span>
<span>
    视频名称：<input name="name" id="name"/>
</span>
<button class="operation_btn" onclick="renderTable()">查询</button></span>
<div id="maingrid"></div>
<div class="l-loading" id="page_loading"></div>
</body>
</html>
