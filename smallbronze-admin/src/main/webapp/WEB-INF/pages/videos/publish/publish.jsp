<%--
  Created by IntelliJ IDEA.
  User: feifan.gou
  Date: 2017/9/28
  Time: 15:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/pages/common/style/bomb.css.jsp"%>
<%@ include file="/WEB-INF/pages/common/js/bomb.js.jsp"%>
<link rel="stylesheet" charset="UTF-8" href="/plugin/switch/css/honeySwitch.css">
<script type="text/javascript" charset="UTF-8" src="/plugin/switch/js/honeySwitch.js"></script>
<style>
    #dialog {
        width: 600px;
        height: 545px;
        display: none;
        background-color: #ffffff;
        position: fixed;
        top: 26%;
        left: 40%;
        margin: -120px 0 0 -150px;
        z-index: 100;
        border: 1px solid #ccc;
        border-radius: 10px;
        -webkit-border-radius: 10px;
        box-shadow: 3px 2px 4px rgba(0, 0, 0, 0.2);
        -webkit-box-shadow: 3px 2px 4px rgba(0, 0, 0, 0.2);
    }
    video {
        width: 300px;
        height: 200px;
    }
    span.video-span {
        float: left;
        margin-top: 93px;
    }
    span.cover-span {
        float: left;
        margin-top: 46px;
    }
    li.video-li {
        display: none;
    }
    li.cover-li {
        margin-bottom: 40px;
    }
    span#is-default-switch {
        margin-right: 305px;
    }
    img#video_cover {
        margin-top: 3px;
    }
    #upload_again {
        margin-top: -33px;
        margin-left: 317px;
        display: none;
    }
    #new_video_cover {
        display: none;
    }
    span#is-re-upload{
        margin-left: -331px;
    }
</style>
<div id="dialogBg"></div>
<div id="dialog" class="animated">
    <h4 class="dialogTitle">发布视频</h4>
    <div class="dialogTop">
        <a href="javascript:;" class="closeDialogBtn">关闭</a>
    </div>
    <form action="" method="post" id="editForm">
        <input type="hidden" name="id" value=""/>
        <ul class="editInfo">
            <li>
                <label><span class="channel-span"><font color="#ff0000">* </font>频道：</span>
                    <select name="channelId" id="channel_id" class="ipt" required style="width: 313px;">
                        <%--<c:forEach items="${channel_list}" var="first_level">--%>
                            <%--<optgroup label="${first_level.name}">--%>
                                <%--<c:forEach items="${first_level.children}" var="child">--%>
                                    <%--<option value="${child.id}">${child.name}</option>--%>
                                <%--</c:forEach>--%>
                            <%--</optgroup>--%>
                        <%--</c:forEach>--%>
                        <c:forEach items="${channel_list}" var="channel">
                            <option value="${channel.id}">${channel.name}</option>
                        </c:forEach>
                    </select>
                </label>
            </li>
            <li><label><span class="title-span"><font color="#ff0000">* </font>标题：</span><input id="title" type="text" name="title" required value="" class="ipt" /></label></li>
            <li><label><span class="publisher-span"><font color="#ff0000">* </font>发布人：</span><input readonly id="publisher" type="text" name="publisher" required value="" class="ipt" /></label></li>
            <li><label><span class="is-default-span"><font color="#ff0000">* </font>是否默认封面：</span><span id="is-default-switch" class="switch-on"></span></label></li>
            <li><label><span class="is-default-span"><font color="#ff0000">* </font>是否重新上传封面：</span><span id="is-re-upload" class="switch-off"></span></label><button type="button" id="upload_again" class="submitBtn">重新上传</button></li>
            <li class="video-li"><label><span class="video-span"><font color="#ff0000">* </font>选择封面：</span><video width="200px" height="100px" controls src=""></video></label></li>
            <li class="cover-li"><label><span class="cover-span"><font color="#ff0000">* </font>当前封面：</span><img src="" id="video_cover"></label></li>
            <li><input type="submit" id="submit_btn" value="确认提交" class="submitBtn" /></li>
            <input type="hidden" id="cover_is_empty">
            <input type="file" id="new_video_cover" accept="image/jpeg,image/jpg,image/png">
            <input type="hidden" id="new_video_cover_value" value="" name="newCover"/>
        </ul>
    </form>
    <input type="hidden" id="old_video_cover_value" value=""/>
</div>
