<%@ page import="com.daishumovie.base.enums.db.ActivityStatus" %>
<%@ page import="com.daishumovie.base.enums.db.Whether" %><%--
  index 页需要引入的js
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<script type="text/javascript">

    $(function () {

        renderTable();
        //绑定事件
        enterEvent($('input'), renderTable);
        $('select#status').change(renderTable);
    });

    function columnList() {

        var columns = [];
        var status = $('select#status').val().toString();
        if (status === '<%=Whether.yes.getValue()%>') {
            columns.push(
                {
                    display: '操作', width: 120, render: function (row) {
                    var edit = '<button class="operation_btn activity_edit" onclick="f_edit(' + row.id + ')">编辑</button>';
                    var offline = '<button class="operation_btn_with_margin activity_offline" onclick="offline(' + row.id + ',\'' + row['name']+'\')">下线</button>';
                    return edit + offline;
                }, hide: $button_authority.isHide(['activity_edit', 'activity_offline'])
                }
            )
        }
        columns.push(
            {display: '名称', name: 'title', width: 140,render:function (row) {
                return divWithTitle(row['name']);
            }},

            {display: '广告位', name: 'orders', width: 80},
            {
                display: '封面图', name: 'adCover', width: 80, render: function (row) { //封面图
                return "<img src='" + row['adCover'] + "' width='50' " + "onerror='imageError(this)' onmouseover='zoom(event,this.src)' onmouseleave='imgHide()' />";
            }
            },

            {display: '跳转类型', name: 'targetTypeName', width: 80},
            {display: '跳转地址', name: 'target', width: 80},
            {display: '广告类型', name: 'adTypeName', width: 80},
            {display: '切换时间', name: 'duration', width: 80},
            {display: '开始时间', name: 'startTime', width: 160},
            {display: '结束时间', name: 'endTime', width: 160},
            {display: '创建人', name: 'creatorName', width: 120},
            {display: '创建时间', name: 'createTime', width: 160}
        );
<%--   if (status === '<%=Whether.no.getValue()%>') {
       columns.push(
           {display: '更新人', name: 'modifierName', width: 120},
           {display: '更新时间', name: 'modifyTime', width: 160}
       );
   }--%>
   return columns;
}

function renderTable() {

   var grid = $('#ad_list').ligerGrid({
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
       url: "/ajax/ad/paginate",
       parms: [
           document.getElementById('name'),
           document.getElementById('orders'),
           document.getElementById('status'),
           document.getElementById('create_time'),
       ],
       pageSize: 20,
       page: 1,
       pageSizeOptions: [20, 50, 100]
   });
   $("#page_loading").hide();
   return grid;
}

function offline(id, name) {

   $.ligerDialog.confirm("确定下线【" + name + "】?", function (ensure) {
       if (ensure) {
           postRequest('/ajax/ad/delete', {id: id}, function () {
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
   window.location.href = "/ad/initEdit?id=" + id + '&' + param.join("&");
}
function view(id) {
   $.getJSON('/ajax/activity/get?id=' + id,function (data) {
       if (data) {
           showView(data['instruction']);
       }
   });
}
</script>
