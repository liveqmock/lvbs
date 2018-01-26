<%@ page import="com.daishumovie.base.enums.db.TopicStatus" %><%--
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
        $("#put_topic_layout").ligerLayout({rightWidth: 300, allowRightCollapse: false});

        $('#search_topic').click(searchVideo);

        enterEvent($('#topic_id'), searchVideo);

        $('#add_topic').click(function () {

            var topicId = $('#add_data').children('div.border').data('id');
            if (stringIsNull(topicId)) {
                $.prompt("请查询出要添加的视频");
                return;
            }
            var activityId = $('#activity_id').val();
            add_video(topicId, activityId);
        });

        function searchVideo() {
            var topicId = $('#topic_id').val();
            if (stringIsNull(topicId)) {
                $.prompt("请输入视频ID");
                return;
            }
            postRequest('/ajax/activity/topicByActivityId', {
                topicId: topicId,
                activityId: $('#activity_id').val()
            }, function (video) {
                $('#add_data').html('').append(layoutVideo(video, true));
            }, function (msg) {
                $.prompt(msg);
            }, loading, loadingStop);
        }
    });
    $(function () {
        $.getJSON('/ajax/activity/topicListByActivityId', {activityId: $('#activity_id').val()}, function (videoList) {
            var $oResultBox = $('#resultBox');
            $('#paging').paging({
                nowPage: 1,
                allPages: Math.ceil(videoList.length / 9),
                displayPage: 6,
                callBack: function (now) {
                    var currentPages = now * 9 < videoList.length ? 9 : videoList.length - (now - 1) * 9;
                    $oResultBox.html('');
                    for (var i = 0; i < currentPages; i++) {
                        var num = (now - 1) * 9 + i;
                        $oResultBox.append(layoutVideo(videoList[num]));
                    }
                }
            });
        });
        enterEvent($('div.go>input'),function () {
            $('div.go>button').click();
        });
    });
    function layoutVideo(video, isCreate) {
        var create_dl = $('<dl class="bounceIn animated"></dl>');
        var border = $('<div/>',{
            'data-id':video['id']
        }).addClass('border').appendTo(create_dl);

        {
            var $dt = $('<dt/>').appendTo(border);
            var $a = $('<a/>', {
                href: '/player/' + video['id'],
                target: '_blank'
            }).appendTo($dt);
            var $cover = $('<img/>', {
                src: video['cover']
            }).appendTo($a);
            if (video['transverse']) {
                $cover.addClass('transverse');
            }
        }
        {
            var $dd = $('<dd/>').appendTo(border);
            var $p_id = $('</p>').html('视频ID：' + stringTrim(video['id'])).appendTo($dd);
            if (video['fictitious']) {
                $('<p/>').html('用户名：' + '<font color="red">' + video['publisherName'] + '</font>').appendTo($dd);
            } else {
                $('<img/>', {
                    src: '/plugin/form/img/close.ico'
                }).on('click',function () {
                    if (isCreate) {
                        $(this).parents('div.border').slideUp(function () {
                            $(this).remove();
                        });
                    } else {
                        remove_video(video['id']);
                    }
                }).appendTo($p_id);
                $('<p/>').html('用户名：' + stringTrim(video['publisherName'])).appendTo($dd);
            }
            $('<p/>').html('播放次数：' + stringTrim(video['playCount'])).appendTo($dd);
            if (video['status'].toString() !== '<%=TopicStatus.published.getValue()%>') {
                $dd.append('<p>点赞：' + video['followNum'] + '</p>');
            } else {
                $dd.append('<p>点赞：<input type="number" value="' + video['followNum'] + '"></p>');
            }

            var $p_button = $('<p/>').appendTo($dd);
            $('<button/>').on('click', function () {
                var likeCount = $(this).parents('dd').find('input[type=number]').val();
                update_like_count(video['id'], likeCount);
            }).html('提交').appendTo($p_button);
            if (video['status'].toString() !== '<%=TopicStatus.published.getValue()%>') {
                $p_button.children('button').prop('disabled', 'disabled');
                $p_button.children('button').html('视频' + video['statusName']);
                $p_button.children('button').css('background-color', '#BEBEBE');
            }
        }
        if (isCreate) {
            return border;
        }
        return create_dl;
    }

    function remove_video(topicId) {

        $.ligerDialog.confirm("确定将该视频移除当前活动 ?",function (ensure) {
            if (ensure) {
                postRequest('/ajax/activity/removeVideoFromActivity', {topicId:topicId}, function () {
                    location.reload();
                }, function (msg) {
                    $.prompt(msg);
                }, loading);
            }
        });
    }

    function add_video(topicId, activityId) {

        $.ligerDialog.confirm("确定将该视频加入当前活动 ?",function (ensure) {
            if (ensure) {
                postRequest('/ajax/activity/putVideo', {topicId: topicId, activityId: activityId}, function () {
                    location.reload();
                }, function (msg) {
                    $.prompt(msg);
                }, loading);
            }
        });
    }

    function update_like_count(topicId, likeCount) {

        $.ligerDialog.confirm('确定更新该视频点赞数为【'+likeCount+'】 ?',function (ensure) {
            if (ensure) {
                postRequest('/ajax/activity/updateLikeCount', {topicId: topicId, likeCount: likeCount}, function () {
                    $.ligerDialog.success("修改成功!");
                }, function (msg) {
                    $.prompt(msg);
                }, loading, loadingStop);
            }
        });

    }
</script>
