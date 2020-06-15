package servlet;

import common.AnswerManager;
import common.QuestionManager;
import model.Questionnaire;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * 该Servlet用于回答问题，传入用户名，正在回答第几题，题号，问卷号即可
 *
 * @author 软工1801温蟾圆
 * @date 2020/06/14
 */

@WebServlet("/AnswerQuestionServlet")
public class AnswerQuestionServlet extends HttpServlet {

    public AnswerQuestionServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 设置编码防止乱码
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");

        // 接收参数
        String user = req.getParameter("user");

        if (user == null) {
            // 对应用户直接通过域名访问的情况，返回登陆界面
            req.getSession().setAttribute("status", "notLogin");
            resp.sendRedirect("/FinalProject/index.jsp");
        } else {
            // 接收参数
            int questionID = Integer.parseInt(req.getParameter("questionID"));
            int questionnaireID = Integer.parseInt(req.getParameter("questionnaireID"));
            int currentQuestion = Integer.parseInt(req.getParameter("currentQuestion"));
            String answer = req.getParameter("answer");

            // 将用户的回答保存到数据库
            try {
                AnswerManager.answerQuestion(questionID, user, answer);
            } catch (SQLException e) {
                e.printStackTrace();
            }

            // 获取当前正在回答的问卷
            Questionnaire questionnaire = null;
            try {
                questionnaire = QuestionManager.getQuestionnaire(questionnaireID);
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                if (currentQuestion == QuestionManager.getQuestionNum(questionnaireID)) {
                    // 如果当前正在回答最后一题，进入查看答案界面。
                    req.setAttribute("questionnaire", questionnaire);
                    req.setAttribute("user", user);
                    req.getRequestDispatcher("/WEB-INF/main/result.jsp").forward(req, resp);
                } else {
                    // 否则类似GetQuestionnaireServlet，传递三个参数开始答下一题
                    req.setAttribute("questionnaire", questionnaire);
                    req.setAttribute("currentQuestion", currentQuestion);
                    req.setAttribute("user", user);
                    req.getRequestDispatcher("/WEB-INF/main/question.jsp").forward(req, resp);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
