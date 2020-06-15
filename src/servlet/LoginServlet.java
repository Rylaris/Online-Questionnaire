package servlet;

import common.UserManager;
import model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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
        // 设置编码防止乱码
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");

        // 管理员如果需要返回菜单需要携带mode参数请求LoginServlet，由LoginServlet重新分发
        String user = req.getParameter("user");
        String mode = req.getParameter("mode");
        String mainMode = "main";
        if (user != null) {
            req.setAttribute("user", user);
            if (mainMode.equals(mode)) {
                req.getRequestDispatcher("/WEB-INF/main/admin.jsp").forward(req, resp);
            } else {
                req.getRequestDispatcher("/WEB-INF/main/user.jsp").forward(req, resp);
            }
        }

        // 接收index.jsp传递过来的登录参数
        String username = req.getParameter("username");
        String password = UserManager.sha256(req.getParameter("password"));

        User target = null;

        // 在数据库中寻找用户输入的用户名对应的用户
        try {
            target = UserManager.getUser(username);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // 错误判断和处理
        if (target == null) {
            // 用户输入的用户名没有对应的用户，设置查无用户错误信息，重定向回index.jsp
            req.getSession().setAttribute("status", "userNotExist");
            resp.sendRedirect("/FinalProject/index.jsp");
        } else if (!password.equals(target.getPassword())) {
            // 用户输入密码不匹配，设置密码错误错误信息，重定向回index.jsp
            req.getSession().setAttribute("status", "passwordWrong");
            resp.sendRedirect("/FinalProject/index.jsp");
        } else {
            // 设置用户名作为参数，管理员进入菜单，用户直接进入选择题目界面
            req.setAttribute("user", target.getUsername());
            if (target.isAdministrator()) {
                req.getRequestDispatcher("/WEB-INF/main/admin.jsp").forward(req, resp);
            } else {
                req.getRequestDispatcher("/WEB-INF/main/user.jsp").forward(req, resp);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
