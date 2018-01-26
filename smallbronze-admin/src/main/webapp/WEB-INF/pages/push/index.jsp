<%--
  Created by IntelliJ IDEA.
  User: feifan.gou
  Date: 2017/8/28
  Time: 10:34
  To change this template use File | Settings | File Templates.
    push列表
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
    <%@ include file="/WEB-INF/pages/common/js/push/index.js.jsp" %>
</head>
<body>
<span class="push_time"> <%-- 时间框--%>
    <label>推送时间</label>： <input name="pushTime" id="push_time" onclick="WdatePicker({
                    dateFmt: 'yyyy-MM-dd',
                    skin:'blueFresh',
                    onpicked:function() {
                          renderTable();
                    }
                });">
</span>
<span>
    推送文案：<input name="alert" id="alert"/>
</span>
<span>
    状态：
        <select type="select" name="status" id="status">
            <c:if test="${not empty push_status}">
                <c:forEach items="${push_status}" var="status">
                    <option value="${status.value }">${status.name }</option>
                </c:forEach>
            </c:if>
        </select>
</span>
<span>
    推动平台：
        <select type="select" name="platform" id="push_platform">
            <c:if test="${not empty push_platform}">
                <c:forEach items="${push_platform}" var="platform">
                    <option value="${platform.value }">${platform.name }</option>
                </c:forEach>
            </c:if>
        </select>
</span>
<span>
    推送内容：
        <select type="select" name="targetType" id="target_type">
            <c:if test="${not empty target_type}">
                <c:forEach items="${target_type}" var="type">
                    <option value="${type.value }">${type.name }</option>
                </c:forEach>
            </c:if>
        </select>
</span>
<span><button class="operation_btn" onclick="renderTable()">查询</button></span>
<span><button class="operation_btn" onclick="location.href='/push/initAdd'">新建</button></span>
<div class="l-loading" id="page_loading"></div>
<div class="l-clear"></div>
<div id="push_list"></div>
</body>
</html>

