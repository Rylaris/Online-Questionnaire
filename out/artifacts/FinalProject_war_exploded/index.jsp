<%--
  Created by IntelliJ IDEA.
  User: 软工1801温蟾圆
  Date: 2020/6/13
  Time: 17:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>在线问卷系统-登录</title>
    <link rel="stylesheet" href="css/index.css" type="text/css">
</head>
<body>
<%
    // 接收异常状态信息
    String status = (String)session.getAttribute("status");
    String warningString = "";

    // 异常处理
    if (status != null && status.equals("passwordWrong")) {
        warningString = "密码错误";
    } else if (status != null && status.equals("userNotExist")) {
        warningString = "用户不存在";
    } else if (status != null && status.equals("notLogin")) {
        warningString = "尚未登录，请先登录";
    }

    session.invalidate();
%>
<div class="login-page">
    <div class="form">
        <form class="login-form" action="/FinalProject/LoginServlet" method="post">
            <input class="input" type="text" placeholder="用户名" name="username" required autofocus/>
            <input class="input" type="password" placeholder="密码" name="password" required/>
            <input type="submit" value="登录" class="submit">
            <p class="message">还没有账户？<a href="register.jsp">注册账户</a></p>
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
