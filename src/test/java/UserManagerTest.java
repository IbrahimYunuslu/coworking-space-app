import org.junit.jupiter.api.*;
import java.io.*;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

class UserManagerTest {
    private List<Workspace> workspaces;
    private List<Reservation> reservations;
    private UserManager userManager;

    @BeforeEach
    void setUp() {
        workspaces = new ArrayList<>();
        workspaces.add(new Workspace(1, "Desk", 50.0, true));
        reservations = new ArrayList<>();
        userManager = new UserManager(workspaces, reservations, 1);
    }

    @Test
    void makeReservation_whenWorkspaceNotExists_shouldThrowException() {
        String input = "999\nJohn\n2023-10-01\n09:00\n12:00";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        assertThrows(CustomException.class, () -> {
            userManager.makeReservation(new Scanner(System.in));
        });
    }

    @Test
    void makeReservation_whenWorkspaceUnavailable_shouldThrowException() {
        workspaces.get(0).setAvailable(false);
        String input = "1\nJohn\n2023-10-01\n09:00\n12:00";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        assertThrows(CustomException.class, () -> {
            userManager.makeReservation(new Scanner(System.in));
        });
    }

    @Test
    void viewMyReservations_whenNoReservations_shouldThrowException() {
        String input = "NonExistentUser";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        assertThrows(CustomException.class, () -> {
            userManager.viewMyReservations(new Scanner(System.in));
        });
    }
}