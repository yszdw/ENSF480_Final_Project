package src;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Date;

public class Flight {
    // HashMap of seats, key is seat number, value is Seat object
    HashMap<String, Seat> seats;
    Aircraft aircraft;
    // Assuming flight ID is in the format AA###, where A is a letter and # is a number
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
        this.seats = new HashMap<>();
        // Create seats for the flight
        for (int i = 0; i < aircraft.numEconomySeats; i++) {
            Seat temp = new Seat(Integer.toString(i), "economy", 100);
            this.seats.put(temp.getSeatNumber(), temp);
        }
        for (int i = 0; i < aircraft.numComfortSeats; i++) {
            Seat temp = new Seat(Integer.toString(i), "Comfort", 145);
            this.seats.put(temp.getSeatNumber(), temp);
        }
        for (int i = 0; i < aircraft.numBusinessSeats; i++) {
            Seat temp = new Seat(Integer.toString(i), "business", 250);
            this.seats.put(temp.getSeatNumber(), temp);
        }

        this.crew = new ArrayList<CrewMember>();
    }

    /* Methods */

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

    /* Getters and Setters */
    public void setAircraft(Aircraft aircraft) {
        this.aircraft = aircraft;
    }
    public void setFlightID(String flightID) {
        this.flightID = flightID;
    }
    public void setDepartureTime(int departureTime) {
        this.departureTime = departureTime;
    }
    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }
    public void setDepartureLocation(String departureLocation) {
        this.departureLocation = departureLocation;
    }
    public void setArrivalLocation(String arrivalLocation) {
        this.arrivalLocation = arrivalLocation;
    }
    public void setDepartureDate(Date departureDate) {
        this.departureDate = departureDate;
    }
    public void setArrivalDate(Date arrivalDate) {
        this.arrivalDate = arrivalDate;
    }
    public Aircraft getAircraft() {
        return aircraft;
    }
    public String getFlightID() {
        return flightID;
    }
    public int getDepartureTime() {
        return departureTime;
    }
    public int getArrivalTime() {
        return arrivalTime;
    }
    public String getDepartureLocation() {
        return departureLocation;
    }
    public String getArrivalLocation() {
        return arrivalLocation;
    }
    public Date getDepartureDate() {
        return departureDate;
    }
    public Date getArrivalDate() {
        return arrivalDate;
    }
    public HashMap<String, Seat> getSeats() {
        return seats;
    }
    public ArrayList<CrewMember> getCrew() {
        return crew;
    }

}
