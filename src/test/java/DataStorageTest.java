import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class DataStorageTest {
    @BeforeEach
    void setUp() {
        DatabaseManager.initializeDatabase();
        WorkspaceDAO.addWorkspace(new Workspace(1, "Test Desk", 50.0, true));
    }

    @Test
    void loadWorkspaces_shouldReturnWorkspacesFromDatabase() {
        List<Workspace> workspaces = DataStorage.loadWorkspaces();
        assertFalse(workspaces.isEmpty());
        assertEquals("Test Desk", workspaces.get(0).getType());
    }

    @Test
    void saveWorkspace_shouldPersistToDatabase() {
        Workspace newWorkspace = new Workspace(2, "New Desk", 75.0, true);
        DataStorage.saveWorkspace(newWorkspace);

        List<Workspace> workspaces = DataStorage.loadWorkspaces();
        assertTrue(workspaces.stream()
                .anyMatch(w -> w.getId() == 2));
    }
}