<%--
  Created by IntelliJ IDEA.
  User: rylaris
  Date: 2020/6/14
  Time: 15:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>在线问卷系统-注册</title>
    <link rel="stylesheet" href="css/index.css" type="text/css">
</head>
<body>
<%
    // 接收异常状态信息
    String status = (String)session.getAttribute("status");
    String warningString = "";

    // 异常处理
    if (status != null && status.equals("passwordWrong")) {
        warningString = "两次输入的密码不一致，请重新输入。";
    } else if (status != null && status.equals("userExist")) {
        warningString = "该用户已存在，请前往登录。";
    } else if (status != null && status.equals("registerSuccess")) {
        warningString = "注册成功";
    }

    session.invalidate();
%>
<div class="login-page">
    <div class="form">
        <form class="login-form" action="/FinalProject/RegisterServlet" method="post">
            <input class="input" type="text" placeholder="用户名" name="username" required autofocus/>
            <input class="input" type="password" placeholder="密码" name="password" required/>
            <input class="input" type="password" placeholder="再次输入密码" name="confirmPassword" required/>
            <input type="submit" value="注册" class="submit">
            <p class="message">如需注册管理员，请联系数据库管理员，<a href="index.jsp">返回登录</a></p>
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
