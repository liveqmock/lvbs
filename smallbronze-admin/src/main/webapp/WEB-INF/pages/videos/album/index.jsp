<%--
  Created by IntelliJ IDEA.
  User: feifan.gou
  Date: 2017/8/28
  Time: 10:34
  To change this template use File | Settings | File Templates.
    合辑列表页。
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
    <%@ include file="/WEB-INF/pages/common/js/videos/album/index.js.jsp"%>
</head>
<body>
<span class="param_time_span param_span"> <%-- 时间框--%>
    <label>创建时间</label>： <input name="paramTime" id="param_time" onclick="WdatePicker({
                    dateFmt: 'yyyy-MM-dd',
                    skin:'blueFresh',
                    onpicked:function() {
                          renderTable();
                    }
                });" value="${param_time}"/>
</span>
<span class="param_span">
    标题：<input name="title" id="title" value="${title}"/>
</span>
<span class="param_span">
    副标题：<input name="subtitle" id="subtitle" value="${subtitle}"/>
</span>
<span class="param_span">
    状态：
        <select type="select" name="status" id="status" >
            <c:if test="${not empty album_status}">
                <c:forEach items="${album_status}" var="status">
                    <c:if test="${param_status == status.value}">
                        <option value = "${status.value }" selected>${status.name }</option>
                    </c:if>
                    <c:if test="${param_status != status.value}">
                        <option value = "${status.value }">${status.name }</option>
                    </c:if>
                </c:forEach>
            </c:if>
        </select>
</span>
<span><button class="operation_btn" onclick="renderTable()">查询</button></span>
<span ><button class="operation_btn" onclick="location.href='/album/initAdd'">新建</button></span>
<div class="l-loading" id="page_loading"></div>
<div class="l-clear"></div>
<div id="album_list"></div>
</body>
</html>

