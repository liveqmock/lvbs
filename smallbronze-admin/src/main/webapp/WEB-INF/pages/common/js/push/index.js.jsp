<%@ page import="com.daishumovie.base.enums.db.PushStatus" %>
<%@ page import="com.daishumovie.base.enums.db.PushTargetType" %>
<%@ page import="com.sun.org.apache.bcel.internal.generic.PUSH" %><%--
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
        $('#status').change(function () {

            var status = $(this).val().toString();
            if (status === '<%=PushStatus.pushing.getValue()%>') {
                $('span.push_time>label').html('定时时间');
            } else if(status === '<%=PushStatus.cancel.getValue()%>') {
                $('span.push_time>label').html('取消时间');
            } else {
                $('span.push_time>label').html('推送时间');
            }
            renderTable();
        });
        $('#push_platform,#target_type').change(renderTable);
    });

    function columnList() {

        var status = $('#status').val().toString();
        var columns = [];

        if (status === '<%=PushStatus.pushing.getValue()%>') {
            columns.push(
                {
                    display: '操作', width: 60, render: function (row) {
                    return '<button class="operation_btn push_cancel" onclick="cancel(' + row.id + ')">取消</button>';
                }, hide: $button_authority.isHide(['push_cancel'])
                }
            );
        }
        columns.push(
            {display: '数据ID',  width: 60,render:function(row){
                if (row['targetType'] === ~~'<%=PushTargetType.video.getValue()%>') {
                    return '<a target="_blank" href="/player/'+row['targetId']+'">' + row['targetId'] + '</a>';
                } else {
                    return row['targetId'];
                }
            }},
            {display: '推送状态', name: 'statusName', width: 80},
            {display: '推送平台', name: 'platformName', width: 80},
            {
                display: '推送文案', name: 'alert', align: 'left', width: 300, render: function (row) {

                return divWithTitle(row['alert']);
            }
            }
        );

        columns.push(
            {display: '推送方式', name: 'pushWay', width: 100},
            {display: '推送时间', name: 'pushTime', width: 160},
            {display: '推送人', name: 'pusher', width: 80},
            {display: '创建时间', name: 'createTimeFormat', width: 160}
        );
        if (status === '<%=PushStatus.cancel.getValue()%>') {
            columns.push(
                {display: '取消时间', name: 'cancelTime', width: 160}
            );
        }
        if (status !== '<%=PushStatus.cancel.getValue()%>' && status !== '<%=PushStatus.fail.getValue()%>') {
            columns.push(
                {display: '推送量', name: 'receivedCount', width: 50},
                {display: '点击量', name: 'clickTimes', width: 50},
                {display: '点击率', name: 'clickRate', width: 50}
            );
        }
        return columns;
    }

    function renderTable() {

        var grid = $('#push_list').ligerGrid({
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
            url: "/ajax/push/paginate",
            parms: [
                document.getElementById('push_time'),
                document.getElementById('status'),
                document.getElementById('target_type'),
                document.getElementById('alert'),
                document.getElementById('push_platform')
            ],
            pageSize: 20,
            page: 1,
            pageSizeOptions: [20, 50, 100]
        });
        $("#page_loading").hide();
        return grid;
    }

    function cancel(id) {

        $.ligerDialog.confirm("确定取消该定时任务 ?", function (ensure) {
            if (ensure) {
                postRequest('/ajax/push/cancelSchedule', {taskId: id}, function () {
                    renderTable();
                }, function (msg) {
                    $.ligerDialog.error(msg);
                });
            }
        });
    }
</script>
