<%--
  Created by IntelliJ IDEA.
  User: feifan.gou
  Date: 2017/10/10
  Time: 11:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<style type="text/css">
    .upload_icon {
        cursor: pointer;
    }

    .upload_local_div_backup, .upload_remote_div_backup, .upload_crawler_div_backup {
        border: 1px dashed #bbb;
        padding: 8px;
        display: none;
    }

    .data_div {
        border: 1px dashed #bbb;
        padding: 8px;
    }

    .upload_local_div, .upload_crawler_div {

        display: none;
    }

    input[name=title] {
        margin-bottom: 25px;
    }

    span.add_button {
        float: right;
    }
    span.add_button>img.add_img {
        width: 25px;
        cursor: pointer;
    }
    div.remove_button {
        margin: -4px 0 -7px 560px !important;
    }
    div.remove_button>img {
        width: 25px;
        cursor: pointer;
    }
    #upload_progress {
        display: none;
    }
    #upload_progress>p {
        width: 20%;
        margin-bottom: 10px;
        font-weight: bold;
    }
</style>
