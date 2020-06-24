CREATE DATABASE `hotel`;
USE hotel;

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";

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
  `precioTotal` decimal(10,3) NOT NULL,
  `descripcion` varchar(300),
  `path` varchar(300),
  `FechaReserva` Timestamp NOT NULL,
  PRIMARY KEY (`idTicket`),
  CONSTRAINT FOREIGN KEY fk_idCliente (idCliente) REFERENCES cliente (idCliente)
);

CREATE TABLE `producto`
(
  `idProducto` int(11) NOT NULL AUTO_INCREMENT,
  `Precio` Decimal(10,3) NOT NULL,
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

INSERT INTO `permiso` (nombrePermiso) VALUES ('ABM Usuarios');
INSERT INTO `permiso` (nombrePermiso) VALUES ('ABM Clientes');
INSERT INTO `permiso` (nombrePermiso) VALUES ('ABM Cuartos');
INSERT INTO `permiso` (nombrePermiso) VALUES ('ABM Productos');
INSERT INTO `permiso` (nombrePermiso) VALUES ('ABM Reserva cuarto');
INSERT INTO `permiso` (nombrePermiso) VALUES ('ABM Reserva evento ');
INSERT INTO `permiso` (nombrePermiso) VALUES ('Importar reservas');
INSERT INTO `permiso` (nombrePermiso) VALUES ('ABM Categoria evento');
INSERT INTO `permiso` (nombrePermiso) VALUES ('ABM Perfiles');
INSERT INTO `permiso` (nombrePermiso) VALUES ('ABM Categoria cuarto');
INSERT INTO `permiso` (nombrePermiso) VALUES ('ABM Salones');
INSERT INTO `permiso` (nombrePermiso) VALUES ('Configuracion email');
INSERT INTO `permiso` (nombrePermiso) VALUES ('ABM Orden pedidos');
INSERT INTO `permiso` (nombrePermiso) VALUES ('Gestion backup');

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

INSERT INTO `perfil` (nombrePerfil) VALUES ('full');
INSERT INTO `usuario` (idPerfil,Nombre,Apellido,TipoDocumento,Documento,Email,Password,Estado) VALUES (1,'Administrador','Administrador','DNI','38784589','carpinchocorp@gmail.com','123',1);
INSERT INTO `permisoPerfil` (idPerfil,IdPermiso) VALUES (1,1);
INSERT INTO `permisoPerfil` (idPerfil,IdPermiso) VALUES (1,2);
INSERT INTO `permisoPerfil` (idPerfil,IdPermiso) VALUES (1,3);
INSERT INTO `permisoPerfil` (idPerfil,IdPermiso) VALUES (1,4);
INSERT INTO `permisoPerfil` (idPerfil,IdPermiso) VALUES (1,5);
INSERT INTO `permisoPerfil` (idPerfil,IdPermiso) VALUES (1,6);
INSERT INTO `permisoPerfil` (idPerfil,IdPermiso) VALUES (1,7);
INSERT INTO `permisoPerfil` (idPerfil,IdPermiso) VALUES (1,8);
INSERT INTO `permisoPerfil` (idPerfil,IdPermiso) VALUES (1,9);
INSERT INTO `permisoPerfil` (idPerfil,IdPermiso) VALUES (1,10);
INSERT INTO `permisoPerfil` (idPerfil,IdPermiso) VALUES (1,11);
INSERT INTO `permisoPerfil` (idPerfil,IdPermiso) VALUES (1,12);
INSERT INTO `permisoPerfil` (idPerfil,IdPermiso) VALUES (1,13);
INSERT INTO `permisoPerfil` (idPerfil,IdPermiso) VALUES (1,14);

CREATE TABLE `ordenPedido`
(
  `idOrdenPedido` int(11) NOT NULL AUTO_INCREMENT,
  `idProducto` int(11) NOT NULL,
  `idCliente` int(11) NOT NULL,
  `idUsuario` int(11) NOT NULL,
  `Cantidad` int(20) NOT NULL,
  `PrecioTotal` decimal(10,3) NOT NULL,
  `FormaPago` varchar(20),
  `TipoTarjeta` varchar(25),
  `NumeroTarjeta` varchar(25),
  `FechaVencTarjeta` varchar(15),
  `CodSeguridadTarjeta` varchar(10),
  `esRestoran` boolean NOT NULL,
  PRIMARY KEY (`idOrdenPedido`, `idProducto`),
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
  `Monto` double(10,3) NOT NULL,
  `MontoSenia` double(10,3) NOT NULL,
  `Piso` varchar(5) NOT NULL,   
  `Habitacion` varchar(5) NOT NULL,
  `Estado` boolean NOT NULL,
  PRIMARY KEY (`idCuarto`),
  CONSTRAINT FOREIGN KEY fk_idCategoriaCuarto (idCategoriaCuarto) REFERENCES categoriaCuarto (idCategoriaCuarto)
);

CREATE TABLE `reservacuarto` (
  `idReservaCuarto` int(11) NOT NULL,
  `idCliente` int(11) NOT NULL,
  `idUsuario` int(11) NOT NULL,
  `idCuarto` int(11) NOT NULL,
  `Senia` decimal(10,3) NOT NULL,
  `MontoReservaCuarto` decimal(10,3) NOT NULL,
  `EmailFacturacion` varchar(50) NOT NULL,
  `FechaReserva` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
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
  `Estado` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Indices de la tabla `reservacuarto`
--

ALTER TABLE `reservacuarto`
  ADD PRIMARY KEY (`idReservaCuarto`),
  ADD KEY `fk_clienteId` (`idCliente`),
  ADD KEY `fk_id_Usuario` (`idUsuario`),
  ADD KEY `fk_idCuarto` (`idCuarto`);

--
-- AUTO_INCREMENT de la tabla `reservacuarto`
--

ALTER TABLE `reservacuarto`
  MODIFY `idReservaCuarto` int(11) NOT NULL AUTO_INCREMENT;

--
-- Filtros para la tabla `reservacuarto`
--

ALTER TABLE `reservacuarto`
  ADD CONSTRAINT `fk_clienteId` FOREIGN KEY (`idCliente`) REFERENCES `cliente` (`idCliente`),
  ADD CONSTRAINT `fk_idCuarto` FOREIGN KEY (`idCuarto`) REFERENCES `cuarto` (`idCuarto`),
  ADD CONSTRAINT `fk_id_Usuario` FOREIGN KEY (`idUsuario`) REFERENCES `usuario` (`idUsuario`);
COMMIT;

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
  `recipiente` varchar(60) NOT NULL,
  `encuestado` boolean NOT NULL,
  PRIMARY KEY (`idEncuesta`),
  CONSTRAINT FOREIGN KEY fk_cliente_id_encuesta (idCliente) REFERENCES cliente (idCliente)
);
-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `categoriaevento`
--

CREATE TABLE `categoriaevento` (
  `idCategoriaEvento` int(11) NOT NULL,
  `Nombre` varchar(45) NOT NULL,
  `Detalle` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `categoriaevento`
--

INSERT INTO `categoriaevento` (`idCategoriaEvento`, `Nombre`, `Detalle`) VALUES
(1, 'Cumpleaños', 'Un cumple con papitas y pancho'),
(2, 'Casamiento', 'Con torta y musica'),
(3, 'Infantil', 'Algo infantil con lo basico');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `reservaevento`
--

CREATE TABLE `reservaevento` (
  `idReservaEvento` int(11) NOT NULL,
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
  `FechaGeneracionReserva` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `FechaInicioReserva` timestamp NULL DEFAULT NULL,
  `FechaFinReserva` timestamp NULL DEFAULT NULL,
  `FechaIngreso` timestamp NULL DEFAULT NULL,
  `FechaEgreso` timestamp NULL DEFAULT NULL,
  `EstadoReserva` enum('PENDIENTE','CANCELADO','EN_CURSO','FINALIZADO') NOT NULL,
  `Observaciones` varchar(200) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `salon`
--

CREATE TABLE `salon` (
  `idSalon` int(11) NOT NULL,
  `Capacidad` int(5) NOT NULL,
  `Senia` int(20) NOT NULL,
  `Estilo` varchar(150) NOT NULL,
  `Monto` decimal(20,3) NOT NULL,
  `Estado` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `salon`
--

INSERT INTO `salon` (`idSalon`, `Capacidad`, `Senia`, `Estilo`, `Monto`, `Estado`) VALUES
(19, 150, 23, 'Amplio salon 150 personas', '1300.000', 1),
(20, 50, 40, 'Salon pequeño ', '349.990', 1),
(21, 300, 35, 'Salon grande', '2300.990', 1);


CREATE TABLE `email`(
  `idEmail` int(11) NOT NULL AUTO_INCREMENT, 
  `fechaCreacion` datetime NOT NULL,
  `Texto` VARCHAR(500) NOT NULL,
  `Asunto` VARCHAR(50) NOT NULL,
  `Emisor` VARCHAR(50) NOT NULL,
  `Receptor` VARCHAR(50) NOT NULL,
  `Estado` TINYINT(1) NOT NULL,
  `Pass` VARCHAR(50) NOT NULL,

  PRIMARY KEY (`idEmail`)
);

CREATE TABLE `configuracion`
(
  `idConfig` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `provSMTP` varchar(100) NOT NULL,
  PRIMARY KEY (`idConfig`)
);

insert into configuracion(username, password, provSMTP)
values ("carpinchocorp@gmail.com", "covid-19", "smtp.gmail.com");

CREATE TABLE `errorImportar`
(
  `idError` int(11) NOT NULL AUTO_INCREMENT,
  `fecha` datetime NOT NULL,
  `usuario` int(11) NOT NULL,
  `detalle` varchar(500) NOT NULL,
  PRIMARY KEY (`idError`)
);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `categoriaevento`
--
ALTER TABLE `categoriaevento`
  ADD PRIMARY KEY (`idCategoriaEvento`);

--
-- Indices de la tabla `reservaevento`
--
ALTER TABLE `reservaevento`
  ADD PRIMARY KEY (`idReservaEvento`),
  ADD KEY `idCliente` (`idCliente`),
  ADD KEY `idUsuario` (`idUsuario`),
  ADD KEY `idSalon` (`idSalon`),
  ADD KEY `idCategoriaEvento` (`idCategoriaEvento`);

--
-- Indices de la tabla `salon`
--
ALTER TABLE `salon`
  ADD PRIMARY KEY (`idSalon`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `categoriaevento`
--
ALTER TABLE `categoriaevento`
  MODIFY `idCategoriaEvento` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `reservaevento`
--
ALTER TABLE `reservaevento`
  MODIFY `idReservaEvento` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `salon`
--
ALTER TABLE `salon`
  MODIFY `idSalon` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `reservaevento`
--
ALTER TABLE `reservaevento`
  ADD CONSTRAINT `reservaevento_ibfk_1` FOREIGN KEY (`idSalon`) REFERENCES `salon` (`idSalon`),
  ADD CONSTRAINT `reservaevento_ibfk_2` FOREIGN KEY (`idUsuario`) REFERENCES `usuario` (`idUsuario`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `reservaevento_ibfk_3` FOREIGN KEY (`idCliente`) REFERENCES `cliente` (`idCliente`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `reservaevento_ibfk_4` FOREIGN KEY (`idCategoriaEvento`) REFERENCES `categoriaevento` (`idCategoriaEvento`) ON DELETE CASCADE ON UPDATE CASCADE;
  

  
  
  