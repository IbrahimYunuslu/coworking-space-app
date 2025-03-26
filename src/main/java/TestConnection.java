import java.sql.Connection;

public class TestConnection {
    public static void main(String[] args) {
        try {
            Connection conn = DatabaseManager.getConnection();
            System.out.println("Connected to PostgreSQL successfully!");
            conn.close();
        } catch (Exception e) {
            System.out.println("Connection failed: " + e.getMessage());
        }
    }
}