package src;

public class User {
    int userID;
    String username;
    String email;
    String address;
    String creditCard;
    boolean isMember;
    boolean hasInsurance;
    String userType;

    public User(int userID, String username, String email, String address, String creditCard, String userType) {
        this.userID = userID;
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

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCreditCard(String creditCard) {
        this.creditCard = creditCard;
    }

    public void setIsMember(boolean isMember) {
        this.isMember = isMember;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getAddress() {
        return address;
    }

    public String getCreditCard() {
        return creditCard;
    }

    public boolean getIsMember() {
        return isMember;
    }

    public String getUserType() {
        return userType;
    }

    public int getUserID() {
        return userID;
    }

    /* Methods */
}
