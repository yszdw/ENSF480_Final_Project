package src;

/**
 * Represents an admin user.
 * Inherits from the User class.
 */
public class Admin extends User {
    /**
     * Constructs an Admin object with the specified user ID, username, email, and
     * address.
     * 
     * @param userID   the ID of the admin user
     * @param username the username of the admin user
     * @param email    the email of the admin user
     * @param address  the address of the admin user
     */
    public Admin(int userID, String username, String email, String address) {
        super(userID, username, email, address);
    }
}
