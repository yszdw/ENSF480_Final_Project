package src;

import java.time.LocalDateTime;
import java.util.*;

public class Booking {
    int bookingID;
    Flight flight;
    User user;
    int seats;
    boolean cancellationInsurance;
    LocalDateTime bookingDateTime;

    public Booking(int bookingID, Flight fight, User user, int seats, boolean cancel, LocalDateTime bookingDateTime) {
        this.bookingID = bookingID;
        this.flight = fight;
        this.user = user;
        this.seats = seats;
        this.cancellationInsurance = cancel;
        this.bookingDateTime = bookingDateTime;
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

    public int getBookedSeats() {
        return seats;
    }

    public void setBookedSeats(int seats) {
        this.seats = seats;
    }

    public boolean getCancellationInsurance() {
        return cancellationInsurance;
    }

    public void setCancellationInsurance(boolean cancellationInsurance) {
        this.cancellationInsurance = cancellationInsurance;
    }

    public LocalDateTime getBookingDateTime() {
        return bookingDateTime;
    }

    // /* Methods */
    // public void addSeat(Seat seat) {
    // seats.add(seat);
    // }

    // public void removeSeat(Seat seat) {
    // for (Seat s : seats) {
    // if (Objects.equals(s.getSeatNumber(), seat.getSeatNumber())) {
    // seats.remove(s);
    // return;
    // }
    // }
    // System.out.println("Seat not found in booking");
    // }
}
