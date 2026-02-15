-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               8.4.3 - MySQL Community Server - GPL
-- Server OS:                    Win64
-- HeidiSQL Version:             12.8.0.6908
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Dumping database structure for parking_system
DROP DATABASE IF EXISTS `parking_system`;
CREATE DATABASE IF NOT EXISTS `parking_system` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `parking_system`;

-- Dumping structure for table parking_system.fine
DROP TABLE IF EXISTS `fine`;
CREATE TABLE IF NOT EXISTS `fine` (
  `id` int NOT NULL AUTO_INCREMENT,
  `license_plate` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `amount` decimal(10,2) NOT NULL,
  `status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT 'unpaid',
  `reason` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Dumping data for table parking_system.fine: ~0 rows (approximately)
REPLACE INTO `fine` (`id`, `license_plate`, `amount`, `status`, `reason`, `created_at`) VALUES
	(5, '1233', 60.00, 'unpaid', 'Overstayed parking duration', '2026-02-16 04:46:45');

-- Dumping structure for table parking_system.fine_scheme
DROP TABLE IF EXISTS `fine_scheme`;
CREATE TABLE IF NOT EXISTS `fine_scheme` (
  `id` int NOT NULL AUTO_INCREMENT,
  `scheme_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '0',
  `base_amount` double DEFAULT '0',
  `additional_24_48` double DEFAULT '0',
  `additional_48_72` double DEFAULT '0',
  `above_72` double DEFAULT '0',
  `hourly_rate` double DEFAULT '0',
  `is_active` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table parking_system.fine_scheme: ~3 rows (approximately)
REPLACE INTO `fine_scheme` (`id`, `scheme_type`, `base_amount`, `additional_24_48`, `additional_48_72`, `above_72`, `hourly_rate`, `is_active`) VALUES
	(1, 'Flat Rate', 50, 0, 0, 0, 0, 0),
	(2, 'Per Hour', 50, 100, 150, 200, 0, 0),
	(3, 'Progressive', 0, 0, 0, 0, 20, 1);

-- Dumping structure for table parking_system.parking_floor
DROP TABLE IF EXISTS `parking_floor`;
CREATE TABLE IF NOT EXISTS `parking_floor` (
  `id` int NOT NULL AUTO_INCREMENT,
  `number` int NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table parking_system.parking_floor: ~5 rows (approximately)
REPLACE INTO `parking_floor` (`id`, `number`, `created_at`, `updated_at`) VALUES
	(1, 1, '2026-01-29 09:22:23', '2026-01-29 09:22:23'),
	(2, 2, '2026-01-29 09:22:23', '2026-01-29 09:22:23'),
	(3, 3, '2026-01-29 09:22:23', '2026-01-29 09:22:23'),
	(4, 4, '2026-01-29 09:22:23', '2026-01-29 09:22:23'),
	(5, 5, '2026-01-29 09:22:23', '2026-01-29 09:22:23');

-- Dumping structure for table parking_system.parking_spot
DROP TABLE IF EXISTS `parking_spot`;
CREATE TABLE IF NOT EXISTS `parking_spot` (
  `id` int NOT NULL AUTO_INCREMENT,
  `row_number` int DEFAULT NULL,
  `floor_id` int DEFAULT NULL,
  `spot_number` int DEFAULT NULL,
  `type_id` int DEFAULT NULL,
  `status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `current_vehicle` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `floor_id` (`floor_id`)
) ENGINE=InnoDB AUTO_INCREMENT=151 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table parking_system.parking_spot: ~10 rows (approximately)
REPLACE INTO `parking_spot` (`id`, `row_number`, `floor_id`, `spot_number`, `type_id`, `status`, `current_vehicle`, `created_at`, `updated_at`) VALUES
	(51, 1, 1, 1, 1, 'available', NULL, '2026-01-29 10:38:37', '2026-01-29 10:38:37'),
	(52, 1, 1, 2, 1, 'occupied', '1233', '2026-01-29 10:38:37', '2026-01-29 10:38:37'),
	(53, 1, 1, 3, 1, 'available', NULL, '2026-01-29 10:38:37', '2026-01-29 10:38:37'),
	(54, 1, 1, 4, 1, 'available', NULL, '2026-01-29 10:38:37', '2026-01-29 10:38:37'),
	(55, 1, 1, 5, 1, 'occupied', '4566', '2026-01-29 10:38:37', '2026-01-29 10:38:37'),
	(56, 2, 1, 1, 2, 'available', NULL, '2026-01-29 10:38:37', '2026-01-29 10:38:37'),
	(57, 2, 1, 2, 2, 'available', NULL, '2026-01-29 10:38:37', '2026-01-29 10:38:37'),
	(58, 2, 1, 3, 2, 'available', NULL, '2026-01-29 10:38:37', '2026-01-29 10:38:37'),
	(59, 2, 1, 4, 2, 'available', NULL, '2026-01-29 10:38:37', '2026-01-29 10:38:37'),
	(60, 2, 1, 5, 2, 'available', NULL, '2026-01-29 10:38:37', '2026-01-29 10:38:37'),
	(61, 3, 1, 1, 2, 'available', NULL, '2026-01-29 10:38:37', '2026-01-29 10:38:37'),
	(62, 3, 1, 2, 2, 'occupied', 'ASD', '2026-01-29 10:38:37', '2026-01-29 10:38:37'),
	(63, 3, 1, 3, 2, 'available', NULL, '2026-01-29 10:38:37', '2026-01-29 10:38:37'),
	(64, 3, 1, 4, 2, 'occupied', 'VCBCVB', '2026-01-29 10:38:37', '2026-01-29 10:38:37'),
	(65, 3, 1, 5, 2, 'available', NULL, '2026-01-29 10:38:37', '2026-01-29 10:38:37'),
	(66, 4, 1, 1, 4, 'available', NULL, '2026-01-29 10:38:37', '2026-01-29 10:38:37'),
	(67, 4, 1, 2, 4, 'available', NULL, '2026-01-29 10:38:37', '2026-01-29 10:38:37'),
	(68, 4, 1, 3, 4, 'available', NULL, '2026-01-29 10:38:37', '2026-01-29 10:38:37'),
	(69, 4, 1, 4, 3, 'available', NULL, '2026-01-29 10:38:37', '2026-01-29 10:38:37'),
	(70, 4, 1, 5, 3, 'available', NULL, '2026-01-29 10:38:37', '2026-01-29 10:38:37'),
	(71, 1, 2, 1, 1, 'available', NULL, '2026-01-29 10:38:37', '2026-01-29 10:38:37'),
	(72, 1, 2, 2, 1, 'available', NULL, '2026-01-29 10:38:37', '2026-01-29 10:38:37'),
	(73, 1, 2, 3, 1, 'available', NULL, '2026-01-29 10:38:37', '2026-01-29 10:38:37'),
	(74, 1, 2, 4, 1, 'available', NULL, '2026-01-29 10:38:37', '2026-01-29 10:38:37'),
	(75, 1, 2, 5, 1, 'available', NULL, '2026-01-29 10:38:37', '2026-01-29 10:38:37'),
	(76, 2, 2, 1, 2, 'available', NULL, '2026-01-29 10:38:37', '2026-01-29 10:38:37'),
	(77, 2, 2, 2, 2, 'available', NULL, '2026-01-29 10:38:37', '2026-01-29 10:38:37'),
	(78, 2, 2, 3, 2, 'available', NULL, '2026-01-29 10:38:37', '2026-01-29 10:38:37'),
	(79, 2, 2, 4, 2, 'available', NULL, '2026-01-29 10:38:37', '2026-01-29 10:38:37'),
	(80, 2, 2, 5, 2, 'available', NULL, '2026-01-29 10:38:37', '2026-01-29 10:38:37'),
	(81, 3, 2, 1, 2, 'available', NULL, '2026-01-29 10:38:37', '2026-01-29 10:38:37'),
	(82, 3, 2, 2, 2, 'available', NULL, '2026-01-29 10:38:37', '2026-01-29 10:38:37'),
	(83, 3, 2, 3, 2, 'available', NULL, '2026-01-29 10:38:37', '2026-01-29 10:38:37'),
	(84, 3, 2, 4, 2, 'available', NULL, '2026-01-29 10:38:37', '2026-01-29 10:38:37'),
	(85, 3, 2, 5, 2, 'available', NULL, '2026-01-29 10:38:37', '2026-01-29 10:38:37'),
	(86, 4, 2, 1, 4, 'available', NULL, '2026-01-29 10:38:37', '2026-01-29 10:38:37'),
	(87, 4, 2, 2, 4, 'available', NULL, '2026-01-29 10:38:37', '2026-01-29 10:38:37'),
	(88, 4, 2, 3, 4, 'available', NULL, '2026-01-29 10:38:37', '2026-01-29 10:38:37'),
	(89, 4, 2, 4, 3, 'available', NULL, '2026-01-29 10:38:37', '2026-01-29 10:38:37'),
	(90, 4, 2, 5, 3, 'available', NULL, '2026-01-29 10:38:37', '2026-01-29 10:38:37'),
	(91, 1, 3, 1, 1, 'available', NULL, '2026-01-29 10:38:37', '2026-01-29 10:38:37'),
	(92, 1, 3, 2, 1, 'available', NULL, '2026-01-29 10:38:37', '2026-01-29 10:38:37'),
	(93, 1, 3, 3, 1, 'available', NULL, '2026-01-29 10:38:37', '2026-01-29 10:38:37'),
	(94, 1, 3, 4, 1, 'available', NULL, '2026-01-29 10:38:37', '2026-01-29 10:38:37'),
	(95, 1, 3, 5, 1, 'available', NULL, '2026-01-29 10:38:37', '2026-01-29 10:38:37'),
	(96, 2, 3, 1, 2, 'available', NULL, '2026-01-29 10:38:37', '2026-01-29 10:38:37'),
	(97, 2, 3, 2, 2, 'available', NULL, '2026-01-29 10:38:37', '2026-01-29 10:38:37'),
	(98, 2, 3, 3, 2, 'available', NULL, '2026-01-29 10:38:37', '2026-01-29 10:38:37'),
	(99, 2, 3, 4, 2, 'available', NULL, '2026-01-29 10:38:37', '2026-01-29 10:38:37'),
	(100, 2, 3, 5, 2, 'available', NULL, '2026-01-29 10:38:37', '2026-01-29 10:38:37'),
	(101, 3, 3, 1, 2, 'available', NULL, '2026-01-29 10:38:37', '2026-01-29 10:38:37'),
	(102, 3, 3, 2, 2, 'available', NULL, '2026-01-29 10:38:37', '2026-01-29 10:38:37'),
	(103, 3, 3, 3, 2, 'available', NULL, '2026-01-29 10:38:37', '2026-01-29 10:38:37'),
	(104, 3, 3, 4, 2, 'available', NULL, '2026-01-29 10:38:37', '2026-01-29 10:38:37'),
	(105, 3, 3, 5, 2, 'available', NULL, '2026-01-29 10:38:37', '2026-01-29 10:38:37'),
	(106, 4, 3, 1, 4, 'available', NULL, '2026-01-29 10:38:37', '2026-01-29 10:38:37'),
	(107, 4, 3, 2, 4, 'available', NULL, '2026-01-29 10:38:37', '2026-01-29 10:38:37'),
	(108, 4, 3, 3, 4, 'available', NULL, '2026-01-29 10:38:37', '2026-01-29 10:38:37'),
	(109, 4, 3, 4, 3, 'available', NULL, '2026-01-29 10:38:37', '2026-01-29 10:38:37'),
	(110, 4, 3, 5, 3, 'available', NULL, '2026-01-29 10:38:37', '2026-01-29 10:38:37'),
	(111, 1, 4, 1, 1, 'available', NULL, '2026-01-29 10:38:37', '2026-01-29 10:38:37'),
	(112, 1, 4, 2, 1, 'available', NULL, '2026-01-29 10:38:37', '2026-01-29 10:38:37'),
	(113, 1, 4, 3, 1, 'available', NULL, '2026-01-29 10:38:37', '2026-01-29 10:38:37'),
	(114, 1, 4, 4, 1, 'available', NULL, '2026-01-29 10:38:37', '2026-01-29 10:38:37'),
	(115, 1, 4, 5, 1, 'available', NULL, '2026-01-29 10:38:37', '2026-01-29 10:38:37'),
	(116, 2, 4, 1, 2, 'available', NULL, '2026-01-29 10:38:37', '2026-01-29 10:38:37'),
	(117, 2, 4, 2, 2, 'available', NULL, '2026-01-29 10:38:37', '2026-01-29 10:38:37'),
	(118, 2, 4, 3, 2, 'available', NULL, '2026-01-29 10:38:37', '2026-01-29 10:38:37'),
	(119, 2, 4, 4, 2, 'available', NULL, '2026-01-29 10:38:37', '2026-01-29 10:38:37'),
	(120, 2, 4, 5, 2, 'available', NULL, '2026-01-29 10:38:37', '2026-01-29 10:38:37'),
	(121, 3, 4, 1, 2, 'available', NULL, '2026-01-29 10:38:37', '2026-01-29 10:38:37'),
	(122, 3, 4, 2, 2, 'available', NULL, '2026-01-29 10:38:37', '2026-01-29 10:38:37'),
	(123, 3, 4, 3, 2, 'available', NULL, '2026-01-29 10:38:37', '2026-01-29 10:38:37'),
	(124, 3, 4, 4, 2, 'available', NULL, '2026-01-29 10:38:37', '2026-01-29 10:38:37'),
	(125, 3, 4, 5, 2, 'available', NULL, '2026-01-29 10:38:37', '2026-01-29 10:38:37'),
	(126, 4, 4, 1, 4, 'available', NULL, '2026-01-29 10:38:37', '2026-01-29 10:38:37'),
	(127, 4, 4, 2, 4, 'available', NULL, '2026-01-29 10:38:37', '2026-01-29 10:38:37'),
	(128, 4, 4, 3, 4, 'available', NULL, '2026-01-29 10:38:37', '2026-01-29 10:38:37'),
	(129, 4, 4, 4, 3, 'available', NULL, '2026-01-29 10:38:37', '2026-01-29 10:38:37'),
	(130, 4, 4, 5, 3, 'available', NULL, '2026-01-29 10:38:37', '2026-01-29 10:38:37'),
	(131, 1, 5, 1, 1, 'available', NULL, '2026-01-29 10:38:37', '2026-01-29 10:38:37'),
	(132, 1, 5, 2, 1, 'available', NULL, '2026-01-29 10:38:37', '2026-01-29 10:38:37'),
	(133, 1, 5, 3, 1, 'available', NULL, '2026-01-29 10:38:37', '2026-01-29 10:38:37'),
	(134, 1, 5, 4, 1, 'available', NULL, '2026-01-29 10:38:37', '2026-01-29 10:38:37'),
	(135, 1, 5, 5, 1, 'available', NULL, '2026-01-29 10:38:37', '2026-01-29 10:38:37'),
	(136, 2, 5, 1, 2, 'available', NULL, '2026-01-29 10:38:37', '2026-01-29 10:38:37'),
	(137, 2, 5, 2, 2, 'available', NULL, '2026-01-29 10:38:37', '2026-01-29 10:38:37'),
	(138, 2, 5, 3, 2, 'available', NULL, '2026-01-29 10:38:37', '2026-01-29 10:38:37'),
	(139, 2, 5, 4, 2, 'available', NULL, '2026-01-29 10:38:37', '2026-01-29 10:38:37'),
	(140, 2, 5, 5, 2, 'available', NULL, '2026-01-29 10:38:37', '2026-01-29 10:38:37'),
	(141, 3, 5, 1, 2, 'available', NULL, '2026-01-29 10:38:37', '2026-01-29 10:38:37'),
	(142, 3, 5, 2, 2, 'available', NULL, '2026-01-29 10:38:37', '2026-01-29 10:38:37'),
	(143, 3, 5, 3, 2, 'available', NULL, '2026-01-29 10:38:37', '2026-01-29 10:38:37'),
	(144, 3, 5, 4, 2, 'available', NULL, '2026-01-29 10:38:37', '2026-01-29 10:38:37'),
	(145, 3, 5, 5, 2, 'available', NULL, '2026-01-29 10:38:37', '2026-01-29 10:38:37'),
	(146, 4, 5, 1, 4, 'available', NULL, '2026-01-29 10:38:37', '2026-01-29 10:38:37'),
	(147, 4, 5, 2, 4, 'available', NULL, '2026-01-29 10:38:37', '2026-01-29 10:38:37'),
	(148, 4, 5, 3, 4, 'available', NULL, '2026-01-29 10:38:37', '2026-01-29 10:38:37'),
	(149, 4, 5, 4, 3, 'available', NULL, '2026-01-29 10:38:37', '2026-01-29 10:38:37'),
	(150, 4, 5, 5, 3, 'available', NULL, '2026-01-29 10:38:37', '2026-01-29 10:38:37');

-- Dumping structure for table parking_system.parking_spot_type
DROP TABLE IF EXISTS `parking_spot_type`;
CREATE TABLE IF NOT EXISTS `parking_spot_type` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `hourly_rate` double NOT NULL,
  `created_at` timestamp NOT NULL,
  `updated_at` timestamp NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table parking_system.parking_spot_type: ~4 rows (approximately)
REPLACE INTO `parking_spot_type` (`id`, `name`, `hourly_rate`, `created_at`, `updated_at`) VALUES
	(1, 'compact', 2, '2026-01-29 09:13:47', '2026-01-29 09:13:47'),
	(2, 'regular', 5, '2026-01-29 09:13:47', '2026-01-29 09:13:47'),
	(3, 'handicapped', 2, '2026-01-29 09:13:47', '2026-01-29 09:13:47'),
	(4, 'reserved', 10, '2026-01-29 09:13:47', '2026-01-29 09:13:47');

-- Dumping structure for table parking_system.payment
DROP TABLE IF EXISTS `payment`;
CREATE TABLE IF NOT EXISTS `payment` (
  `id` int NOT NULL AUTO_INCREMENT,
  `ticket_id` int NOT NULL,
  `amount` decimal(10,2) NOT NULL,
  `method` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `payment_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `ticket_id` (`ticket_id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Dumping data for table parking_system.payment: ~16 rows (approximately)
REPLACE INTO `payment` (`id`, `ticket_id`, `amount`, `method`, `payment_time`) VALUES
	(10, 9, 0.00, 'Cash', '2026-02-16 02:03:14'),
	(11, 10, 0.00, 'Cash', '2026-02-16 02:10:00'),
	(12, 11, 0.00, 'Cash', '2026-02-16 02:18:19'),
	(13, 12, 0.00, 'Cash', '2026-02-16 02:20:45'),
	(14, 13, 5.00, 'Cash', '2026-02-16 02:28:28'),
	(15, 15, 0.00, 'Cash', '2026-02-16 02:30:17'),
	(16, 15, 0.00, 'Cash', '2026-02-16 02:31:21'),
	(17, 15, 0.00, 'Cash', '2026-02-16 02:35:27'),
	(18, 15, 0.00, 'Cash', '2026-02-16 02:36:23'),
	(19, 15, 0.00, 'Cash', '2026-02-16 02:37:27'),
	(20, 16, 4.00, 'Cash', '2026-02-16 02:37:35'),
	(21, 19, 0.00, 'Cash', '2026-02-16 03:47:15'),
	(22, 18, 0.00, 'Cash', '2026-02-16 03:47:23'),
	(23, 14, 4.00, 'Cash', '2026-02-16 03:47:29'),
	(24, 21, 5.00, 'Cash', '2026-02-16 06:10:26'),
	(25, 24, 2.00, 'Cash', '2026-02-16 06:14:53');

-- Dumping structure for table parking_system.ticket
DROP TABLE IF EXISTS `ticket`;
CREATE TABLE IF NOT EXISTS `ticket` (
  `id` int NOT NULL AUTO_INCREMENT,
  `ticket_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `vehicle_id` int NOT NULL,
  `spot_id` int NOT NULL,
  `entry_time` datetime NOT NULL,
  `exit_time` datetime DEFAULT NULL,
  `status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'active',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `ticket_code` (`ticket_code`),
  KEY `vehicle_id` (`vehicle_id`),
  KEY `spot_id` (`spot_id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table parking_system.ticket: ~16 rows (approximately)
REPLACE INTO `ticket` (`id`, `ticket_code`, `vehicle_id`, `spot_id`, `entry_time`, `exit_time`, `status`, `created_at`, `updated_at`) VALUES
	(9, 'T-SAD-20260216020300', 9, 60, '2026-02-16 02:03:00', '2026-02-16 02:03:14', 'paid', '2026-02-15 18:03:00', '2026-02-15 20:48:56'),
	(10, 'T-123-20260216020953', 10, 132, '2026-02-16 02:09:53', '2026-02-16 02:10:00', 'paid', '2026-02-15 18:09:53', '2026-02-15 20:49:01'),
	(11, 'T-123-20260216021806', 11, 53, '2026-02-16 02:18:07', '2026-02-16 02:18:19', 'paid', '2026-02-15 18:18:06', '2026-02-15 20:49:01'),
	(12, 'T-123-20260216022040', 12, 53, '2026-02-16 02:20:41', '2026-02-16 02:20:45', 'paid', '2026-02-15 18:20:40', '2026-02-15 20:49:00'),
	(13, 'T-123-20260216022058', 13, 59, '2026-02-16 01:30:59', '2026-02-16 02:28:28', 'paid', '2026-02-15 18:20:58', '2026-02-15 20:49:00'),
	(14, 'T-123-20260216022933', 14, 55, '2026-02-16 02:29:34', '2026-02-16 03:47:29', 'paid', '2026-02-15 18:29:33', '2026-02-15 20:48:59'),
	(15, 'T-456-20260216022945', 15, 69, '2026-02-16 01:29:45', '2026-02-16 02:37:27', 'paid', '2026-02-15 18:29:45', '2026-02-15 20:48:59'),
	(16, 'T-789-20260216022954', 16, 59, '2026-02-16 01:29:55', '2026-02-16 02:37:35', 'paid', '2026-02-15 18:29:54', '2026-02-15 20:48:59'),
	(17, 'T-1233-20260216034647', 17, 52, '2026-02-15 03:46:48', NULL, 'active', '2026-02-15 19:46:47', '2026-02-15 20:42:22'),
	(18, 'T-TRUCK-20260216034659', 18, 65, '2026-02-16 03:46:59', '2026-02-16 03:47:23', 'paid', '2026-02-15 19:46:59', '2026-02-15 20:49:02'),
	(19, 'T-H-20260216034709', 19, 69, '2026-02-16 03:47:10', '2026-02-16 03:47:15', 'paid', '2026-02-15 19:47:09', '2026-02-15 20:49:02'),
	(20, 'T-4566-20260216060413', 20, 55, '2026-02-16 06:04:14', NULL, 'active', '2026-02-15 22:04:13', '2026-02-15 22:04:13'),
	(21, 'T-123-20260216060423', 21, 58, '2026-02-16 06:04:23', '2026-02-16 06:10:26', 'paid', '2026-02-15 22:04:23', '2026-02-15 22:10:26'),
	(22, 'T-ASD-20260216060434', 22, 62, '2026-02-16 06:04:34', NULL, 'active', '2026-02-15 22:04:34', '2026-02-15 22:04:34'),
	(23, 'T-VCBCVB-20260216060502', 23, 64, '2026-02-16 06:05:02', NULL, 'active', '2026-02-15 22:05:02', '2026-02-15 22:05:02'),
	(24, 'T-A-20260216061008', 24, 54, '2026-02-16 06:10:08', '2026-02-16 06:14:53', 'paid', '2026-02-15 22:10:08', '2026-02-15 22:14:53');

-- Dumping structure for table parking_system.vehicle
DROP TABLE IF EXISTS `vehicle`;
CREATE TABLE IF NOT EXISTS `vehicle` (
  `id` int NOT NULL AUTO_INCREMENT,
  `license_plate` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `vehicle_type_id` int NOT NULL,
  `has_handicapped_card` tinyint(1) NOT NULL DEFAULT '0',
  `entry_time` datetime NOT NULL,
  `exit_time` datetime DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `vehicle_type_id` (`vehicle_type_id`),
  KEY `license_plate` (`license_plate`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table parking_system.vehicle: ~0 rows (approximately)
REPLACE INTO `vehicle` (`id`, `license_plate`, `vehicle_type_id`, `has_handicapped_card`, `entry_time`, `exit_time`, `created_at`, `updated_at`) VALUES
	(9, 'SAD', 3, 0, '2026-02-16 02:03:00', NULL, '2026-02-15 18:03:00', '2026-02-15 18:03:00'),
	(10, '123', 1, 0, '2026-02-16 02:09:53', '2026-02-16 06:10:26', '2026-02-15 18:09:53', '2026-02-15 22:10:26'),
	(11, '123', 1, 0, '2026-02-16 02:18:07', '2026-02-16 06:10:26', '2026-02-15 18:18:06', '2026-02-15 22:10:26'),
	(12, '123', 1, 1, '2026-02-16 02:20:41', '2026-02-16 06:10:26', '2026-02-15 18:20:40', '2026-02-15 22:10:26'),
	(13, '123', 5, 1, '2026-02-16 01:33:59', '2026-02-16 06:10:26', '2026-02-15 18:20:58', '2026-02-15 22:10:26'),
	(14, '123', 1, 0, '2026-02-16 02:29:34', '2026-02-16 06:10:26', '2026-02-15 18:29:33', '2026-02-15 22:10:26'),
	(15, '456', 5, 1, '2026-02-16 01:29:45', '2026-02-16 02:37:27', '2026-02-15 18:29:45', '2026-02-15 18:37:27'),
	(16, '789', 5, 1, '2026-02-16 01:29:55', '2026-02-16 02:37:35', '2026-02-15 18:29:54', '2026-02-15 18:37:35'),
	(17, '1233', 1, 0, '2026-02-16 03:46:48', NULL, '2026-02-15 19:46:47', '2026-02-15 19:46:47'),
	(18, 'TRUCK', 4, 0, '2026-02-16 03:46:59', '2026-02-16 03:47:24', '2026-02-15 19:46:59', '2026-02-15 19:47:23'),
	(19, 'H', 5, 1, '2026-02-16 03:47:10', '2026-02-16 03:47:15', '2026-02-15 19:47:09', '2026-02-15 19:47:15'),
	(20, '4566', 1, 1, '2026-02-16 06:04:14', NULL, '2026-02-15 22:04:13', '2026-02-15 22:04:13'),
	(21, '123', 2, 0, '2026-02-16 06:04:23', '2026-02-16 06:10:26', '2026-02-15 22:04:23', '2026-02-15 22:10:26'),
	(22, 'ASD', 3, 0, '2026-02-16 06:04:34', NULL, '2026-02-15 22:04:34', '2026-02-15 22:04:34'),
	(23, 'VCBCVB', 5, 0, '2026-02-16 06:05:02', NULL, '2026-02-15 22:05:02', '2026-02-15 22:05:02'),
	(24, 'A', 1, 0, '2026-02-16 06:10:08', '2026-02-16 06:14:53', '2026-02-15 22:10:08', '2026-02-15 22:14:53');

-- Dumping structure for table parking_system.vehicle_spot_rule
DROP TABLE IF EXISTS `vehicle_spot_rule`;
CREATE TABLE IF NOT EXISTS `vehicle_spot_rule` (
  `id` int NOT NULL AUTO_INCREMENT,
  `vehicle_type` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `spot_type` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table parking_system.vehicle_spot_rule: ~0 rows (approximately)
REPLACE INTO `vehicle_spot_rule` (`id`, `vehicle_type`, `spot_type`) VALUES
	(1, '1', 1),
	(2, '2', 1),
	(3, '2', 2),
	(4, '3', 2),
	(5, '4', 2),
	(6, '5', 1),
	(7, '5', 2),
	(8, '5', 3),
	(9, '5', 4);

-- Dumping structure for table parking_system.vehicle_type
DROP TABLE IF EXISTS `vehicle_type`;
CREATE TABLE IF NOT EXISTS `vehicle_type` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table parking_system.vehicle_type: ~5 rows (approximately)
REPLACE INTO `vehicle_type` (`id`, `name`, `created_at`, `updated_at`) VALUES
	(1, 'Motorcycle', '2026-01-29 17:50:13', '2026-01-29 17:50:13'),
	(2, 'Car', '2026-01-29 17:50:13', '2026-01-29 17:50:13'),
	(3, 'SUV', '2026-01-29 17:50:13', '2026-01-29 17:50:13'),
	(4, 'Truck', '2026-01-29 17:50:13', '2026-01-29 17:50:13'),
	(5, 'Handicapped', '2026-01-29 17:50:13', '2026-01-29 17:50:13');

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
