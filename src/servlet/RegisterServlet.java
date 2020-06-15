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
 * @date 2020/06/14
 */

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
    public RegisterServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 设置编码防止乱码
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");

        // 获取用户需要注册的信息
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String confirmPassword = req.getParameter("confirmPassword");

        User target = null;

        // 错误判断和处理
        if (username == null) {
            // 通过链接进入网页重定向回登录页面，设置错误信息没有登录
            req.getSession().setAttribute("status", "notLogin");
            resp.sendRedirect("/FinalProject/index.jsp");
        } else {
            try {
                target = UserManager.getUser(username);
            } catch (SQLException e) {
                e.printStackTrace();
            }

            if (!password.equals(confirmPassword)) {
                // 两次输入密码不一致处理
                req.getSession().setAttribute("status", "passwordWrong");
                resp.sendRedirect("/FinalProject/register.jsp");
            } else if (target != null) {
                // 注册用户已存在处理
                req.getSession().setAttribute("status", "userExist");
                resp.sendRedirect("/FinalProject/register.jsp");
            } else {
                // 注册用户，网页只提供普通用户注册，无法注册管理员
                User newUser = new User(username, password, false);
                try {
                    UserManager.addUser(newUser);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                // 注册成功回到注册页面，显示注册成功信息
                req.getSession().setAttribute("status", "registerSuccess");
                resp.sendRedirect("/FinalProject/register.jsp");
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
