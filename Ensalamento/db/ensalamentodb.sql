CREATE DATABASE  IF NOT EXISTS `ensalamentodb` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `ensalamentodb`;
-- MySQL dump 10.13  Distrib 8.0.18, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: ensalamentodb
-- ------------------------------------------------------
-- Server version	8.0.18

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `areaconhecimento`
--

DROP TABLE IF EXISTS `areaconhecimento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `areaconhecimento` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `descricao` varchar(255) DEFAULT NULL,
  `nome` varchar(255) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `areaconhecimento`
--

LOCK TABLES `areaconhecimento` WRITE;
/*!40000 ALTER TABLE `areaconhecimento` DISABLE KEYS */;
INSERT INTO `areaconhecimento` VALUES (1,'','Administração/Gestão',1),(2,'','Administração/Gestão',0),(3,'','Aeroespacial ',1),(4,'','Alimentos ',1),(5,'','Automação ',1),(6,'','Automotivo ',1),(7,'','Cerâmica ',1),(8,'','Civil/Edificações ',1),(9,'','Eletroeletrônica ',1),(10,'','Exatas ',1),(11,'','Gramática/Metodologia ',1),(12,'','Madeira/Mobiliário',1),(13,'','Metalmecânica ',1),(14,'','Polímeros ',1),(15,'','Processos',1),(16,'','Qualidade ',1),(17,'','Química ',1),(18,'','Saúde/Segurança/Meio Ambiente',1),(19,'','Tecnologia da Informação',1),(20,'','Têxtil/Vestuário',1);
/*!40000 ALTER TABLE `areaconhecimento` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `disponibilidade`
--

DROP TABLE IF EXISTS `disponibilidade`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `disponibilidade` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `data` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `domM` bit(1) NOT NULL,
  `domN` bit(1) NOT NULL,
  `domT` bit(1) NOT NULL,
  `quaM` bit(1) NOT NULL,
  `quaN` bit(1) NOT NULL,
  `quaT` bit(1) NOT NULL,
  `quiM` bit(1) NOT NULL,
  `quiN` bit(1) NOT NULL,
  `quiT` bit(1) NOT NULL,
  `sabM` bit(1) NOT NULL,
  `sabN` bit(1) NOT NULL,
  `sabT` bit(1) NOT NULL,
  `segM` bit(1) NOT NULL,
  `segN` bit(1) NOT NULL,
  `segT` bit(1) NOT NULL,
  `sexM` bit(1) NOT NULL,
  `sexN` bit(1) NOT NULL,
  `sexT` bit(1) NOT NULL,
  `terM` bit(1) NOT NULL,
  `terN` bit(1) NOT NULL,
  `terT` bit(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `disponibilidade`
--

LOCK TABLES `disponibilidade` WRITE;
/*!40000 ALTER TABLE `disponibilidade` DISABLE KEYS */;
INSERT INTO `disponibilidade` VALUES (1,'2021-04-27 22:14:27',_binary '\0',_binary '\0',_binary '\0',_binary '\0',_binary '\0',_binary '\0',_binary '\0',_binary '\0',_binary '\0',_binary '\0',_binary '\0',_binary '\0',_binary '\0',_binary '\0',_binary '\0',_binary '\0',_binary '\0',_binary '\0',_binary '\0',_binary '\0',_binary '\0'),(2,'2021-04-27 23:24:43',_binary '\0',_binary '\0',_binary '\0',_binary '\0',_binary '\0',_binary '\0',_binary '\0',_binary '\0',_binary '\0',_binary '\0',_binary '\0',_binary '\0',_binary '\0',_binary '\0',_binary '\0',_binary '\0',_binary '\0',_binary '\0',_binary '\0',_binary '\0',_binary '\0');
/*!40000 ALTER TABLE `disponibilidade` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `modalidade`
--

DROP TABLE IF EXISTS `modalidade`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `modalidade` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `descricao` varchar(255) DEFAULT NULL,
  `nome` varchar(255) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `modalidade`
--

LOCK TABLES `modalidade` WRITE;
/*!40000 ALTER TABLE `modalidade` DISABLE KEYS */;
INSERT INTO `modalidade` VALUES (1,'','Aprendizagem Industrial',1),(2,'sa','vinicios',0),(3,'','Curso Técnico',1),(4,'','Superior de Tecnologia',1),(5,'','Pós-graduação',1),(6,'','Aprendizagem Industrial',0),(7,'','Aprendizagem',1);
/*!40000 ALTER TABLE `modalidade` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `professor`
--

DROP TABLE IF EXISTS `professor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `professor` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `cpf` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `nome` varchar(255) DEFAULT NULL,
  `permissao` varchar(255) DEFAULT NULL,
  `senha` varchar(255) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `telefone` varchar(255) DEFAULT NULL,
  `disponibilidade_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK3B4FF64F222A69D2` (`disponibilidade_id`),
  CONSTRAINT `FK3B4FF64F222A69D2` FOREIGN KEY (`disponibilidade_id`) REFERENCES `disponibilidade` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `professor`
--

LOCK TABLES `professor` WRITE;
/*!40000 ALTER TABLE `professor` DISABLE KEYS */;
INSERT INTO `professor` VALUES (1,'11111111111','0vinicios1@gmail.com','vinicios','ADMINISTRADOR','C9C05FFB82A529151D7919560917999E',1,'(11) 11111-1111',1),(2,'22222222222','silvio@gmail.com','silvio','PROFESSOR','548085616AEA7913DA6ACC6DEAD5BD36',1,'(22) 22222-2222',2);
/*!40000 ALTER TABLE `professor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `professor_areaconhecimento`
--

DROP TABLE IF EXISTS `professor_areaconhecimento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `professor_areaconhecimento` (
  `professor_id` bigint(20) NOT NULL,
  `areaconhecimento_id` bigint(20) NOT NULL,
  KEY `FK6A0D288978AEBDB2` (`professor_id`),
  KEY `FK6A0D28895D7FD1E2` (`areaconhecimento_id`),
  CONSTRAINT `FK6A0D28895D7FD1E2` FOREIGN KEY (`areaconhecimento_id`) REFERENCES `areaconhecimento` (`id`),
  CONSTRAINT `FK6A0D288978AEBDB2` FOREIGN KEY (`professor_id`) REFERENCES `professor` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `professor_areaconhecimento`
--

LOCK TABLES `professor_areaconhecimento` WRITE;
/*!40000 ALTER TABLE `professor_areaconhecimento` DISABLE KEYS */;
/*!40000 ALTER TABLE `professor_areaconhecimento` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `professor_modalidade`
--

DROP TABLE IF EXISTS `professor_modalidade`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `professor_modalidade` (
  `professor_id` bigint(20) NOT NULL,
  `modalidade_id` bigint(20) NOT NULL,
  KEY `FK6421BF2A78AEBDB2` (`professor_id`),
  KEY `FK6421BF2A4EBF9822` (`modalidade_id`),
  CONSTRAINT `FK6421BF2A4EBF9822` FOREIGN KEY (`modalidade_id`) REFERENCES `modalidade` (`id`),
  CONSTRAINT `FK6421BF2A78AEBDB2` FOREIGN KEY (`professor_id`) REFERENCES `professor` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `professor_modalidade`
--

LOCK TABLES `professor_modalidade` WRITE;
/*!40000 ALTER TABLE `professor_modalidade` DISABLE KEYS */;
/*!40000 ALTER TABLE `professor_modalidade` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `professor_unidadecurricular`
--

DROP TABLE IF EXISTS `professor_unidadecurricular`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `professor_unidadecurricular` (
  `professor_id` bigint(20) NOT NULL,
  `unidadecurricular_id` bigint(20) NOT NULL,
  KEY `FKEF51F91278AEBDB2` (`professor_id`),
  KEY `FKEF51F91225B214F2` (`unidadecurricular_id`),
  CONSTRAINT `FKEF51F91225B214F2` FOREIGN KEY (`unidadecurricular_id`) REFERENCES `unidadecurricular` (`id`),
  CONSTRAINT `FKEF51F91278AEBDB2` FOREIGN KEY (`professor_id`) REFERENCES `professor` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `professor_unidadecurricular`
--

LOCK TABLES `professor_unidadecurricular` WRITE;
/*!40000 ALTER TABLE `professor_unidadecurricular` DISABLE KEYS */;
/*!40000 ALTER TABLE `professor_unidadecurricular` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `unidadecurricular`
--

DROP TABLE IF EXISTS `unidadecurricular`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `unidadecurricular` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `descricao` varchar(255) DEFAULT NULL,
  `nome` varchar(255) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `unidadecurricular`
--

LOCK TABLES `unidadecurricular` WRITE;
/*!40000 ALTER TABLE `unidadecurricular` DISABLE KEYS */;
/*!40000 ALTER TABLE `unidadecurricular` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'ensalamentodb'
--

--
-- Dumping routines for database 'ensalamentodb'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-04-27 21:58:22
