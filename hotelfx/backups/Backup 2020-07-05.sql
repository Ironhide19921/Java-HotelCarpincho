-- MySQL dump 10.13  Distrib 5.7.19, for Win64 (x86_64)
--
-- Host: localhost    Database: hotel
-- ------------------------------------------------------
-- Server version	5.5.5-10.0.38-MariaDB

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
-- Table structure for table `categoriacuarto`
--

DROP TABLE IF EXISTS `categoriacuarto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `categoriacuarto` (
  `idCategoriaCuarto` int(11) NOT NULL AUTO_INCREMENT,
  `Nombre` varchar(45) NOT NULL,
  `Detalle` varchar(100) NOT NULL,
  PRIMARY KEY (`idCategoriaCuarto`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categoriacuarto`
--

LOCK TABLES `categoriacuarto` WRITE;
/*!40000 ALTER TABLE `categoriacuarto` DISABLE KEYS */;
INSERT INTO `categoriacuarto` VALUES (1,'cate1','det1'),(2,'categoriaB','detalleB');
/*!40000 ALTER TABLE `categoriacuarto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `categoriaevento`
--

DROP TABLE IF EXISTS `categoriaevento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `categoriaevento` (
  `idCategoriaEvento` int(11) NOT NULL AUTO_INCREMENT,
  `Nombre` varchar(45) NOT NULL,
  `Detalle` varchar(100) NOT NULL,
  PRIMARY KEY (`idCategoriaEvento`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categoriaevento`
--

LOCK TABLES `categoriaevento` WRITE;
/*!40000 ALTER TABLE `categoriaevento` DISABLE KEYS */;
INSERT INTO `categoriaevento` VALUES (1,'Cumpleaños','Un cumple con papitas y pancho'),(2,'Casamiento','Con torta y musica'),(3,'Infantil','Algo infantil con lo basico');
/*!40000 ALTER TABLE `categoriaevento` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cliente`
--

DROP TABLE IF EXISTS `cliente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cliente` (
  `idCliente` int(11) NOT NULL AUTO_INCREMENT,
  `Nombre` varchar(45) NOT NULL,
  `Apellido` varchar(45) NOT NULL,
  `TipoDocumento` varchar(20) NOT NULL,
  `Documento` varchar(20) NOT NULL,
  `Email` varchar(35) NOT NULL,
  `Telefono` varchar(20) NOT NULL,
  `Estado` tinyint(1) NOT NULL,
  `FechaNacimiento` date NOT NULL,
  PRIMARY KEY (`idCliente`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cliente`
--

LOCK TABLES `cliente` WRITE;
/*!40000 ALTER TABLE `cliente` DISABLE KEYS */;
INSERT INTO `cliente` VALUES (1,'clientea','apellidoa','DNI','22222222','email@email.com','1111111111',1,'2015-07-17'),(2,'ClienteB','ApellidoB','DNI','12234567','cecialf1991@gmail.com','1345678906',1,'2020-06-04'),(3,'clienteC','clienteC','DNI','34555666','email@email.com','1534343434',1,'2020-07-03');
/*!40000 ALTER TABLE `cliente` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `configuracion`
--

DROP TABLE IF EXISTS `configuracion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `configuracion` (
  `idConfig` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `provSMTP` varchar(100) NOT NULL,
  PRIMARY KEY (`idConfig`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `configuracion`
--

LOCK TABLES `configuracion` WRITE;
/*!40000 ALTER TABLE `configuracion` DISABLE KEYS */;
INSERT INTO `configuracion` VALUES (1,'carpinchocorp@gmail.com','covid-19','smtp.gmail.com');
/*!40000 ALTER TABLE `configuracion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cuarto`
--

DROP TABLE IF EXISTS `cuarto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cuarto` (
  `idCuarto` int(11) NOT NULL AUTO_INCREMENT,
  `idCategoriaCuarto` int(11) NOT NULL,
  `Capacidad` int(5) NOT NULL,
  `Monto` double(10,3) NOT NULL,
  `MontoSenia` double(10,3) NOT NULL,
  `Piso` varchar(5) NOT NULL,
  `Habitacion` varchar(5) NOT NULL,
  `Estado` tinyint(1) NOT NULL,
  PRIMARY KEY (`idCuarto`),
  KEY `fk_idCategoriaCuarto` (`idCategoriaCuarto`),
  CONSTRAINT `fk_idCategoriaCuarto` FOREIGN KEY (`idCategoriaCuarto`) REFERENCES `categoriacuarto` (`idCategoriaCuarto`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cuarto`
--

LOCK TABLES `cuarto` WRITE;
/*!40000 ALTER TABLE `cuarto` DISABLE KEYS */;
INSERT INTO `cuarto` VALUES (1,1,2,300.000,10.000,'2','B',1),(2,1,4,700.000,25.000,'1','B',1),(3,1,3,500.000,25.000,'2','A',1);
/*!40000 ALTER TABLE `cuarto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `email`
--

DROP TABLE IF EXISTS `email`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `email` (
  `idEmail` int(11) NOT NULL AUTO_INCREMENT,
  `fechaCreacion` datetime NOT NULL,
  `Texto` varchar(500) NOT NULL,
  `Asunto` varchar(50) NOT NULL,
  `Emisor` varchar(50) NOT NULL,
  `Receptor` varchar(50) NOT NULL,
  `Estado` tinyint(1) NOT NULL,
  `Pass` varchar(50) NOT NULL,
  PRIMARY KEY (`idEmail`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `email`
--

LOCK TABLES `email` WRITE;
/*!40000 ALTER TABLE `email` DISABLE KEYS */;
INSERT INTO `email` VALUES (1,'2020-06-27 00:00:00','Acordate que tenes una reserva','Recordatorio','carpinchocorp@gmail.com','cecialf1991@gmail.com',0,'covid-19'),(2,'2020-06-30 00:00:00','Acordate que tenes una reserva','Recordatorio reserva evento','carpinchocorp@gmail.com','email@email.com',1,'covid-19'),(3,'2020-06-28 00:00:00','Acordate que tenes una reserva','Recordatorio','carpinchocorp@gmail.com','alfonsocecilia91@gmail.com',0,'covid-19'),(4,'2020-07-03 00:00:00','Acordate que tenes una reserva','Recordatorio','carpinchocorp@gmail.com','cecialf1991@gmail.com',1,'covid-19'),(5,'2020-06-29 00:00:00','Acordate que tenes una reserva','Recordatorio','carpinchocorp@gmail.com','cecialf1991@hotmail.com',0,'covid-19'),(6,'2020-06-28 00:00:00','Acordate que tenes una reserva','Recordatorio','carpinchocorp@gmail.com','cecialf1991@gmail.com',0,'covid-19'),(7,'2020-06-28 00:00:00','Acordate que tenes una reserva','Recordatorio','carpinchocorp@gmail.com','cecialf1991@gmail.com',0,'covid-19'),(8,'2020-07-09 00:00:00','Estimado\nSu reserva ha sido registrada con éxito. La fecha de ingreso es el día 2020-07-09 07:00:00.0.\nMuchas gracias.','Registro de reserva','carpinchocorp@gmail.com','cecialf1991@hotmail.com',1,'covid-19');
/*!40000 ALTER TABLE `email` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `encuesta`
--

DROP TABLE IF EXISTS `encuesta`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `encuesta` (
  `idEncuesta` int(11) NOT NULL AUTO_INCREMENT,
  `idCliente` int(11) NOT NULL,
  `recipiente` varchar(60) NOT NULL,
  `encuestado` tinyint(1) NOT NULL,
  PRIMARY KEY (`idEncuesta`),
  KEY `fk_cliente_id_encuesta` (`idCliente`),
  CONSTRAINT `fk_cliente_id_encuesta` FOREIGN KEY (`idCliente`) REFERENCES `cliente` (`idCliente`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `encuesta`
--

LOCK TABLES `encuesta` WRITE;
/*!40000 ALTER TABLE `encuesta` DISABLE KEYS */;
INSERT INTO `encuesta` VALUES (1,1,'5570377442',0),(2,2,'5570929725',0),(3,3,'5573812597',0);
/*!40000 ALTER TABLE `encuesta` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `errorimportar`
--

DROP TABLE IF EXISTS `errorimportar`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `errorimportar` (
  `idError` int(11) NOT NULL AUTO_INCREMENT,
  `fecha` datetime NOT NULL,
  `usuario` int(11) NOT NULL,
  `detalle` varchar(500) NOT NULL,
  PRIMARY KEY (`idError`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `errorimportar`
--

LOCK TABLES `errorimportar` WRITE;
/*!40000 ALTER TABLE `errorimportar` DISABLE KEYS */;
/*!40000 ALTER TABLE `errorimportar` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `notificacion`
--

DROP TABLE IF EXISTS `notificacion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `notificacion` (
  `idNotificacion` int(11) NOT NULL AUTO_INCREMENT,
  `idCliente` int(11) NOT NULL,
  `Detalle` varchar(45) NOT NULL,
  `Estado` varchar(20) NOT NULL,
  `Tipo` varchar(20) NOT NULL,
  PRIMARY KEY (`idNotificacion`),
  KEY `fk_cliente_id` (`idCliente`),
  CONSTRAINT `fk_cliente_id` FOREIGN KEY (`idCliente`) REFERENCES `cliente` (`idCliente`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notificacion`
--

LOCK TABLES `notificacion` WRITE;
/*!40000 ALTER TABLE `notificacion` DISABLE KEYS */;
/*!40000 ALTER TABLE `notificacion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ordenpedido`
--

DROP TABLE IF EXISTS `ordenpedido`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ordenpedido` (
  `idOrdenPedido` int(11) NOT NULL AUTO_INCREMENT,
  `idProducto` int(11) NOT NULL,
  `idCliente` int(11) NOT NULL,
  `idUsuario` int(11) NOT NULL,
  `Cantidad` int(20) NOT NULL,
  `PrecioTotal` decimal(10,3) NOT NULL,
  `FormaPago` varchar(20) DEFAULT NULL,
  `TipoTarjeta` varchar(25) DEFAULT NULL,
  `NumeroTarjeta` varchar(25) DEFAULT NULL,
  `FechaVencTarjeta` varchar(15) DEFAULT NULL,
  `CodSeguridadTarjeta` varchar(10) DEFAULT NULL,
  `esRestoran` tinyint(1) NOT NULL,
  PRIMARY KEY (`idOrdenPedido`,`idProducto`),
  KEY `fk_idProducto` (`idProducto`),
  KEY `fk_id_Cliente` (`idCliente`),
  KEY `fk_idUsuario` (`idUsuario`),
  CONSTRAINT `fk_idProducto` FOREIGN KEY (`idProducto`) REFERENCES `producto` (`idProducto`),
  CONSTRAINT `fk_idUsuario` FOREIGN KEY (`idUsuario`) REFERENCES `usuario` (`idUsuario`),
  CONSTRAINT `fk_id_Cliente` FOREIGN KEY (`idCliente`) REFERENCES `cliente` (`idCliente`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ordenpedido`
--

LOCK TABLES `ordenpedido` WRITE;
/*!40000 ALTER TABLE `ordenpedido` DISABLE KEYS */;
/*!40000 ALTER TABLE `ordenpedido` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `perfil`
--

DROP TABLE IF EXISTS `perfil`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `perfil` (
  `idPerfil` int(11) NOT NULL AUTO_INCREMENT,
  `NombrePerfil` varchar(45) NOT NULL,
  PRIMARY KEY (`idPerfil`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `perfil`
--

LOCK TABLES `perfil` WRITE;
/*!40000 ALTER TABLE `perfil` DISABLE KEYS */;
INSERT INTO `perfil` VALUES (1,'full');
/*!40000 ALTER TABLE `perfil` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `permiso`
--

DROP TABLE IF EXISTS `permiso`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `permiso` (
  `idPermiso` int(11) NOT NULL AUTO_INCREMENT,
  `NombrePermiso` varchar(45) NOT NULL,
  PRIMARY KEY (`idPermiso`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `permiso`
--

LOCK TABLES `permiso` WRITE;
/*!40000 ALTER TABLE `permiso` DISABLE KEYS */;
INSERT INTO `permiso` VALUES (1,'ABM Usuarios'),(2,'ABM Clientes'),(3,'ABM Cuartos'),(4,'ABM Productos'),(5,'ABM Reserva cuarto'),(6,'ABM Reserva evento '),(7,'Importar reservas'),(8,'ABM Categoria evento'),(9,'ABM Perfiles'),(10,'ABM Categoria cuarto'),(11,'ABM Salones'),(12,'Configuracion email'),(13,'ABM Orden pedidos'),(14,'Gestion backup'),(15,'Reportes'),(16,'Reporte Ocupacion'),(17,'Reporte Contable'),(18,'Reporte Reservas'),(19,'Reporte Errores'),(20,'Reporte Encuestas'),(21,'Restore');
/*!40000 ALTER TABLE `permiso` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `permisoperfil`
--

DROP TABLE IF EXISTS `permisoperfil`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `permisoperfil` (
  `idPermisoPerfil` int(11) NOT NULL AUTO_INCREMENT,
  `idPerfil` int(11) NOT NULL,
  `idPermiso` int(11) NOT NULL,
  PRIMARY KEY (`idPermisoPerfil`),
  KEY `fk_idPerfil` (`idPerfil`),
  KEY `fk_idPermiso` (`idPermiso`),
  CONSTRAINT `fk_idPerfil` FOREIGN KEY (`idPerfil`) REFERENCES `perfil` (`idPerfil`),
  CONSTRAINT `fk_idPermiso` FOREIGN KEY (`idPermiso`) REFERENCES `permiso` (`idPermiso`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `permisoperfil`
--

LOCK TABLES `permisoperfil` WRITE;
/*!40000 ALTER TABLE `permisoperfil` DISABLE KEYS */;
INSERT INTO `permisoperfil` VALUES (1,1,1),(2,1,2),(3,1,3),(4,1,4),(5,1,5),(6,1,6),(7,1,7),(8,1,8),(9,1,9),(10,1,10),(11,1,11),(12,1,12),(13,1,13),(14,1,14),(15,1,15),(16,1,16),(17,1,17),(18,1,18),(19,1,19),(20,1,20),(21,1,21);
/*!40000 ALTER TABLE `permisoperfil` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `producto`
--

DROP TABLE IF EXISTS `producto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `producto` (
  `idProducto` int(11) NOT NULL AUTO_INCREMENT,
  `Precio` decimal(10,3) NOT NULL,
  `Nombre` varchar(45) NOT NULL,
  `Descripcion` varchar(200) NOT NULL,
  `Proveedor` varchar(50) NOT NULL,
  `Tipo` varchar(20) NOT NULL,
  PRIMARY KEY (`idProducto`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `producto`
--

LOCK TABLES `producto` WRITE;
/*!40000 ALTER TABLE `producto` DISABLE KEYS */;
INSERT INTO `producto` VALUES (1,122.000,'productoA','productoA','A','Comida'),(2,444.000,'productoB','productoB','B','Comida');
/*!40000 ALTER TABLE `producto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reservacuarto`
--

DROP TABLE IF EXISTS `reservacuarto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `reservacuarto` (
  `idReservaCuarto` int(11) NOT NULL AUTO_INCREMENT,
  `idCliente` int(11) NOT NULL,
  `idUsuario` int(11) NOT NULL,
  `idCuarto` int(11) NOT NULL,
  `Senia` decimal(20,3) NOT NULL,
  `MontoReservaCuarto` decimal(20,3) NOT NULL,
  `EmailFacturacion` varchar(50) NOT NULL,
  `FechaReserva` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `FechaCheckIn` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `FechaIngreso` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `FechaOut` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `FechaEgreso` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `FormaPago` enum('EFECTIVO','DEBITO','CREDITO') NOT NULL,
  `TipoTarjeta` enum('VISA','MASTERCARD','NO') NOT NULL,
  `NumeroTarjeta` varchar(25) DEFAULT NULL,
  `FechaVencTarjeta` varchar(15) DEFAULT NULL,
  `CodSeguridadTarjeta` varchar(10) DEFAULT NULL,
  `EstadoReserva` enum('PENDIENTE','CANCELADO','EN_CURSO','FINALIZADO') NOT NULL,
  `Comentarios` varchar(200) DEFAULT NULL,
  `Estado` tinyint(1) NOT NULL,
  PRIMARY KEY (`idReservaCuarto`),
  KEY `fk_clienteId` (`idCliente`),
  KEY `fk_id_Usuario` (`idUsuario`),
  KEY `fk_idCuarto` (`idCuarto`),
  CONSTRAINT `fk_clienteId` FOREIGN KEY (`idCliente`) REFERENCES `cliente` (`idCliente`),
  CONSTRAINT `fk_idCuarto` FOREIGN KEY (`idCuarto`) REFERENCES `cuarto` (`idCuarto`),
  CONSTRAINT `fk_id_Usuario` FOREIGN KEY (`idUsuario`) REFERENCES `usuario` (`idUsuario`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reservacuarto`
--

LOCK TABLES `reservacuarto` WRITE;
/*!40000 ALTER TABLE `reservacuarto` DISABLE KEYS */;
INSERT INTO `reservacuarto` VALUES (1,1,1,1,10.000,87900.000,'cecialf1991@gmail.com','2020-06-27 23:00:00','2020-06-27 23:07:37','2020-06-28 00:00:00','2020-06-27 23:07:37','2020-07-10 06:00:00','EFECTIVO','NO','0','0','0','PENDIENTE','',0),(2,1,1,1,10.000,27000.000,'alfonsocecilia91@gmail.com','2020-06-28 13:00:00','2020-06-28 13:43:13','2020-06-28 14:00:00','2020-06-28 13:43:13','2020-07-02 09:00:00','EFECTIVO','NO','0','0','0','PENDIENTE','a',1),(3,2,1,1,10.000,50700.000,'cecialf1991@gmail.com','2020-06-28 14:00:00','2020-06-28 14:10:54','2020-07-03 04:00:00','2020-06-28 14:10:54','2020-07-10 06:00:00','EFECTIVO','NO','0','0','0','PENDIENTE','s',1),(4,2,1,2,42350.000,169400.000,'cecialf1991@hotmail.com','2020-06-28 21:00:00','2020-06-29 07:00:00','2020-06-29 05:00:00','2020-07-02 09:00:00','2020-07-09 09:00:00','EFECTIVO','NO','0','0','0','FINALIZADO','w',0),(5,2,1,3,25.000,1500.000,'cecialf1991@gmail.com','2020-06-28 22:00:00','2020-06-29 01:00:00','2020-06-29 00:00:00','2020-06-29 04:00:00','2020-06-29 05:00:00','EFECTIVO','NO','0','0','0','FINALIZADO','a',0),(6,2,1,2,25.000,2800.000,'cecialf1991@gmail.com','2020-06-28 23:00:00','2020-06-28 23:23:35','2020-06-29 00:00:00','2020-06-28 23:23:35','2020-06-29 05:00:00','EFECTIVO','NO','0','0','0','PENDIENTE','1',1),(7,3,1,2,25.000,48300.000,'cecialf1991@hotmail.com','2020-06-29 00:00:00','2020-07-10 06:00:00','2020-07-09 10:00:00','2020-07-03 09:00:00','2020-07-12 10:00:00','EFECTIVO','NO','0','0','0','FINALIZADO','b',0);
/*!40000 ALTER TABLE `reservacuarto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reservaevento`
--

DROP TABLE IF EXISTS `reservaevento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `reservaevento` (
  `idReservaEvento` int(11) NOT NULL AUTO_INCREMENT,
  `idCliente` int(11) NOT NULL,
  `idUsuario` int(11) NOT NULL,
  `idSalon` int(11) NOT NULL,
  `idCategoriaEvento` int(11) NOT NULL,
  `TipoTarjeta` enum('VISA','MASTERCARD','NO') NOT NULL,
  `CodSeguridadTarjeta` varchar(10) NOT NULL,
  `FechaVencTarjeta` varchar(15) NOT NULL,
  `NumeroTarjeta` varchar(25) NOT NULL,
  `FormaPago` enum('EFECTIVO','DEBITO','CREDITO') NOT NULL,
  `MontoTotal` decimal(20,3) NOT NULL,
  `MontoReservaEvento` decimal(20,3) NOT NULL,
  `Senia` decimal(20,3) NOT NULL,
  `FechaGeneracionReserva` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `FechaInicioReserva` timestamp NULL DEFAULT NULL,
  `FechaFinReserva` timestamp NULL DEFAULT NULL,
  `FechaIngreso` timestamp NULL DEFAULT NULL,
  `FechaEgreso` timestamp NULL DEFAULT NULL,
  `EstadoReserva` enum('PENDIENTE','CANCELADO','EN_CURSO','FINALIZADO') NOT NULL,
  `Observaciones` varchar(200) NOT NULL,
  PRIMARY KEY (`idReservaEvento`),
  KEY `idCliente` (`idCliente`),
  KEY `idUsuario` (`idUsuario`),
  KEY `idSalon` (`idSalon`),
  KEY `idCategoriaEvento` (`idCategoriaEvento`),
  CONSTRAINT `reservaevento_ibfk_1` FOREIGN KEY (`idSalon`) REFERENCES `salon` (`idSalon`),
  CONSTRAINT `reservaevento_ibfk_2` FOREIGN KEY (`idUsuario`) REFERENCES `usuario` (`idUsuario`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `reservaevento_ibfk_3` FOREIGN KEY (`idCliente`) REFERENCES `cliente` (`idCliente`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `reservaevento_ibfk_4` FOREIGN KEY (`idCategoriaEvento`) REFERENCES `categoriaevento` (`idCategoriaEvento`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reservaevento`
--

LOCK TABLES `reservaevento` WRITE;
/*!40000 ALTER TABLE `reservaevento` DISABLE KEYS */;
INSERT INTO `reservaevento` VALUES (1,1,1,19,1,'NO','0','0','0','EFECTIVO',6500.000,1300.000,1495.000,'2020-06-28 13:39:01','2020-06-30 05:00:00','2020-06-30 11:00:00',NULL,NULL,'PENDIENTE','Cumple de Abuelo');
/*!40000 ALTER TABLE `reservaevento` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `salon`
--

DROP TABLE IF EXISTS `salon`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `salon` (
  `idSalon` int(11) NOT NULL AUTO_INCREMENT,
  `Capacidad` int(5) NOT NULL,
  `Senia` int(20) NOT NULL,
  `Estilo` varchar(150) NOT NULL,
  `Monto` decimal(20,3) NOT NULL,
  `Estado` tinyint(1) NOT NULL,
  PRIMARY KEY (`idSalon`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `salon`
--

LOCK TABLES `salon` WRITE;
/*!40000 ALTER TABLE `salon` DISABLE KEYS */;
INSERT INTO `salon` VALUES (19,150,23,'Amplio salon 150 personas',1300.000,1),(20,50,40,'Salon pequeño',349.990,1),(21,300,35,'Salon grande',2300.990,1);
/*!40000 ALTER TABLE `salon` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ticket`
--

DROP TABLE IF EXISTS `ticket`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ticket` (
  `idTicket` int(11) NOT NULL AUTO_INCREMENT,
  `idCliente` int(10) NOT NULL,
  `precioTotal` decimal(10,3) NOT NULL,
  `descripcion` varchar(300) DEFAULT NULL,
  `path` varchar(300) DEFAULT NULL,
  `FechaReserva` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`idTicket`),
  KEY `fk_idCliente` (`idCliente`),
  CONSTRAINT `fk_idCliente` FOREIGN KEY (`idCliente`) REFERENCES `cliente` (`idCliente`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ticket`
--

LOCK TABLES `ticket` WRITE;
/*!40000 ALTER TABLE `ticket` DISABLE KEYS */;
INSERT INTO `ticket` VALUES (1,1,29996.000,'descripcion','/tickets/reserva/ticketReserva_Label[id=idReserva, styleClass=label]\'2\'_1.pdf','2020-06-28 13:52:27'),(2,1,244.000,'descripcion','/tickets/ordenPedido/ticketPedido_2_2.pdf','2020-06-28 13:54:04'),(3,3,48300.000,'descripcion','/tickets/reserva/ticketReserva_Label[id=idReserva, styleClass=label]\'7\'_3.pdf','2020-06-29 01:09:12'),(4,2,1500.000,'descripcion','/tickets/reserva/ticketReserva_Label[id=idReserva, styleClass=label]\'5\'_4.pdf','2020-06-29 01:20:34'),(5,2,169400.000,'descripcion','/tickets/reserva/ticketReserva_Label[id=idReserva, styleClass=label]\'4\'_5.pdf','2020-06-29 01:59:00'),(6,1,91140.000,'descripcion','/tickets/reserva/ticketReserva_Label[id=idReserva, styleClass=label]\'1\'_6.pdf','2020-06-29 20:14:57');
/*!40000 ALTER TABLE `ticket` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `usuario` (
  `idUsuario` int(11) NOT NULL AUTO_INCREMENT,
  `idPerfil` int(11) NOT NULL,
  `Nombre` varchar(45) NOT NULL,
  `Apellido` varchar(45) NOT NULL,
  `TipoDocumento` varchar(20) NOT NULL,
  `Documento` varchar(20) NOT NULL,
  `Email` varchar(35) NOT NULL,
  `Password` varchar(35) NOT NULL,
  `Estado` tinyint(1) NOT NULL,
  PRIMARY KEY (`idUsuario`),
  KEY `fk_id_Perfil` (`idPerfil`),
  CONSTRAINT `fk_id_Perfil` FOREIGN KEY (`idPerfil`) REFERENCES `perfil` (`idPerfil`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` VALUES (1,1,'Administrador','Administrador','DNI','38784589','a@gmail.com','123',1);
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-07-05 18:45:49
