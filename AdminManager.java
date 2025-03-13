import java.util.List;
import java.util.Scanner;
import java.util.Optional;

public class AdminManager {

    private List<Workspace> workspaces;
    private List<Reservation> reservations;

    public AdminManager(List<Workspace> workspaces, List<Reservation> reservations) {
        this.workspaces = workspaces;
        this.reservations = reservations;
    }

    public void adminMenu(Scanner scanner) {
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

    private void addWorkspace(Scanner scanner) {
        try {
            System.out.print("Enter workspace ID: ");
            int id = scanner.nextInt();
            scanner.nextLine();
            System.out.print("Enter workspace type: ");
            String type = scanner.nextLine();
            System.out.print("Enter price: ");
            double price = scanner.nextDouble();
            scanner.nextLine();

            Optional<Workspace> existingWorkspace = workspaces.stream()
                                                             .filter(w -> w.getId() == id)
                                                             .findFirst();

            if (existingWorkspace.isPresent()) {
                throw new CustomException("Workspace with ID " + id + " already exists.");
            } else {
                workspaces.add(new Workspace(id, type, price, true));
                System.out.println("Workspace added successfully!");
            }
        } catch (CustomException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("Error adding workspace: " + e.getMessage());
        }
    }

    private void removeWorkspace(Scanner scanner) {
        try {
            System.out.print("Enter workspace ID to remove: ");
            int id = scanner.nextInt();
            scanner.nextLine();

            boolean removed = workspaces.removeIf(workspace -> workspace.getId() == id);

            if (removed) {
                System.out.println("Workspace removed successfully!");
            } else {
                throw new CustomException("Workspace not found.");
            }
        } catch (CustomException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("Error removing workspace: " + e.getMessage());
        }
    }

    private void viewAllReservations() {
        if (reservations.isEmpty()) {
            System.out.println("No reservations found.");
        } else {
            reservations.forEach(System.out::println);
        }
    }
}