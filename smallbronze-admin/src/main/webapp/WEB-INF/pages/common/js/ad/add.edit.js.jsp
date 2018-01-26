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
        $('img.file_upload').click(function () {

            $(this).siblings(':file').trigger('click');
        });

        $('img.file_upload').siblings(':file').change(function () {

            var file = this.files[0];
            var hiddenValue = $(this).siblings(':hidden');
            var img_placeholder = $(this).siblings('img');
            var standard_width = 1000 ,standard_height = 500, standard_size = 200;

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

        $('#ad_form').submit(function () {
            var param = {};
            $(this).find('input[type=text],input:hidden,input[type=number]').each(function () {
                param[this.name] = $(this).val();
            });
            if (stringIsNull(param['name'])) {
                $.prompt('广告名称不能为空');
                return;
            } else {
                if(param['name'].length > 20) {
                    $.prompt('广告名称字数请保证在20个以内');
                    return;
                }
            }
            param['adType'] = $('#ad_type').val();
            param['orders'] = $('#orders').val();
            param['targetType'] = $('#target_type').val();
            if (stringIsNull(param['orders'])) {
                $.prompt('广告位置不能为空');
                return;
            }

            if (stringIsNull(param['adType'])) {
                $.prompt('广告类型不能为空');
                return;
            }
            if (stringIsNull(param['targetType'])) {
                $.prompt('跳转类型不能为空');
                return;
            }
            if (stringIsNull(param['target'])) {
                $.prompt('跳转地址不能为空');
                return;
            }
            if(param['targetType'] == 4 && stringIsNotNull(param['target'])) {
                var Expression=/http(s)?:\/\/([\w-]+\.)+[\w-]+(\/[\w- .\/?%&=]*)?/;
                var objExp=new RegExp(Expression);
                if(objExp.test(param['target']) != true){
                    $.prompt('网址格式不正确');
                    return;
                }
            }

            if (stringIsNull(param['adCover'])) {
                $.prompt('活动封面不能为空');
                return;
            }
            if (stringIsNull(param['startTime'])) {
                $.prompt('开始时间不能为空');
                return;
            } else {
                param['startTime'] = new Date(param['startTime']);
            }
            if (stringIsNull(param['endTime'])) {
                $.prompt('结束时间不能为空');
                return;
            } else {
                param['endTime'] = new Date(param['endTime']);
            }

            var url = '/ajax/ad/save';
            if ($('#ad_id').length) {
                url = '/ajax/ad/update';
            }

            postRequest(url, param, function () {
                if ($('div.param_div') && $('div.param_div').length) {
                    var param = [];
                    $('div.param_div').children('input:hidden').each(function () {
                        param.push(this.name + '=' + this.value);
                    });
                    location.href = '/ad/index?'+ param.join('&');
                } else {
                    location.href = '/ad/index';
                }
            },function (msg) {
                $.prompt(msg);
            }, loading, loadingStop);
        });

        $('#ad_type').on("change", function(){
            var data = {0:["0"], 1:["1","2","3","4","5"]};
            var adType = $(this).val();
            var detail = $('#orders');
            detail.empty();
            if(adType){//选了具体的值才改变下面的 选择框的值
                $.each(data[adType], function(){
                    //把具体的值追加到第二个下拉框
                    $('<option> ' + this+'</option>').appendTo(detail);
                });
            }
        });

    });
    var $eidtor = $('#instruction');
</script>