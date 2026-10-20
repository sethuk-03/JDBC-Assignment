
package stackly;

import java.util.Scanner;

public class Input {

    public static final Scanner sc = new Scanner(System.in);

    private Input() {
    }

    public static int readInt(String message) {

        while (true) {

            System.out.print(message);

            if (sc.hasNextInt()) {
                return sc.nextInt();
            }

            System.out.println("Invalid input! Please enter a number.");
            sc.next();
        }
    }
}
