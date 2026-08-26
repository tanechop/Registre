-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/+
--
-- Host: 127.0.0.1
-- Generation Time: Aug 18, 2026 at 01:44 PM
-- Server version: 10.4.25-MariaDB
-- PHP Version: 8.1.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `visitors_db`
--

-- --------------------------------------------------------

--
-- Table structure for table `utilisateur`
--

CREATE TABLE `utilisateur` (
  `id_Utilisateur` int(11) NOT NULL,
  `nom_d_utilisateur` varchar(70) NOT NULL,
  `mot_de_passe` varchar(45) NOT NULL,
  `role` varchar(45) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `visites`
--

CREATE TABLE `visites` (
  `id_Visites` int(11) NOT NULL,
  `motif` varchar(45) DEFAULT NULL,
  `date_visite` datetime(3) DEFAULT NULL,
  `heure_de_depart` time DEFAULT NULL,
  `heure_d_arrivee` time DEFAULT NULL,
  `service` varchar(45) DEFAULT NULL,
  `Visiteurs_id_Visiteurs` int(11) NOT NULL,
  `Utilisateur_id_Utilisateur` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `visiteurs`
--

CREATE TABLE `visiteurs` (
  `id_Visiteurs` int(11) NOT NULL,
  `nom` varchar(45) DEFAULT NULL,
  `prenom` varchar(45) DEFAULT NULL,
  `contact` varchar(13) DEFAULT NULL,
  `num_CNI` varchar(45) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Indexes for dumped tables
--
ALTER TABLE utilisateur
    ADD COLUMN question_securite VARCHAR(255),
    ADD COLUMN reponse_securite VARCHAR(70);

INSERT INTO utilisateur(nom_d_utilisateur, mot_de_passe, role, question_securite, reponse_securite) VALUES ('firstAdmin', '$2a$10$DNMN1cWemRmELivXyevDmOOJwuGr0uZU390vgS7oD1GXOamCd1HyK', 'administrateur', 'Quel est le nom de votre manga préféré ?', '$2a$10$9Ir.WAf25Qwrbm8VAB32neVuoCoAS1CsNpLayPrPkMVZZisjSUnzW' );
--
-- Indexes for table `utilisateur`
--
ALTER TABLE `utilisateur`
  ADD PRIMARY KEY (`id_Utilisateur`);

--
-- Indexes for table `visites`
--
ALTER TABLE `visites`
  ADD PRIMARY KEY (`id_Visites`),
  ADD KEY `fk_Visites_Visiteurs_idx` (`Visiteurs_id_Visiteurs`),
  ADD KEY `fk_Visites_Utilisateur1_idx` (`Utilisateur_id_Utilisateur`);

--
-- Indexes for table `visiteurs`
--
ALTER TABLE `visiteurs`
  ADD PRIMARY KEY (`id_Visiteurs`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `visites`
--
ALTER TABLE `visites`
  ADD CONSTRAINT `fk_Visites_Utilisateur1` FOREIGN KEY (`Utilisateur_id_Utilisateur`) REFERENCES `utilisateur` (`id_Utilisateur`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_Visites_Visiteurs` FOREIGN KEY (`Visiteurs_id_Visiteurs`) REFERENCES `visiteurs` (`id_Visiteurs`) ON DELETE NO ACTION ON UPDATE NO ACTION;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
