<%@ page import="com.daishumovie.base.enums.db.PushWay" %>
<%@ page import="com.daishumovie.base.enums.db.PushPlatform" %><%--
  Created by IntelliJ IDEA.
  User: feifan.gou
  Date: 2017/8/28
  Time: 10:58
  To change this template use File | Settings | File Templates.
  // 新建推送
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
    <%@ include file="/WEB-INF/pages/common/style/push/add.css.jsp" %>
    <%-- 需要根据逻辑重写此js文 --%>
    <%@ include file="/WEB-INF/pages/common/js/push/add.js.jsp" %>
</head>

<body>
<%@include file="/WEB-INF/pages/common/page/prompt.jsp" %>
<%@include file="/WEB-INF/pages/common/page/loading.jsp" %>
<div class="jq22-container">
    <span class="close-span"><img src="/plugin/form/img/close.ico"></span>
    <form class="cd-form floating-labels" id="push_form" action="javascript:;">
        <legend>新建推送</legend>

        <div>
            <h4>推送平台</h4>

            <ul class="cd-form-list">
                <li>
                    <input type="checkbox" id="platform_ios" value="<%=PushPlatform.ios.getValue()%>" name="platform">
                    <label for="platform_ios">IOS</label>
                </li>
                <li>
                    <input type="checkbox" id="platform_android" value="<%=PushPlatform.android.getValue()%>" name="platform">
                    <label for="platform_android">安卓</label>
                </li>
            </ul>
        </div>

        <h4>推送类别</h4>
        <div>
            <p class="cd-select icon">
                <select class="budget" id="target_type">
                    <c:if test="${not empty target_type}">
                        <c:forEach items="${target_type}" var="type">
                            <option value = "${type.value }">${type.name }</option>
                        </c:forEach>
                    </c:if>
                </select>
            </p>
        </div>

        <div class="icon" id="target_id_div">
            <label class="cd-label" for="target_id">视频ID</label>
            <input class="budget" type="text" name="targetId" id="target_id" required>
            <label class="tip_label">点击右侧查询按钮可进行检索</label>
            <img src="/images/search.ico" class="search_img">
        </div>
        <div class="icon">
            <label class="cd-label" for="alert">推送文案(40个字以内)</label>
            <textarea class="message" name="alert" id="alert" required></textarea>
        </div>

        <h4>推送方式</h4>
        <div>
            <ul class="cd-form-list">
                <li>
                    <input type="radio" name="way" value="<%=PushWay.immediately.getValue()%>" checked>
                    <label>立即推送</label>
                </li>

                <li>
                    <input type="radio" name="way" value="<%=PushWay.schedule.getValue()%>">
                    <label>定时推送</label>
                </li>
            </ul>
        </div>
        <div class="icon push_time" style="display: none;">
            <label class="cd-label" for="alert">推送时间</label>
            <input class="budget" type="text" name="pushTime" id="push_time" onclick="WdatePicker({
                    dateFmt: 'yyyy-MM-dd HH:mm:ss',
                    skin:'blueFresh',
                    onpicked:function() {
                                     $('#push_time').siblings('label').addClass('float');
                          console.log($('#push_time').val());
                    }
                });">
        </div>
        <div>
            <input type="submit" value="提交">
        </div>
    </form>
</div>
<%@include file="/WEB-INF/pages/common/js/base$.js.jsp"%>
</body>
</html>