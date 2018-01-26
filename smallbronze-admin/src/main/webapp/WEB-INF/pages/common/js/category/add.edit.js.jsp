<%--
  Created by IntelliJ IDEA.
  User: feifan.gou
  Date: 2017/9/4
  Time: 18:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<script>
    jQuery(function () {

        /**
         * 图片按钮点击事件
         */
        $('img.file_upload').click(function () {

            $(this).next().trigger('click');
        });

        /**
         * logo图上传
         */
        $('#logo_image').change(function () {

            var file = this.files[0];
//            console.log(checkImageInfo(file, 100, 200));
            doUpload(this, $('#logo_image_value'), true);
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

        $('form#category_form').submit(function () {

            var param = {};
            var id = $('#category_id').val();
            if (stringIsNotNull(id)) {
                param['id'] = id;
            }
            $('input[type=text],input[type=hidden]').each(function () {

                param[this.name] = this.value;
            });
            $('select').each(function () {

                param[this.name] = this.value;
            });
            if (stringIsNull(param['name'])) {
                $.prompt('分类名称不能为空');
                return;
            }
            if (stringIsNull(param['icon'])) {
                $.prompt('请上传logo');
                return;
            }
            param['remarks'] = $('textarea[name=remarks]').val();
            var url = '/ajax/category/save';
            if (stringIsNotNull(id)) {
                url = '/ajax/category/update';
            }
            postRequest(url, param, function () {
                loadingStop();
                window.location.href = '/category/index';
            },function (msg) {
                $.prompt(msg);
            },loading,loadingStop);
        });
    });
</script>