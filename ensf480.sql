DROP DATABASE IF EXISTS ENSF480;
CREATE DATABASE ENSF480;
USE ENSF480;
-- Users Table
CREATE TABLE IF NOT EXISTS Users (
    UserID INT AUTO_INCREMENT PRIMARY KEY,
    Name VARCHAR(255) NOT NULL,
    Address VARCHAR(255),
    Email VARCHAR(255) UNIQUE NOT NULL,
    UserType ENUM(
        'passenger',
        'airline_agent',
        'admin'
    ) NOT NULL,
    MembershipStatus BOOLEAN DEFAULT FALSE,
    CreditCardInfo VARCHAR(255)
);
-- Inserting Data
INSERT INTO Users (
        Name,
        Address,
        Email,
        UserType,
        MembershipStatus,
        CreditCardInfo
    )
VALUES (
        'John Doe',
        '1234 5th Ave, Calgary, AB',
        'johnDoe@gmail.com',
        'passenger',
        FALSE,
        '1234-5678-9012-3456'
    ),
    (
        'Jane Doe',
        '1234 5th Ave, Calgary, AB',
        'janeDoe@gmail.com',
        'passenger',
        FALSE,
        '1234-5678-9012-3456'
    );
-- Flights Table
CREATE TABLE IF NOT EXISTS Flights (
    FlightID INT AUTO_INCREMENT PRIMARY KEY,
    Origin VARCHAR(255) NOT NULL,
    Destination VARCHAR(255) NOT NULL,
    DepartureDateTime DATETIME NOT NULL,
    ArrivalDateTime DATETIME NOT NULL,
    AircraftID INT NOT NULL
);
INSERT INTO Flights (
        Origin,
        Destination,
        DepartureDateTime,
        ArrivalDateTime,
        AircraftID
    )
VALUES (
        'Calgary',
        'Toronto',
        '2024-04-01 12:00:00',
        '2024-04-01 16:00:00',
        1
    ),
    (
        'Calgary',
        'Vancouver',
        '2024-05-01 1:00:00',
        '2024-05-01 2:00:00',
        2
    ),
    (
        'Vancouver',
        'Toronto',
        '2024-06-01 16:00:00',
        '2024-06-01 20:00:00',
        1
    ),
    (
        'Vancouver',
        'Calgary',
        '2024-07-01 11:00:00',
        '2024-07-01 15:00:00',
        4
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
    FOREIGN KEY (FlightID) REFERENCES Flights(FlightID)
    
);

INSERT INTO Bookings (
        UserID,
        FlightID,
        SeatID,
        CancellationInsurance,
        BookingDateTime
    )
VALUES (1, 1, 1, TRUE, '2024-03-01 12:00:00'),
    (2, 2, 2, FALSE, '2024-04-01 12:00:00'),
    (1, 3, 3, FALSE, '2024-05-01 12:00:00'),
    (2, 1, 1, TRUE, '2024-03-03 12:00:00');
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
    Ordinary INT NOT NULL,
    Comfort INT NOT NULL,
    Business INT NOT NULL
);
INSERT INTO Aircrafts (
        Model,
        Ordinary,
        Comfort,
        Business
    )
VALUES (
        'Boeing 737',
        50,
        20,
        10
    ),
    (
        'Boeing 737',
        50,
        20,
        10
    ),
    (
        'Boeing 717',
        40,
        15,
        5
    ),
    (
        'Boeing 717',
        40,
        15,
        5
    ),
    (
        'Boeing 757',
        60,
        30,
        10
    ),
    (
        'Airbus A320',
        50,
        30,
        10
    );
-- Promotions Table
CREATE TABLE IF NOT EXISTS Promotions (
    PromotionID INT AUTO_INCREMENT PRIMARY KEY,
    UserID INT NOT NULL,
    Description TEXT NOT NULL,
    ValidUntil DATETIME NOT NULL,
    FOREIGN KEY (UserID) REFERENCES Users(UserID)
);