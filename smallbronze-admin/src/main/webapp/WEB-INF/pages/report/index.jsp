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
    <%@ include file="/WEB-INF/pages/common/style/index.css.jsp" %>
    <%@ include file="/WEB-INF/pages/authority/button/buttonAuthStyle.jsp" %>
    <%@ include file="/WEB-INF/pages/common/page/zoom.jsp" %>
    <%@ include file="/WEB-INF/pages/common/js/report/index.js.jsp"%>
<body>

<span style="float:left;margin-left:10px;">时间:
    <tip></tip><input type="text" name="create_time" id="create_time" onclick="WdatePicker({
                    dateFmt: 'yyyy-MM-dd',
                    skin:'blueFresh'
                })">
    </span>
<span style="float:left;margin-left:10px;">状态：
        <select type="select" name="status" id="status">
            <c:forEach items="${report_status}" var="item">
                <option value="${item.code}">${item.name}</option>
            </c:forEach>
        </select>
    </span>
<span style="float:left;margin-left:10px;">类型：
        <select type="select" name="type" id="type">
            <c:forEach items="${report_types}" var="item">
                <option value="${item.code}">${item.name}</option>
            </c:forEach>
        </select>
    </span>
<span>
<span style="float:left;margin-left:10px;">应用：
        <select type="select" name="appId" id="app">
            <option value="">全部</option>
            <c:forEach items="${appIds}" var="item">
                <option value="${item.id}">${item.appName}</option>
            </c:forEach>
        </select>
    </span>
<span>
<button class="operation_btn" onclick="renderTable()">查询</button></span>
<div id="maingrid"></div>
<div class="l-loading" id="page_loading"></div>
</body>
</html>
