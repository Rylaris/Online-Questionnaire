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
 * @date 2020/06/14
 */

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
    public RegisterServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        PrintWriter out = resp.getWriter();
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String confirmPassword = req.getParameter("confirmPassword");
        String isAdmin = req.getParameter("isAdmin");
        User target = null;
        if (username == null) {
            req.getSession().setAttribute("status", "notLogin");
            resp.sendRedirect("/FinalProject/index.jsp");
        } else {
            try {
                target = UserManager.getUser(username);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if (!password.equals(confirmPassword)) {
                req.getSession().setAttribute("status", "passwordWrong");
                resp.sendRedirect("/FinalProject/register.jsp");
            } else if (target != null) {
                out.print("用户不存在，请先进行注册");
                req.getSession().setAttribute("status", "userExist");
                resp.sendRedirect("/FinalProject/register.jsp");
            } else {
                User newUser = new User(username, password, "on".equals(isAdmin));
                try {
                    UserManager.addUser(newUser);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
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
