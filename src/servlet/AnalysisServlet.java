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

@WebServlet("/AnalysisServlet")
public class AnalysisServlet extends HttpServlet {

    public AnalysisServlet() {
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
            req.setAttribute("user", user);
            req.getRequestDispatcher("/WEB-INF/main/analysis.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
