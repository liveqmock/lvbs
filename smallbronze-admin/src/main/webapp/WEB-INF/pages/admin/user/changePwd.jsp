<%@ page pageEncoding="utf-8" %>
<html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ include file="/WEB-INF/pages/inc/meta.inc" %>
<!DOCTYPE HTML>
<head>
    <%@ include file="/WEB-INF/pages/inc/head.inc" %>
    <style type="text/css">

        .alert_width2 {
            margin: 20px 0;
            font-size: 18px;
            padding: 10px
        }

        .alert_width2 .button_send input[type=button] {
            background-position: 0 -227px
        }

        .alert_width2 .title {
            line-height: 2em;
            text-align: center;
            font-size: 23px
        }
        input{
            height: 25px;
            -webkit-border-radius: 3px;
        }
        span.error_msg{
            color:red;
            font-size: 14px;
            margin-left: 13px;
        }
        ul.bor1dash{
            padding: 23px;
        }
    </style>
    <script>
        $(function () {
            $(':password[name=old_password],:password[appName=new_password],:password[appName=confirm_password]').focus(function () {
                $(this).parent().next().html('');
            });
            $(':password[name=old_password]').blur(function () {

                var ths = this;
                if (stringIsNull(ths.value)) {
                    $(ths).parent().next().html('必填');
                } else {
                    postRequest('/ajax/admin/checkOldPwd', {oldPwd: ths.value},null,function (msg) {
                        $(ths).parent().next().html(msg);
                    });
                }
            });
            $(':password[name=new_password]').blur(function () {

                var ths = this;
                if (stringIsNull(ths.value)) {
                    $(ths).parent().next().html('必填');
                } else {
                    if(!(ths.value.length >=6 && ths.value.length <= 20)){
                        $(ths).parent().next().html('密码长度[6-20]');
                    }
                }
            });
            $(':password[name=confirm_password]').blur(function () {

                var ths = this;
                if (stringIsNull(ths.value)) {
                    $(ths).parent().next().html('必填');
                } else {
                    if ($(':password[name=new_password]').val() != ths.value) {
                        $(ths).parent().next().html('两次密码不一致');
                    }
                }
            });
            $('button#change_pwd_submit').click(function () {
                var param = {
                    oldPwd:$(':password[name=old_password]').val(),
                    newPwd:$(':password[name=new_password]').val(),
                    confirmPwd:$(':password[name=confirm_password]').val()
                };

                postRequest('/ajax/admin/changePwd', param, function () {
                    parent.location.href = '/admin/login'
                },function (msg) {
                    $.ligerDialog.error(msg)
                });
            });
        });
    </script>
</head>
<body>

<div class="alert alert_width2">
    <div class="bor1dash p050 title">修改密码</div>
    <ul class="bor1dash p050">
        <div>旧密码：<span>&nbsp;&nbsp;&nbsp;<input type="password" name="old_password"></span><span class="error_msg"></span></div><br/>
        <div>新密码：<span>&nbsp;&nbsp;&nbsp;<input type="password" name="new_password"></span><span class="error_msg"></span></div><br/>
        <div>确认密码：<span><input type="password" name="confirm_password"></span><span class="error_msg"></span></div><br/><br/>
        <button style="width: 80px;" id="change_pwd_submit" class="operation_btn">提交</button>
    </ul>
</div>
</body>
</html>
