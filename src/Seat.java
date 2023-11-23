package src;

public class Seat {
    final String SEATNUMBER;
    final String CLASS;
    boolean isAvailable;
    int price;
    User passenger;

    public Seat(String seatNumber, String seatClass, int price) {
        this.SEATNUMBER = seatNumber;
        this.CLASS = seatClass;
        this.price = price;
        this.isAvailable = true;
        this.passenger = null;
    }

    /* Getters and Setters */
    public String getSeatNumber() {
        return SEATNUMBER;
    }
    public String getSeatClass() {
        return CLASS;
    }
    public boolean getIsAvailable() {
        return isAvailable;
    }
    public int getPrice() {
        return price;
    }
    public User getPassenger() {
        return passenger;
    }

    /* Methods */
    public void bookSeat() {
        this.isAvailable = false;
    }
    public void cancelSeat() {
        this.isAvailable = true;
    }

    public void setPassenger(User passenger) {
        this.passenger = passenger;
        bookSeat();
    }

    public void removePassenger() {
        this.passenger = null;
        cancelSeat();
    }
}
