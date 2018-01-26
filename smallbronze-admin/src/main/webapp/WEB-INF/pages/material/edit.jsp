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
    <%@ include file="/WEB-INF/pages/common/js/material/add.edit.js.jsp" %>
</head>
<body>
<%@include file="/WEB-INF/pages/common/page/prompt.jsp" %>
<%@include file="/WEB-INF/pages/common/page/loading.jsp" %>

<div class="jq22-container">
    <span class="close-span"><img src="/plugin/form/img/close.ico"></span>
    <form class="cd-form floating-labels" id="material_form" action="javascript:;">
            <input type="hidden" name="categoryType" value="${category_type}">
            <input type="hidden" id="material_id" value="${material.id}">
            <legend>素材编辑【${category_name}】</legend>

            <div class="icon">
                <label class="cd-label" for="cd-company">素材名称</label>
                <input class="user" type="text" name="name" id="cd-company" required value="${material.name}">
            </div>

            <h4>应用</h4>
            <div>
                <p class="cd-select icon">
                    <select class="budget" name="appId">
                        <c:if test="${not empty app_list}">
                            <c:forEach items="${app_list}" var="app">
                                <option <c:if test="${rel.appId == app.id}">selected</c:if> value="${app.id}">${app.appName}</option>
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
                                <option value="${category.id}" <c:if test="${rel.categoryId == category.id}">selected</c:if> >${category.name}</option>
                            </c:forEach>
                        </c:if>
                    </select>
                </p>
            </div>

            <h4>上架状态</h4>
            <div>
                <ul class="cd-form-list">
                    <li>
                        <input type="radio" name="radio-button" value="1" <c:if test="${material.isOnShelf == 1}">checked</c:if> id="on_shelf">
                        <label for="on_shelf">上架</label>
                    </li>

                    <li>
                        <input type="radio" name="radio-button" value="0" <c:if test="${material.isOnShelf == 0}">checked</c:if> id="off_shelf">
                        <label for="off_shelf">下架</label>
                    </li>
                </ul>
            </div>
            <h4>预览图</h4>
            <div>
                <c:if test="${material.previewUrl == '' || null == material.previewUrl}">
                    <img class="file_upload" width="200px" src="http://daishumovie.oss-cn-shanghai.aliyuncs.com/img/empty_image.png" alt=""/>
                </c:if>
                <c:if test="${material.previewUrl != ''&& null != material.previewUrl}">
                    <img class="file_upload" width="200px" onerror="imageError(this)" src="${material.previewUrl}" alt=""/>
                </c:if>
                <input type="file" accept="image/jpeg,image/jpg,image/png" id="preview_image" name="file_input">
                <input type="hidden" name="previewUrl"  id="preview_image_value" value="${material.previewUrl}">
            </div>
            <h4>logo</h4>
            <div>
                <c:if test="${material.icon == '' || null == material.icon}">
                    <img class="file_upload" width="200px" src="http://daishumovie.oss-cn-shanghai.aliyuncs.com/img/empty_image.png" alt=""/>
                </c:if>
                <c:if test="${material.icon != '' && null != material.icon}">
                    <img class="file_upload" width="200px" onerror="imageError(this)" src="${material.icon}" alt=""/>
                </c:if>
                <input type="file" accept="image/jpeg,image/jpg,image/png" id="logo_image" name="file_input">
                <input type="hidden" name="icon" id="logo_image_value" value="${material.icon}">
            </div>
            <h4>素材文件</h4>
            <div>
                <span class="file_info" style="display: block;">
                    <img class="zip_icon" src="/plugin/form/img/zip-icon.jpg" alt=""/>
                    <p>素材文件</p>
                </span>
                <input type="file" accept="application/zip" id="material_file" name="file_input">
                <input type="hidden" name="contentPath" id="material_file_value" value="${material.contentPath}">
            </div>
            <div>
                <input type="submit" value="提交">
            </div>
    </form>
</div>
<%@include file="/WEB-INF/pages/common/js/base$.js.jsp"%>
</body>
</html>