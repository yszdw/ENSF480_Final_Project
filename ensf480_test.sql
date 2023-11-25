/*
 Navicat Premium Data Transfer

 Source Server         : database
 Source Server Type    : MySQL
 Source Server Version : 80035 (8.0.35)
 Source Host           : localhost:3306
 Source Schema         : ensf480

 Target Server Type    : MySQL
 Target Server Version : 80035 (8.0.35)
 File Encoding         : 65001

 Date: 24/11/2023 18:15:07
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for aircrafts
-- ----------------------------
DROP TABLE IF EXISTS `aircrafts`;
CREATE TABLE `aircrafts`  (
  `AircraftID` int NOT NULL AUTO_INCREMENT,
  `Model` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `Capacity` int NOT NULL,
  PRIMARY KEY (`AircraftID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of aircrafts
-- ----------------------------

-- ----------------------------
-- Table structure for bookings
-- ----------------------------
DROP TABLE IF EXISTS `bookings`;
CREATE TABLE `bookings`  (
  `BookingID` int NOT NULL AUTO_INCREMENT,
  `UserID` int NOT NULL,
  `FlightID` int NOT NULL,
  `SeatID` int NOT NULL,
  `CancellationInsurance` tinyint(1) NULL DEFAULT 0,
  `BookingDateTime` datetime NOT NULL,
  PRIMARY KEY (`BookingID`) USING BTREE,
  INDEX `UserID`(`UserID` ASC) USING BTREE,
  INDEX `FlightID`(`FlightID` ASC) USING BTREE,
  INDEX `SeatID`(`SeatID` ASC) USING BTREE,
  CONSTRAINT `bookings_ibfk_1` FOREIGN KEY (`UserID`) REFERENCES `users` (`UserID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `bookings_ibfk_2` FOREIGN KEY (`FlightID`) REFERENCES `flights` (`FlightID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `bookings_ibfk_3` FOREIGN KEY (`SeatID`) REFERENCES `seats` (`SeatID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of bookings
-- ----------------------------

-- ----------------------------
-- Table structure for crews
-- ----------------------------
DROP TABLE IF EXISTS `crews`;
CREATE TABLE `crews`  (
  `CrewID` int NOT NULL AUTO_INCREMENT,
  `Name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `Position` enum('pilot','flight_attendant','engineer') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`CrewID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of crews
-- ----------------------------

-- ----------------------------
-- Table structure for destinations
-- ----------------------------
DROP TABLE IF EXISTS `destinations`;
CREATE TABLE `destinations`  (
  `DestinationID` int NOT NULL AUTO_INCREMENT,
  `City` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `Country` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`DestinationID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of destinations
-- ----------------------------

-- ----------------------------
-- Table structure for flights
-- ----------------------------
DROP TABLE IF EXISTS `flights`;
CREATE TABLE `flights`  (
  `FlightID` int NOT NULL AUTO_INCREMENT,
  `Origin` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `Destination` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `DepartureDateTime` datetime NOT NULL,
  `ArrivalDateTime` datetime NOT NULL,
  PRIMARY KEY (`FlightID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of flights
-- ----------------------------
INSERT INTO `flights` VALUES (1, 'Calgary', 'Toronto', '2024-04-01 12:00:00', '2024-04-01 16:00:00');
INSERT INTO `flights` VALUES (2, 'Calgary', 'Vancouver', '2024-05-01 01:00:00', '2024-05-01 02:00:00');
INSERT INTO `flights` VALUES (3, 'Vancouver', 'Toronto', '2024-06-01 16:00:00', '2024-06-01 20:00:00');
INSERT INTO `flights` VALUES (4, 'Vancouver', 'Calgary', '2024-07-01 11:00:00', '2024-07-01 15:00:00');

-- ----------------------------
-- Table structure for payments
-- ----------------------------
DROP TABLE IF EXISTS `payments`;
CREATE TABLE `payments`  (
  `PaymentID` int NOT NULL AUTO_INCREMENT,
  `BookingID` int NOT NULL,
  `Amount` decimal(10, 2) NOT NULL,
  `PaymentDateTime` datetime NOT NULL,
  `CreditCardUsed` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`PaymentID`) USING BTREE,
  INDEX `BookingID`(`BookingID` ASC) USING BTREE,
  CONSTRAINT `payments_ibfk_1` FOREIGN KEY (`BookingID`) REFERENCES `bookings` (`BookingID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of payments
-- ----------------------------

-- ----------------------------
-- Table structure for promotions
-- ----------------------------
DROP TABLE IF EXISTS `promotions`;
CREATE TABLE `promotions`  (
  `PromotionID` int NOT NULL AUTO_INCREMENT,
  `UserID` int NOT NULL,
  `Description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `ValidUntil` datetime NOT NULL,
  PRIMARY KEY (`PromotionID`) USING BTREE,
  INDEX `UserID`(`UserID` ASC) USING BTREE,
  CONSTRAINT `promotions_ibfk_1` FOREIGN KEY (`UserID`) REFERENCES `users` (`UserID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of promotions
-- ----------------------------

-- ----------------------------
-- Table structure for seats
-- ----------------------------
DROP TABLE IF EXISTS `seats`;
CREATE TABLE `seats`  (
  `SeatID` int NOT NULL AUTO_INCREMENT,
  `FlightID` int NOT NULL,
  `SeatNumber` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `SeatType` enum('ordinary','comfort','business_class') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `Price` decimal(10, 2) NOT NULL,
  `IsBooked` tinyint(1) NULL DEFAULT 0,
  PRIMARY KEY (`SeatID`) USING BTREE,
  INDEX `FlightID`(`FlightID` ASC) USING BTREE,
  CONSTRAINT `seats_ibfk_1` FOREIGN KEY (`FlightID`) REFERENCES `flights` (`FlightID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of seats
-- ----------------------------

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users`  (
  `UserID` int NOT NULL AUTO_INCREMENT,
  `Name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `Address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `Email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `UserType` enum('passenger','tourism_agent','airline_agent','admin') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `MembershipStatus` tinyint(1) NULL DEFAULT 0,
  `CreditCardInfo` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `PasswordHash` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`UserID`) USING BTREE,
  UNIQUE INDEX `Email`(`Email` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES (1, 'John Doe', '1234 5th Ave, Calgary, AB', 'johnDoe@gmail.com', 'passenger', 0, '1234-5678-9012-3456', '');
INSERT INTO `users` VALUES (2, 'Jane Doe', '1234 5th Ave, Calgary, AB', 'janeDoe@gmail.com', 'passenger', 0, '1234-5678-9012-3456', '');
INSERT INTO `users` VALUES (3, 'ysz', NULL, 'sgg', 'passenger', 0, NULL, '123456');
INSERT INTO `users` VALUES (4, 'Hhf', NULL, 'bgesrg', 'passenger', 0, NULL, '12345678');
INSERT INTO `users` VALUES (5, 'ryg', NULL, 'wghtg', 'passenger', 0, NULL, '4545');
INSERT INTO `users` VALUES (6, 'sara', NULL, 'fsefs', 'passenger', 0, NULL, '123456');

SET FOREIGN_KEY_CHECKS = 1;
