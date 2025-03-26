import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ReservationTest {

    @Test
    void testReservationCreation() {
        Reservation reservation = new Reservation(1, 1, "John Doe", "2023-10-01", "09:00", "12:00");
        assertEquals(1, reservation.getReservationId());
        assertEquals(1, reservation.getWorkspaceId());
        assertEquals("John Doe", reservation.getCustomerName());
        assertEquals("2023-10-01", reservation.getDate());
        assertEquals("09:00", reservation.getStartTime());
        assertEquals("12:00", reservation.getEndTime());
    }

    @Test
    void testReservationToString() {
        Reservation reservation = new Reservation(1, 1, "John Doe", "2023-10-01", "09:00", "12:00");
        String expected = "Reservation ID: 1, Workspace ID: 1, Customer: John Doe, Date: 2023-10-01, Time: 09:00 - 12:00";
        assertEquals(expected, reservation.toString());
    }
}