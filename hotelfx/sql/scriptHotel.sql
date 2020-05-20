CREATE DATABASE `hotel`;
USE hotel;
CREATE TABLE `cliente`
(
  `idCliente` int(11) NOT NULL AUTO_INCREMENT,
  `Nombre` varchar(45) NOT NULL,
  `Apellido` varchar(45) NOT NULL,
  `TipoDocumento` varchar(20) NOT NULL,
  `Documento` varchar(20) NOT NULL,
  `Email` varchar(35) NOT NULL,
  `Telefono` varchar(20) NOT NULL,
  `Estado` boolean NOT NULL,
  `FechaNacimiento` date NOT NULL,
  PRIMARY KEY (`idCliente`)
);

CREATE TABLE `ticket`
(
  `idTicket` int(11) NOT NULL AUTO_INCREMENT,
  `idCliente` int(10) NOT NULL,
  `Cantidad` int(10) NOT NULL,
  `PrecioTotal` int(10) NOT NULL,
  PRIMARY KEY (`idTicket`),
  CONSTRAINT FOREIGN KEY fk_idCliente (idCliente) REFERENCES cliente (idCliente)
);

CREATE TABLE `producto`
(
  `idProducto` int(11) NOT NULL AUTO_INCREMENT,
  `Precio` int(11) NOT NULL,
  `Nombre` varchar(45) NOT NULL,
  `Descripcion` varchar(200) NOT NULL,
  `Proveedor` varchar(50) NOT NULL,
  `Tipo` varchar(20) NOT NULL,
  PRIMARY KEY (`idProducto`)
);

CREATE TABLE `perfil`
(
  `idPerfil` int(11) NOT NULL AUTO_INCREMENT,
  `NombrePerfil` varchar(45) NOT NULL,
  PRIMARY KEY (`idPerfil`)
);

CREATE TABLE `permiso`
(
  `idPermiso` int(11) NOT NULL AUTO_INCREMENT,
  `NombrePermiso` varchar(45) NOT NULL,
  PRIMARY KEY (`idPermiso`)
);

CREATE TABLE `permisoPerfil`
(
  `idPermisoPerfil` int(11) NOT NULL AUTO_INCREMENT,
  `idPerfil` int(11) NOT NULL,
  `idPermiso` int(11) NOT NULL,
  PRIMARY KEY (`idPermisoPerfil`),
  CONSTRAINT FOREIGN KEY fk_idPerfil (idPerfil) REFERENCES perfil (idPerfil),
  CONSTRAINT FOREIGN KEY fk_idPermiso (idPermiso) REFERENCES permiso (idPermiso)
);

CREATE TABLE `usuario`
(
  `idUsuario` int(11) NOT NULL AUTO_INCREMENT,
  `idPerfil` int(11) NOT NULL,
  `Nombre` varchar(45) NOT NULL,
  `Apellido` varchar(45) NOT NULL,
  `TipoDocumento` varchar(20) NOT NULL,
  `Documento` varchar(20) NOT NULL,
  `Email` varchar(35) NOT NULL,
  `Password` varchar(35)NOT NULL,
  `Estado` boolean NOT NULL,
  PRIMARY KEY (`idUsuario`),
  CONSTRAINT FOREIGN KEY fk_id_Perfil (idPerfil) REFERENCES perfil (idPerfil)
);

CREATE TABLE `ordenPedido`
(
  `idOrdenPedido` int(11) NOT NULL AUTO_INCREMENT,
  `idProducto` int(11) NOT NULL,
  `idCliente` int(11) NOT NULL,
  `idUsuario` int(11) NOT NULL,
  `Cantidad` int(20) NOT NULL,
  `PrecioTotal` int(20) NOT NULL,
  PRIMARY KEY (`idOrdenPedido`),
  CONSTRAINT FOREIGN KEY fk_idProducto (idProducto) REFERENCES producto (idProducto),
  CONSTRAINT FOREIGN KEY fk_id_Cliente (idCliente) REFERENCES cliente (idCliente),
  CONSTRAINT FOREIGN KEY fk_idUsuario (idUsuario) REFERENCES usuario (idUsuario)
);

CREATE TABLE `categoriaCuarto`
(
  `idCategoriaCuarto` int(11) NOT NULL AUTO_INCREMENT,
  `Nombre` varchar(45) NOT NULL,
  `Detalle` varchar(100) NOT NULL,
  PRIMARY KEY (`idCategoriaCuarto`)
);

CREATE TABLE `cuarto`
(
  `idCuarto` int(11) NOT NULL AUTO_INCREMENT,
  `idCategoriaCuarto` int(11) NOT NULL,
  `Capacidad` int(5) NOT NULL,
  `Tamanio` int(5) NOT NULL,
  `Monto` double(10,3) NOT NULL,
  `MontoSenia` double(10,3) NOT NULL,
  `Estado` boolean NOT NULL,
  PRIMARY KEY (`idCuarto`),
  CONSTRAINT FOREIGN KEY fk_idCategoriaCuarto (idCategoriaCuarto) REFERENCES categoriaCuarto (idCategoriaCuarto)
);

CREATE TABLE `categoriaEvento`
(
  `idCategoriaEvento` int(11) NOT NULL AUTO_INCREMENT,
  `Nombre` varchar(45) NOT NULL,
  `Detalle` varchar(100) NOT NULL,
  PRIMARY KEY (`idCategoriaEvento`)
);

CREATE TABLE `salon`
(
  `idSalon` int(11) NOT NULL AUTO_INCREMENT,
  `Capacidad` int(5) NOT NULL,
  `Senia` double(10,3) NOT NULL,
  `Monto` double(10,3) NOT NULL,
  `Estado` boolean NOT NULL,
  PRIMARY KEY (`idSalon`)
);

CREATE TABLE `reservaEvento`
(
  `idReservaEvento` int(11) NOT NULL AUTO_INCREMENT,
  `idCliente` int(11) NOT NULL,
  `idUsuario` int(11) NOT NULL,
  `idSalon` int(11) NOT NULL,
  `idCategoriaEvento` int(11) NOT NULL,
  `Senia` double(10,3) NOT NULL,
  `FechaGeneracionReserva` dateTime NOT NULL,
  `FechaInicioReserva` dateTime NOT NULL,
  `FechaFinReserva` dateTime NOT NULL,
  `FechaIngreso` dateTime NOT NULL,
  `FechaEgreso` dateTime NOT NULL,
  `FormaPago` varchar(20) NOT NULL,
  `TipoTarjeta` varchar(25) NOT NULL,
  `NumeroTarjeta` varchar(25) NOT NULL,
  `FechaVencTarjeta` varchar(15) NOT NULL,
  `CodSeguridadTarjeta` varchar(10) NOT NULL,
  `EstadoReserva` varchar(20) NOT NULL,
  `Observaciones` varchar(200) NOT NULL,
  PRIMARY KEY (`idReservaEvento`)
);

CREATE TABLE `reservaCuarto`
(
  `idReservaCuarto` int(11) NOT NULL AUTO_INCREMENT,
  `idCliente` int(11) NOT NULL,
  `idUsuario` int(11) NOT NULL,
  `idCuarto` int(11) NOT NULL,
  `Senia` double(10,3) NOT NULL,
  `MontoReservaCuarto` double(10,3) NOT NULL,
  `EmailFacturacion` varchar(50) NOT NULL,
  `FechaReserva` dateTime NOT NULL,
  `FechaCheckIn` dateTime NOT NULL,
  `FechaIngreso` dateTime NOT NULL,
  `FechaOut` dateTime NOT NULL,
  `FechaEgreso` dateTime NOT NULL,
  `FormaPago` varchar(20) NOT NULL,
  `TipoTarjeta` varchar(25) NOT NULL,
  `NumeroTarjeta` varchar(25) NOT NULL,
  `FechaVencTarjeta` varchar(15) NOT NULL,
  `CodSeguridadTarjeta` varchar(10) NOT NULL,
  `EstadoReserva` varchar(20) NOT NULL,
  `Comentarios` varchar(200) NOT NULL,
  PRIMARY KEY (`idReservaCuarto`),
  CONSTRAINT FOREIGN KEY fk_clienteId (idCliente) REFERENCES cliente (idCliente),
  CONSTRAINT FOREIGN KEY fk_id_Usuario (idUsuario) REFERENCES usuario (idUsuario),
  CONSTRAINT FOREIGN KEY fk_idCuarto (idCuarto) REFERENCES cuarto (idCuarto)
);

CREATE TABLE `notificacion`
(
  `idNotificacion` int(11) NOT NULL AUTO_INCREMENT,
  `idCliente` int(11) NOT NULL,
  `Detalle` varchar(45) NOT NULL,
  `Estado` varchar(20) NOT NULL,
  `Tipo` varchar(20) NOT NULL,
  PRIMARY KEY (`idNotificacion`),
  CONSTRAINT FOREIGN KEY fk_cliente_id (idCliente) REFERENCES cliente (idCliente)
);

CREATE TABLE `encuesta`
(
  `idEncuesta` int(11) NOT NULL AUTO_INCREMENT,
  `idCliente` int(11) NOT NULL,
  `Detalle` varchar(45) NOT NULL,
  `Estado` varchar(20) NOT NULL,
  `FechaEnvio` dateTime NOT NULL,
  `FechaRecepcion` dateTime NOT NULL,
  PRIMARY KEY (`idEncuesta`),
  CONSTRAINT FOREIGN KEY fk_IdCli (idCliente) REFERENCES cliente (idCliente)
);