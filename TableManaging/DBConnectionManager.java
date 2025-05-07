package TableManaging;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnectionManager {
    private static final String URL      =
            "jdbc:mysql://localhost:3306/quackstagram?serverTimezone=UTC&useSSL=false";
    private static final String USER     = "root";
    private static final String PASSWORD = "";

    private static Connection conn;

    private DBConnectionManager() { }

    public static Connection getConnection() throws SQLException {
        if (conn == null || conn.isClosed()) {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
        }
        return conn;
    }
}
