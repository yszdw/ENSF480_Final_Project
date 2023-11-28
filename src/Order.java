package src;

public class Order {
    private int orderID;
    private String email;
    private String username;
    private int flightID;
    private String aircraftModel;
    private String departureLocation;
    private String arrivalLocation;
    private String departureTime;
    private String arrivalTime;
    private String seatClass;
    private String seatNumber;
    private boolean insurance;
    private double totalPrice;

    public Order(int orderID, String email, String username, int flightID, String aircraftModel,
            String departureLocation,
            String arrivalLocation, String departureTime, String arrivalTime, String seatClass, String seatNumber,
            boolean insurance, double totalPrice) {
        this.orderID = orderID;
        this.email = email;
        this.username = username;
        this.flightID = flightID;
        this.aircraftModel = aircraftModel;
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

    public String getAircraftModel() {
        return aircraftModel;
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