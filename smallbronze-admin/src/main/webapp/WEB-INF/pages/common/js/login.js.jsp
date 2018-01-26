<%--
  Created by IntelliJ IDEA.
  User: feifan.gou
  Date: 2017/9/14
  Time: 17:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8"%>
<script>
    (function () {
        $("input[name=username]").trigger('focus');
        $("#submit").bind('click',doLogin);
        $("input[name=username],input[name=password]").live('keydown', function (key) {
            if (key.keyCode === 13) {
                doLogin();
            }
        });
        function doLogin() {
            var username = $("input[name=username]").val();
            var password = $("input[name=password]").val();
            if (stringIsNull(username) || stringIsNull(password)) {
                $.prompt("用户名或密码不能为空~");
                return false;
            } else {
                $("#submit").unbind('click');
                postRequest('/ajax/admin/login', {
                    admin_name: username,
                    password: password
                },function () {
                    window.location.href = '/';
                },function () {
                    $("#submit").bind('click',doLogin);
                    $.prompt("用户名或密码错误");
                });
            }
        }
        window.onload = function () {
            $(".connect p").eq(0).animate({"left": "0%"}, 600);
            $(".connect p").eq(1).animate({"left": "0%"}, 400);
            $(".connect p").eq(2).animate({"left": "0%"}, 200);
        };
    })(jQuery);
</script>
