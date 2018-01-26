<%--
  // 新建活动
--%>
<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/WEB-INF/pages/inc/meta.inc" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <%@ include file="/WEB-INF/pages/inc/head.inc" %>
    <%@ include file="/WEB-INF/pages/common/style/form.css.jsp" %>
    <%@ include file="/WEB-INF/pages/common/style/activity/add.edit.css.jsp" %>
    <%@ include file="/WEB-INF/pages/common/js/form.js.jsp" %>
</head>

<body>
<%@include file="/WEB-INF/pages/common/page/prompt.jsp" %>
<%@include file="/WEB-INF/pages/common/page/loading.jsp" %>
<div class="jq22-container">
    <span class="close-span"><img src="/plugin/form/img/close.ico"></span>
    <form class="cd-form floating-labels" id="ad_form" action="javascript:;">
        <legend>创建广告</legend>

        <div class="icon title_div">
            <label class="cd-label" for="name">名称(20个字以内)</label>
            <input class="message" type="text" name="name" id="name" required>
        </div>

        <h4>广告类型</h4>
        <div>
            <p class="cd-select icon">
                <select class="budget"  id="ad_type">
                    <c:if test="${not empty adType_list}">
                        <c:forEach items="${adType_list}" var="adType">
                            <option value="${adType.value}">${adType.name}</option>
                        </c:forEach>
                    </c:if>
                </select>
            </p>
        </div>

        <h4>广告位置</h4>
        <div>
            <p class="cd-select icon">
                <select class="budget" id= "orders" >
                   <option value="1" >1</option>
                   <option value="2" >2</option>
                   <option value="3" >3</option>
                   <option value="4" >4</option>
                   <option value="5" >5</option>
                </select>
            </p>
        </div>

        <h4>封面图(1000x500)</h4>
        <div>
            <img class="file_upload" width="200px" src="http://daishumovie.oss-cn-shanghai.aliyuncs.com/img/empty_image.png"/>
            <input type="file" accept="image/jpeg,image/jpg,image/png" name="cover_file">
            <input type="hidden" name="adCover">
        </div>

        <h4>跳转类型</h4>
        <div>
            <p class="cd-select icon">
                <select class="budget" id="target_type">
                    <c:if test="${not empty targetType_list}">
                        <c:forEach items="${targetType_list}" var="targetType">
                            <c:if test="${targetType.value != 2 && targetType.value != 3}">
                                <option value="${targetType.value}">${targetType.name}</option>
                            </c:if>
                        </c:forEach>
                    </c:if>
                </select>
            </p>
        </div>

        <h4>跳转地址</h4>
        <div class="icon title_div">
            <%--<label class="cd-label" for="name">跳转地址</label>--%>
            <input class="message" type="text" name="target" id="target" required>
        </div>

      <%--  <h4>切换/停留时间(s)</h4>
        <div>
            <input  type="number"  name="duration" id="duration" required>
        </div>--%>

        <div class="icon activity_margin">
            <label class="cd-label" for="start_time">开始时间(必填)</label>
            <input class="message thirty_percent" type="text" name="startTime" id="start_time" onclick="WdatePicker({
                    dateFmt: 'yyyy-MM-dd HH:mm:ss',
                    skin:'blueFresh',
                    maxDate:'#F{$dp.$D(\'end_time\')}',
                    onpicked:function() {
                        $dp.$('end_time').click();
                         $('#start_time').siblings('label').addClass('float');
                    }
                });" required>
        </div>
        <div class="icon activity_margin end_time">
            <label class="cd-label" for="end_time">结束时间(必填)</label>
            <input class="message thirty_percent" type="text" name="endTime" id="end_time" onclick="WdatePicker({
                dateFmt: 'yyyy-MM-dd HH:mm:ss',
                skin:'blueFresh',
                minDate:'#F{$dp.$D(\'start_time\')}',
                onpicked:function() {
                     $('#end_time').siblings('label').addClass('float');
                }
            });" required>
        </div>
        <div>
            <input type="submit" value="提交">
        </div>
    </form>
</div>
<%-- 需要根据逻辑重写此js文 --%>
<%@ include file="/WEB-INF/pages/common/js/ad/add.edit.js.jsp" %>
<%@include file="/WEB-INF/pages/common/js/base$.js.jsp"%>
</body>
</html>