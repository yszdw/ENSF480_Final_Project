package src;
public class CreditCard {
    final String CARDNUMBER;
    final String CARDHOLDERNAME;
    final int EXPIRY;
    final int CVV;

    public CreditCard(String cardNumber, String cardHolderName, int expiry, int cvv) {
        this.CARDNUMBER = cardNumber;
        this.CARDHOLDERNAME = cardHolderName;
        this.EXPIRY = expiry;
        this.CVV = cvv;
    }

    /* Getters and Setters */
    public String getCardNumber() {
        return CARDNUMBER;
    }

    public int getExpiryDate() {
        return EXPIRY;
    }

    public int getCVV() {
        return CVV;
    }
}
