<%--
  Created by IntelliJ IDEA.
  User: 软工1801温蟾圆
  Date: 2020/6/14
  Time: 17:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>在线问卷系统-管理员菜单</title>
    <link rel="stylesheet" href="css/index.css" type="text/css">
</head>
<body>
<%
    // 接收参数
    String user = (String) request.getAttribute("user");

    // 接收异常状态信息
    String status = (String) session.getAttribute("status");
    String warningString = "";

    // 异常处理
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
        <form class="login-form" action="/FinalProject/DesignQuestionnaireServlet" method="post">
            <h1>欢迎您，<%=user%></h1>
            <input class="submit" type="submit" value="设计问卷"/>
            <input type="text" value="<%=user%>" name="user" style="display: none">
        </form>
        <form class="login-form" action="/FinalProject/LoginServlet" method="post">
            <input class="submit" type="submit" value="开始答题"/>
            <input type="text" value="<%=user%>" name="user" style="display: none">
        </form>
        <form class="login-form" action="/FinalProject/AnalysisServlet" method="post">
            <input class="submit" type="submit" value="分析问卷"/>
            <input type="text" value="<%=user%>" name="user" style="display: none">
            <p class="message">请选择您想要做的任务</p>
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
