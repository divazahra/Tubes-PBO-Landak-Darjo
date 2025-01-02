-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: Jan 02, 2025 at 08:51 AM
-- Server version: 8.0.36
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `tubespbo`
--

-- --------------------------------------------------------

--
-- Table structure for table `datarental`
--

CREATE TABLE `datarental` (
  `dataRentalId` int NOT NULL,
  `namaKustomer` varchar(255) DEFAULT NULL,
  `psId` varchar(255) DEFAULT NULL,
  `jam` int DEFAULT NULL,
  `extraController` int NOT NULL,
  `extraVRDevice` enum('iya','tidak') NOT NULL DEFAULT 'tidak',
  `totalHarga` double DEFAULT NULL,
  `userId` varchar(255) NOT NULL,
  `tanggal` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `datarental`
--

INSERT INTO `datarental` (`dataRentalId`, `namaKustomer`, `psId`, `jam`, `extraController`, `extraVRDevice`, `totalHarga`, `userId`, `tanggal`) VALUES
(1, 'dva', 'PS4-03', 2, 2, 'tidak', 40000, '4', '2024-12-30'),
(2, 'atip', 'PS3-02', 5, 0, 'tidak', 25000, '4', '2024-12-30'),
(4, 'dank', 'PS5-03', 5, 1, 'iya', 99000, '4', '2024-12-30'),
(5, 'pen', 'PS5-01', 4, 1, 'tidak', 67000, '4', '2024-12-31'),
(6, 'dankdonk', 'PS5-03', 5, 1, 'iya', 92000, '4', '2024-12-31'),
(7, 'dvazahr', 'PS4-02', 2, 1, 'tidak', 25000, '4', '2024-12-31'),
(8, 'L Monk', 'PS4-03', 4, 2, 'tidak', 50000, '4', '2024-12-31'),
(9, 'diva', 'PS3-01', 10, 0, 'tidak', 50000, '4', '2024-12-31'),
(10, 'zahra', 'PS4-02', 15, 2, 'tidak', 160000, '4', '2024-12-31'),
(11, 'fahira', 'PS5-04', 24, 1, 'iya', 377000, '4', '2024-12-31'),
(12, 'suci', 'PS5-01', 2, 0, 'tidak', 30000, '4', '2024-12-31'),
(13, 'suci', 'PS5-01', 3, 2, 'tidak', 59000, '4', '2024-12-31'),
(14, 'alfi', 'PS5-01', 4, 0, 'iya', 70000, '6', '2024-12-31'),
(15, 'a', 'PS5-02', 4, 2, 'iya', 84000, '4', '2025-01-01'),
(16, 'kiel', 'PS5-03', 120, 2, 'iya', 1824000, '4', '2025-01-01'),
(17, 'k', 'PS3-02', 1, 0, 'tidak', 5000, '4', '2025-01-02'),
(18, 'User', 'PS3-05', 2, 0, 'tidak', 10000, '9', '2025-01-02'),
(19, 'Cobaa', 'PS4-06', 2, 2, 'tidak', 30000, '9', '2025-01-02');

-- --------------------------------------------------------

--
-- Table structure for table `ps`
--

CREATE TABLE `ps` (
  `id` varchar(255) NOT NULL,
  `type` varchar(50) DEFAULT NULL,
  `extraController` int DEFAULT '0',
  `extraVRDevice` enum('iya','tidak') DEFAULT 'tidak',
  `status` enum('available','not available') NOT NULL,
  `userId` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `ps`
--

INSERT INTO `ps` (`id`, `type`, `extraController`, `extraVRDevice`, `status`, `userId`) VALUES
('PS3-01', 'PS3', 0, 'tidak', 'not available', '4'),
('PS3-02', 'PS3', 0, 'tidak', 'available', '4'),
('PS3-03', 'PS3', 2, 'iya', 'available', '4'),
('PS3-05', 'PS3', 2, 'iya', 'available', '9'),
('PS4-01', 'PS4', 2, 'tidak', 'available', '4'),
('PS4-02', 'PS4', 2, 'tidak', 'not available', '4'),
('PS4-03', 'PS4', 2, 'iya', 'available', '4'),
('PS4-06', 'PS4', 2, 'iya', 'not available', '9'),
('PS5-01', 'PS5', 2, 'iya', 'not available', '4'),
('PS5-01', 'PS5', 2, 'iya', 'not available', '6'),
('PS5-02', 'PS5', 2, 'iya', 'not available', '4'),
('PS5-03', 'PS5', 2, 'iya', 'not available', '4'),
('PS5-04', 'PS5', 2, 'iya', 'not available', '4');

-- --------------------------------------------------------

--
-- Table structure for table `rental`
--

CREATE TABLE `rental` (
  `rentalId` int NOT NULL,
  `namaKustomer` varchar(255) DEFAULT NULL,
  `psId` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `jam` int DEFAULT NULL,
  `extraController` int NOT NULL,
  `extraVRDevice` enum('iya','tidak') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'tidak',
  `totalHarga` double DEFAULT NULL,
  `userId` varchar(255) NOT NULL,
  `tanggal` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `rental`
--

INSERT INTO `rental` (`rentalId`, `namaKustomer`, `psId`, `jam`, `extraController`, `extraVRDevice`, `totalHarga`, `userId`, `tanggal`) VALUES
(15, 'diva', 'PS3-01', 10, 0, 'tidak', 50000, '4', '2024-12-31'),
(16, 'zahra', 'PS4-02', 15, 2, 'tidak', 160000, '4', '2024-12-31'),
(17, 'fahira', 'PS5-04', 24, 1, 'iya', 377000, '4', '2024-12-31'),
(19, 'suci', 'PS5-01', 3, 2, 'tidak', 59000, '4', '2024-12-31'),
(20, 'alfi', 'PS5-01', 4, 0, 'iya', 70000, '6', '2024-12-31'),
(21, 'a', 'PS5-02', 4, 2, 'iya', 84000, '4', '2025-01-01'),
(22, 'kiel', 'PS5-03', 120, 2, 'iya', 1824000, '4', '2025-01-01'),
(25, 'Cobaa', 'PS4-06', 2, 2, 'tidak', 30000, '9', '2025-01-02');

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `userId` int NOT NULL,
  `username` varchar(100) NOT NULL,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`userId`, `username`, `email`, `password`) VALUES
(4, 'fahira', 'dvazahr31@gmail.com', 'fahira'),
(6, 'vlektip', 'atipnyaecin@gmail.com', 'atipganteng'),
(8, 'suci', 'sucilemon@gmail.com', 'lemon'),
(9, 'test1', 'test1@gmail.com', 'test1');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `datarental`
--
ALTER TABLE `datarental`
  ADD PRIMARY KEY (`dataRentalId`);

--
-- Indexes for table `ps`
--
ALTER TABLE `ps`
  ADD PRIMARY KEY (`id`,`userId`);

--
-- Indexes for table `rental`
--
ALTER TABLE `rental`
  ADD PRIMARY KEY (`rentalId`),
  ADD KEY `psId` (`psId`),
  ADD KEY `userId` (`userId`),
  ADD KEY `fk_ps_rental` (`psId`,`userId`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`userId`),
  ADD UNIQUE KEY `username` (`username`),
  ADD UNIQUE KEY `email` (`email`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `datarental`
--
ALTER TABLE `datarental`
  MODIFY `dataRentalId` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;

--
-- AUTO_INCREMENT for table `rental`
--
ALTER TABLE `rental`
  MODIFY `rentalId` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=26;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `userId` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `rental`
--
ALTER TABLE `rental`
  ADD CONSTRAINT `fk_ps_rental` FOREIGN KEY (`psId`,`userId`) REFERENCES `ps` (`id`, `userId`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
