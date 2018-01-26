<%--
  Created by IntelliJ IDEA.
  User: feifan.gou
  Date: 2017/10/23
  Time: 17:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<style>
    #view_task {
        height: 100%;
        width: 100%;
        background-color: #EAEAEA;
        position: fixed;
        top: 0;
        z-index: 999;
        display: none;
    }
    #view_content{
        margin-left: 20%;
        margin-top: 20%;
        color: #0e0e0e;
    }
</style>
<div id="view_task">
    <span id="view_close" class="close-span" onclick="hideView()"><img src="/plugin/form/img/close.ico"></span>
    <div id="view_content">

    </div>
</div>
<script>
    function showView(content) {
        $('#view_content').html(content);
        $('#view_task').slideDown();
    }
    function hideView() {
        $('#view_task').slideUp();
    }
</script>
