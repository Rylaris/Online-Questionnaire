<%@ page import="model.Questionnaire" %>
<%@ page import="model.Question" %>
<%@ page import="model.Option" %>
<%@ page import="common.AnswerManager" %>
<%@ page import="java.sql.SQLException" %><%--
  Created by IntelliJ IDEA.
  User: 软工1801温蟾圆
  Date: 2020/6/14
  Time: 20:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>在线问卷系统-查看结果</title>
    <link rel="stylesheet" href="css/index.css" type="text/css">
</head>
<body>
<%
    // 接收参数
    String user = (String) request.getAttribute("user");
    Questionnaire questionnaire = (Questionnaire) request.getAttribute("questionnaire");
    String mode = (String) request.getAttribute("mode");

    // 需要内嵌的HTML片段
    String content = "";
    String warningString = "";
    String nextStep = "";
    String message = "";
    String h1 = "";
    String action = "";

    // 将传递到本页面的问卷作为参数传递给下一个Servlet
    application.setAttribute("questionnaire", questionnaire);


    if (mode!= null && mode.equals("analysis")) {
        // 分析试卷模式
        h1 = "分析结果";
        action = "/FinalProject/AnalysisServlet";
        nextStep = "重新选择问卷";
        message = "重新选择问卷将回到问卷编号填写页面";
    } else if (mode!= null && mode.equals("preview")) {
        // 预览试卷模式
        h1 = "预览试卷";
        action = "/FinalProject/PostServlet";
        nextStep = "发布问卷";
        message = "发布问卷将正式将问卷添加到题库";
    } else {
        // 查看答案模式
        h1 = "查看答案";
        action = "/FinalProject/LoginServlet";
        nextStep = "重新选择问卷";
        message = "重新选择问卷将回到问卷编号填写页面";
    }

    // 根据不同的显示模式拼接需要显示的问卷内容
    for (int i = 0; i < questionnaire.getQuestions().size(); i++) {
        Question question = (Question) questionnaire.getQuestions().get(i);
        content += "<h2>" + (i + 1) + ". " + question.getDescription() + "</h2>";
        for (int j = 0; j < question.getOptions().size(); j++) {
            Option option = (Option) question.getOptions().get(j);
            char order = (char) (j + 65);
            content += "<p class=\"option\">";
            content += order + ".";
            content += option.getDescription();
            content += "</p></br>";
        }
        content += "<p class=\"option\">";
        if (mode!= null && (mode.equals("analysis") || mode.equals("preview"))) {
            content += "答案：" + question.getAnswer() + "</p></br>";
        } else {
            content += "正确答案：" + question.getAnswer() + "</p></br>";
        }
        content += "<p class=\"option\">";
        try {
            if (mode!= null && mode.equals("analysis")) {
                content += "正确率：" + AnswerManager.getCorrectRate(question.getId()) + "</p></br>";
            } else if (mode!= null && mode.equals("preview")) {
                content += "";
            } else {
                content += "您的答案：" + AnswerManager.getUserAnswer(question.getId(), user) + "</p></br>";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    session.invalidate();
%>
<div class="login-page">
    <div class="form">
        <h1><%=h1%></h1>
        <form class="login-form" action=<%=action%> method="post">
            <%=content%>
            <input type="submit" value=<%=nextStep%> class="submit"/>
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
