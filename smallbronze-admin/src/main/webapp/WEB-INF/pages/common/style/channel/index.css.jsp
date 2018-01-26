<%--
  Created by IntelliJ IDEA.
  User: feifan.gou
  Date: 2017/9/7
  Time: 15:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<link rel="stylesheet" type="text/css" charset="UTF-8"
      href="http://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css"/>
<style type="text/css">

    div.container {
        background: #fff;
        padding: 2em;
    }

    .filtr-item {
        padding: 10px;
        border-radius: 10px;
    }

    .item-desc {
        background-color: rgba(0, 0, 0, 0.5);
        color: white;
        position: absolute;
        text-transform: uppercase;
        text-align: center;
        padding: 5px;
        z-index: 1;
        bottom: 20px;
        left: 24px;
        right: 25px;
        font-size: 12px;
        border: 1px solid;
        border-radius: 25px;
        overflow: hidden;
        text-overflow: ellipsis;
        white-space: nowrap;
    }

    .operation_span {
        background-color: #F5F5F5;
        color: white;
        position: absolute;
        text-transform: uppercase;
        text-align: center;
        /*padding: 5px;*/
        z-index: 1;
        right: 25px;
        left: 24px;
        border: 1px solid;
        border-radius: 11px;
        top: 20px;
        opacity: 0.8;
        display: none;
    }

    /* Filter controls */
    .simple_filter li, .multifilter li, .sortandshuffle li {
        color: white;
        cursor: pointer;
        display: inline-block;
        text-transform: uppercase;
        padding: 7px 20px;
        border: 2px solid;
        border-radius: 15px;
    }

    .simple_filter li {
        background-color: #585858;
    }

    .simple_filter li.active {
        background-color: #6495ED;
    }

        /*background-color: #4EEE94;*/

    .multifilter li {
        background-color: #4b9eff;
    }

    .multifilter li.active {
        background-color: #2265b4;
    }

    /* Shuffle and sort controls */
    select {
        padding: 1rem 1rem;
        text-transform: uppercase;
    }

    .sortandshuffle .shuffle-btn {
        background-color: #dec800;
    }

    .sortandshuffle .sort-btn {
        background-color: #de0000;
    }

    .sortandshuffle .sort-btn.active {
        background-color: #9d0000;
    }

    /* Search control */
    .search-row {
        margin-left: 1.5rem;
    }

    .filtr-search {
        padding: 0.5rem;
        text-transform: uppercase;
        border: 1px solid;
        border-radius: 5px;
    }

    body, html {
        font-size: 100%;
        padding: 0;
        margin: 0;
    }

    /* Reset */
    *,
    *:after,
    *:before {
        -webkit-box-sizing: border-box;
        -moz-box-sizing: border-box;
        box-sizing: border-box;
    }

    /* Clearfix hack by Nicolas Gallagher: http://nicolasgallagher.com/micro-clearfix-hack/ */
    .clearfix:before,
    .clearfix:after {
        content: " ";
        display: table;
    }

    .clearfix:after {
        clear: both;
    }

    body {
        font-family: "Microsoft YaHei", "Segoe UI", "Lucida Grande", Helvetica, Arial, sans-serif;
        /*background-color: #DCDCDC;*/
    }

    img#add_second_level {
        cursor: pointer;
    }

    span.operation_span > img {
        cursor: pointer;
    }

    span.operation_span > img.channel_delete {
        width: 18px;

    }

    span.operation_span > img.channel_edit {
        width: 25px;

    }

    img.img-responsive {
        border: 2px solid;
        border-radius: 16px;
    }
</style>