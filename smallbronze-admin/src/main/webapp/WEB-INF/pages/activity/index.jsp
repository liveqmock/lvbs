<%--
  Created by IntelliJ IDEA.
  User: feifan.gou
  Date: 2017/8/28
  Time: 10:34
  To change this template use File | Settings | File Templates.
    活动列表页
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
    <%@ include file="/WEB-INF/pages/common/js/activity/index.js.jsp"%>
    <style type="text/css">
        #pre_time,#start_time {
            width:80px;
        }
    </style>
</head>
<body>
<%@ include file="/WEB-INF/pages/common/page/view.jsp"%>
<span class="param_span"> <%-- 时间框--%>
   预热时间： <input name="preTime" id="pre_time" onclick="WdatePicker({
                    dateFmt: 'yyyy-MM-dd',
                    skin:'blueFresh',
                    onpicked:function() {
                          renderTable();
                    }
                });" value="${param_pre_time}">
</span>
<span class="param_span"> <%-- 时间框--%>
   开始时间： <input name="startTime" id="start_time" onclick="WdatePicker({
                    dateFmt: 'yyyy-MM-dd',
                    skin:'blueFresh',
                    onpicked:function() {
                          renderTable();
                    }
                });" value="${param_start_time}">
</span>
<span class="param_span">
    标题：<input name="title" id="title" value="${param_title}"/>
</span>
<span class="param_span">
    话题：<input name="topic" id="topic" value="${param_topic}" />
</span>
<span class="param_span">
    状态：
        <select type="select" name="status" id="status" >
            <c:if test="${not empty activity_status}">
                <c:forEach items="${activity_status}" var="status">
                    <c:if test="${status.value == param_status}">
                        <option value = "${status.value}" selected>${status.name}</option>
                    </c:if>
                    <c:if test="${status.value != param_status}">
                        <option value = "${status.value}">${status.name}</option>
                    </c:if>
                </c:forEach>
            </c:if>
        </select>
</span>
<span class="param_span">
    是否上线：
        <select type="select" name="whetherOnline" id="whether_online" >
            <c:if test="${not empty whether_online}">
                <c:forEach items="${whether_online}" var="whether">
                    <c:if test="${whether.value == 1}">
                        <option value = "${whether.value}" selected>${whether.name}</option>
                    </c:if>
                    <c:if test="${whether.value == 0}">
                        <option value = "${whether.value}" selected>${whether.name}</option>
                    </c:if>
                </c:forEach>
            </c:if>
        </select>
</span>
<span><button class="operation_btn" onclick="renderTable()">查询</button></span>
<span ><button class="operation_btn" onclick="location.href='/activity/initAdd'">新建</button></span>
<div class="l-loading" id="page_loading"></div>
<div class="l-clear"></div>
<div id="activity_list"></div>
</body>
</html>