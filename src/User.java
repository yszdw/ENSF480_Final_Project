package src;

public class User {
    String username;
    String email;
    String address;
    String creditCard;
    boolean isMember;
    boolean hasInsurance;
    String userType;

    public User(String username, String email, String address, String creditCard, String userType) {
        this.username = username;
        this.email = email;
        this.address = address;
        this.creditCard = creditCard;
        this.userType = userType;
        this.isMember = false;
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

    /* Methods */
}
