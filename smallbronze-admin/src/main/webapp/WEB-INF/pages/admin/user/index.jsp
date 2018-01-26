<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/WEB-INF/pages/inc/meta.inc" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <%@ include file="/WEB-INF/pages/inc/head.inc" %>
    <script type="text/javascript">
        var grid;
        var menu;
        var actionID;
        $(function () {
            $("form").ligerForm();
            function itemclick(item, i) {
                if (item.id === "update") {
                    f_add(actionID);
                }
                if (item.id === "communication") {
                    f_communication(actionID);
                }
                if (item.id === "delete") {
                    f_delete(actionID);
                }
                if (item.id === "lock") {
                    f_lock(actionID);
                }

            }

            menu = $.ligerMenu({
                width: 120, items: [
                    {id: 'update', text: '编辑用户', click: itemclick, icon: 'edit'},
                    {line: true},
                    {id: 'communication', text: '关联角色', click: itemclick, icon: 'communication'},
                    {line: true},
                    {id: 'lock', text: '启/停用', click: itemclick, icon: 'up'},
                    {line: true},
                    {id: 'delete', text: '删除用户', click: itemclick, icon: 'delete'}
                ]
            });
            grid = $("#maingrid").ligerGrid({
                width: '100%', height: '100%',
                columns: [
                    {display: '用户名', name: 'username', align: 'left', width: 120},
                    {display: '真实名称', name: 'realName', align: 'left', width: 120},
                    {
                        display: '状态', name: 'status', width: 80, render: function (item) {
                        var status = item.status;
                        if (status === 0)
                            return '<span style="color:red">停用</span>';
                        else if (status === 1)
                            return '<span style="color:green">启用</span>';
                    }
                    }
                ],
                root: 'rows',
                record: 'total',
                pageParmName: 'pageNo',
                pagesizeParmName: 'pageSize',
                rownumbers: true,
                url: "/ajax/user/pagerForUser",
                parms: [document.getElementById('username')],
                pageSize: 20, page: 1, pageSizeOptions: [20, 50, 100], onRClickToSelect: true,
                onContextmenu: function (row, e) {
                    actionID = row.data.id;
                    menu.show({top: e.pageY, left: e.pageX});
                    return false;
                }
            });

            $("#pageloading").hide();
        });

        //查询
        function f_search() {
            grid.loadData();
        }

        function f_communication(actionID) {

            var url = '/user/roleList?adminId=' + actionID + '&r_t=' + new Date().getTime();
            $.ligerDialog.open({
                title: "已关联角色", isDrag: true, url: url, height: 300, width: 420,
                isResize: true, name: 'eidtFrame'
            });
        }


        function f_lock(actionID) {
            $.ligerDialog.confirm('确定要操作吗?', function (yes) {
                if (yes) {
                    $.ligerDialog.waitting('正在处理中,请稍候...');
                    postRequest('/ajax/user/editUserStatus', {id: actionID}, function () {
                        $.ligerDialog.closeWaitting();
                        grid.loadData();
                    },function (msg) {
                        $.ligerDialog.closeWaitting();
                        $.ligerDialog.error(msg);
                    });
                }
            });
        }

        function f_add(id) {
            var url = '/user/addUserIndex?id=' + id + '&r_t=' + new Date().getTime();
            $.ligerDialog.open({
                height: 250,
                url: url,
                width: 300,
                name: 'addUserIndex',
                title: '用户信息',
                isResize: false,
                buttons: [{
                    text: '确认', onclick: function () {
                        //获取字窗口值
                        document.getElementById('addUserIndex').contentWindow.f_ok(); //此写法兼容IE,FF
                        var realName = $("#_realName").val();
                        var userName = $("#_userName").val();
                        var userPassword = $("#_userPassword").val();
                        if (stringIsNull(realName)) {
                            $.ligerDialog.warn("真实名称不能为空");
                            return;
                        } else if (stringIsNull(userName)) {
                            $.ligerDialog.warn("用户名不能为空");
                            return;
                        }
                        if (stringIsNull(id)) {
                            if (stringIsNull(userPassword)) {
                                $.ligerDialog.warn("密码不能为空");
                                return;
                            }
                        }
                        var param = {
                            "id": id,
                            "realName": realName,
                            "passWord": userPassword,
                            "userName": userName
                        };
                        postRequest('/ajax/user/saveOrUpdate', param, function () {
                            grid.loadData();
                            $(".l-dialog,.l-window-mask").remove();
                        },function (msg) {
                            $.ligerDialog.error(msg);
                        });

                    }
                }
                ]
            });
        }


        //删除客户
        function f_delete(id) {
            $.ligerDialog.confirm('确定要删除该用户?', function (yes) {
                if (yes) {
                    $.ligerDialog.waitting('正在处理中,请稍候...');
                    postRequest('/ajax/user/delete/' + id, {}, function () {
                        $.ligerDialog.closeWaitting();
                        grid.loadData();
                        $.ligerDialog.success('删除成功 ');
                    },function (msg) {
                        $.ligerDialog.closeWaitting();
                        $.ligerDialog.error(msg);
                    });
                }
            });
        }
    </script>
</head>

<body style="overflow-x:hidden; padding:2px;">
<input id="_realName" name="_realName" style="width:100px" type="hidden"/>
<input id="_userName" name="_userName" style="width:100px" type="hidden"/>
<input id="_userPassword" name="_userPassword" style="width:100px" type="hidden"/>
<input id="_userUnit" name="_userUnit" style="width:100px" type="hidden"/>
<span style="float:left;margin-left:10px;">名称：<input name="username" id="username"></span>
<span style="float:left;margin-left:10px;"><button class="operation_btn" onclick="f_search()">查询</button></span>
<span><button class="operation_btn" id="release" onclick="f_add('');">新增用户</button></span>
<div class="l-loading" style="display:block" id="pageloading"></div>
<div class="l-clear"></div>
<div id="maingrid"></div>
<div style="display:none;">
</div>
</body>
</html>
