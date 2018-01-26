<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/WEB-INF/pages/inc/meta.inc" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <%@ include file="/WEB-INF/pages/inc/head.inc" %>
    <script type="text/javascript">
        var data = []
        var availableResourceMap;
        var roleResourceMap;
        var tree;
        var treeManager = null;
        $(function () {
            availableResourceMap = new Map();
            roleResourceMap = new Map();

            <c:forEach items="${availableResources}" var="item">
            availableResourceMap.put('${item.uuid}', '${item.name}');
            </c:forEach>
            <c:forEach items="${roleResources}" var="item">
            roleResourceMap.put('${item.uuid}', '${item.name}');
            </c:forEach>

            initResource();
        });


        function initResource() {
            $.ajax({
                url: '/ajax/role/resourceQuery',
                type: 'POST',
                dataType: 'JSON',
                success: function (result) {
                    var _c = {};
                    _c["uuid"] = 1;
                    _c["parentUuid"] = "";
                    if (!roleResourceMap.isEmpty()) {
                        _c["ischecked"] = true;
                        _c["isExpand"] = true;
                    }
                    _c["parentUuid"] = "";
                    _c["text"] = '资源树';
                    data.push(_c);
                    $.each(result, function (idx, item) {
                        var _c = {};
                        if (item.parentUuid === "") {
                            _c["uuid"] = item.uuid;
                            _c["parentUuid"] = 1;
                            _c["text"] = item.name;
                            if (roleResourceMap.exists(item.uuid)) {
                                _c["ischecked"] = true;
                                _c["isExpand"] = true;
                            }
                        } else {
                            if (!availableResourceMap.exists(item.uuid)) {
                                return true;
                            }
                            if (roleResourceMap.exists(item.uuid)) {
                                _c["ischecked"] = true;
                                _c["isExpand"] = true;
                            }

                            _c["uuid"] = item.uuid;
                            _c["parentUuid"] = item.parentUuid;
                            _c["text"] = item.name;
                        }
                        data.push(_c);
                    });

                    tree = $("#tree1").ligerTree({
                        data: data,
                        checkbox: true,
                        idFieldName: 'uuid',
                        slide: false,
                        parentIDFieldName: 'parentUuid',
                        isExpand: 1
                    });
                    treeManager = $("#tree1").ligerGetTreeManager();
                }
            });
        }


        function f_commun() {
            var notes = treeManager.getChecked();
            var resourceIds = "";
            for (var i = 0; i < notes.length; i++) {
                resourceIds += notes[i].data.uuid + ",";
            }
            if (resourceIds.length === 0) {
                $.ligerDialog.warn("关联资源不能为空");
                return;
            }
            $(window.parent.document).find("input[id='resourceUuid']").val(resourceIds);
            parent.$.ligerDialog.close();
        }
    </script>
</head>
<body style="padding:10px">
<input id="resourceUuid" name="resourceUuid" type="hidden"/>
<div style="width:98%; height:100%; margin:10px; float:left; border:1px solid #ccc; overflow:auto;  ">
    <ul id="tree1"></ul>
</div>

<div style="display:none"></div>
</body>
</html>