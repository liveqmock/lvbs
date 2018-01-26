<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ include file="/WEB-INF/pages/inc/meta.inc" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/WEB-INF/pages/inc/head.inc" %>
    <script type="text/javascript">
        function f_ok(){
            //_startTime 该元素为父窗口元素
             $(window.parent.document).find(":hidden[name=reportId]").val($('input:radio[name="report"]:checked').val());
        }
    </script>
    <style type="text/css">
        body {
            font-size: 12px;
        }

        .l-table-edit-td {
            padding: 5px;
        }

        .l-button-submit, .l-button-test {
            width: 80px;
            float: left;
            margin-left: 10px;
            padding-bottom: 2px;
        }

        tr.image_tr {
            display: none;
        }

        label.label_target_type {
            margin-right: 16px;
        }

        div.data_div_select, div.data_div_input {
            display: none;
        }
        #g-edit-box>li { float: left; }
        #g-edit-box>li>input { margin: 0; }
        #g-edit-box>li>label { display: inline-block; }
    </style>

</head>

<body style="padding:10px">
    <form name="topic_form" class="form" method="post" id="topic_form">
    <input type="hidden" value="${_id}" name="_id" id="_id"/>
        <table cellpadding="0" cellspacing="0" class="l-table-edit" >
            <c:forEach items="${reportProblemList}" var="reportProblem">
                <tr>
                    <td align="right" class="l-table-edit-td"><input type="radio" name="report" value="${reportProblem.code}"></td>
                    <td align="left" class="l-table-edit-td" ><label for="">${reportProblem.name}</label></td>
                </tr>
            </c:forEach>
        </table>

    </form>
    <div style="display:none">
    <!--  数据统计代码 --></div>

</body>

</html>