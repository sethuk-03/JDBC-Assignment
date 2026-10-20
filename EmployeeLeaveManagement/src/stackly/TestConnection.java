
package stackly;

import java.sql.Connection;
import java.sql.SQLException;

public class TestConnection {

    public static void main(String[] args) {

        try {
            Connection con = DBConnection.getConnection();

            if (con != null && !con.isClosed()) {
                System.out.println("Connection test successful!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
