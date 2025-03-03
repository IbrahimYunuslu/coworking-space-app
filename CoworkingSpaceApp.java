import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.*;

public class CoworkingSpaceApp {
    private static List<Workspace> workspaces = new ArrayList<>();
    private static List<Reservation> reservations = new ArrayList<>();
    private static int reservationCounter = 1;
    private static final String WORKSPACE_FILE = "workspaces.dat";
    private static final String STATE_FILE = "state.dat";

    public static void main(String[] args) {
        loadWorkspaces();
        loadState();
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
                    saveWorkspaces();
                    saveState();
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
        try {
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
        } catch (Exception e) {
            System.out.println("Error adding workspace: " + e.getMessage());
        }
    }

    private static void removeWorkspace(Scanner scanner) {
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

            Reservation reservation = new Reservation(reservationCounter++, workspaceId, name, date, startTime, endTime);
            reservations.add(reservation);
            System.out.println("Reservation made successfully!");
        } catch (Exception e) {
            System.out.println("Error making reservation: " + e.getMessage());
        }
    }

    private static void viewMyReservations(Scanner scanner) {
        try {
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
                throw new CustomException("No reservations found for " + name);
            }
        } catch (CustomException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("Error viewing reservations: " + e.getMessage());
        }
    }

    private static void cancelReservation(Scanner scanner) {
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

    private static void loadWorkspaces() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(WORKSPACE_FILE))) {
            workspaces = (List<Workspace>) ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("No existing workspace file found. Starting with an empty list.");
        } catch (Exception e) {
            System.out.println("Error loading workspaces: " + e.getMessage());
        }
    }

    private static void saveWorkspaces() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(WORKSPACE_FILE))) {
            oos.writeObject(workspaces);
        } catch (Exception e) {
            System.out.println("Error saving workspaces: " + e.getMessage());
        }
    }

    private static void loadState() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(STATE_FILE))) {
            reservations = (List<Reservation>) ois.readObject();
            reservationCounter = ((Integer) ois.readObject()).intValue();
        } catch (FileNotFoundException e) {
            System.out.println("No existing state file found. Starting with an empty list.");
        } catch (Exception e) {
            System.out.println("Error loading state: " + e.getMessage());
        }
    }

    private static void saveState() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(STATE_FILE))) {
            oos.writeObject(reservations);
            oos.writeObject(Integer.valueOf(reservationCounter));
        } catch (Exception e) {
            System.out.println("Error saving state: " + e.getMessage());
        }
    }
}