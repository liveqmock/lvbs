<%--
  Created by IntelliJ IDEA.
  User: feifan.gou
  Date: 2017/9/4
  Time: 18:41
  To change this template use File | Settings | File Templates.
  素材列表js
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<script>
    var current_tab ;
    $(function () {

        $("#material_tab").ligerTab({
            onBeforeSelectTabItem: function (tabId) {

                $("#page_loading").show();
                $('#category_id').html('');
                $('#category_type').val(tabId.substr(tabId.length - 1));
                categoryType();
                renderTable();
            }, onAfterSelectTabItem: function (tabId) {

            }
        });
        //自动点击tab页
        $('li[tabid=tabitem' + $('#category_type').val() + ']').click();
        //绑定事件
        enterEvent($(':text[name=name]'), loadingTb);
        $('select#app_id,select#is_on_shelf').change(loadingTb);
    });

    function loadingTb() {
        if(current_tab){
            current_tab.loadData();
        }
    }

    function categoryType() {

        var categoryType = $('#category_type').val();
        $.getJSON('/ajax/material/categoryType', {category_type: categoryType}, function (data) {
            if (data) {
                var categoryIdSelect = $('#category_id');
                $('<option/>', {
                    value: '',
                    html: '全部'
                }).appendTo(categoryIdSelect);
                $(data).each(function () {

                    $('<option/>', {
                        value: this['id'],
                        html: this['name']
                    }).appendTo(categoryIdSelect);
                });
            }
        });
    }

    function columnList() {

        return [
            {
                display: '操作', width: 150, render: function (row) {
                var edit = '<button class="operation_btn material_edit" onclick="f_edit(' + row.id + ')">编辑</button>';
                var _delete = '<button class="operation_btn_with_margin material_delete" onclick="f_delete(' + row.id + ')">删除</button>';
                var off_shelf = '<button class="operation_btn_with_margin material_off_shelf" onclick="offShelf(' + row.id + ')">下架</button>';
                var on_shelf = '<button class="operation_btn_with_margin material_on_shelf" onclick="onShelf(' + row.id + ')">上架</button>';
                var button_html = edit + _delete;
                if (row['isOnShelf'] === 0) {
                    button_html += on_shelf;
                } else {
                    button_html += off_shelf;
                }
                return button_html;
            }, hide: $button_authority.isHide(['material_edit', 'material_delete', 'material_off_shelf', 'material_on_shelf'])
            },
            {display: '素材名称', name: 'name', width: 120, align: "left"},
            {
                display: 'logo', name: 'icon', width: 80, render: function (row) { //图片显示
                return "<img src='" + row['icon'] + "' width='50' " + "onerror='imageError(this)' onmouseover='zoom(event,this.src)' onmouseleave='imgHide()' />";
            }
            },
            {
                display: '预览', name: 'previewUrl', width: 80,  render: function (row) { //图片显示
                return "<img src='" + row['previewUrl'] + "' width='50' " + "onerror='imageError(this)' onmouseover='zoom(event,this.src)' onmouseleave='imgHide()' />";
            }
            },
            {display: '时长/秒', name: 'duration', width: 100},
            {
                display: '素材文件', name: 'contentPath', width: 100, render: function (row) {
                return '<a href="' + row['contentPath'] + '" target="_blank">文件下载</a>';
            }
            },
            {display: '状态', name: 'shelfStatus', width: 100},
            {display: '创建时间', name: 'createTimeFormat', width: 180},
            {display: '更新时间', name: 'updateTimeFormat', width: 180},
            {display: '操作者', name: 'operatorName', width: 100}

        ];
    }

    function renderTable() {

        current_tab = $('#tab_'+$('#category_type').val()).ligerGrid({
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
            url: "/ajax/material/paginate",
            parms: [
                document.getElementById('category_id'),
                document.getElementById('app_id'),
                document.getElementById('name'),
                document.getElementById('create_time'),
                document.getElementById('category_type'),
                document.getElementById('is_on_shelf')
            ],
            pageSize: 20,
            page: 1,
            pageSizeOptions: [20, 50, 100]
        });
        $("#page_loading").hide();
    }

    function f_delete(id) {

        $.ligerDialog.confirm("确定删除该素材 ?", function (ensure) {
            if (ensure) {
                postRequest('/ajax/material/delete', {id: id}, function () {
                    loadingTb();
                }, function (msg) {
                    $.ligerDialog.error(msg);
                });
            }
        });
    }

    function offShelf(id) {

        $.ligerDialog.confirm("确定将该素材下架 ?", function (ensure) {
            if (ensure) {
                postRequest('/ajax/material/offShelf', {id: id}, function () {
                    loadingTb();
                }, function (msg) {
                    $.ligerDialog.error(msg);
                });
            }
        });
    }

    function onShelf(id) {

        $.ligerDialog.confirm("确定将该素材上架 ?", function (ensure) {
            if (ensure) {
                postRequest('/ajax/material/onShelf', {id: id}, function () {
                    loadingTb();
                }, function (msg) {
                    $.ligerDialog.error(msg);
                });
            }
        });
    }

    function f_add() {
        var categoryIdSelect = $('#category_id').find('option');
        if (collectionIsEmpty(categoryIdSelect) || categoryIdSelect.length <= 1) {
            $.ligerDialog.warn('还没有素材分类,请先去创建分类哦');
        } else {
            window.location.href = "/material/initAdd?type=" + $('#category_type').val();
        }
    }

    function f_edit(id) {
        window.location.href = "/material/initEdit?type=" + $('#category_type').val() + "&id=" + id;
    }
</script>
