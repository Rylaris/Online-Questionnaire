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

@WebServlet("/GetAnalysisServlet")
public class GetAnalysisServlet extends HttpServlet {

    public GetAnalysisServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");

        PrintWriter out = resp.getWriter();

        // 接收question.jsp传递的参数
        String user = req.getParameter("user");
        String questionnaireID = req.getParameter("questionnaireID");
        if (user == null) {
            req.getSession().setAttribute("status", "notLogin");
            resp.sendRedirect("/FinalProject/index.jsp");
        } else {
            Questionnaire questionnaire = null;

            // 如果问卷编号无效返回user.jsp并传递错误信息
            if (questionnaireID == null || "".equals(questionnaireID) || !isValid(questionnaireID)) {
                req.getSession().setAttribute("status", "questionnaireIDNull");
                req.getRequestDispatcher("/WEB-INF/main/analysis.jsp").forward(req, resp);
            }
            try {
                questionnaire = QuestionManager.getQuestionnaire(Integer.parseInt(questionnaireID));
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if (questionnaire == null) {
                req.getSession().setAttribute("status", "questionnaireNotExist");
                req.getRequestDispatcher("/WEB-INF/main/analysis.jsp").forward(req, resp);
            }

            req.setAttribute("questionnaire", questionnaire);
            req.setAttribute("user", user);
            req.setAttribute("mode", "analysis");
            req.getRequestDispatcher("/WEB-INF/main/result.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    private boolean isValid(String id) {
        for (int i = 0; i < id.length(); i++) {
            if (!Character.isDigit(id.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}
