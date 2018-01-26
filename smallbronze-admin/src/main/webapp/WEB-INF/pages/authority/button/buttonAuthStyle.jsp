<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: feifan.gou
  Date: 2017/7/13
  Time: 11:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<style type="text/css">
    <c:forEach var="item" items="${authority_list}" varStatus="status">
        <c:if test="${status.last}">
            .${item.className}
        </c:if>
        <c:if test="${!status.last}">
            .${item.className},
        </c:if>
    </c:forEach> { display: none; }
</style>
<script type="text/javascript">

    var $button_authority = {};

    {
        $button_authority.classNameList = [];
        <c:forEach var="item" items="${authority_list}" varStatus="status">
        $button_authority.classNameList.push('${item.className}');
        </c:forEach>
    }

    $button_authority.isHide = function (current_page_class) {
        var count = 0;
        $(current_page_class).each(function (index,ele) {
            if ($button_authority.classNameList.indexOf(ele) > -1) {
                count++;
            }
        });
        return current_page_class.length === count;
    };
</script>