
public abstract class User {
    int userID;
    String username;
    String email;
    String address;
    boolean hasInsurance;

    public User(int userID, String username, String email, String address) {
        this.userID = userID;
        this.username = username;
        this.email = email;
        this.address = address;
        this.hasInsurance = false;
    }

    public User() {
        this.username = "";
        this.email = "";
    }
    /* Getters and Setters */

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setHasInsurance(boolean hasInsurance) {
        this.hasInsurance = hasInsurance;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public boolean getHasInsurance() {
        return hasInsurance;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public int getUserID() {
        return userID;
    }

    /* Methods */
}
