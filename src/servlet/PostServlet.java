package servlet;

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
 * @author 软工1801温蟾圆
 * @date 2020/06/14
 */

@WebServlet("/PostServlet")
public class PostServlet extends HttpServlet {

    public PostServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 设置编码防止乱码
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");

        // 获取参数
        String user = req.getParameter("user");
        Questionnaire questionnaire = (Questionnaire) getServletContext().getAttribute("questionnaire");

        // 如果问卷不完整，回到设计页面以命名模式重新设计
        if (questionnaire.getDescription() == null || questionnaire.getQuestions().isEmpty()) {
            req.getSession().setAttribute("status", "empty");
            req.setAttribute("user", user);
            req.setAttribute("mode", "name");
            req.getRequestDispatcher("/WEB-INF/main/design.jsp").forward(req, resp);
        }

        // 向数据库中添加问卷
        try {
            QuestionManager.addQuestionnaire(questionnaire);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // 设置参数，进入成功界面
        req.setAttribute("questionnaire", questionnaire);
        req.setAttribute("user", user);
        req.getRequestDispatcher("/WEB-INF/main/success.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
