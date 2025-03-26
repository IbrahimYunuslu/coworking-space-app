import java.util.List;
import java.util.Scanner;

public class CoworkingSpaceApp {

    private static List<Workspace> workspaces;
    private static List<Reservation> reservations;
    private static int reservationCounter;

    public static void main(String[] args) {
        workspaces = DataStorage.loadWorkspaces();
        State state = DataStorage.loadState();
        reservations = state.getReservations();
        reservationCounter = state.getReservationCounter();

        Scanner scanner = new Scanner(System.in);
        AdminManager adminManager = new AdminManager(workspaces, reservations);
        UserManager userManager = new UserManager(workspaces, reservations, reservationCounter);

        while (true) {
            System.out.println("Welcome to the Coworking Space Reservation System!");
            System.out.println("1. Admin Login");
            System.out.println("2. User Login");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
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
                    DataStorage.saveWorkspaces(workspaces);
                    DataStorage.saveState(reservations, reservationCounter);
                    System.out.println("Thank you for using the system. Goodbye!");
                    System.exit(0);
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}