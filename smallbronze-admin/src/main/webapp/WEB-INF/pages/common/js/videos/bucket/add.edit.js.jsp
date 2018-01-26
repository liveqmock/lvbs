<%@ page import="com.daishumovie.admin.constant.Configuration" %>
<%@ page import="com.daishumovie.base.enums.db.TopicStatus" %><%--
  Created by IntelliJ IDEA.
  User: feifan.gou
  Date: 2017/9/4
  Time: 18:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<script type="text/javascript">

    var select_status = false, bind = false, table_grid, queried = false, count_msg = '视频个数保证<%=Configuration.bucket_min%>~<%=Configuration.bucket_max%>个';

    $(function () {
        //layout
        $("#bucket_layout").ligerLayout({rightWidth:330, allowRightCollapse: false});
        $('div.data_div').show();
        //渲染数据
        table_grid = renderTable();
        $('#channel,#inside').change(loadingData);
        //绑定事件
        enterEvent($('input'), loadingData);
        //提交事件
        $('#submit_bucket').click(function () {
            if (selected_videos().length === 0 || !check_count()) {
                $.prompt(count_msg);
            } else {
                if (stringIsNull($('#remarks').val())) {
                    $.prompt("请填写备注");
                } else {
                    var url = '/ajax/bucket/save', param = {
                        videoIds: selected_videos().join(','),
                        remarks: $('#remarks').val()
                    };
                    if(stringIsNotNull($('#bucket_id_backup').val())){
                        url = '/ajax/bucket/update';
                        param['id'] = $('#bucket_id_backup').val();
                    }
                    $.confirm({
                        title: '数据提交',
                        message: '确定提交 ?',
                        buttons: {
                            Yes: {
                                'class': 'blue',
                                action: function () {
                                    postRequest(url, param, function () {
                                        window.location.href = '/bucket/index';
                                    }, function (msg) {
                                        $.prompt(msg);
                                    }, loading);
                                }
                            },
                            No: {
                                'class': 'gray',
                                action: function () {

                                }
                            }
                        }
                    });
                }
            }
        });
    });

    function loadingData() {
        table_grid.loadData();
    }

    function columnList() {

        return [
            {display: 'ID', name: 'id', width: 80, isSort: false, render:function (row) {
                return '<a target="_blank" href="/player/' + row['id'] + '">' + row['id']+'</a><input type="hidden" id="video_data_'+row['id']+'">';
            }},
            {
                display: '标题', name: 'title', width: 100, isSort: false, render: function (row) { //视频地址
                return divWithTitle(row['title']);
            }
            },
            {display: '状态', name: 'statusName', width: 80, isSort: false},
            {display: '所属频道', name: 'categoryName', width: 80, isSort: false},
            {display: '已进桶?', name: 'description', width: 100, isSort: false, render:function (row) {
                return divWithTitle(row['description']);
            }},
            {display: '发布者昵称', name: 'nickName', width: 100, isSort: false},
//            {display: '发布者角色', name: 'userType', width: 100, isSort: false},
            {display: '发布时间', name: 'create_time', width: 160}
        ];
    }

    function renderTable() {

        var url = '/ajax/bucket/video/paginate?isAdd=true';
        if (stringIsNotNull($('#bucket_id').val())) {
            url = '/ajax/bucket/video/paginate?isAdd=false';
        }
        var grid = $('#bucket_list').ligerGrid({
            width: '100%', height: '95%',
            columns: columnList(),
            root: 'rows',
            record: 'total',
            pageParmName: 'page_number',
            pagesizeParmName: 'page_size',
            sortnameParmName: "sort_name",
            sortorderParmName: "sort_order",
            rownumbers: true,
            colDraggable: true,
            checkbox:true,
            url: url,
            parms: [
                document.getElementById('id'),
                document.getElementById('channel'),
                document.getElementById('user_name'),
                document.getElementById('create_time'),
                document.getElementById('inside'),
                document.getElementById('topic_id')
            ],
            pageSize: 8,
            page: 1,
            pageSizeOptions: [8],
            onSelectRow: function (data, row_index, row_obj) {
                if (selected_videos().indexOf(data['id']) < 0) {
                    var tr_id = $(row_obj).prop('id');
                    var tr_id_split = tr_id.split('|');
                    var checkbox_tr = $('tr[id^=' + tr_id_split[0] + '\\|1\\|' + tr_id_split[2] + ']');
                    if (!check_count()) {
                        checkbox_tr.removeClass('l-selected');
                        if (!$('div.l-dialog').length || $('div.l-dialog').is('hidden')) {
                            $.ligerDialog.warn(count_msg);
                        }
                        return false;
                    } else {
                        select_status = true;
                        if ($('div.video_div[data-id=' + data['id'] + ']').length > 0) {
                            return false;
                        }
                        if (data['status'].toString() !== '<%=TopicStatus.published.getValue()%>') {
                            $.ligerDialog.error("该视频状态为【" + data['statusName'] + "】,请重新选择 !");
                            checkbox_tr.trigger('click');
                            return false;
                        }
                        $('#page_loading').show();
                        $.getJSON('/ajax/bucket/videoInfo', {videoId: data['videoId']}, function (videoInfo) {

                            var id = data['id'];
                            { //行内渲染
                                var selected_style = {
                                    'background-color': '#E0E0E0',
                                    color: '#8E8E8E'
                                };
                                $(row_obj).css(selected_style);
                            }
                            { //右侧桶内增加数据
                                var $video_div = $('<div/>', {
                                    'class': 'video_div',
                                    'data-id': id
                                });
                                var $video_span = $('<span/>', {
                                    'class': 'video_span'
                                });
                                $('<img/>', {
                                    src: '/images/bucket/player.ico'
                                }).on('click', function () {
                                    window.open('/player/' + id);
                                }).appendTo($video_span);
                                $('<img/>', {
                                    'class': 'remove_video',
                                    src: '/images/delete.ico'
                                }).on('click',function () {
                                    if (!checkbox_tr.is(':hidden')) {
                                        checkbox_tr.trigger('click');
                                    } else {
                                        $(this).parents('div.video_div').remove();
                                    }
                                }).appendTo($video_span);
                                $('<span/>', {
                                    'class':'video_id'
                                }).html(data['id']).appendTo($video_div);
                                $('<span/>', {
                                    'class':'sort_number'
                                }).html('【'+(selected_videos().length + 1)+'】').appendTo($video_div);
                                $video_span.appendTo($video_div);
                                $('<img class="video_cover" onerror="imageError(this)" src="' + videoInfo['cover'] + '" />').appendTo($video_div);
                                $video_div.appendTo($('div.data_div'));
                                if (!check_count()) {
                                    $('#video_data_' + data['id']).parents('tr[id^=bucket_list]').click();
                                }
                            }
                            $('#page_loading').hide();
                            //增加拖拽事件
                            if (selected_videos().length > 1 && !bind) {
                                bind = true;
                                $("div.data_div").eq(0).dragsort(
                                    {
                                        dragSelector: "div.video_div",
                                        dragBetween: true,
                                        dragEnd: function () {
                                            $('div.data_div').children('div.video_div').each(function (index) {
                                                $(this).find('span.sort_number').html('【' + (index + 1) + '】');
                                            });
                                        },
                                        placeHolderTemplate: '<div class="video_div" style="background-color:white !important; border:dashed 1px gray !important;"></div>'
                                    }
                                );
                            }
                            //顺序号重排列
                            $('div.data_div').children('div.video_div').each(function (index) {
                                $(this).find('span.sort_number').html('【' + (index + 1) + '】');
                            });
                        });
                    }
                }
                else {
                    var selected_style = {
                        'background-color': '#E0E0E0',
                        color: '#8E8E8E'
                    };
                    $(row_obj).css(selected_style);
                    select_status = true;
                }
            },
            onUnSelectRow:function (data, row_index, row_obj) {
                $('div.video_div[data-id=' + data['id'] + ']').remove();
                var original_style = {
                    'background-color': '',
                    color: ''
                };
                $(row_obj).css(original_style);
                //顺序号重排列
                $('div.data_div').children('div.video_div').each(function (index) {
                    $(this).find('span.sort_number').html('【' + (index + 1) + '】');
                });
            },
            onBeforeCheckRow:function (checked, data, rowid, rowdata) {
                if (!check_count() && !select_status) {
                    if (!$('div.l-dialog').length || $('div.l-dialog').is('hidden')) {
                        $.ligerDialog.warn(count_msg);
                    }
                    return false;
                }
                if (stringIsNull($('#bucket_id').val()) && checked) {
                    if (data['status'].toString() !== '<%=TopicStatus.published.getValue()%>') {
                        $.ligerDialog.error("该视频状态为【" + data['statusName'] + "】,请重新选择 !");
                        return false;
                    }
                }
            },
            onAfterShowData: function (data) {
                $('tr.l-grid-row-alt').each(function () {
                    $(this).removeClass('l-grid-row-alt');
                });
                $('tr.l-grid-row').css('cursor', 'pointer');
                //编辑状况下自动勾选
                if (queried) {
                    $(data['rows']).each(function () {
                        if (selected_videos().indexOf(this['id']) > -1) {
                            $('#video_data_' + this['id']).parents('tr[id^=bucket_list]').trigger('click');
                        }
                    });
                }
            },
            onBeforeShowData:function () {
                if(stringIsNotNull($('#bucket_id').val())){
                    /*$('div.l-grid-hd-cell-btn-checkbox').click();
                    $('#topic_id').val('');
                    $('#bucket_id').val('');
                    $('tr.l-grid-hd-row').removeClass('l-checked');*/
                    var topic_ids = [];
                    $.each($('#topic_id').val().split(','), function () {
                        topic_ids.push(parseInt(this));
                    });
                    if(queried) {
                        return;
                    }
                    $.getJSON('/ajax/bucket/videoList', {topic_ids: topic_ids}, function (data) {
                        if (collectionIsNotEmpty(data)) {
                            if ($('div.data_div').is(':hidden')) {
                                $('div.data_div').show();
                                $("#bucket_layout").ligerLayout({ rightWidth: 330 ,allowRightCollapse:false});
                            } else {
                                $('div.data_div').html('');
                            }
                            $(data).each(function (index, ele) {

                                var $video_div = $('<div/>', {
                                    'class': 'video_div',
                                    'data-id': ele['topicId']
                                });
                                var $video_span = $('<span/>', {
                                    'class': 'video_span'
                                });
                                $('<img/>', {
                                    src: '/images/bucket/player.ico'
                                }).on('click', function () {
                                    window.open('/player/' + ele['topicId']);
                                }).appendTo($video_span);
                                $('<img/>', {
                                    'class': 'remove_video',
                                    src: '/images/delete.ico'
                                }).on('click',function () {
                                    $(this).parents('div.video_div').remove();
                                    $('#video_data_' + ele['topicId']).parents('tr[id^=bucket_list]').click();
                                }).appendTo($video_span);
                                $('<span/>', {
                                    'class':'video_id'
                                }).html(ele['topicId']).appendTo($video_div);
                                $('<span/>', {
                                    'class':'sort_number'
                                }).html('【'+(selected_videos().length + 1)+'】').appendTo($video_div);
                                $video_span.appendTo($video_div);
                                $('<img class="video_cover" onerror="imageError(this)" src="' + ele['cover'] + '" />').appendTo($video_div);
                                $video_div.appendTo($('div.data_div'));
                                $('#video_data_' + ele['topicId']).parents('tr[id^=bucket_list]').trigger('click');
                            });
                            //绑定拖拽事件
                            $("div.data_div").eq(0).dragsort(
                                {
                                    dragSelector: "div.video_div",
                                    dragBetween: true,
                                    dragEnd: function () {
                                        $('div.data_div').children('div.video_div').each(function (index) {
                                            $(this).find('span.sort_number').html('【' + (index + 1) + '】');
                                        });
                                    },
                                    placeHolderTemplate: '<div class="video_div" style="background-color:white !important; border:dashed 1px gray !important;"></div>'
                                }
                            );
                            bind = true;
                            queried = true;
//                            $('div.l-grid-hd-cell-btn-checkbox').click();
                        }
                    });
                } else {
                    queried = true;
                }

            }
        });
        $("#page_loading").hide();
        return grid;
    }

    function selected_videos() {
        var selected_videos = [];
        $('div.data_div').children('div.video_div').each(function () {
            selected_videos.push($(this).data('id'));
        });
        return selected_videos;
    }

    function check_count() {

        var count = selected_videos().length;
        if (count === 0) {
            return true;
        }
        return count >= ~~'<%=Configuration.bucket_min%>' && count <= ~~'<%=Configuration.bucket_max%>';
    }
</script>
