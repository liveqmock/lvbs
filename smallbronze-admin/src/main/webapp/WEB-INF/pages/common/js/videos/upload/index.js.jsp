<%@ page import="com.daishumovie.base.enums.db.UploadVideosStatus" %><%--
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
        $('select#status').change(renderTable);
    });

    function columnList() {
        var columns = [];
        columns.push(
            {display: '标题', name: 'name', width: 250,align: 'left',isSort: false, render: function (row) { //视频地址
                  return divWithTitle(row['name']);
              }},
            {display: '视频上传类型', name: 'typeName', width: 100, isSort: false},
            {
                display: '上传状态', name: 'statusName', width: 100, isSort: false, render: function (row) {
                var pass = '<button class="operation_btn topic_pass" onclick="reload(' + row.id + ',\'' + row['name'] + '\')">重新上传</button>';
                if (row['status'] == '<%=UploadVideosStatus.UPLOAD_FAIL.getValue()%>') {
                    return pass;
                } else if(row['status'] == '<%=UploadVideosStatus.UPLOADING.getValue()%>') {
                    return '<img width="30px" src="/images/loading.gif">'
                } else {
                    return row.statusName;
                }
            }
            },
            {display: '创建时间', name: 'create_time', width: 180},
            {display: '上传人', name: 'operatorName', width: 100, isSort: false}
        );
        return columns;
    }

    function renderTable() {

        grid = $('#topic_list').ligerGrid({
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
            url: "/ajax/upload/v/paginate",
            parms: [
                document.getElementById('title'),
                document.getElementById('status')
            ],
            pageSize: 20,
            page: 1,
            pageSizeOptions: [20, 50, 100]
        });
        $("#page_loading").hide();
        return grid;
    }

    function reload(id, title) {
        $.ligerDialog.confirm("确定将【" + title + "】重新上传?", function (yes) {

            if (yes) {
                postRequest('/ajax/upload/v/uploadAgain', {id: id}, function () {
                    renderTable();
                }, function (msg) {
                    $.ligerDialog.error(msg);
                }, loading, loadingStop);
            }
        });
    }
</script>
