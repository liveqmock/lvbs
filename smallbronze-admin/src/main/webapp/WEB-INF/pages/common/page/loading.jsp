<%--
  Created by IntelliJ IDEA.
  User: feifan.gou
  Date: 2017/8/31
  Time: 15:00
  To change this template use File | Settings | File Templates.
  遮罩
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<style>
    #common_mask {
        height: 100%;
        width: 100%;
        background-color: #000;
        position: fixed;
        top: 0;
        -moz-opacity: 0.5;
        opacity: 0.5;
        z-index: 999;
        display: none;
    }
    #common_mask img {
        margin-top: 20%;
    }
</style>
<div id="common_mask">
    <center><img src="/plugin/form/img/loading.gif"></center>
</div>
<script>
    function loading() {
        $('#common_mask').show();
    }
    function loadingStop() {
        $('#common_mask').hide();
    }
</script>