<%@ page import="model.Questionnaire" %>
<%--
  Created by IntelliJ IDEA.
  User: 软工1801温蟾圆
  Date: 2020/6/15
  Time: 11:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>在线问卷系统-发布成功</title>
    <link rel="stylesheet" href="css/index.css" type="text/css">
</head>
<body>
<%
    // 接收参数
    String user = (String) request.getAttribute("user");
    Questionnaire questionnaire = (Questionnaire) request.getAttribute("questionnaire");

    // 初始化HTML片段
    String h1 = questionnaire.getDescription() + "已发布，问卷ID为" + questionnaire.getId();

    session.invalidate();
%>
<div class="login-page">
    <div class="form">
        <form class="login-form" action="/FinalProject/LoginServlet" id="mainForm" method="post">
            <h1><%=h1%></h1>
            <input type="submit" value="返回菜单" class="submit"/>
            <input type="text" value="main" name="mode" style="display: none">
            <input type="text" value="<%=user%>" name="user" style="display: none">
        </form>
        <p class="message">问卷已经成功发布到问卷库中，您可以将问卷编号告诉他人，邀请他人前来答题。</p>
    </div>
</div>
<footer id="foot">
    <img src="https://jsjxy.bistu.edu.cn/images/Group.png" alt="">
    <p id="copyright">JavaWeb实验3 / 在线问卷系统<br>
        COPYRIGHT @ 软工1801温蟾圆 && 软工1802俞昊洋</p>
</footer>
</body>
</html>
