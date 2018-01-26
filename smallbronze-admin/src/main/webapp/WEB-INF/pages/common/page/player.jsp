<%--
  Created by IntelliJ IDEA.
  User: feifan.gou
  Date: 2017/9/22
  Time: 18:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<link href="/plugin/player/css/videoCT.css" rel="stylesheet">
<script type="text/javascript" charset="UTF-8" src="/plugin/player/js/playerCT.js"></script>
<style>
    #background_mask {
        height: 100%;
        width: 100%;
        background-color: #000;
        position: fixed;
        top: 0;
        -moz-opacity: 0.5;
        opacity: 1;
        z-index: 999;
        display: none;
    }
    div.layout_div {
        display: none;
    }
    .closes_span {
        color: white;
        float: right;
        margin-right: 20px;
        margin-top: 10px;
        font-size: 40px;
        cursor: pointer;
    }
</style>
<div class="layout_div">
    <div id="background_mask">
        <span class="closes_span" onclick="player_close()">×</span>
        <section>
            <video width="100%" height="100%" id="player"></video>
        </section>
    </div>
</div>
<script>
    /*window.onload = function () {

        //初始化
        var video;
        //扩展
        video.title;                    //标题
        video.status;                   //状态
        video.currentTime;              //当前时长
        video.duration;                 //总时长
        video.volume;                   //音量
        video.clarityType;              //清晰度
        video.claritySrc;               //链接地址
        video.fullScreen;               //全屏
        video.reversal;                 //镜像翻转
        video.playSpeed;                //播放速度
        video.cutover;                  //切换下个视频是否自动播放
        video.commentTitle;             //弹幕标题
        video.commentId;                //弹幕id
        video.commentClass;             //弹幕类型
        video.commentSwitch;            //弹幕是否打开
    }*/
</script>
<script>
    //播放
    function play(link, poster, title) {
        if (stringIsNull(link)) {
            $.ligerDialog.warn("视频链接为空");
            return;
        }
        if (stringIsNull(poster)) {
            poster = '/plugin/player/img/default_cover';
        }
        if (stringIsNull(title)) {
            title = '小铜人视频';
        }
        {//清除旧的
            $('div.video-player').remove();
            $('section').html('<video width="100%" height="100%" id="player"></video>');
        }
        { //初始化播放器
            $('#player').videoCt({
                title: title,              //标题
                volume: 0.2,                //音量
                barrage: false,              //弹幕开关
                comment: false,              //弹幕
                reversal: true,             //镜像翻转
                playSpeed: true,            //播放速度
                update: false,               //下载
                autoplay: false,            //自动播放
                clarityType:false,
                clarity: {
                    type: ['1024P'],            //清晰度
                    src: [link]      //链接地址
                },
                commentFile: '',           //导入弹幕json数据
                cutover:false
            });
            //清晰度隐藏
            $('div.video-clarity').hide();
        }
        $('div.layout_div').show();
        $('#background_mask').slideDown(1500);
        { //播放器归零
//            $('#player').prop('src',link);
//            $('#player').prop('poster', poster);
//            $('div.video-timer').children('span').eq(1).html(duration);
//            setTimeout(function () {
//                $('div.video-title').html(title);
//            },1000);
//            $('div.video-timer>span.realTime').html('00:00');
//            $('div.video-seek').width('0px');
        }
//        $('#player').trigger('play');
//        $('a.video-play').trigger('click');
//        video['title'] = title;
    }
    //暂停
    function player_close() {
        $('#background_mask').slideUp(800, function () {
            $('div.layout_div').hide();
        });
        $('#player').trigger('pause');
    }
</script>