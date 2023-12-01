package src;

public class Order {
    private final int orderID;
    private final String email;
    private final String username;
    private final int flightID;
    private final String departureLocation;
    private final String arrivalLocation;
    private final String departureTime;
    private final String arrivalTime;
    private final String seatClass;
    private final String seatNumber;
    private final boolean insurance;
    private final double totalPrice;

    public Order(int orderID, String email, String username, int flightID, String departureLocation,
            String arrivalLocation, String departureTime, String arrivalTime, String seatClass, String seatNumber,
            boolean insurance, double totalPrice) {
        this.orderID = orderID;
        this.email = email;
        this.username = username;
        this.flightID = flightID;
        this.departureLocation = departureLocation;
        this.arrivalLocation = arrivalLocation;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.seatClass = seatClass;
        this.seatNumber = seatNumber;
        this.insurance = insurance;
        this.totalPrice = totalPrice;
    }

    /* Getters and Setters */
    public int getOrderID() {
        return orderID;
    }

    // provide all the getters and setters for the fields above
    public String getUsername() {
        return username;
    }

    public int getFlightID() {
        return flightID;
    }

    public String getDepartureLocation() {
        return departureLocation;
    }

    public String getArrivalLocation() {
        return arrivalLocation;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public String getSeatClass() {
        return seatClass;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public boolean getInsurance() {
        return insurance;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public String getEmail() {
        return email;
    }
}