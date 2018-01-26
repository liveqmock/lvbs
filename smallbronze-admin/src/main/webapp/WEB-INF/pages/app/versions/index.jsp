<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/WEB-INF/pages/inc/meta.inc" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <%@ include file="/WEB-INF/pages/inc/head.inc" %>
    <%@ include file="/WEB-INF/pages/authority/button/buttonAuthStyle.jsp" %>
    <%@ include file="/WEB-INF/pages/common/js/app/versions/index.js.jsp"%>
</head>

<body style="overflow-x:hidden; padding:2px;">
<span style="float:left;margin-left:10px;">版本号：<input type="text" name="versionNum" id="versionNum"/></span>
<span style="float:left;margin-left:10px;">平台：
        <select type="select" name="plat" id="plat">
            <option value="">全部</option>
            <c:if test="${not empty platList}">
                <c:forEach items="${platList}" var="item">
                    <option value="${item.code }">${item.name }</option>
                </c:forEach>
            </c:if>
        </select>
    </span>
<span style="float:left;margin-left:10px;">
    APPID：
        <select type="select" name="appId" id="appId" >
            <option value = "">全部</option>
            <c:if test="${not empty app_list}">
                <c:forEach items="${app_list}" var="app">
                    <option value="${app.id}">${app.appName}</option>
                </c:forEach>
            </c:if>
        </select>
</span>
<span style="float:left;margin-left:10px;"><button class="operation_btn app_version_query"
                                                   onclick="f_search()">查询</button></span>
<span><button class="operation_btn app_version_add"
                                                   onclick="f_add()">新建</button></span>
<div class="l-loading" style="display:block" id="pageloading"></div>
<div class="l-clear"></div>
<div id="maingrid"></div>
<div style="display:none;"></div>
</body>
</html>
