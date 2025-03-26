import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

public class WorkspaceDAOTest {
    @BeforeEach
    void setUp() {
        DatabaseManager.initializeDatabase();
    }

    @Test
    void addWorkspace_shouldPersistToDatabase() {
        Workspace workspace = new Workspace(99, "Test Desk", 75.0, true);
        WorkspaceDAO.addWorkspace(workspace);
        List<Workspace> workspaces = WorkspaceDAO.getAllWorkspaces();
        assertTrue(workspaces.stream().anyMatch(w -> w.getId() == 99));
    }

    @Test
    void deleteWorkspace_shouldRemoveFromDatabase() {
        WorkspaceDAO.addWorkspace(new Workspace(100, "Temp Desk", 50.0, true));
        boolean result = WorkspaceDAO.deleteWorkspace(100);
        assertTrue(result);
        assertFalse(WorkspaceDAO.getAllWorkspaces().stream()
                .anyMatch(w -> w.getId() == 100));
    }
}