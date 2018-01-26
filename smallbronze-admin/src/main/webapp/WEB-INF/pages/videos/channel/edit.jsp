<%--
  Created by IntelliJ IDEA.
  User: feifan.gou
  Date: 2017/8/28
  Time: 10:58
  To change this template use File | Settings | File Templates.
  // 编辑频道模板
--%>
<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/WEB-INF/pages/inc/meta.inc" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <%@ include file="/WEB-INF/pages/inc/head.inc" %>
    <%@ include file="/WEB-INF/pages/common/style/form.css.jsp" %>
    <%@ include file="/WEB-INF/pages/common/js/form.js.jsp" %>
    <%-- 需要根据逻辑重写此js文 --%>
    <%@ include file="/WEB-INF/pages/common/js/videos/channel/add.edit.js.jsp" %>
</head>

<body>
<%@include file="/WEB-INF/pages/common/page/prompt.jsp" %>
<%@include file="/WEB-INF/pages/common/page/loading.jsp" %>
<div class="jq22-container">
    <span class="close-span"><img src="/plugin/form/img/close.ico"></span>
    <form class="cd-form floating-labels" id="channel_form" action="javascript:;">
        <legend>编辑频道</legend>
        <input type="hidden" name="pid" value="${channel.pid}"/>
        <input type="hidden" id="channel_id" value="${channel.id}"/>
        <div class="icon">
            <label class="cd-label" for="cd-name">名称</label>
            <input class="user" type="text" value="${channel.name}" name="name" id="cd-name" required>
        </div>
        <div class="icon" style="margin-top: 26px;">
            <label class="cd-label" for="cd-alias-name">别名</label>
            <input class="user" type="text" value="${channel.aliasName}" name="aliasName" id="cd-alias-name">
        </div>
        <h4>APP</h4>
        <div>
            <p class="cd-select icon">
                <select class="budget" name="appId" disabled>
                    <c:forEach items="${app_list}" var="app">
                    <option value="${app.id}" <c:if test="${channel.appId == app.id}">selected</c:if> >${app.appName}</option>
                    </c:forEach>
                </select>
            </p>
        </div>
        <default-url>
            <h4>空墙默认图</h4>
            <div>
                <c:if test="${channel.defaultUrl == '' || null == channel.defaultUrl}">
                    <img class="file_upload" width="200px" src="http://daishumovie.oss-cn-shanghai.aliyuncs.com/img/empty_image.png" alt=""/>
                </c:if>
                <c:if test="${channel.defaultUrl != ''&& null != channel.defaultUrl}">
                    <img class="file_upload" width="200px" onerror="imageError(this)" src="${channel.defaultUrl}" alt=""/>
                </c:if>
                <input type="file" accept="image/jpeg,image/jpg,image/png" id="default_url" name="">
                <input type="hidden" name="defaultUrl" id="default_url_value">
            </div>
        </default-url>
        <h4>logo</h4>
        <div>
            <c:if test="${channel.url == '' || null == channel.url}">
                <img class="file_upload" width="200px" src="http://daishumovie.oss-cn-shanghai.aliyuncs.com/img/empty_image.png" alt=""/>
            </c:if>
            <c:if test="${channel.url != ''&& null != channel.url}">
                <img class="file_upload" width="200px" onerror="imageError(this)" src="${channel.url}" alt=""/>
            </c:if>
            <input type="file" accept="image/jpeg,image/jpg,image/png" id="logo_image" name="">
            <input type="hidden" name="url" id="logo_image_value" value="${channel.url}">
        </div>
        <div class="icon" style="margin-top: 27px;">
            <label class="cd-label" for="cd-textarea">描述</label>
            <textarea class="message" name="remarks" id="cd-textarea">${channel.remarks}</textarea>
        </div>
        <div>
            <input type="submit" value="提交">
        </div>
    </form>
</div>
<%@include file="/WEB-INF/pages/common/js/base$.js.jsp"%>
</body>
</html>