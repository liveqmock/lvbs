<%@ page import="com.daishumovie.base.enums.db.ActivityStatus" %>
<%@ page import="com.daishumovie.base.enums.db.Whether" %>
<%@ page import="com.daishumovie.admin.service.impl.ActivityService" %>
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
        $('select#status,#whether_online').change(renderTable);
    });
    function columnList() {
        var columns = [];
        var whetherOffline = $('select#whether_online').val().toString();
        if (whetherOffline === '<%=Whether.yes.getValue()%>') {
            columns.push(
                {
                    display: '操作', width: 120, render: function (row) {
                    var edit = '<button class="operation_btn activity_edit" onclick="f_edit(' + row.id + ')">编辑</button>';
                    var offline = '<button class="operation_btn_with_margin activity_offline" onclick="offline(' + row.id + ',\'' + row['title']+'\')">下线</button>';
                    return edit + offline;
                }, hide: $button_authority.isHide(['activity_edit', 'activity_offline'])
                }
            )
        }
        columns.push(
            {display: '活动ID', name: 'id', width: 80},
            {display: '标题', name: 'title', width: 140,render:function (row) {
                return divWithTitle(row['title']);
            }},
            {display: '话题', name: 'topic', width: 80,render:function (row) {
                return divWithTitle('#'+row['topic']+'#');
            }},
            {
                display: '封面图', name: 'cover', width: 80, render: function (row) { //封面图
                return "<img src='" + row['cover'] + "' width='50' " + "onerror='imageError(this)' onmouseover='zoom(event,this.src)' onmouseleave='imgHide()' />";
            }
            },
            {
                display: '缩略图', name: 'thumbCover', width: 80, render: function (row) { //缩略图
                return "<img src='" + row['thumbCover'] + "' width='50' " + "onerror='imageError(this)' onmouseover='zoom(event,this.src)' onmouseleave='imgHide()' />";
            }
            },
            {display: '状态', name: 'statusName', width: 80},
//            {display: '活动说明', width: 100,render:function (row) {
//
//                return '<button class="operation_btn_with_margin" onclick="view('+row['id']+')">查看</button>';
//            }},
            {display: '评论数', name: 'replyNum', width: 80}
        );
        var status = $('#status').val().toString();
        if (status === '<%=ActivityStatus.ongoing.getValue()%>') {
            columns.push(
                {
                    display: '视频数', width: 80, render: function (row) {
                    return '<button class="operation_btn" onclick="put(' + row['id'] + ')">' + row['topicCount'] + '</button>';
                }
                }
            );
        }
        columns.push(
            {display: '预热时间', width: 160,render:function (row) {
                if (row['preTime'] === '<%=ActivityService.java_start_time%>') {
                    return '';
                }
                return row['preTime'];
            }},
            {display: '开始时间', name: 'startTime', width: 160},
            {display: '结束时间', name: 'endTime', width: 160},
            {display: '创建人', name: 'operatorName', width: 120},
            {display: '创建时间', name: 'createTime', width: 160}
        );
        if (whetherOffline === '<%=Whether.no.getValue()%>') {
            columns.push(
                {display: '更新人', name: 'modifierName', width: 120},
                {display: '更新时间', name: 'modifyTime', width: 160}
            );
        }
        return columns;
    }
    function renderTable() {
        var grid = $('#activity_list').ligerGrid({
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
            url: "/ajax/activity/paginate",
            parms: [
                document.getElementById('title'),
                document.getElementById('topic'),
                document.getElementById('status'),
                document.getElementById('whether_online'),
                document.getElementById('start_time'),
                document.getElementById('pre_time')
            ],
            pageSize: 20,
            page: 1,
            pageSizeOptions: [20, 50, 100]
        });
        $("#page_loading").hide();
        return grid;
    }
    function offline(id, title) {
        $.ligerDialog.confirm("确定下线【" + title + "】?", function (ensure) {
            if (ensure) {
                postRequest('/ajax/activity/offline', {id: id}, function () {
                    renderTable();
                }, function (msg) {
                    $.ligerDialog.error(msg);
                });
            }
        });
    }
    function f_edit(id) {
        var param = [];
        $('span.param_span>input,span.param_span>select').each(function () {
            param.push(this.name + '=' + this.value);
        });
        window.location.href = "/activity/initEdit?id=" + id + '&' + param.join("&");
    }
    function view(id) {
        $.getJSON('/ajax/activity/get?id=' + id,function (data) {
            if (data) {
                showView(data['instruction']);
            }
        });
    }
    function put(id) {
        location.href = '/activity/put?id=' + id;
    }
</script>