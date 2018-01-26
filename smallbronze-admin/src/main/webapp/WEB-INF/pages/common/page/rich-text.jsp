<%--
  Created by IntelliJ IDEA.
  User: feifan.gou
  Date: 2017/10/28
  Time: 11:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>

<link href="https://cdn.bootcss.com/bootstrap/3.2.0/css/bootstrap.min.css" rel="stylesheet">
<link href="http://cdn.bootcss.com/font-awesome/4.4.0/css/font-awesome.min.css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="/plugin/rich-text/css/default.css">
<link rel="stylesheet" type="text/css" href="/plugin/rich-text/css/editor.css">

<div class="container">
    <div class="row">
        <div class="col-lg-12 nopadding">
            <textarea id="rich_text"></textarea>
        </div>
    </div>
</div>
<script type="text/javascript" charset="UTF-8" src="/plugin/group-select/js/bootstrap.min.js"></script>
<script src="/plugin/rich-text/js/editor.js"></script>
<script type="text/javascript">
    $(document).ready(function () {
        $("#rich_text").Editor(
            /**
             * 'texteffects':true,
             'aligneffects':true,
             'textformats':true,
             'fonteffects':true,
             'actions' : true,
             'insertoptions' : true,
             'extraeffects' : true,
             'advancedoptions' : true,
             'screeneffects':true,
             'bold': true,
             'italics': true,
             'underline':true,
             'ol':true,
             'ul':true,
             'undo':true,
             'redo':true,
             'l_align':true,
             'r_align':true,
             'c_align':true,
             'justify':true,
             'insert_link':true,
             'unlink':true,
             'insert_img':true,
             'hr_line':true,
             'block_quote':true,
             'source':true,
             'strikeout':true,
             'indent':true,
             'outdent':true,
             'fonts':fonts,
             'styles':styles,
             'print':true,
             'rm_format':true,
             'status_bar':true,
             'font_size':fontsizes,
             'color':colors,
             'splchars':specialchars,
             'insert_table':true,
             'select_all':true,
             'togglescreen':true
             */
            {
                indent:false,
                outdent:false,
                block_quote:false,
                unlink:false,
                insert_img:false,
                insert_link:false,
                insert_table:false,
                strikeout:false,
                hr_line:false,
                splchars:false,
                print:false,
                rm_format:false,
                select_all:false,
                source:false,
                togglescreen:false,
                fonts:false,
//                styles:false,
                justify:false,
                ul:false,
                ol:false


            }
        );
    });
    function getTextVal() {
        return $("#rich_text").Editor("getText");
    }
    function setTextVal(text) {
        $("#rich_text").Editor("setText", text);
    }
</script>