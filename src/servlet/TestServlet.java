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

@WebServlet("/TestServlet")
public class TestServlet extends HttpServlet {

    public TestServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");

        Questionnaire questionnaire = (Questionnaire) getServletContext().getAttribute("questionnaire");

        String description = req.getParameter("description");
        String[] options = req.getParameterValues("option");
        questionnaire.setDescription(description);

        req.setAttribute("questionnaire", questionnaire);
        req.setAttribute("user", questionnaire.getMaker().getUsername());
        req.setAttribute("mode", "design");

        PrintWriter out = resp.getWriter();
        out.println(questionnaire.getDescription());
        out.println(options[0]);
        out.println(options[1]);
        out.println(options[2]);
//        req.getRequestDispatcher("/WEB-INF/main/design.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
