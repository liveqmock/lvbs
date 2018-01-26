<%--
  Created by IntelliJ IDEA.
  User: feifan.gou
  Date: 2017/9/4
  Time: 18:26
  To change this template use File | Settings | File Templates.
  公共的页面加载之后的js 因为需要页面加载完毕,所以放在文件底部
--%>
<%@ page contentType="text/html;charset=UTF-8"%>
<script>
    (function () {
        /**
         * 关闭事件
         */
        $('span.close-span').click(function () {

            $(this).off('click');
            window.history.back();
        });
    })(jQuery);
</script>