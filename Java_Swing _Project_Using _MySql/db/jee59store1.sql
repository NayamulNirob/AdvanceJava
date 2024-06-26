-- MySQL dump 10.13  Distrib 8.0.36, for Win64 (x86_64)
--
-- Host: localhost    Database: jee59store
-- ------------------------------------------------------
-- Server version	8.0.37

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
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `unitPrice` float(8,2) NOT NULL,
  `Quantity` float(8,2) NOT NULL,
  `TotalPrice` float(8,2) NOT NULL,
  `SalesPrice` float(8,2) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES (1,'Mouse',400.00,20.00,8000.00,450.00),(2,'Rice',100.00,50.00,5000.00,120.00),(3,'Potato',50.00,20.00,1000.00,55.00),(5,'Tomato',100.00,5.00,500.00,150.00),(6,'Apple',150.00,20.00,3000.00,200.00),(7,'Jam',100.00,50.00,5000.00,150.00),(8,'Carrot',100.00,5.00,500.00,150.00),(11,'Mobile',10000.00,5.00,50000.00,11500.00),(12,'Ac',60000.00,2.00,120000.00,65000.00),(14,'Matherbord',35000.00,2.00,70000.00,40000.00),(15,'Ram DDR4 3200 16GB',3200.00,5.00,16000.00,4200.00),(16,'Ac',60000.00,5.00,300000.00,65000.00),(17,'Ac',60000.00,10.00,600000.00,65000.00),(18,'Ram DD4 3200 16GB',3200.00,12.00,38400.00,4200.00),(19,'Ram DDR4 3200 16GB',3200.00,10.00,32000.00,4200.00),(20,'Flux',90.00,100.00,9000.00,100.00);
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sales`
--

DROP TABLE IF EXISTS `sales`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sales` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `salesunitePrice` float(8,2) NOT NULL,
  `salesQuantity` float(8,2) NOT NULL,
  `salesTotalPrice` float(8,2) NOT NULL,
  `salesDate` datetime(6) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sales`
--

LOCK TABLES `sales` WRITE;
/*!40000 ALTER TABLE `sales` DISABLE KEYS */;
INSERT INTO `sales` VALUES (1,'Apple',200.00,5.00,1000.00,'2024-05-31 00:00:00.000000'),(2,'Apple',200.00,4.00,800.00,'2024-05-22 00:00:00.000000'),(3,'Jam',150.00,5.00,750.00,'2024-05-28 00:00:00.000000'),(4,'Jam',150.00,5.00,750.00,'2024-05-28 00:00:00.000000'),(5,'Mouse',450.00,5.00,2250.00,'2024-05-29 00:00:00.000000'),(6,'Rice',120.00,10.00,1200.00,'2024-05-27 00:00:00.000000'),(7,'Ram DDR4 3200 16GB',4200.00,10.00,42000.00,'2024-05-30 00:00:00.000000'),(8,'Ac',65000.00,5.00,325000.00,'2024-05-30 00:00:00.000000'),(9,'Flux',100.00,10.00,1000.00,'2024-05-23 00:00:00.000000');
/*!40000 ALTER TABLE `sales` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `stock`
--

DROP TABLE IF EXISTS `stock`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `stock` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `quantity` float(8,2) NOT NULL,
  `parchesPrice` float(8,2) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `stock`
--

LOCK TABLES `stock` WRITE;
/*!40000 ALTER TABLE `stock` DISABLE KEYS */;
INSERT INTO `stock` VALUES (1,'Ac',12.00,60000.00),(2,'Ram DDR4 3200 6GB',5.00,3200.00),(3,'Matherbord',2.00,35000.00),(4,'Ram DDR4 3200 16GB',5.00,3200.00),(5,'Ram DD4 3200 16GB',12.00,3200.00),(6,'Flux',90.00,90.00);
/*!40000 ALTER TABLE `stock` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-05-30 11:12:53
