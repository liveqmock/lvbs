<%--
    广告列表页
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
    <%@ include file="/WEB-INF/pages/common/js/ad/index.js.jsp"%>
    <style type="text/css">
        #pre_time,#start_time {
            width:80px;
        }
    </style>
</head>
<body>
<%@ include file="/WEB-INF/pages/common/page/view.jsp"%>


<span class="param_span">
    名称：<input name="name" id="name" value="${name}"/>
</span>
<span class="param_span">
    广告位：<input name="orders" id="orders" value="${orders}" />
</span>
<span class="param_span">
    是否上线：
        <select type="select" name="status" id="status" >
            <c:if test="${not empty ad_status}">
                <c:forEach items="${ad_status}" var="s">
                    <c:if test="${s.value == 1}">
                        <option value = "${s.value}" selected>${s.name}</option>
                    </c:if>
                    <c:if test="${s.value == 0}">
                        <option value = "${s.value}" selected>${s.name}</option>
                    </c:if>
                </c:forEach>
            </c:if>
        </select>
</span>

<span class="param_span"> <%-- 时间框--%>
   创建时间： <input name="createTime" id="create_time" onclick="WdatePicker({
                    dateFmt: 'yyyy-MM-dd',
                    skin:'blueFresh',
                    onpicked:function() {
                          renderTable();
                    }
                });" value="${create_time}">
</span>

<span><button class="operation_btn" onclick="renderTable()">查询</button></span>
<span ><button class="operation_btn" onclick="location.href='/ad/initAdd'">新建</button></span>
<div class="l-loading" id="page_loading"></div>
<div class="l-clear"></div>
<div id="ad_list"></div>
</body>
</html>

