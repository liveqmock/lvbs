<%--
  Created by IntelliJ IDEA.
  User: feifan.gou
  Date: 2017/9/8
  Time: 10:53
  To change this template use File | Settings | File Templates.
  频道添加,编辑js
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<script>
    jQuery(function () {

        if ('0' !== $(':hidden[name=pid]', '#channel_form').val()) {
            $('#cd-name').prop('placeholder', '           字数长度【1-14】');
        } else {
            $('#cd-name').prop('placeholder', '           字数长度【1-4】');
        }

        /**
         * 图片按钮点击事件
         */
        $('img.file_upload').click(function () {

            $(this).next().trigger('click');
        });


        /**
         * 默认空墙图上传
         */
        $('#default_url').change(function () {

            doUpload(this, $('#default_url_value'), true);
        });

        /**
         * logo图上传
         */
        $('#logo_image').change(function () {

            doUpload(this, $('#logo_image_value'), true);
        });


        {//只有一级频道才需要显示空墙默认图
            if ('0' !== $(':hidden[name=pid]', '#channel_form').val()) {
                $('default-url').hide();
            }
        }

        { //校验名称字数
            $('#cd-name').blur(function () {
                checkNameLength(this);
            });
        }
        function checkNameLength(ele) {
            var lengthMsg = '频道名称请保证长度【1-4】';
            var minLength = 1, maxLength = 4;
            if ('0' !== $(':hidden[name=pid]', '#channel_form').val()) {
                maxLength = 14;
                lengthMsg = '频道名称请保证长度【1-14】';
            }
            var value = stringTrim($(ele).val());
            if (!(value.length <= maxLength && value.length >= minLength)) {
                $.prompt(lengthMsg);
                return false;
            }
            return true;
        }
        /**
         * 上传到服务器本地
         * @param ths
         * @param hiddenValue
         * @param isImage
         */
        function doUpload(ths, hiddenValue) {

            var file = ths.files[0];
            if (!file) {
                return;
            }
            upload2Local(file, function (data) {
                if (data) {
                    var upload_result = data[0];
                    hiddenValue.val(upload_result['absolute_path']);
                    $(ths).prev().prop('src', upload_result['show_url']);
                }
            }, function (msg) {
                $.prompt(msg);
            }, loading, loadingStop);
        }

        $('form#channel_form').submit(function () {

            var param = {};
            var id = $('#channel_id').val();
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
                $.prompt('频道名称不能为空');
                return;
            }
            if (!checkNameLength($('#cd-name'))) {
                return false;
            }
            if (stringIsNull(param['url'])) {
                $.prompt('请上传logo');
                return;
            }
            param['remarks'] = $('textarea[name=remarks]').val();
            var url = '/ajax/channel/save', firstLevelId = param['pid'];
            if (stringIsNotNull(id)) {
                if(param['pid'] == '0'){
                    firstLevelId = param['id'];
                }
                url = '/ajax/channel/update';
            } else {
                if(param['pid'] == '0'){
                    firstLevelId = -999;
                }
            }
            postRequest(url, param, function () {
                loadingStop();
                window.location.href = '/channel/index?appId=' + param['appId'] + '&firstLevelId=' + firstLevelId;
            },function (msg) {
                $.prompt(msg);
            },loading,loadingStop);
        });
    });
</script>