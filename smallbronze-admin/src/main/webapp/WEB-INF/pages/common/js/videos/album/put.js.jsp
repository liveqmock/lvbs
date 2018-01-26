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

    var bind = false, table_grid, count_msg = '合辑中的视频保证在<%=Configuration.album_min%>~<%=Configuration.album_max%>个', first_time = true, init = true;

    $(function () {

        //layout
        $("#album_layout").ligerLayout({rightWidth: 460, allowRightCollapse: false});
        $('div.data_div').show();
        //渲染数据
        table_grid = renderTable();
        $('#channel').change(loadingData);
        //绑定事件
        enterEvent($('input'), loadingData);
        //提交事件
        $('#submit_album').click(function () {
            if (selected_videos().length >24 || selected_videos().length < 6) {
                $.prompt(count_msg);
            } else {
                var url = '/ajax/album/putVideo', param = {
                    topicIds: selected_videos().join(','),
                    albumId: $('#album_id').val()
                };
                $.confirm({
                    title: '数据提交',
                    message: '确定提交 ?',
                    buttons: {
                        Yes: {
                            'class': 'blue',
                            action: function () {
                                postRequest(url, param, function () {
                                    window.location.href = '/album/index';
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
        });
    });

    function loadingData() {
        table_grid.loadData();
    }

    function columnList() {

        return [
            {
                display: '视频ID', name: 'id', width: 80, isSort: false, render: function (row) {
                return '<a target="_blank" href="/player/' + row['id'] + '">' + row['id'] + '</a><input type="hidden" id="video_data_' + row['id'] + '">';
            }
            },
            {
                display: '标题', name: 'title', width: 100, isSort: false, render: function (row) { //视频地址
                return divWithTitle(row['title']);
            }
            },
            {display: '所属频道', name: 'categoryName', width: 80, isSort: false},
            {display: '状态', name: 'statusName', width: 80, isSort: false},
            {
                display: '是否存在合辑中', width: 100, isSort: false, render: function (row) {
                    var a_html;
                    if (!~~row['description'] || row['description'] === $('#album_id').val().toString()) {
                        a_html = ',' + row['description'];
                    } else {
                        a_html = ',<a href="/album/putVideo?id=' + row['description'] + '">' + row['description'] + '</a>'
                    }
                    if (row['description'].indexOf(",") > -1) {
                        a_html = '';
                        $(row['description'].split(",")).each(function () {
                            if (this.toString() === $('#album_id').val().toString()) {
                                a_html += '，'+ this;
                            } else {
                                a_html += ('，<a href="/album/putVideo?id='+this+'">'+this+'</a>');
                            }
                        });
                    }
                return a_html.substring(1);
            }
            },
            {display: '发布者昵称', name: 'nickName', width: 100, isSort: false},
            {display: '发布时间', name: 'publishTime', width: 160}
        ];
    }

    function renderTable() {
        var grid = $('#album_list').ligerGrid({
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
            checkbox: true,
            url: "/ajax/album/video/paginate",
            parms: [
                document.getElementById('id'),
                document.getElementById('channel'),
                document.getElementById('user_name'),
                document.getElementById('create_time'),
                document.getElementById('topic_id')
            ],
            pageSize: 24,
            page: 1,
            pageSizeOptions: [24],
            onSelectRow: function (data, row_index, row_obj) {
                <%--if(selected_videos().length === ~~'<%=Configuration.album_max%>'){--%>
                    <%--{ //行内渲染--%>
                        <%--var selected_style = {--%>
                            <%--'background-color': '#E0E0E0',--%>
                            <%--color: '#8E8E8E'--%>
                        <%--};--%>
                        <%--$(row_obj).css(selected_style);--%>
                    <%--}--%>
                    <%--return;--%>
                <%--}--%>
                if (!check_video_count()) {
                    if (selected_videos().indexOf(data['id']) <= -1) {
                        $(row_obj).trigger('click');
                        $.ligerDialog.warn(count_msg);
                    } else {
                        { //行内渲染
                            var selected_style = {
                                'background-color': '#E0E0E0',
                                color: '#8E8E8E'
                            };
                            $(row_obj).css(selected_style);
                        }
                    }
                } else {
                    { //行内渲染
                        var selected_style = {
                            'background-color': '#E0E0E0',
                            color: '#8E8E8E'
                        };
                        $(row_obj).css(selected_style);
                    }
                    if (selected_videos().indexOf(data['id']) <= -1) {
                        if (data['status'].toString() !== '<%=TopicStatus.published.getValue()%>') {
                            $.ligerDialog.error("该视频状态为【" + data['statusName'] + "】,请重新选择 !");
                            $(row_obj).trigger('click');
                            return false;
                        }
                        loading();
                        $.getJSON('/ajax/bucket/videoInfo', {videoId: data['videoId']}, function (videoInfo) {

                            var id = data['id'];
                            { //右侧桶内增加数据
                                var $video_div = $('<div/>', {
                                    'class': 'video_div',
                                    'data-id': id
                                });
                                var $video_span = $('<span/>', {
                                    'class': 'video_span'
                                }).on('click', function () {
                                    window.open('/player/' + id);
                                });
                                $('<img/>', {
                                    src: '/images/bucket/player.ico'
                                }).appendTo($video_span);
//                            $('<img/>', {
//                                'class': 'remove_video',
//                                src: '/images/delete.ico'
//                            }).on('click',function () {
//                                console.log('=-========')
//                                $(row_obj).click();
//                            }).appendTo($video_span);
                                $('<span/>', {
                                    'class':'video_id'
                                }).html(data['id']).appendTo($video_div);
                                $('<span/>', {
                                    'class':'sort_number'
                                }).html('【'+(selected_videos().length + 1)+'】').appendTo($video_div);
                                $video_span.appendTo($video_div);
                                $('<img class="video_cover" onerror="imageError(this)" src="' + videoInfo['cover'] + '" />').appendTo($video_div);
                                $video_div.appendTo($('div.data_div'));
                            }
                            //增加拖拽事件
                            if (selected_videos().length > 1 && !bind) {
                                bind = true;
                                $("div.data_div").eq(0).dragsort(
                                    {
                                        dragSelector: "div.video_div",
                                        dragBetween: true,
                                        dragEnd: sort,
                                        placeHolderTemplate: '<div class="video_div" style="background-color:white !important; border:dashed 1px gray !important;"></div>'
                                    }
                                );
                            }
                            //顺序号重排列
                            $('div.data_div').children('div.video_div').each(function (index) {
                                $(this).find('span.sort_number').html('【' + (index + 1) + '】');
                            });
                            loadingStop();
                        });
                    }

                }
            },
            onUnSelectRow: function (data, row_index, row_obj) {
                var original_style = {
                    'background-color': '',
                    color: ''
                };
                $(row_obj).css(original_style);
                $('div.video_div[data-id=' + data['id'] + ']').remove();
                sort();
            },
//            onBeforeCheckRow:function () {
//
//            },
            onBeforeShowData:function (currentData) {
                if (first_time) {
                    first_time = false;
                    {
                        var topic_ids = [];
                        $.each($('#topic_id').val().split(','), function () {
                            topic_ids.push(parseInt(this));
                        });
                        if (topic_ids.length > 0) {
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
                                        }).on('click', function () {
                                            window.open('/player/' + ele['topicId']);
                                        });
                                        $('<img/>', {
                                            src: '/images/bucket/player.ico'
                                        }).appendTo($video_span);
//                                    $('<img/>', {
//                                        'class': 'remove_video',
//                                        src: '/images/delete.ico'
//                                    }).on('click',function () {
//                                        $(this).parents('div.video_div').remove();
//                                        $('#video_data_' + ele['topicId']).parents('tr[id^=bucket_list]').click();
//                                    }).appendTo($video_span);
                                        $('<span/>', {
                                            'class':'video_id'
                                        }).html(ele['topicId']).appendTo($video_div);
                                        $('<span/>', {
                                            'class':'sort_number'
                                        }).html('【'+(selected_videos().length + 1)+'】').appendTo($video_div);
                                        $video_span.appendTo($video_div);
                                        $('<img class="video_cover" onerror="imageError(this)" src="' + ele['cover'] + '" />').appendTo($video_div);
                                        $video_div.appendTo($('div.data_div'));
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
                                    $(currentData['rows']).each(function () {
                                        if (selected_videos().indexOf(this['id']) > -1) {
                                            $('#video_data_' + this['id']).parents('tr.l-grid-row').trigger('click');
                                        }
                                    });
                                }
                            });
                        }
                    }
                }
//                else {
//
//                }
            },
            onAfterShowData: function (currentData) {
                $('tr.l-grid-row-alt').each(function () {
                    $(this).removeClass('l-grid-row-alt');
                });
                $('tr.l-grid-row').css('cursor', 'pointer');
                //去除全选
                $('#album_list\\|hcell\\|c102').remove();
                if (!first_time) {
                    $(currentData['rows']).each(function () {
                        if (selected_videos().indexOf(this['id']) > -1) {
                            $('#video_data_' + this['id']).parents('tr.l-grid-row').trigger('click');
                        }
                    });
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

    function check_video_count() {
        var count = selected_videos().length;
        return count < ~~'<%=Configuration.album_max%>';
    }

    function sort() {
        $('div.data_div').children('div.video_div').each(function (index) {
            $(this).find('span.sort_number').html('【' + (index + 1) + '】');
        });
    }
</script>
