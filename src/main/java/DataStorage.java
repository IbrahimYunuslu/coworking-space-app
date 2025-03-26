import java.util.List;

public class DataStorage {
    public static List<Workspace> loadWorkspaces() {
        return WorkspaceDAO.getAllWorkspaces();
    }

    public static void saveWorkspace(Workspace workspace) {
        WorkspaceDAO.addWorkspace(workspace);
    }

    public static State loadState() {
        List<Reservation> reservations = ReservationDAO.getAllReservations();
        int counter = getNextReservationId();
        return new State(reservations, counter);
    }

    public static void saveReservation(Reservation reservation) {
        ReservationDAO.addReservation(reservation);
    }

    public static void cancelReservation(int reservationId) {
        ReservationDAO.cancelReservation(reservationId);
    }

    private static int getNextReservationId() {
        return 1;
    }
}