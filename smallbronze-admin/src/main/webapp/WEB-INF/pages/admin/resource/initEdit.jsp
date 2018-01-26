<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ include file="/WEB-INF/pages/inc/meta.inc" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<%@ include file="/WEB-INF/pages/inc/head.inc" %>
    <script type="text/javascript">
        function f_ok(){
            $(window.parent.document).find("input[id='_itemName']").val($(":text[name=itemName]").val());
            $(window.parent.document).find("input[id='_path']").val($(":text[name=path]").val());
            $(window.parent.document).find("input[id='_type']").val($(':radio[name=type]:checked').val());
            $(window.parent.document).find("input[id='_descn']").val($(":text[name=description]").val());
            parent.$.ligerDialog.close();
        }
        $(function () {

            $(':text[name=name]').blur(function () {

                var description = $(':text[name=descn]').val();
                if(!description && $(this).val()) {
                    $(':text[name=descn]').val($(this).val());
                }
            });
            $('#submit_button_add').click(function () {

                var param = {};
                if(parameter(param)){
                    postRequest('/ajax/resource/save', param, function () {
                        // 强制刷新页面
                        window.parent.location.reload();
                        $(".l-dialog,.l-window-mask").remove();
                    }, function (msg) {
                        $.ligerDialog.warn(msg);
                    });
                }

            });
            $('#submit_button_update').click(function () {

                var param = {};
                if(parameter(param)){
                    postRequest('/ajax/resource/update', param, function () {
                        // 强制刷新页面
                        window.parent.location.reload();
                        $(".l-dialog,.l-window-mask").remove();
                    }, function (msg) {
                        $.ligerDialog.warn(msg);
                    });
                }
            });
            $('#cancel_button').click(function () {

                parent.$.ligerDialog.hide();
            });

            function parameter(param) {
                $(':text,:hidden', '#form1').each(function () {
                    param[this.name] = this.value;
                });
                if (!param['name']) {
                    $.ligerDialog.warn("资源名称不能为空");
                    return ;
                }
                var type = $(':radio[name=type]:checked').val();
                if (!type) {
                    $.ligerDialog.warn("类型不能为空");
                    return;
                }
                param['type'] = type;
                return true;
            }
        });
    </script>
    <style type="text/css">
        body {
            font-size: 12px;
        }
        .l-table-edit-td {
            padding: 4px;
        }
        .l-button-submit, .l-button-test {
            width: 80px;
            float: left;
            margin-left: 10px;
            padding-bottom: 2px;
            margin-top: 70px;
        }
    </style>

</head>

<body style="padding:10px">
    <form name="form1" class="form" method="post" id="form1">
        <c:if test="${null == resource}">
            <input name="parentUuid" type="hidden" value="${parentUuid}">
            <input name="parentsUuids" type="hidden" value="${parentsUuids}">
        </c:if>
        <input name="uuid" type="hidden" value="${resource.uuid}">
        <table cellpadding="0" cellspacing="0" class="l-table-edit" >
        	<tr>
                <td align="right" class="l-table-edit-td">资源名称:</td>
                <td align="left" class="l-table-edit-td">
                    <input name="name" type="text" value="${resource.name}" validate="{required:true}" />
                </td>
                <td align="left"><font color="red">*</font></td>
            </tr>
            <tr>
                <td align="right" class="l-table-edit-td">资源路径:</td>
                <td align="left" class="l-table-edit-td">
                    <input name="path" type="text" id="path" value="${resource.path}" />
                </td>
                <td align="left"></td>
            </tr>
            <tr>
                <td align="right" class="l-table-edit-td">类型:</td>
                <td align="left" class="l-table-edit-td">
                    <input name="type" type="radio" value="0" <c:if test="${resource.type == 0}">checked</c:if>>菜单</input>
                    <input name="type" type="radio" value="1" <c:if test="${resource.type == 1}">checked</c:if>>功能</input>
                </td>
                <td align="left"><font color="red">*</font></td>
            </tr>
            <tr>
                <td align="right" class="l-table-edit-td">描述:</td>
                <td align="left" class="l-table-edit-td">
                    <input name="descn" type="text"  value="${resource.descn}" />
                </td>
                <td align="left"></td>
            </tr>
        </table>
    </form>
    <c:if test="${null == resource}">
        <input type="submit" value="提交" id="submit_button_add" class="l-button l-button-submit" />
    </c:if>
    <c:if test="${null != resource}">
        <input type="submit" value="提交" id="submit_button_update" class="l-button l-button-submit" />
    </c:if>
    
    <input type="button" value="取消" id="cancel_button" class="l-button l-button-test"/>
</body>
</html>