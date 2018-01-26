<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: feifan.gou
  Date: 2017/9/7
  Time: 15:30
  To change this template use File | Settings | File Templates.
  频道首页
--%>
<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html lang="zh">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <%@ include file="/WEB-INF/pages/common/style/channel/index.css.jsp"%>
    <script src="/js/jquery/jquery-3.2.1.min.js" type="text/javascript"></script>
</head>
<body>
    <%@ include file="/WEB-INF/pages/common/page/prompt.jsp"%>
    <%@ include file="/WEB-INF/pages/common/page/loading.jsp"%>
    <%@ include file="/WEB-INF/pages/common/page/confirm.jsp"%>
    <%@ include file="/WEB-INF/pages/videos/channel/list.jsp"%>
</body>
</html>