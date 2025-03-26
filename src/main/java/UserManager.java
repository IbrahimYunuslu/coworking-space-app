import java.util.List;
import java.util.Scanner;
import java.util.Optional;

public class UserManager {
    private List<Workspace> workspaces;
    private List<Reservation> reservations;
    private int reservationCounter;

    public UserManager(List<Workspace> workspaces, List<Reservation> reservations, int reservationCounter) {
        this.workspaces = workspaces;
        this.reservations = reservations;
        this.reservationCounter = reservationCounter;
    }

    public void userMenu(Scanner scanner) {
        while (true) {
            System.out.println("\nUser Menu");
            System.out.println("1. Browse Available Spaces");
            System.out.println("2. Make a Reservation");
            System.out.println("3. View My Reservations");
            System.out.println("4. Cancel a Reservation");
            System.out.println("5. Back to Main Menu");
            System.out.print("Choose an option: ");

            try {
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
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                scanner.nextLine();
            }
        }
    }

    private void browseAvailableSpaces() {
        workspaces = DataStorage.loadWorkspaces();
        workspaces.stream()
                .filter(Workspace::isAvailable)
                .forEach(System.out::println);
    }

    void makeReservation(Scanner scanner) {
        try {
            System.out.print("Enter workspace ID: ");
            int workspaceId = scanner.nextInt();
            scanner.nextLine();

            Optional<Workspace> workspaceOpt = workspaces.stream()
                    .filter(w -> w.getId() == workspaceId)
                    .findFirst();

            if (workspaceOpt.isEmpty()) {
                System.out.println("Workspace not found!");
                return;
            }

            Workspace workspace = workspaceOpt.get();
            if (!workspace.isAvailable()) {
                System.out.println("Workspace is not available!");
                return;
            }

            System.out.print("Enter your name: ");
            String name = scanner.nextLine();
            System.out.print("Enter date (yyyy-mm-dd): ");
            String date = scanner.nextLine();
            System.out.print("Enter start time (HH:mm): ");
            String startTime = scanner.nextLine();
            System.out.print("Enter end time (HH:mm): ");
            String endTime = scanner.nextLine();

            Reservation reservation = new Reservation(
                    reservationCounter++,
                    workspaceId,
                    name,
                    date,
                    startTime,
                    endTime);

            DataStorage.saveReservation(reservation);
            System.out.println("Reservation created successfully!");

        } catch (Exception e) {
            System.out.println("Error making reservation: " + e.getMessage());
        }
    }

    private void viewMyReservations(Scanner scanner) {
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();

        reservations = DataStorage.loadState().getReservations();
        reservations.stream()
                .filter(r -> r.getCustomerName().equalsIgnoreCase(name))
                .forEach(System.out::println);
    }

    private void cancelReservation(Scanner scanner) {
        System.out.print("Enter reservation ID to cancel: ");
        int reservationId = scanner.nextInt();
        scanner.nextLine();

        DataStorage.cancelReservation(reservationId);
        System.out.println("Reservation cancelled successfully!");
    }
}