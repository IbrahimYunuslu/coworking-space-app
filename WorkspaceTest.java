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
}