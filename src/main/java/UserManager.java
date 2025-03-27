import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Scanner;

import javax.persistence.EntityManager;

import java.util.Optional;

public class UserManager {
    private List<Workspace> workspaces;
    private List<Reservation> reservations;
    private int reservationCounter;

    public UserManager() {
        this.workspaces = DataStorage.loadWorkspaces();
        this.reservations = DataStorage.getAllReservations();
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

            Workspace workspace = workspaces.stream()
                    .filter(w -> w.getId() == workspaceId)
                    .findFirst()
                    .orElse(null);

            if (workspace == null) {
                System.out.println("Workspace not found!");
                return;
            }

            if (!workspace.isAvailable()) {
                System.out.println("Workspace is not available!");
                return;
            }

            System.out.print("Enter your name: ");
            String name = scanner.nextLine();
            System.out.print("Enter date (yyyy-mm-dd): ");
            LocalDate date = LocalDate.parse(scanner.nextLine());
            System.out.print("Enter start time (HH:mm): ");
            LocalTime startTime = LocalTime.parse(scanner.nextLine());
            System.out.print("Enter end time (HH:mm): ");
            LocalTime endTime = LocalTime.parse(scanner.nextLine());

            Reservation reservation = new Reservation();
            reservation.setWorkspace(workspace);
            reservation.setCustomerName(name);
            reservation.setDate(date);
            reservation.setStartTime(startTime);
            reservation.setEndTime(endTime);

            EntityManager em = JPAUtil.getEntityManager();
            try {
                em.getTransaction().begin();
                em.persist(reservation);
                workspace.setAvailable(false);
                em.getTransaction().commit();
                System.out.println("Reservation created successfully!");
            } finally {
                em.close();
            }

        } catch (Exception e) {
            System.out.println("Error making reservation: " + e.getMessage());
        }
    }

    private void viewMyReservations(Scanner scanner) {
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();

        EntityManager em = JPAUtil.getEntityManager();
        try {
            List<Reservation> userReservations = em.createQuery(
                    "SELECT r FROM Reservation r WHERE r.customerName = :name", Reservation.class)
                    .setParameter("name", name)
                    .getResultList();

            if (userReservations.isEmpty()) {
                System.out.println("No reservations found for " + name);
            } else {
                userReservations.forEach(System.out::println);
            }
        } finally {
            em.close();
        }
    }

    private void cancelReservation(Scanner scanner) {
        System.out.print("Enter reservation ID to cancel: ");
        int reservationId = scanner.nextInt();
        scanner.nextLine();

        DataStorage.cancelReservation(reservationId);
        System.out.println("Reservation cancelled successfully!");
    }
}