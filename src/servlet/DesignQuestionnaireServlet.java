package servlet;

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

@WebServlet("/DesignQuestionnaireServlet")
public class DesignQuestionnaireServlet extends HttpServlet {

    public DesignQuestionnaireServlet() {
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
            // 对应直接访问
            req.getSession().setAttribute("status", "notLogin");
            resp.sendRedirect("/FinalProject/index.jsp");
        } else {
            // 进入设计问卷页面，同时设置为命名模式，首先对问卷进行命名
            req.setAttribute("user", user);
            req.setAttribute("mode", "name");
            req.getRequestDispatcher("/WEB-INF/main/design.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
