<%--
  Created by IntelliJ IDEA.
  User: feifan.gou
  Date: 2017/9/6
  Time: 14:35
  To change this template use File | Settings | File Templates.
  用户列表js
--%>
<%@ page contentType="text/html;charset=UTF-8"%>
<script type="text/javascript">

    $(function () {

        renderTable();
        //绑定事件
        enterEvent($('input'), renderTable);
        $('#is_topic_auth,#is_reply_auth,#fictitious').change(renderTable);
    });

    function columnList() {

        return [
            {
                display: '操作', width: 220, render: function (row) {
                var publish_switch = '开启', reply_switch = '开启';
                if (row['isReplyAuth'] == '1') {
                    reply_switch = '关闭';
                }
                if (row['isTopicAuth'] == '1') {
                    publish_switch = '关闭';
                }
                var publish_topic = '<button class="operation_btn user_topic_auth" onclick="publish_topic(\''+row['nickName']+'\',' + row.uid + ',' + row['isTopicAuth'] + ')">' +
                    publish_switch +'视频权限</button>';
                var reply_comment = '<button class="operation_btn_with_margin user_comment_auth" onclick="reply_comment(\''+row['nickName']+'\',' + row.uid + ',' + row['isReplyAuth'] + ')">' +
                    reply_switch +'评论权限</button>';
                return publish_topic + reply_comment;
            }, hide: $button_authority.isHide(['user_topic_auth', 'user_comment_auth'])
            },
            {display: '昵称', name: 'nickName', width: 100},
            {
                display: '头像', name: 'avatar', width: 80, render: function (row) { //图片显示
                return "<img src='" + row['avatar'] + "' width='50' " + "onerror='imageError(this)' onmouseover='zoom(event,this.src)' onmouseleave='imgHide()' />";
            }
            },
            {display: '手机号', name: 'mobile', width: 100},
            {display: '性别', name: 'gender', width: 100},
            {display: '是否有发布视频权限', name: 'isTopicAuthName', width: 120},
            {display: '是否有回复评论权限', name: 'isReplyAuthName', width: 120},
            {display: '自我介绍', name: 'introduce', align:'left', width: 180,render:function (row) {

                return divWithTitle(row['introduce']);
            }},
            {display: '注册时间', name: 'registerTime', width: 180},
            {display: '最后登录时间', name: 'lastLoginTimeFormat', width: 180},
            {display: '操作人', name: 'operatorName', width: 120}

        ];
    }

    function renderTable() {

        var grid = $('#user_list').ligerGrid({
            width: '100%', height: '100%',
            checkbox: true,
            columns: columnList(),
            root: 'rows',
            record: 'total',
            pageParmName: 'page_number',
            pagesizeParmName: 'page_size',
            sortnameParmName: "sort_name",
            sortorderParmName: "sort_order",
            rownumbers: true,
            colDraggable: true,
            url: "/ajax/app/user/paginate",
            parms: [
                document.getElementById('register_time'),
                document.getElementById('nickname'),
                document.getElementById('mobile'),
                document.getElementById('is_topic_auth'),
                document.getElementById('is_reply_auth'),
                document.getElementById('fictitious')
            ],
            pageSize: 20,
            page: 1,
            pageSizeOptions: [20, 50, 100]
        });
        $("#page_loading").hide();
        return grid;
    }

    function reply_comment(nickname,id,is_reply_auth) {

        var info = '用户"'+nickname+'"回复评论的权限当前为' + (is_reply_auth === 0 ? '【关闭】' : '【开启】') + '，要进行' +
            (is_reply_auth === 0 ? '【开启】' : '【关闭】') + '操作吗？';
        $.ligerDialog.confirm(info, function (ensure) {
            if (ensure) {
                postRequest('/ajax/app/user/replyComment', {id: id}, function () {
                    renderTable();
                }, function (msg) {
                    $.ligerDialog.error(msg);
                });
            }
        });
    }
    function publish_topic(nickname,id,is_topic_auth) {

        var info = '用户"'+nickname+'"发布视频的权限当前为' + (is_topic_auth === 0 ? '【关闭】' : '【开启】') + '，要进行' +
            (is_topic_auth === 0 ? '【开启】' : '【关闭】') + '操作吗？';
        $.ligerDialog.confirm(info, function (ensure) {
            if (ensure) {
                postRequest('/ajax/app/user/publishTopic', {id: id}, function () {
                    renderTable();
                }, function (msg) {
                    $.ligerDialog.error(msg);
                });
            }
        });
    }
</script>

