<%@ page import="common.UserManager" %>
<%@ page import="java.sql.SQLException" %><%--
  Created by IntelliJ IDEA.
  User: rylaris
  Date: 2020/6/14
  Time: 17:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>在线问卷系统-获取问卷</title>
    <link rel="stylesheet" href="css/index.css" type="text/css">
</head>
<body>
<%
    // 接收LoginServlet传递归来的参数
    String user = (String) request.getAttribute("user");

    // 对于管理员，额外显示返回菜单
    String content = "";
    try {
        if (UserManager.getUser(user).isAdministrator()) {
            content = "<input type=\"submit\" value=\"返回菜单\" class=\"submit\"/>";
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    // 接收GetQuestionnaireServlet传递过来的错误信息
    String status = (String) session.getAttribute("status");
    String warningString = "";

    // 判断是否有异常状态并显示对应的提示信息
    if (status != null && status.equals("questionnaireIDNull")) {
        warningString = "请输入正确的问卷编号";
    } else if (status != null && status.equals("questionnaireNotExist")) {
        warningString = "问卷不存在，请输入正确的问卷编号";
    } else if (status != null && status.equals("hasAnswer")) {
        warningString = "不能对问卷重复作答";
    }
    session.invalidate();
%>
<div class="login-page">
    <div class="form">
        <form class="login-form" action="/FinalProject/GetQuestionnaireServlet" method="post">
            <h1>欢迎您，<%=user%></h1>
            <input class="input" type="text" placeholder="输入问卷编号" name="questionnaireID" required autofocus/>
            <input type="submit" value="开始答题" class="submit"/>
            <input type="text" value="<%=user%>" name="user" style="display: none">
        </form>
        <form class="login-form" action="/FinalProject/LoginServlet" method="post">
            <%=content%>
            <input type="text" value="main" name="mode" style="display: none">
            <input type="text" value="<%=user%>" name="user" style="display: none">
            <p class="message">您可以从管理员处获得问卷编号，每一份问卷都有独一无二的编号，输入编号即可答题。</p>
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
