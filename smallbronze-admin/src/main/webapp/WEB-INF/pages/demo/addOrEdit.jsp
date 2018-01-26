<%--
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
    <%@ include file="/WEB-INF/pages/common/js/form.js.jsp" %>
    <%-- 需要根据逻辑重写此js文 --%>
    <%@ include file="/WEB-INF/pages/common/js/demo/add.edit.js.jsp" %>
</head>

<body>
<%@include file="/WEB-INF/pages/common/page/prompt.jsp" %>
<%@include file="/WEB-INF/pages/common/page/loading.jsp" %>
<div class="jq22-container">
    <span class="close-span"><img src="/plugin/form/img/close.ico"></span>
    <form class="cd-form floating-labels" id="material_form" action="javascript:;">
        <legend>XXX标题</legend>

        <div class="icon">
            <label class="cd-label" for="cd-company">文本框</label>
            <input class="message" type="text" name="name" id="cd-company" required>
        </div>

        <h4>下拉框</h4>
        <div>
            <p class="cd-select icon">
                <select class="message" name="">
                </select>
            </p>
        </div>

        <h4>radio框</h4>
        <div>
            <ul class="cd-form-list">
                <li>
                    <input type="radio" name="radio-button" value="" checked id="" >
                    <label for="">radio-1</label>
                </li>

                <li>
                    <input type="radio" name="radio-button" value="" id="" >
                    <label for="">radio-1</label>
                </li>
            </ul>
        </div>
        <h4>图片上传</h4>
        <div>
            <img class="file_upload" width="200px" src="http://daishumovie.oss-cn-shanghai.aliyuncs.com/img/empty_image.png"/>
            <input type="file" accept="image/jpeg,image/jpg,image/png" id="" name="">
            <input type="hidden" name="" id="">
        </div>
        <div>
            <h4>checkbox框</h4>

            <ul class="cd-form-list">
                <li>
                    <input type="checkbox" id="">
                    <label for="">Option 1</label>
                </li>

                <li>
                    <input type="checkbox" id="">
                    <label for="">Option 2</label>
                </li>

                <li>
                    <input type="checkbox" id="">
                    <label for="">Option 3</label>
                </li>
            </ul>
        </div>

        <div class="icon">
            <label class="cd-label" for="cd-textarea">textarea</label>
            <textarea class="message" name="cd-textarea" id="cd-textarea" required></textarea>
        </div>
        <div>
            <input type="submit" value="提交">
        </div>
    </form>
</div>
<%@include file="/WEB-INF/pages/common/js/base$.js.jsp"%>
</body>
</html>