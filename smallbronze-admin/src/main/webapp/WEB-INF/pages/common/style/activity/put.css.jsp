<%--
  Created by IntelliJ IDEA.
  User: feifan.gou
  Date: 2017/10/26
  Time: 16:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8"%>
<style>

    #add_data dl {
        overflow: hidden;
        display: inline-block;
        width: 250px;
        padding: 5px 0;
        vertical-align: top;
        border-top: 1px solid #e8e8e8;
    }

    #add_data dt {
        float: left;
        margin-right: 5px;
    }

    #add_data dt a {
        /*
          display: block;
          width: 100%;
          height: 100%;*/
    }

    #add_data dt a img {
        max-width: 100px;
        max-height: 130px;
        vertical-align: top;
    }

    #add_data dd {
        overflow: hidden;
        display: block;
    }

    #add_data dd a {
        display: inline-block;
        padding: 6px 0;
        margin-bottom: 10px;
        line-height: 24px;
        font-size: 15px;
        text-decoration: none;
        color: #37A;
    }

    #add_data dd a:hover {
        color: #fff;
        background-color: #37A;
    }

    #add_data dd p {
        line-height: 20px;
        font-size: 14px;
        color: #666;
        overflow: hidden;
        text-overflow: ellipsis;
        white-space: nowrap;
    }
    #topic_id {
        border: solid grey 1px;
        border-radius: 4px;
        height: 20px;
        text-align: center;
    }

    div.search {
        margin: 15px 0 10px 40px;
    }

    button.blue_btn{
        color: #fff;
        background-color: #0099ff;
        border-color: #428bca;
        width: 40px;
        border-radius: 4px;
        height: 22px;
        cursor: pointer;
    }
    #add_topic {
        margin-left: 35px;
        width: 120px;
        height: 30px;
        margin-top: 30px;
    }
    #confirmBox>p,#confirmBox>h1{
        font-size: 18px !important;
    }
    #return_list {
        width: 120px;
        height: 30px;
    }
</style>