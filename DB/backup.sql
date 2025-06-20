-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: localhost    Database: iotib
-- ------------------------------------------------------
-- Server version	8.0.34
--
-- Table structure for table `adeunis_payload`
--
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
--
-- Table structure for table `arf8123aa`
--
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
--
-- Table structure for table `centros`
--
CREATE TABLE `centros` (
  `Name` varchar(255) DEFAULT NULL,
  `Latitude` decimal(10,8) DEFAULT NULL,
  `Longitude` decimal(10,8) DEFAULT NULL,
  `Sensor_1` varchar(255) DEFAULT NULL,
  `Sensor_2` varchar(255) DEFAULT NULL,
  `Sensor_3` varchar(255) DEFAULT NULL,
  `Sensor_4` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
--
-- Dumping data for table `centros`
--
LOCK TABLES `centros` WRITE;
INSERT INTO `centros` VALUES ('Center 1',39.61670000,2.98330000,'Indoor Environment Meter','Outdoor Temperature and Humidity Meter','Open Doors Detector',NULL),('Center 2',39.77200000,2.71700000,'Indoor Temperature and Humidity Meter','Motion Sensor',NULL,NULL),('Center 3',39.00720000,1.43700000,'Indoor Motion Meter','Outdoor Ambiance Meter',NULL,NULL);
UNLOCK TABLES;
--
-- Table structure for table `data`
--
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
--
-- Table structure for table `dispositivos`
--
CREATE TABLE `dispositivos` (
  `DEV_EUI` varchar(23) NOT NULL,
  `NOMBRE` varchar(20) DEFAULT NULL,
  `CLASE` varchar(1) DEFAULT NULL,
  `FABRICANTE` varchar(20) DEFAULT NULL,
  `APP_EUI` varchar(23) DEFAULT NULL,
  `APP_KEY` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`DEV_EUI`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
--
-- Table structure for table `dragino_payload`
--
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
--
-- Table structure for table `gateways`
--
CREATE TABLE `gateways` (
  `GW_EUI` varchar(23) NOT NULL,
  `GW_NAME` varchar(20) DEFAULT NULL,
  `GW_FABRICANTE` varchar(20) DEFAULT NULL,
  `LOCALITATION` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`GW_EUI`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
--
-- Table structure for table `milesight_payload`
--
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
--
-- Table structure for table `paquetesperdidos`
--
CREATE TABLE `paquetesperdidos` (
  `dev_eui` varchar(50) DEFAULT NULL,
  `porcentajePaquetesPerdidos` decimal(10,5) DEFAULT NULL,
  `periodo` varchar(100) DEFAULT NULL,
  `id` int NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
--
-- Table structure for table `payload`
--
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
--
-- Table structure for table `sensecap_payload`
--
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
-- Dump completed on 2024-03-07 13:10:57
