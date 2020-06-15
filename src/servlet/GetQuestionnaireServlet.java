package servlet;

import common.AnswerManager;
import common.QuestionManager;
import model.Question;
import model.Questionnaire;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * @author 软工1801温蟾圆
 * @date 2020/06/14
 */

@WebServlet("/GetQuestionnaireServlet")
public class GetQuestionnaireServlet extends HttpServlet {

    public GetQuestionnaireServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 设置编码防止乱码
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");

        // 接收参数
        String questionnaireID = req.getParameter("questionnaireID");
        String user = req.getParameter("user");

        if (user == null) {
            // 对应直接输入域名
            req.getSession().setAttribute("status", "notLogin");
            resp.sendRedirect("/FinalProject/index.jsp");
        } else {
            Questionnaire questionnaire = null;

            // 如果问卷编号无效返回user.jsp并传递错误信息
            if (questionnaireID == null || "".equals(questionnaireID) || !isValid(questionnaireID)) {
                req.getSession().setAttribute("status", "questionnaireIDNull");
                req.getRequestDispatcher("/WEB-INF/main/user.jsp").forward(req, resp);
            }
            try {
                questionnaire = QuestionManager.getQuestionnaire(Integer.parseInt(questionnaireID));
            } catch (SQLException e) {
                e.printStackTrace();
            }

            // 如果问卷不存在返回user.jsp并传递错误信息
            if (questionnaire == null) {
                req.getSession().setAttribute("status", "questionnaireNotExist");
                req.getRequestDispatcher("/WEB-INF/main/user.jsp").forward(req, resp);
            }

            // 如果已经答过这张问卷返回user.jsp并传递错误信息
            try {
                Question question = (Question) questionnaire.getQuestions().get(0);
                if (AnswerManager.getUserAnswer(question.getId(), user) != null) {
                    req.setAttribute("user", user);
                    req.getSession().setAttribute("status", "hasAnswer");
                    req.getRequestDispatcher("/WEB-INF/main/user.jsp").forward(req, resp);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            // 一切正常传递参数给question.jsp，开始答题
            // question.jsp需要三个参数，当前问卷，正在答第几题，答题者
            // 由于当前是刚刚获取问卷，所以从第0题开始答
            req.setAttribute("questionnaire", questionnaire);
            req.setAttribute("currentQuestion", 0);
            req.setAttribute("user", user);
            req.getRequestDispatcher("/WEB-INF/main/question.jsp").forward(req, resp);
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
