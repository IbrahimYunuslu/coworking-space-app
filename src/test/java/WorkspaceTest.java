import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class WorkspaceTest {
    @Test
    void workspaceCreation_shouldStoreAllFields() {
        Workspace workspace = new Workspace(1, "Private Office", 100.0, true);

        assertEquals(1, workspace.getId());
        assertEquals("Private Office", workspace.getType());
        assertTrue(workspace.isAvailable());
    }

    @Test
    void setAvailable_shouldChangeAvailability() {
        Workspace workspace = new Workspace(1, "Desk", 50.0, true);
        workspace.setAvailable(false);
        assertFalse(workspace.isAvailable());
    }
}