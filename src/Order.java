/*
Using this table as reference create a basic order class:
CREATE TABLE `orders`  (
  `OrderID` int NOT NULL AUTO_INCREMENT,
  `Username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `FlightID` int NOT NULL,
  `AircraftModel` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `DepartureLocation` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `ArrivalLocation` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `DepartureTime` time NOT NULL,
  `ArrivalTime` time NOT NULL,
  `Class` enum('Economy','Business','Comfort') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `SeatNumber` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `Insurance` tinyint(1) NOT NULL,
  `TotalPrice` decimal(10, 2) NOT NULL,
  PRIMARY KEY (`OrderID`) USING BTREE,
  INDEX `FlightID`(`FlightID` ASC) USING BTREE,
  CONSTRAINT `orders_ibfk_1` FOREIGN KEY (`FlightID`) REFERENCES `flights` (`FlightID`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;
 */

public class Order {
    private int orderID;
    private String username;
    private int flightID;
    private Aircraft aircraftModel;
    private String departureLocation;
    private String arrivalLocation;
    private String departureTime;
    private String arrivalTime;
    private String seatClass;
    private String seatNumber;
    private boolean insurance;
    private double totalPrice;

    public Order(int orderID, String username, int flightID, Aircraft aircraftModel, String departureLocation,
                 String arrivalLocation, String departureTime, String arrivalTime, String seatClass, String seatNumber,
                 boolean insurance, double totalPrice) {
        this.orderID = orderID;
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
    public Aircraft getAircraftModel() {
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
}