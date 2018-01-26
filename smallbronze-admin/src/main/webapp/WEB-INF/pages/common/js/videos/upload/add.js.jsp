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
        /**
         * 点击上传icon
         */
        $('img.upload_icon').click(function () {

            $(this).siblings(':file.upload_file_input').trigger('click');
        });
        /**
         * 方式切换
         */
        $(':radio[name=upload_method]').change(function () {

            var radioId = $(this).prop('id');
            if (radioId == 'upload_local') {
                $('#upload_progress').show();
            } else {
                $('#upload_progress').hide();
            }
            $('div.origin_data>div[class!=' + radioId + '_div]').slideUp(function () {

                $(this).find(':text').removeAttr('required');
            });
            $('div.add_data').html('');
            $('div.' + radioId + '_div').slideDown(function () {
                $(this).find(':text').prop('required', true);
            });
            $(':text[name=title]').css('border', '');
            $(':text[name=title],:text[name=url]').val('');
        });
        /**
         * 新建
         */
        $('#create_data').click(function () {

            var currentRadioId = $(':radio[name=upload_method]:checked').prop('id');
            var children = $('div.' + currentRadioId + '_div_backup').children('*').clone();
            var newDataDiv = $('<div/>', {
                'class': 'data_div'
            });
            children.find('input').on('keydown', function () {
                $(this).siblings('label').addClass('float');
            });
            children.find(':text[name=title]').on('focus', function () {
                $(this).css('border', '');
            });
            children.find(':file').on('change', function () {
                var fileName = $(this).prop('files')[0]['name'];
                $(this).parent().siblings('h4').html(fileName);
                $(this).parent().siblings('div.icon').children('label').addClass('float');
                $(this).parent().siblings('div.icon').children('input[name=title]').val(fileName.substring(0, fileName.lastIndexOf(".")));
            });
            children.find('img.remove_img').on('click', function () {
                $(this).parents('div.data_div').slideUp(function () {
                    $(this).remove();
                });
            });
            children.find('img.upload_icon').on('click', function () {
                $(this).siblings(':file.upload_file_input').trigger('click');
            });
            children.appendTo(newDataDiv);
            newDataDiv.appendTo($('div.add_data'));
        });
        /**
         * 提交
         */
        $('#upload_form').on('submit', function () {
            var method = $(':radio[name=upload_method]:checked').val();
            var radioId = $(':radio[name=upload_method]:checked').prop('id');
            var title = $('div.' + radioId + '_div').find(':text[name=title]').val();
            if (title.length > 30) {
                $('div.' + radioId + '_div').find(':text[name=title]').css('border', '2px solid red')
                $.prompt('视频标题长度不能超过30个字');
                return false;
            }
            var titles = [title];
            var flag = true;
            //远程/爬虫
            if (method == '<%=VideoSource.remote.getValue()%>' || method == '<%=VideoSource.crawler.getValue()%>') {
                var urls = [$('div.' + radioId + '_div').find(':text[name=url]').val()];
                $('div.add_data').children('div.data_div').each(function () {
                    title = $(this).find(':text[name=title]').val();
                    if (title.length > 30) {
                        $(this).find(':text[name=title]').css('border', '2px solid red');
                        $.prompt('视频标题长度不能超过30个字');
                        flag = false;
                        return flag;
                    }
                    titles.push(title);
                    urls.push($(this).find(':text[name=url]').val());
                });
                if (!flag) {
                    return false;
                }
                var param = {
                    titles: titles,
                    source: method,
                    urls: urls
                };
                postRequest('/ajax/upload/v/saveVideoInfo', param, function () {
                    location.href = '/upload/v/index';
                }, function (msg) {
                    $.prompt(msg);
                }, loading, loadingStop);
            } else { //本地上传
                var files = [$('div.' + radioId + '_div').find(':file').prop('files')[0]];
                $('div.add_data').children('div.data_div').each(function () {
                    title = $(this).find(':text[name=title]').val();
                    if (title.length > 30) {
                        $(this).find(':text[name=title]').css('border', '2px solid red');
                        $.prompt('视频标题长度不能超过30个字');
                        flag = false;
                        return flag;
                    }
                    titles.push(title);
                    files.push($(this).find(':file').prop('files')[0]);
                });
                if (!flag) {
                    return false;
                }
                var formData = new FormData();
                $.each(files, function () {
                    formData.append("file[]", this);
                });
                formData.append("titles", titles);
                loading();
                $.ajax({
                    url: "/ajax/upload/v/saveLocalVideo",
                    type: "POST",
                    data: formData,
                    processData: false,  // 不处理数据
                    contentType: false,   // 不设置内容类型
                    xhr: function () { //获取ajaxSettings中的xhr对象，为它的upload属性绑定progress事件的处理函数
                        var uploadXhl = $.ajaxSettings.xhr();
                        if (uploadXhl.upload) { //检查upload属性是否存在
                            //绑定progress事件的回调函数
                            uploadXhl.upload.addEventListener('progress', function (e) {
                                if (e['lengthComputable']) {
                                    $('progress').attr({value: e['loaded'], max: e.total}); //更新数据到进度条
                                    var percent = parseInt(e['loaded'] / e.total * 100);
                                    $('#upload_progress').find('p').css('width', percent + '%');
                                    $('#upload_progress').find('p').prop('dataset').value = percent;
                                    $('#upload_progress').find('span').css('width', percent + '%');
                                    $('#upload_progress').find('span').html(percent + '%');

                                }
                            }, false);
                        }
                        return uploadXhl; //xhr对象返回给jQuery使用
                    },
                    success: function (result) {
                        loadingStop();
                        handlerResult(result, function () {
                            location.href = '/upload/v/index';
                        }, function (msg) {
                            $.prompt(msg);
                        });
                    },
                    error: function (data) {
                        loadingStop();
                        console.log(data.status);
                    }
                });
            }
        });
        /**
         * 选择文件
         */
        $(':file.upload_file_input').change(function () {

            var fileName = $(this).prop('files')[0]['name'];
            $(this).parent().siblings('h4').html(fileName);
            $(this).parent().siblings('div.icon').children('label').addClass('float');
            $(this).parent().siblings('div.icon').children('input[name=title]').val(fileName.substring(0, fileName.lastIndexOf(".")));
        });
        /**
         * 取消校验
         */
        $(':text[name=title]').focus(function () {
            $(this).css('border', '');
        });

    });
</script>