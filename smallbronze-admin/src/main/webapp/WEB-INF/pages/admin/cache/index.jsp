<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/pages/inc/meta.inc" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <%@ include file="/WEB-INF/pages/inc/head.inc" %>
    <%@ include file="/WEB-INF/pages/authority/button/buttonAuthStyle.jsp" %>
    <script type="text/javascript">
        var grid;
        $(function () {
            $("form").ligerForm();
            grid = $("#maingrid").ligerGrid({

                width: '100%', height: '100%',
                columns: [
                    {
                        display: '操作', width: 100, render: function (row) {
                            return "<button class='operation_btn cache_clean' onclick='f_edit(\"" + row.id + "\")'>删除缓存</button>";
                        }, hide: $button_authority.isHide(['cache_clean'])
                    },
                    {display: 'id', name: 'id', align: 'left', width: 450, minWidth: 100}

                ],
                root: 'rows',
                record: 'total',
                pageParmName: 'page_number',
                pagesizeParmName: 'page_size',
                rownumbers: true,
                isChecked: f_isChecked, onCheckRow: f_onCheckRow, onCheckAllRow: f_onCheckAllRow,
                url: "/ajax/cache/paginate",
                pageSize: 20, page: 1, pageSizeOptions: [20, 50, 100]
            });
            $("#pageloading").hide();
        });

        function f_onCheckAllRow(checked) {
            for (var rowid in this.records) {
                if (checked)
                    addCheckedCustomer(this.records[rowid]['CustomerID']);
                else
                    removeCheckedCustomer(this.records[rowid]['CustomerID']);
            }
        }

        /*
         该例子实现 表单分页多选
         即利用onCheckRow将选中的行记忆下来，并利用isChecked将记忆下来的行初始化选中
         */
        var checkedCustomer = [];
        function findCheckedCustomer(CustomerID) {
            for (var i = 0; i < checkedCustomer.length; i++) {
                if (checkedCustomer[i] == CustomerID) return i;
            }
            return -1;
        }
        function addCheckedCustomer(CustomerID) {
            if (findCheckedCustomer(CustomerID) == -1)
                checkedCustomer.push(CustomerID);
        }
        function removeCheckedCustomer(CustomerID) {
            var i = findCheckedCustomer(CustomerID);
            if (i == -1) return;
            checkedCustomer.splice(i, 1);
        }
        function f_isChecked(rowdata) {
            if (findCheckedCustomer(rowdata.CustomerID) == -1)
                return false;
            return true;
        }
        function f_onCheckRow(checked, data) {
            if (checked) addCheckedCustomer(data.CustomerID);
            else removeCheckedCustomer(data.CustomerID);
        }
        function f_edit(id) {
            $.ligerDialog.confirm('确定删除该缓存?', function (yes) {
                if (yes) {
                    $.getJSON("/ajax/cache/clear", {"id": id}, function (data) {
                        if (data) {
                            $.ligerDialog.success("成功！");
                        }

                    });
                }
            });
        }

        function f_del() {
            $.ligerDialog.confirm('确定要删除全部吗?', function (yes) {
                if (yes) {
                    $.ligerDialog.confirm('再次确定?', function (yes) {
                        if (yes) {
                            $.ligerDialog.confirm('重要的事说三次！', function (yes) {
                                if (yes) {
                                    $.getJSON("/ajax/cache/clearAll", function (data) {
                                        if (data) {
                                            $.ligerDialog.success("删除全部缓存成功！");
                                        }
                                    })
                                }
                            })
                        }
                    })
                }
            })
        }


    </script>

</head>

<body style="overflow-x:hidden; padding:2px;">
<span style="float:left;margin-left:10px;"><button class="operation_btn cache_clean_all" onclick="f_del()">删除全部缓存</button></span>
<div class="l-loading" style="display:block" id="pageloading"></div>
<div class="l-clear"></div>
<div id="maingrid"></div>

<div style="display:none;"></div>


</body>
</html>
