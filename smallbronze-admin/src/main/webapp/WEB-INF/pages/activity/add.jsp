<%--
  Created by IntelliJ IDEA.
  User: feifan.gou
  Date: 2017/8/28
  Time: 10:58
  To change this template use File | Settings | File Templates.
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
    <form class="cd-form floating-labels" id="activity_form" action="javascript:;">
        <legend>创建活动</legend>
        <div class="icon title_div">
            <label class="cd-label" for="title">标题(18字以内)</label>
            <input class="message" type="text" name="title" id="title" required>
        </div>
        <div class="icon">
            <label class="cd-label" for="topic">话题(8个字以内)</label>
            <input class="message" type="text" name="topic" id="topic" required>
        </div>
        <h4>封面图(750x375)</h4>
        <div>
            <img class="file_upload" width="200px" src="http://daishumovie.oss-cn-shanghai.aliyuncs.com/img/empty_image.png"/>
            <input type="file" accept="image/jpeg,image/jpg,image/png" name="cover_file">
            <input type="hidden" name="cover">
        </div>
        <h4>缩略图(345x345)</h4>
        <div>
            <img class="file_upload" width="200px" src="http://daishumovie.oss-cn-shanghai.aliyuncs.com/img/empty_image.png"/>
            <input type="file" accept="image/jpeg,image/jpg,image/png" />
            <input type="hidden" name="thumbCover">
        </div>
        <div class="icon activity_margin">
            <textarea class="message" name="description" id="description" required></textarea>
            <label class="cd-label" for="description">活动描述(40个字以内)</label>
        </div>
        <div class="icon activity_margin">
            <label class="cd-label float" id="instruction_label">活动说明(500字以内)</label>
            <%@ include file="/WEB-INF/pages/common/page/rich-text.jsp"%>
        </div>
        <div class="icon activity_margin">
            <label class="cd-label" for="pre_time">预热时间(选填)</label>
            <input class="message thirty_percent" type="text" name="preTime" id="pre_time" onclick="WdatePicker({
                    dateFmt: 'yyyy-MM-dd HH:mm:ss',
                    skin:'blueFresh',
                    maxDate:'#F{$dp.$D(\'start_time\')}',
                    onpicked:function() {
                        $dp.$('start_time').click();
                         $('#pre_time').siblings('label').addClass('float');
                    }
                });">
        </div>
        <div class="icon activity_margin">
            <label class="cd-label" for="start_time">开始时间(必填)</label>
            <input class="message thirty_percent" type="text" name="startTime" id="start_time" onclick="WdatePicker({
                    dateFmt: 'yyyy-MM-dd HH:mm:ss',
                    skin:'blueFresh',
                    minDate:'#F{$dp.$D(\'pre_time\')}',
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
<%@ include file="/WEB-INF/pages/common/js/activity/add.edit.js.jsp" %>
<%@include file="/WEB-INF/pages/common/js/base$.js.jsp"%>
</body>
</html>