import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class DataStorageTest {

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
}