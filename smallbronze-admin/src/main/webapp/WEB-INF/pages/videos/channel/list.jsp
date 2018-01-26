<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: feifan.gou
  Date: 2017/9/7
  Time: 18:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<div class="container">
    <input type="hidden" value="" id="hover_id"/>
    <input type="hidden" value="" id="hover_name"/>
    <input type="hidden" id="all_add_right">
    <div class="row">
        <ul class="simple_filter" id="app_select">
            应&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;用：
            <c:forEach items="${app_list}" var="app">
                <li data-id="${app.id}">${app.appName}</li>
            </c:forEach>
        </ul>
    </div>
    <div class="row">
        <ul class="simple_filter">
            一级频道：
            <li class="active" data-filter="all">全部</li>
            <c:forEach items="${first_level_list}" var="item">
                <li data-filter="${item.id}" data-url="${item.url}">${item.name}</li>
            </c:forEach>
            <li class="add_first_level" id="add_first_level">+</li>
        </ul>
    </div>
    <div class="row" style="display: none">
        <ul class="multifilter">
            滤波器控制：
            <li data-multifilter="1, 2">城市</li>
            <li data-multifilter="2">乡村</li>
            <li data-multifilter="3">工业</li>
        </ul>
    </div>

    <div class="row" style="display: none">
        <ul class="sortandshuffle">
            排序 &amp; 移动 控制:
            <!-- Basic shuffle control -->
            <li class="shuffle-btn" data-shuffle>Shuffle</li>
            <!-- Basic sort controls consisting of asc/desc button and a select -->
            <li class="sort-btn active" data-sortAsc>Asc</li>
            <li class="sort-btn" data-sortDesc>Desc</li>
            <select data-sortOrder>
                <option value="domIndex">
                    Position
                </option>
                <option value="sortData">
                    Description
                </option>
            </select>
        </ul>
    </div>

    <div class="row search-row">
        关键字搜索：
        <input type="text" class="filtr-search" name="filtr-search" data-search>
    </div>

    <div class="row">
        <div class="filtr-container">
            <div class="col-xs-4 col-sm-4 col-md-3 filtr-item add-div" data-category="<c:forEach items="${first_level_list}" var="item" varStatus="status"><c:if test="${status.index == 0}">${item.id}</c:if><c:if test="${status.index != 0}">, ${item.id}</c:if> </c:forEach>" data-sort="">
                <img class="img-responsive" src="/plugin/classify/img/add.jpg" id="add_second_level">
            </div>
            <c:forEach items="${second_level_list}" var="item">
                <div class="col-xs-6 col-sm-4 col-md-3 filtr-item normal" data-category="${item.pid}" data-sort="${item.name}">
                    <span class="operation_span">
                        <img class="channel_edit" src="/plugin/classify/img/edit.png" data-id="${item.id}">
                        <img class="channel_delete" src="/plugin/classify/img/delete.png" data-id="${item.id}" data-name="${item.name}">
                    </span>
                    <img class="img-responsive" src="${item.url}" alt="暂无图片" onerror="imageError(this)">
                    <span class="item-desc" title="${item.name}">${item.name}</span>
                </div>
            </c:forEach>
        </div>
    </div>
</div>
<%@ include file="/WEB-INF/pages/common/js/videos/channel/index.js.jsp"%>
