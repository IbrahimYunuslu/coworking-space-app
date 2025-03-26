import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ReservationTest {
    @Test
    void reservationCreation_shouldStoreAllFields() {
        Reservation reservation = new Reservation(
                1, 101, "John Doe", "2023-12-25", "09:00", "11:00");

        assertEquals(101, reservation.getWorkspaceId());
        assertEquals("John Doe", reservation.getCustomerName());
        assertEquals("2023-12-25", reservation.getDate());
    }

    @Test
    void toString_shouldFormatCorrectly() {
        Reservation reservation = new Reservation(
                1, 101, "John Doe", "2023-12-25", "09:00", "11:00");

        String expected = "Reservation ID: 1, Workspace ID: 101, " +
                "Customer: John Doe, Date: 2023-12-25, Time: 09:00 - 11:00";
        assertEquals(expected, reservation.toString());
    }
}