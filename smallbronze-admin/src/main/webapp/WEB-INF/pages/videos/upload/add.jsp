<%@ page import="com.daishumovie.base.enums.db.VideoSource" %><%--
  Created by IntelliJ IDEA.
  User: feifan.gou
  Date: 2017/8/28
  Time: 10:58
  To change this template use File | Settings | File Templates.
  // 新建/编辑 XXX的模板
--%>
<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/WEB-INF/pages/inc/meta.inc" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <%@ include file="/WEB-INF/pages/inc/head.inc" %>
    <%@ include file="/WEB-INF/pages/common/style/form.css.jsp" %>
    <%@ include file="/WEB-INF/pages/common/style/video/upload/add.css.jsp" %>
    <%@ include file="/WEB-INF/pages/common/style/process.css.jsp" %>
    <%@ include file="/WEB-INF/pages/common/js/form.js.jsp" %>
    <%-- 需要根据逻辑重写此js文 --%>
    <%@ include file="/WEB-INF/pages/common/js/videos/upload/add.js.jsp" %>
</head>

<body>
<%@include file="/WEB-INF/pages/common/page/prompt.jsp" %>
<%@include file="/WEB-INF/pages/common/page/loading.jsp" %>
<div class="jq22-container">
    <span class="close-span"><img src="/plugin/form/img/close.ico"></span>
    <div class="upload_remote_div_backup">
        <div class="remove_button">
            <img src="/images/remove.ico" class="remove_img">
        </div>
        <div class="icon">
            <label class="cd-label">标题</label>
            <input class="company" type="text" name="title" required>
        </div>
        <div class="icon">
            <label class="cd-label">远程地址</label>
            <input class="budget" type="text" name="url" required>
        </div>
    </div>
    <div class="upload_local_div_backup">
        <div class="remove_button">
            <img src="/images/remove.ico" class="remove_img">
        </div>
        <div class="icon">
            <label class="cd-label">标题</label>
            <input class="company" type="text" name="title" required>
        </div>
        <h4>请选择本地视频</h4>
        <div>
            <img class="upload_icon" src="/images/upload.ico"/>
            <input class="upload_file_input" multiple="true" type="file" accept="video/*">
        </div>
    </div>
    <div class="upload_crawler_div_backup">
        <div class="remove_button">
            <img src="/images/remove.ico" class="remove_img">
        </div>
        <div class="icon">
            <label class="cd-label">标题</label>
            <input class="company" type="text" name="title" required>
        </div>
        <div class="icon">
            <label class="cd-label">爬虫地址</label>
            <input class="budget" type="text" name="url" required>
        </div>
    </div>
    <form class="cd-form floating-labels" id="upload_form" action="javascript:;">
        <legend>上传视频</legend>

        <%--<div class="icon">--%>
            <%--<label class="cd-label" for="cd-company">文本框</label>--%>
            <%--<input class="user" type="text" name="name" id="cd-company" required>--%>
        <%--</div>--%>
        <h4>视频来源</h4>
        <div>
            <ul class="cd-form-list">
                <li>
                    <input type="radio" name="upload_method" value="<%=VideoSource.crawler.getValue()%>" checked id="upload_crawler" >
                    <label for="upload_crawler"><%=VideoSource.crawler.getName()%></label>
                </li>
                <li>
                    <input type="radio" name="upload_method" value="<%=VideoSource.remote.getValue()%>"  id="upload_remote" >
                    <label for="upload_remote"><%=VideoSource.remote.getName()%></label>
                </li>
                <li>
                    <input type="radio" name="upload_method" value="<%=VideoSource.local.getValue()%>" id="upload_local" >
                    <label for="upload_local"><%=VideoSource.local.getName()%></label>
                </li>

                <span class="add_button">
                    <img src="/images/add.ico" id="create_data" class="add_img">
                </span>
            </ul>
        </div>
        <div id="upload_progress">
            <p data-value="0">上传进度</p>
            <progress max="100" value="0" class="css3">
                <div class="progress-bar">
                    <span style="width: 0">0%</span>
                </div>
            </progress>
        </div>
        <div class="video_data_div">
            <div class="origin_data">
                <div class="upload_remote_div data_div">
                    <div class="icon">
                        <label class="cd-label">标题</label>
                        <input class="company" type="text" name="title" required>
                    </div>
                    <div class="icon">
                        <label class="cd-label">远程地址</label>
                        <input class="budget" type="text" name="url" required>
                    </div>
                </div>
                <div class="upload_local_div data_div">
                    <div class="icon">
                        <label class="cd-label">标题</label>
                        <input class="company" type="text" name="title">
                    </div>
                    <h4>请选择本地视频</h4>
                    <div>
                        <img class="upload_icon" src="/images/upload.ico" alt=""/>
                        <input class="upload_file_input" multiple="true" type="file" accept="video/*">
                    </div>
                </div>
                <div class="upload_crawler_div data_div">
                    <div class="icon">
                        <label class="cd-label">标题</label>
                        <input class="company" type="text" name="title">
                    </div>
                    <div class="icon">
                        <label class="cd-label">爬虫地址</label>
                        <input class="budget" type="text" name="url">
                    </div>
                </div>
            </div>
            <div class="add_data">

            </div>
        </div>
        <div>
            <input type="submit" value="提交">
        </div>
    </form>
</div>
<%@include file="/WEB-INF/pages/common/js/base$.js.jsp"%>
</body>
</html>