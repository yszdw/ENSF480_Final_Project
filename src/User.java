package src;

public class User {
    String username;
    String email;

    boolean hasInsurance;

    public User(String username, String email) {
        this.username = username;
        this.email = email;
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
