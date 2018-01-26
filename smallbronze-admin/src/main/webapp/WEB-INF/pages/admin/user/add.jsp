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
            //_startTime 该元素为父窗口元素
            var realName = $("#realName").val();
            var userName = $("#userName").val();
            var userPassword = $("#userPassword").val();
            var userUnit = $("#userUnit").val();
            if (realName !== "" && userName !== "" && userPassword !== "" && userUnit !== "") {
                $(window.parent.document).find("input[id='_realName']").val(realName);
                $(window.parent.document).find("input[id='_userName']").val(userName);
                $(window.parent.document).find("input[id='_userPassword']").val(userPassword);
                $(window.parent.document).find("input[id='_userUnit']").val(userUnit);
                parent.$.ligerDialog.close();
            }
        }
    </script>
</head>

<body style="padding:10px">
<table cellpadding="0" cellspacing="0" class="l-table-edit">
    <tr>
        <td align="right" class="l-table-edit-td">真实名字:</td>
        <td align="left" class="l-table-edit-td" style="width:160px">
            <input name="realName" id="realName" ltype="text" value="${user.realName}"
                   validate="{ required: true}" nullText="不能为空!">
        </td>
        </td>
        <td align="left"><font color="red">*</font></td>
    </tr>
    <td align="right" class="l-table-edit-td">用户账号:</td>
    <td align="left" class="l-table-edit-td" style="width:160px">
        <input name="userName" id="userName" ltype="text" value="${user.username}" validate="{ required: true}" nullText="不能为空!">
    </td>
    </td>
    <td align="left"><font color="red">*</font></td>
    </tr>
    <c:if test="${empty user}">
        <tr>
            <td align="right" class="l-table-edit-td">密码:</td>
            <td align="left" class="l-table-edit-td" style="width:160px">
                <input name="userPassword" type="password" id="userPassword" ltype="text" value=""
                       validate="{ required: true}" nullText="不能为空!">
            </td>
            </td>
            <td align="left"><font color="red">*</font></td>
        </tr>
    </c:if>
</table>
<div style="display:none">
    <!--  数据统计代码 --></div>
</body>
</html>