import java.util.List;
import java.util.Scanner;

import javax.persistence.EntityManager;

import java.util.Optional;

public class AdminManager {
    private List<Workspace> workspaces;
    private List<Reservation> reservations;

    public AdminManager() {
        this.workspaces = DataStorage.loadWorkspaces();
        this.reservations = DataStorage.getAllReservations();
    }

    public void adminMenu(Scanner scanner) {
        while (true) {
            System.out.println("\nAdmin Menu");
            System.out.println("1. Add a Workspace");
            System.out.println("2. Remove a Workspace");
            System.out.println("3. View All Reservations");
            System.out.println("4. Back to Main Menu");
            System.out.print("Choose an option: ");

            try {
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
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                scanner.nextLine();
            }
        }
    }

    void addWorkspace(Scanner scanner) {
        try {
            System.out.print("Enter workspace ID: ");
            int id = scanner.nextInt();
            scanner.nextLine();
            System.out.print("Enter workspace type: ");
            String type = scanner.nextLine();
            System.out.print("Enter price: ");
            double price = scanner.nextDouble();
            scanner.nextLine();

            Workspace workspace = new Workspace(id, type, price, true);
            DataStorage.saveWorkspace(workspace);
            workspaces = DataStorage.loadWorkspaces(); // Refresh list
            System.out.println("Workspace added successfully!");
        } catch (Exception e) {
            System.out.println("Error adding workspace: " + e.getMessage());
        }
    }

    void removeWorkspace(Scanner scanner) {
        try {
            System.out.print("Enter workspace ID to remove: ");
            int id = scanner.nextInt();
            scanner.nextLine();

            Optional<Workspace> workspaceOpt = workspaces.stream()
                    .filter(w -> w.getId() == id)
                    .findFirst();

            if (workspaceOpt.isPresent()) {
                boolean hasReservations = reservations.stream()
                        .anyMatch(r -> r.getWorkspace().getId() == id);

                if (hasReservations) {
                    System.out.println("Cannot remove workspace - it has active reservations!");
                    return;
                }

                EntityManager em = JPAUtil.getEntityManager();
                try {
                    em.getTransaction().begin();
                    Workspace workspace = em.find(Workspace.class, id);
                    if (workspace != null) {
                        em.remove(workspace);
                        workspaces = DataStorage.loadWorkspaces();
                        System.out.println("Workspace removed successfully!");
                    }
                    em.getTransaction().commit();
                } finally {
                    em.close();
                }
            } else {
                System.out.println("Workspace not found!");
            }
        } catch (Exception e) {
            System.out.println("Error removing workspace: " + e.getMessage());
        }
    }

    private void viewAllReservations() {
        reservations = DataStorage.getAllReservations();
        if (reservations.isEmpty()) {
            System.out.println("No reservations found.");
        } else {
            reservations.forEach(System.out::println);
        }
    }
}