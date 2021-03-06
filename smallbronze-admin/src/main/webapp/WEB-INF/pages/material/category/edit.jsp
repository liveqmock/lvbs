<%--
  Created by IntelliJ IDEA.
  User: feifan.gou
  Date: 2017/8/28
  Time: 10:58
  To change this template use File | Settings | File Templates.
  编辑素材分类
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
    <%@ include file="/WEB-INF/pages/common/js/category/add.edit.js.jsp" %>
</head>
<body>
<%@include file="/WEB-INF/pages/common/page/prompt.jsp" %>
<%@include file="/WEB-INF/pages/common/page/loading.jsp" %>
<div class="jq22-container">
    <span class="close-span"><img src="/plugin/form/img/close.ico"></span>
    <form class="cd-form floating-labels" id="category_form" action="javascript:;">
        <input type="hidden" id="category_id" value="${category.id}">
        <legend>素材分类编辑</legend>

        <div class="icon">
            <label class="cd-label" for="cd-company">分类名称</label>
            <input class="user" type="text" name="name" id="cd-company" required value="${category.name}">
        </div>

        <h4>类型</h4>
        <div>
            <p class="cd-select icon">
                <select class="budget" name="type" disabled>
                    <c:if test="${not empty type_list}">
                        <c:forEach items="${type_list}" var="type">
                            <option value="${type.value}"
                                    <c:if test="${category.type == type.value}">selected</c:if> >${type.name}</option>
                        </c:forEach>
                    </c:if>
                </select>
            </p>
        </div>
        <h4>logo</h4>
        <div>
            <c:if test="${category.icon == '' || null == category.icon}">
                <img class="file_upload" width="200px"
                     src="http://daishumovie.oss-cn-shanghai.aliyuncs.com/img/empty_image.png" alt=""/>
            </c:if>
            <c:if test="${category.icon != ''&& null != category.icon}">
                <img class="file_upload" width="200px" onerror="imageError(this)" src="${category.icon}" alt=""/>
            </c:if>
            <input type="file" accept="image/jpeg,image/jpg,image/png" id="logo_image" name="file_input">
            <input type="hidden" name="icon" id="logo_image_value" value="${category.icon}">
        </div>
        <br/>
        <div class="icon">
            <label class="cd-label" for="cd-textarea">备注</label>
            <textarea class="message" name="remarks" id="cd-textarea">${category.remarks}</textarea>
        </div>
        <div>
            <input type="submit" value="提交">
        </div>
    </form>
</div>
<%@include file="/WEB-INF/pages/common/js/base$.js.jsp" %>
</body>
</html>