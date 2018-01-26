<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: feifan.gou
  Date: 2017/7/14
  Time: 13:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<%@ include file="/WEB-INF/pages/inc/meta.inc" %>
<!DOCTYPE HTML>
<head>
    <%@ include file="/WEB-INF/pages/inc/head.inc" %>
    <link rel="stylesheet" type="text/css" href="/css/purview/bootstrap.min.css"/>
    <link rel="stylesheet" type="text/css" href="/css/purview/style.css"/>
    <link rel="stylesheet" type="text/css" href="/css/form/style.css"/>
    <script type="text/javascript">
        $(function () {
            $('.tree li:has(ul)').addClass('parent_li').find(' > span');
            $(':input[name!=resourceId]').val('');

            $('li>span').click(function () {
                var focus_css = {
                    'color': 'white',
                    'background-color': '#7B7B7B'
                };
                var blur_css = {
                    'color': '',
                    'background-color': ''
                };
                $('li>span').css(blur_css);
                $(this).css(focus_css);
                $(':text[name=className]').val($(this).data('className'));
                $(':text[name=buttonName]').val($(this).data('buttonName'));
                $(':text[name=remark]').val($(this).data('remark'));
                $(':hidden[name=id]').val($(this).data('id'));
                $(':hidden[name=parentId]').val('');
                $('button.delete').data('id', $(this).data('id'));
                $('button.delete').data('name', $(this).data('buttonName'));
            });
            $('p.submit button.submit').on('click', function () {

                var flag = true;
                $(':text').each(function () {
                   if (stringIsNull(this.value)) {
                       $(this).css('background-color','#FFEDEF');
                       flag = false;
                       return flag;
                   }
                });
                if (!flag) {
                    return;
                }
                var param = {};
                $('input', '#formElem').each(function () {
                    param[this.name] = this.value;
                });
                if (stringIsNull(param['parentId'])) {
                    param['parentId'] = 'top';
                }
                $.ligerDialog.confirm('确定提交？',function (yes) {
                    if (yes) {
                        postRequest('/ajax/buttonAuth/update', param, function () {
                            window.location.reload();
                        },function (msg) {
                            $.ligerDialog.error(msg);
                        });
                    }
                });
            });

            $('p.submit button.add').click(function () {

                var parent_id = $(':hidden[name=id]').val();
                var pre_id = $(':hidden[name=parentId]').val();
                $(":text").css('background-color','#FFFFFF');
                $(':text').val('');
                $(':hidden[name=id]').val('');
                if (stringIsNull(parent_id) && stringIsNull(pre_id)) {
                    $.ligerDialog.confirm('未选择父级按钮，将在当前菜单下新增按钮，确定？',function (yes) {
                        if(yes){
                            $(':hidden[name=parentId]').val('top');
                        }
                    });
                } else {
                    if (stringIsNull(pre_id)) {
                        $(':hidden[name=parentId]').val(parent_id);
                    }
                }
            });

            $('button.delete').click(function () {

                var id = $(this).data('id');
                var name = $(this).data('name');
                if (stringIsNull(id)) {
                    $.ligerDialog.warn("请选择要删除的数据");
                    return;
                }
                $.ligerDialog.confirm('确定删除【' + name + '】按钮？',function (yes) {
                    if (yes) {
                            $.ligerDialog.confirm('下面的子级也会被删除哦~',function (flag) {
                            if (flag) {
                                postRequest('/ajax/buttonAuth/delete', {id: id}, function () {
                                    window.location.reload();
                                },function (msg) {
                                    $.ligerDialog.error(msg);
                                });
                            }
                        });
                    }
                });
            });
            $(':text').focus(function () {
                $(this).css('background-color','#FFFFFF');
            });
        });
    </script>
    <style>
        /* 清除浮动 */
        .fix{display:inline-block;}
        .fix{display:block;}
        .fix:after{content:"";display:block;height:0px; clear:both;visibility:hidden;}
    </style>
</head>
<body>
<div class="tree well fix">
    <ul>
        <c:if test="${null != button_list && not empty button_list }">
            <c:forEach var="first" items="${button_list}">
               <li>
                   <span data-id="${first.id}" data-class-name="${first.className}" data-button-name="${first.buttonName}" data-remark="${first.remark}"><i class="icon-folder-open"></i>${first.buttonName}</span>
                   <ul>
                       <c:if test="${null != first.children && not empty first.children }">
                           <c:forEach var="second" items="${first.children}">
                               <li>
                                   <span data-id="${second.id}" data-class-name="${second.className}" data-button-name="${second.buttonName}" data-remark="${second.remark}"><i class="icon-leaf"></i>${second.buttonName}</span>
                                   <ul>
                                       <c:if test="${null != second.children && not empty second.children }">
                                           <li>
                                           <c:forEach var="third" items="${second.children}">
                                               <span data-id="${third.id}" data-class-name="${third.className}" data-button-name="${third.buttonName}" data-remark="${third.remark}"><i class="icon-leaf"></i>${third.buttonName}</span>
                                           </c:forEach>
                                           </li>
                                       </c:if>
                                   </ul>
                               </li>
                           </c:forEach>
                       </c:if>
                   </ul>
               </li>
            </c:forEach>
        </c:if>
        <%--<li>
            <span><i class="icon-folder-open"></i> Parent</span>
            <ul>
                <li>
                    <span><i class="icon-minus-sign"></i> Child</span>
                    <ul>
                        <li>
                            <span><i class="icon-leaf"></i> Grand Child</span>
                        </li>
                    </ul>
                </li>
                <li>
                    <span><i class="icon-minus-sign"></i> Child</span>
                    <ul>
                        <li>
                            <span><i class="icon-leaf"></i> Grand Child</span>

                        </li>
                        <li>
                            <span><i class="icon-minus-sign"></i> Grand Child</span>
                            <ul>
                                <li>
                                    <span><i class="icon-minus-sign"></i> Great Grand Child</span>
                                    <ul>
                                        <li>
                                            <span><i class="icon-leaf"></i> Great great Grand Child</span>
                                        </li>
                                        <li>
                                            <span><i class="icon-leaf"></i> Great great Grand Child</span>
                                        </li>
                                    </ul>
                                </li>
                                <li>
                                    <span><i class="icon-leaf"></i> Great Grand Child</span>
                                </li>
                                <li>
                                    <span><i class="icon-leaf"></i> Great Grand Child</span>
                                </li>
                            </ul>
                        </li>
                        <li>
                            <span><i class="icon-leaf"></i> Grand Child</span>
                        </li>
                    </ul>
                </li>
            </ul>
        </li>--%>
    </ul>
        <div id="steps">
            <form id="formElem">
                <fieldset class="step">
                    <input type="hidden" name="id" value=""/>
                    <input type="hidden" name="resourceId" value="${resource_id}"/>
                    <input type="hidden" name="parentId" value=""/>
                    <%--<legend>Account</legend>--%>
                    <p>
                        <label >类名</label>
                        <input name="className" type="text" />
                    </p>
                    <p>
                        <label >按钮名称</label>
                        <input name="buttonName" type="text" >
                    </p>
                    <p>
                        <label >备注</label>
                        <input name="remark" type="text"/>
                    </p>
                    <p class="submit">
                        <button class="add" style="margin-right: 20px" type="button">新建</button>
                        <button class="submit" style="margin-right: 20px" type="button">提交</button>
                        <button class="delete" type="button" >删除</button>
                    </p>
                </fieldset>
            </form>
        </div>
</div>
</body>
</html>