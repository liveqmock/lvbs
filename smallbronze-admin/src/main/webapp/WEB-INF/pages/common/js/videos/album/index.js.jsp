<%@ page import="com.daishumovie.base.enums.db.AlbumStatus" %>
<%--
  Created by IntelliJ IDEA.
  User: feifan.gou
  Date: 2017/9/4
  Time: 18:45
  To change this template use File | Settings | File Templates.
  index 页需要引入的js
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<script type="text/javascript">

    $(function () {

        renderTable();
        //绑定事件
        enterEvent($('input'), renderTable);
        $('select#status').change(function () {
            var status = $(this).val().toString();
            var label = '创建时间';
            switch(status){
                case '<%=AlbumStatus.published.getValue()%>':
                    label = '发布时间';
                    break;
                case '<%=AlbumStatus.offline.getValue()%>':
                    label = '下线时间';
                    break;
            }
            $('span.param_time_span>label').html(label);
            renderTable();
        });
    });

    function columnList() {

        var columns = [];
        var status = $('#status').val().toString();
        if (status !== '<%=AlbumStatus.offline.getValue()%>') {
            columns.push(
                {
                    display: '操作', width: 120, render: function (row) {
                    var count = 0;
                    if(row['topicIds']){
                        count = row['topicIds'].split(",").length;
                    }
                    var edit = '<button class="operation_btn album_edit" onclick="f_edit(' + row.id + ')">编辑</button>';
                    var publish = '<button class="operation_btn_with_margin album_publish" onclick="publish(' + row.id + ',' + count + ')">发布</button>';
                    var offline = '<button class="operation_btn_with_margin album_offline" onclick="offline(' + row.id + ')">下线</button>';
                    var button_html;
                    if (status === '<%=AlbumStatus.publishing.getValue()%>') {
                        button_html = edit + publish;
                    } else {
                        button_html = edit + offline;
                    }
                    return button_html;
                }, hide: $button_authority.isHide(['category_edit', 'category_delete'])
                }
            );
        }
        columns.push(
            {display: '合辑ID', name: 'id', width: 80},
            {display: '标题',  width: 180,render:function (row) {
                return divWithTitle(row['title']);
            }},
            {display: '副标题', width: 150,render:function (row) {

                return divWithTitle(row['subtitle']);
            }},
            {display: '状态', name: 'statusName', width: 100},
            {
                display: '封面', name: 'cover', width: 80, render: function (row) { //图片显示
                return "<img src='" + row['cover'] + "' width='50' " + "onerror='imageError(this)' onmouseover='zoom(event,this.src)' onmouseleave='imgHide()' />";
            }
            },
            {
                display: '视频数', width: 80, render: function (row) { //图片显示
                var count = 0;
                if(row['topicIds']){
                    count = row['topicIds'].split(",").length;
                }
                return '<button class="operation_btn" onclick="put('+row['id']+',\''+row['topicIds']+'\')">'+count+'</button>';
            }
            }
        );
        if (status !== '<%=AlbumStatus.publishing.getValue()%>') {
            columns.push(
                {display: '发布时间', name: 'publishTime', width: 180}
            );
        }
        columns.push(
            {display: '创建人', name: 'operator', width: 100},
            {display: '创建时间', name: 'createTimeFormat', width: 180},
            {display: '更新人', name: 'modifier', width: 100},
            {display: '更新时间', name: 'modifyTimeFormat', width: 180}
        );
        return columns;
    }

    function renderTable() {

        var grid = $('#album_list').ligerGrid({
            width: '100%', height: '100%',
            columns: columnList(),
            root: 'rows',
            record: 'total',
            pageParmName: 'page_number',
            pagesizeParmName: 'page_size',
            sortnameParmName: "sort_name",
            sortorderParmName: "sort_order",
            rownumbers: true,
            colDraggable: true,
            url: "/ajax/album/paginate",
            parms: [
                document.getElementById('title'),
                document.getElementById('subtitle'),
                document.getElementById('param_time'),
                document.getElementById('status')
            ],
            pageSize: 20,
            page: 1,
            pageSizeOptions: [20, 50, 100]
        });
        $("#page_loading").hide();
        return grid;
    }

    function publish(id, count) {

        if (count === 0) {
            $.ligerDialog.error("该合辑中还未添加视频，暂不可发布");
            return;
        }

        $.ligerDialog.confirm("确定发布该合辑 ?", function (ensure) {
            if (ensure) {
                postRequest('/ajax/album/publish', {albumId: id}, function () {
                    renderTable();
                }, function (msg) {
                    $.ligerDialog.error(msg);
                });
            }
        });
    }
    function offline(id) {

        $.ligerDialog.confirm("确定下线该合辑 ?", function (ensure) {
            if (ensure) {
                postRequest('/ajax/album/offline', {albumId: id}, function () {
                    renderTable();
                }, function (msg) {
                    $.ligerDialog.error(msg);
                });
            }
        });
    }
    function f_edit(id) {
        var param = [];
        $('span.param_span').find('input,select').each(function() {
            param.push(this.name + '=' + this.value);
        });
        window.location.href = "/album/initEdit?id=" + id + "&" + param.join("&");
    }

    function put(id) {
        window.location.href = "/album/putVideo?id=" + id;
    }
</script>
