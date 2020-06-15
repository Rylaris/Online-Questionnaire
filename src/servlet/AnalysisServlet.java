package servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 该Servlet负责连接管理员菜单与分析问卷页面
 *
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
        // 设置编码防止乱码
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");

        // 接收参数
        String user = req.getParameter("user");

        // 根据参数分发页面
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
