<%--
  Created by IntelliJ IDEA.
  User: rylaris
  Date: 2020/6/14
  Time: 21:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>在线问卷系统-分析问卷</title>
    <link rel="stylesheet" href="css/index.css" type="text/css">
</head>
<body>
<%
    // 接收参数
    String user = (String) request.getAttribute("user");

    // 接收传递过来的错误信息
    String status = (String) session.getAttribute("status");
    String warningString = "";

    // 异常处理
    if (status != null && status.equals("questionnaireIDNull")) {
        warningString = "请输入正确的问卷编号";
    } else if (status != null && status.equals("questionnaireNotExist")) {
        warningString = "问卷不存在，请输入正确的问卷编号";
    }
    session.invalidate();
%>
<div class="login-page">
    <div class="form">
        <form class="login-form" action="/FinalProject/GetAnalysisServlet" method="post">
            <input class="input" type="text" placeholder="问卷编号" name="questionnaireID" required autofocus/>
            <input type="submit" value="开始分析" class="submit"/>
            <input type="text" value="<%=user%>" name="user" style="display: none">
        </form>
        <form class="login-form" action="/FinalProject/LoginServlet" method="post">
            <input type="submit" value="返回菜单" class="submit"/>
            <input type="text" value="main" name="mode" style="display: none">
            <input type="text" value="<%=user%>" name="user" style="display: none">
            <p class="message">分析问卷将会向您展现每一题的正确率。</p>
        </form>
        <p class="warningMessage"><%=warningString%></p>
    </div>
</div>
<footer id="foot">
    <img src="https://jsjxy.bistu.edu.cn/images/Group.png" alt="">
    <p id="copyright">JavaWeb实验3 / 在线问卷系统<br>
        COPYRIGHT @ 软工1801温蟾圆 && 软工1802俞昊洋</p>
</footer>
</body>
</html>
