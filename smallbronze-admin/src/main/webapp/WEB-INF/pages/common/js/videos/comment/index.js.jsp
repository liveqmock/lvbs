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
        $('#audit_status').change(function () {
            if (!+$('#audit_status').val()) {
                $('button.audit').show();
            } else {
                $('button.audit').hide();
            }
            renderTable();
        });
        $('#type').change(function () {
            if (!+$('#type').val()) {
                $('button.audit').show();
            } else {
                $('button.audit').hide();
            }
            renderTable();
        });
    });

    function columnList() {

        var columns = [];
        if (!+$('#audit_status').val()) {
            columns.push({
                display: '操作', width: 120, render: function (row) {
                    var pass = '<button class="operation_btn comment_through" onclick="f_pass(' + row.id + ')">通过</button>';
                    var no_pass = '<button class="operation_btn_with_margin comment_fail" onclick="f_not_pass(' + row.id + ')">不通过</button>';
                    if (row['auditStatus'] <= 2) {
                        return pass + no_pass + "<input type='hidden' value='" + row.id + "'>";
                    } else if (row['auditStatus'] > 2) {
                        return "";
                    }
                }, hide: $button_authority.isHide(['comment_through', 'comment_fail'])
            });
        }
        columns.push(
            {display: '评论ID', name: 'id', width: 100, isSort: false},
            {display: '评论内容', name: 'content', width: 120, align: 'left',isSort: false,render:function (row) {
                return divWithTitle(row['content']);
            }},

            {display: '评论类型', name: 'targetTypeName', width: 180, isSort: false},
            {display: '评论时间', name: 'commentTime', width: 180, isSort: false},
            {display: '评论者昵称', name: 'reviewer', width: 150, isSort: false},
            {display: '手机号码', name: 'mobile', width: 100, isSort: false},
            {display: '用户角色', name: 'userType', width: 100, isSort: false},
        );

        if ($('#type').val() == '1'){
	        columns.push(
                {display: '视频ID', name: 'targetId', width: 100, isSort: false,render:function (row) {
                    return '<a target="_blank" href="/player/'+row['targetId']+'">' + row['targetId'] + '</a>';
                }},
                {display: '视频标题', name: 'videoTitle', width: 120, isSort: false,render:function (row) {
                     return divWithTitle(row['videoTitle']);
                 }},
            );
        }else if ($('#type').val() == '5'){
            columns.push(
                {display: '合辑ID', name: 'targetId', width: 100, isSort: false,render:function (row) {
                    return row['targetId'];
                }},
                {display: '合辑标题', name: 'videoTitle', width: 120, isSort: false,render:function (row) {
                     return divWithTitle(row['videoTitle']);
                 }},
            );
        }else if ($('#type').val() == '6'){
             columns.push(
                 {display: '活动ID', name: 'targetId', width: 100, isSort: false,render:function (row) {
                     return row['targetId'];
                 }},
                 {display: '活动标题', name: 'videoTitle', width: 120, isSort: false,render:function (row) {
                     return divWithTitle(row['videoTitle']);
                 }},
             );
         }


        columns.push(
            {display: '审核状态', name: 'auditStatusName', width: 100, isSort: false}
        );
        if(!!+$('#audit_status').val()){
            if ($('#audit_status').val() == '3') {
                columns.push(
                    {display: '审核描述', name: 'auditDesc', width: 100, isSort: false,render:function (row) {
                        return divWithTitle(row['auditDesc']);
                    }}
                );
            }
            columns.push(
                {display: '审核者', name: 'auditor', isSort: false, width: 100},
                {display: '审核时间', name: 'auditTimeFormat', width: 180}
            );
        }
        return columns;
    }

    function renderTable() {

        $('#comment_list').ligerGrid({
            width: '100%', height: '100%',
            checkbox: !+$('#audit_status').val(),
            columns: columnList(),
            root: 'rows',
            record: 'total',
            pageParmName: 'page_number',
            pagesizeParmName: 'page_size',
            sortnameParmName: "sort_name",
            sortorderParmName: "sort_order",
            rownumbers: true,
            colDraggable: true,
            url: "/ajax/comment/paginate",
            parms: [
                document.getElementById('create_time'),
                document.getElementById('audit_status'),
                document.getElementById('type')
            ],
            pageSize: 20,
            page: 1,
            pageSizeOptions: [20, 50, 100],
            onRClickToSelect: true
        });
        $("#page_loading").hide();
    }

    const post_url = {
        fail_url:'/ajax/comment/fail',
        through_url:'/ajax/comment/through'
    };
    /**
     * 批量不通过
     */
    function batchFail() {

        var selected_tr = $(".l-grid2 .l-selected");
        var ids = [];
        $.each(selected_tr, function () {

            if (stringIsNotNull($(this).find(':hidden').val())){
                ids.push($(this).find(':hidden').val());
            }
        });
        if (collectionIsEmpty(ids)) {
            $.ligerDialog.warn("还未选择数据哦");
            return;
        }

        $.ligerDialog.promptAuditOk('请输入审核描述',true, function (yes,value) {
             if (stringIsNotNull(value)){
                updateAuditStatus(post_url.fail_url, '确认批量不通过?',value, ids);
             }
        });
    }

    /**
     * 批量通过
     */
    function batchThrough(){

        var selected_tr = $(".l-grid2 .l-selected");
        var ids = [];
        $.each(selected_tr, function () {
            if (stringIsNotNull($(this).find(':hidden').val())){
                ids.push($(this).find(':hidden').val());
            }
        });
        if (collectionIsEmpty(ids)) {
            $.ligerDialog.warn("还未选择数据哦");
            return;
        }
        $.ligerDialog.confirm("确认批量通过?", function (yes) {
            if (yes) {
                updateAuditStatus(post_url.through_url, '确认批量通过?','', ids);
            }
        });

    }

    /**
     * 不通过
     */
    function f_not_pass(id){

        var ids = [id];
        $.ligerDialog.promptAuditOk('请输入审核描述',true, function (yes,value) {
             if (stringIsNotNull(value)){
                updateAuditStatus(post_url.fail_url, '确认审核不通过?',value, ids);
             }
        });
    }

    /**
     * 通过
     * @param actionID
     */
    function f_pass(id) {

        var ids = [id];
        $.ligerDialog.confirm("确认通过?", function (yes) {
            if (yes) {
                updateAuditStatus(post_url.through_url, '确认通过?','', ids);
            }
        });
    }

    /**
     * 修改审核状态
     * @param url
     * @param msg
     * @param ids
     */
    function updateAuditStatus(url, msg,value, ids) {
        postRequest(url, {ids: ids,auditDesc:value}, function () {
            renderTable();
        },function (msg) {
            $.ligerDialog.warn(msg);
        });
    }
</script>
