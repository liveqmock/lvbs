<%--
  Created by IntelliJ IDEA.
  User: feifan.gou
  Date: 2017/9/4
  Time: 18:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<script type="text/javascript">
    var grid;
    $(function () {

        renderTable();
        //绑定事件
        enterEvent($('input'), renderTable);
        $('select#audit_status').change(renderTable);
    });

    function columnList() {
        var columns = [];
        if(!+$('#audit_status').val()){
            columns.push(
                {
                    display: '操作', width: 120, render: function (row) {
                    var pass = '<button class="operation_btn topic_pass" onclick="f_pass(' + row.id + ')">通过</button>';
                    var not_pass = '<button class="operation_btn_with_margin topic_not_pass" onclick="f_not_pass(' + row.id + ')">不通过</button>';
                    var hidden_id = "<input type='hidden' value='" + row.id + "'>";
                    if (row['auditStatus'] <= 2) {
                        return pass + not_pass +　hidden_id;
                    } else {
                        return '';
                    }
                }, hide: $button_authority.isHide(['topic_pass', 'topic_not_pass'])
                });
        }
        columns.push(

            {display: '标题', name: 'title', width: 100,isSort: false,align: 'left', render: function (row) { //视频地址
                return divWithTitle(row['title']);
            }},
            {
                display: '封面', name: 'cover', width: 80, isSort: false, render: function (row) { //视频封面地址
                return "<a href='../player/" + row.id + "' target='_blank'><img src='" + row['cover'] + "' width='50' " + "onerror='imageError(this)' onmouseover='zoom(event,this.src)' onmouseleave='imgHide()' /></a>";
            }
            },
            {display: '所属频道', name: 'categoryName', width: 100, isSort: false},
            {display: '发布者昵称', name: 'nickName', width: 100, isSort: false},
            {display: '手机号码', name: 'mobile', width: 100, isSort: false},
            {display: '发布者角色', name: 'userType', width: 100, isSort: false},
            {display: '上传时间', name: 'create_time', width: 180},
            {display: '来源', name: 'sourceName', width: 100, isSort: false},
        );
        if(!!+$('#audit_status').val() || $('#audit_status').val() == ""){
            columns.push(
                {display: '审核状态', name: 'auditStatusName', width: 100, isSort: false},
                {display: '审核备注', name: 'auditDesc1', width: 100, isSort: false,render:function (row) {
                    return divWithTitle(row['auditDesc']);
                }},
                {display: '审核时间', name: 'audit_time', width: 180},
                {display: '操作者', name: 'operatorName', isSort: false, width: 100},
            );
        }
        return columns;
    }

    function renderTable() {

        grid = $('#topic_list').ligerGrid({
            width: '100%', height: '100%',
            //checkbox: !+$('#audit_status').val(),
            columns: columnList(),
            root: 'rows',
            record: 'total',
            pageParmName: 'page_number',
            pagesizeParmName: 'page_size',
            sortnameParmName: "sort_name",
            sortorderParmName: "sort_order",
            rownumbers: true,
            colDraggable: true,
            url: "/ajax/topic/paginate",
            parms: [
                document.getElementById('title'),
                document.getElementById('create_time'),
                document.getElementById('audit_status')
            ],
            pageSize: 20,
            page: 1,
//            scrollToPage:true,
            pageSizeOptions: [20, 50, 100],
            onRClickToSelect: true,
            onAfterShowData:function (currentData) {
                if (collectionIsNotEmpty(currentData['rows'])) {
                    $(currentData['rows']).each(function (index, ele) {
                        if(ele['auditStatus'] > 2){
                            if (index+1 < 10){
                                $('#topic_list\\|1\\|r100' + (index + 1) + '\\|c102').children().remove();
                            }else{
                                $('#topic_list\\|1\\|r10' + (index + 1) + '\\|c102').children().remove();
                            }
                        }
                    });
                }
            }
        });
        $("#page_loading").hide();
        return grid;
    }


    function f_pass(id){

        $.ligerDialog.confirm("确定通过？",function (ensure) {
            if (ensure) {
                postRequest('/ajax/topic/pass',{id:id},function () {
                    renderTable();
                },function (msg) {
                    $.ligerDialog.error(msg);
                });
            }
        });
    }

    function f_not_pass(id){

        $.ligerDialog.confirm("确定不通过？",function (ensure) {
            if (ensure) {
                $.ligerDialog.open({ url: '/topic/audit', name:'wintest4',height: 280,width: null,
                    buttons: [ { text: '确定', onclick: function (item, dialog) {
                        callbackSubmit(id);
                    } },
                        { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });
            }
        });
    }

    function callbackSubmit(ids) {
        //获取字窗口值
        document.getElementById('wintest4').contentWindow.f_ok();
        if (stringIsNull($("#reportId").val())) {
            $.ligerDialog.warn("请填写不通过原因");
            return;
        }
        postRequest('/ajax/topic/unPass',{id:ids,reason:$("#reportId").val()},function () {
            renderTable();
            $(".l-dialog,.l-window-mask").remove();
        },function (msg) {
            $.ligerDialog.error(msg);
        });
    }

    function selectIds() {

        var selected_tr = $(".l-selected", ".l-grid2");
        var ids = [];
        $.each(selected_tr, function () {
            var idStr = $(this).find(':hidden').val();
            if (stringIsNotNull(idStr)) {
                ids.push(parseInt(idStr));
            }
        });
        console.log(ids);
        return ids;
    }

    function batch_pass() {

        var ids = selectIds();
        if (ids.length === 0) {
            $.ligerDialog.warn("请选择数据哦");
            return;
        }
        $.ligerDialog.confirm("确定批量通过？",function (ensure) {
            if (ensure) {
                postRequest('/ajax/topic/batchPass',{ids:ids},function () {
                    renderTable();
                },function (msg) {
                    $.ligerDialog.error(msg);
                });
            }
        });
    }

    function batch_not_pass() {

        var ids = selectIds();
        if (ids.length === 0) {
            $.ligerDialog.warn("请选择数据哦");
            return;
        }
        $.ligerDialog.confirm("确定批量不通过？",function (ensure) {
            if (ensure) {
                $.ligerDialog.open({ url: '/topic/audit',name:'wintest4', height: 280,width: null,
                    buttons: [ { text: '确定', onclick: function (item, dialog) {
                        //获取字窗口值
                        document.getElementById('wintest4').contentWindow.f_ok();
                        if (stringIsNull($("#reportId").val())) {
                            $.ligerDialog.warn("请填写不通过原因");
                            return;
                        }
                        postRequest('/ajax/topic/batchUnPass',{ids:ids,reason:$("#reportId").val()},function () {
                            renderTable();
                            $(".l-dialog,.l-window-mask").remove();
                        },function (msg) {
                            $.ligerDialog.error(msg);
                        });
                    } },
                        { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });
            }
        });
    }


</script>
