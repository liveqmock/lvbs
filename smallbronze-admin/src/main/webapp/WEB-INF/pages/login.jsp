<%--
  Created by IntelliJ IDEA.
  User: feifan.gou
  Date: 2017/9/14
  Time: 17:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en" class="no-js">

<head>
    <meta charset="utf-8">
    <link rel="shortcut icon" type="image/x-icon"
          href="http://m.daishumovie.com/copperShare/favicon.ico">
    <title>小铜人后台管理系统</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!-- CSS -->
    <link rel="stylesheet" href="/plugin/login/css/reset.css">
    <link rel="stylesheet" href="/plugin/login/css/super_sized.css">
    <link rel="stylesheet" href="/plugin/login/css/style.css">
    <script src="http://apps.bdimg.com/libs/jquery/1.6.4/jquery.min.js" charset="UTF-8" type="text/javascript"></script>
    <script src="/js/common/base.js" charset="UTF-8" type="text/javascript"></script>
    <script type="text/javascript">
        $(function () {
            if (top.location !== self.location) {
                top.location = self.location;
            }
        });
    </script>
</head>

<body>
<%@ include file="common/page/prompt.jsp"%>
<div class="page-container">
    <h1>小铜人 - 登录</h1>
    <form action="" method="post">
        <div>
            <input type="text" name="username" class="username" placeholder="用户名" autocomplete="off"/>
        </div>
        <div>
            <input type="password" name="password" class="password" placeholder="密码" oncontextmenu="return false"
                   onpaste="return false"/>
        </div>
        <button id="submit" type="button">登录</button>
    </form>
    <div class="connect">
        <p>Let's login small bronze  </p>
        <p style="margin-top:20px;">登录，小铜人吧 </p>
        <br><br><br>
        <p >Copyright © 2017</p>
    </div>
</div>
<!-- Javascript -->
<script src="/plugin/login/js/super_sized.3.2.7.min.js" charset="UTF-8" type="text/javascript"></script>
<script src="/plugin/login/js/super_sized-init.js" charset="UTF-8" type="text/javascript"></script>
<%@ include file="common/js/login.js.jsp" %>
</body>

</html>
