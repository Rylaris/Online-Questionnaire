package util;

import java.sql.*;

/**
 * @author 软工1801温蟾圆
 * @date 2020/06/11
 */
public class Connect {
    private Connection connect = null;

    public Connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection(String dbName, String username, String password) {
        try {
            connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + dbName
                    + "?user=" + username + "&password=" + password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connect;
    }

    public static ResultSet executeSelect(String statement, String[] parameters) throws SQLException {
        return getPrepared(statement, parameters).executeQuery();
    }

    public static int executeUpdate(String statement, String[] parameters) throws SQLException {
        return getPrepared(statement, parameters).executeUpdate();
    }

    private static PreparedStatement getPrepared(String statement, String[] parameters) throws SQLException {
        Connect connect = new Connect();
        Connection connection = connect.getConnection("JavaWeb", "root", "zxcvbnm123");
        PreparedStatement sql = connection.prepareStatement(statement);
        for (int i = 0; i < parameters.length; i++) {
            sql.setString(i + 1, parameters[i]);
        }
        return sql;
    }
}
