package servlet;

import common.UserManager;
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
 * @date 2020/06/12
 */

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    public LoginServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
		resp.setCharacterEncoding("utf-8");
        PrintWriter out = resp.getWriter();

        String user = req.getParameter("user");
        String mode = req.getParameter("mode");
        if (user != null) {
            req.setAttribute("user", user);
            if ("main".equals(mode)) {
                req.getRequestDispatcher("/WEB-INF/main/admin.jsp").forward(req, resp);
            } else {
                req.getRequestDispatcher("/WEB-INF/main/user.jsp").forward(req, resp);
            }
        }

        // 接收index.jsp传递过来的参数
        String username = req.getParameter("username");
        String password = UserManager.sha256(req.getParameter("password"));

        User target = null;
        try {
            target = UserManager.getUser(username);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // 进行错误判断和处理
        if (target == null) {
            // 设置查无用户错误信息，重定向回index.jsp
            req.getSession().setAttribute("status", "userNotExist");
            resp.sendRedirect("/FinalProject/index.jsp");
        } else if (!password.equals(target.getPassword())) {
            // 设置密码错误错误信息，重定向回index.jsp
            req.getSession().setAttribute("status", "passwordWrong");
            resp.sendRedirect("/FinalProject/index.jsp");
        } else {
            // 根据管理员或用户身份进入不同的界面
            if (target.isAdministrator()) {
                req.setAttribute("user", target.getUsername());
                req.getRequestDispatcher("/WEB-INF/main/admin.jsp").forward(req, resp);
            } else {
                // 传递用户名参数给user.jsp
                req.setAttribute("user", target.getUsername());
                req.getRequestDispatcher("/WEB-INF/main/user.jsp").forward(req, resp);
            }

        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
