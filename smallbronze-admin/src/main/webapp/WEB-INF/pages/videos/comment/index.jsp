<%--
  Created by IntelliJ IDEA.
  User: feifan.gou
  Date: 2017/8/28
  Time: 11:15
  To change this template use File | Settings | File Templates.
  话题回复、评论列表
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
    <%--<%@ include file="/WEB-INF/pages/common/page/zoom.jsp" %>--%>
    <%@ include file="/WEB-INF/pages/common/js/videos/comment/index.js.jsp"%>
<body>
<span> <%-- 时间框--%>
   发布时间： <input name="createTime" id="create_time" onclick="WdatePicker({
                    dateFmt: 'yyyy-MM-dd',
                    skin:'blueFresh',
                    onpicked:function() {
                      renderTable();
                    }
                });">
    </span>
<%--<span>--%>
    <%--评论内容：<input name="content" id="content"/>--%>
    <%--</span>--%>
<span>
    评论类型：
        <select type="select" name="type" id="type" >
            <c:forEach items="${comment_target_types}" var="types">
                <c:if test="${types.value == 1 or types.value == 5 or types.value == 6}">
                    <option value="${types.value}">${types.name}</option>
                </c:if>
            </c:forEach>
        </select>
</span>
<span>
    审核状态：
        <select type="select" name="auditStatus" id="audit_status" >
            <c:forEach items="${audit_status_list}" var="status">
                <option value="${status.value}">${status.name}</option>
            </c:forEach>
        </select>
</span>

<%--<span>--%>
    <%--状态：--%>
        <%--<select type="select" name="status" id="status" >--%>
            <%--<option value = "">全部</option>--%>
            <%--<option value = "1" selected = "selected" >正常</option>--%>
            <%--<option value = "0" >已删除</option>--%>
        <%--</select>--%>
<%--</span>--%>
<span><button class="operation_btn" onclick="renderTable()">查询</button></span>
<span><button class="operation_btn comment_batch_through audit" onclick="batchThrough()">批量通过</button></span>
<span><button class="operation_btn comment_batch_fail audit" onclick="batchFail()">批量不通过</button></span>
<div id="comment_list"></div>
<div class="l-loading" id="page_loading"></div>
</body>
</html>
