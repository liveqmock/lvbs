<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/WEB-INF/pages/inc/meta.inc" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <%@ include file="/WEB-INF/pages/inc/head.inc" %>
    <script type="text/javascript">
        jQuery(function () {
            $('button.allot').click(function () {
                var role_ids = [];
                $(':checkbox:checked').each(function () {
                    role_ids.push(this.value);
                });
                if (role_ids.length === 0) {
                    $.ligerDialog.warn("请选择要分配的角色");
                    return;
                }
                $.ligerDialog.confirm("确定提交?", function (yes) {
                    if (yes) {
                        $.ligerDialog.waitting("正在处理,请稍后...")
                        var param = {
                            admin_id:$('#adminId').val(),
                            role_ids:role_ids
                        };
                        postRequest("/ajax/user/allotRole", param, function () {
                            $.ligerDialog.closeWaitting();
                            parent.$.ligerDialog.close();
                            parent.$(".l-dialog,.l-window-mask").remove();
                        },function (msg) {
                            $.ligerDialog.closeWaitting();
                            $.ligerDialog.error(msg);
                        });
                    }
                });
            });
        });

    </script>
</head>

<body style="overflow-x:hidden; padding:2px;">
<input id="_realName" name="_realName" style="width:100px" type="hidden"/>
<input id="_userName" name="_userName" style="width:100px" type="hidden"/>
<input id="_userPassword" name="_userPassword" style="width:100px" type="hidden"/>
<input id="_userUnit" name="_userUnit" style="width:100px" type="hidden"/>
<br/>
<span style="float:left;margin-left:10px;">当前用户名：${admin.username}</span><br/><br/>
<span style="float:left;margin-left:10px;">
    <c:forEach items="${role_list}" var="role">
    <c:if test="${role.including}">
      &nbsp;&nbsp;<input type="checkbox" value="${role.id}" checked />${role.name}
    </c:if>
    <c:if test="${!role.including}">
        &nbsp;&nbsp<input type="checkbox" value="${role.id}" />${role.name}
    </c:if>
    </c:forEach>
</span> <br/>
<input type="hidden" id="adminId" name="adminId" value="${admin.id}">
<span style="float:left;margin:20px 0 0 10px">
    <button class="operation_btn allot" >提交</button>
</span>
<div class="l-clear"></div>
<div class="l-clear"></div>
<div id="maingrid"></div>
<div style="display:none;">
</div>
</body>
</html>
