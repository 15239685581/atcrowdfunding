<%--
  Created by IntelliJ IDEA.
  User: zou
  Date: 2021/7/28
  Time: 11:52
  To change this template use File | Settings | File Templates.
--%>
<!--
在page directive中的isELIgnored属性用来指定是否忽略。
格式为： ＜%@ page isELIgnored＝"true|false"%＞
如果设定为真，那么JSP中的表达式被当成字符串处理。比如下面这个表达式${2000 % 20},
在isELIgnored＝"true"时输出为${2000 % 20}，而isELIgnored＝"false"时输出为100-->
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false"%>
<html>
<head>
    <title>测试</title>
 <base href="http://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/">
    <script type="text/javascript" src="jquery/jquery-2.1.1.min.js"></script>
    <script>
        $(function () {
            $("#btn1").click(
                function(){
                    $.ajax(
                        {
                            "url":"send/array/one.html",        //请求目标资源的地址
                            "type":"post",                  //请求方式
                            "data":{"array":[5,8,12]},                //要发送的请求参数
                            "dataType":"text",              //如何对待服务器端返回的数据
                            "success":function (response) { //服务器成功处理请求后调用的回调函数
                                alert(response)
                            },
                            "error":function (response) {   //服务器端失败处理后调用的回调函数
                                alert(response)
                            }
                        }
                    );
                }
            )
        });

        $(function () {
            $("#btn2").click(
                function(){
                    $.ajax(
                        {
                            "url":"send/array/two.html",        //请求目标资源的地址
                            "type":"post",                      //请求方式
                            "data":{
                                "array[0]":5,
                                "array[1]":8,
                                "array[2]":12
                            },          //要发送的请求参数
                            "dataType":"text",              //如何对待服务器端返回的数据
                            "success":function (response) { //服务器成功处理请求后调用的回调函数
                                alert(response)
                            },
                            "error":function (response) {   //服务器端失败处理后调用的回调函数
                                alert(response)
                            }
                        }
                    );
                }
            )
        });

        $(function () {
            $("#btn3").click(
                function(){
                    //准备好要发送到服务器端的数组
                    var array = [5,8,12];
                    console.log(array.length);

                    //将json数组转换为JSON字符串
                    var  requestBody = JSON.stringify(array);
                    console.log(requestBody.length);
                    $.ajax(
                        {
                            "url":"send/array/three.json",        //请求目标资源的地址
                            "type":"post",                      //请求方式
                            "data":requestBody,          //请求体
                            "contentType":"application/json;charset=UTF-8",//设置请求体的内容类型，告诉服务器本次请求的请求体是json数据
                            "dataType":"json",              //如何对待服务器端返回的数据
                            "success":function (response) { //服务器成功处理请求后调用的回调函数
                                console.log(response)
                            },
                            "error":function (response) {   //服务器端失败处理后调用的回调函数
                                console.log(response)
                            }
                        }
                    );
                }
            )
        });

    </script>





</head>
<body>
    <% pageContext.forward("admin/to/login/page.html");   %>
    <a href="test/ssm.html">测试SSM整合环境</a>
    <br/>
    <br/>
    <button id="btn1">Send [5,8,12] One</button>
    <br/>
    <br/>
    <button id="btn2">Send [5,8,12] two</button>

    <br/>
    <br/>
    <button id="btn3">Send [5,8,12] three</button>

</body>
</html>
