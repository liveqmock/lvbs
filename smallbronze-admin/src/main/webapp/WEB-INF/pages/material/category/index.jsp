<%--
  Created by IntelliJ IDEA.
  User: feifan.gou
  Date: 2017/8/28
  Time: 11:15
  To change this template use File | Settings | File Templates.
  素材分类列表
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
    <%@ include file="/WEB-INF/pages/common/js/category/index.js.jsp"%>
<body>
<span> <%-- 时间框--%>
   创建时间： <input name="createTime" id="create_time" onclick="WdatePicker({
                    dateFmt: 'yyyy-MM-dd',
                    skin:'blueFresh',
                    onpicked:function() {
                          renderTable();
                    }
                });">
    </span>
<span>
    分类名称：<input name="name" id="name"/>
    </span>
<span>
    类型：
        <select type="select" name="type" id="type">
            <option value="">全部</option>
            <c:if test="${not empty type_list}">
                <c:forEach items="${type_list}" var="item">
                    <option value="${item.value }">${item.name}</option>
                </c:forEach>
            </c:if>
        </select>
    </span>
<span><button class="operation_btn" onclick="renderTable()">查询</button></span>
<span><button class="operation_btn category_add" onclick="f_add()">新建</button></span>
<div id="category_list"></div>
<div class="l-loading" id="page_loading"></div>
</body>
</html>
