<%@ page import="model.Questionnaire" %>
<%@ page import="common.UserManager" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="common.QuestionManager" %><%--
  Created by IntelliJ IDEA.
  User: 软工1801温蟾圆
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
    // 接收传递过来的错误信息
    String status = (String) session.getAttribute("status");
    String warningString = "";

    // 接收参数
    String user = (String) request.getAttribute("user");
    String mode = (String) request.getAttribute("mode");

    Questionnaire questionnaire = null;

    // 需要根据不同情况进行内嵌的HTML片段
    String h1 = "";
    String message = "";
    String placeholder = "";
    String addOptionButton = "";
    String nextStep = "";
    String answerInput = "";

    // 异常处理
    if (status != null && status.equals("empty")) {
        warningString = "问卷不完整，请重新设计";
    }

    if (mode.equals("name")) {
        // 问卷命名阶段
        // 初始化问卷
        questionnaire = new Questionnaire();
        try {
            questionnaire.setMaker(UserManager.getUser(user));
            questionnaire.setId(QuestionManager.getQuestionnaireNum() + 1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        questionnaire.setQuestions(new ArrayList());

        // 设置需要内嵌的元素
        message = "首先填写问卷的标题，形如：\"天文学知识小测试\"";
        placeholder = "问卷标题";
        nextStep = "下一步";
    } else {
        // 设计题目阶段
        // 获取尚未设计完毕的问卷
        questionnaire = (Questionnaire) request.getAttribute("questionnaire");

        // 设置需要内嵌的元素
        h1 = "<h1>" + questionnaire.getDescription() + "</h1>";
        message = "设计问卷的问题，请注意，选择结束设计不会保存当前正在设计的题目。";
        placeholder = "问题描述";
        addOptionButton = "<button type=\"button\" class=\"submit\" style=\"margin-bottom: 15px\" onclick=\"addOption()\">添加选项</button>";
        nextStep = "下一题";
        answerInput = "<input class=\"input\" type=\"text\" placeholder=\"答案\" name=\"answer\" required autocomplete=\"off\"/>";
    }

    // 将问卷设置为参数，传递给Servlet，由Servlet完善问卷
    application.setAttribute("questionnaire", questionnaire);

    session.invalidate();
%>
<div class="login-page">
    <div class="form">
        <form class="login-form" action="/FinalProject/DesignQuestionServlet" id="mainForm" method="post">
            <%=h1%>
            <input class="input" type="text" placeholder=<%=placeholder%> name="description" required autofocus autocomplete="off"/>
            <%=answerInput%>
            <%=addOptionButton%>
            <input type="submit" value=<%=nextStep%> class="submit"/>
            <input type="text" value="<%=user%>" name="user" style="display: none">
        </form>
        <form class="login-form" action="/FinalProject/FinishDesignServlet" method="post">
            <input type="submit" value="结束设计" class="submit"/>
            <input type="text" value="<%=user%>" name="user" style="display: none">
        </form>
        <p class="message"><%=message%></p>
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
    // 添加新的题目选项
    function addOption(){
        // 初始化HTML元素
        var newItem=document.createElement("input");
        newItem.setAttribute("class", "input");
        newItem.setAttribute("type", "text");
        newItem.setAttribute("placeholder", "选项");
        newItem.setAttribute("name", "option");
        newItem.setAttribute("required", "true");
        newItem.setAttribute("autocomplete", "off");

        // 获取父节点
        var list=document.getElementById("mainForm");

        // 插入到正确的位置
        list.insertBefore(newItem,list.childNodes[list.childNodes.length - 8]);
    }
</script>
