<%--
  Created by IntelliJ IDEA.
  User: feifan.gou
  Date: 2017/9/29
  Time: 11:09
  To change this template use File | Settings | File Templates.
  视频不存在
--%>
<%@ page contentType="text/html;charset=UTF-8"%>
<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <link rel="shortcut icon" type="image/x-icon" href="http://m.daishumovie.com/copperShare/favicon.ico">
    <title>观看的视频不存在</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            outline: none;
            -webkit-user-select: none;
            -moz-user-select: none;
            -ms-user-select: none;
            -khtml-user-select: none;
            user-select: none;
            cursor: default;
            font-weight: lighter;
        }

        .center {
            margin: 0 auto;
        }

        .whole {
            width: 100%;
            height: 100%;
            line-height: 100%;
            position: fixed;
            bottom: 0;
            left: 0;
            z-index: -1000;
            overflow: hidden;
        }

        .whole img {
            width: 100%;
            height: 100%;
        }

        .mask {
            width: 100%;
            height: 100%;
            position: absolute;
            top: 0;
            left: 0;
            background: #000;
            opacity: 0.6;
            filter: alpha(opacity=60);
        }

        .b {
            width: 100%;
            text-align: center;
            height: 400px;
            position: absolute;
            top: 50%;
            margin-top: -230px
        }

        .a {
            width: 150px;
            height: 50px;
            margin-top: 30px
        }

        .a a {
            display: block;
            float: left;
            width: 150px;
            height: 50px;
            background: #fff;
            text-align: center;
            line-height: 50px;
            font-size: 18px;
            border-radius: 25px;
            color: #333
        }

        .a a:hover {
            color: #000;
            box-shadow: #fff 0 0 20px
        }

        p {
            color: #fff;
            margin-top: 40px;
            font-size: 24px;
        }

        #num {
            margin: 0 5px;
            font-weight: bold;
        }
    </style>
    <script type="text/javascript">
        var num = 6;

        function redirect() {
            num--;
            document.getElementById("num").innerHTML = num;
            if (num < 0) {
                document.getElementById("num").innerHTML = 0;
                location.href = "/index";
            }
        }

        setInterval("redirect()", 1000);
    </script>
</head>

<body onLoad="redirect();">
<div class="whole">
    <img src="http://small-bronze.oss-cn-shanghai.aliyuncs.com/image/common/admin/404_background.jpg"/>
    <div class="mask"></div>
</div>
<div class="b">
    <img src="http://small-bronze.oss-cn-shanghai.aliyuncs.com/image/common/admin/404.png" class="center"/>
    <p>
        您要观看的视频不存在了(╥╯^╰╥)<br>
        <span id="num"></span>秒后自动跳转到主页
    </p>
</div>

</body>
</html>

