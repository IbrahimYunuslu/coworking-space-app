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
        Connection conn = null;
        try {
            conn = DatabaseManager.getConnection();
            conn.setAutoCommit(false); // Start transaction

            // 1. Insert reservation
            String sql = "INSERT INTO reservations (workspace_id, customer_name, date, start_time, end_time) " +
                    "VALUES (?, ?, ?, ?, ?)";

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, reservation.getWorkspaceId());
                pstmt.setString(2, reservation.getCustomerName());
                pstmt.setDate(3, Date.valueOf(reservation.getDate()));
                pstmt.setTime(4, Time.valueOf(reservation.getStartTime() + ":00"));
                pstmt.setTime(5, Time.valueOf(reservation.getEndTime() + ":00"));
                pstmt.executeUpdate();
            }

            // 2. Update workspace availability (using the transaction-aware version)
            WorkspaceDAO.updateAvailability(conn, reservation.getWorkspaceId(), false);

            conn.commit(); // Commit transaction if both operations succeed
            System.out.println("Reservation added successfully!");
        } catch (SQLException e) {
            try {
                if (conn != null)
                    conn.rollback(); // Rollback if any operation fails
            } catch (SQLException ex) {
                System.err.println("Error during rollback: " + ex.getMessage());
            }
            System.err.println("Error adding reservation: " + e.getMessage());
            throw new RuntimeException("Failed to add reservation", e);
        } finally {
            try {
                if (conn != null) {
                    conn.setAutoCommit(true); // Reset auto-commit mode
                    conn.close();
                }
            } catch (SQLException e) {
                System.err.println("Error closing connection: " + e.getMessage());
            }
        }
    }

    public static void cancelReservation(int reservationId) {
        Connection conn = null;
        try {
            conn = DatabaseManager.getConnection();
            conn.setAutoCommit(false); // Start transaction

            String getWorkspaceId = "SELECT workspace_id FROM reservations WHERE reservation_id = ? FOR UPDATE";
            String deleteSql = "DELETE FROM reservations WHERE reservation_id = ?";

            try (PreparedStatement getIdStmt = conn.prepareStatement(getWorkspaceId);
                    PreparedStatement deleteStmt = conn.prepareStatement(deleteSql)) {

                getIdStmt.setInt(1, reservationId);
                ResultSet rs = getIdStmt.executeQuery();

                if (rs.next()) {
                    int workspaceId = rs.getInt("workspace_id");

                    deleteStmt.setInt(1, reservationId);
                    deleteStmt.executeUpdate();

                    WorkspaceDAO.updateAvailability(conn, workspaceId, true);

                    conn.commit();
                } else {
                    conn.rollback();
                    throw new SQLException("Reservation not found");
                }
            }
        } catch (SQLException e) {
            try {
                if (conn != null)
                    conn.rollback();
            } catch (SQLException ex) {
                System.err.println("Error during rollback: " + ex.getMessage());
            }
            System.err.println("Error canceling reservation: " + e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.setAutoCommit(true); // Reset auto-commit
                    conn.close();
                }
            } catch (SQLException e) {
                System.err.println("Error closing connection: " + e.getMessage());
            }
        }
    }

}