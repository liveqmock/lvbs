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

        //layout
//        $("#bucket_layout").ligerLayout({ rightWidth: 1000 ,allowRightCollapse:false});
        //渲染数据
        renderTable();
        //绑定事件
        enterEvent($('input'), renderTable);
    });

    function columnList() {

        return [
            {
                display: '操作', width: 60, render: function (row) {
                return '<button class="operation_btn bucket_edit" onclick="f_edit(\''+row['id']+'\',\''+row['topicIds']+'\')">编辑</button>';
            }, hide: $button_authority.isHide(['bucket_edit'])
            },
            {display: 'ID', name: 'id', width: 60, render:function (row) {
                return '<a href="javascript:;" onclick="f_view(\''+row['topicIds']+'\',\''+row['remarks']+'\')">' + row['id']+'</a>';
            }},
            {display: '内容', name: 'topicIds', align:'left', width: 180,render:function (row) {
                var topicIdList = row['topicIds'].split(',');
                var show_html = '';
                $(topicIdList).each(function (index) {
                    if (0 !== index) {
                        show_html += '，';
                    }
                    show_html += ('<a target="_blank" href="/player/' + this + '">' + this + '</a>');
                });
                return show_html;
            }},
            {display: '备注', name: 'remarks', width: 160, render:function (row) {
                return divWithTitle(row['remarks']);
            }},
            {display: '创建时间', name: 'createTimeFormat', width: 160},
            {display: '创建人', name: 'operatorName', width: 100}
        ];
    }

    function renderTable() {

        var grid = $('#bucket_list').ligerGrid({
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
            url: "/ajax/bucket/paginate",
            parms: [
                document.getElementById('id'),
                document.getElementById('create_time'),
                document.getElementById('admin_name')
            ],
            pageSize: 20,
            page: 1,
            pageSizeOptions: [20, 50, 100]
        });
        $("#page_loading").hide();
        return grid;
    }

    function f_view(topicIds, remarks) {
        $('h4.remarks').html("备注：【"+remarks+"】");
        var topicIdList = topicIds.split(',');
        if (stringIsNull(topicIds) || !topicIds.split(',').length) {
            $.prompt("该桶内暂时没有数据");
        } else {
            $.getJSON('/ajax/bucket/videoList', {topic_ids: topicIdList}, function (data) {
                if (collectionIsNotEmpty(data)) {
                    if ($('div.data_div').is(':hidden')) {
                        $('div.data_div').show();
                        $("#bucket_layout").ligerLayout({ rightWidth: 330 ,allowRightCollapse:false});
                    } else {
                        $('div.data_div').html('');
                    }
                    $(data).each(function (index, ele) {

                        var $video_div = $('<div/>',{
                            'class':'video_div'
                        }).on('click',function () {
                            window.open('/player/' + topicIdList[index]);
                        });
                        var $video_span = $('<span/>', {
                            'class':'video_span'
                        });
                        $('<span/>', {
                            'class':'video_id'
                        }).html(ele['topicId']).appendTo($video_div);
                        $('<span/>', {
                            'class':'sort_number'
                        }).html('【'+(index + 1)+'】').appendTo($video_div);
                        $('<img/>', {
                            src: '/images/bucket/player.ico'
                        }).appendTo($video_span);
                        $video_span.appendTo($video_div);
                        $('<img/>', {
                            src: ele['cover'],
                            'class':'video_cover'
                        }).appendTo($video_div);
                        $video_div.appendTo($('div.data_div'));
                    });
                }

            });
        }
    }
    function f_edit(id, topicIds) {
        location.href = '/bucket/initEdit?id=' + id;
    }
</script>
