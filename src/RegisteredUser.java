package src;

public class RegisteredUser extends User {

    CreditCard creditCard;


    public RegisteredUser(int userID, String username, String email, String address, CreditCard creditCard) {
        super(userID, username, email, address);
        this.creditCard = creditCard;
    }

    /* Getters and Setters */
    public void setCreditCard(CreditCard creditCard) {
        this.creditCard = creditCard;
    }
    public CreditCard getCreditCard() {
        return creditCard;
    }


}
