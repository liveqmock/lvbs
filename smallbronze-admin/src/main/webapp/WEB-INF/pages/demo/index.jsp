<%--
  Created by IntelliJ IDEA.
  User: feifan.gou
  Date: 2017/8/28
  Time: 10:34
  To change this template use File | Settings | File Templates.
    列表页的demo，可将此文件复制，修改字段，按钮即可。
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
    <%@ include file="/WEB-INF/pages/common/js/demo/index.js.jsp"%>
</head>
<body>
<span> <%-- 时间框--%>
   时间文本框： <input name="createTime" id="create_time" onclick="WdatePicker({
                    dateFmt: 'yyyy-MM-dd',
                    skin:'blueFresh',
                    onpicked:function() {
                          renderTable();
                    }
                });">
</span>
<span>
    普通文本框：<input name="name" id="name"/>
</span>
<span>
    下拉选项：
        <select type="select" name="type" id="type" >
            <option  value = "">全部</option>
            <option  value = "1">option-1</option>
            <option  value = "2">option-2</option>
            <option  value = "3">option-3</option>
            <%--<c:if test="${not empty video_episode_type}">--%>
                <%--<c:forEach  items="${video_episode_type}" var="item">--%>
                    <%--<option  value = "${item.value }">${item.name }</option>--%>
                <%--</c:forEach>--%>
            <%--</c:if>--%>
        </select>
</span>
<span><button class="operation_btn" onclick="renderTable()">查询</button></span>
<span ><button class="operation_btn" onclick="location.href='/activity/initAdd'">新建</button></span>
<div class="l-loading" id="page_loading"></div>
<div class="l-clear"></div>
<div id="demo_list"></div>
</body>
</html>

