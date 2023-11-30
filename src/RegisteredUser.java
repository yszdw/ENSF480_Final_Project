package src;

public class RegisteredUser extends User {

    CreditCard creditCard;

    int companionTickets;

    public RegisteredUser(int userID, String username, String email, String address, CreditCard creditCard,
            int companionTickets) {
        super(userID, username, email, address);
        this.creditCard = creditCard;
        this.companionTickets = companionTickets;
    }

    /* Getters and Setters */
    public void setCreditCard(CreditCard creditCard) {
        this.creditCard = creditCard;
    }

    public CreditCard getCreditCard() {
        return creditCard;
    }

    public void setCompanionTickets(int companionTickets) {
        this.companionTickets = companionTickets;
    }

    public int getCompanionTickets() {
        return companionTickets;
    }

}
