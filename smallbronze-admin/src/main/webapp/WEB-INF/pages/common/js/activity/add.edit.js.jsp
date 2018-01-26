<%@ page import="com.daishumovie.admin.service.impl.ActivityService" %><%--
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
            var standard_width = 750,standard_height = 375, standard_size = 200;
            if (!$(this).prop('name')) {
                standard_width = 345;
                standard_height = 345;
            }
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
        $('#activity_form').submit(function () {
            var param = {};
            $(this).find('input[type=text],input:hidden,textarea').each(function () {
                param[this.name] = $(this).val();
            });
            if (stringIsNull(param['title'])) {
                $.prompt('活动标题不能为空');
                return;
            } else {
                if(param['title'].length > 18) {
                    $.prompt('活动标题字数请保证在18个以内');
                    return;
                }
            }
            if (stringIsNull(param['topic'])) {
                $.prompt('活动话题不能为空');
                return;
            } else {
                if(param['topic'].length > 8) {
                    $.prompt('活动话题字数请保证在8个以内');
                    return;
                }
            }
            if (stringIsNull(param['description'])) {
                $.prompt('活动描述不能为空');
                return;
            } else {
                if(param['description'].length > 40) {
                    $.prompt('活动描述字数请保证在40个以内');
                    return;
                }
            }
            if (stringIsNull(param['cover'])) {
                $.prompt('活动封面不能为空');
                return;
            }
            if (stringIsNull(param['thumbCover'])) {
                $.prompt('活动缩略图不能为空');
                return;
            }
            if (stringIsNotNull(param['preTime'])) {
                param['preTime'] = new Date(param['preTime']);
            } else {
                param['preTime'] = new Date('<%=ActivityService.java_start_time%>');
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
            var url = '/ajax/activity/save';
            if ($('#activity_id').length) {
                url = '/ajax/activity/update';
            }
            { //富文本获取
                var instructionVal = $('div.Editor-editor').text();
//                if ($('div.Editor-editor').find('*').length) {
//                    $('div.Editor-editor').find('*').each(function () {
//
//                        if (!stringIsNull($(this).text())) {
//                            instructionVal += $(this).text();
//                        }
//                    });
//                } else {
//                    instructionVal = $('div.Editor-editor').text();
//                }
                if (stringIsNull(instructionVal)) {
                    $.prompt('请填写活动说明');
                    return;
                }
                if (instructionVal.length > 500) {
                    $.prompt('活动说明字数请保证在500个以内【当前字数' + instructionVal.length + '】');
                    return;
                }
                param['instruction'] = getTextVal();
            }
//
            postRequest(url, param, function () {
                if ($('div.param_div') && $('div.param_div').length) {
                    var param = [];
                    $('div.param_div').children('input:hidden').each(function () {
                        param.push(this.name + '=' + this.value);
                    });
                    location.href = '/activity/index?'+ param.join('&');
                } else {
                    location.href = '/activity/index';
                }
            },function (msg) {
                $.prompt(msg);
            }, loading, loadingStop);
        });
        $('#description').on('keyup', function () {
            $(this).siblings('label').addClass('float');
        });
        $('#instruction_label').addClass('float');
        if ($('#instruction_val').length) {
            setTextVal('${activity.instruction}');
        }
    });
    var $eidtor = $('#instruction');
</script>