
package stackly;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class MemberDAO {

    public void displayMembers() {

        String sql = "SELECT * FROM members ORDER BY member_id";

        try {
            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            System.out.println("\n----- MEMBER LIST -----");

            while (rs.next()) {
                System.out.println(
                    "Member ID: " + rs.getInt("member_id")
                    + ", Name: " + rs.getString("member_name")
                    + ", Phone: " + rs.getString("phone")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addMember() {

    	Scanner sc = Input.sc;

        System.out.print("Enter member name: ");
        String name = sc.nextLine();

        System.out.print("Enter phone number: ");
        String phone = sc.nextLine();

        String sql = "INSERT INTO members (member_name, phone) VALUES (?, ?)";

        try {
            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, name);
            ps.setString(2, phone);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Member added successfully!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
