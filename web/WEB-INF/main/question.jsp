<%@ page import="model.Questionnaire" %>
<%@ page import="model.Question" %>
<%@ page import="model.Option" %><%--
  Created by IntelliJ IDEA.
  User: rylaris
  Date: 2020/6/14
  Time: 17:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>在线问卷系统-答题</title>
    <link rel="stylesheet" href="css/index.css" type="text/css">
</head>
<body>
<%
    // 接收传递过来的错误信息
    String status = (String)session.getAttribute("status");
    String warningString = "";

    // 接收参数
    String user = (String) request.getAttribute("user");
    Questionnaire questionnaire = (Questionnaire) request.getAttribute("questionnaire");
    int currentQuestion = (int) request.getAttribute("currentQuestion");

    Question question = null;

    // 需要内嵌的HTML片段
    String questionnaireDescription = questionnaire.getDescription();
    String questionDescription = "";
    String content = "";

    // 异常处理
    if (status != null && status.equals("questionnaireIDNull")) {
        warningString = "请输入正确的问卷编号";
    } else if (status != null && status.equals("questionnaireNotExist")) {
        warningString = "问卷不存在，请填写正确的问卷编号";
    }

    if (questionnaire.getQuestions().isEmpty()) {
        warningString = "问卷还是空的，看看别的问卷吧。";
    } else {
        question = (Question) questionnaire.getQuestions().get(currentQuestion);
    }
    questionDescription = (currentQuestion + 1) + "." + question.getDescription();

    // 显示完整的题目，内嵌到HTML中
    for (int i = 0; i < question.getOptions().size(); i++) {
        Option option = (Option) question.getOptions().get(i);
        char order = (char) (i + 65);
        content += "<p class=\"option\">";
        content += order + ". ";
        content += option.getDescription();
        content += "</p></br>";
    }

    session.invalidate();
%>
<div class="login-page">
    <div class="form">
        <h1><%=user%></h1>
        <h1><%=questionnaireDescription%></h1>
        <h2><%=questionDescription%></h2>
        <form class="login-form" action="/FinalProject/AnswerQuestionServlet" method="post">
            <%=content%>
            <input class="input" type="text" placeholder="输入答案" name="answer" required autofocus autocomplete="off"/>
            <input type="submit" value="提交" class="submit"/>
            <input type="text" value="<%=questionnaire.getId()%>" name="questionnaireID" style="display: none">
            <input type="text" value="<%=question.getId()%>" name="questionID" style="display: none">
            <input type="text" value="<%=user%>" name="user" style="display: none">
            <input type="text" value="<%=currentQuestion + 1%>" name="currentQuestion" style="display: none">
            <p class="message">若答案为A，请填写大写字母A，若有多个答案，请按"ACD"的格式填写答案。</p>
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
