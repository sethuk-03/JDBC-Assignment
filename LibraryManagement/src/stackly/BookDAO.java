
package stackly;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class BookDAO {

    public void displayBooks() {

        String sql = "SELECT * FROM books";

        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            System.out.println("----- BOOK LIST -----");

            while (rs.next()) {
                System.out.println(
                    "Book ID: " + rs.getInt("book_id")
                    + ", Title: " + rs.getString("title")
                    + ", Author: " + rs.getString("author")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    public void addBook() {

    	Scanner sc = Input.sc;

    System.out.print("Enter book title: ");
    String title = sc.nextLine();

    System.out.print("Enter author name: ");
    String author = sc.nextLine();

    System.out.print("Enter price: ");
    double price = sc.nextDouble();

    int quantity = Input.readInt("Enter quantity: ");

    String sql = "INSERT INTO books (title, author, price, quantity) "
               + "VALUES (?, ?, ?, ?)";

    try {
        Connection con = DBConnection.getConnection();

        PreparedStatement ps = con.prepareStatement(sql);

        ps.setString(1, title);
        ps.setString(2, author);
        ps.setDouble(3, price);
        ps.setInt(4, quantity);

        int rows = ps.executeUpdate();

        if (rows > 0) {
            System.out.println("Book added successfully!");
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }}

    public void updateBook() {

    	Scanner sc = Input.sc;

    
    int bookId = Input.readInt("Enter Book ID to update: ");

    System.out.print("Enter new price: ");
    double price = sc.nextDouble();

    
    int quantity = Input.readInt("Enter new quantity: ");	

    String sql = "UPDATE books SET price = ?, quantity = ? "
               + "WHERE book_id = ?";

    try {
        Connection con = DBConnection.getConnection();

        PreparedStatement ps = con.prepareStatement(sql);

        ps.setDouble(1, price);
        ps.setInt(2, quantity);
        ps.setInt(3, bookId);

        int rows = ps.executeUpdate();

        if (rows > 0) {
            System.out.println("Book updated successfully!");
        } else {
            System.out.println("Book ID not found!");
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }}

    public void deleteBook() {

    	Scanner sc = Input.sc;

    
    int bookId = Input.readInt("Enter Book ID to delete: ");

    String sql = "DELETE FROM books WHERE book_id = ?";

    try {
        Connection con = DBConnection.getConnection();

        PreparedStatement ps = con.prepareStatement(sql);

        ps.setInt(1, bookId);

        int rows = ps.executeUpdate();

        if (rows > 0) {
            System.out.println("Book deleted successfully!");
        } else {
            System.out.println("Book ID not found!");
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }



}

}


