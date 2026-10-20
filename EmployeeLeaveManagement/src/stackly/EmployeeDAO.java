
package stackly;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeDAO {

    public void displayEmployees() {

        String sql = "SELECT * FROM employees ORDER BY employee_id";

        try {
            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            System.out.println("\n===== EMPLOYEE LIST =====");

            while (rs.next()) {
                System.out.println(
                    "ID: " + rs.getInt("employee_id") +
                    " | Name: " + rs.getString("employee_name") +
                    " | Department: " + rs.getString("department") +
                    " | Email: " + rs.getString("email") +
                    " | Leave Balance: " + rs.getInt("leave_balance")
                );
            }

        } catch (SQLException e) {
            System.out.println("Error displaying employees: " + e.getMessage());
        }
    }

    public void addEmployee(
            String name,
            String department,
            String email) {

        String sql =
            "INSERT INTO employees " +
            "(employee_name, department, email) VALUES (?, ?, ?)";

        try {
            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, name);
            ps.setString(2, department);
            ps.setString(3, email);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Employee added successfully!");
            }

        } catch (SQLException e) {
            System.out.println("Error adding employee: " + e.getMessage());
        }
    }
}
