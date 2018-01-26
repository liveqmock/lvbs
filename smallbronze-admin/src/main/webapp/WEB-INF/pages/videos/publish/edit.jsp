<%@ page import="com.daishumovie.base.enums.db.Whether" %><%--
  Created by IntelliJ IDEA.
  User: feifan.gou
  Date: 2017/8/28
  Time: 10:58
  To change this template use File | Settings | File Templates.
  // 编辑已发布视频
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
    <%@ include file="/WEB-INF/pages/common/js/videos/publish/edit.js.jsp" %>
    <style type="text/css">
        .video_div {
            display: none;
        }
        .jq22-container {
            height: 65em;
        }
        input[type=submit]{
            margin-top: -48px;
        }
        #upload_again {
            display: none;
        }
    </style>
</head>

<body>
<%@include file="/WEB-INF/pages/common/page/prompt.jsp" %>
<%@include file="/WEB-INF/pages/common/page/loading.jsp" %>
<div class="jq22-container">
    <span class="close-span"><img src="/plugin/form/img/close.ico"></span>
    <form class="cd-form floating-labels" id="topic_form" action="javascript:;">
        <legend>编辑已发布视频</legend>
        <input type="hidden" name="id" value="${id}"/>
        <div class="icon">
            <label class="cd-label float" for="title">标题</label>
            <input class="user" type="text" value="${title}" name="name" id="title" required>
        </div>

        <h4>是否使用原封面</h4>
        <div>
            <ul class="cd-form-list">
                <li>
                    <input type="radio" name="is_default" value="<%=Whether.yes.getValue()%>" checked id="choice_yes" >
                    <label for="choice_yes">是</label>
                </li>

                <li>
                    <input type="radio" name="is_default" value="<%=Whether.no.getValue()%>" id="choice_no" >
                    <label for="choice_no">否</label>
                </li>
            </ul>
        </div>
        <h4>是否重新上传封面</h4>
        <div>
            <ul class="cd-form-list">
                <li>
                    <input type="radio" name="is_re_upload" value="<%=Whether.yes.getValue()%>"/>
                    <label for="choice_yes">是</label>
                </li>
                <li>
                    <input type="radio" name="is_re_upload" value="<%=Whether.no.getValue()%>" checked/>
                    <label for="choice_no">否</label>
                </li>
            </ul>
        </div>
        <div class="old_cover">
            <h4>封面图</h4>
            <div>
                <img width="400px" src="${cover}"/>
            </div>
        </div>
        <div class="video_div">
            <h4>重新截取</h4>
            <div>
                <video width="400x" controls src="${video_url}"></video>
            </div>
        </div>
        <div>
            <input type="submit" value="提交">
        </div>
    </form>
</div>
<input type="file" name="file" id="upload_again">
<input type="hidden" id="old_cover_value" value="${cover}">
<input type="hidden" id="new_cover_value">
<%@include file="/WEB-INF/pages/common/js/base$.js.jsp"%>
</body>
</html>