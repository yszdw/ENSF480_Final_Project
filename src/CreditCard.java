package src;

public class CreditCard {
    final int CARDNUMBER;
    final String CARDHOLDERNAME;
    final int EXPIRY;
    final int CVV;

    public CreditCard(int cardNumber, String cardHolderName, int expiry, int cvv) {
        this.CARDNUMBER = cardNumber;
        this.CARDHOLDERNAME = cardHolderName;
        this.EXPIRY = expiry;
        this.CVV = cvv;
    }

    /* Getters and Setters */
    public int getCardNumber() {
        return CARDNUMBER;
    }
    public String getCardHolderName() {
        return CARDHOLDERNAME;
    }
    public int getExpiryDate() {
        return EXPIRY;
    }
    public int getCVV() {
        return CVV;
    }
}
