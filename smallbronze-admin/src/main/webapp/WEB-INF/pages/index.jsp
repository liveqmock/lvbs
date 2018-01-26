<%@ page pageEncoding="UTF-8" %>
<%@page import="java.util.Set" %>
<%@page import="com.daishumovie.dao.model.auth.RoleEntity" %>
<%@page import="org.apache.commons.lang.StringUtils" %>
<%@page import="java.util.List" %>
<%@page import="com.daishumovie.dao.model.auth.enums.EnumRoleType" %>
<%@page import="com.daishumovie.utils.CollectionUtils" %>
<%@page import="com.daishumovie.dao.model.auth.UserInfo" %>
<%@page import="com.daishumovie.dao.model.auth.ResourceEntity" %>
<%@ page import="com.daishumovie.admin.util.SessionUtil" %>
<%@ include file="inc/meta.inc" %>
<html>
<head>
    <%@ include file="inc/head.inc" %>
    <script type="text/javascript">
        var tab = null;
        var accordion = null;
        var tree = null;
        $(function () {
            window.name = 'root';
            $('#change_pwd_button').click(function () {
                $.ligerDialog.open({
                    title: "",
                    isDrag: true,
                    url: '/admin/initChangePwd',
                    height: 420,
                    width: 600,
                    isResize: false,
                    name: 'change_pwd'
                });

            });

            $('#logout_button').click(function () {

                $.ligerDialog.confirm('确定退出后台系统？', function (yes) {
                    if(yes) {
                        postRequest('/ajax/admin/logout', {}, function () {
                            window.location.href='/admin/login'
                        });
                    }
                });
            });
            //布局
            $("#layout1").ligerLayout({
                leftWidth: 190,
                height: '100%',
                heightDiff: -34,
                space: 4,
                onHeightChanged: f_heightChanged
            });

            var height = $(".l-layout-center").height();

            //Tab
            $("#framecenter").ligerTab({height: height});

            //面板
            $("#accordion1").ligerAccordion({height: height - 24, speed: null});

            $(".l-link").hover(function () {
                $(this).addClass("l-link-over");
            }, function () {
                $(this).removeClass("l-link-over");
            });
            //树
            $("#tree1").ligerTree({
                checkbox: false,
                slide: false,
                nodeWidth: 120,
                attribute: ['nodename', 'url'],
                onSelect: function (node) {
                    if (!node.data.url) return;
                    tab.overrideSelectedTabItem({text: node.data.text, url: node.data.url, showClose: false});
                }
            });

            tab = $("#framecenter").ligerGetTabManager();
            accordion = $("#accordion1").ligerGetAccordionManager();
            tree = $("#tree1").ligerGetTreeManager();
            $("#pageloading").hide();

        });
        function f_heightChanged(options) {
            if (tab)
                tab.addHeight(options.diff);
            if (accordion && options.middleHeight - 24 > 0)
                accordion.setHeight(options.middleHeight - 24);
        }
        function f_addTab(tabid, text, url) {
            console.info(tabid + "|" + text + "|" + url);
            tab.addTabItem({tabid: tabid, text: text, url: url});
        }
    </script>
    <style type="text/css">
        body, html {
            height: 100%;
        }

        body {
            padding: 0px;
            margin: 0;
            overflow: hidden;
        }

        .l-link {
            display: block;
            height: 26px;
            line-height: 26px;
            padding-left: 10px;
            text-decoration: underline;
            color: #333;
        }

        .l-link2 {
            text-decoration: underline;
            color: white;
        }

        .l-layout-top {
            background: #102A49;
            color: White;
        }

        .l-layout-bottom {
            background: #E5EDEF;
            text-align: center;
        }

        #pageloading {
            position: absolute;
            left: 0px;
            top: 0px;
            background: white url('/images/loading.gif') no-repeat center;
            width: 100%;
            height: 100%;
            z-index: 99999;
        }

        .l-link {
            display: block;
            line-height: 22px;
            height: 22px;
            padding-left: 20px;
            border: 1px solid white;
            margin: 4px;
        }

        .l-link-over {
            background: #FFEEAC;
            border: 1px solid #DB9F00;
        }

        body, html {
            height: 100%;
        }

        body {
            padding: 0px;
            margin: 0;
            overflow: hidden;
        }

        .l-link {
            display: block;
            height: 26px;
            line-height: 26px;
            padding-left: 10px;
            text-decoration: underline;
            color: #333;
        }

        .l-link2 {
            text-decoration: underline;
            color: white;
            margin-left: 2px;
            margin-right: 2px;
        }

        .l-layout-top {
            background: #102A49;
            color: White;
        }

        .l-layout-bottom {
            background: #E5EDEF;
            text-align: center;
        }

        #pageloading {
            position: absolute;
            left: 0px;
            top: 0px;
            background: white url('/images/loading.gif') no-repeat center;
            width: 100%;
            height: 100%;
            z-index: 99999;
        }

        .l-link {
            display: block;
            line-height: 22px;
            height: 22px;
            padding-left: 16px;
            border: 1px solid white;
            margin: 4px;
        }

        .l-link-over {
            background: #FFEEAC;
            border: 1px solid #DB9F00;
        }

        .l-winbar {
            background: #2B5A76;
            height: 30px;
            position: absolute;
            left: 0px;
            bottom: 0px;
            width: 100%;
            z-index: 99999;
        }

        .space {
            color: #E7E7E7;
        }

        /* 顶部 */
        .l-topmenu {
            margin: 0;
            padding: 0;
            height: 31px;
            line-height: 31px;
            background: url('/images/top.jpg') repeat-x bottom;
            position: relative;
            border-top: 1px solid #1D438B;
        }

        .l-topmenu-logo {
            color: #E7E7E7;
            padding-left: 35px;
            line-height: 26px;
            background: url('/images/topicon.gif') no-repeat 10px 5px;
        }

        .l-topmenu-welcome {
            position: absolute;
            height: 24px;
            line-height: 24px;
            right: 30px;
            top: 2px;
            color: #070A0C;
        }

        .l-topmenu-welcome a {
            color: #E7E7E7;
            text-decoration: underline
        }

        .body-gray2014 #framecenter {
            margin-top: 3px;
        }

        .viewsourcelink {
            background: #B3D9F7;
            display: block;
            position: absolute;
            right: 10px;
            top: 3px;
            padding: 6px 4px;
            color: #333;
            text-decoration: underline;
        }

        .viewsourcelink-over {
            background: #81C0F2;
        }

        .l-topmenu-welcome label {
            color: white;
        }

        #skinSelect {
            margin-right: 6px;
        }
        #change_pwd_button{
            float: left;
            margin-right: 10px;
        }
        h4.home_title {
            font-size: 24px;
            margin-left: 31px;
            margin-top: 10px;
        }
    </style>
</head>
<%!
    String renderMenu(ResourceEntity menu) throws Exception {
        StringBuffer tree = new StringBuffer("");
        List<ResourceEntity> children = menu.getChildren();
        if (children != null && !children.isEmpty()) {
            tree.append("<li isexpand=\"false\"><span>" + menu.getName() + "</span>");
            tree.append("   <ul>");
            for (ResourceEntity child : children) {
                tree.append(renderMenu(child));
            }
            tree.append("   </ul>");
            tree.append("</li>");
        } else {
            tree.append("<li url=\"" + menu.getPath() + "\"><span>" + menu.getName() + "</span></li>");
        }
        return tree.toString();
    }
%>
<body style="padding:0px;background:#EAEEF5;">
<div id="pageloading"></div>
<%@ include file="inc/top.inc" %>
<div id="layout1" style="width:99.2%; margin:0 auto; margin-top:4px; ">
    <div position="left" title="当前登录用户【<%=(SessionUtil.getLoginAdmin(request)).getRealName() %>】" id="accordion1">
        <div title="功能列表" class="l-scroll">
            <ul id="tree1" style="margin-top:3px;">
                <%
                    UserInfo uInfo = SessionUtil.getLoginUserInfo(request);
                    boolean isAdmin = false;
                    Set<RoleEntity> roles = uInfo.getRoles();
                    if (!CollectionUtils.isNullOrEmpty(roles)) {
                        for (RoleEntity role : roles) {
                            if (role.getType() == EnumRoleType.ADMIN.getIndex()) {
                                isAdmin = true;
                                break;
                            }
                        }
                    }
                    if (isAdmin) {

                        ResourceEntity menu = uInfo.getMenu();
                        if (menu != null) {
                            List<ResourceEntity> items = menu.getChildren();
                            if (items != null) {
                                for (ResourceEntity item : items) {
                                    if (StringUtils.equals(StringUtils.trim(item.getName()), "系统管理"))
                                        continue;
                                    out.println(renderMenu(item));
                                }
                            }
                        }
                        out.print("<li isexpand=\"false\"><span>系统管理</span>" +
                                "   <ul>\n" +
                                "   	<li url=\"" + request.getContextPath() + "/resource/index\"><span>资源管理</span></li>\n" +
                                "   	<li url=\"" + request.getContextPath() + "/user/index\"><span>用户管理</span></li>\n" +
                                "   	<li url=\"" + request.getContextPath() + "/role/index\"><span>角色管理</span></li>\n" +
                                "   	<li url=\"" + request.getContextPath() + "/log/index\"><span>日志管理</span></li>\n" +
                                "   	<li url=\"" + request.getContextPath() + "/cache/index\"><span>缓存管理</span></li>\n" +
                                "   </ul>\n" +
                                "</li>");

                    } else {
                        ResourceEntity menu = uInfo.getMenu();
                        if (menu != null) {
                            List<ResourceEntity> items = menu.getChildren();
                            if (items != null) {
                                for (ResourceEntity item : items) {
                                    out.println(renderMenu(item));
                                }
                            }
                        }
                    }
                %>
            </ul>
        </div>
    </div>
    <div position="center" id="framecenter">
        <div tabid="home" title="我的主页" style="height:300px">
            <h4 class="home_title">小铜人后台管理系统</h4>
        </div>
    </div>
</div>
<%@ include file="inc/foot.inc" %>
<div style="display:none"></div>
<script>

    $('#tree1').ready(function () {

        $('div.l-box', 'div.l-body').click(function () {

            $(this).parents('li').siblings().find('div.l-expandable-open').click();
        });

    });
</script>
</body>
</html>
