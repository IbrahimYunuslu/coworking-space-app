import java.util.List;

public class State {
    private List<Reservation> reservations;
    private int reservationCounter;

    public State(List<Reservation> reservations, int reservationCounter) {
        this.reservations = reservations;
        this.reservationCounter = reservationCounter;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public int getReservationCounter() {
        return reservationCounter;
    }
}