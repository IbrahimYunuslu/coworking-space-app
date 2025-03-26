import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

public class UserManagerTest {
    private UserManager userManager;
    private List<Workspace> workspaces;
    private List<Reservation> reservations;

    @BeforeEach
    void setUp() {
        workspaces = new ArrayList<>();
        workspaces.add(new Workspace(1, "Desk", 50.0, true));
        reservations = new ArrayList<>();
        userManager = new UserManager(workspaces, reservations, 1);
        DatabaseManager.initializeDatabase();
        WorkspaceDAO.addWorkspace(new Workspace(1, "Test Desk", 50.0, true));
    }

    @Test
    void makeReservation_shouldCreateNewReservation() {
        int initialCount = ReservationDAO.getAllReservations().size();
        Scanner scanner = new Scanner("1\nTest User\n2023-12-25\n09:00\n11:00");
        userManager.makeReservation(scanner);
        assertEquals(initialCount + 1, ReservationDAO.getAllReservations().size());
    }
}