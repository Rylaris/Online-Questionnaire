package servlet;

import common.QuestionManager;
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
 * @date 2020/06/15
 */
@WebServlet("/TestServlet")
public class TestServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 设置编码防止乱码
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");

        // 接收index.jsp传递过来的登录参数
        String username = req.getParameter("username");
        String password = UserManager.sha256(req.getParameter("password"));
        String data = "";
        String method = req.getMethod();
        String POST = "POST";

        User target = null;
        PrintWriter out = resp.getWriter();

        // 在数据库中寻找用户输入的用户名对应的用户
        try {
            target = UserManager.getUser(username);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // 错误判断和处理
        if (!POST.equals(method)) {
            data = "请求方法错误";
        } else {
            if (target == null) {
                data = "用户不存在";
            } else if (!password.equals(target.getPassword())) {
                data = "密码错误";
            } else {
                data = target.toString();
                try {
                    data += "\n录入题目数量：" + QuestionManager.questionNumMakeByUser(username);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        out.println(data);
    }
}
