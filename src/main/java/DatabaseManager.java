import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {
    private static final String URL = "jdbc:postgresql://localhost:5432/coworking_space";
    private static final String USER = "postgres";
    private static final String PASSWORD = "1541"; // this is my password, please put your owns

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void initializeDatabase() {
        String createWorkspaces = """
                CREATE TABLE IF NOT EXISTS workspaces (
                    id SERIAL PRIMARY KEY,
                    type VARCHAR(50) NOT NULL,
                    price DECIMAL(10,2) NOT NULL,
                    available BOOLEAN DEFAULT TRUE
                )""";

        String createReservations = """
                CREATE TABLE IF NOT EXISTS reservations (
                    reservation_id SERIAL PRIMARY KEY,
                    workspace_id INTEGER NOT NULL REFERENCES workspaces(id),
                    customer_name VARCHAR(100) NOT NULL,
                    date DATE NOT NULL,
                    start_time TIME NOT NULL,
                    end_time TIME NOT NULL
                )""";

        try (Connection conn = getConnection();
                var stmt = conn.createStatement()) {
            stmt.execute(createWorkspaces);
            stmt.execute(createReservations);
        } catch (SQLException e) {
            System.err.println("Database initialization failed: " + e.getMessage());
        }
    }
}