package TableManaging;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnectionManager {
    private static final String URL      =
            "jdbc:mysql://localhost:3306/quackstagram?serverTimezone=UTC&useSSL=false";
    private static final String USER     = "root";
    private static final String PASSWORD = "";

    public static void main(String[] args) {
        System.out.println("Attempting to connect to database…");
        try (Connection c = DBConnectionManager.getConnection()) {
            if (c != null && !c.isClosed()) {
                System.out.println("✅ Connection successful!");
                // Optional: print some metadata
                System.out.println("Database product: " +
                        c.getMetaData().getDatabaseProductName() + " " +
                        c.getMetaData().getDatabaseProductVersion());
            } else {
                System.out.println("❌ Connection returned a closed or null object.");
            }
        } catch (SQLException e) {
            System.err.println("❌ Failed to connect:");
            e.printStackTrace();
        }
    }

    private static Connection conn;

    private DBConnectionManager() { }

    public static Connection getConnection() throws SQLException {
        if (conn == null || conn.isClosed()) {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
        }
        return conn;
    }
}
