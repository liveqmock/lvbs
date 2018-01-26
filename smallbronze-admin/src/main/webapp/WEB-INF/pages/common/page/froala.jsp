<%--
  Created by IntelliJ IDEA.
  User: feifan.gou
  Date: 2017/10/23
  Time: 16:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.4.0/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="/plugin/froala/css/codemirror.min.css">
<!-- Include Editor style. -->
<link rel="stylesheet" href="/plugin/froala/css/codemirror.min.css">
<link href="/plugin/froala/css/froala_editor.pkgd.min.css" rel="stylesheet" type="text/css" />
<link href="/plugin/froala/css/froala_style.min.css" rel="stylesheet" type="text/css" />
<!-- Include external JS libs. -->
<script type="text/javascript" src="/plugin/froala/js/codemirror.min.js"></script>
<script type="text/javascript" src="/plugin/froala/js/xml.min.js"></script>
<!-- Include Editor JS files. -->
<script type="text/javascript" src="/plugin/froala/js/froala_editor.pkgd.min.js"></script>
<!-- Initialize the editor. -->
<script>
    $(function () {
        if ($eidtor && $eidtor.length) {
            $eidtor.froalaEditor(
                {
                    language: 'zh_cn',
//                    imageAllowedTypes: ['jpeg', 'jpg', 'png', 'gif'],
                    //insertImage
                    toolbarButtons:['emoticons','bold', 'underline','fontSize', 'align','color','fontSize','insertTable','undo', 'redo'],
                    height:'400px',
                    imageEditButtons: [ 'imageAlign', 'imageRemove'],
                    imageInsertButtons:['imageUpload','imageByURL'],
                    quickInsertE:[]
                }
            );
        }
    });
    function getVal() {
        if ($eidtor && $eidtor.length) {
            return $eidtor.froalaEditor('html.get');
        }
        return '';
    }
</script>