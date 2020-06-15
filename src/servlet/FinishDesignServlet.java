package servlet;

import model.Questionnaire;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 软工1801温蟾圆
 * @date 2020/06/14
 */

@WebServlet("/FinishDesignServlet")
public class FinishDesignServlet extends HttpServlet {

    public FinishDesignServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 设置编码防止乱码
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");

        // 接收参数
        Questionnaire questionnaire = (Questionnaire) getServletContext().getAttribute("questionnaire");
        String user = req.getParameter("user");

        // 设置参数，以预览模式进入查看界面
        req.setAttribute("questionnaire", questionnaire);
        req.setAttribute("user", user);
        req.setAttribute("mode", "preview");
        req.getRequestDispatcher("/WEB-INF/main/result.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
