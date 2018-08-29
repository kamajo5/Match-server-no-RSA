-- phpMyAdmin SQL Dump
-- version 4.7.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Czas generowania: 04 Sty 2018, 21:17
-- Wersja serwera: 10.1.28-MariaDB
-- Wersja PHP: 7.1.11

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Baza danych: `cloud_database`
--

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `input_date`
--

CREATE TABLE `input_date` (
  `ID` int(11) NOT NULL,
  `ID_user` int(11) DEFAULT NULL,
  `Input_data` varchar(30) COLLATE utf8_polish_ci DEFAULT NULL,
  `Select_option` int(10) DEFAULT NULL,
  `Output_data` varchar(30) COLLATE utf8_polish_ci DEFAULT NULL,
  `Is_done` int(1) DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;

--
-- Zrzut danych tabeli `input_date`
--

INSERT INTO `input_date` (`ID`, `ID_user`, `Input_data`, `Select_option`, `Output_data`, `Is_done`) VALUES
(11, 1, '15+ 67', 1, '82', 1),
(164, 1, '2+89', 1, '91', 1);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `user`
--

CREATE TABLE `user` (
  `ID` int(11) NOT NULL,
  `Login` varchar(30) COLLATE utf8_polish_ci NOT NULL,
  `Password` varchar(40) COLLATE utf8_polish_ci NOT NULL,
  `Email` varchar(30) COLLATE utf8_polish_ci NOT NULL,
  `Token` varchar(10) COLLATE utf8_polish_ci DEFAULT NULL,
  `Activation` int(1) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;

--
-- Zrzut danych tabeli `user`
--

INSERT INTO `user` (`ID`, `Login`, `Password`, `Email`, `Token`, `Activation`) VALUES
(1, 'admin', '207023ccb44feb4d7dadca005ce29a64', 'xd', '74147', 1);

--
-- Indeksy dla zrzutów tabel
--

--
-- Indexes for table `input_date`
--
ALTER TABLE `input_date`
  ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`ID`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT dla tabeli `input_date`
--
ALTER TABLE `input_date`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=165;

--
-- AUTO_INCREMENT dla tabeli `user`
--
ALTER TABLE `user`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
