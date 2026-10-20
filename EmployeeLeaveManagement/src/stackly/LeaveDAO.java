
package stackly;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class LeaveDAO {

    public void applyLeave(
            int employeeId,
            String leaveType,
            String startDate,
            String endDate,
            String reason) {

        String sql =
            "INSERT INTO leave_requests " +
            "(employee_id, leave_type, start_date, end_date, reason) " +
            "VALUES (?, ?, ?, ?, ?)";

        try {
            LocalDate start = LocalDate.parse(startDate);
            LocalDate end = LocalDate.parse(endDate);

            if (end.isBefore(start)) {
                System.out.println(
                    "End date cannot be before start date!"
                );
                return;
            }

            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, employeeId);
            ps.setString(2, leaveType);
            ps.setDate(3, java.sql.Date.valueOf(start));
            ps.setDate(4, java.sql.Date.valueOf(end));
            ps.setString(5, reason);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println(
                    "Leave application submitted successfully!"
                );
                System.out.println("Status: PENDING");
            }

        } catch (DateTimeParseException e) {
            System.out.println(
                "Invalid date! Use YYYY-MM-DD format."
            );
        } catch (SQLException e) {
            System.out.println(
                "Error applying for leave: " + e.getMessage()
            );
        }}

public void displayLeaveRequests() {

    String sql =
        "SELECT lr.request_id, e.employee_name, " +
        "lr.leave_type, lr.start_date, lr.end_date, " +
        "lr.reason, lr.status " +
        "FROM leave_requests lr " +
        "JOIN employees e ON lr.employee_id = e.employee_id " +
        "ORDER BY lr.request_id DESC";

    try {
        java.sql.Connection con = DBConnection.getConnection();

        java.sql.PreparedStatement ps =
            con.prepareStatement(sql);

        java.sql.ResultSet rs = ps.executeQuery();

        System.out.println("\n===== ALL LEAVE REQUESTS =====");

        while (rs.next()) {
            System.out.println(
                "Request ID: " + rs.getInt("request_id") +
                " | Employee: " + rs.getString("employee_name") +
                " | Type: " + rs.getString("leave_type") +
                " | Start: " + rs.getDate("start_date") +
                " | End: " + rs.getDate("end_date") +
                " | Reason: " + rs.getString("reason") +
                " | Status: " + rs.getString("status")
            );
        }

    } catch (java.sql.SQLException e) {
        System.out.println(
            "Error displaying leave requests: " + e.getMessage()
        );
    }}


public void updateLeaveStatus(int requestId, String newStatus) {

    if (!newStatus.equals("APPROVED") &&
        !newStatus.equals("REJECTED")) {

        System.out.println("Invalid leave status!");
        return;
    }

    String selectSql =
        "SELECT employee_id, start_date, end_date " +
        "FROM leave_requests " +
        "WHERE request_id = ? AND status = 'PENDING'";

    String updateSql =
        "UPDATE leave_requests SET status = ? " +
        "WHERE request_id = ? AND status = 'PENDING'";

    String balanceSql =
        "UPDATE employees SET leave_balance = leave_balance - ? " +
        "WHERE employee_id = ? AND leave_balance >= ?";

    try {
        java.sql.Connection con = DBConnection.getConnection();

        con.setAutoCommit(false);

        try {
            java.sql.PreparedStatement select =
                con.prepareStatement(selectSql);

            select.setInt(1, requestId);

            java.sql.ResultSet rs = select.executeQuery();

            if (!rs.next()) {
                System.out.println(
                    "Request not found or already processed!"
                );
                con.rollback();
                return;
            }

            int employeeId = rs.getInt("employee_id");

            java.time.LocalDate start =
                rs.getDate("start_date").toLocalDate();

            java.time.LocalDate end =
                rs.getDate("end_date").toLocalDate();

            int days = (int) (
                java.time.temporal.ChronoUnit.DAYS.between(start, end) + 1
            );

            if (newStatus.equals("APPROVED")) {

                java.sql.PreparedStatement balance =
                    con.prepareStatement(balanceSql);

                balance.setInt(1, days);
                balance.setInt(2, employeeId);
                balance.setInt(3, days);

                int updatedBalance = balance.executeUpdate();

                if (updatedBalance == 0) {
                    System.out.println(
                        "Insufficient leave balance! Request not approved."
                    );
                    con.rollback();
                    return;
                }
            }

            java.sql.PreparedStatement update =
                con.prepareStatement(updateSql);

            update.setString(1, newStatus);
            update.setInt(2, requestId);

            int rows = update.executeUpdate();

            if (rows > 0) {
                con.commit();
                System.out.println(
                    "Leave request " + newStatus.toLowerCase() +
                    " successfully!"
                );
            } else {
                con.rollback();
            }

        } catch (java.sql.SQLException e) {
            con.rollback();
            throw e;
        } finally {
            con.setAutoCommit(true);
        }

    } catch (java.sql.SQLException e) {
        System.out.println(
            "Error updating leave status: " + e.getMessage()
        );
    }}
    public void displayLeaveSummary() {
        String sql =
            "SELECT e.employee_id, e.employee_name, " +
            "COUNT(lr.request_id) AS total_requests " +
            "FROM employees e " +
            "LEFT JOIN leave_requests lr " +
            "ON e.employee_id = lr.employee_id " +
            "GROUP BY e.employee_id, e.employee_name " +
            "ORDER BY total_requests DESC";

        try {
            java.sql.Connection con = DBConnection.getConnection();
            java.sql.PreparedStatement ps = con.prepareStatement(sql);
            java.sql.ResultSet rs = ps.executeQuery();

            System.out.println("\n===== EMPLOYEE LEAVE SUMMARY =====");

            while (rs.next()) {
                System.out.println(
                    "ID: " + rs.getInt("employee_id") +
                    " | Name: " + rs.getString("employee_name") +
                    " | Total Requests: " + rs.getInt("total_requests")
                );
            }
        } catch (java.sql.SQLException e) {
            System.out.println("Error displaying leave summary: " + e.getMessage());
        }
    

    }
}
