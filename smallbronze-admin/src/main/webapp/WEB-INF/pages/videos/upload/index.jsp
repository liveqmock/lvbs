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
    <%@ include file="/WEB-INF/pages/common/js/videos/upload/index.js.jsp"%>
<body>
<%@ include file="/WEB-INF/pages/common/page/loading.jsp"%>
<span>
    标题：<input name="title" id="title"/>
    </span>
<span>
    上传状态：
        <select type="select" name="status" id="status" >
            <option value = "">全部</option>
            <c:forEach items="${upload_status}" var="item">
                <c:if test="${item.value == 3}">selected="selected"</c:if>
                <option value="${item.value}">${item.name}</option>
            </c:forEach>
        </select>
</span>
<span><button class="operation_btn" onclick="renderTable()">查询</button></span>
<span><button class="operation_btn upload_add" onclick="location.href='/upload/v/initAdd'">新建</button></span>
<div id="topic_list"></div>
<div class="l-loading" id="page_loading"></div>
</body>
</html>
