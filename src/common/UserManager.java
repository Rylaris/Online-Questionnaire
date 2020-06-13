package common;

import model.User;
import util.Connect;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.sql.*;

/**
 * @author 软工1801温蟾圆
 * @date 2020/06/12
 */
public class UserManager {
    /**
     * 根据指定的用户名查询对应的用户信息
     *
     * @param username 指定的用户名
     * @return 返回该用户的全部信息，如果查不到有该用户返回空
     */
    public static User getUser(String username) throws SQLException {
        User user = null;
        String[] parameters = {username};
        ResultSet resultSet = Connect.executeSelect("SELECT * FROM User WHERE username=?", parameters);
        if (resultSet.next()) {
            user = new User();
            user.setUsername(resultSet.getString("username"));
            user.setPassword(resultSet.getString("password"));
            user.setAdministrator(resultSet.getInt("administrator") == 1);
        }
        return user;
    }

    /**
     * 将新用户添加到数据库，如果用户已存在，则不添加。
     * 密码以SHA256的加密方式保存到数据库。
     *
     * @param newUser 需要添加到数据库的新用户
     * @return 返回添加用户的状态，0为添加成功，1为已存在用户
     */
    public static int addUser(User newUser) throws SQLException {
        String username = newUser.getUsername();
        String password = sha256(newUser.getPassword());
        String administrator = newUser.isAdministrator() ? "1" : "0";
        if (getUser(username) != null) {
            return 1;
        }
        String[] parameters = {username, password, administrator};
        Connect.executeUpdate("INSERT INTO User(username,password,administrator) VALUES (?, ?, ?)", parameters);
        return 0;
    }

    /**
     * 删除指定用户名的用户
     *
     * @param username 指定的用户名
     */
    private static void deleteUser(String username) throws SQLException {
        String[] parameters = {username};
        Connect.executeUpdate("DELETE FROM User WHERE username=?", parameters);
    }

    /**
     * 将一个字符串进行SHA256加密。
     *
     * @param base 需要进行SHA256加密的字符串
     * @return 加密完成的字符串
     */
    public static String sha256(String base) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(base.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
