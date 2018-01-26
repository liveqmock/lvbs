<%--
  Created by IntelliJ IDEA.
  User: feifan.gou
  Date: 2017/7/15
  Time: 13:43
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<%@ include file="/WEB-INF/pages/inc/meta.inc" %>
<!DOCTYPE HTML>
<head>
    <%@ include file="/WEB-INF/pages/inc/head.inc" %>
    <link rel="stylesheet" type="text/css" href="/css/purview/bootstrap.min.css"/>
    <link rel="stylesheet" type="text/css" href="/css/purview/style.css"/>
    <link rel="stylesheet" type="text/css" href="/css/switch/style.css"/>
    <script type="text/javascript">
        $(function () {
            $('.tree li:has(ul)').addClass('parent_li');
            $('.tree li.parent_li > span').on('click', function (e) {
                var children = $(this).parent('li.parent_li').find(' > ul > li');
                var sibling = $(this).parent('li.parent_li').siblings('li.parent_li').find(' > ul > li')
                if (children.is(":visible")) {
                    children.hide('fast');
                } else {
                    children.show('fast');
                    sibling.hide('fast');
                }
                e.stopPropagation();
            });
            $(':checkbox', 'div.tree').on('change', function () {
                if (!$(this).prop('checked')) {
                    //子级全部关掉
                    $(this).siblings('ul').find(':checkbox').prop('checked',false);
                    if (collectionIsEmpty($(this).parents('ul').eq(0).find(':checkbox:checked'))) {
                        $(this).parents('ul').eq(0).siblings(':checkbox').prop('checked',false);
                    }
                } else {
                    $(this).siblings('ul').find(':checkbox').prop('checked','checked');
                    $(this).parents('ul').siblings(':checkbox').prop('checked','checked');
                }
            });
            $('#submit_authority').click(function () {

                $.ligerDialog.confirm("确定提交？",function (yes) {

                    if (yes) {
                        var param = {},role_list = [];
                        $(':checkbox:checked', 'div.tree').each(function () {
                            role_list.push($(this).data('id'));
                        });
                        if (collectionIsEmpty(role_list)) {
                            $.ligerDialog.error('请选择数据');
                            return;
                        }
                        param['button_ids'] = role_list;
                        param['roleId'] = $(':hidden[name=role_id]').val();
                        $.ligerDialog.waitting('正在处理...请稍后');
                        postRequest('/ajax/buttonAuth/save', param, function () {
                            window.location.reload();
                        },function (msg) {
                            $.ligerDialog.closeWaitting();
                            $.ligerDialog.error(msg);
                        });
                    }
                });
            });
            $('span.top_menu,span.function_menu').click(function () {
                var focus_css = {
                    'color': 'white',
                    'background-color': '#7B7B7B'
                };
                var blur_css = {
                    'background-color':'',
                    'color':''
                };
                $('span.top_menu,span.function_menu').css(blur_css).removeClass('chosen');
                $(this).css(focus_css).addClass('chosen');
            });
            $('#open_all').click(function () {

                $('ul > li').show('fast');
            });
            $('#close_all').click(function () {

                $('ul[class!=top] > li').hide('fast');
            });
            $('#open').click(function () {

                $('span.top_menu.chosen,span.function_menu.chosen').parent('li.parent_li').find(' ul > li').show('fast');
            });
            $('#close').click(function () {

                $('span.top_menu.chosen,span.function_menu.chosen').parent('li.parent_li').find(' ul > li').hide('fast');
            });
        });
    </script>
    <style type="text/css">
        li.parent_li{
            width: 80%;
        }
        #refresh{
            margin-left: 133px;
            position: fixed;
        }
        #submit_authority{
            margin-left: 190px;
            position: fixed;
        }
    </style>
</head>
<body>
<input type="hidden" value="${role_id}" name="role_id"/>
<div class="button_div">
</div>
<div class="tree well fix">
    <div class="controller">
        <button class="operation_btn" id="open">展开</button>
        <button class="operation_btn" id="close">关闭</button>
        <button class="operation_btn" id="open_all">展开全部</button>
        <button class="operation_btn" id="close_all">关闭全部</button>
        <button class="operation_btn" id="refresh" onclick="window.location.reload()">刷新</button>
        <button class="operation_btn" id="submit_authority">提交</button>
    </div>
    <ul style="float: inherit;width: 100%" class="top">
        <c:if test="${null != button_list && not empty button_list }">
            <c:forEach var="first" items="${button_list}" varStatus="first_status">
                <li>
                    <span class="top_menu"><i class="icon-folder-open"></i>${first.buttonName}</span>
                        <c:if test="${null != first.children && not empty first.children }">
                            <ul style="width: 100%;">
                            <c:forEach var="second" items="${first.children}" varStatus="second_status">
                                <li style="display: none">
                                    <span class="function_menu"><i class="icon-leaf"></i>${second.buttonName}</span>
                                    <c:if test="${null != second.children && not empty second.children }">
                                        <ul >
                                            <c:forEach var="third" items="${second.children}" varStatus="third_status">
                                                <li style="display: none">
                                                    <span><i class="icon-leaf"></i>${third.buttonName}</span>
                                                    <input data-id="${third.id}" type="checkbox" class="switch" <c:if test="${third.has}">checked="checked"</c:if>
                                                           id="switch_${first_status.index}_${second_status.index}_${third_status.index}"/><label style="margin-bottom: -6px;" for="switch_${first_status.index}_${second_status.index}_${third_status.index}"></label>
                                                    <c:if test="${null != third.children && not empty third.children }">
                                                        <ul >
                                                            <c:forEach var="fourth" items="${third.children}" varStatus="fourth_status">
                                                            <li style="display: none">
                                                                <span><i class="icon-leaf"></i>${fourth.buttonName}</span>
                                                                <input data-id="${fourth.id}" type="checkbox" class="switch" <c:if test="${fourth.has}">checked="checked"</c:if>
                                                                       id="switch_${first_status.index}_${second_status.index}_${third_status.index}_${fourth_status.index}"/><label style="margin-bottom: -6px;" for="switch_${first_status.index}_${second_status.index}_${third_status.index}_${fourth_status.index}"></label>
                                                            </li>
                                                            </c:forEach>
                                                        </ul>
                                                    </c:if>
                                                </li>
                                            </c:forEach>
                                        </ul>
                                    </c:if>
                                </li>
                            </c:forEach>
                            </ul>
                    </c:if>
                </li>
            </c:forEach>
        </c:if>
    </ul>
</div>
</body>
</html>
