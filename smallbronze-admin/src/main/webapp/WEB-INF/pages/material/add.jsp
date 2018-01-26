<%--
  Created by IntelliJ IDEA.
  User: feifan.gou
  Date: 2017/8/28
  Time: 10:58
  To change this template use File | Settings | File Templates.
  新建素材
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/WEB-INF/pages/inc/meta.inc" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <%@ include file="/WEB-INF/pages/inc/head.inc" %>
    <%@ include file="/WEB-INF/pages/common/style/form.css.jsp" %>
    <%@ include file="/WEB-INF/pages/common/js/form.js.jsp" %>
    <%@ include file="/WEB-INF/pages/common/js/material/add.edit.js.jsp"%>
</head>
<body>
<%@include file="/WEB-INF/pages/common/page/prompt.jsp" %>
<%@include file="/WEB-INF/pages/common/page/loading.jsp" %>
<div class="jq22-container">
    <span class="close-span"><img src="/plugin/form/img/close.ico"></span>
    <form class="cd-form floating-labels" id="material_form" action="javascript:;">
        <input type="hidden" name="categoryType" value="${category_type}">
        <legend>素材添加【${category_name}】</legend>

        <div class="icon">
            <label class="cd-label" for="cd-company">素材名称</label>
            <input class="user" type="text" name="name" id="cd-company" required>
        </div>

        <h4>应用</h4>
        <div>
            <p class="cd-select icon">
                <select class="budget" name="appId">
                    <c:if test="${not empty app_list}">
                        <c:forEach items="${app_list}" var="app">
                            <option value="${app.id}">${app.appName}</option>
                        </c:forEach>
                    </c:if>
                </select>
            </p>
        </div>

        <h4>素材类型</h4>
        <div>
            <p class="cd-select icon">
                <select class="budget" name="categoryId">
                    <c:if test="${not empty category_list}">
                        <c:forEach items="${category_list}" var="category">
                            <option value="${category.id}">${category.name}</option>
                        </c:forEach>
                    </c:if>
                </select>
            </p>
        </div>

        <h4>上架状态</h4>
        <div>
            <ul class="cd-form-list">
                <li>
                    <input type="radio" name="radio-button" value="1" checked id="on_shelf">
                    <label for="on_shelf">上架</label>
                </li>

                <li>
                    <input type="radio" name="radio-button" value="0" id="off_shelf">
                    <label for="off_shelf">下架</label>
                </li>
            </ul>
        </div>
        <h4>预览图</h4>
        <div>
            <img class="file_upload" width="200px"
                 src="http://daishumovie.oss-cn-shanghai.aliyuncs.com/img/empty_image.png" alt=""/>
            <input type="file" accept="image/jpeg,image/jpg,image/png" id="preview_image" name="file_input">
            <input type="hidden" name="previewUrl" id="preview_image_value">
        </div>
        <h4>logo</h4>
        <div>
            <img class="file_upload" width="200px"
                 src="http://daishumovie.oss-cn-shanghai.aliyuncs.com/img/empty_image.png" alt=""/>
            <input type="file" accept="image/jpeg,image/jpg,image/png" id="logo_image" name="file_input">
            <input type="hidden" value="" name="icon" id="logo_image_value">
        </div>
        <h4>素材文件</h4>
        <div>
            <span class="file_info">
                <img class="zip_icon" src="/plugin/form/img/zip-icon.jpg" alt=""/>
                <p></p>
            </span>
            <img class="file_upload" width="px" src="/plugin/form/img/choose-file.jpg" alt=""/>
            <input type="file" accept="application/zip" id="material_file" name="file_input">
            <input type="hidden" name="contentPath" value="" id="material_file_value">
        </div>
        <div>
            <input type="submit" value="提交">
        </div>
    </form>
</div>
<%@include file="/WEB-INF/pages/common/js/base$.js.jsp"%>
</body>
</html>