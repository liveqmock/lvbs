<%@ page import="com.daishumovie.base.enums.db.PushTargetType" %><%--
  Created by IntelliJ IDEA.
  User: feifan.gou
  Date: 2017/9/4
  Time: 18:00
  To change this template use File | Settings | File Templates.
  material 的js
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<script>
    jQuery(function () {
        $('#target_type').change(function () {
            var value = $(this).val().toString();
            if (value === '<%=PushTargetType.video.getValue()%>') {
                $('#target_id_div>label.cd-label').html('视频ID');
            } else if(value === '<%=PushTargetType.activity.getValue()%>') {
                $('#target_id_div>label.cd-label').html('活动ID');
            } else if(value === '<%=PushTargetType.album.getValue()%>') {
                $('#target_id_div>label.cd-label').html('合辑ID');
            }
        });
        $(':radio[name=way]').change(function () {
            var value = $(this).val().toString();
            if (value === '<%=PushWay.immediately.getValue()%>') {
                $('div.push_time').hide();
                $('#push_time').val('');
            } else {
                $('div.push_time').show();
            }
        });
        $('#push_form').submit(function () {
            var param = {};
            $(this).find('input[type=text]:visible').each(function () {
                param[this.name] = this.value;
            });
            param['targetType'] = $('#target_type').val();
            param['way'] = $(':radio[name=way]:checked').val();
            param['alert'] = $('textarea#alert').val();
            if (stringIsNull(param['alert'])) {
                $.prompt('推送文案不能为空');
                return;
            } else {
                if (param['alert'].length > 40) {
                    $.prompt('推送文案不能超过40个字');
                    return;
                }
            }
            var platformList = $(':checkbox[name=platform]:checked');
            if (!platformList.length) {
                $.prompt('请选择推送平台');
                return;
            }
            if (platformList.length === 1) {
                param['platform'] = $(':checkbox[name=platform]:checked').val();
            } else {
                param['platform'] = '<%=PushPlatform.all.getValue()%>';
            }
            if (param['way'] === '<%=PushWay.schedule.getValue()%>') {
                if(stringIsNull($('#push_time').val())){
                    $.prompt('请选择推送时间');
                    return;
                }
                param['pushTime'] = new Date(param['pushTime']);
            } else {
                param['pushTime'] = new Date();
            }
            console.log(param);
            postRequest('/ajax/push/push', param, function () {
                location.href = '/push/index';
            },function (msg) {
                $.prompt(msg)
            },loading, loadingStop);
        });
        $('img.search_img').click(function () {
            var target_id = $('#target_id');
            var label = target_id.siblings('label').html();
            var value = target_id.val();
            if (stringIsNull(value)) {
                $.prompt(label + '不能为空');
                return;
            }
            if (~~value === 0 || parseInt(value) > ~~'<%=Integer.MAX_VALUE%>') {
                $.prompt('请输入正确的'+label);
                return;
            }
            var targetType = $('#target_type').val();
            $.getJSON('/ajax/push/getAlert', {targetType: targetType, targetId: value}, function (data) {
                if (stringIsNotNull(data['error'])) {
                    $.prompt(data['error']);
                    $('textarea#alert').val('');
                    $('textarea#alert').siblings('label').removeClass('float');
                } else {
                    $('textarea#alert').val(data['alert']);
                    $('textarea#alert').siblings('label').addClass('float');
                }
            });
        });
    });
</script>