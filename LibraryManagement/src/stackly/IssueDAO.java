
package stackly;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Scanner;

public class IssueDAO {

    public void issueBook() {

    	Scanner sc = Input.sc;

    	int bookId = Input.readInt("Enter Book ID: ");
    	int memberId = Input.readInt("Enter Member ID: ");


        String checkSql =
            "SELECT quantity FROM books WHERE book_id = ?";

        String issueSql =
            "INSERT INTO issued_books " +
            "(book_id, member_id, issue_date, status) " +
            "VALUES (?, ?, ?, 'ISSUED')";

        String updateSql =
            "UPDATE books SET quantity = quantity - 1 " +
            "WHERE book_id = ? AND quantity > 0";

        try {
            Connection con = DBConnection.getConnection();

            con.setAutoCommit(false);

            try {
                PreparedStatement check =
                    con.prepareStatement(checkSql);

                check.setInt(1, bookId);

                ResultSet rs = check.executeQuery();

                if (!rs.next()) {
                    System.out.println("Book ID not found!");
                    con.rollback();
                    return;
                }

                if (rs.getInt("quantity") <= 0) {
                    System.out.println("Book is out of stock!");
                    con.rollback();
                    return;
                }

                PreparedStatement issue =
                    con.prepareStatement(issueSql);

                issue.setInt(1, bookId);
                issue.setInt(2, memberId);
                issue.setDate(3, Date.valueOf(LocalDate.now()));

                int rows = issue.executeUpdate();

                PreparedStatement update =
                    con.prepareStatement(updateSql);

                update.setInt(1, bookId);

                update.executeUpdate();

                con.commit();

                if (rows > 0) {
                    System.out.println("Book issued successfully!");
                }

            } catch (SQLException e) {
                con.rollback();
                throw e;
            } finally {
                con.setAutoCommit(true);
            }

        } catch (SQLException e) {
            System.out.println("Issue failed: " + e.getMessage());
        }
    }

    public void returnBook() {

    	Scanner sc = Input.sc;

    	int issueId = Input.readInt("Enter Issue ID: ");

        String selectSql =
            "SELECT book_id FROM issued_books " +
            "WHERE issue_id = ? AND status = 'ISSUED'";

        String returnSql =
            "UPDATE issued_books SET status = 'RETURNED', " +
            "return_date = ? WHERE issue_id = ?";

        String updateSql =
            "UPDATE books SET quantity = quantity + 1 " +
            "WHERE book_id = ?";

        try {
            Connection con = DBConnection.getConnection();

            con.setAutoCommit(false);

            try {
                PreparedStatement select =
                    con.prepareStatement(selectSql);

                select.setInt(1, issueId);

                ResultSet rs = select.executeQuery();

                if (!rs.next()) {
                    System.out.println(
                        "Invalid Issue ID or book already returned!"
                    );
                    con.rollback();
                    return;
                }

                int bookId = rs.getInt("book_id");

                PreparedStatement updateIssue =
                    con.prepareStatement(returnSql);

                updateIssue.setDate(
                    1, Date.valueOf(LocalDate.now())
                );
                updateIssue.setInt(2, issueId);
                updateIssue.executeUpdate();

                PreparedStatement updateBook =
                    con.prepareStatement(updateSql);

                updateBook.setInt(1, bookId);
                updateBook.executeUpdate();

                con.commit();

                System.out.println("Book returned successfully!");

            } catch (SQLException e) {
                con.rollback();
                throw e;
            } finally {
                con.setAutoCommit(true);
            }

        } catch (SQLException e) {
            System.out.println("Return failed: " + e.getMessage());
        }}
        

public void displayIssueReport() {

    String sql =
        "SELECT ib.issue_id, b.title, m.member_name, " +
        "ib.issue_date, ib.return_date, ib.status " +
        "FROM issued_books ib " +
        "JOIN books b ON ib.book_id = b.book_id " +
        "JOIN members m ON ib.member_id = m.member_id " +
        "ORDER BY ib.issue_id DESC";

    try {
        java.sql.Connection con = DBConnection.getConnection();

        java.sql.PreparedStatement ps =
            con.prepareStatement(sql);

        java.sql.ResultSet rs = ps.executeQuery();

        System.out.println("\n===== BOOK ISSUE REPORT =====");

        while (rs.next()) {
            System.out.println(
                "Issue ID: " + rs.getInt("issue_id") +
                " | Book: " + rs.getString("title") +
                " | Member: " + rs.getString("member_name") +
                " | Issue Date: " + rs.getDate("issue_date") +
                " | Return Date: " + rs.getDate("return_date") +
                " | Status: " + rs.getString("status")
            );
        }

    } catch (java.sql.SQLException e) {
        e.printStackTrace();
    }}

public void displayBookIssueCounts() {

    String sql =
        "SELECT b.book_id, b.title, " +
        "COUNT(ib.issue_id) AS total_issues " +
        "FROM books b " +
        "LEFT JOIN issued_books ib ON b.book_id = ib.book_id " +
        "GROUP BY b.book_id, b.title " +
        "ORDER BY total_issues DESC";

    try {
        java.sql.Connection con = DBConnection.getConnection();

        java.sql.PreparedStatement ps =
            con.prepareStatement(sql);

        java.sql.ResultSet rs = ps.executeQuery();

        System.out.println("\n===== BOOK ISSUE COUNTS =====");

        while (rs.next()) {
            System.out.println(
                "Book ID: " + rs.getInt("book_id") +
                " | Title: " + rs.getString("title") +
                " | Total Issues: " + rs.getInt("total_issues")
            );
        }

    } catch (java.sql.SQLException e) {
        e.printStackTrace();
    }



    }
}
