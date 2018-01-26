<%--
  Created by IntelliJ IDEA.
  User: feifan.gou
  Date: 2017/8/28
  Time: 10:34
  To change this template use File | Settings | File Templates.
    用户列表页
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
    <%@ include file="/WEB-INF/pages/common/js/user/index.js.jsp"%>
</head>
<body>
<span> <%-- 时间框--%>
   注册时间： <input name="registerTime" id="register_time" onclick="WdatePicker({
                    dateFmt: 'yyyy-MM-dd',
                    skin:'blueFresh',
                    onpicked:function() {
                          renderTable();
                    }
                });">
</span>
<span>
    昵称：<input name="nickName" id="nickname"/>
</span>
<span>
    手机号：<input name="mobile" id="mobile"/>
</span>
<span>
    是否可以发布视频：
        <select type="select" name="isTopicAuth" id="is_topic_auth" >
            <option  value = "">全部</option>
            <c:forEach items="${whether}" var="whether">
                <option  value = "${whether.value}">${whether.name}</option>
            </c:forEach>
        </select>
</span>
<span>
    是否可以回复评论：
        <select type="select" name="isReplyAuth" id="is_reply_auth" >
            <option  value = "">全部</option>
             <c:forEach items="${whether}" var="whether">
                 <option  value = "${whether.value}">${whether.name}</option>
             </c:forEach>
        </select>
</span>
<span>
    是否虚拟：
        <select type="select" name="fictitious" id="fictitious" >
            <option  value = "">全部</option>
             <c:forEach items="${whether}" var="whether">
                 <option  value = "${whether.value}">${whether.name}</option>
             </c:forEach>
        </select>
</span>
<span><button class="operation_btn" onclick="renderTable()">查询</button></span>
<%--<span ><button class="operation_btn" onclick="f_add()">新建</button></span>--%>
<div class="l-loading" id="page_loading"></div>
<div class="l-clear"></div>
<div id="user_list"></div>
</body>
</html>

