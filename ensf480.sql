/*
 Navicat Premium Data Transfer
 
 Source Server         : 111
 Source Server Type    : MySQL
 Source Server Version : 80035 (8.0.35)
 Source Host           : localhost:3306orders
 Source Schema         : ensf480
 
 Target Server Type    : MySQL
 Target Server Version : 80035 (8.0.35)
 File Encoding         : 65001
 
 Date: 26/11/2023 08:30:42
 */
DROP DATABASE IF EXISTS ENSF480;
CREATE DATABASE ENSF480;
USE ENSF480;
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;
DROP DATABASE IF EXISTS ENSF480;
CREATE DATABASE ENSF480;
USE ENSF480;
-- ----------------------------
-- Table structure for aircrafts
-- ----------------------------
DROP TABLE IF EXISTS `aircrafts`;
CREATE TABLE `aircrafts` (
  `AircraftID` int NOT NULL AUTO_INCREMENT,
  `Model` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `Ordinary` int NOT NULL,
  `Comfort` int NOT NULL,
  `Business` int NOT NULL,
  `EconomyPrice` decimal(10, 2) NOT NULL,
  `BusinessPrice` decimal(10, 2) NOT NULL,
  PRIMARY KEY (`AircraftID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;
-- ----------------------------
-- Records of aircrafts
-- ----------------------------
INSERT INTO `aircrafts`
VALUES (1, 'Boeing 737', 50, 20, 10, 100.00, 200.00);
INSERT INTO `aircrafts`
VALUES (2, 'Boeing 737', 50, 20, 10, 100.00, 200.00);
INSERT INTO `aircrafts`
VALUES (3, 'Boeing 717', 40, 15, 5, 100.00, 200.00);
INSERT INTO `aircrafts`
VALUES (4, 'Boeing 717', 40, 15, 5, 100.00, 200.00);
INSERT INTO `aircrafts`
VALUES (5, 'Boeing 757', 60, 30, 10, 100.00, 200.00);
INSERT INTO `aircrafts`
VALUES (6, 'Airbus A320', 50, 30, 10, 100.00, 200.00);
INSERT INTO `aircrafts`
VALUES (7, 'Boeing 737', 50, 20, 10, 120.00, 300.00);
INSERT INTO `aircrafts`
VALUES (8, 'Boeing 737', 50, 20, 10, 125.00, 310.00);
INSERT INTO `aircrafts`
VALUES (9, 'Boeing 717', 40, 15, 5, 130.00, 320.00);
INSERT INTO `aircrafts`
VALUES (10, 'Boeing 717', 40, 15, 5, 135.00, 330.00);
INSERT INTO `aircrafts`
VALUES (11, 'Boeing 757', 60, 30, 10, 140.00, 340.00);
INSERT INTO `aircrafts`
VALUES (12, 'Airbus A320', 50, 30, 10, 145.00, 350.00);
-- ----------------------------
-- Table structure for crews
-- ----------------------------
DROP TABLE IF EXISTS `crews`;
CREATE TABLE `crews` (
  `CrewID` int NOT NULL AUTO_INCREMENT,
  `Name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `Position` enum('pilot', 'flight_attendant', 'engineer') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `FlightID` int NULL DEFAULT NULL,
  PRIMARY KEY (`CrewID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;
-- ----------------------------
-- Records of crews
-- ----------------------------
INSERT INTO `crews`
VALUES (1, 'John Doe', 'pilot', 1);
INSERT INTO `crews`
VALUES (2, 'Jane Doe', 'pilot', 5);
INSERT INTO `crews`
VALUES (3, 'J Doe', 'flight_attendant', 1);
INSERT INTO `crews`
VALUES (4, 'Jane Doe', 'flight_attendant', 1);
INSERT INTO `crews`
VALUES (5, 'John Doe', 'engineer', 1);
INSERT INTO `crews`
VALUES (6, 'Jane Doe', 'engineer', 4);
INSERT INTO `crews`
VALUES (7, 'Michael Johnson', 'pilot', 3);
INSERT INTO `crews`
VALUES (8, 'Amanda Williams', 'pilot', 0);
INSERT INTO `crews`
VALUES (9, 'Christopher Lee', 'flight_attendant', 2);
INSERT INTO `crews`
VALUES (10, 'Samantha Miller', 'flight_attendant', 3);
INSERT INTO `crews`
VALUES (11, 'Daniel White', 'engineer', 2);
INSERT INTO `crews`
VALUES (12, 'Olivia Brown', 'engineer', 3);
INSERT INTO `crews`
VALUES (13, 'Matthew Davis', 'pilot', 2);
INSERT INTO `crews`
VALUES (14, 'Ella Wilson', 'flight_attendant', 0);
-- ----------------------------
-- Table structure for flights
-- ----------------------------
DROP TABLE IF EXISTS `flights`;
CREATE TABLE `flights` (
  `FlightID` int NOT NULL AUTO_INCREMENT,
  `Origin` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `Destination` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `DepartureDateTime` datetime NOT NULL,
  `ArrivalDateTime` datetime NOT NULL,
  `AircraftID` int NOT NULL,
  PRIMARY KEY (`FlightID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 24 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;
-- ----------------------------
-- Records of flights
-- ----------------------------
INSERT INTO `flights`
VALUES (
    1,
    'Calgary',
    'Toronto',
    '2024-04-01 12:00:00',
    '2024-04-01 16:00:00',
    1
  );
INSERT INTO `flights`
VALUES (
    2,
    'Calgary',
    'Vancouver',
    '2024-05-01 01:00:00',
    '2024-05-01 02:00:00',
    5
  );
INSERT INTO `flights`
VALUES (
    3,
    'Vancouver',
    'Toronto',
    '2024-06-01 16:00:00',
    '2024-06-01 20:00:00',
    6
  );
INSERT INTO `flights`
VALUES (
    4,
    'Vancouver',
    'Calgary',
    '2024-07-01 11:00:00',
    '2024-07-01 15:00:00',
    8
  );
INSERT INTO `flights`
VALUES (
    5,
    'Montreal',
    'Halifax',
    '2024-08-31 10:00:00',
    '2024-08-31 13:00:00',
    3
  );
INSERT INTO `flights`
VALUES (
    6,
    'Vancouver',
    'Montreal',
    '2024-12-09 11:00:00',
    '2024-12-09 14:00:00',
    4
  );
INSERT INTO `flights`
VALUES (
    7,
    'Montreal',
    'Toronto',
    '2024-09-10 05:00:00',
    '2024-09-10 06:00:00',
    1
  );
INSERT INTO `flights`
VALUES (
    8,
    'Calgary',
    'Ottawa',
    '2024-11-02 21:00:00',
    '2024-11-03 01:00:00',
    3
  );
INSERT INTO `flights`
VALUES (
    9,
    'Vancouver',
    'Calgary',
    '2024-11-03 06:00:00',
    '2024-11-03 08:00:00',
    10
  );
INSERT INTO `flights`
VALUES (
    10,
    'Calgary',
    'Montreal',
    '2024-10-25 04:00:00',
    '2024-10-25 06:00:00',
    12
  );
INSERT INTO `flights`
VALUES (
    11,
    'Montreal',
    'Ottawa',
    '2024-12-27 00:00:00',
    '2024-12-27 06:00:00',
    6
  );
INSERT INTO `flights`
VALUES (
    12,
    'Vancouver',
    'Halifax',
    '2024-09-05 01:00:00',
    '2024-09-05 03:00:00',
    8
  );
INSERT INTO `flights`
VALUES (
    13,
    'Vancouver',
    'Halifax',
    '2024-11-07 09:00:00',
    '2024-11-07 14:00:00',
    12
  );
INSERT INTO `flights`
VALUES (
    14,
    'Toronto',
    'Halifax',
    '2024-10-11 15:00:00',
    '2024-10-11 17:00:00',
    10
  );
INSERT INTO `flights`
VALUES (
    15,
    'Halifax',
    'Toronto',
    '2024-10-12 09:00:00',
    '2024-10-12 11:00:00',
    11
  );
INSERT INTO `flights`
VALUES (
    16,
    'Halifax',
    'Vancouver',
    '2024-09-06 13:00:00',
    '2024-09-06 15:00:00',
    9
  );
INSERT INTO `flights`
VALUES (
    17,
    'Toronto',
    'Calgary',
    '2024-04-01 19:00:00',
    '2024-04-01 23:00:00',
    9
  );
INSERT INTO `flights`
VALUES (
    18,
    'Toronto',
    'Vancouver',
    '2024-06-02 07:00:00',
    '2024-06-02 10:00:00',
    10
  );
INSERT INTO `flights`
VALUES (
    19,
    'Halifax',
    'Montreal',
    '2024-08-31 17:00:00',
    '2024-08-31 20:00:00',
    1
  );
INSERT INTO `flights`
VALUES (
    20,
    'Ottawa',
    'Montreal',
    '2024-12-27 08:00:00',
    '2024-12-27 12:00:00',
    12
  );
INSERT INTO `flights`
VALUES (
    21,
    'Toronto',
    'Montreal',
    '2024-09-10 09:00:00',
    '2024-09-10 10:00:00',
    2
  );
INSERT INTO `flights`
VALUES (
    22,
    'Ottawa',
    'Calgary',
    '2024-11-03 07:00:00',
    '2024-11-03 11:00:00',
    5
  );
INSERT INTO `flights`
VALUES (
    23,
    'Montreal',
    'Calgary',
    '2024-10-25 08:00:00',
    '2024-10-25 10:00:00',
    4
  );
-- ----------------------------
-- Table structure for orders
-- ----------------------------
DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders` (
  `OrderID` int NOT NULL AUTO_INCREMENT,
  `Email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `Username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `FlightID` int NOT NULL,
  `AircraftModel` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `DepartureLocation` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `ArrivalLocation` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `DepartureDateTime` datetime NOT NULL,
  `ArrivalDateTime` datetime NOT NULL,
  `Class` enum('Economy', 'Business', 'Comfort') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `SeatNumber` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `Insurance` tinyint(1) NOT NULL,
  `TotalPrice` decimal(10, 2) NOT NULL,
  PRIMARY KEY (`OrderID`) USING BTREE,
  INDEX `FlightID`(`FlightID` ASC) USING BTREE,
  CONSTRAINT `orders_ibfk_1` FOREIGN KEY (`FlightID`) REFERENCES `flights` (`FlightID`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;
-- ----------------------------
-- Records of orders
-- ----------------------------
INSERT INTO `orders`
VALUES (
    1,
    'sara@gmail.com',
    'sara',
    2,
    'Boeing 737',
    'Calgary',
    'Vancouver',
    '2024-05-01 01:00:00',
    '2024-05-01 02:00:00',
    'Economy',
    'E16',
    1,
    150.00
  );
INSERT INTO `orders`
VALUES (
    2,
    'sara@gmail.com',
    'sara',
    2,
    'Boeing 737',
    'Calgary',
    'Vancouver',
    '2024-05-01 01:00:00',
    '2024-05-01 02:00:00',
    'Economy',
    'E17',
    1,
    150.00
  );
INSERT INTO `orders`
VALUES (
    3,
    'sara@gmail.com',
    'sara',
    2,
    'Boeing 737',
    'Calgary',
    'Vancouver',
    '2024-05-01 01:00:00',
    '2024-05-01 02:00:00',
    'Economy',
    'E18',
    1,
    150.00
  );
INSERT INTO `orders`
VALUES (
    4,
    'sara@gmail.com',
    'sara',
    3,
    'Boeing 737',
    'Calgary',
    'Vancouver',
    '2024-05-01 01:00:00',
    '2024-05-01 02:00:00',
    'Economy',
    'E16',
    1,
    150.00
  );
INSERT INTO `orders`
VALUES (
    5,
    'sara@gmail.com',
    'sara',
    3,
    'Boeing 737',
    'Calgary',
    'Vancouver',
    '2024-05-01 01:00:00',
    '2024-05-01 02:00:00',
    'Economy',
    'E17',
    1,
    150.00
  );
INSERT INTO `orders`
VALUES (
    6,
    'sara@gmail.com',
    'sara',
    3,
    'Boeing 737',
    'Calgary',
    'Vancouver',
    '2024-05-01 01:00:00',
    '2024-05-01 02:00:00',
    'Economy',
    'E18',
    1,
    150.00
  );
-- ----------------------------
-- Table structure for promotions
-- ----------------------------
DROP TABLE IF EXISTS `promotions`;
CREATE TABLE `promotions` (
  `PromotionID` int NOT NULL AUTO_INCREMENT,
  `Description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `ValidOn` datetime NOT NULL,
  `ValidUntil` datetime NOT NULL,
  PRIMARY KEY (`PromotionID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;
-- ----------------------------
-- Records of promotions
-- ----------------------------
INSERT INTO `promotions`
VALUES (
    1,
    'Promotion 1',
    '2023-10-01 12:00:00',
    '2023-10-30 11:59:59'
  );
INSERT INTO `promotions`
VALUES (
    2,
    'Promotion 2',
    '2023-11-01 12:00:00',
    '2023-10-30 11:59:59'
  );
INSERT INTO `promotions`
VALUES (
    3,
    'Promotion 3',
    '2023-12-01 12:00:00',
    '2023-10-30 11:59:59'
  );
INSERT INTO `promotions`
VALUES (
    4,
    'Promotion 4',
    '2024-01-01 12:00:00',
    '2023-10-30 11:59:59'
  );
-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `UserID` int NOT NULL AUTO_INCREMENT,
  `Name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `Address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `Email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `UserType` enum(
    'passenger',
    'tourism_agent',
    'airline_agent',
    'admin',
    'flight_attendant'
  ) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `MembershipStatus` tinyint(1) NULL DEFAULT 0,
  `CreditCardInfo` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `CreditCardExpiry` int NULL DEFAULT NULL,
  `CreditCardCVV` int NULL DEFAULT NULL,
  `PasswordHash` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `CompanionTickets` int NULL DEFAULT 1,
  PRIMARY KEY (`UserID`) USING BTREE,
  UNIQUE INDEX `Name`(`Name` ASC) USING BTREE,
  UNIQUE INDEX `Email`(`Email` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;
-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users`
VALUES (
    1,
    'John Doe',
    '1234 5th Ave, Calgary, AB',
    'johnDoe@gmail.com',
    'passenger',
    0,
    '1234-5678-9012-3456',
    101,
    123,
    '',
    1
  );
INSERT INTO `users`
VALUES (
    2,
    'Jane Doe',
    '1234 5th Ave, Calgary, AB',
    'janeDoe@gmail.com',
    'passenger',
    0,
    '1234-5678-9012-3456',
    202,
    123,
    '',
    1
  );
INSERT INTO `users`
VALUES (
    6,
    'sara',
    NULL,
    'sara@gmail.com',
    'passenger',
    0,
    NULL,
    606,
    123,
    '123456',
    1
  );
INSERT INTO `users`
VALUES (
    7,
    'admin',
    NULL,
    '00000',
    'admin',
    0,
    NULL,
    606,
    123,
    '0000',
    0
  );
INSERT INTO `users`
VALUES (
    8,
    'Aryan',
    '1236 5th Ave, Calgary, AB',
    'karadiaaryan@gmail.com',
    'passenger',
    0,
    '1234123412341234',
    1024,
    100,
    'pass',
    9
  );
INSERT INTO `users`
VALUES (
    9,
    'braydon',
    '1236 5th Ave, Calgary, AB',
    'braydon@gmail.com',
    'flight_attendant',
    0,
    '1234123412341234',
    1024,
    100,
    '0000',
    1
  );
INSERT INTO `users`
VALUES (
    10,
    'bruh',
    '1236 5th Ave, Calgary, AB',
    'bruh@gmail.com',
    'airline_agent',
    0,
    '1234123412341234',
    1024,
    100,
    '0000',
    1
  );
SET FOREIGN_KEY_CHECKS = 1;