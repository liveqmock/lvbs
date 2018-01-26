<%--
  Created by IntelliJ IDEA.
  User: feifan.gou
  Date: 2017/8/28
  Time: 11:15
  To change this template use File | Settings | File Templates.
  话题审核列表
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/WEB-INF/pages/inc/meta.inc" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head style="padding:5px; overflow:hidden;">
    <%@ include file="/WEB-INF/pages/inc/head.inc" %>
    <%@ include file="/WEB-INF/pages/common/style/index.css.jsp" %>
    <%@ include file="/WEB-INF/pages/authority/button/buttonAuthStyle.jsp" %>
    <%@ include file="/WEB-INF/pages/common/page/zoom.jsp" %>
    <%@ include file="/WEB-INF/pages/common/js/statist/video/index.js.jsp"%>
    <script type="text/javascript" src="/plugin/combo/js/comboselect.min.js" ></script>
    <script type="text/javascript" src="/plugin/combo/js/b.comboselect.js" ></script>
    <!--<style>
    .l-grid2>.l-grid-header2.l-grid-header { height: auto !important; }
    </style>-->
<body>
<span class="type" style="float:left;margin-left:10px;">类型：
        <select type="select" name="type" id="type">
            <c:forEach items="${statisticsType}" var="item">
                <option value="${item.code}">${item.name}</option>
            </c:forEach>
        </select>
    </span>
<span style="float:left;margin-left:10px;">开始时间:
    <tip></tip><input type="text" name="createTime" id="createTime" value="${startT}"  onclick="WdatePicker({
                    dateFmt: 'yyyy-MM-dd',
                    skin:'blueFresh'
                })">
    </span>
<span style="float:left;margin-left:10px;">结束时间:
    <tip></tip><input type="text" name="endTime" id="endTime" value="${endT}" onclick="WdatePicker({
                    dateFmt: 'yyyy-MM-dd',
                    skin:'blueFresh'
                })">
    </span>
<span>
    视频ID：<input name="videoId" id="videoId"/>
</span>
<span>
    视频名称：<input name="name" id="name"/>
</span>
<span>
   合集 ：<input name="s_type" id="s_type" type="checkbox"  value=""/>
</span>
<button class="operation_btn" onclick="renderTable()">查询</button></span>
<div id="maingrid"></div>
<div class="l-loading" id="page_loading"></div>
</body>
</html>
