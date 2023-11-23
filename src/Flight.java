package src;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Date;

public class Flight {
    // HashMap of seats, key is seat number, value is Seat object
    HashMap<String, Seat> seats;
    Aircraft aircraft;
    String flightID;
    // ArrayList of crew members
    ArrayList<CrewMember> crew;
    // Departure and arrival times are in military time
    int departureTime;
    int arrivalTime;
    String departureLocation;
    String arrivalLocation;
    // Departure and arrival dates are in the format YYYY-MM-DD
    Date departureDate;
    Date arrivalDate;

    public Flight(Aircraft aircraft, String flightID, int departureTime, int arrivalTime, String departureLocation,
                  String arrivalLocation, Date departureDate, Date arrivalDate) {
        this.aircraft = aircraft;
        this.flightID = flightID;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.departureLocation = departureLocation;
        this.arrivalLocation = arrivalLocation;
        this.departureDate = departureDate;
        this.arrivalDate = arrivalDate;
        this.seats = new HashMap<String,Seat>();
        // Create seats for the flight
        for (int i = 0; i < aircraft.numSeats; i++) {
            Seat temp = new Seat(Integer.toString(i), "economy", 100);
            this.seats.put(temp.getSeatNumber(), temp);
        }
        this.crew = new ArrayList<CrewMember>();
    }

    /* Methods */
    public void addCrewMember(CrewMember crewMember) {
        this.crew.add(crewMember);
    }

    public void removeCrewMember(CrewMember crewMember) {
        this.crew.remove(crewMember);
    }

    public void bookSeat(Seat seat, User username) {
        if (seat.getIsAvailable() && seats.containsKey(seat.getSeatNumber())) {
            seat.setPassenger(username);
        }
        if (!seats.containsKey(seat.getSeatNumber())) {
            System.out.println("Seat does not exist");
        }
    }

    // This method is used to cancel a seat
    public void cancelSeat(String username) {
        for (Seat seat : seats.values()) {
            if (seat.getPassenger().getUsername().equals(username)) {
                seat.removePassenger();
            }
        }
    }





}
