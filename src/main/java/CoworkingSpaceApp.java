import java.util.List;
import java.util.Scanner;

import javax.persistence.EntityManager;

public class CoworkingSpaceApp {

    public static void testDatabaseConnection() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();

            Workspace testSpace = new Workspace();
            testSpace.setType("Test Desk");
            testSpace.setPrice(99.99);
            testSpace.setAvailable(true);

            em.persist(testSpace);
            em.getTransaction().commit();

            System.out.println("Workspace saved with ID: " + testSpace.getId());
        } finally {
            em.close();
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        AdminManager adminManager = new AdminManager();
        UserManager userManager = new UserManager();

        try {
            while (true) {
                System.out.println("\nCoworking Space Reservation System");
                System.out.println("1. Admin Login");
                System.out.println("2. User Login");
                System.out.println("3. Exit");
                System.out.print("Choose an option: ");

                if (!scanner.hasNextLine()) {
                    System.out.println("No input available. Exiting...");
                    break;
                }

                String input = scanner.nextLine().trim();

                if (input.isEmpty()) {
                    System.out.println("Please enter a valid option (1-3)");
                    continue;
                }

                try {
                    int choice = Integer.parseInt(input);

                    switch (choice) {
                        case 1:
                            adminManager.adminMenu(scanner);
                            break;
                        case 2:
                            userManager.userMenu(scanner);
                            break;
                        case 3:
                            System.out.println("Exiting system...");
                            return;
                        default:
                            System.out.println("Invalid option. Please try again.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Please enter a number (1-3)");
                }
            }
        } finally {
            JPAUtil.close();
            scanner.close();
        }
    }
}