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
INSERT INTO `disponibilidade` VALUES (1,'2021-04-27 22:14:27',_binary '\0',_binary '\0',_binary '\0',_binary '\0',_binary '\0',_binary '\0',_binary '\0',_binary '\0',_binary '\0',_binary '\0',_binary '\0',_binary '\0',_binary '\0',_binary '\0',_binary '\0',_binary '\0',_binary '\0',_binary '\0',_binary '\0',_binary '\0',_binary '\0'),(2,'2021-04-27 23:24:43',_binary '\0',_binary '\0',_binary '\0',_binary '\0',_binary '',_binary '',_binary '\0',_binary '',_binary '',_binary '\0',_binary '\0',_binary '\0',_binary '\0',_binary '',_binary '',_binary '\0',_binary '',_binary '',_binary '\0',_binary '',_binary '');
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
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `modalidade`
--

LOCK TABLES `modalidade` WRITE;
/*!40000 ALTER TABLE `modalidade` DISABLE KEYS */;
INSERT INTO `modalidade` VALUES (1,'','Aprendizagem Industrial',1),(2,'sa','vinicios',0),(3,'','Curso Técnico',1),(4,'','Superior de Tecnologia',1),(5,'','Pós-graduação',1),(6,'','Aprendizagem Industrial',0),(7,'','Aprendizagem',1),(8,'','Aprendizagem In',0);
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
INSERT INTO `professor_areaconhecimento` VALUES (2,19);
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
INSERT INTO `professor_modalidade` VALUES (2,3),(2,4);
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
INSERT INTO `professor_unidadecurricular` VALUES (2,88),(2,93),(2,19),(2,20),(2,137),(2,23),(2,24),(2,25),(2,95),(2,27),(2,28),(2,100),(2,40),(2,41),(2,101),(2,42),(2,103),(2,43),(2,107),(2,44),(2,108),(2,109),(2,2),(2,3),(2,4),(2,5),(2,45),(2,6),(2,111),(2,49),(2,50),(2,51),(2,7),(2,8),(2,9),(2,52),(2,53),(2,10),(2,55),(2,56),(2,11),(2,12),(2,13),(2,14),(2,112),(2,15),(2,57),(2,113),(2,58),(2,114),(2,59),(2,60),(2,115),(2,116),(2,61),(2,62),(2,63),(2,64),(2,65),(2,66),(2,117),(2,120),(2,68),(2,69),(2,70),(2,71),(2,123),(2,74),(2,124),(2,75),(2,76),(2,77),(2,78),(2,127),(2,79),(2,80),(2,135),(2,81),(2,83),(2,86),(2,87),(2,18);
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
) ENGINE=InnoDB AUTO_INCREMENT=139 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `unidadecurricular`
--

LOCK TABLES `unidadecurricular` WRITE;
/*!40000 ALTER TABLE `unidadecurricular` DISABLE KEYS */;
INSERT INTO `unidadecurricular` VALUES (1,'','Eletrônica Básica',1),(2,'','Informática',1),(3,'','Informática Aplicada á Gestão',1),(4,'','Informática Básica',1),(5,'','Informática Fundamental',1),(6,'','Instalação de Sistemas Operacionais e Aplicativos',1),(7,'','Introdução de Sistemas Operacionais e Aplicativos',1),(8,'','Introdução e Lógica de Programação',1),(9,'','Linguagens de Programação Web',1),(10,'','Manipulação e criação de banco de dados',1),(11,'','Montagem e Manutenção de Computadores',1),(12,'','Montagem e Manutenção de Computadores e Periféricos',1),(13,'','Montagem e manutenção I',1),(14,'','Montagem e manutenção II',1),(15,'','Organização, Sistemas e Métodos',1),(16,'','Redes Locais',1),(17,'','Serviços de Rede e Internet',1),(18,'','Webdesign',1),(19,'','Análise de Sistemas Web',1),(20,'','Análise e Projeto de Sistemas',1),(21,'','Arquitetura de Computadores',1),(22,'','Arquitetura e Manutenção de Computadores',1),(23,'','Banco de dados aplicado',1),(24,'','Banco de Dados I',1),(25,'','Banco de Dados II',1),(26,'','Cabeamento Estruturado',1),(27,'','Desenvolvimento para Dispositivos Móveis',1),(28,'','Desenvolvimento Web com MVC',1),(29,'','Design',1),(30,'','Elementos Gráficos',1),(31,'','Eletricidade Aplicada à Manutenção de Computadores',1),(32,'','Eletricidade Aplicada à Manutenção e Suporte em Informática',1),(33,'','Eletrônica Aplicada à Manutenção de Computadores',1),(34,'','Eletrônica Aplicada à Manutenção e Suporte em Informática',1),(35,'','Eletrônica Básica',1),(36,'','Eletrônica Digital',1),(37,'','Eletrônica Digital Aplicada a Mecatrônica',1),(38,'','Eletrônica Digital I',1),(39,'','Eletrônica digital II',1),(40,'','Estrutura de Dados',1),(41,'','Fundamento de Linguagem para Web',1),(42,'','Fundamentos de Engenharia de Software',1),(43,'','Fundamentos de Sistemas Operacionais',1),(44,'','Gerenciamento de Processos e Serviços de TI',1),(45,'','Informática III',1),(46,'','Infra-estrutura da Internet',1),(47,'','Instalação e Configuração de Rede',1),(48,'','Instalação e Manutenção de Periféricos',1),(49,'','Introdução a Computação',1),(50,'','Introdução a Informática',1),(51,'','Introdução a Programação Web',1),(52,'','Lógica de Programação',1),(53,'','Lógica de Programação para Microcontroladores',1),(54,'','Manutenção em Equipamentos de Informática',1),(55,'','Métodos de Testes',1),(56,'','Modelagem de Banco de Dados',1),(57,'','Programação Básica para Web',1),(58,'','Programação Desktop',1),(59,'','Programação Orientada a Objetos I',1),(60,'','Programação Orientada a Objetos II',1),(61,'','Programação Web',1),(62,'','Programação Web Avançada',1),(63,'','Programação Web I',1),(64,'','Programação Web II',1),(65,'','Programação Web III',1),(66,'','Programação Web IV',1),(67,'','Projeto e Desenvolvimento de Interfaces',1),(68,'','Projetos de software',1),(69,'','Projetos de software I',1),(70,'','Projetos de software II',1),(71,'','Qualidade de Software',1),(72,'','Redes de Computadores',1),(73,'','Redes Industriais',1),(74,'','Segurança de Dados',1),(75,'','Servidores Web e Segurança',1),(76,'','Sistemas Operacionais Cliente',1),(77,'','Sistemas Operacionais Servidor',1),(78,'','Suporte ao Usuário',1),(79,'','Tecnologia da Informação',1),(80,'','Tecnologia da Informação e Comunicação',1),(81,'','Tendências e Demandas Tecnológicas em Informática',1),(82,'','Tendências e Demandas Tecnológicas em Manutenção e Suporte em Informática',1),(83,'','Tendências e Demandas Tecnológicas em TI',1),(84,'','Terminologia de Hardware, Software e Redes',1),(85,'','Testes de software',1),(86,'','Tópicos Avançados de Banco de Dados',1),(87,'','Tópicos Especiais de Tecnologia',1),(88,'','Algoritmos e programação',1),(89,'','	Eletrônica Básica',0),(90,'','Informática',0),(91,'','	Informática',0),(92,'','Tecnológicas em Informática',0),(93,'','Análise de sistemas',1),(94,'','Arquitetura de Redes',1),(95,'','Criação e Manipulação de Banco de dados',1),(96,'','Eletrônica de Potência',1),(97,'','Eletrônica I (Digital)',1),(98,'','Eletrônica II (Analógica)',1),(99,'','Eletrônica III (Potência)',1),(100,'','Engenharia de Software',1),(101,'','Fundamentos da Computação e Sistemas Operacionais',1),(102,'','Fundamentos de Computação e Sistemas Operacionais',0),(103,'','Fundamentos de Lógica de Programação',1),(104,'','Fundamentos de Redes',1),(105,'','Fundamentos de Redes Locais',1),(106,'','Gerência de Redes',1),(107,'','Gerenciamento de Banco de Dados',1),(108,'','Gestão de Projetos de TI',1),(109,'','Gestão de TI',1),(110,'','Integração de Serviços de Redes',1),(111,'','Interface Homem-Computador',1),(112,'','Novas Tecnologias',1),(113,'','Programação de Computadores',1),(114,'','Programação Orientada a Objetos',1),(115,'','Programação para Web',1),(116,'','Programação Script',1),(117,'','Projeto de Banco de Dados',1),(118,'','Projeto de Cabeamento Estruturado',1),(119,'','Projeto de Redes de Computadores',1),(120,'','Projeto de Sistemas',1),(121,'','Projetos de Redes',1),(122,'','Roteamento Intermediário e Tecnologias de Switching',1),(123,'','Segurança da Informação',1),(124,'','Segurança no Desenvolvimento de Sistemas',1),(125,'','Serviços Básicos de Redes para Web',1),(126,'','Serviços de Redes',1),(127,'','Técnicas de segurança de Redes de Computadores',1),(128,'','Tecnologia de Comutação e Roteamento',1),(129,'','Tecnologia de Redes de Longa Distância',1),(130,'','Tecnologias de Comutação e Redes Sem Fio',1),(131,'','Tecnologias de Convergência de Redes',1),(132,'','Tecnologias de Redes de Longa Distância',1),(133,'','Tecnologias de Redes Locais Sem Fio',1),(134,'','Tecnologias de Roteamento',1),(135,'','Tendências e Demandas Tecnológicas',1),(136,'','Teoria e Tecnologias de Roteamento',1),(137,'','Aplicação de Controladores Lógicos Programáveis',1),(138,'','Integração de Redes',1);
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

-- Dump completed on 2021-04-30 21:02:52
