<%--
  Created by IntelliJ IDEA.
  User: feifan.gou
  Date: 2017/9/4
  Time: 18:00
  To change this template use File | Settings | File Templates.
  album 的js
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<script>
    jQuery(function () {

        $('img.file_upload').click(function () {

            $(this).siblings(':file').trigger('click');
        });

        $('img.file_upload').siblings(':file').change(function () {

            var file = this.files[0];
            var hiddenValue = $(this).siblings(':hidden');
            var img_placeholder = $(this).siblings('img');
            var standard_width = 750,standard_height = 375, standard_size = 200;
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
        $('#album_form').submit(function () {

            var param = {};
            $(this).find('input[type=text],input:hidden').each(function () {
                param[this.name] = $(this).val();
            });
            if (stringIsNull(param['title'])) {
                $.prompt('合辑标题不能为空');
                return;
            } else {
                if(param['title'].length > 10) {
                    $.prompt('合辑标题字数请保证在10个以内');
                    return;
                }
            }
            if (stringIsNull(param['subtitle'])) {
                $.prompt('活动副标题不能为空');
                return;
            } else {
                if(param['subtitle'].length > 30) {
                    $.prompt('活动副标题字数请保证在30个以内');
                    return;
                }
            }
            if (stringIsNull(param['cover'])) {
                $.prompt('合辑封面不能为空');
                return;
            }
            var url = '/ajax/album/save';
            if ($('#album_id').length) {
                url = '/ajax/album/update';
            }
            postRequest(url, param, function () {
                var search_param = [];
                $('div.param_div').children(':hidden').each(function () {
                    search_param.push(this.name + '=' + this.value);
                });
                location.href = '/album/index?' + search_param.join('&');
            },function (msg) {
                $.prompt(msg);
            }, loading, loadingStop);
        });
    });
</script>