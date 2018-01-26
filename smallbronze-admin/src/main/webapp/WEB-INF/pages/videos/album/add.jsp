<%--
  Created by IntelliJ IDEA.
  User: feifan.gou
  Date: 2017/8/28
  Time: 10:58
  To change this template use File | Settings | File Templates.
  新建合辑
--%>
<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/WEB-INF/pages/inc/meta.inc" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <%@ include file="/WEB-INF/pages/inc/head.inc" %>
    <%@ include file="/WEB-INF/pages/common/style/form.css.jsp" %>
    <%@ include file="/WEB-INF/pages/common/js/form.js.jsp" %>
    <%-- 需要根据逻辑重写此js文 --%>
    <%@ include file="/WEB-INF/pages/common/js/videos/album/add.edit.js.jsp" %>
</head>

<body>
<%@include file="/WEB-INF/pages/common/page/prompt.jsp" %>
<%@include file="/WEB-INF/pages/common/page/loading.jsp" %>
<div class="jq22-container">
    <span class="close-span"><img src="/plugin/form/img/close.ico"></span>
    <form class="cd-form floating-labels" id="album_form" action="javascript:;">
        <legend>新建合辑</legend>

        <div class="icon">
            <label class="cd-label" for="title">标题（10个字以内）</label>
            <input class="message" type="text" name="title" id="title" required>
        </div>
        <div class="icon" style="margin-top: 20px;">
            <label class="cd-label" for="subtitle">副标题（20个字以内）</label>
            <input class="message" type="text" name="subtitle" id="subtitle" required>
        </div>

        <h4>封面（750x375，JPG PNG格式）</h4>
        <div>
            <img class="file_upload" width="200px" src="http://daishumovie.oss-cn-shanghai.aliyuncs.com/img/empty_image.png"/>
            <input type="file" accept="image/jpeg,image/jpg,image/png">
            <input type="hidden" name="cover" >
        </div>
        <div>
            <input type="submit" value="提交">
        </div>
    </form>
</div>
<%@include file="/WEB-INF/pages/common/js/base$.js.jsp"%>
</body>
</html>