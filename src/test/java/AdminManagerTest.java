import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

public class AdminManagerTest {
    private AdminManager adminManager;
    private List<Workspace> workspaces;
    private List<Reservation> reservations;

    @BeforeEach
    void setUp() {
        workspaces = new ArrayList<>();
        workspaces.add(new Workspace(1, "Desk", 50.0, true));
        reservations = new ArrayList<>();
        adminManager = new AdminManager(workspaces, reservations);
        DatabaseManager.initializeDatabase();
        WorkspaceDAO.addWorkspace(new Workspace(99, "Test Desk", 100.0, true));
    }

    @Test
    void addWorkspace_shouldAddNewWorkspace() {
        Scanner scanner = new Scanner("99\nTest Desk\n100.0");
        int initialSize = WorkspaceDAO.getAllWorkspaces().size();
        adminManager.addWorkspace(scanner);
        assertEquals(initialSize + 1, WorkspaceDAO.getAllWorkspaces().size());
    }

    @Test
    void removeWorkspace_shouldDeleteWhenNoReservations() {
        WorkspaceDAO.addWorkspace(new Workspace(100, "Temp Desk", 75.0, true));
        Scanner scanner = new Scanner("100");
        adminManager.removeWorkspace(scanner);
        assertFalse(WorkspaceDAO.getAllWorkspaces().stream()
                .anyMatch(w -> w.getId() == 100));
    }
}