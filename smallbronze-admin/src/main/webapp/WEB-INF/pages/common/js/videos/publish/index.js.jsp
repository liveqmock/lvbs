<%--
  Created by IntelliJ IDEA.
  User: feifan.gou
  Date: 2017/9/4
  Time: 18:45
  To change this template use File | Settings | File Templates.
  index 页需要引入的js
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<script type="text/javascript">

    var bind = false, isDefault = true;
    $(function () {
        //绑定事件
        enterEvent($('input'), renderTable);
        $('select#publish_status').change(function () {
            var value = $(this).val();
            if (value == '2') {
                $('#publish_time').val('');
                $('span.publish_time').hide();
            } else {
                if ($('#publish_time').is(":hidden")) {
                    $('span.publish_time').val('');
                    $('span.publish_time').show();
                }
            }
            renderTable();
        });
        $('select#uploader').change(renderTable);
        $('select#channelId').change(renderTable);
        if ('${from_edit}' === 'true') {
            $('#publish_status>option:eq(1)').prop('selected', true);
            renderTable();
        } else {
            renderTable();
        }
        $('#publisher').bComboSelect({
            showField : 'introduce',
            keyField : 'uid',
            data : '/ajax/app/user/fictitiousList',
            //启用多选模式
            multiple : false
        });
        $('#editForm').submit(function () {

            if($('#cover_is_empty').val()) {
                $.prompt('默认封面为空请从视频中截取');
                return false;
            }
            if ($('#is-re-upload').hasClass('switch-on')) {
                if (stringIsNull($('#new_video_cover_value').val())) {
                    $.prompt('请重新上传封面，否则请关闭【重新上传】开关');
                    return false;
                }
            }
            var param = {};
            $('#editForm').find('input[type!=submit],input[type!=file]').each(function () {
                if ('publisher_text' === this.name) {
                    param['publisher'] = this.name;
                } else {
                    param[this.name] = this.value.trim();
                }
            });
            if (stringIsNull(param['publisher'])) {
                $.prompt("请选择发布人");
                return false;
            }
            if (param['title'].length > 30) {
                $.prompt("标题长度不能大于30");
                return false;
            }
            param['channelId'] = $('#channel_id').val();
            param['isDefault'] = isDefault;
            if (!isDefault) {
                param['coverTime'] = $('video').eq(0).prop('currentTime');
                if(param['coverTime'] === 0){
                    param['coverTime'] = 0.00;
                }
            }
            postRequest('/ajax/topic/publish', param, function () {
                $('.closeDialogBtn').click();
                renderTable();
            },function (msg) {
                $.prompt(msg)
            },loading, function () {
                loadingStop();
            });
            return false;
        });

        $('.closeDialogBtn').click(function () {

            $('video').trigger('pause');
            $('#new_video_cover_value').val('');
            if ($('#is-re-upload').hasClass('switch-on')) {
                $('#is-re-upload').click();
            }
        });
        $('#is-default-switch').click(function () {

            if (!$(this).hasClass('switch-on')) {
                $('li.cover-li').show();
                isDefault = true;
                $('video').trigger('pause');
                $('li.video-li').hide();
            } else {
                isDefault = false;
                $('video').trigger('play');
                $('li.video-li').show();
                $('li.cover-li').hide();

            }
        });
        $('#is-re-upload').click(function () {

            if ($(this).hasClass('switch-on')) {
                $('#upload_again').hide();
                $('#new_video_cover_value').val('');
                $('#video_cover').prop('src', $('#old_video_cover_value').val());
            } else {
                $('#new_video_cover_value').val('');
                $('#upload_again').show();
            }
        });
        $('video').on('play', function () {
            if ($(this).attr('src')) {
                $('#cover_is_empty').val('');
            }
        });

        $('#upload_again').click(function () {
            $('video').trigger('pause');
            if (!$('#is-default-switch').hasClass('switch-on')) {
                $('#is-default-switch').click();
            }
            $('#new_video_cover').click();
        });

        $('#new_video_cover').change(function () {

            var file = this.files[0];
            var hiddenValue = $('#new_video_cover_value');
            var img_placeholder = $('#video_cover');
            var standard_width = 750,standard_height = 422, standard_size = 200;
            checkImageInfo(file, standard_width, standard_height, standard_size, function () {
                upload2Local(file, function (data) {
                    if (data) {
                        var upload_result = data[0];
                        hiddenValue.val(upload_result['absolute_path']);
                        img_placeholder.prop('src', upload_result['show_url']);
                    }
                }, function (msg) {
                    $.prompt(msg);
                }, loading, loadingStop);
            });
        });
    });

    function columnList() {

        var publish_status = $('#publish_status').val();
        var channelId = $('#channelId').val();
        var columns = [];
        if (publish_status == '2') { //待发布
            columns.push({
                display: '操作', width: 100, render: function (row) {
                    var publish = '<button class="operation_btn topic_publish" onclick="publish(' + row.id + ',\'' + row['title']+'\',\''+row['videoUrl']+'\',\''+row['cover']+'\')">发布</button>';
                    var remove = '<button class="operation_btn topic_remove" onclick="remove(' + row.id + ',\'' + row['title']+'\')">删除</button>';
                    return publish + remove;
                }, hide: $button_authority.isHide(['topic_publish','topic_remove'])
            });
        } else if (publish_status == '1') { //已发布

            columns.push({
                display: '操作', width: 140, render: function (row) {
                    var offline = '<button class="operation_btn topic_offline" onclick="offline(' + row.id + ')">下线</button>';
                    var edit = '<button class="operation_btn topic_edit" onclick="edit(' + row.id + ',\'' + row['videoUrl'] + '\',\'' + row['cover'] + '\',\'' + row['title'] + '\')">编辑</button>';
                    if (channelId != ""){
                        var up = '<button class="operation_btn topic_up" onclick="up(' + row.id + ')">置顶</button>';
                        return offline + edit + up;
                    }
                    return offline + edit;
                }, hide: $button_authority.isHide(['topic_offline','topic_edit','topic_up'])
            });
        } else { //已下线

        }
        columns.push(
            {display: 'ID', name: 'id', width: 80,render:function (row) {
                return '<a target="_blank" href="/player/'+row['id']+'">' + row['id'] + '</a>';
            }},
            {
                display: '封面', name: 'cover', width: 80, render: function (row) { //图片显示
                return "<img src='" + row['cover'] + "' width='50' " + "onerror='imageError(this)' onmouseover='zoom(event,this.src)' onmouseleave='imgHide()' />";
            }
            }
        );
        if(publish_status != '2'){
            columns.push(
                {display: '频道', name: 'categoryName', width: 70}
            );
        }
        columns.push(
            {
                display: '标题', name: 'title', align: 'left',width: 450, render: function (row) {
                return divWithTitle(row['title']);
            }
            },
            {display: '视频来源', name: 'sourceName', width: 70},
            {display: '上传人', name: 'operatorName', width: 100},
            {display: '上传时间', name: 'create_time', width: 160}
        );

        if(publish_status != '2'){
            columns.push(
                {display: '发布人', name: 'nickName', width: 100},
                {display: '发布时间', name: 'publishTimeFormat', width: 160},
                {display: '操作人', name: 'publisherName', width: 80}
            );
        }
        return columns;
    }

    function renderTable() {

        var grid = $('#publish_list').ligerGrid({
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
            url: "/ajax/topic/publish/paginate",
            parms: [
                document.getElementById('publish_time'),
                document.getElementById('publish_status'),
                document.getElementById('id'),
                document.getElementById('video_title'),
                document.getElementById('uploader'),
                document.getElementById('channelId')
            ],
            pageSize: 20,
            page: 1,
            pageSizeOptions: [20, 50, 100],
            onAfterShowData:function () {
                if (!bind) {
                    bind = true;
                    $('div.video_source').on({
                        blur:function () {
                            $(this).children('span').show();
                            $(this).children('button').hidden();
                        },
                        focus:function () {
                            $(this).children('button').show();
                            $(this).children('span').hidden();
                        }
                    });
                }
            }
        });
        $("#page_loading").hide();
        return grid;
    }
    function publish(id, title, videoLink, coverLink) {
        if (stringIsNull(coverLink)) {
            coverLink = 'http://daishumovie.oss-cn-shanghai.aliyuncs.com/img/empty_image.png';
        }
        $(':hidden[name=id]').val(id);
        $('#publisher_text,#publisher').val('');
        $('#title').val(title);
        $('video').prop('src', videoLink);
        { //计算宽高
            var tempCover = document.createElement('img');
            tempCover.src = coverLink;
            var width = tempCover.width;
            var height = tempCover.height;
            if(width === 0 || height === 0) {
                coverLink = 'http://daishumovie.oss-cn-shanghai.aliyuncs.com/img/empty_image.png';
                tempCover.src = coverLink;
                width = tempCover.width;
                height = tempCover.height;
                $('#cover_is_empty').val(true);
            } else {
                $('#cover_is_empty').val('');
            }
            $('#video_cover').prop('src', coverLink);
            $('#old_video_cover_value').val(coverLink);
            if (width > height) {
                $('#video_cover').width('200px');
                $('#video_cover').height('auto');
                $('#video_cover').css('margin-left','-128px')
            } else {
                $('#video_cover').height('150px');
                $('#video_cover').width('auto');
                $('#video_cover').css('margin-left','-247px')
            }
        }
        if ($('#is-default-switch').hasClass('switch-off')) {
            $('#is-default-switch').trigger('click');
        }
        showBomb();
    }
    function offline(id) {
        $.confirm({
            title: '视频下线',
            message: '确定将此视频下线 ?',
            buttons: {
                Yes: {
                    'class': 'blue',
                    action: function () {
                        postRequest('/ajax/topic/offline', {id: id}, function () {
                            renderTable();
                        }, function (msg) {
                            $.prompt(msg);
                        }, loading, loadingStop);
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

    function up(id){
        $.confirm({
            title: '视频置顶',
            message: '确定将此视频置顶 ?',
            buttons: {
                Yes: {
                    'class': 'blue',
                    action: function () {
                        postRequest('/ajax/topic/up', {id: id}, function () {
                            renderTable();
                        }, function (msg) {
                            $.prompt(msg);
                        }, loading, loadingStop);
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
    function remove(id, title) {
        $.confirm({
            title: '删除视频',
            message: '确定将【'+title+'】下线 ?',
            buttons: {
                Yes: {
                    'class': 'blue',
                    action: function () {
                        postRequest('/ajax/topic/delete', {id: id}, function () {
                            renderTable();
                        }, function (msg) {
                            $.prompt(msg);
                        }, loading, loadingStop);
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
    function edit(id, videoUrl, cover, title) {

        var param = ["id=" + id, "videoUrl=" + videoUrl, "cover=" + cover, "title=" + title];

        location.href = '/topic/initEdit?' + param.join("&");
    }
</script>
