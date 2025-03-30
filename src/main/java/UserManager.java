import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Scanner;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class UserManager {
    private List<Workspace> workspaces;
    private List<Reservation> reservations;

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
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();

            System.out.print("Enter workspace ID: ");
            int workspaceId = scanner.nextInt();
            scanner.nextLine();

            Workspace workspace = em.find(Workspace.class, workspaceId);
            if (workspace == null) {
                throw new CustomException("Workspace not found");
            }

            if (!workspace.isAvailable()) {
                throw new CustomException("Workspace is not available");
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

            em.persist(reservation);
            workspace.setAvailable(false);

            transaction.commit();
            System.out.println("Reservation created successfully!");
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            System.out.println("Error making reservation: " + e.getMessage());
        } finally {
            em.close();
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
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            System.out.print("Enter reservation ID to cancel: ");
            int reservationId = scanner.nextInt();
            scanner.nextLine();

            transaction.begin();
            Reservation reservation = em.find(Reservation.class, reservationId);
            if (reservation != null) {
                reservation.getWorkspace().setAvailable(true);
                em.remove(reservation);
                transaction.commit();
                System.out.println("Reservation cancelled successfully!");
            } else {
                System.out.println("Reservation not found!");
            }
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            System.out.println("Error cancelling reservation: " + e.getMessage());
        } finally {
            em.close();
        }
    }
}