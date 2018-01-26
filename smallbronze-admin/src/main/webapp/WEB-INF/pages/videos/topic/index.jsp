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
<head>
    <%@ include file="/WEB-INF/pages/inc/head.inc" %>
    <%@ include file="/WEB-INF/pages/common/style/index.css.jsp" %>
    <%@ include file="/WEB-INF/pages/authority/button/buttonAuthStyle.jsp" %>
    <%@ include file="/WEB-INF/pages/common/page/zoom.jsp" %>
    <%@ include file="/WEB-INF/pages/common/js/videos/topic/index.js.jsp"%>
<body>
<input type="hidden" name="reportId" id="reportId"/>
<span> <%-- 时间框--%>
   时间： <input name="createTime" id="create_time" onclick="WdatePicker({
                    dateFmt: 'yyyy-MM-dd',
                    skin:'blueFresh',
                    onpicked:function() {
                          renderTable();
                    }
                });">
    </span>
<span>
    标题：<input name="title" id="title"/>
    </span>
<span>
    审核状态：
        <select type="select" name="auditStatus" id="audit_status" >
            <option value = "">全部</option>
            <c:forEach items="${audit_status}" var="item">
                <option value="${item.key}" <c:if test="${item.key eq 0}">selected</c:if>>${item.value}</option>
            </c:forEach>
        </select>
</span>
<span><button class="operation_btn" onclick="renderTable()">查询</button></span>
<!--<span><button class="operation_btn" onclick="batch_pass()">批量通过</button></span>
<span><button class="operation_btn" onclick="batch_not_pass()">批量不通过</button></span>-->
<div id="topic_list"></div>
<div class="l-loading" id="page_loading"></div>
</body>
</html>
