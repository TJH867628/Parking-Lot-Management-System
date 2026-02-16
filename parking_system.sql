-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Feb 16, 2026 at 09:15 AM
-- Server version: 8.0.30
-- PHP Version: 8.2.0

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `parking_system`
--

-- --------------------------------------------------------

--
-- Table structure for table `fine`
--

CREATE TABLE `fine` (
  `id` int NOT NULL,
  `license_plate` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `amount` decimal(10,2) NOT NULL,
  `status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT 'unpaid',
  `reason` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `fine`
--

INSERT INTO `fine` (`id`, `license_plate`, `amount`, `status`, `reason`, `created_at`) VALUES
(5, '1233', 280.00, 'paid', 'Overstayed parking duration', '2026-02-16 04:46:45'),
(8, 'TTC', 50.00, 'paid', 'Overstayed parking duration', '2026-02-16 17:02:30'),
(9, 'ASD', 150.00, 'paid', 'Overstayed parking duration', '2026-02-16 17:04:27'),
(10, '4566', 150.00, 'paid', 'Overstayed parking duration', '2026-02-16 17:09:38');

-- --------------------------------------------------------

--
-- Table structure for table `fine_scheme`
--

CREATE TABLE `fine_scheme` (
  `id` int NOT NULL,
  `scheme_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '0',
  `base_amount` double DEFAULT '0',
  `additional_24_48` double DEFAULT '0',
  `additional_48_72` double DEFAULT '0',
  `above_72` double DEFAULT '0',
  `hourly_rate` double DEFAULT '0',
  `is_active` tinyint(1) DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `fine_scheme`
--

INSERT INTO `fine_scheme` (`id`, `scheme_type`, `base_amount`, `additional_24_48`, `additional_48_72`, `above_72`, `hourly_rate`, `is_active`) VALUES
(1, 'Flat Rate', 50, 0, 0, 0, 0, 0),
(2, 'Per Hour', 50, 100, 150, 200, 0, 1),
(3, 'Progressive', 0, 0, 0, 0, 20, 0);

-- --------------------------------------------------------

--
-- Table structure for table `parking_floor`
--

CREATE TABLE `parking_floor` (
  `id` int NOT NULL,
  `number` int NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `parking_floor`
--

INSERT INTO `parking_floor` (`id`, `number`, `created_at`, `updated_at`) VALUES
(1, 1, '2026-01-29 09:22:23', '2026-01-29 09:22:23'),
(2, 2, '2026-01-29 09:22:23', '2026-01-29 09:22:23'),
(3, 3, '2026-01-29 09:22:23', '2026-01-29 09:22:23'),
(4, 4, '2026-01-29 09:22:23', '2026-01-29 09:22:23'),
(5, 5, '2026-01-29 09:22:23', '2026-01-29 09:22:23');

-- --------------------------------------------------------

--
-- Table structure for table `parking_spot`
--

CREATE TABLE `parking_spot` (
  `id` int NOT NULL,
  `row_number` int DEFAULT NULL,
  `floor_id` int DEFAULT NULL,
  `spot_number` int DEFAULT NULL,
  `type_id` int DEFAULT NULL,
  `status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `current_vehicle` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `parking_spot`
--

INSERT INTO `parking_spot` (`id`, `row_number`, `floor_id`, `spot_number`, `type_id`, `status`, `current_vehicle`, `created_at`, `updated_at`) VALUES
(51, 1, 1, 1, 1, 'available', NULL, '2026-01-29 10:38:37', '2026-01-29 10:38:37'),
(52, 1, 1, 2, 1, 'available', NULL, '2026-01-29 10:38:37', '2026-01-29 10:38:37'),
(53, 1, 1, 3, 1, 'available', NULL, '2026-01-29 10:38:37', '2026-01-29 10:38:37'),
(54, 1, 1, 4, 1, 'available', NULL, '2026-01-29 10:38:37', '2026-01-29 10:38:37'),
(55, 1, 1, 5, 1, 'available', NULL, '2026-01-29 10:38:37', '2026-01-29 10:38:37'),
(56, 2, 1, 1, 2, 'available', NULL, '2026-01-29 10:38:37', '2026-01-29 10:38:37'),
(57, 2, 1, 2, 2, 'occupied', 'JMW927', '2026-01-29 10:38:37', '2026-01-29 10:38:37'),
(58, 2, 1, 3, 2, 'available', NULL, '2026-01-29 10:38:37', '2026-01-29 10:38:37'),
(59, 2, 1, 4, 2, 'available', NULL, '2026-01-29 10:38:37', '2026-01-29 10:38:37'),
(60, 2, 1, 5, 2, 'available', NULL, '2026-01-29 10:38:37', '2026-01-29 10:38:37'),
(61, 3, 1, 1, 2, 'available', NULL, '2026-01-29 10:38:37', '2026-01-29 10:38:37'),
(62, 3, 1, 2, 2, 'available', NULL, '2026-01-29 10:38:37', '2026-01-29 10:38:37'),
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
(149, 4, 5, 4, 3, 'occupied', 'WWWW', '2026-01-29 10:38:37', '2026-01-29 10:38:37'),
(150, 4, 5, 5, 3, 'available', NULL, '2026-01-29 10:38:37', '2026-01-29 10:38:37');

-- --------------------------------------------------------

--
-- Table structure for table `parking_spot_type`
--

CREATE TABLE `parking_spot_type` (
  `id` int NOT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `hourly_rate` double NOT NULL,
  `created_at` timestamp NOT NULL,
  `updated_at` timestamp NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `parking_spot_type`
--

INSERT INTO `parking_spot_type` (`id`, `name`, `hourly_rate`, `created_at`, `updated_at`) VALUES
(1, 'compact', 2, '2026-01-29 09:13:47', '2026-01-29 09:13:47'),
(2, 'regular', 5, '2026-01-29 09:13:47', '2026-01-29 09:13:47'),
(3, 'handicapped', 2, '2026-01-29 09:13:47', '2026-01-29 09:13:47'),
(4, 'reserved', 10, '2026-01-29 09:13:47', '2026-01-29 09:13:47');

-- --------------------------------------------------------

--
-- Table structure for table `payment`
--

CREATE TABLE `payment` (
  `id` int NOT NULL,
  `ticket_id` int NOT NULL,
  `amount` decimal(10,2) NOT NULL,
  `method` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `payment_time` datetime DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `payment`
--

INSERT INTO `payment` (`id`, `ticket_id`, `amount`, `method`, `payment_time`) VALUES
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
(25, 24, 2.00, 'Cash', '2026-02-16 06:14:53'),
(26, 29, 2.00, 'Cash', '2026-02-16 16:18:26'),
(27, 25, 2.00, 'Cash', '2026-02-16 16:21:31'),
(28, 26, 5.00, 'Cash', '2026-02-16 16:24:25'),
(29, 30, 0.00, 'Cash', '2026-02-16 16:26:42'),
(31, 17, 356.00, 'Card', '2026-02-16 16:56:04'),
(32, 27, 295.00, 'Card', '2026-02-16 17:03:06'),
(33, 22, 225.00, 'Card', '2026-02-16 17:05:08'),
(34, 20, 222.00, 'Card', '2026-02-16 17:10:07');

-- --------------------------------------------------------

--
-- Table structure for table `ticket`
--

CREATE TABLE `ticket` (
  `id` int NOT NULL,
  `ticket_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `vehicle_id` int NOT NULL,
  `spot_id` int NOT NULL,
  `entry_time` datetime NOT NULL,
  `exit_time` datetime DEFAULT NULL,
  `status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'active',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `ticket`
--

INSERT INTO `ticket` (`id`, `ticket_code`, `vehicle_id`, `spot_id`, `entry_time`, `exit_time`, `status`, `created_at`, `updated_at`) VALUES
(9, 'T-SAD-20260216020300', 9, 60, '2026-02-16 02:03:00', '2026-02-16 02:03:14', 'paid', '2026-02-15 18:03:00', '2026-02-15 20:48:56'),
(10, 'T-123-20260216020953', 10, 132, '2026-02-16 02:09:53', '2026-02-16 02:10:00', 'paid', '2026-02-15 18:09:53', '2026-02-15 20:49:01'),
(11, 'T-123-20260216021806', 11, 53, '2026-02-16 02:18:07', '2026-02-16 02:18:19', 'paid', '2026-02-15 18:18:06', '2026-02-15 20:49:01'),
(12, 'T-123-20260216022040', 12, 53, '2026-02-16 02:20:41', '2026-02-16 02:20:45', 'paid', '2026-02-15 18:20:40', '2026-02-15 20:49:00'),
(13, 'T-123-20260216022058', 13, 59, '2026-02-16 01:30:59', '2026-02-16 02:28:28', 'paid', '2026-02-15 18:20:58', '2026-02-15 20:49:00'),
(14, 'T-123-20260216022933', 14, 55, '2026-02-16 02:29:34', '2026-02-16 03:47:29', 'paid', '2026-02-15 18:29:33', '2026-02-15 20:48:59'),
(15, 'T-456-20260216022945', 15, 69, '2026-02-16 01:29:45', '2026-02-16 02:37:27', 'paid', '2026-02-15 18:29:45', '2026-02-15 20:48:59'),
(16, 'T-789-20260216022954', 16, 59, '2026-02-16 01:29:55', '2026-02-16 02:37:35', 'paid', '2026-02-15 18:29:54', '2026-02-15 20:48:59'),
(17, 'T-1233-20260216034647', 17, 52, '2026-02-15 03:46:48', '2026-02-16 16:56:04', 'paid', '2026-02-15 19:46:47', '2026-02-16 08:56:04'),
(18, 'T-TRUCK-20260216034659', 18, 65, '2026-02-16 03:46:59', '2026-02-16 03:47:23', 'paid', '2026-02-15 19:46:59', '2026-02-15 20:49:02'),
(19, 'T-H-20260216034709', 19, 69, '2026-02-16 03:47:10', '2026-02-16 03:47:15', 'paid', '2026-02-15 19:47:09', '2026-02-15 20:49:02'),
(20, 'T-4566-20260216060413', 20, 55, '2026-02-15 06:04:14', '2026-02-16 17:10:07', 'paid', '2026-02-15 22:04:13', '2026-02-16 09:10:07'),
(21, 'T-123-20260216060423', 21, 58, '2026-02-16 06:04:23', '2026-02-16 06:10:26', 'paid', '2026-02-15 22:04:23', '2026-02-15 22:10:26'),
(22, 'T-ASD-20260216060434', 22, 62, '2026-02-15 06:04:34', '2026-02-16 17:05:08', 'paid', '2026-02-15 22:04:34', '2026-02-16 09:05:08'),
(23, 'T-VCBCVB-20260216060502', 23, 64, '2026-02-13 06:05:02', NULL, 'active', '2026-02-15 22:05:02', '2026-02-16 09:13:02'),
(24, 'T-A-20260216061008', 24, 54, '2026-02-16 06:10:08', '2026-02-16 06:14:53', 'paid', '2026-02-15 22:10:08', '2026-02-15 22:14:53'),
(25, 'T-JMW927-20260216160325', 25, 53, '2026-02-16 16:03:25', '2026-02-16 16:21:31', 'paid', '2026-02-16 08:03:25', '2026-02-16 08:21:31'),
(26, 'T-ABC-20260216160450', 26, 58, '2026-02-16 16:04:51', '2026-02-16 16:24:25', 'paid', '2026-02-16 08:04:50', '2026-02-16 08:24:25'),
(27, 'T-TTC-20260216160902', 27, 57, '2026-02-14 16:09:03', '2026-02-16 17:03:06', 'paid', '2026-02-16 08:09:02', '2026-02-16 09:03:06'),
(28, 'T-WWWW-20260216161129', 28, 149, '2026-02-16 16:11:30', NULL, 'active', '2026-02-16 08:11:29', '2026-02-16 08:11:29'),
(29, 'T-TEST-20260216161203', 29, 69, '2026-02-16 16:12:04', '2026-02-16 16:18:26', 'paid', '2026-02-16 08:12:03', '2026-02-16 08:18:26'),
(30, 'T-HANDICAPPED-20260216161919', 30, 69, '2026-02-16 16:19:20', '2026-02-16 16:26:42', 'paid', '2026-02-16 08:19:19', '2026-02-16 08:26:42'),
(31, 'T-JMW927-20260216171339', 31, 57, '2026-02-14 17:13:39', NULL, 'active', '2026-02-16 09:13:39', '2026-02-16 09:13:51');

-- --------------------------------------------------------

--
-- Table structure for table `vehicle`
--

CREATE TABLE `vehicle` (
  `id` int NOT NULL,
  `license_plate` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `vehicle_type_id` int NOT NULL,
  `has_handicapped_card` tinyint(1) NOT NULL DEFAULT '0',
  `entry_time` datetime NOT NULL,
  `exit_time` datetime DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `vehicle`
--

INSERT INTO `vehicle` (`id`, `license_plate`, `vehicle_type_id`, `has_handicapped_card`, `entry_time`, `exit_time`, `created_at`, `updated_at`) VALUES
(9, 'SAD', 3, 0, '2026-02-16 02:03:00', NULL, '2026-02-15 18:03:00', '2026-02-15 18:03:00'),
(10, '123', 1, 0, '2026-02-16 02:09:53', '2026-02-16 06:10:26', '2026-02-15 18:09:53', '2026-02-15 22:10:26'),
(11, '123', 1, 0, '2026-02-16 02:18:07', '2026-02-16 06:10:26', '2026-02-15 18:18:06', '2026-02-15 22:10:26'),
(12, '123', 1, 1, '2026-02-16 02:20:41', '2026-02-16 06:10:26', '2026-02-15 18:20:40', '2026-02-15 22:10:26'),
(13, '123', 5, 1, '2026-02-16 01:33:59', '2026-02-16 06:10:26', '2026-02-15 18:20:58', '2026-02-15 22:10:26'),
(14, '123', 1, 0, '2026-02-16 02:29:34', '2026-02-16 06:10:26', '2026-02-15 18:29:33', '2026-02-15 22:10:26'),
(15, '456', 5, 1, '2026-02-16 01:29:45', '2026-02-16 02:37:27', '2026-02-15 18:29:45', '2026-02-15 18:37:27'),
(16, '789', 5, 1, '2026-02-16 01:29:55', '2026-02-16 02:37:35', '2026-02-15 18:29:54', '2026-02-15 18:37:35'),
(17, '1233', 1, 0, '2026-02-16 03:46:48', '2026-02-16 16:56:04', '2026-02-15 19:46:47', '2026-02-16 08:56:04'),
(18, 'TRUCK', 4, 0, '2026-02-16 03:46:59', '2026-02-16 03:47:24', '2026-02-15 19:46:59', '2026-02-15 19:47:23'),
(19, 'H', 5, 1, '2026-02-16 03:47:10', '2026-02-16 03:47:15', '2026-02-15 19:47:09', '2026-02-15 19:47:15'),
(20, '4566', 1, 1, '2026-02-15 06:04:14', '2026-02-16 17:10:08', '2026-02-15 22:04:13', '2026-02-16 09:10:07'),
(21, '123', 2, 0, '2026-02-16 06:04:23', '2026-02-16 06:10:26', '2026-02-15 22:04:23', '2026-02-15 22:10:26'),
(22, 'ASD', 3, 0, '2026-02-15 06:04:34', '2026-02-16 17:05:08', '2026-02-15 22:04:34', '2026-02-16 09:05:08'),
(23, 'VCBCVB', 5, 0, '2026-02-13 06:05:02', NULL, '2026-02-15 22:05:02', '2026-02-16 09:12:55'),
(24, 'A', 1, 0, '2026-02-16 06:10:08', '2026-02-16 06:14:53', '2026-02-15 22:10:08', '2026-02-15 22:14:53'),
(25, 'JMW927', 1, 0, '2026-02-16 16:03:25', '2026-02-16 16:21:32', '2026-02-16 08:03:25', '2026-02-16 08:21:31'),
(26, 'ABC', 2, 0, '2026-02-16 16:04:51', '2026-02-16 16:24:25', '2026-02-16 08:04:50', '2026-02-16 08:24:25'),
(27, 'TTC', 3, 0, '2026-02-14 16:09:03', '2026-02-16 17:03:06', '2026-02-16 08:09:02', '2026-02-16 09:03:06'),
(28, 'WWWW', 5, 0, '2026-02-16 16:11:30', NULL, '2026-02-16 08:11:29', '2026-02-16 08:11:29'),
(29, 'TEST', 5, 0, '2026-02-16 16:12:04', '2026-02-16 16:18:27', '2026-02-16 08:12:03', '2026-02-16 08:18:26'),
(30, 'HANDICAPPED', 5, 1, '2026-02-16 16:19:20', '2026-02-16 16:26:43', '2026-02-16 08:19:19', '2026-02-16 08:26:42'),
(31, 'JMW927', 2, 0, '2026-02-14 17:13:39', NULL, '2026-02-16 09:13:39', '2026-02-16 09:13:59');

-- --------------------------------------------------------

--
-- Table structure for table `vehicle_spot_rule`
--

CREATE TABLE `vehicle_spot_rule` (
  `id` int NOT NULL,
  `vehicle_type` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `spot_type` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `vehicle_spot_rule`
--

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

-- --------------------------------------------------------

--
-- Table structure for table `vehicle_type`
--

CREATE TABLE `vehicle_type` (
  `id` int NOT NULL,
  `name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `vehicle_type`
--

INSERT INTO `vehicle_type` (`id`, `name`, `created_at`, `updated_at`) VALUES
(1, 'Motorcycle', '2026-01-29 17:50:13', '2026-01-29 17:50:13'),
(2, 'Car', '2026-01-29 17:50:13', '2026-01-29 17:50:13'),
(3, 'SUV', '2026-01-29 17:50:13', '2026-01-29 17:50:13'),
(4, 'Truck', '2026-01-29 17:50:13', '2026-01-29 17:50:13'),
(5, 'Handicapped', '2026-01-29 17:50:13', '2026-01-29 17:50:13');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `fine`
--
ALTER TABLE `fine`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `fine_scheme`
--
ALTER TABLE `fine_scheme`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `parking_floor`
--
ALTER TABLE `parking_floor`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `parking_spot`
--
ALTER TABLE `parking_spot`
  ADD PRIMARY KEY (`id`),
  ADD KEY `floor_id` (`floor_id`);

--
-- Indexes for table `parking_spot_type`
--
ALTER TABLE `parking_spot_type`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `payment`
--
ALTER TABLE `payment`
  ADD PRIMARY KEY (`id`),
  ADD KEY `ticket_id` (`ticket_id`);

--
-- Indexes for table `ticket`
--
ALTER TABLE `ticket`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `ticket_code` (`ticket_code`),
  ADD KEY `vehicle_id` (`vehicle_id`),
  ADD KEY `spot_id` (`spot_id`);

--
-- Indexes for table `vehicle`
--
ALTER TABLE `vehicle`
  ADD PRIMARY KEY (`id`),
  ADD KEY `vehicle_type_id` (`vehicle_type_id`),
  ADD KEY `license_plate` (`license_plate`);

--
-- Indexes for table `vehicle_spot_rule`
--
ALTER TABLE `vehicle_spot_rule`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `vehicle_type`
--
ALTER TABLE `vehicle_type`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `name` (`name`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `fine`
--
ALTER TABLE `fine`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT for table `fine_scheme`
--
ALTER TABLE `fine_scheme`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `parking_floor`
--
ALTER TABLE `parking_floor`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `parking_spot`
--
ALTER TABLE `parking_spot`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=151;

--
-- AUTO_INCREMENT for table `parking_spot_type`
--
ALTER TABLE `parking_spot_type`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `payment`
--
ALTER TABLE `payment`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=35;

--
-- AUTO_INCREMENT for table `ticket`
--
ALTER TABLE `ticket`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=32;

--
-- AUTO_INCREMENT for table `vehicle`
--
ALTER TABLE `vehicle`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=32;

--
-- AUTO_INCREMENT for table `vehicle_spot_rule`
--
ALTER TABLE `vehicle_spot_rule`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT for table `vehicle_type`
--
ALTER TABLE `vehicle_type`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
