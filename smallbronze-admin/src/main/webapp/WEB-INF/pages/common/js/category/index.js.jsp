<%--
  Created by IntelliJ IDEA.
  User: feifan.gou
  Date: 2017/9/4
  Time: 18:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<script type="text/javascript">

    $(function () {

        renderTable();
        //绑定事件
        enterEvent($('input'), renderTable);
        $('select#type').change(renderTable);
    });

    function columnList() {

        return [
            {
                display: '操作', width: 120, render: function (row) {
                var edit = '<button class="operation_btn category_edit" onclick="f_edit(' + row.id + ')">编辑</button>';
                var _delete = '<button class="operation_btn_with_margin category_delete" onclick="f_delete(' + row.id + ')">删除</button>';
                return edit + _delete;
            }, hide: $button_authority.isHide(['category_edit', 'category_delete'])
            },
            {display: '分类名称', name: 'name', width: 100},
            {
                display: 'logo', name: 'icon', width: 80, render: function (row) { //图片显示
                return "<img src='" + row['icon'] + "' width='50' " + "onerror='imageError(this)' onmouseover='zoom(event,this.src)' onmouseleave='imgHide()' />";
            }
            },
            {display: '类型', name: 'typeName', width: 100},
            {display: '备注', name: 'remarks', width: 100,render:function (row) {

                return divWithTitle(row['remarks']);
            }},
            {display: '创建时间', name: 'createTimeFormat', width: 180},
            {display: '操作者', name: 'operatorName', width: 100}

        ];
    }

    function renderTable() {

        var grid = $('#category_list').ligerGrid({
            width: '100%', height: '100%',
            checkbox: true,
            columns: columnList(),
            root: 'rows',
            record: 'total',
            pageParmName: 'page_number',
            pagesizeParmName: 'page_size',
            sortnameParmName: "sort_name",
            sortorderParmName: "sort_order",
            rownumbers: true,
            colDraggable: true,
            url: "/ajax/category/paginate",
            parms: [
                document.getElementById('name'),
                document.getElementById('create_time'),
                document.getElementById('type')
            ],
            pageSize: 20,
            page: 1,
            pageSizeOptions: [20, 50, 100]
        });
        $("#page_loading").hide();
        return grid;
    }

    function f_delete(id) {

        $.ligerDialog.confirm("确定删除该素材分类 ?", function (ensure) {
            if (ensure) {
                postRequest('/ajax/category/delete', {id: id}, function () {
                    renderTable();
                }, function (msg) {
                    $.ligerDialog.error(msg);
                });
            }
        });
    }


    function f_add() {
        window.location.href = "/category/initAdd";
    }
    function f_edit(id) {
        window.location.href = "/category/initEdit?id=" + id;
    }
</script>
