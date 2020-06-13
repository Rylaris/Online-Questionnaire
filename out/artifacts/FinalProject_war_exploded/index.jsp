<%--
  Created by IntelliJ IDEA.
  User: rylaris
  Date: 2020/6/13
  Time: 17:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>登录</title>
</head>
<body>
<%
    String status = (String)session.getAttribute("status");
    String warningString = " ";
    if (status != null && status.equals("passwordWrong")) {
        warningString = "密码错误";
    }
    session.invalidate();
%>
<form action="/FinalProject/LoginServlet" method="post">
    <p>输入用户名：</p><input type="text" name="username"><br/>
    <p>输入密码：</p><input type="password" name="password"><br/>
    <p><%=warningString%></p><br/>
    <input type="submit" value="登录"><br/>
</form>
</body>
</html>
