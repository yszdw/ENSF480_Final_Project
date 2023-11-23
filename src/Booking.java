package src;
import java.util.ArrayList;
import java.util.Objects;

public class Booking {
    int bookingID;
    Flight flight;
    User user;
    ArrayList<Seat> seats;

    public Booking(int bookingID, Flight fight, User user, ArrayList<Seat> seats) {
        this.bookingID = bookingID;
        this.flight = fight;
        this.user = user;
        this.seats = seats;
    }

    /* Getters and setters */
    public int getBookingID() {
        return bookingID;
    }
    public void setBookingID(int bookingID) {
        this.bookingID = bookingID;
    }
    public Flight getFlight() {
        return flight;
    }
    public void setFlight(Flight flight) {
        this.flight = flight;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

    /* Methods */
    public void addSeat(Seat seat) {
        seats.add(seat);
    }
    public void removeSeat(Seat seat) {
        for (Seat s : seats) {
            if (Objects.equals(s.getSeatNumber(), seat.getSeatNumber())) {
                seats.remove(s);
                return;
            }
        }
        System.out.println("Seat not found in booking");
    }
    public ArrayList<Seat> getBookedSeats() {
        return seats;
    }
}
