package src;

import java.util.HashMap;
import java.util.Map;

public class Agent extends User {
    // Only additional thing agent can do is view list of passengers in a flight,
    // otherwise they are the same as a registered user
    public Agent(int userID, String username, String email, String address) {
        super(userID, username, email, address);
    }

    /* Methods */

    /*
    * This method is used to return a list of all passengers in a flight
    * @param flight The flight to get passengers from
    * @return A HashMap of passenger email, seat number
    */
    public HashMap<String, String> getPassengers(Flight flight) {
        HashMap<String, String> passengers = new HashMap<String, String>();
        for (Map.Entry<String, Seat> entry : flight.getSeats().entrySet()) {
            // if seat is taken
            if (entry.getValue().getPassenger() != null) {
                // add passenger email and seat number to hashmap
                passengers.put(entry.getValue().getPassenger().getEmail(), entry.getKey());
            }
        }
        return passengers;
    }
}
