

-- Users Table
CREATE TABLE IF NOT EXISTS Users (
    UserID INT AUTO_INCREMENT PRIMARY KEY,
    Name VARCHAR(255) NOT NULL,
    Address VARCHAR(255),
    Email VARCHAR(255) UNIQUE NOT NULL,
    UserType ENUM('passenger', 'tourism_agent', 'airline_agent', 'admin') NOT NULL,
    MembershipStatus BOOLEAN DEFAULT FALSE,
    CreditCardInfo VARCHAR(255)
);

-- Flights Table
CREATE TABLE IF NOT EXISTS Flights (
    FlightID INT AUTO_INCREMENT PRIMARY KEY,
    Origin VARCHAR(255) NOT NULL,
    Destination VARCHAR(255) NOT NULL,
    DepartureDateTime DATETIME NOT NULL,
    ArrivalDateTime DATETIME NOT NULL
);

-- Seats Table
CREATE TABLE IF NOT EXISTS Seats (
    SeatID INT AUTO_INCREMENT PRIMARY KEY,
    FlightID INT NOT NULL,
    SeatNumber VARCHAR(10) NOT NULL,
    SeatType ENUM('ordinary', 'comfort', 'business_class') NOT NULL,
    Price DECIMAL(10, 2) NOT NULL,
    IsBooked BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (FlightID) REFERENCES Flights(FlightID)
);

-- Bookings Table
CREATE TABLE IF NOT EXISTS Bookings (
    BookingID INT AUTO_INCREMENT PRIMARY KEY,
    UserID INT NOT NULL,
    FlightID INT NOT NULL,
    SeatID INT NOT NULL,
    CancellationInsurance BOOLEAN DEFAULT FALSE,
    BookingDateTime DATETIME NOT NULL,
    FOREIGN KEY (UserID) REFERENCES Users(UserID),
    FOREIGN KEY (FlightID) REFERENCES Flights(FlightID),
    FOREIGN KEY (SeatID) REFERENCES Seats(SeatID)
);

-- Payments Table
CREATE TABLE IF NOT EXISTS Payments (
    PaymentID INT AUTO_INCREMENT PRIMARY KEY,
    BookingID INT NOT NULL,
    Amount DECIMAL(10, 2) NOT NULL,
    PaymentDateTime DATETIME NOT NULL,
    CreditCardUsed VARCHAR(255) NOT NULL,
    FOREIGN KEY (BookingID) REFERENCES Bookings(BookingID)
);

-- Crews Table
CREATE TABLE IF NOT EXISTS Crews (
    CrewID INT AUTO_INCREMENT PRIMARY KEY,
    Name VARCHAR(255) NOT NULL,
    Position ENUM('pilot', 'flight_attendant', 'engineer') NOT NULL
);

-- Aircrafts Table
CREATE TABLE IF NOT EXISTS Aircrafts (
    AircraftID INT AUTO_INCREMENT PRIMARY KEY,
    Model VARCHAR(255) NOT NULL,
    Capacity INT NOT NULL
);

-- Destinations Table
CREATE TABLE IF NOT EXISTS Destinations (
    DestinationID INT AUTO_INCREMENT PRIMARY KEY,
    City VARCHAR(255) NOT NULL,
    Country VARCHAR(255) NOT NULL
);

-- Promotions Table
CREATE TABLE IF NOT EXISTS Promotions (
    PromotionID INT AUTO_INCREMENT PRIMARY KEY,
    UserID INT NOT NULL,
    Description TEXT NOT NULL,
    ValidUntil DATETIME NOT NULL,
    FOREIGN KEY (UserID) REFERENCES Users(UserID)
);

