-- MySQL dump 10.13  Distrib 5.1.45, for Win64 (unknown)
--
-- Host: localhost    Database: kemri_visitors_project
-- ------------------------------------------------------
-- Server version	5.1.45-community

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `date_number`
--

DROP TABLE IF EXISTS `date_number`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `date_number` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `DATE_TODAY` varchar(12) NOT NULL,
  `CURRENT_COUNT` int(11) NOT NULL,
  `IS_STAFF_COUNT` varchar(5) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `date_number`
--

LOCK TABLES `date_number` WRITE;
/*!40000 ALTER TABLE `date_number` DISABLE KEYS */;
INSERT INTO `date_number` VALUES (1,'2024 6 3',4,'YES'),(2,'2024 6 3',4,'NO');
/*!40000 ALTER TABLE `date_number` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `destination`
--

DROP TABLE IF EXISTS `destination`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `destination` (
  `DESTINATION_ID` varchar(10) NOT NULL,
  `DESTINATION_NAME` varchar(100) NOT NULL,
  PRIMARY KEY (`DESTINATION_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `destination`
--

LOCK TABLES `destination` WRITE;
/*!40000 ALTER TABLE `destination` DISABLE KEYS */;
INSERT INTO `destination` VALUES ('DAD','Data Analysis Department'),('HRM','Human Resource Management'),('ICT','ICT Help Desk and User Support'),('LAB','Bio-medical Laboratory'),('PJT','Project'),('RCH','Research'),('SCI','Science Department');
/*!40000 ALTER TABLE `destination` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `security`
--

DROP TABLE IF EXISTS `security`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `security` (
  `SECURITY_NUMBER` varchar(20) NOT NULL,
  `SECURITY_NAME` varchar(100) NOT NULL,
  `PHONE_NUMBER` varchar(10) NOT NULL,
  `SECURITY_PASSWORD` varchar(20) NOT NULL,
  PRIMARY KEY (`SECURITY_NUMBER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `security`
--

LOCK TABLES `security` WRITE;
/*!40000 ALTER TABLE `security` DISABLE KEYS */;
INSERT INTO `security` VALUES ('4444','Daniel Ndung`u Macharia','0712696965','@Macharia2261');
/*!40000 ALTER TABLE `security` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `staff`
--

DROP TABLE IF EXISTS `staff`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `staff` (
  `STAFF_ID` varchar(10) NOT NULL,
  `STAFF_NAME` varchar(100) NOT NULL,
  `STAFF_PHONE` varchar(20) NOT NULL,
  `STATION_ID` varchar(10) NOT NULL,
  PRIMARY KEY (`STAFF_ID`),
  KEY `CONSTRAINTS` (`STATION_ID`),
  CONSTRAINT `staff_ibfk_1` FOREIGN KEY (`STATION_ID`) REFERENCES `station` (`STATION_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `staff`
--

LOCK TABLES `staff` WRITE;
/*!40000 ALTER TABLE `staff` DISABLE KEYS */;
INSERT INTO `staff` VALUES ('37187760','Elijah Mwaura','0714716961','pjt'),('39868835','Stephen Oduor','0703763448','cdc'),('40372042','Daniel Macharia','0712696965','kemri');
/*!40000 ALTER TABLE `staff` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `staff_visit`
--

DROP TABLE IF EXISTS `staff_visit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `staff_visit` (
  `VISIT_DATE` varchar(12) NOT NULL,
  `VISIT_NUMBER` int(11) NOT NULL,
  `STAFF_ID` varchar(10) NOT NULL,
  `CHECK_IN_STATION` varchar(10) NOT NULL,
  `STAFF_CHECK_IN_TIME` varchar(20) DEFAULT NULL,
  `STAFF_CHECK_OUT_TIME` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`VISIT_DATE`,`VISIT_NUMBER`),
  KEY `CONSTRAINTS` (`STAFF_ID`),
  CONSTRAINT `staff_visit_ibfk_1` FOREIGN KEY (`STAFF_ID`) REFERENCES `staff` (`STAFF_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `staff_visit`
--

LOCK TABLES `staff_visit` WRITE;
/*!40000 ALTER TABLE `staff_visit` DISABLE KEYS */;
INSERT INTO `staff_visit` VALUES ('2024-6-03',0,'40372042','KEMRI','2024-06-03 15:53:49','15:53:49'),('2024-6-03',1,'40372042','KEMRI','2024-06-03 15:53:49','15:53:49'),('2024-6-03',2,'40372042','KEMRI','2024-06-03 15:53:49','15:53:49'),('2024-6-03',3,'40372042','KEMRI','2024-06-03 15:53:49','15:53:49'),('2024-6-03',4,'40372042','KEMRI','2024-06-03 15:53:49','15:53:49'),('2024-6-03',5,'39868835','KEMRI','2024-06-03 15:30:45','16:01:20'),('2024-6-03',6,'37187760','KEMRI','2024-06-03 15:35:05','15:35:05');
/*!40000 ALTER TABLE `staff_visit` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `station`
--

DROP TABLE IF EXISTS `station`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `station` (
  `STATION_ID` varchar(10) NOT NULL DEFAULT '',
  `STATION_NAME` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`STATION_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `station`
--

LOCK TABLES `station` WRITE;
/*!40000 ALTER TABLE `station` DISABLE KEYS */;
INSERT INTO `station` VALUES ('cdc','center of disease control'),('kemri','kemri administration'),('pjt','project');
/*!40000 ALTER TABLE `station` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `visit`
--

DROP TABLE IF EXISTS `visit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `visit` (
  `VISIT_DATE` date NOT NULL,
  `VISIT_NUMBER` int(11) NOT NULL,
  `ID_NUMBER` varchar(8) NOT NULL,
  `SECURITY_NUMBER` varchar(20) NOT NULL,
  `VISITOR_ORIGIN` varchar(100) NOT NULL,
  `VISITOR_DESTINATION_ID` varchar(10) NOT NULL,
  `VISIT_REASON` varchar(200) NOT NULL,
  `VISITOR_TIME_IN` varchar(20) DEFAULT NULL,
  `VISITOR_TIME_OUT` varchar(20) DEFAULT NULL,
  `VEHICLE_NUMBER_PLATE` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`VISIT_DATE`,`VISIT_NUMBER`),
  KEY `ID_NUMBER` (`ID_NUMBER`),
  KEY `VISITOR_DESTINATION_ID` (`VISITOR_DESTINATION_ID`),
  KEY `SECURITY_NUMBER` (`SECURITY_NUMBER`),
  CONSTRAINT `visit_ibfk_1` FOREIGN KEY (`ID_NUMBER`) REFERENCES `visitor` (`ID_NUMBER`),
  CONSTRAINT `visit_ibfk_2` FOREIGN KEY (`VISITOR_DESTINATION_ID`) REFERENCES `destination` (`DESTINATION_ID`),
  CONSTRAINT `visit_ibfk_3` FOREIGN KEY (`SECURITY_NUMBER`) REFERENCES `security` (`SECURITY_NUMBER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `visit`
--

LOCK TABLES `visit` WRITE;
/*!40000 ALTER TABLE `visit` DISABLE KEYS */;
INSERT INTO `visit` VALUES ('2024-06-03',1,'40372042','4444','Breakin','PJT','Software deployment','2024-06-03 09:23:34','09:23:34','KBJ 224K'),('2024-06-03',2,'39868835','4444','mmust','ICT','attachment','2024-06-03 09:29:15','09:29:15','NULL'),('2024-06-03',3,'37187760','4444','Juja','RCH','Agricultural research','2024-06-03 09:41:06','09:41:06','KBK 727G'),('2024-06-03',4,'40372042','4444','dsgfsd','DAD','sdgfsd','16:00:19','16:03:05','NULL');
/*!40000 ALTER TABLE `visit` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `visitor`
--

DROP TABLE IF EXISTS `visitor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `visitor` (
  `ID_NUMBER` varchar(8) NOT NULL,
  `VISITOR_NAME` varchar(100) NOT NULL,
  `PHONE_NUMBER` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`ID_NUMBER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `visitor`
--

LOCK TABLES `visitor` WRITE;
/*!40000 ALTER TABLE `visitor` DISABLE KEYS */;
INSERT INTO `visitor` VALUES ('37187760','Elijah Mwaura','0714716961'),('39868835','Stephen Oduor','0703763448'),('40372042','Daniel Macharia','0712696965');
/*!40000 ALTER TABLE `visitor` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-06-03 16:37:59
