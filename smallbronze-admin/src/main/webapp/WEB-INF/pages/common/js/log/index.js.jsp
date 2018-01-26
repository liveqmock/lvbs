<%--
  Created by IntelliJ IDEA.
  User: feifan.gou
  Date: 2017/9/11
  Time: 16:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8"%>
<script type="text/javascript">

    $(function () {

        renderTable();
        //绑定事件
        enterEvent($('input'), renderTable);
        $('select#operation_type,select#operation_object,select#operation_result').change(renderTable);
    });

    function columnList() {

        return [
            {display: '操作类型', name: 'typeName', width: 100},
            {display: '操作对象', name: 'objectName', width: 100},
            {display: '操作人', name: 'operatorName', width: 100},
            {display: 'IP', name: 'ip', align: 'left', width: 100},
            {display: '操作时间', name: 'timeFormat', width: 180},
            {display: '参数', name: 'operationParam', align: 'left', width: 800, render:function (row) {
                if (stringIsNull(row['operationParam'])) {
                    return '';
                }
                return "<div title='"+row['operationParam']+"'>"+row['operationParam']+"</div>"
            }},
            {display: '备注', name: 'remark', width: 300, render:function (row) {
                if (stringIsNull(row['remark'])) {
                    return '';
                }
                return "<div title='"+row['remark']+"'>"+row['remark']+"</div>"
            }},
            {display: '耗时/毫秒', name: 'consumeTime', width: 100},
            {display: '操作结果', name: 'resultName',  width: 80}

        ];
    }

    function renderTable() {

        var grid = $('#log_list').ligerGrid({
            columns: columnList(),
            root: 'rows',
            record: 'total',
            pageParmName: 'page_number',
            pagesizeParmName: 'page_size',
            rownumbers: true,
            colDraggable: true,
            url: "/ajax/log/paginate",
            parms: [
                document.getElementById('log_time'),
                document.getElementById('operator_name'),
                document.getElementById('operation_param'),
                document.getElementById('operation_type'),
                document.getElementById('operation_object'),
                document.getElementById('operation_result')
            ],
            pageSize: 20,
            page: 1,
            pageSizeOptions: [20, 50, 100]
        });
        $("#page_loading").hide();
        return grid;
    }

</script>


