package servlet;

import common.QuestionManager;
import model.Option;
import model.Question;
import model.Questionnaire;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author 软工1801温蟾圆
 * @date 2020/06/14
 */

@WebServlet("/DesignQuestionServlet")
public class DesignQuestionServlet extends HttpServlet {

    public DesignQuestionServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 设置编码防止乱码
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");

        // 接收参数
        Questionnaire questionnaire = (Questionnaire) getServletContext().getAttribute("questionnaire");
        String description = req.getParameter("description");
        String[] options = req.getParameterValues("option");
        String answer = req.getParameter("answer");

        // 即将插入当前问卷的新问题
        Question newQuestion = new Question();

        if (answer != null) {
            // 初始化新问题
            newQuestion.setAnswer(answer);
            newQuestion.setDescription(description);
            newQuestion.setOptions(new ArrayList());
            try {
                newQuestion.setId(QuestionManager.getAllQuestionNum() + 1);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            for (int i = 0; i < options.length; i++) {
                Option newOption = new Option();
                newOption.setDescription(options[i]);
                try {
                    newOption.setId(QuestionManager.getAllOptionNum() + 1);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                newQuestion.addOptions(newOption);
            }
            // 将新问题添加到问卷
            questionnaire.addQuestions(newQuestion);
        } else {
            questionnaire.setDescription(description);
        }

        // 设置参数，回到设计页面设计下一个问题
        req.setAttribute("questionnaire", questionnaire);
        req.setAttribute("user", questionnaire.getMaker().getUsername());
        req.setAttribute("mode", "design");
        req.getRequestDispatcher("/WEB-INF/main/design.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
