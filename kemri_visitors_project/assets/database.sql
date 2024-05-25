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

DROP DATABASE IF EXISTS KEMRI_VISITORS_PROJECT;

CREATE DATABASE KEMRI_VISITORS_PROJECT;
USE KEMRI_VISITORS_PROJECT;

--
-- Table structure for table `date_number`
--

DROP TABLE IF EXISTS `date_number`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `date_number` (
  `DATE_TODAY` varchar(12) NOT NULL,
  `CURRENT_COUNT` int(11) NOT NULL,
  PRIMARY KEY (`DATE_TODAY`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `date_number`
--

LOCK TABLES `date_number` WRITE;
/*!40000 ALTER TABLE `date_number` DISABLE KEYS */;
INSERT INTO `date_number` VALUES ('2024 5 24',0);
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
-- Table structure for table `security_staff`
--

DROP TABLE IF EXISTS `security_staff`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `security_staff` (
  `STAFF_NUMBER` varchar(20) NOT NULL,
  `STAFF_NAME` varchar(100) NOT NULL,
  `STAFF_PHONE_NUMBER` varchar(10) NOT NULL,
  `STAFF_PASSWORD` varchar(20) NOT NULL,
  PRIMARY KEY (`STAFF_NUMBER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `security_staff`
--

LOCK TABLES `security_staff` WRITE;
/*!40000 ALTER TABLE `security_staff` DISABLE KEYS */;
/*!40000 ALTER TABLE `security_staff` ENABLE KEYS */;
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
  `VISITOR_ORIGIN` varchar(100) NOT NULL,
  `VISITOR_DESTINATION_ID` varchar(10) NOT NULL,
  `VISIT_REASON` varchar(200) NOT NULL,
  `VISITOR_TIME_IN` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `VISITOR_TIME_OUT` varchar(12) DEFAULT NULL,
  `VEHICLE_NUMBER_PLATE` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`VISIT_DATE`,`VISIT_NUMBER`),
  KEY `ID_NUMBER` (`ID_NUMBER`),
  KEY `VISITOR_DESTINATION_ID` (`VISITOR_DESTINATION_ID`),
  CONSTRAINT `visit_ibfk_1` FOREIGN KEY (`ID_NUMBER`) REFERENCES `visitor` (`ID_NUMBER`),
  CONSTRAINT `visit_ibfk_2` FOREIGN KEY (`VISITOR_DESTINATION_ID`) REFERENCES `destination` (`DESTINATION_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `visit`
--

LOCK TABLES `visit` WRITE;
/*!40000 ALTER TABLE `visit` DISABLE KEYS */;
INSERT INTO `visit` VALUES ('2024-05-14',1,'37187722','MMUST','LAB','attachment','2024-05-14 18:25:39','','KDH 343K'),('2024-05-14',2,'37187722','mmust','LAB','attachement','2024-05-14 18:27:38','','KSN 234K'),('2024-05-14',3,'37187722','mmust','HRM','attachment','2024-05-14 18:37:08','','KBV 728Z'),('2024-05-14',4,'37187722','michigan','ICT','requisition','2024-05-14 18:40:08','','KNS 342L'),('2024-05-14',5,'37187722','juja','RCH','agriculture','2024-05-14 18:41:51','','KAX 324Z'),('2024-05-14',6,'37187722','juja','RCH','agriculture','2024-05-14 18:43:02','','KBV 636K'),('2024-05-14',7,'37187722','sdf','DAD','sdf','2024-05-14 18:48:54','','NULL'),('2024-05-14',8,'37187722','dfg','SCI','fg','2024-05-14 18:50:26','','NULL'),('2024-05-14',9,'37187722','KJDF','HRM','SDF','2024-05-14 18:52:30','','NULL'),('2024-05-14',10,'40372042','KSDJ','PJT','ADSA','2024-05-14 18:55:03','','NULL'),('2024-05-14',11,'40372042','mmust','PJT','attachment','2024-05-14 19:00:19','','KBV 738Y'),('2024-05-14',12,'40372042','mmust','SCI','attachment','2024-05-14 19:02:16','','KCB 994R'),('2024-05-14',13,'40372042','mmust','PJT','attachment','2024-05-14 19:27:56','','KBV 748H'),('2024-05-14',14,'40372042','MMUST','RCH','Attachment','2024-05-14 19:29:44','','NULL'),('2024-05-14',15,'40372042','mmust','LAB','attachment','2024-05-14 19:30:52','','NULL'),('2024-05-14',16,'40372042','mmust','RCH','attachment','2024-05-14 19:35:06','','NULL'),('2024-05-14',17,'40372042','mmust','PJT','attachment','2024-05-14 19:36:49','','ksc 435j'),('2024-05-14',18,'40372042','mmust','PJT','project','2024-05-14 19:41:42','','kbv 454j'),('2024-05-14',19,'40372042','mmust','PJT','ict','2024-05-14 19:45:23','','NULL'),('2024-05-14',20,'40372042','mmust','PJT','sfd','2024-05-14 19:48:00','','NULL'),('2024-05-14',21,'40372042','mmust','HRM','sfd','2024-05-14 19:53:30','','NULL'),('2024-05-14',22,'40372042','dsfd','HRM','sdfs','2024-05-14 19:54:39','','NULL'),('2024-05-14',23,'40372042','sdd','HRM','sd','2024-05-14 19:56:10','','NULL'),('2024-05-14',24,'40372042','djksf','LAB','sfs','2024-05-14 19:58:42','','NULL'),('2024-05-14',25,'40372042','dsfa','HRM','sfs','2024-05-14 19:59:39','','NULL'),('2024-05-14',26,'40372042','fdgf','PJT','dgfd','2024-05-14 20:00:25','','NULL'),('2024-05-14',27,'40372042','MMUST','PJT','ATTACHMENT','2024-05-14 20:02:28','','NULL'),('2024-05-14',28,'40372042','MMUST','PJT','ATTACHMENT','2024-05-14 20:03:34','','NULL'),('2024-05-14',29,'37187722','dfd','RCH','dfgds','2024-05-14 20:08:48','','NULL'),('2024-05-14',30,'40372042','sdfdas','DAD','fhgf','2024-05-14 20:11:35','','NULL'),('2024-05-14',31,'40372042','xcbvc','LAB','cvbc','2024-05-14 20:13:32','','NULL'),('2024-05-14',32,'40372042','sfds','LAB','sfs','2024-05-14 20:14:24','','NULL'),('2024-05-14',33,'40372042','xzxg','HRM','sdfs','2024-05-14 20:16:01','','NULL'),('2024-05-18',1,'40372042','fh','HRM','fgh','2024-05-18 05:57:36','','NULL'),('2024-05-18',2,'40372042','fg','HRM','fgd','2024-05-18 06:01:27','','NULL'),('2024-05-18',3,'40372042','fgdf','ICT','dfgd','2024-05-18 06:02:20','','NULL'),('2024-05-18',4,'37187722','dfx','HRM','xcvxc','2024-05-18 06:48:53','09:48:53','NULL'),('2024-05-18',5,'37187722','fdgf','HRM','sfd','2024-05-18 06:06:54','','NULL'),('2024-05-18',6,'40372042','dgfd','ICT','dfgfd','2024-05-18 06:07:15','','NULL'),('2024-05-18',7,'40032897','dfgd','HRM','dgf','2024-05-18 06:08:41','','NULL'),('2024-05-18',8,'39868835','gfdg','ICT','dfg','2024-05-18 06:08:57','','NULL');
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
INSERT INTO `visitor` VALUES ('37187722','Elijah Mwaura','0714716961'),('39868835','Stephen Oduor','0703763448'),('40032897','Dollah','0702670179'),('40372042','Daniel Ndung`u Macharia','0712696965');
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

-- Dump completed on 2024-05-24  7:50:48
