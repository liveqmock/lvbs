<%--
  Created by IntelliJ IDEA.
  User: feifan.gou
  Date: 2017/8/28
  Time: 10:34
  To change this template use File | Settings | File Templates.
    桶数据列表。
--%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/WEB-INF/pages/inc/meta.inc" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <%@ include file="/WEB-INF/pages/inc/head.inc" %>
    <%@ include file="/WEB-INF/pages/common/style/index.css.jsp" %>
    <%@ include file="/WEB-INF/pages/common/style/video/bucket/index.css.jsp" %>
    <%@ include file="/WEB-INF/pages/authority/button/buttonAuthStyle.jsp" %>
    <%@ include file="/WEB-INF/pages/common/js/videos/bucket/index.js.jsp"%>
</head>
<body>
<%@ include file="/WEB-INF/pages/common/page/prompt.jsp"%>
    <div id="bucket_layout">
        <div position="center">
            <span> <%-- 时间框--%>
                创建时间： <input name="createTime" id="create_time" onclick="WdatePicker({
                                dateFmt: 'yyyy-MM-dd',
                                skin:'blueFresh',
                                onpicked:function() {
                                      renderTable();
                                }
                            });">
            </span>
            <span>
                创建人：<input name="adminName" id="admin_name"/>
            </span>
            <span>
                桶ID：<input name="id" id="id"/>
            </span>
            <span><button class="operation_btn" onclick="renderTable()">查询</button></span>
            <span><button class="operation_btn bucket_add" onclick="window.location.href='/bucket/initAdd'">新建</button></span>
            <div class="l-loading" id="page_loading"></div>
            <div class="l-clear"></div>
            <div id="bucket_list"></div>
        </div>
        <div position="right" title="视频数据（点击封面可查看视频）">
            <span>
                <h4 class="remarks"></h4>
            </span>
            <div class="data_div">

            </div>
            <%--<div class="video_div">--%>
                <%--<span class="video_span">--%>
                    <%--<img src="/images/bucket/player.ico" alt=""/>--%>
                <%--</span>--%>
                <%--<img class="video_cover" src="http://img.htmleaf.com/1611/201611021538.jpg">--%>
            <%--</div>--%>
        </div>
    </div>
</body>
</html>

