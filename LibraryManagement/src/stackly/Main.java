
package stackly;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

    	Scanner sc = Input.sc;

        BookDAO bookDAO = new BookDAO();
        MemberDAO memberDAO = new MemberDAO();
        IssueDAO issueDAO = new IssueDAO();

        int choice;

        do {
            System.out.println("\n===== LIBRARY MANAGEMENT SYSTEM =====");
            System.out.println("1. Display All Books");
            System.out.println("2. Add Book");
            System.out.println("3. Update Book");
            System.out.println("4. Delete Book");
            System.out.println("5. Exit");
            System.out.println("6. Display All Members");
            System.out.println("7. Add New Member");
            System.out.println("8. Issue a Book");
            System.out.println("9. Return a Book");
            System.out.println("10. Display Book Issue Report");
            System.out.println("11. Display Book Issue Counts");

            choice = Input.readInt("Enter your choice: ");
            sc.nextLine();

            switch (choice) {

                case 1:
                    bookDAO.displayBooks();
                    break;

                case 2:
                    bookDAO.addBook();
                    break;

                case 3:
                    bookDAO.updateBook();
                    break;

                case 4:
                    bookDAO.deleteBook();
                    break;

                case 5:
                    System.out.println(
                        "Thank you for using the Library Management System!"
                    );
                    break;

                case 6:
                    memberDAO.displayMembers();
                    break;

                case 7:
                    memberDAO.addMember();
                    break;

                case 8:
                    issueDAO.issueBook();
                    break;

                case 9:
                    issueDAO.returnBook();
                    break;
                    
                case 10:
                    issueDAO.displayIssueReport();
                    break;    
                    
                case 11:
                    issueDAO.displayBookIssueCounts();
                    break;    
                default:
                    System.out.println(
                        "Invalid choice! Please try again."
                    );
            }

        } while (choice != 5);

        sc.close();
    }
}
