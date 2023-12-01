package src;

public class Agent extends User {
    // Only additional thing agent can do is view list of passengers in a flight,
    // otherwise they are the same as a registered user
    public Agent(int userID, String username, String email, String address) {
        super(userID, username, email, address);
    }

}
