-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Gép: 127.0.0.1:3306
-- Létrehozás ideje: 2022. Ápr 18. 11:56
-- Kiszolgáló verziója: 10.5.12-MariaDB-cll-lve
-- PHP verzió: 7.2.34

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Adatbázis: `u121374417_jatekdoboz`
--
CREATE DATABASE IF NOT EXISTS `u121374417_jatekdoboz` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE `u121374417_jatekdoboz`;

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `hirek`
--

DROP TABLE IF EXISTS `hirek`;
CREATE TABLE `hirek` (
  `id` int(11) NOT NULL,
  `hircim` varchar(30) COLLATE utf8mb4_unicode_ci NOT NULL,
  `datum` varchar(30) COLLATE utf8mb4_unicode_ci NOT NULL,
  `hir` varchar(1000) COLLATE utf8mb4_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- A tábla adatainak kiíratása `hirek`
--

INSERT INTO `hirek` (`id`, `hircim`, `datum`, `hir`) VALUES
(1, 'Próba', '2022.04.05', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aliquam ac nulla nec libero porta convallis eu vitae risus. Nulla quis aliquam lacus, dapibus tincidunt ligula. Maecenas justo nibh, consectetur nec nisi eleifend, posuere bibendum libero.\n'),
(2, 'Telefonról feltöltve', '2022.04.07', 'Lorem ipsum dolor sit amet, consetetur sadipscing eltir, sed diam nonumy\n\nés szerkesztve\n');

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `jatekok3`
--

DROP TABLE IF EXISTS `jatekok3`;
CREATE TABLE `jatekok3` (
  `id` int(11) NOT NULL,
  `jateknev` varchar(30) NOT NULL,
  `kategoria` varchar(30) NOT NULL,
  `korhatar` varchar(11) NOT NULL,
  `varos` varchar(30) NOT NULL,
  `leiras` varchar(255) NOT NULL,
  `datum` date NOT NULL,
  `telefon` int(11) NOT NULL,
  `email` varchar(100) NOT NULL,
  `image` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- A tábla adatainak kiíratása `jatekok3`
--

INSERT INTO `jatekok3` (`id`, `jateknev`, `kategoria`, `korhatar`, `varos`, `leiras`, `datum`, `telefon`, `email`, `image`) VALUES
(7, 'woody', 'plüss', '5', 'Abony', 'Adminnal szerkesztve', '2022-04-02', 828383, 'ahhah@jsjs.hh', '2Q==.jpg'),
(16, 'Csillagos ötös', 'oktató', '13', 'Balatonalmádi', 'belepiszkalva hehehe', '2022-04-09', 630000000, 'halo@halo.halo', 'csillagos_otos.png'),
(126, 'foci labda', 'sport', '10', 'Oroszlány', 'Bőr labda', '2022-04-14', 620, 'zovikm@gmail.com', 'iic-adidas-z9078-656927-back-x-0001.jpg'),
(127, 'csibe', 'zenés', '4', 'Aszód', 'uwuehehe', '2022-04-14', 2838373, 'asjsj@bdjs.sjdh', '9msLDJjDS1Gun9ZrAQl1Onx3Dikg6K.jpg'),
(137, '20 db-os puzzle', 'Puzzle', '3', 'Dombóvár', 'egyszer használt, új', '2022-04-16', 309876543, 'ifjpeldabela@mail.com', '3264_1_700x.jpg'),
(138, '100 db-os építőkocka szett', 'Építőkocka', '3', 'Gyõr', 'fa anyagú, használt', '2022-04-16', 111222333, 'peldabela@mail.com', '3509_700x.jpg'),
(141, 'játék a halállal ', 'Akciójáték', '140', 'Budapest', 'soha semmilyen körülmények között ne vedd meg, mert a halálba fog idegesíteni.', '2022-04-18', 1209879988, 'reptail@energy.hur', 'received_409113507693863.webp');

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `uzenet`
--

DROP TABLE IF EXISTS `uzenet`;
CREATE TABLE `uzenet` (
  `id` int(11) NOT NULL,
  `nev` varchar(50) CHARACTER SET utf8 NOT NULL,
  `email` varchar(50) CHARACTER SET utf8 NOT NULL,
  `uzenet` varchar(255) CHARACTER SET utf8 NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- A tábla adatainak kiíratása `uzenet`
--

INSERT INTO `uzenet` (`id`, `nev`, `email`, `uzenet`) VALUES
(1, 'Zólyomi Viktor', 'zovikm@gmail.com', 'Kérem, hogy vegyék le az oldalról az adományomat.   ');

--
-- Indexek a kiírt táblákhoz
--

--
-- A tábla indexei `hirek`
--
ALTER TABLE `hirek`
  ADD PRIMARY KEY (`id`);

--
-- A tábla indexei `jatekok3`
--
ALTER TABLE `jatekok3`
  ADD PRIMARY KEY (`id`);

--
-- A tábla indexei `uzenet`
--
ALTER TABLE `uzenet`
  ADD PRIMARY KEY (`id`);

--
-- A kiírt táblák AUTO_INCREMENT értéke
--

--
-- AUTO_INCREMENT a táblához `hirek`
--
ALTER TABLE `hirek`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT a táblához `jatekok3`
--
ALTER TABLE `jatekok3`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=142;

--
-- AUTO_INCREMENT a táblához `uzenet`
--
ALTER TABLE `uzenet`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
