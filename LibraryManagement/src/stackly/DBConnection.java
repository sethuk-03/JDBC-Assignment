package stackly;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static Connection con;

    private DBConnection() {
    }

    public static Connection getConnection() throws SQLException {

        if (con == null || con.isClosed()) {
            con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/library_db",
                "root",
                "YOUR_MYSQL_PASSWORD"
            );

            System.out.println("Database connected successfully!");
        }

        return con;
    }
}




