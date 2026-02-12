-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: Jan 30, 2026 at 08:25 AM
-- Server version: 9.2.0
-- PHP Version: 8.2.4

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
(1, 1, '2026-01-29 17:22:23', '2026-01-29 17:22:23'),
(2, 2, '2026-01-29 17:22:23', '2026-01-29 17:22:23'),
(3, 3, '2026-01-29 17:22:23', '2026-01-29 17:22:23'),
(4, 4, '2026-01-29 17:22:23', '2026-01-29 17:22:23'),
(5, 5, '2026-01-29 17:22:23', '2026-01-29 17:22:23');

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
(51, 1, 1, 1, 1, 'parked', 'JNX6383', '2026-01-29 18:38:37', '2026-01-29 18:38:37'),
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

-- --------------------------------------------------------

--
-- Table structure for table `parking_spot_type`
--

CREATE TABLE `parking_spot_type` (
  `id` int NOT NULL,
  `name` varchar(255) NOT NULL,
  `hourly_rate` double NOT NULL,
  `created_at` timestamp NOT NULL,
  `updated_at` timestamp NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `parking_spot_type`
--

INSERT INTO `parking_spot_type` (`id`, `name`, `hourly_rate`, `created_at`, `updated_at`) VALUES
(1, 'compact', 2, '2026-01-29 17:13:47', '2026-01-29 17:13:47'),
(2, 'regular', 5, '2026-01-29 17:13:47', '2026-01-29 17:13:47'),
(3, 'handicapped', 2, '2026-01-29 17:13:47', '2026-01-29 17:13:47'),
(4, 'reserved', 10, '2026-01-29 17:13:47', '2026-01-29 17:13:47');

-- --------------------------------------------------------

--
-- Table structure for table `vehicle_spot_rule`
--

CREATE TABLE `vehicle_spot_rule` (
  `id` int NOT NULL,
  `vehicle_type` varchar(30) DEFAULT NULL,
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
  `name` varchar(30) DEFAULT NULL,
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
-- AUTO_INCREMENT for table `vehicle_spot_rule`
--
ALTER TABLE `vehicle_spot_rule`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT for table `vehicle_type`
--
ALTER TABLE `vehicle_type`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `parking_spot`
--
ALTER TABLE `parking_spot`
  ADD CONSTRAINT `floor_id` FOREIGN KEY (`floor_id`) REFERENCES `parking_floor` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

