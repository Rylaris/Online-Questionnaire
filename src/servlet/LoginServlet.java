package servlet;

import com.mysql.cj.log.Log;
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
        String username = req.getParameter("username");
        String password = UserManager.sha256(req.getParameter("password"));
        User target = null;
        try {
            target = UserManager.getUser(username);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (target == null) {
            out.print("用户不存在，请先进行注册");
        } else if (!password.equals(target.getPassword())) {
            out.print("输入密码错误");
            req.getSession().setAttribute("status", "passwordWrong");
            resp.sendRedirect("/FinalProject/index.jsp");
        } else {
            out.print("success");
            req.getRequestDispatcher("/WEB-INF/main/admin.html").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
