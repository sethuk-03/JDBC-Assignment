
package stackly;

public class Main {

    public static void main(String[] args) {

        EmployeeDAO employeeDAO = new EmployeeDAO();
        LeaveDAO leaveDAO = new LeaveDAO();

        int choice;

        do {
            System.out.println("\n===== EMPLOYEE LEAVE MANAGEMENT =====");
            System.out.println("1. Display All Employees");
            System.out.println("2. Add Employee");
            System.out.println("3. Apply for Leave");
            System.out.println("4. Display All Leave Requests");
            System.out.println("5. Approve or Reject Leave");
            System.out.println("6. Employee Leave Summary");
            System.out.println("7. Exit");

            choice = Input.readInt("Enter your choice: ");
            Input.sc.nextLine();

            switch (choice) {

                case 1:
                    employeeDAO.displayEmployees();
                    break;

                case 2:
                    System.out.print("Enter employee name: ");
                    String name = Input.sc.nextLine();

                    System.out.print("Enter department: ");
                    String department = Input.sc.nextLine();

                    System.out.print("Enter email: ");
                    String email = Input.sc.nextLine();

                    employeeDAO.addEmployee(name, department, email);
                    break;

                case 3:
                    int employeeId =
                        Input.readInt("Enter employee ID: ");
                    Input.sc.nextLine();

                    System.out.print("Enter leave type (CASUAL/SICK): ");
                    String leaveType =
                        Input.sc.nextLine().trim().toUpperCase();

                    System.out.print("Enter start date (YYYY-MM-DD): ");
                    String startDate = Input.sc.nextLine();

                    System.out.print("Enter end date (YYYY-MM-DD): ");
                    String endDate = Input.sc.nextLine();

                    System.out.print("Enter reason: ");
                    String reason = Input.sc.nextLine();

                    leaveDAO.applyLeave(
                        employeeId, leaveType,
                        startDate, endDate, reason
                    );
                    break;

                case 4:
                    leaveDAO.displayLeaveRequests();
                    break;

                case 5:
                    leaveDAO.displayLeaveRequests();

                    int requestId =
                        Input.readInt("Enter request ID: ");
                    Input.sc.nextLine();

                    System.out.print("Enter status (APPROVED/REJECTED): ");
                    String status =
                        Input.sc.nextLine().trim().toUpperCase();

                    leaveDAO.updateLeaveStatus(requestId, status);
                    break;

                case 6:
                    leaveDAO.displayLeaveSummary();
                    break;

                case 7:
                    System.out.println("Thank you for using the system!");
                    break;

                default:
                    System.out.println("Invalid choice! Try again.");
            }

        } while (choice != 7);
    }
}
