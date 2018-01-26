<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ include file="../../inc/meta.inc" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="../../inc/head.inc" %>
<script type="text/javascript">
    $(function () {

        $('#cancel_button').click(function () {
            parent.$.ligerDialog.hide();
        });

        function checkUrl(str) {
            var strRegex = /^((https|http|ftp|rtsp|mms)?:\/\/)+[A-Za-z0-9]+\.[A-Za-z0-9]+[\/=\?%\-&_~`@[\]\':+!]*([^<>\"\"])*$/;
            var url_regex = new RegExp(strRegex);
            return url_regex.test(str);
        }

        $('#submit_button').click(function () {
            var data = {},
                tmpArray = $("#_Form").serializeArray();
            $.each(tmpArray, function() {
                data[this.name] = this.value;
            });
            if (data['versionNum'] == ""){
                $.ligerDialog.warn("版本号不能为空");
                return;
            }else{
                var ver = "[1-9]{1,2}\.+[0-9]{1,2}\.+[0-9]{1,2}$";
                var ver_regex = new RegExp(ver);
                if (!ver_regex.test(data['versionNum'])){
                    $.ligerDialog.warn("版本号格式不正确");
                    return;
                }
            }
            if (data['downUrl'] == ""){
                $.ligerDialog.warn("下载地址不能为空");
                return;
            }else{
                if(!checkUrl(data['downUrl'])){
                    $.ligerDialog.warn("下载地址格式错误");
                    return;
                }
            }
            $.ajax({
                url: '../app/saveAppVersion',
                type : 'POST',
                dataType: 'JSON',
                data:data,
                success:function(result) {
                    if(result.success) {
                        parent.$.ligerDialog.hide();
                        parent.grid.loadData();
                        parent.$(".l-dialog,.l-window-mask").remove();
                    } else{
                        var errMsg = result.errMsg;
                        if(typeof(errMsg) != 'undefined' && errMsg != '')
                            $.ligerDialog.warn(result.errMsg);
                        else
                            $.ligerDialog.warn("失败！");
                    }
                }
            });
        });

        $("#plat").change(function (){
            $.ajax({
                url: '../app/getAppVersionByPlat',
                type : 'POST',
                dataType: 'JSON',
                data:{plat:$("#plat").val()},
                success:function(result) {
                    if(result.success) {
                        var append_str = '<option  value = ""></option>';
                        var option_str = "";
                        $("#minVersion").empty();
                        $.each(result.appVersionList,function(n,obj) {
                             option_str = option_str + '<option  value ="'+obj.id+'">'+obj.versionNum+'</option>';
                       });
                       $("#minVersion").html(append_str+option_str);
                    }
                }
            });
        });
    });
</script>
</head>

<body style="padding:10px">
    <form id="_Form" name="_Form">
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
        <input type="hidden" id="_id" name="_id" value="${appVersion.id}" >
        <tr>
            <td align="right" class="l-table-edit-td">应用 :</td>
            <td align="left" class="l-table-edit-td">
                <select class="budget" name="appId" id="app" >
                    <c:if test="${not empty app_list}">
                        <c:forEach items="${app_list}" var="app">
                            <option value="${app.id}" <c:if test="${!empty appVersion and app.id eq appVersion.appId}">selected="selected"</c:if>>${app.appName}</option>
                        </c:forEach>
                    </c:if>
                </select>
            </td>
            <td align="left"><font color="red">*</font></td>
        </tr>
        <tr>
            <td align="right" class="l-table-edit-td">版本号 :</td>
            <td align="left" class="l-table-edit-td">
                <input name="versionNum" type="text" id="versionNum" validate="{required:true}" value="${appVersion.versionNum}"/>
            </td>
            <td align="left"><font color="red">*</font></td>
        </tr>
        <tr>
            <td align="right" class="l-table-edit-td">平台 :</td>
            <td align="left" class="l-table-edit-td">
                <select type="select" name="plat" id="plat" >
                    <c:if test="${not empty platList}">
                        <c:forEach  items="${platList}" var="item">
                            <option  value = "${item.code }" <c:if test="${appVersion.plat eq item.code}">selected="selected"</c:if>>${item.name }</option>
                        </c:forEach>
                    </c:if>
                </select>
            </td>
            <td align="left"><font color="red">*</font></td>
        </tr>
        <tr>
            <td align="right" class="l-table-edit-td">强制更新版本 :</td>
            <td align="left" class="l-table-edit-td">
                <select type="select" name="minVersion" id="minVersion" >
                    <option  value = ""></option>
                    <c:if test="${not empty appVersionList}">
                        <c:forEach  items="${appVersionList}" var="item">
                            <option  value = "${item.versionNum }" <c:if test="${appVersion.minVersion eq item.versionNum}">selected="selected"</c:if>>${item.versionNum }</option>
                        </c:forEach>
                    </c:if>
                </select>
            </td>
            <td align="left"></td>
        </tr>
        <tr>
            <td align="right" class="l-table-edit-td">下载地址 :</td>
            <td align="left" class="l-table-edit-td">
                <input type="text" name="downUrl" id="downUrl" style="width:400px" validate="{required:true}" value="${appVersion.downUrl}"  >
            </td>
            <td align="left"><font color="red">*</font></td>
        </tr>
        <tr>
            <td align="right" class="l-table-edit-td">更新说明 :</td>
            <td align="left" class="l-table-edit-td">
               <textarea cols="100" rows="6" class="l-textarea" name="updateDesc" id="updateDesc" style="width:400px" >${appVersion.updateDesc}</textarea>
            </td>
            <td align="left"></td>
        </tr>
        <tr>
            <td align="right" class="l-table-edit-td">备注 :</td>
            <td align="left" class="l-table-edit-td">
                <textarea cols="100" rows="6" class="l-textarea" name="remark" id="remark" style="width:400px" >${appVersion.remark}</textarea>
            </td>
        </tr>
        <tr>
        <td align="right" class="l-table-edit-td"></td>
        <td>
        <input type="button" value="提交" id="submit_button" name="submit_button" class="l-button l-button-submit" />
        <input type="button" value="取消" id="cancel_button" name="cancel_button" class="l-button l-button-test"/>
        </td>
        </tr>
    </table>
</form>
</body>
</html>