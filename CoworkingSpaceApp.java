import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CoworkingSpaceApp {
    private static List<Workspace> workspaces = new ArrayList<>();
    private static List<Reservation> reservations = new ArrayList<>();
    private static int reservationCounter = 1;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
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
                    adminMenu(scanner);
                    break;
                case 2:
                    userMenu(scanner);
                    break;
                case 3:
                    System.out.println("Thank you for using the system. Goodbye!");
                    System.exit(0);
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void adminMenu(Scanner scanner) {
        while (true) {
            System.out.println("\nAdmin Menu");
            System.out.println("1. Add a Workspace");
            System.out.println("2. Remove a Workspace");
            System.out.println("3. View All Reservations");
            System.out.println("4. Back to Main Menu");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addWorkspace(scanner);
                    break;
                case 2:
                    removeWorkspace(scanner);
                    break;
                case 3:
                    viewAllReservations();
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void addWorkspace(Scanner scanner) {
        System.out.print("Enter workspace ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter workspace type: ");
        String type = scanner.nextLine();
        System.out.print("Enter price: ");
        double price = scanner.nextDouble();
        scanner.nextLine();
        workspaces.add(new Workspace(id, type, price, true));
        System.out.println("Workspace added successfully!");
    }

    private static void removeWorkspace(Scanner scanner) {
        System.out.print("Enter workspace ID to remove: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        workspaces.removeIf(workspace -> workspace.getId() == id);
        System.out.println("Workspace removed successfully!");
    }

    private static void viewAllReservations() {
        if (reservations.isEmpty()) {
            System.out.println("No reservations found.");
        } else {
            for (Reservation reservation : reservations) {
                System.out.println(reservation);
            }
        }
    }

    private static void userMenu(Scanner scanner) {
        while (true) {
            System.out.println("\nUser Menu");
            System.out.println("1. Browse Available Spaces");
            System.out.println("2. Make a Reservation");
            System.out.println("3. View My Reservations");
            System.out.println("4. Cancel a Reservation");
            System.out.println("5. Back to Main Menu");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    browseAvailableSpaces();
                    break;
                case 2:
                    makeReservation(scanner);
                    break;
                case 3:
                    viewMyReservations(scanner);
                    break;
                case 4:
                    cancelReservation(scanner);
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void browseAvailableSpaces() {
        if (workspaces.isEmpty()) {
            System.out.println("No workspaces available.");
        } else {
            for (Workspace workspace : workspaces) {
                if (workspace.isAvailable()) {
                    System.out.println(workspace);
                }
            }
        }
    }

    private static void makeReservation(Scanner scanner) {
        System.out.print("Enter workspace ID: ");
        int workspaceId = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();
        System.out.print("Enter date (yyyy-MM-dd): ");
        String date = scanner.nextLine();
        System.out.print("Enter start time (HH:mm): ");
        String startTime = scanner.nextLine();
        System.out.print("Enter end time (HH:mm): ");
        String endTime = scanner.nextLine();

        Reservation reservation = new Reservation(reservationCounter++, workspaceId, name, date, startTime, endTime);
        reservations.add(reservation);
        System.out.println("Reservation made successfully!");
    }

    private static void viewMyReservations(Scanner scanner) {
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();
        boolean found = false;
        for (Reservation reservation : reservations) {
            if (reservation.getCustomerName().equals(name)) {
                System.out.println(reservation);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No reservations found for " + name);
        }
    }

    private static void cancelReservation(Scanner scanner) {
        System.out.print("Enter reservation ID to cancel: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        boolean removed = reservations.removeIf(reservation -> reservation.getReservationId() == id);
        if (removed) {
            System.out.println("Reservation cancelled successfully!");
        } else {
            System.out.println("Reservation not found.");
        }
    }
}