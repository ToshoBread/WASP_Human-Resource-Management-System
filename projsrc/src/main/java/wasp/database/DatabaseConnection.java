package wasp.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class DatabaseConnection {

    private static String url = "jdbc:mysql://localhost:3306/wasp_hrms";
    private static String user = "root";
    private static String password = "";
    private static Connection conn = null;

    protected Connection getConnection() {
        try {
            conn = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

}
