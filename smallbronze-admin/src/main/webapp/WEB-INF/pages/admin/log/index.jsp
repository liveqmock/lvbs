<%--
  Created by IntelliJ IDEA.
  User: feifan.gou
  Date: 2017/8/28
  Time: 10:34
  To change this template use File | Settings | File Templates.
    日志列表页
--%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/WEB-INF/pages/inc/meta.inc" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <%@ include file="/WEB-INF/pages/inc/head.inc" %>
    <%@ include file="/WEB-INF/pages/common/style/index.css.jsp" %>
    <%@ include file="/WEB-INF/pages/common/js/log/index.js.jsp"%>
</head>
<body>
<span> <%-- 时间框--%>
   操作时间： <input name="paramTime" id="log_time" onclick="WdatePicker({
                    dateFmt: 'yyyy-MM-dd',
                    skin:'blueFresh',
                    onpicked:function() {
                          renderTable();
                    }
                });">
</span>
<span>
    操作人：<input name="operatorName" id="operator_name"/>
</span>
<span>
    参数：<input name="operationParam" id="operation_param"/>
</span>
<span>
    操作类型：
        <select type="select" name="operationType" id="operation_type" >
            <option value="">全部</option>
        <c:forEach items="${operation_type}" var="item">
            <option value="${item}">${item.value }</option>
        </c:forEach>
        </select>
</span>
<span>
    操作对象：
        <select type="select" name="operationObject" id="operation_object" >
            <option value="">全部</option>
        <c:forEach items="${operation_object}" var="item">
            <option value="${item}">${item.value}</option>
        </c:forEach>
        </select>
</span>
<span>
    操作结果：
        <select type="select" name="operationResult" id="operation_result" >
            <option value="">全部</option>
        <c:forEach items="${operation_result}" var="item">
            <option value="${item}">${item.value}</option>
        </c:forEach>
        </select>
</span>
<span><button class="operation_btn" onclick="renderTable()">查询</button></span>
<div class="l-loading" id="page_loading"></div>
<div class="l-clear"></div>
<div id="log_list"></div>
</body>
</html>

