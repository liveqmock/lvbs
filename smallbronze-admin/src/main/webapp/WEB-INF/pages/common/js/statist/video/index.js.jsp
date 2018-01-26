<%--
  Created by IntelliJ IDEA.
  User: feifan.gou
  Date: 2017/9/4
  Time: 18:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<script type="text/javascript">
    $(function () {
        renderTable();
        enterEvent($('input'), renderTable);

        $('select#type').change(renderTable);

        $('input#s_type').click(function(){
            if (this.checked){
                $('span.type').hide();
                $(this).val(1);
            }else{
                $('span.type').show();
                $(this).val(0);
            }
            renderTable();
        });
    });

     function formatCurrency(num)
    {
        if (!num) return "0.00";
        num = num.toString().replace(/\$|\,/g, '');
        if (isNaN(num))
            num = "0.00";
        sign = (num == (num = Math.abs(num)));
        num = Math.floor(num * 100 + 0.50000000001);
        cents = num % 100;
        num = Math.floor(num / 100).toString();
        if (cents < 10)
            cents = "0" + cents;
        for (var i = 0; i < Math.floor((num.length - (1 + i)) / 3); i++)
            num = num.substring(0, num.length - (4 * i + 3)) + ',' +
        num.substring(num.length - (4 * i + 3));
        return (((sign) ? '' : '-') + '' + num + '.' + cents);
    }
    function columnList() {
        var columns = [];
        columns.push(
             {display: '时间', name: 't_f', width: 140,isSort: false},
             {display: '视频ID', name: 'video_id', width: 100, isSort: false},
             {display: '标题', name: 'name', width: 180,align: 'left', isSort: false, render: function (row) { //视频地址
                  return divWithTitle(row['name']);
              }},
             {display: '渠道', name: 'channelName', width: 120, isSort: false},
             {display: '播放量', name: 'play_pv', width: 150},
             {display: '播放人数', name: 'play_uv', width: 100},
            {display: '0~5秒播放量占比(%)', name: 'five_second_pv',type: 'float', width: 150,render: function(item)
              {
                  return formatCurrency(item.five_second_pv);
              }},
            {display: '5秒~90%播放量占比(%)', name: 'five_ninety_pv', type: 'float',width: 150,render: function(item)
              {
                  return formatCurrency(item.five_ninety_pv);
              }},
            {display: '完整性播放量占比(%)(进度≥90%)', name: 'greater_ninety_pv',type: 'float', width: 170,render: function(item)
              {
                  return formatCurrency(item.greater_ninety_pv);
              }},
            {display: '平均播放时长(秒)', name: 'play_t_time',type: 'int', width: 100},
            {display: '评论数', name: 'comments', width: 100},
            {display: '点赞数', name: 'praise_num', width: 100},
            {display: '分享数', name: 'share_num', width: 100},
            {display: '弹幕数', name: 'barrage_num', width: 100}
        );

        return columns;
    }

    function renderTable() {

        $('#maingrid').ligerGrid({
            width: '100%', height: '100%',
            columns: columnList(),
            root: 'rows',
            record: 'total',
            pageParmName: 'page_number',
            pagesizeParmName: 'page_size',
            sortnameParmName: "sort_name",
            sortorderParmName: "sort_order",
            rownumbers: true,
            colDraggable: true,
            url: "/ajax/statistics/video/paginate",
            parms: [
                document.getElementById('createTime'),
                document.getElementById('endTime'),
                document.getElementById('type'),
                document.getElementById('videoId'),
                document.getElementById('name'),
                document.getElementById('s_type')
            ],
            pageSize: 50,
            page: 1,
            pageSizeOptions: [20, 50, 100],
            onRClickToSelect: true
        });
        $("#page_loading").hide();
    }
</script>
