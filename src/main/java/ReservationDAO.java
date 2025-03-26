import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReservationDAO {
    public static List<Reservation> getAllReservations() {
        List<Reservation> reservations = new ArrayList<>();
        String sql = "SELECT * FROM reservations";

        try (Connection conn = DatabaseManager.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                reservations.add(new Reservation(
                        rs.getInt("reservation_id"),
                        rs.getInt("workspace_id"),
                        rs.getString("customer_name"),
                        rs.getDate("date").toString(),
                        rs.getTime("start_time").toString(),
                        rs.getTime("end_time").toString()));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching reservations: " + e.getMessage());
        }
        return reservations;
    }

    public static void addReservation(Reservation reservation) {
        String sql = "INSERT INTO reservations (workspace_id, customer_name, date, start_time, end_time) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseManager.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, reservation.getWorkspaceId());
            pstmt.setString(2, reservation.getCustomerName());
            pstmt.setDate(3, Date.valueOf(reservation.getDate()));
            pstmt.setTime(4, Time.valueOf(reservation.getStartTime() + ":00"));
            pstmt.setTime(5, Time.valueOf(reservation.getEndTime() + ":00"));
            pstmt.executeUpdate();

            WorkspaceDAO.updateAvailability(reservation.getWorkspaceId(), false);
        } catch (SQLException e) {
            System.err.println("Error adding reservation: " + e.getMessage());
        }
    }

    public static void cancelReservation(int reservationId) {
        String getWorkspaceId = "SELECT workspace_id FROM reservations WHERE reservation_id = ?";
        String deleteSql = "DELETE FROM reservations WHERE reservation_id = ?";

        try (Connection conn = DatabaseManager.getConnection();
                PreparedStatement getIdStmt = conn.prepareStatement(getWorkspaceId);
                PreparedStatement deleteStmt = conn.prepareStatement(deleteSql)) {

            getIdStmt.setInt(1, reservationId);
            ResultSet rs = getIdStmt.executeQuery();

            if (rs.next()) {
                int workspaceId = rs.getInt("workspace_id");

                deleteStmt.setInt(1, reservationId);
                deleteStmt.executeUpdate();

                WorkspaceDAO.updateAvailability(workspaceId, true);
            }
        } catch (SQLException e) {
            System.err.println("Error canceling reservation: " + e.getMessage());
        }
    }
}