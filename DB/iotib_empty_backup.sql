-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: localhost    Database: iotib
-- ------------------------------------------------------
-- Server version	8.0.34

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `adeunis_payload`
--

DROP TABLE IF EXISTS `adeunis_payload`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `adeunis_payload` (
  `id` int NOT NULL,
  `devEui` varchar(23) DEFAULT NULL,
  `FormatType` varchar(20) DEFAULT NULL,
  `FrameCounter` int DEFAULT NULL,
  `StatusByte` int DEFAULT NULL,
  `TRX_KeepAlive` varchar(50) DEFAULT NULL,
  `TRX_Periodic` int DEFAULT NULL,
  `SamplingPeriod` varchar(50) DEFAULT NULL,
  `InhibitionTime` varchar(50) DEFAULT NULL,
  `AlarmButton1Configuration` int DEFAULT NULL,
  `ThresholdButton1` int DEFAULT NULL,
  `AlarmButton2Configuration` int DEFAULT NULL,
  `ThresholdButton2` int DEFAULT NULL,
  `ADR` int DEFAULT NULL,
  `OTAA` int DEFAULT NULL,
  `PresenceState` int DEFAULT NULL,
  `Presence` int DEFAULT NULL,
  `HistoryPeriod` int DEFAULT NULL,
  `AlarmState` varchar(20) DEFAULT NULL,
  `Luminosity` int DEFAULT NULL,
  `DutyCycle` int DEFAULT NULL,
  `ABP` int DEFAULT NULL,
  `CurrentState` int DEFAULT NULL,
  `PreviousState` int DEFAULT NULL,
  `DigitalInput` int DEFAULT NULL,
  `GlobalCounter` int DEFAULT NULL,
  `InstantCounter` int DEFAULT NULL,
  `DeboundState1` int DEFAULT NULL,
  `DeboundState2` int DEFAULT NULL,
  `Counter` int DEFAULT NULL,
  `CurrentMeter_Type` int DEFAULT NULL,
  `CurrentMeter_Value` int DEFAULT NULL,
  `CurrentMeter_State` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `adeunis_payload`
--

LOCK TABLES `adeunis_payload` WRITE;
/*!40000 ALTER TABLE `adeunis_payload` DISABLE KEYS */;
/*!40000 ALTER TABLE `adeunis_payload` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `arf8123aa`
--

DROP TABLE IF EXISTS `arf8123aa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `arf8123aa` (
  `ID` int NOT NULL,
  `DEV_EUI` varchar(23) DEFAULT NULL,
  `SatelitNumber` int DEFAULT NULL,
  `Latitud` decimal(10,8) DEFAULT NULL,
  `Longitude` decimal(10,8) DEFAULT NULL,
  `Quality` int DEFAULT NULL,
  `UpCounter` int DEFAULT NULL,
  `DownCounter` int DEFAULT NULL,
  `BatteryLevel` int DEFAULT NULL,
  `Rssi_dl` int DEFAULT NULL,
  `SNR` int DEFAULT NULL,
  `ButtonStatus` int DEFAULT NULL,
  `GPSStatus` int DEFAULT NULL,
  `UPlinkStatus` int DEFAULT NULL,
  `DownlinkStatus` int DEFAULT NULL,
  `BatteryStatus` int DEFAULT NULL,
  `RSSI_SNR_Status` int DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `arf8123aa`
--

LOCK TABLES `arf8123aa` WRITE;
/*!40000 ALTER TABLE `arf8123aa` DISABLE KEYS */;
/*!40000 ALTER TABLE `arf8123aa` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `centros`
--

DROP TABLE IF EXISTS `centros`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `centros` (
  `Name` varchar(255) DEFAULT NULL,
  `Latitude` decimal(10,8) DEFAULT NULL,
  `Longitude` decimal(10,8) DEFAULT NULL,
  `Sensor_1` varchar(255) DEFAULT NULL,
  `Sensor_2` varchar(255) DEFAULT NULL,
  `Sensor_3` varchar(255) DEFAULT NULL,
  `Sensor_4` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `centros`
--

LOCK TABLES `centros` WRITE;
/*!40000 ALTER TABLE `centros` DISABLE KEYS */;
INSERT INTO `centros` VALUES ('Center 1',39.61670000,2.98330000,'Indoor Environment Meter','Outdoor Temperature and Humidity Meter','Open Doors Detector',NULL),('Center 2',39.77200000,2.71700000,'Indoor Temperature and Humidity Meter','Motion Sensor',NULL,NULL),('Center 3',39.00720000,1.43700000,'Indoor Motion Meter','Outdoor Ambiance Meter',NULL,NULL);
/*!40000 ALTER TABLE `centros` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `data`
--

DROP TABLE IF EXISTS `data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `data` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `DEV_EUI` varchar(23) DEFAULT NULL,
  `APP_EUI` varchar(23) DEFAULT NULL,
  `CODE_RATE` varchar(10) DEFAULT NULL,
  `DATA_RATE` varchar(10) DEFAULT NULL,
  `FREQUENCY` decimal(6,0) DEFAULT NULL,
  `PORT` int DEFAULT NULL,
  `RSSI_UP` int DEFAULT NULL,
  `SNR` int DEFAULT NULL,
  `GW_EUI` varchar(23) DEFAULT NULL,
  `TRX_TIME` timestamp NULL DEFAULT NULL,
  `PAYLOAD` varchar(100) DEFAULT NULL,
  `COUNTER` int DEFAULT NULL,
  `DIRECTION` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=8519 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `data`
--

LOCK TABLES `data` WRITE;
/*!40000 ALTER TABLE `data` DISABLE KEYS */;
/*!40000 ALTER TABLE `data` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dispositivos`
--

DROP TABLE IF EXISTS `dispositivos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `dispositivos` (
  `DEV_EUI` varchar(23) NOT NULL,
  `NOMBRE` varchar(20) DEFAULT NULL,
  `CLASE` varchar(1) DEFAULT NULL,
  `FABRICANTE` varchar(20) DEFAULT NULL,
  `APP_EUI` varchar(23) DEFAULT NULL,
  `APP_KEY` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`DEV_EUI`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dispositivos`
--

LOCK TABLES `dispositivos` WRITE;
/*!40000 ALTER TABLE `dispositivos` DISABLE KEYS */;
INSERT INTO `dispositivos` VALUES ('00-18-b2-00-00-02-27-36','Field Test','A','Adeunis','00-18-B2-44-41-52-46-32','304F8E87AE1B40DAB559B9FAA4870D1A'),('00-18-B2-60-00-00-04-7F','Motion','A','Adeunis','00-18-B2-53-42-50-4C-31','744AE09BB6114B9DB60EF79B4C47C6FC'),('24-e1-24-12-8b-10-96-30','Ambience Sensor','A','Milesight','24-E1-24-C0-00-2A-00-01','11111111111111111111111111111111');
/*!40000 ALTER TABLE `dispositivos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dragino_payload`
--

DROP TABLE IF EXISTS `dragino_payload`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `dragino_payload` (
  `id` int NOT NULL,
  `devEui` varchar(23) DEFAULT NULL,
  `ocStatus` varchar(10) DEFAULT NULL,
  `Alarm` int DEFAULT NULL,
  `totalPulse` int DEFAULT NULL,
  `lastOpen` int DEFAULT NULL,
  `unixTime` varchar(30) DEFAULT NULL,
  `Model` varchar(10) DEFAULT NULL,
  `Version` varchar(10) DEFAULT NULL,
  `Frequency` varchar(10) DEFAULT NULL,
  `Battery` double DEFAULT NULL,
  `Counter` int DEFAULT NULL,
  `ocNumber` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dragino_payload`
--

LOCK TABLES `dragino_payload` WRITE;
/*!40000 ALTER TABLE `dragino_payload` DISABLE KEYS */;
/*!40000 ALTER TABLE `dragino_payload` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gateways`
--

DROP TABLE IF EXISTS `gateways`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `gateways` (
  `GW_EUI` varchar(23) NOT NULL,
  `GW_NAME` varchar(20) DEFAULT NULL,
  `GW_FABRICANTE` varchar(20) DEFAULT NULL,
  `LOCALITATION` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`GW_EUI`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gateways`
--

LOCK TABLES `gateways` WRITE;
/*!40000 ALTER TABLE `gateways` DISABLE KEYS */;
/*!40000 ALTER TABLE `gateways` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `milesight_payload`
--

DROP TABLE IF EXISTS `milesight_payload`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `milesight_payload` (
  `id` int NOT NULL,
  `devEui` varchar(23) DEFAULT NULL,
  `Device_SN` varchar(23) DEFAULT NULL,
  `ProtocolVersion` varchar(10) DEFAULT NULL,
  `HardwareVersion` varchar(10) DEFAULT NULL,
  `SoftwareVersion` varchar(10) DEFAULT NULL,
  `PowerOn` varchar(5) DEFAULT NULL,
  `DeviceClass` int DEFAULT NULL,
  `Presure` double DEFAULT NULL,
  `TVOC` int DEFAULT NULL,
  `CO2` int DEFAULT NULL,
  `Light` int DEFAULT NULL,
  `Presence` int DEFAULT NULL,
  `Humidity` double DEFAULT NULL,
  `Temperature` double DEFAULT NULL,
  `BatteryLevel` int DEFAULT NULL,
  `VisibilityInfrareed` int DEFAULT NULL,
  `Infrareed` int DEFAULT NULL,
  `Counter` int DEFAULT NULL,
  `tipo_dec` varchar(10) DEFAULT NULL,
  `HayPresencia` int DEFAULT NULL,
  `ValorPresencia` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `milesight_payload`
--

LOCK TABLES `milesight_payload` WRITE;
/*!40000 ALTER TABLE `milesight_payload` DISABLE KEYS */;
/*!40000 ALTER TABLE `milesight_payload` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `paquetesperdidos`
--

DROP TABLE IF EXISTS `paquetesperdidos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `paquetesperdidos` (
  `dev_eui` varchar(50) DEFAULT NULL,
  `porcentajePaquetesPerdidos` decimal(10,5) DEFAULT NULL,
  `periodo` varchar(100) DEFAULT NULL,
  `id` int NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `paquetesperdidos`
--

LOCK TABLES `paquetesperdidos` WRITE;
/*!40000 ALTER TABLE `paquetesperdidos` DISABLE KEYS */;
INSERT INTO `paquetesperdidos` VALUES ('00-18-b2-00-00-02-27-36',0.00000,'2023-8-31 23:59:59 - 2023-8-01 00:00:01',1),('00-18-b2-60-00-00-04-7f',0.00000,'2023-8-31 23:59:59 - 2023-8-01 00:00:01',2),('24-e1-24-12-8b-10-96-30',0.00000,'2023-8-31 23:59:59 - 2023-8-01 00:00:01',3),('24-e1-24-53-8d-05-17-44',0.00000,'2023-8-31 23:59:59 - 2023-8-01 00:00:01',4),('24-e1-24-78-5d-12-43-02',0.00000,'2023-8-31 23:59:59 - 2023-8-01 00:00:01',5),('a8-40-41-f0-61-87-16-d5',0.00000,'2023-8-31 23:59:59 - 2023-8-01 00:00:01',6),('2c-f7-f1-c0-53-20-01-27',0.00000,'2023-8-31 23:59:59 - 2023-8-01 00:00:01',7),('24-e1-24-12-6a-23-33-97',0.00000,'2023-8-31 23:59:59 - 2023-8-01 00:00:01',8),('00-18-b2-00-00-02-27-36',0.00000,'2023-9-30 23:59:59 - 2023-9-01 00:00:01',9),('00-18-b2-60-00-00-04-7f',0.00000,'2023-9-30 23:59:59 - 2023-9-01 00:00:01',10),('24-e1-24-12-8b-10-96-30',0.00000,'2023-9-30 23:59:59 - 2023-9-01 00:00:01',11),('24-e1-24-53-8d-05-17-44',0.00000,'2023-9-30 23:59:59 - 2023-9-01 00:00:01',12),('24-e1-24-78-5d-12-43-02',0.00000,'2023-9-30 23:59:59 - 2023-9-01 00:00:01',13),('a8-40-41-f0-61-87-16-d5',0.00000,'2023-9-30 23:59:59 - 2023-9-01 00:00:01',14),('2c-f7-f1-c0-53-20-01-27',0.00000,'2023-9-30 23:59:59 - 2023-9-01 00:00:01',15),('24-e1-24-12-6a-23-33-97',0.00000,'2023-9-30 23:59:59 - 2023-9-01 00:00:01',16),('00-18-b2-00-00-02-27-36',32.60726,'2023-10-31 23:59:59 - 2023-10-01 00:00:01',17),('00-18-b2-60-00-00-04-7f',48.29157,'2023-10-31 23:59:59 - 2023-10-01 00:00:01',18),('24-e1-24-12-8b-10-96-30',25.57377,'2023-10-31 23:59:59 - 2023-10-01 00:00:01',19),('24-e1-24-53-8d-05-17-44',0.00000,'2023-10-31 23:59:59 - 2023-10-01 00:00:01',20),('24-e1-24-78-5d-12-43-02',0.00000,'2023-10-31 23:59:59 - 2023-10-01 00:00:01',21),('a8-40-41-f0-61-87-16-d5',0.00000,'2023-10-31 23:59:59 - 2023-10-01 00:00:01',22),('2c-f7-f1-c0-53-20-01-27',0.00000,'2023-10-31 23:59:59 - 2023-10-01 00:00:01',23),('24-e1-24-12-6a-23-33-97',0.00000,'2023-10-31 23:59:59 - 2023-10-01 00:00:01',24),('00-18-b2-00-00-02-27-36',2.85714,'2023-11-30 23:59:59 - 2023-11-01 00:00:01',25),('00-18-b2-60-00-00-04-7f',55.52923,'2023-11-30 23:59:59 - 2023-11-01 00:00:01',26),('24-e1-24-12-8b-10-96-30',9.87821,'2023-11-30 23:59:59 - 2023-11-01 00:00:01',27),('24-e1-24-53-8d-05-17-44',51.11111,'2023-11-30 23:59:59 - 2023-11-01 00:00:01',28),('24-e1-24-78-5d-12-43-02',8.73016,'2023-11-30 23:59:59 - 2023-11-01 00:00:01',29),('a8-40-41-f0-61-87-16-d5',16.69794,'2023-11-30 23:59:59 - 2023-11-01 00:00:01',30),('2c-f7-f1-c0-53-20-01-27',13.50000,'2023-11-30 23:59:59 - 2023-11-01 00:00:01',31),('24-e1-24-12-6a-23-33-97',13.39286,'2023-11-30 23:59:59 - 2023-11-01 00:00:01',32),('00-18-b2-00-00-02-27-36',0.00000,'2023-12-31 23:59:59 - 2023-12-01 00:00:01',33),('00-18-b2-60-00-00-04-7f',39.38815,'2023-12-31 23:59:59 - 2023-12-01 00:00:01',34),('24-e1-24-12-8b-10-96-30',24.78261,'2023-12-31 23:59:59 - 2023-12-01 00:00:01',35),('24-e1-24-53-8d-05-17-44',61.53846,'2023-12-31 23:59:59 - 2023-12-01 00:00:01',36),('24-e1-24-78-5d-12-43-02',31.68317,'2023-12-31 23:59:59 - 2023-12-01 00:00:01',37),('a8-40-41-f0-61-87-16-d5',23.33333,'2023-12-31 23:59:59 - 2023-12-01 00:00:01',38),('2c-f7-f1-c0-53-20-01-27',69.56522,'2023-12-31 23:59:59 - 2023-12-01 00:00:01',39),('24-e1-24-12-6a-23-33-97',87.03495,'2023-12-31 23:59:59 - 2023-12-01 00:00:01',40);
/*!40000 ALTER TABLE `paquetesperdidos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `payload`
--

DROP TABLE IF EXISTS `payload`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `payload` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `DEV_EUI` varchar(23) DEFAULT NULL,
  `DATA1` varchar(10) DEFAULT NULL,
  `DATA2` varchar(10) DEFAULT NULL,
  `DATA3` varchar(10) DEFAULT NULL,
  `DATA4` varchar(10) DEFAULT NULL,
  `DATA5` varchar(10) DEFAULT NULL,
  `DATA6` varchar(10) DEFAULT NULL,
  `DATA7` varchar(10) DEFAULT NULL,
  `DATA8` varchar(10) DEFAULT NULL,
  `DATA9` varchar(10) DEFAULT NULL,
  `DATA10` varchar(10) DEFAULT NULL,
  `DATA11` varchar(10) DEFAULT NULL,
  `DATA12` varchar(10) DEFAULT NULL,
  `DATA13` varchar(10) DEFAULT NULL,
  `DATA14` varchar(20) DEFAULT NULL,
  `DATA15` varchar(10) DEFAULT NULL,
  `DATA16` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  CONSTRAINT `fk_ID` FOREIGN KEY (`ID`) REFERENCES `data` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=6950 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `payload`
--

LOCK TABLES `payload` WRITE;
/*!40000 ALTER TABLE `payload` DISABLE KEYS */;
/*!40000 ALTER TABLE `payload` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sensecap_payload`
--

DROP TABLE IF EXISTS `sensecap_payload`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sensecap_payload` (
  `id` int NOT NULL,
  `devEui` varchar(23) DEFAULT NULL,
  `batteryLevel` int DEFAULT NULL,
  `uplinkInterval` int DEFAULT NULL,
  `Temperature` double DEFAULT NULL,
  `Humidity` double DEFAULT NULL,
  `CRC` varchar(255) DEFAULT NULL,
  `Counter` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sensecap_payload`
--

LOCK TABLES `sensecap_payload` WRITE;
/*!40000 ALTER TABLE `sensecap_payload` DISABLE KEYS */;
/*!40000 ALTER TABLE `sensecap_payload` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-03-07 13:10:57
