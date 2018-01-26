<%--
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
        $(':radio[name=is_default]').change(function () {

            if ($(this).val() == '0') {
                $('div.video_div').show();
                $('div.old_cover').hide();
                $('video').trigger('play');
            } else {
                $('video').trigger('pause');
                $('div.old_cover').show();
                $('div.video_div').hide();
            }
        });
        $(':radio[name=is_re_upload]').change(function () {

            if ($(this).val().toString() === '<%=Whether.yes.getValue()%>') {
                $(':radio[name=is_default][value=<%=Whether.yes.getValue()%>]').click();
                $('#upload_again').click();
            } else {
                $('#new_cover_value').val('');
                $('.old_cover img').prop('src', $('#old_cover_value').val());
                $(':radio[name=is_default][value=<%=Whether.yes.getValue()%>]').click();
            }
        });
        $('#topic_form').on('submit', function () {

            var title = $('#title').val();
            if (stringIsNull(title)) {
                $.prompt('标题不能为空');
                return;
            }
            if (title.length > 30) {
                $.prompt('标题长度不能大于30');
                return;
            }
            if ($(':radio[name=is_re_upload][value=<%=Whether.yes.getValue()%>]:checked').length) {
                if (stringIsNull($('#new_cover_value').val())) {
                    $.prompt('请重新上传封面，否则请关闭【重新上传】开关');
                    return;
                }
            }
            var param = {
                title: title,
                topicId: $(':hidden[name=id]').val(),
                isDefault: $(':radio[name=is_default]:checked').val() == '1',
                coverTime: $('video').eq(0).prop('currentTime'),
                uploadCover:$('#new_cover_value').val()
            };
            postRequest('/ajax/topic/edit', param, function () {
                location.href = '/topic/publish?from_edit=true';
            }, function (msg) {
                $.prompt(msg);
            }, loading, loadingStop);
        });
        if ($('video').height() > 300) {
            $('video').height(300);
        }
        $('#upload_again').change(function () {

            var file = this.files[0];
            var hiddenValue = $('#new_cover_value');
            var img_placeholder = $('.old_cover img');
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
</script>