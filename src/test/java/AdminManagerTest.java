import org.junit.jupiter.api.*;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

class AdminManagerTest {
    private List<Workspace> workspaces;
    private List<Reservation> reservations;
    private AdminManager adminManager;

    @BeforeEach
    void setUp() {
        workspaces = new ArrayList<>();
        reservations = new ArrayList<>();
        adminManager = new AdminManager(workspaces, reservations);
    }

    @Test
    void addWorkspace_whenDuplicateId_shouldThrowException() {
        workspaces.add(new Workspace(1, "Desk", 50.0, true));

        assertThrows(CustomException.class, () -> {
            adminManager.addWorkspace(new Scanner("1\nNew Desk\n60.0"));
        });
    }

    @Test
    void removeWorkspace_whenNotExists_shouldThrowException() {
        assertThrows(CustomException.class, () -> {
            adminManager.removeWorkspace(new Scanner("999"));
        });
    }
}