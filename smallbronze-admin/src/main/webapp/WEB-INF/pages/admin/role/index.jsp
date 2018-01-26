<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/pages/inc/meta.inc" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <%@ include file="/WEB-INF/pages/inc/head.inc" %>
    <script type="text/javascript">
        var grid, menu, name, desc, status;
        $(function () {
            $("form").ligerForm();
            function itemClick(item, i) {
                if (item.id === "update") f_edit(actionID);
                if (item.id === "communication") f_communication(actionID);
                if (item.id === "delete") f_delete(actionID);
                if (item.id === "lock") f_lock(actionID, status);
                if (item.id === "authority") f_authority(actionID);

            }

            menu = $.ligerMenu({
                width: 120, items: [
                    {id: 'update', text: '编辑角色', click: itemClick, icon: 'edit'},
                    {line: true},
                    {id: 'communication', text: '关联资源', click: itemClick, icon: 'communication'},
                    {line: true},
                    {id: 'authority', text: '关联权限', click: itemClick, icon: 'role'},
                    {line: true},
                    {id: 'lock', text: '启/停用', click: itemClick, icon: 'up'},
                    {line: true},
                    {id: 'delete', text: '删除角色', click: itemClick, icon: 'delete'}
                ]
            });
            grid = $("#maingrid").ligerGrid({
                width: '100%', height: '100%',
                columns: [
                    {display: '角色名称', name: 'name', align: 'left', width: 100},
                    {
                        display: '状态', name: 'status', width: 180, render: function (item) {
                        var status = item.status;
                        if (status === 0)
                            return '<span style="color:red">停用</span>';
                        else if (status === 1)
                            return '<span style="color:green">启用</span>';
                    }
                    },
                    {
                        display: '类型', name: 'type', width: 100, render: function (item) {
                        var type = item.type;
                        if (type === 0)
                            return '一级管理员';
                        else if (type === 1)
                            return '二级管理员';
                        else
                            return "操作员";
                    }
                    },
                    {display: '描述', name: 'descn', align: 'left', width: 150}
                ],
                root: 'rows',
                record: 'total',
                pageParmName: 'pageNo',
                pagesizeParmName: 'pageSize',
                rownumbers: true,
                url: "/ajax/role/pager",
                pageSize: 20, page: 1, pageSizeOptions: [20, 50, 100],
                onContextmenu: function (row, e) {
                    actionID = row.data.id;
                    name = row.data.name;
                    status = row.data.status;
                    desc = row.data.descn;
                    menu.show({top: e.pageY, left: e.pageX});
                    return false;
                }
            });

            $("#pageloading").hide();
        });


        function f_communication(actionID) {
            var url = '/role/manage/' + actionID;
            $.ligerDialog.open({
                height: 700,
                url: url,
                width: 550,
                name: 'manage',
                title: '资源管理',
                isResize: false,
                buttons: [{
                    text: '保存关联', onclick: function () {
                        //获取字窗口值
                        document.getElementById('manage').contentWindow.f_commun(); //此写法兼容IE,FF
                        var resourceUuid = $("#resourceUuid").val();
                        postRequest('/ajax/role/saveRoleSource', {
                            "roleId": actionID,
                            "resourceUuid": $.trim(resourceUuid)
                        }, function () {
                            window.parent.location.reload();
                        },function (msg) {
                            $.ligerDialog.error(msg);
                        });
                    }
                }]
            });
        }

        function f_edit(id) {
            var url = '/role/initAdd?name=' + name + '&desc=' + desc + '&r_t=' + new Date().getTime();
            $.ligerDialog.open({
                height: 250,
                url: url,
                width: 300,
                name: 'initAdd',
                title: '角色信息',
                isResize: false,
                buttons: [{
                    text: '确认', onclick: function () {
                        //获取字窗口值
                        document.getElementById('initAdd').contentWindow.f_ok(); //此写法兼容IE,FF

                        var roleName = $("#_roleName").val();
                        var desc = $("#_roleDesc").val();
                        var param = {
                            "id": id,
                            "roleName": $.trim(roleName),
                            "roleDesc": $.trim(desc)
                        };
                        if (stringIsNull(roleName)) {
                            $.ligerDialog.warn("角色名称不能为空");
                        } else {
                            postRequest('/ajax/role/save', param, function () {
                                grid.loadData();
                                $(".l-dialog,.l-window-mask").remove();
                            }, function (msg) {
                                $.ligerDialog.error(msg);
                            });
                        }
                    }
                }]
            });
        }


        //删除客户
        function f_delete(id) {
            $.ligerDialog.confirm('确定要删除该角色?', function (yes) {
                if (yes) {
                    $.ligerDialog.waitting('正在处理中,请稍候...');
                    postRequest("/ajax/role/delete/" + id, {}, function () {
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

        function f_lock(actionID, status) {
            var param = {};
            console.log(status);
            param.status = (status === '1' ? 0 : 1);
            console.log(param);
            $.ligerDialog.confirm('确定要操作吗?', function (yes) {
                if (yes) {
                    $.ligerDialog.waitting('正在处理中,请稍候...');
                    postRequest('/ajax/role/setStatus/' + actionID, param, function () {
                        $.ligerDialog.closeWaitting();
                        grid.loadData();
                    },function (msg) {
                        $.ligerDialog.closeWaitting();
                        $.ligerDialog.error(msg);
                    });
                }
            });
        }

        function f_authority(id) {
            var url = '/buttonAuth/authorityByRole?role_id=' + id;
            $.ligerDialog.open({
                url: url,
                height: 600,
                width: 600,
                name: 'authority',
                title: '关联权限'
            });
        }


    </script>
</head>
<body style="overflow-x:hidden; padding:2px;">
<input id="_roleName" name="_roleName" type="hidden"/>
<input id="_roleDesc" name="_roleDesc" type="hidden"/>
<input id="_roleUnit" name="_roleUnit" type="hidden"/>
<input id="resourceUuid" name="resourceUuid" type="hidden"/>
<span><button class="operation_btn" id="release" onclick="f_edit('');">新增用户</button></span>
<div class="l-loading" style="display:block" id="pageloading"></div>
<div class="l-clear"></div>
<div id="maingrid"></div>
<div style="display:none;">
</div>
</body>
</html>