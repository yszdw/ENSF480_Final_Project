package src;

public class CreditCard {
    final long CARDNUMBER;
    final String CARDHOLDERNAME;
    final int EXPIRY;
    final int CVV;

    public CreditCard(long cardNumber, String cardHolderName, int expiry, int cvv) {
        this.CARDNUMBER = cardNumber;
        this.CARDHOLDERNAME = cardHolderName;
        this.EXPIRY = expiry;
        this.CVV = cvv;
    }

    /* Getters and Setters */
    public long getCardNumber() {
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
