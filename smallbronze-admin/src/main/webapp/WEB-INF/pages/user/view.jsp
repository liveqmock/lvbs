<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ include file="/WEB-INF/pages/inc/meta.inc" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <%@ include file="/WEB-INF/pages/inc/head.inc" %>

    <style type="text/css">
        body {
            font-size: 12px;
        }

        .l-table-edit {
            margin-top: 10px;
        }

        .l-table-edit-td {
            padding: 4px;
        }

        .l-button-submit {
            width: 80px;
            float: left;
            margin-left: 10px;
            padding-bottom: 2px;
        }

        table.branch {
            border: 1px dashed #bbb;
        }

        button.remove_table {
            width: 50px;
            margin-left: 145px;
            display: none;
        }

        button#add_table {
            width: 50px;
        }
    </style>

</head>

<body style="padding:10px">
     <table cellpadding="0" cellspacing="0" class="l-table-edit" >
        	<tr>
                <td align="right" class="l-table-edit-td">用户昵称:</td>
                <td align="left" class="l-table-edit-td"><input name="name" type="text" id="name" ltype="text" value="${user.nickName}"  ></td>

                <td align="right" class="l-table-edit-td">用户性别:</td>
                <td align="left" class="l-table-edit-td"><input name="aliasName" type="text" id="aliasName" ltype=" text" value="${user.gender}" ></td>

            </tr>
            <tr>
                <td align="right" class="l-table-edit-td">用户类型:</td>
                <td align="left" class="l-table-edit-td"><input name="aliasName" type="text" id="aliasName" ltype=" text" value="${user.typeName}" ></td>

                <td align="right" class="l-table-edit-td"></td>
                <td align="left" class="l-table-edit-td"></td>

            </tr>
            <tr>
                <td align="right" class="l-table-edit-td">是否有发送话题权限:</td>
                <td align="left" class="l-table-edit-td"><input name="aliasName" type="text" id="aliasName" ltype=" text" value="${user.isTopicAuthName}" ></td>

                <td align="right" class="l-table-edit-td">是否有回复评论权限:</td>
                <td align="left" class="l-table-edit-td"><input name="director" type="text" id="director" ltype=" text" value="${user.isReplyAuthName}"  ></td>
            </tr>
            <tr>
                <td align="right" class="l-table-edit-td">自我介绍:</td>
                <td align="left" class="l-table-edit-td" colspan="3" >
                <textarea cols="100" rows="4" class="l-textarea" name="summary" id="summary" style="width:400px" >${user.introduce}</textarea>
                </td>
            </tr>
            <tr>
                <td align="right" class="l-table-edit-td">头像:</td>
                <td align="left" class="l-table-edit-td" colspan="3" >
                    <c:if test="${null != user.avatar && '' != user.avatar}">
                        <img class="upload_image" src="${user.avatar}" width="130px" height="180px">
                    </c:if>
                    <c:if test="${null == user.avatar || '' == user.avatar}">
                        <img class="upload_image" src="http://daishumovie.oss-cn-shanghai.aliyuncs.com/img/empty_image.png" alt="" width="200px" height="110px">
                    </c:if>
                </td>
            </tr>
        </table>
</body>
</html>