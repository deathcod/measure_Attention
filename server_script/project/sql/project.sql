-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Apr 12, 2017 at 03:01 PM
-- Server version: 10.1.9-MariaDB
-- PHP Version: 5.6.15

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `project`
--

-- --------------------------------------------------------

--
-- Table structure for table `card`
--

CREATE TABLE `card` (
  `score_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `score_1` int(11) NOT NULL,
  `time_1` int(11) NOT NULL,
  `score_2` int(11) NOT NULL,
  `time_2` int(11) NOT NULL,
  `score_3` int(11) NOT NULL,
  `time_3` int(11) NOT NULL,
  `score_4` int(11) NOT NULL,
  `time_4` int(11) NOT NULL,
  `datetime` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `card`
--

INSERT INTO `card` (`score_id`, `user_id`, `score_1`, `time_1`, `score_2`, `time_2`, `score_3`, `time_3`, `score_4`, `time_4`, `datetime`) VALUES
(1, 1, 16, 1055, 17, 1252, 11, 1215, 10, 1250, '2017-04-12 18:04:15');

-- --------------------------------------------------------

--
-- Table structure for table `chess`
--

CREATE TABLE `chess` (
  `score_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `score_1` int(11) NOT NULL,
  `time_1` int(11) NOT NULL,
  `score_2` int(11) NOT NULL,
  `time_2` int(11) NOT NULL,
  `score_3` int(11) NOT NULL,
  `time_3` int(11) NOT NULL,
  `score_4` int(11) NOT NULL,
  `time_4` int(11) NOT NULL,
  `datetime` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `chess`
--

INSERT INTO `chess` (`score_id`, `user_id`, `score_1`, `time_1`, `score_2`, `time_2`, `score_3`, `time_3`, `score_4`, `time_4`, `datetime`) VALUES
(1, 1, 16, 1055, 17, 1252, 11, 1215, 10, 1250, '2017-04-12 17:46:56');

-- --------------------------------------------------------

--
-- Table structure for table `stroop`
--

CREATE TABLE `stroop` (
  `score_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `score` int(11) NOT NULL,
  `time` double NOT NULL,
  `avg_time` double NOT NULL,
  `datetime` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `stroop`
--

INSERT INTO `stroop` (`score_id`, `user_id`, `score`, `time`, `avg_time`, `datetime`) VALUES
(1, 1, 9, 19.3, 1.93, '2017-04-12 14:12:46'),
(2, 1, 9, 19.3, 1.93, '2017-04-12 14:13:25');

-- --------------------------------------------------------

--
-- Table structure for table `test`
--

CREATE TABLE `test` (
  `score_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `score_0` int(11) NOT NULL,
  `time_0` int(11) NOT NULL,
  `score_1` int(11) NOT NULL,
  `time_1` int(11) NOT NULL,
  `score_2` int(11) NOT NULL,
  `time_2` int(11) NOT NULL,
  `score_3` int(11) NOT NULL,
  `time_3` int(11) NOT NULL,
  `score_4` int(11) NOT NULL,
  `time_4` int(11) NOT NULL,
  `datetime` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `test`
--

INSERT INTO `test` (`score_id`, `user_id`, `score_0`, `time_0`, `score_1`, `time_1`, `score_2`, `time_2`, `score_3`, `time_3`, `score_4`, `time_4`, `datetime`) VALUES
(1, 1, 12, 1255, 16, 1055, 17, 1252, 11, 1215, 10, 1250, '2017-04-12 17:13:42');

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `user_id` int(11) NOT NULL,
  `token` varchar(100) NOT NULL,
  `name` varchar(100) NOT NULL,
  `ip` varchar(100) NOT NULL,
  `datetime` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`user_id`, `token`, `name`, `ip`, `datetime`) VALUES
(1, 'c4ca4238a0b923820dcc509a6f75849b', 'Nazish Fraz', '192.168.1.110', '2017-04-12 13:43:58'),
(2, 'c81e728d9d4c2f636f067f89cc14862c', 'Nazish', '192.168.1.110', '2017-04-12 13:55:56');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `card`
--
ALTER TABLE `card`
  ADD PRIMARY KEY (`score_id`);

--
-- Indexes for table `chess`
--
ALTER TABLE `chess`
  ADD PRIMARY KEY (`score_id`);

--
-- Indexes for table `stroop`
--
ALTER TABLE `stroop`
  ADD PRIMARY KEY (`score_id`);

--
-- Indexes for table `test`
--
ALTER TABLE `test`
  ADD PRIMARY KEY (`score_id`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`user_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `card`
--
ALTER TABLE `card`
  MODIFY `score_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT for table `chess`
--
ALTER TABLE `chess`
  MODIFY `score_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT for table `stroop`
--
ALTER TABLE `stroop`
  MODIFY `score_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT for table `test`
--
ALTER TABLE `test`
  MODIFY `score_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `user_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
