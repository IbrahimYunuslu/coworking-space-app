import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WorkspaceDAO {
    public static List<Workspace> getAllWorkspaces() {
        List<Workspace> workspaces = new ArrayList<>();
        String sql = "SELECT * FROM workspaces";

        try (Connection conn = DatabaseManager.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                workspaces.add(new Workspace(
                        rs.getInt("id"),
                        rs.getString("type"),
                        rs.getDouble("price"),
                        rs.getBoolean("available")));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching workspaces: " + e.getMessage());
        }
        return workspaces;
    }

    public static void addWorkspace(Workspace workspace) {
        String sql = "INSERT INTO workspaces (type, price, available) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseManager.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, workspace.getType());
            pstmt.setDouble(2, workspace.getPrice());
            pstmt.setBoolean(3, workspace.isAvailable());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error adding workspace: " + e.getMessage());
        }
    }

    public static void updateAvailability(Connection conn, int id, boolean available) throws SQLException {
        String sql = "UPDATE workspaces SET available = ? WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setBoolean(1, available);
            pstmt.setInt(2, id);
            pstmt.executeUpdate();
        }
    }

    public static boolean deleteWorkspace(int id) {
        String sql = "DELETE FROM workspaces WHERE id = ?";

        try (Connection conn = DatabaseManager.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting workspace: " + e.getMessage());
            return false;
        }
    }

}