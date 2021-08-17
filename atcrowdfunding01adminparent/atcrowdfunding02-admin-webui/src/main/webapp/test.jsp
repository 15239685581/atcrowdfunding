<%--
  Created by IntelliJ IDEA.
  User: zou
  Date: 2021/8/2
  Time: 15:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<!DOCTYPE html>
<html lang="zh-CN">
<%@include file="/WEB-INF/include-head.jsp" %>
<script type="text/javascript">
    $(function () {
        $("#ascy").click(function () {
            console.log("ajax函数之前");
            $.ajax(
                {
                    "url":"test/ajax/async.html",
                    "type":"post",
                    "dataType":"text",
                    "async":false, //关闭异步工作模式，使用同步方式工作。
                    "success":function(response)
                    {
                        console.log("ajax函数内部的success函数"+response);
                    }
                }
            );
            console.log("ajax函数之后");
        });
    });
</script>
<body>

<%@include file="/WEB-INF/include-nav.jsp" %>
    <button id="ascy">ajax异步请求·</button>
</body>
</html>
