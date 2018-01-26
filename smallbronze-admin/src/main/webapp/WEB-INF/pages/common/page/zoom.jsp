<%--
  Created by IntelliJ IDEA.
  User: feifan.gou
  Date: 2017/6/27
  Time: 11:41
  To change this template use File | Settings | File Templates.
  图片悬浮放大
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<style type="text/css">
    .previewShowWindow {
        position: absolute;
        border: 1px solid #dadade;
        background: #95959d;
        padding: 5px;
        display: none;
        color: #fff;
        text-align: center;
        z-index: 10000;
    }
    img.hide_image {
        display: none;
    }
</style>
<img class="hide_image" src="" alt="">
<script type="text/javascript" charset="UTF-8">
    function zoom(e,src) {
        var offX = 25, offY = 25;
        $("body").append("<div id='preview' class='previewShowWindow'><img id='pi' src='' /></div>");
        $("#pi").attr("src", src);
        $('img.hide_image').prop('src', src);
        var pageWidth = $('#framecenter', window.parent.document).width() + $(window).scrollLeft();
        var pageHeight = $('#framecenter', window.parent.document).height() + $(window).scrollTop();
        var imageWidth = $('img.hide_image').width(), imageHeight = $('img.hide_image').height();
        var multiple;
        if (imageHeight > pageHeight) {
            multiple = parseInt(imageHeight / pageHeight) + 2;
            $("#pi").css('height', (imageHeight / multiple) + 'px');
        } else if (imageHeight > 400) {
            $("#pi").css('height', (imageHeight / 3) + 'px');
        }
        if(imageWidth > pageWidth) {
            if (multiple < parseInt(imageWidth / pageWidth) + 2) {
                multiple = parseInt(imageWidth / pageWidth) + 2;
                $("#pi").css('width', (imageWidth / multiple) + 'px');
            }
        } else if (imageWidth > 800) {
            $("#pi").css('width', (imageWidth / 3) + 'px');
        }
        var zoomWidth = $("#preview").width(),
            zoomHeight = $("#preview").height();
        var moveX,moveY;
        if ((e.pageX + offX + zoomWidth) > pageWidth) {
            moveX = e.pageX - (zoomWidth + offX) + "px"
        } else {
            moveX = e.pageX + offX + "px"
        }
        if ((e.pageY + offY + zoomHeight) > pageHeight) {
            moveY = pageHeight - (zoomHeight + offY) + "px"
        } else {
            moveY = e.pageY + offY + "px"
        }
        $("#preview").css("top", moveY).css("left", moveX).fadeIn("fast");

    }
    function imgHide() {
        $("#preview").remove();
    }
</script>
