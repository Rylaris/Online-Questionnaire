<%@ page import="model.Questionnaire" %>
<%@ page import="common.UserManager" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="common.QuestionManager" %><%--
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
    <title>在线问卷系统-设计问卷</title>
    <link rel="stylesheet" href="css/index.css" type="text/css">
</head>
<body>
<%
    // 接收LoginServlet传递归来的参数
    String user = (String) request.getAttribute("user");
    String mode = (String) request.getAttribute("mode");
    Questionnaire questionnaire = null;

    String h1 = "";
    String message = "";
    String placeholder = "";

    if (mode.equals("name")) {
        questionnaire = new Questionnaire();
        try {
            questionnaire.setMaker(UserManager.getUser(user));
            questionnaire.setId(QuestionManager.getQuestionnaireNum() + 1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        questionnaire.setQuestions(new ArrayList());
        message = "首先填写问卷的标题，形如：\"天文学知识小测试\"";
        placeholder = "问卷标题";
    } else {
        questionnaire = (Questionnaire) request.getAttribute("questionnaire");
        h1 = "<h1>" + questionnaire.getDescription() + "</h1>";
        message = "设计问卷的问题";
        placeholder = "问题描述";
    }
    application.setAttribute("questionnaire", questionnaire);
    // 接收GetQuestionnaireServlet传递过来的错误信息
    String status = (String) session.getAttribute("status");
    String warningString = "";

    // 判断是否有异常状态并显示对应的提示信息
    if (status != null && status.equals("questionnaireIDNull")) {
        warningString = "请输入正确的问卷编号";
    } else if (status != null && status.equals("questionnaireNotExist")) {
        warningString = "问卷不存在，请输入正确的问卷编号";
    }
    session.invalidate();
%>
<div class="login-page">
    <div class="form">
        <form class="login-form" action="/FinalProject/TestServlet" id="mainForm" method="post">
            <%=h1%>
            <input class="input" type="text" placeholder=<%=placeholder%> name="description" required autofocus/>
            <input class="input" type="text" placeholder="答案" name="answer" required/>
            <button type="button" class="submit" style="margin-bottom: 15px" onclick="addOption()">添加选项</button>
            <input type="submit" value="下一步" class="submit"/>
            <input type="text" value="<%=user%>" name="user" style="display: none">
            <p class="message"><%=message%></p>
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
<script>
    function addOption(){
        var newItem=document.createElement("input")
        newItem.setAttribute("class", "input")
        newItem.setAttribute("type", "text")
        newItem.setAttribute("placeholder", "选项")
        newItem.setAttribute("name", "option")
        newItem.setAttribute("required", "true")
        var list=document.getElementById("mainForm")
        list.insertBefore(newItem,list.childNodes[list.childNodes.length - 10]);
    }

</script>
