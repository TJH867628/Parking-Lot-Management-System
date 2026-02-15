-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               8.0.30 - MySQL Community Server - GPL
-- Server OS:                    Win64
-- HeidiSQL Version:             12.1.0.6537
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
CREATE DATABASE IF NOT EXISTS `parking_system`
DEFAULT CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;

-- Dumping structure for table parking_system.vehicle_type
CREATE TABLE IF NOT EXISTS `vehicle_type` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(30) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table parking_system.vehicle_type: ~5 rows (approximately)
INSERT INTO `vehicle_type` (`id`, `name`, `created_at`, `updated_at`) VALUES
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


-- Dumping structure for table parking_system.vehicle
CREATE TABLE IF NOT EXISTS `vehicle` (
  `id` int NOT NULL AUTO_INCREMENT,
  `license_plate` varchar(20) COLLATE utf8mb4_general_ci NOT NULL,
  `vehicle_type_id` int NOT NULL,
  `has_handicapped_card` tinyint(1) NOT NULL DEFAULT '0',
  `entry_time` datetime NOT NULL,
  `exit_time` datetime DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `vehicle_type_id` (`vehicle_type_id`),
  KEY `license_plate` (`license_plate`),
  CONSTRAINT `vehicle_vehicle_type_fk` FOREIGN KEY (`vehicle_type_id`) REFERENCES `vehicle_type` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table parking_system.vehicle: ~0 rows (approximately)


-- Dumping structure for table parking_system.parking_floor
CREATE TABLE IF NOT EXISTS `parking_floor` (
  `id` int NOT NULL AUTO_INCREMENT,
  `number` int NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table parking_system.parking_floor: ~5 rows (approximately)
INSERT INTO `parking_floor` (`id`, `number`, `created_at`, `updated_at`) VALUES
	(1, 1, '2026-01-29 17:22:23', '2026-01-29 17:22:23'),
	(2, 2, '2026-01-29 17:22:23', '2026-01-29 17:22:23'),
	(3, 3, '2026-01-29 17:22:23', '2026-01-29 17:22:23'),
	(4, 4, '2026-01-29 17:22:23', '2026-01-29 17:22:23'),
	(5, 5, '2026-01-29 17:22:23', '2026-01-29 17:22:23');


-- Dumping structure for table parking_system.parking_spot_type
CREATE TABLE IF NOT EXISTS `parking_spot_type` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `hourly_rate` double NOT NULL,
  `created_at` timestamp NOT NULL,
  `updated_at` timestamp NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table parking_system.parking_spot_type: ~4 rows (approximately)
INSERT INTO `parking_spot_type` (`id`, `name`, `hourly_rate`, `created_at`, `updated_at`) VALUES
	(1, 'compact', 2, '2026-01-29 17:13:47', '2026-01-29 17:13:47'),
	(2, 'regular', 5, '2026-01-29 17:13:47', '2026-01-29 17:13:47'),
	(3, 'handicapped', 2, '2026-01-29 17:13:47', '2026-01-29 17:13:47'),
	(4, 'reserved', 10, '2026-01-29 17:13:47', '2026-01-29 17:13:47');


-- Dumping structure for table parking_system.parking_spot
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
  KEY `floor_id` (`floor_id`),
  CONSTRAINT `floor_id` FOREIGN KEY (`floor_id`) REFERENCES `parking_floor` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=151 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table parking_system.parking_spot: ~100 rows (approximately)
INSERT INTO `parking_spot` (`id`, `row_number`, `floor_id`, `spot_number`, `type_id`, `status`, `current_vehicle`, `created_at`, `updated_at`) VALUES
	(51, 1, 1, 1, 1, 'available', NULL, '2026-01-29 18:38:37', '2026-01-29 18:38:37'),
	(52, 1, 1, 2, 1, 'available', NULL, '2026-01-29 18:38:37', '2026-01-29 18:38:37'),
	(53, 1, 1, 3, 1, 'available', NULL, '2026-01-29 18:38:37', '2026-01-29 18:38:37'),
	(54, 1, 1, 4, 1, 'available', NULL, '2026-01-29 18:38:37', '2026-01-29 18:38:37'),
	(55, 1, 1, 5, 1, 'available', NULL, '2026-01-29 18:38:37', '2026-01-29 18:38:37'),
	(56, 2, 1, 1, 2, 'available', NULL, '2026-01-29 18:38:37', '2026-01-29 18:38:37'),
	(57, 2, 1, 2, 2, 'available', NULL, '2026-01-29 18:38:37', '2026-01-29 18:38:37'),
	(58, 2, 1, 3, 2, 'available', NULL, '2026-01-29 18:38:37', '2026-01-29 18:38:37'),
	(59, 2, 1, 4, 2, 'available', NULL, '2026-01-29 18:38:37', '2026-01-29 18:38:37'),
	(60, 2, 1, 5, 2, 'available', NULL, '2026-01-29 18:38:37', '2026-01-29 18:38:37'),
	(61, 3, 1, 1, 2, 'available', NULL, '2026-01-29 18:38:37', '2026-01-29 18:38:37'),
	(62, 3, 1, 2, 2, 'available', NULL, '2026-01-29 18:38:37', '2026-01-29 18:38:37'),
	(63, 3, 1, 3, 2, 'available', NULL, '2026-01-29 18:38:37', '2026-01-29 18:38:37'),
	(64, 3, 1, 4, 2, 'available', NULL, '2026-01-29 18:38:37', '2026-01-29 18:38:37'),
	(65, 3, 1, 5, 2, 'available', NULL, '2026-01-29 18:38:37', '2026-01-29 18:38:37'),
	(66, 4, 1, 1, 4, 'available', NULL, '2026-01-29 18:38:37', '2026-01-29 18:38:37'),
	(67, 4, 1, 2, 4, 'available', NULL, '2026-01-29 18:38:37', '2026-01-29 18:38:37'),
	(68, 4, 1, 3, 4, 'available', NULL, '2026-01-29 18:38:37', '2026-01-29 18:38:37'),
	(69, 4, 1, 4, 3, 'available', NULL, '2026-01-29 18:38:37', '2026-01-29 18:38:37'),
	(70, 4, 1, 5, 3, 'available', NULL, '2026-01-29 18:38:37', '2026-01-29 18:38:37'),
	(71, 1, 2, 1, 1, 'available', NULL, '2026-01-29 18:38:37', '2026-01-29 18:38:37'),
	(72, 1, 2, 2, 1, 'available', NULL, '2026-01-29 18:38:37', '2026-01-29 18:38:37'),
	(73, 1, 2, 3, 1, 'available', NULL, '2026-01-29 18:38:37', '2026-01-29 18:38:37'),
	(74, 1, 2, 4, 1, 'available', NULL, '2026-01-29 18:38:37', '2026-01-29 18:38:37'),
	(75, 1, 2, 5, 1, 'available', NULL, '2026-01-29 18:38:37', '2026-01-29 18:38:37'),
	(76, 2, 2, 1, 2, 'available', NULL, '2026-01-29 18:38:37', '2026-01-29 18:38:37'),
	(77, 2, 2, 2, 2, 'available', NULL, '2026-01-29 18:38:37', '2026-01-29 18:38:37'),
	(78, 2, 2, 3, 2, 'available', NULL, '2026-01-29 18:38:37', '2026-01-29 18:38:37'),
	(79, 2, 2, 4, 2, 'available', NULL, '2026-01-29 18:38:37', '2026-01-29 18:38:37'),
	(80, 2, 2, 5, 2, 'available', NULL, '2026-01-29 18:38:37', '2026-01-29 18:38:37'),
	(81, 3, 2, 1, 2, 'available', NULL, '2026-01-29 18:38:37', '2026-01-29 18:38:37'),
	(82, 3, 2, 2, 2, 'available', NULL, '2026-01-29 18:38:37', '2026-01-29 18:38:37'),
	(83, 3, 2, 3, 2, 'available', NULL, '2026-01-29 18:38:37', '2026-01-29 18:38:37'),
	(84, 3, 2, 4, 2, 'available', NULL, '2026-01-29 18:38:37', '2026-01-29 18:38:37'),
	(85, 3, 2, 5, 2, 'available', NULL, '2026-01-29 18:38:37', '2026-01-29 18:38:37'),
	(86, 4, 2, 1, 4, 'available', NULL, '2026-01-29 18:38:37', '2026-01-29 18:38:37'),
	(87, 4, 2, 2, 4, 'available', NULL, '2026-01-29 18:38:37', '2026-01-29 18:38:37'),
	(88, 4, 2, 3, 4, 'available', NULL, '2026-01-29 18:38:37', '2026-01-29 18:38:37'),
	(89, 4, 2, 4, 3, 'available', NULL, '2026-01-29 18:38:37', '2026-01-29 18:38:37'),
	(90, 4, 2, 5, 3, 'available', NULL, '2026-01-29 18:38:37', '2026-01-29 18:38:37'),
	(91, 1, 3, 1, 1, 'available', NULL, '2026-01-29 18:38:37', '2026-01-29 18:38:37'),
	(92, 1, 3, 2, 1, 'available', NULL, '2026-01-29 18:38:37', '2026-01-29 18:38:37'),
	(93, 1, 3, 3, 1, 'available', NULL, '2026-01-29 18:38:37', '2026-01-29 18:38:37'),
	(94, 1, 3, 4, 1, 'available', NULL, '2026-01-29 18:38:37', '2026-01-29 18:38:37'),
	(95, 1, 3, 5, 1, 'available', NULL, '2026-01-29 18:38:37', '2026-01-29 18:38:37'),
	(96, 2, 3, 1, 2, 'available', NULL, '2026-01-29 18:38:37', '2026-01-29 18:38:37'),
	(97, 2, 3, 2, 2, 'available', NULL, '2026-01-29 18:38:37', '2026-01-29 18:38:37'),
	(98, 2, 3, 3, 2, 'available', NULL, '2026-01-29 18:38:37', '2026-01-29 18:38:37'),
	(99, 2, 3, 4, 2, 'available', NULL, '2026-01-29 18:38:37', '2026-01-29 18:38:37'),
	(100, 2, 3, 5, 2, 'available', NULL, '2026-01-29 18:38:37', '2026-01-29 18:38:37'),
	(101, 3, 3, 1, 2, 'available', NULL, '2026-01-29 18:38:37', '2026-01-29 18:38:37'),
	(102, 3, 3, 2, 2, 'available', NULL, '2026-01-29 18:38:37', '2026-01-29 18:38:37'),
	(103, 3, 3, 3, 2, 'available', NULL, '2026-01-29 18:38:37', '2026-01-29 18:38:37'),
	(104, 3, 3, 4, 2, 'available', NULL, '2026-01-29 18:38:37', '2026-01-29 18:38:37'),
	(105, 3, 3, 5, 2, 'available', NULL, '2026-01-29 18:38:37', '2026-01-29 18:38:37'),
	(106, 4, 3, 1, 4, 'available', NULL, '2026-01-29 18:38:37', '2026-01-29 18:38:37'),
	(107, 4, 3, 2, 4, 'available', NULL, '2026-01-29 18:38:37', '2026-01-29 18:38:37'),
	(108, 4, 3, 3, 4, 'available', NULL, '2026-01-29 18:38:37', '2026-01-29 18:38:37'),
	(109, 4, 3, 4, 3, 'available', NULL, '2026-01-29 18:38:37', '2026-01-29 18:38:37'),
	(110, 4, 3, 5, 3, 'available', NULL, '2026-01-29 18:38:37', '2026-01-29 18:38:37'),
	(111, 1, 4, 1, 1, 'available', NULL, '2026-01-29 18:38:37', '2026-01-29 18:38:37'),
	(112, 1, 4, 2, 1, 'available', NULL, '2026-01-29 18:38:37', '2026-01-29 18:38:37'),
	(113, 1, 4, 3, 1, 'available', NULL, '2026-01-29 18:38:37', '2026-01-29 18:38:37'),
	(114, 1, 4, 4, 1, 'available', NULL, '2026-01-29 18:38:37', '2026-01-29 18:38:37'),
	(115, 1, 4, 5, 1, 'available', NULL, '2026-01-29 18:38:37', '2026-01-29 18:38:37'),
	(116, 2, 4, 1, 2, 'available', NULL, '2026-01-29 18:38:37', '2026-01-29 18:38:37'),
	(117, 2, 4, 2, 2, 'available', NULL, '2026-01-29 18:38:37', '2026-01-29 18:38:37'),
	(118, 2, 4, 3, 2, 'available', NULL, '2026-01-29 18:38:37', '2026-01-29 18:38:37'),
	(119, 2, 4, 4, 2, 'available', NULL, '2026-01-29 18:38:37', '2026-01-29 18:38:37'),
	(120, 2, 4, 5, 2, 'available', NULL, '2026-01-29 18:38:37', '2026-01-29 18:38:37'),
	(121, 3, 4, 1, 2, 'available', NULL, '2026-01-29 18:38:37', '2026-01-29 18:38:37'),
	(122, 3, 4, 2, 2, 'available', NULL, '2026-01-29 18:38:37', '2026-01-29 18:38:37'),
	(123, 3, 4, 3, 2, 'available', NULL, '2026-01-29 18:38:37', '2026-01-29 18:38:37'),
	(124, 3, 4, 4, 2, 'available', NULL, '2026-01-29 18:38:37', '2026-01-29 18:38:37'),
	(125, 3, 4, 5, 2, 'available', NULL, '2026-01-29 18:38:37', '2026-01-29 18:38:37'),
	(126, 4, 4, 1, 4, 'available', NULL, '2026-01-29 18:38:37', '2026-01-29 18:38:37'),
	(127, 4, 4, 2, 4, 'available', NULL, '2026-01-29 18:38:37', '2026-01-29 18:38:37'),
	(128, 4, 4, 3, 4, 'available', NULL, '2026-01-29 18:38:37', '2026-01-29 18:38:37'),
	(129, 4, 4, 4, 3, 'available', NULL, '2026-01-29 18:38:37', '2026-01-29 18:38:37'),
	(130, 4, 4, 5, 3, 'available', NULL, '2026-01-29 18:38:37', '2026-01-29 18:38:37'),
	(131, 1, 5, 1, 1, 'available', NULL, '2026-01-29 18:38:37', '2026-01-29 18:38:37'),
	(132, 1, 5, 2, 1, 'available', NULL, '2026-01-29 18:38:37', '2026-01-29 18:38:37'),
	(133, 1, 5, 3, 1, 'available', NULL, '2026-01-29 18:38:37', '2026-01-29 18:38:37'),
	(134, 1, 5, 4, 1, 'available', NULL, '2026-01-29 18:38:37', '2026-01-29 18:38:37'),
	(135, 1, 5, 5, 1, 'available', NULL, '2026-01-29 18:38:37', '2026-01-29 18:38:37'),
	(136, 2, 5, 1, 2, 'available', NULL, '2026-01-29 18:38:37', '2026-01-29 18:38:37'),
	(137, 2, 5, 2, 2, 'available', NULL, '2026-01-29 18:38:37', '2026-01-29 18:38:37'),
	(138, 2, 5, 3, 2, 'available', NULL, '2026-01-29 18:38:37', '2026-01-29 18:38:37'),
	(139, 2, 5, 4, 2, 'available', NULL, '2026-01-29 18:38:37', '2026-01-29 18:38:37'),
	(140, 2, 5, 5, 2, 'available', NULL, '2026-01-29 18:38:37', '2026-01-29 18:38:37'),
	(141, 3, 5, 1, 2, 'available', NULL, '2026-01-29 18:38:37', '2026-01-29 18:38:37'),
	(142, 3, 5, 2, 2, 'available', NULL, '2026-01-29 18:38:37', '2026-01-29 18:38:37'),
	(143, 3, 5, 3, 2, 'available', NULL, '2026-01-29 18:38:37', '2026-01-29 18:38:37'),
	(144, 3, 5, 4, 2, 'available', NULL, '2026-01-29 18:38:37', '2026-01-29 18:38:37'),
	(145, 3, 5, 5, 2, 'available', NULL, '2026-01-29 18:38:37', '2026-01-29 18:38:37'),
	(146, 4, 5, 1, 4, 'available', NULL, '2026-01-29 18:38:37', '2026-01-29 18:38:37'),
	(147, 4, 5, 2, 4, 'available', NULL, '2026-01-29 18:38:37', '2026-01-29 18:38:37'),
	(148, 4, 5, 3, 4, 'available', NULL, '2026-01-29 18:38:37', '2026-01-29 18:38:37'),
	(149, 4, 5, 4, 3, 'available', NULL, '2026-01-29 18:38:37', '2026-01-29 18:38:37'),
	(150, 4, 5, 5, 3, 'available', NULL, '2026-01-29 18:38:37', '2026-01-29 18:38:37');


-- Dumping structure for table parking_system.ticket
CREATE TABLE IF NOT EXISTS `ticket` (
  `id` int NOT NULL AUTO_INCREMENT,
  `ticket_code` varchar(50) COLLATE utf8mb4_general_ci NOT NULL,
  `vehicle_id` int NOT NULL,
  `spot_id` int NOT NULL,
  `entry_time` datetime NOT NULL,
  `exit_time` datetime DEFAULT NULL,
  `status` varchar(20) COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'active',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `ticket_code` (`ticket_code`),
  KEY `vehicle_id` (`vehicle_id`),
  KEY `spot_id` (`spot_id`),
  CONSTRAINT `ticket_spot_fk` FOREIGN KEY (`spot_id`) REFERENCES `parking_spot` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `ticket_vehicle_fk` FOREIGN KEY (`vehicle_id`) REFERENCES `vehicle` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table parking_system.ticket: ~0 rows (approximately)


-- Dumping structure for table parking_system.payment
CREATE TABLE IF NOT EXISTS `payment` (
  `id` int NOT NULL AUTO_INCREMENT,
  `ticket_id` int NOT NULL,
  `amount` decimal(10,2) NOT NULL,
  `method` varchar(20) NOT NULL,
  `payment_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `ticket_id` (`ticket_id`),
  CONSTRAINT `payment_ibfk_1` FOREIGN KEY (`ticket_id`) REFERENCES `ticket` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Dumping data for table parking_system.payment: ~0 rows (approximately)


-- Dumping structure for table parking_system.fine
CREATE TABLE IF NOT EXISTS `fine` (
  `id` int NOT NULL AUTO_INCREMENT,
  `license_plate` varchar(20) NOT NULL,
  `amount` decimal(10,2) NOT NULL,
  `status` varchar(20) DEFAULT 'unpaid',
  `reason` varchar(100) DEFAULT NULL,
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Dumping data for table parking_system.fine: ~0 rows (approximately)



-- Dumping structure for table parking_system.vehicle_spot_rule
CREATE TABLE IF NOT EXISTS `vehicle_spot_rule` (
  `id` int NOT NULL AUTO_INCREMENT,
  `vehicle_type` varchar(30) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `spot_type` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table parking_system.vehicle_spot_rule: ~9 rows (approximately)
INSERT INTO `vehicle_spot_rule` (`id`, `vehicle_type`, `spot_type`) VALUES
	(1, '1', 1),
	(2, '2', 1),
	(3, '2', 2),
	(4, '3', 2),
	(5, '4', 2),
	(6, '5', 1),
	(7, '5', 2),
	(8, '5', 3),
	(9, '5', 4);

-- ----------------------------
-- 1. Create the fine_scheme table
-- ----------------------------
CREATE TABLE IF NOT EXISTS fine_scheme (
    id INT AUTO_INCREMENT PRIMARY KEY,
    scheme_type CHAR(1) NOT NULL,         -- A, B, or C
    base_amount DOUBLE DEFAULT 0,         -- Base fine (for Option A/B)
    additional_24_48 DOUBLE DEFAULT 0,    -- Additional fine for 24-48 hours (Option B)
    additional_48_72 DOUBLE DEFAULT 0,    -- Additional fine for 48-72 hours (Option B)
    above_72 DOUBLE DEFAULT 0,            -- Additional fine above 72 hours (Option B)
    hourly_rate DOUBLE DEFAULT 0,         -- Hourly rate for Option C
    is_active BOOLEAN DEFAULT FALSE       -- Admin selects active fine scheme
);

-- ----------------------------
-- 2. Insert default fine schemes
-- ----------------------------
-- Option A: Flat RM 50 fine
INSERT INTO fine_scheme (scheme_type, base_amount, additional_24_48, additional_48_72, above_72, hourly_rate, is_active)
VALUES ('A', 50, 0, 0, 0, 0, TRUE);

-- Option B: Progressive fine scheme
-- First 24h: RM 50
-- 24-48h: +RM 100
-- 48-72h: +RM 150
-- >72h: +RM 200
INSERT INTO fine_scheme (scheme_type, base_amount, additional_24_48, additional_48_72, above_72, hourly_rate, is_active)
VALUES ('B', 50, 100, 150, 200, 0, FALSE);

-- Option C: Hourly fine scheme
-- RM 20 per hour
INSERT INTO fine_scheme (scheme_type, base_amount, additional_24_48, additional_48_72, above_72, hourly_rate, is_active)
VALUES ('C', 0, 0, 0, 0, 20, FALSE);
