<%--
  Created by IntelliJ IDEA.
  User: feifan.gou
  Date: 2017/8/31
  Time: 10:16
  To change this template use File | Settings | File Templates.
  错误信息提示
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<style>
    .error-message p {
        background: #e94b35;
        color: #ffffff;
        font-size: 14px;
        text-align: center;
        -webkit-font-smoothing: antialiased;
        -moz-osx-font-smoothing: grayscale;
        border-radius: .25em;
        padding: 15px;
    }

    .error-message {
        width: 100%;
        z-index: 200;
        position: fixed;
        display: none;
        top:0;
    }
</style>
<div class="error-message">
    <p></p>
</div>
<script type="text/javascript">
    $.prompt = function(info) {

        if ($('div.error-message').is(':hidden')) {
            $('div.error-message>p').html(info);
            $('div.error-message').slideDown()
            setTimeout(function () {
                $('div.error-message').slideUp();
            }, 2000);
        }
    }
</script>
