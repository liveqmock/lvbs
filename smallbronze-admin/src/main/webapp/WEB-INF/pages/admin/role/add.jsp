<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ include file="/WEB-INF/pages/inc/meta.inc" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <%@ include file="/WEB-INF/pages/inc/head.inc" %>
    <meta charset="utf-8">
    <title>用户添加</title>
    <style type="text/css">
        body {
            font-size: 12px;
        }

        .l-table-edit-td {
            padding: 4px;
        }
    </style>
    <script type="text/javascript">
        function f_ok() {
            var roleName = $("#roleName").val();
            var roleDesc = $("#roleDesc").val();
            var roleUnit = $("#roleUnit").val();
            if (roleName !== "" && roleUnit !== "") {
                $(window.parent.document).find("input[id='_roleName']").val(roleName);
                $(window.parent.document).find("input[id='_roleDesc']").val(roleDesc);
                $(window.parent.document).find("input[id='_roleUnit']").val(roleUnit);
                parent.$.ligerDialog.close();
            }
        }
    </script>
</head>

<body style="padding:10px">
<table cellpadding="0" cellspacing="0" class="l-table-edit">
    <tr>
        <td align="right" class="l-table-edit-td">角色名称:</td>
        <td align="left" class="l-table-edit-td" style="width:160px">
            <input name="roleName" id="roleName" ltype="text" value="${name}"
                   validate="{ required: true,minlength: 2,maxlength: 5}" nullText="不能为空!">
        </td>
        </td>
        <td align="left"><font color="red">*</font></td>
    </tr>
    <td align="right" class="l-table-edit-td">角色描述:</td>
    <td align="left" class="l-table-edit-td" style="width:160px">
        <input name="roleDesc" id="roleDesc" ltype="text" value="${desc}">
    </td>
    </td>
    <td align="left"></td>
</table>
<div style="display:none">
    <!--  数据统计代码 --></div>
</body>
</html>