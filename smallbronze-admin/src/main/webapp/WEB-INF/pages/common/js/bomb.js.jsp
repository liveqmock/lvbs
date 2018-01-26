<%--
  Created by IntelliJ IDEA.
  User: feifan.gou
  Date: 2017/9/28
  Time: 16:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<script type="text/javascript">
    var w,h,className;
    function getScreenWH(){
        w = $(window).width();
        h = $(window).height();
        $('#dialogBg').width(w).height(h);
    }

    window.onresize = function(){
        getScreenWH();
    };
    $(window).resize();

    function showBomb() {
        $('#dialogBg').fadeIn(300);
        $('#dialog').removeAttr('class').addClass('animated bounceInDown').fadeIn();
    }

    $(function(){
        getScreenWH();

        //显示弹框
        $('.box a').click(function(){
            className = $(this).attr('class');
            $('#dialogBg').fadeIn(300);
            $('#dialog').removeAttr('class').addClass('animated '+className+'').fadeIn();
        });

        //关闭弹窗
        $('.closeDialogBtn').click(function(){
            $('#dialogBg').fadeOut(300,function(){
                $('#dialog').addClass('bounceOutUp').fadeOut();
            });
        });
    });
</script>
