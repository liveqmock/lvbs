<%--
  Created by IntelliJ IDEA.
  User: feifan.gou
  Date: 2017/9/29
  Time: 11:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <link rel="shortcut icon" type="image/x-icon" href="http://m.daishumovie.com/copperShare/favicon.ico">
    <meta http-equiv="Content-Type" content="text/html; charset=utf8" />
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta content="telephone=no" name="format-detection">
    <title>${video.title}</title>
    <link rel="stylesheet" charset="UTF-8" href="/plugin/player/css/videoCT.css"/>
    <script charset="UTF-8" type="text/javascript" src="http://www.jq22.com/jquery/jquery-1.10.2.js"></script>
    <script charset="UTF-8" type="text/javascript" src="/plugin/player/js/playerCT.js"></script>
    <style type="text/css">
        #background_mask {
            height: 100%;
            width: 100%;
            background-color: #000;
            position: fixed;
            top: 0;
            -moz-opacity: 0.5;
            opacity: 1;
            z-index: 999;
            margin-left: -8px;
            /*display: none;*/
        }
        .closes_span {
            color: white;
            float: right;
            margin-right: 20px;
            margin-top: 10px;
            font-size: 40px;
            cursor: pointer;
        }
        /* content内容样式 */
        .g-video-content {
            box-sizing: border-box;
            display: flex;
            justify-content: center;
            align-items: left;
            flex-flow: column;
            width: 23%;
            height: 100%;
            position: absolute;
            top: 0;
            right: 0;
            bottom: 0;
            margin: auto;
            overflow: hidden;
            border: 5px solid #000;
            background-color: #000;
        }
        .g-play-num {
            text-indent: 20px;
            background: url("/plugin/player/img/g_playNum.png")no-repeat left 8px/14px 14px;
            font-size: 16px;
            color: #fff;
            line-height: 30px;
        }
        .g-play-time {
            text-indent: 20px;
            background: url("/plugin/player/img/g_play_ago.ico")no-repeat left 8px/14px 14px;
            font-size: 16px;
            color: #fff;
            line-height: 30px;
        }
        .g-play-title {
            text-indent: 20px;
            background: url("/plugin/player/img/g_playTitle.png")no-repeat left 8px/14px 14px;
            font-size: 16px;
            color: #fff;
            line-height: 30px;
            padding-right: 10px;
            box-sizing: border-box;
        }
        .g-play-heart {
            text-indent: 20px;
            background: url("/plugin/player/img/g_playHeart.png")no-repeat left 8px/14px 14px;
            font-size: 16px;
            color: #fff;
            line-height: 30px;
        }
        .g-play-talk {
            text-indent: 20px;
            background: url("/plugin/player/img/g_playTalk.png")no-repeat left 8px/14px 14px;
            font-size: 16px;
            color: #fff;
            line-height: 30px;
        }
        .g-video-content>span>i {
            font-style: normal;
            margin-right: 4px;
        }
        div.video-player {
            margin-bottom: 10px;
        }
    </style>
</head>
<body>

<div class="layout_div">
    <div id="background_mask">
        <span class="closes_span" onclick="player_close()">×</span>
        <section>
            <video width="100%" height="100%" id="player"></video>
            <div class="g-video-content">
                <span class="g-play-num"><i>${video.playNumber}</i>次播放</span>
                <span class="g-play-time">${video.ago}</span>
                <span class="g-play-title"><i style="color: #00b4ed">${video.publisher}：</i>${video.title}</span>
                <span class="g-play-heart"><i>${video.likeNumber}</i>喜欢</span>
                <span class="g-play-talk"><i>${video.commentNumber}</i>评论</span>
            </div>
        </section>
    </div>
</div>
<script type="text/javascript">
    (function(){
        $('#player').videoCt({
            title: '',              //标题
            volume: 0.2,                //音量
            barrage: false,              //弹幕开关
            comment: false,              //弹幕
            reversal: true,             //镜像翻转
            playSpeed: true,            //播放速度
            update: false,               //下载
            autoplay: true,            //自动播放
            clarityType:false,
            clarity: {
                type: ['1024P'],            //清晰度
                src: ['${video.link}']      //链接地址
            },
            commentFile: '',           //导入弹幕json数据
            cutover:false
        });
        $('div.video-clarity').hide();
        $('div.video-screen').hide();
    })(jQuery);
</script>
</body>
</html>
