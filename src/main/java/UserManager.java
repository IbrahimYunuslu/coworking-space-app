import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.Optional;
import java.util.stream.Collectors;

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
                scanner.nextLine(); // consume newline

                switch (choice) {
                    case 1:
                        browseAvailableSpaces();
                        break;
                    case 2:
                        try {
                            makeReservation(scanner);
                        } catch (CustomException e) {
                            System.out.println("Error: " + e.getMessage());
                        }
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
            } catch (InputMismatchException e) {
                System.out.println("Please enter a valid number.");
                scanner.nextLine(); // clear invalid input
            }
        }
    }

    private void browseAvailableSpaces() {
        if (workspaces.isEmpty()) {
            System.out.println("No workspaces available.");
        } else {
            workspaces.stream()
                    .filter(Workspace::isAvailable)
                    .forEach(System.out::println);
        }
    }

    public void makeReservation(Scanner scanner) throws CustomException {
        try {
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

            Optional<Workspace> workspaceOpt = workspaces.stream()
                    .filter(w -> w.getId() == workspaceId)
                    .findAny();

            if (workspaceOpt.isPresent()) {
                Workspace workspace = workspaceOpt.get();
                if (workspace.isAvailable()) {
                    Reservation reservation = new Reservation(reservationCounter++, workspaceId, name, date, startTime,
                            endTime);
                    reservations.add(reservation);
                    System.out.println("Reservation made successfully!");
                } else {
                    throw new CustomException("Workspace with ID " + workspaceId + " is not available.");
                }
            } else {
                throw new CustomException("Workspace with ID " + workspaceId + " not found.");
            }
        } catch (InputMismatchException e) {
            throw new CustomException("Invalid input format");
        }
    }

    public void viewMyReservations(Scanner scanner) {
        try {
            System.out.print("Enter your name: ");
            String name = scanner.nextLine();

            List<Reservation> userReservations = reservations.stream()
                    .filter(r -> r.getCustomerName().equals(name))
                    .toList();

            if (userReservations.isEmpty()) {
                throw new CustomException("No reservations found for " + name);
            } else {
                userReservations.forEach(System.out::println);
            }
        } catch (CustomException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("Error viewing reservations: " + e.getMessage());
        }
    }

    private void cancelReservation(Scanner scanner) {
        try {
            System.out.print("Enter reservation ID to cancel: ");
            int id = scanner.nextInt();
            scanner.nextLine();

            boolean removed = reservations.removeIf(reservation -> reservation.getReservationId() == id);

            if (removed) {
                System.out.println("Reservation cancelled successfully!");
            } else {
                throw new CustomException("Reservation not found.");
            }
        } catch (CustomException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("Error cancelling reservation: " + e.getMessage());
        }
    }
}