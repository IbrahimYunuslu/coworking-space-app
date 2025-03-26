import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CoworkingSpaceApp {
    public static void main(String[] args) {
        DatabaseManager.initializeDatabase();

        List<Workspace> workspaces = DataStorage.loadWorkspaces();
        State state = DataStorage.loadState();
        List<Reservation> reservations = state.getReservations();
        int reservationCounter = state.getReservationCounter();

        Scanner scanner = new Scanner(System.in);
        AdminManager adminManager = new AdminManager(workspaces, reservations);
        UserManager userManager = new UserManager(workspaces, reservations, reservationCounter);

        while (true) {
            System.out.println("\nCoworking Space Reservation System");
            System.out.println("1. Admin Login");
            System.out.println("2. User Login");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");

            try {
                int choice = scanner.nextInt();
                scanner.nextLine();

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
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                scanner.nextLine();
            }
        }
    }
}