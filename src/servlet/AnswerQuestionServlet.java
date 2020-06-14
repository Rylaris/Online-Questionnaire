package servlet;

import common.AnswerManager;
import common.QuestionManager;
import common.UserManager;
import model.Question;
import model.Questionnaire;
import model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

/**
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
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        PrintWriter out = resp.getWriter();

        // 接收question.jsp传递的参数
        String user = req.getParameter("user");
        if (user == null) {
            req.getSession().setAttribute("status", "notLogin");
            resp.sendRedirect("/FinalProject/index.jsp");
        } else {
            int questionID = Integer.parseInt(req.getParameter("questionID"));
            int questionnaireID = Integer.parseInt(req.getParameter("questionnaireID"));
            int currentQuestion = Integer.parseInt(req.getParameter("currentQuestion"));
            String answer = req.getParameter("answer");

            try {
                AnswerManager.answerQuestion(questionID, user, answer);
            } catch (SQLException e) {
                e.printStackTrace();
            }

            Questionnaire questionnaire = null;
            try {
                questionnaire = QuestionManager.getQuestionnaire(questionnaireID);
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                if (currentQuestion == QuestionManager.getQuestionNum(questionnaireID)) {
                    req.setAttribute("questionnaire", questionnaire);
                    req.setAttribute("user", user);
                    req.getRequestDispatcher("/WEB-INF/main/result.jsp").forward(req, resp);
                } else {
                    // 类似GetQuestionnaireServlet，传递三个参数开始答题
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
