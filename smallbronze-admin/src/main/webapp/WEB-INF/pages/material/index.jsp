<%--
  Created by IntelliJ IDEA.
  User: feifan.gou
  Date: 2017/8/28
  Time: 11:15
  To change this template use File | Settings | File Templates.
  素材列表
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/WEB-INF/pages/inc/meta.inc" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <%@ include file="/WEB-INF/pages/inc/head.inc" %>
    <%@ include file="/WEB-INF/pages/authority/button/buttonAuthStyle.jsp" %>
    <%@ include file="/WEB-INF/pages/common/page/zoom.jsp" %>
    <%@ include file="/WEB-INF/pages/common/style/index.css.jsp"%>
    <%@ include file="/WEB-INF/pages/common/style/material/index.css.jsp"%>
    <%@ include file="/WEB-INF/pages/common/js/material/index.js.jsp" %>
</head>
<body>
<div id="material_list">
    <input type="hidden" name="categoryType" id="category_type" value="${category_type}">
    <span> <%-- 时间框--%>
   创建时间： <input name="createTime" id="create_time" onclick="WdatePicker({
                    dateFmt: 'yyyy-MM-dd',
                    skin:'blueFresh',
                    onpicked:function() {
                          loadingTb();
                    }
                });">
</span>
<span>
    素材名称：<input name="name" id="name"/>
</span>
    <span>
    应用：
        <select type="select" name="appId" id="app_id" >
            <option value = "">全部</option>
            <c:if test="${not empty app_list}">
                <c:forEach  items="${app_list}" var="item">
                    <option  value = "${item.id }">${item.appName }</option>
                </c:forEach>
            </c:if>
        </select>
</span>
    <span>
    分类名称：
        <select type="select" name="categoryId" id="category_id" ></select>
</span>
    <span>
    上架状态：
        <select type="select" name="isOnShelf" id="is_on_shelf" >
            <option value = "">全部</option>
            <option value = "0">下架</option>
            <option value = "1">上架</option>
        </select>
</span>
    <span><button class="operation_btn" onclick="loadingTb()">查询</button></span>
    <span ><button class="operation_btn material_add" onclick="f_add()">新建</button></span>
    <div id="material_tab">
        <div title="音乐" class="tab_div">
            <div id="tab_1"></div>
        </div>
        <div title="滤镜" class="tab_div">
            <div id="tab_2"></div>
        </div>
        <div title="贴纸" class="tab_div">
            <div id="tab_3"></div>
        </div>
        <div title="动图" class="tab_div">
            <div id="tab_4"></div>
        </div>
        <div title="字幕" class="tab_div">
            <div id="tab_5"></div>
        </div>
    </div>
    <div class="l-loading" id="page_loading"></div>
</div>
</body>
</html>
