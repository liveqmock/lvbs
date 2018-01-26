<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/WEB-INF/pages/inc/meta.inc" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <%@ include file="/WEB-INF/pages/inc/head.inc" %>
    <script type="text/javascript">
        var menu,actionNodeID,parentsUuids,id;
        var data = [];
        function itemClick(item, i) {
            if (item.id === "add") {
                f_add(actionNodeID, parentsUuids);
            }
            if (item.id === "edit") {
                f_edit(actionNodeID, parentsUuids);
            }
            if (item.id === "delete") {
                f_remove(actionNodeID, parentsUuids);
            }
            if (item.id === "authority") {
                f_authority(id);
            }
        }
        $(function () {
            $.ajax({
                url: '/ajax/resource/treeList',
                type: 'POST',
                dataType: 'JSON',
                success: function (result) {
                    var _c = {};
                    _c["uuid"] = -1;
                    _c["parentUuid"] = "";
                    _c["parentsUuids"] = "";
                    _c["text"] = '资源树';
                    _c["isExpand"] = true;
                    data.push(_c);
                    $.each(result, function (idx, item) {
                        var _c = {};
                        if (item.parentUuid === "") {
                            _c["uuid"] = item.uuid;
                            _c["parentUuid"] = -1;
                            _c["parentsUuids"] = item.parentsUuids;
                            _c["text"] = item.name;
                        } else {
                            _c["uuid"] = item.uuid;
                            _c["parentUuid"] = item.parentUuid;
                            _c["parentsUuids"] = item.parentsUuids;
                            _c["text"] = item.name;
                        }
                        _c['type'] = item.type;
                        _c['id'] = item.id;
                        data.push(_c);
                    });

                    tree = $("#tree1").ligerTree({
                        data: data,
                        checkbox: false,
                        idFieldName: 'uuid',
                        slide: false,
                        parentIDFieldName: 'parentUuid',
                        isExpand: 1,
                        onContextmenu: function (node, e) {
                            var menuList = [
                                {id: 'add', text: '增加', click: itemClick, icon: 'add'},
                                {line: true},
                                {id: 'edit', text: '修改', click: itemClick, icon: 'edit'},
                                {line: true},
                                {id: 'delete', text: '删除', click: itemClick, icon: 'delete'}
                            ];
                            if (node.data.type === 1) {
                                menuList.push(
                                    {
                                        line: true
                                    },
                                    {
                                        id: 'authority',
                                        text: '权限',
                                        click: itemClick,
                                        icon: 'role'
                                    });
                            }
                            menu = $.ligerMenu({
                                top: 100, left: 100, width: 120, items: menuList
                            });
                            actionNodeID = node.data.uuid;
                            id = node.data.id;
                            parentsUuids = node.data.parentsUuids ? (node.data.parentsUuids + "," + node.data.uuid) : node.data.uuid;
                            menu.show({top: e.pageY, left: e.pageX});
                            return false;
                        }
                    });
                    treeManager = $("#tree1").ligerGetTreeManager();
                }
            });
        });

        function f_add(actionNodeId, parentsUuids) {
            var url = '/resource/toEditResource?parentUuid=' + actionNodeId + '&parentsUuids=' + parentsUuids + '&r_t=' + new Date().getTime();
            $.ligerDialog.open({
                url: url,
                height: 300,
                width: 400,
                name: 'add_resource',
                title: '添加资源'
            });
        }
        function f_edit(actionNodeId) {
            var url = '../resource/toEditResource?uuid=' + actionNodeId + '&r_t=' + new Date().getTime();
            $.ligerDialog.open({
                url: url,
                height: 300,
                width: 400,
                name: 'edit_resource',
                title: '编辑资源'
            });
        }
        function f_authority(id) {
            var url = '/buttonAuth/index?resource_id=' + id;
            $.ligerDialog.open({
                url: url,
                height: 600,
                width: 800,
                name: 'authority',
                title: '权限'
            });
        }

        function f_remove(actionNodeId) {
            if (actionNodeId === 1) {
                return;
            }
            var uuids = "'" + actionNodeId + "'";
            $.each(data, function (idx, item) {
                if (item.parentsUuids && item.parentsUuids.indexOf(actionNodeId) >= 0) {
                    uuids = uuids + ",'" + item.uuid + "'";
                }
            });
            $.ligerDialog.confirm('删除后其子级也都会被删除,确认删除吗?', function (yes) {
                if (yes) {
                    $.ligerDialog.waitting('正在处理中,请稍候...');
                    postRequest('/ajax/resource/delete', {"uuids": uuids}, function () {
                        window.location.reload();
                    }, function (msg) {
                        $.ligerDialog.warn(msg);
                        $.ligerDialog.closeWaitting();
                    });
                }
            });
        }
    </script>
</head>
<body style="padding:10px">
<div class="submit_input">
    <input name="name" type="hidden"/>
    <input name="path" type="hidden"/>
    <input name="type" type="hidden"/>
    <input name="descn" type="hidden"/>
</div>
<div style="width:98%; height:100%; margin:10px; float:left; border:1px solid #ccc; overflow:auto;  ">
    <ul id="tree1"></ul>
</div>
</body>
</html>