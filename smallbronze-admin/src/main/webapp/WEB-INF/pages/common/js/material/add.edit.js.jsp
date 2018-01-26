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
    var material_file_suffix = '.zip',material_file_suffix_name = 'zip';
    jQuery(function () {

        /**
         * 图片按钮点击事件
         */
        $('img.file_upload').click(function () {

            $(this).next().trigger('click');
        });

        /**
         * zip图标点击事件
         * */
        $('img.zip_icon').click(function () {

            $('#material_file').click();
        });
        /**
         * 预览图上传
         */
        $('#preview_image').change(function () {

            doUpload(this, $('#preview_image_value'), true);
        });

        /**
         * logo图上传
         */
        $('#logo_image').change(function () {

            doUpload(this, $('#logo_image_value'), true);
        });
        /**
         * 素材文件上传
         */
        $('#material_file').change(function () {

            var categoryType = $(':hidden[name=categoryType]').val();
            if (categoryType !== '1') {
                var file = this.files[0];
                if (!checkFileType(file['name'], material_file_suffix, material_file_suffix_name)) {
                    $.prompt('请上传' + material_file_suffix_name + '文件');
                    return;
                }
            }
            doUpload(this, $('#material_file_value'), false);
        });

        /**
         * 上传到服务器本地
         * @param ths
         * @param hiddenValue
         * @param isImage
         */
        function doUpload(ths, hiddenValue, isImage) {

            var file = ths.files[0];
            if (!file) {
                return;
            }
            upload2Local(file, function (data) {
                if (data) {
                    var upload_result = data[0];
                    hiddenValue.val(upload_result['absolute_path']);
                    if (isImage) {
                        $(ths).prev().prop('src', upload_result['show_url']);
                    } else {
                        $(ths).siblings('span.file_info').children('p').html(file['name']);
                        $(ths).siblings('span.file_info').show();
                        $(ths).siblings('img.file_upload').hide();
                    }
                }
            }, function (msg) {
                $.prompt(msg);
            }, loading, loadingStop);
        }

        $('form#material_form').submit(function () {

            var param = {};
            var id = $('#material_id').val();
            if (stringIsNotNull(id)) {
                param['id'] = id;
            }
            $('input[type=text],input[type=hidden]').each(function () {

                param[this.name] = this.value;
            });
            $('select').each(function () {

                param[this.name] = this.value;
            });
            param['isOnShelf'] = $(':radio:checked').val();
            if (stringIsNull(param['name'])) {
                $.prompt('素材名称不能为空');
                return;
            }
            if (stringIsNull(param['icon'])) {
                $.prompt('请上传素材logo');
                return;
            }
            if (stringIsNull(param['previewUrl'])) {
                $.prompt('请上传素材预览图');
                return;
            }

            if (stringIsNull(param['contentPath'])) {
                $.prompt("请选择素材");
                return;
            }
            var url = '/ajax/material/save';
            if (stringIsNotNull(id)) {
                url = '/ajax/material/update';
            }
            postRequest(url, param, function () {
                loadingStop();
                window.location.href = '/material/index?category_type='+$(':hidden[name=categoryType]').val();
            }, function (msg) {
                $.prompt(msg);
            }, loading, loadingStop);
        });

        //判断是否是分类类型是否是音乐,如果是音乐,则控制选择文件只可以选择音频文件
        if ($(':hidden[name=categoryType]').val() === '1') {
            $('#material_file').prop('accept', 'audio/*');
            $('img.zip_icon').prop('src','/plugin/form/img/audio-icon.jpg')
        }
    });
</script>