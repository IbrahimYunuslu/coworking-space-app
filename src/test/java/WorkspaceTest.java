import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class WorkspaceTest {

    @Test
    void testWorkspaceCreation() {
        Workspace workspace = new Workspace(1, "Desk", 50.0, true);
        assertEquals(1, workspace.getId());
        assertEquals("Desk", workspace.getType());
        assertEquals(50.0, workspace.getPrice());
        assertTrue(workspace.isAvailable());
    }

    @Test
    void testSetAvailable() {
        Workspace workspace = new Workspace(2, "Meeting Room", 100.0, true);
        workspace.setAvailable(false);
        assertFalse(workspace.isAvailable());
    }

    // Edge cases
    @Test
    void createWorkspace_withNegativePrice_shouldAccept() {
        Workspace workspace = new Workspace(3, "Private Office", -75.0, false);
        assertEquals(-75.0, workspace.getPrice());
    }

    @Test
    void createWorkspace_withEmptyType_shouldAccept() {
        Workspace workspace = new Workspace(4, "", 200.0, true);
        assertEquals("", workspace.getType());
    }
}