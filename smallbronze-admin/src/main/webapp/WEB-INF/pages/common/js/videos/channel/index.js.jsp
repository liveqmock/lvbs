<%@ page import="com.daishumovie.base.enums.db.App" %><%--
  Created by IntelliJ IDEA.
  User: feifan.gou
  Date: 2017/9/7
  Time: 16:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<script src="/js/common/base.js" type="text/javascript"></script>
<script src="/plugin/classify/js/jquery.filterizr.min.js"></script>
<script src="/plugin/right-click/contextify.js"></script>
<script type="text/javascript">
    var first_level = '${first_level_id}', add_category;
    { //控制添加按钮
        var addDiv = $('div.add-div');
        addDiv.width('120px');
        addDiv.height('120px');
        addDiv.css('margin-top', '10px');
    }
    $(function () {
        //app 选择
        $('#app_select').children('li').each(function () {

            if ($(this).data('id') == '${app_id}') {
                $(this).addClass('active');
            }
            $('#app_select>li.active').css('background-color', '#43CD80');
        });
        //切换APP
        $('#app_select').children('li').click(function () {

            if ($(this).data('id') != '${app_id}') {
                window.location.href = '/channel/index?appId=' + $(this).data('id');
            }
        });
        //渲染列表
        if ($('div.filtr-container').children('div').length > 1) {
            add_category = $('div.add-div').data('category');
//            $('div.add-div').removeAttr('data-category');
            try {
                $('div.filtr-item').children('img').width('120px');
                $('div.filtr-item').children('img').height('120px');
                $('div.filtr-item').height('120px');
                $('div.filtr-item').width('120px');
                $('div.filtr-item').css('margin-top', '10px');
                $('.filtr-container').filterizr();
            } catch (e) {
                console.error("filter error");
            }
        }
        //一级频道点击
        $('ul.simple_filter>li[id!=add_first_level]').click(function () {

            $(this).addClass('active');
            first_level = $(this).data('filter');
            $(this).siblings('li').removeClass('active');

//            if ($(this).data('filter') == 'all') {
//                $('div.add-div').addClass('filteredOut');
//            } else {
//                $('div.add-div').removeClass('filteredOut');
//            }
        });
        //一级频道默认
        $('ul.simple_filter>li[id!=add_first_level]').each(function () {

            var filter = $(this).data('filter');
            if (filter == first_level) {
                $(this).trigger('click');
            }
        });
        //一级频道添加
        $('#add_first_level').click(function () {

            window.location.href = '/channel/initAdd?pid=0&appId=${app_id}';
        });
        //二级频道添加
        $('#add_second_level').click(function () {

            if ($('ul.simple_filter').eq(1).children('li.active').data('filter') === 'all') {
                $.prompt('请选择一级频道');
            } else {
                window.location.href = '/channel/initAdd?pid=' + first_level + '&appId=${app_id}';
            }
        });
        //删除二级频道
        $('img.channel_delete').click(function () {
            var id = $(this).data('id');
            var name = $(this).data('name');
            $.confirm({
                title: '隐藏频道',
                message: '确定要隐藏频道【' + name + '】?',
                buttons: {
                    Yes: {
                        'class': 'blue',
                        action: function () {
                            loading();
                            postRequest('/ajax/channel/delete', {id: id}, function () {
                                window.location.href = '/channel/index?appId=${app_id}&firstLevelId=' + first_level;
                            }, function (msg) {
                                $.prompt(msg);
                            });
                        }
                    },
                    No: {
                        'class': 'gray',
                        action: function () {

                        }
                    }
                }
            });
        });
        //编辑二级频道
        $('img.channel_edit').click(function () {

            window.location.href = "/channel/initEdit?id=" + $(this).data('id');
        });

        {//右击操作
            var option = {
                items: [
//                {header: '操作'},
//                {divider: true},
                    {
                        text: '编辑', onclick: function () {
                        window.location.href = "/channel/initEdit?id=" + $('#hover_id').val();
                    }
                    },
//                    {
//                        text: '隐藏', onclick: function () {
//                        var name = $('#hover_name').val();
//                        $.confirm({
//                            title: '隐藏频道',
//                            message: '隐藏一级频道【' + name + '】其下面所有的二级菜单都会被隐藏，确定要隐藏吗？',
//                            buttons: {
//                                Yes: {
//                                    'class': 'blue',
//                                    action: function () {
//                                        loading();
//                                        postRequest('/ajax/channel/delete', {id: $('#hover_id').val()}, function () {
//                                            window.location.reload();
//                                        }, function (msg) {
//                                            $.prompt(msg);
//                                        });
//                                    }
//                                },
//                                No: {
//                                    'class': 'gray',
//                                    action: function () {
//
//                                    }
//                                }
//                            }
//                        });
//                    }
//                    }
                ]
            };
            $('ul.simple_filter:eq(1)>li[id!=add_first_level][data-filter!=all]').contextify(option);
        }
        { //二级频道的hover
            //一级频道hover事件
            $('ul.simple_filter>li[id!=add_first_level][data-filter!=all]').hover(function () {

                $('#hover_id').val($(this).data('filter'));
                $('#hover_name').val($(this).html());
            });
            $('div.normal>img').hover(function () {
                $(this).siblings('.operation_span').show();
            }, function () {
                $(this).siblings('.operation_span').hide();
            });

            $('div.normal>.operation_span').hover(function () {
                $(this).show();
            }, function () {
                $(this).hide();
            });

            $('div.normal>.item-desc').hover(function () {
                $(this).siblings('.operation_span').show();
            }, function () {
                $(this).siblings('.operation_span').hide();
            });
        }

        { //小铜人部分功能隐藏隐藏
            var appId = '${app_id}';
            if (!appId  || appId == '<%=App.app_small_bronze.getId()%>') {
//                $('#add_first_level').hide();
                $('img.channel_delete').hide();
            }
        }
    });
</script>
