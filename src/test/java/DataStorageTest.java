import org.junit.jupiter.api.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class DataStorageTest {
    // Use test-specific file names to avoid interfering with real data
    private static final String TEST_WORKSPACE_FILE = "data/test_workspaces.dat";
    private static final String TEST_STATE_FILE = "data/test_state.dat";

    @BeforeEach
    void setUp() {
        DataStorage.setWorkspaceFileForTesting(TEST_WORKSPACE_FILE);
        DataStorage.setStateFileForTesting(TEST_STATE_FILE);
    }

    @AfterEach
    void tearDown() {
        new File(TEST_WORKSPACE_FILE).delete();
        new File(TEST_STATE_FILE).delete();
    }

    @Test
    void testSaveAndLoadWorkspaces() {
        List<Workspace> workspaces = new ArrayList<>();
        workspaces.add(new Workspace(1, "Desk", 50.0, true));
        workspaces.add(new Workspace(2, "Meeting Room", 100.0, false));

        DataStorage.saveWorkspaces(workspaces);
        List<Workspace> loadedWorkspaces = DataStorage.loadWorkspaces();

        assertEquals(2, loadedWorkspaces.size());
        assertEquals(1, loadedWorkspaces.get(0).getId());
        assertEquals("Desk", loadedWorkspaces.get(0).getType());
        assertEquals(50.0, loadedWorkspaces.get(0).getPrice());
        assertTrue(loadedWorkspaces.get(0).isAvailable());
    }

    @Test
    void testSaveAndLoadState() {
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(new Reservation(1, 1, "John Doe", "2023-10-01", "09:00", "12:00"));
        int reservationCounter = 2;

        DataStorage.saveState(reservations, reservationCounter);
        State loadedState = DataStorage.loadState();

        assertEquals(1, loadedState.getReservations().size());
        assertEquals(2, loadedState.getReservationCounter());
    }

    @Test
    void loadWorkspaces_whenFileNotExists_shouldReturnEmptyList() {
        new File(TEST_WORKSPACE_FILE).delete();

        List<Workspace> result = DataStorage.loadWorkspaces();
        assertTrue(result.isEmpty(), "Should return empty list when file doesn't exist");
    }

    @Test
    void loadState_whenFileNotExists_shouldReturnDefaultState() {
        new File(TEST_STATE_FILE).delete();

        State result = DataStorage.loadState();
        assertTrue(result.getReservations().isEmpty(), "Reservations should be empty");
        assertEquals(1, result.getReservationCounter(), "Should return default counter value");
    }

    @Test
    void loadWorkspaces_whenFileCorrupted_shouldReturnEmptyList() throws IOException {
        Files.write(Path.of(TEST_WORKSPACE_FILE), "corrupted data".getBytes());

        List<Workspace> result = DataStorage.loadWorkspaces();
        assertTrue(result.isEmpty(), "Should return empty list for corrupted file");
    }

    @Test
    void saveWorkspaces_whenCannotWrite_shouldNotThrow() {
        new File(TEST_WORKSPACE_FILE).getParentFile().setWritable(false);

        List<Workspace> workspaces = new ArrayList<>();
        workspaces.add(new Workspace(1, "Test", 10.0, true));

        assertDoesNotThrow(() -> DataStorage.saveWorkspaces(workspaces),
                "Should handle write failures gracefully");

        new File(TEST_WORKSPACE_FILE).getParentFile().setWritable(true);
    }
}