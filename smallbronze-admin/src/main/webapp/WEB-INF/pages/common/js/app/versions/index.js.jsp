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
        $('select').change(renderTable);
    });

    function columnList() {

        return [
           {
               display: '操作', width: 80, render: function (row) {
               return "<button class='operation_btn app_version_edit' onclick='f_edit(" + row.id +","+row.appId+ ")'>编辑</button>";
           }, hide: $button_authority.isHide(['app_version_edit'])
           },

           {display: '应用ID', name: 'appName', width: 100, isSort: false,align: "center"},
           {display: '版本号', name: 'versionNum', width: 100, isSort: false,align: "center"},
           {display: '序号', name: 'build', isSort: false,width: 100},
           {display: '渠道号', name: 'channelId', isSort: false,width: 100},
           {display: '平台', name: 'platName',isSort: false, width: 100},
           {display: '强制更新版本', name: 'minVersion',isSort: false, width: 90, align: "center"},
           {display: '下载地址', name: 'downUrl', isSort: false,width: 200, render: function (row) { //下载地址
              return "<a href='" + row['downUrl'] + "' target='_blank'>" + divWithTitle(row['downUrl']) + "</a>";
          }},
           {display: '更新说明', name: 'updateDesc', isSort: false,width: 200},
           {display: '备注', name: 'remark', isSort: false,width: 200},
           {display: '更新时间', name: 'updateTimeF', width: 150}
       ];
    }

    function renderTable() {

        var grid = $('#maingrid').ligerGrid({
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
            hideLoadButton: true,
            url: "/ajax/app/ajaxAppVersionList",
            parms: [
                document.getElementById('versionNum'),
                document.getElementById('plat'),
                document.getElementById('appId')
            ],
            pageSize: 20,page: 1,pageSizeOptions: [20, 50, 100]
        });
        $("#pageloading").hide();
        return grid;
    }

    function f_add() {
        var url = '${basePath}app/versionMethod?appId=&r_t=' + new Date().getTime();
        $.ligerDialog.open({
            title: "添加版本配置", isDrag: true, url: url, height: 460, width: 560,
            isResize: true, name: 'eidtFrame'
        });
    }

    function f_edit(actionId,appId) {
        var url = '${basePath}app/versionMethod?_id=' + actionId +"&appId="+app+ '&r_t=' + new Date().getTime();
        $.ligerDialog.open({
            title: "添加版本配置", isDrag: true, url: url, height: 460, width: 560,
            isResize: true, name: 'eidtFrame'
        });
    }


</script>
