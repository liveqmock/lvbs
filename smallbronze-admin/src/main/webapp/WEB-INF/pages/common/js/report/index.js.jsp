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
        $('select#status,#type,#app').change(renderTable);
    });


    function columnList() {
        var columns = [];

        if(!+$('#status').val()){
            columns.push(
                {
                    display: '操作', width: 160, render: function (row) {
                         var pass = '<button class="operation_btn report_handle" onclick="f_pass(' + row.id +','+row.contentId+ ')">处理</button>';
                         var not_pass = '<button class="operation_btn_with_margin report_not_handle" onclick="f_not_pass(' + row.id+','+row.contentId + ')">暂不处理</button>';
                         var hidden_id = "<input type='hidden' value='" + row.id + "'>";
                         if (row['status'] == 0) {
                             return pass + not_pass +　hidden_id;
                         } else {
                             return '';
                         }
                     }, hide: $button_authority.isHide(['topic_pass', 'topic_not_pass'])
                 }
            );
        }

        var _type = $('#type').val();

        columns.push(
            {display:'举报内容',name:'problemContent', width: 150,isSort: false, render: function (row) { //视频地址
               return divWithTitle(row['problemContent']);
            }},
        );

        if (_type == 1){
            columns.push(
                        {display: '举报视频ID', name: 'contentId', width: 80,isSort: false, render: function (row) { //视频地址
                          return "<a href='../player/"+row.contentId + "' target='_blank'>"+row.contentId+"</a>";
                       }},
                        {display: '举报视频标题', name: 'contentName', width: 150,align: 'left',isSort: false, render: function (row) { //视频地址
                              return divWithTitle(row['contentName']);
                          }},
                        {
                            display: '举报对象封面', name: 'cover', width: 80, isSort: false, render: function (row) { //视频封面地址
                            return "<img src='" + row['cover'] + "' width='50' " + "onerror='imageError(this)' onmouseover='zoom(event,this.src)' onmouseleave='imgHide()' />";
                            }
                        },
                        {display: '所属频道', name: 'categoryName', width: 100, isSort: false},
                        {display: '发布者昵称', name: 'beNickName', width: 100, isSort: false},
                        {display: '发布者角色', name: 'beUserType', width: 100, isSort: false},
                        {display: '发布者手机号码', name: 'beMobile', width: 100, isSort: false},
                        {display: '发布时间', name: 'videoReleaseTime', width: 180},
                       );
        }else if (_type == 2){
            columns.push({display: '评论内容', name: 'contentName', width: 150,align: 'left',isSort: false, render: function (row) { //视频地址
                           return divWithTitle(row['contentName']);
                       }},
                        {display: '评论者昵称', name: 'beNickName', width: 100, isSort: false},
                        {display: '评论者角色', name: 'beUserType', width: 100, isSort: false},
                        {display: '评论者手机号码', name: 'beMobile', width: 100, isSort: false},
                        {display: '评论时间', name: 'videoReleaseTime', width: 180},
                       );
        }else if (_type == 3){
                    columns.push({display: '用户昵称', name: 'contentName', width: 150,align: 'left',isSort: false, render: function (row) { //视频地址
                       return "<a href='../app/user/view/"+row.contentId+"' target='_blank'>"+row.contentName+"</a>";
                    }},
                   );
        }

        columns.push({display: '举报者昵称', name: 'nickName', width: 100, isSort: false},
                                 {display: '举报者角色', name: 'userType', width: 100, isSort: false},
                                 {display: '举报者手机号', name: 'mobile', width: 100, isSort: false},
                                 {display: '举报时间', name: 'create_time', width: 180});

        if(!!+$('#status').val()){
            columns.push(
                {display: '审核状态', name: 'auditStatusName', width: 100, isSort: false},
                {display: '审核备注', name: 'auditDesc', width: 100, isSort: false,render:function (row) {
                    return divWithTitle(row['auditDesc']);
                }},
                {display: '审核时间', name: 'audit_time', width: 180},
                {display: '审核人', name: 'auditName', isSort: false, width: 100}
            );
        }
        return columns;
    }

    function renderTable() {

            grid = $('#maingrid').ligerGrid({
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
            url: "/ajax/report/paginate",
            parms: [
                document.getElementById('type'),
                document.getElementById('create_time'),
                document.getElementById('status')
            ],
            pageSize: 20,
            page: 1,
            pageSizeOptions: [20, 50, 100]
        });
        $("#page_loading").hide();
        return grid;
    }

    function f_pass(id,contentId){
        $.ligerDialog.confirm("确定处理吗？",function (ensure) {
            if (ensure) {
               postRequest('/ajax/report/pass',{id:id,contentId:contentId,type:$("#type").val()},function () {
                   renderTable();
               },function (msg) {
                   $.ligerDialog.error(msg);
               });
            }
        });
    }

    function f_not_pass(id,contentId){
        $.ligerDialog.confirm("确定暂不处理吗？",function (ensure) {
            if (ensure){
                postRequest('/ajax/report/unpass',{id:id,contentId:contentId,type:$("#type").val()},function () {
                   renderTable();
               },function (msg) {
                   $.ligerDialog.error(msg);
               });
            }
        });
    }
</script>
