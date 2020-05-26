package dto;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

public class ReservaCuartoDTO {

	private Integer idReserva,idCliente,idCuarto,idUsuario;
	private BigDecimal senia,montoReservaCuarto;
	private String emailFacturacion, numTarjeta, cantidadDias,
	tipoTarjeta, formaDePago, codSeguridadTarjeta,comentarios;
	private Date fechaVencTarjeta,fechaFacturacion,fechaCheckIn,fechaOut,
	fechaIngreso,fechaEgreso;
	
	private boolean estadoReserva;
	/*
	 `idReservaCuarto` int(11) NOT NULL AUTO_INCREMENT,
	  `idCliente` int(11) NOT NULL,
	  `idUsuario` int(11) NOT NULL,
	  `idCuarto` int(11) NOT NULL,
	  `Senia` decimal(10,3) NOT NULL,
	  `MontoReservaCuarto` decimal(10,3) NOT NULL,
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
	  CONSTRAINT FOREIGN KEY fk_idCuarto (idCuarto) REFERENCES cuarto (idCuarto)*/
	
	public ReservaCuartoDTO(int idCliente, int idCuarto, int idUsuario, BigDecimal senia,
			BigDecimal montoReservaCuarto,String emailFacturacion,String numTarjeta,
			Date fechaVencTarjeta,Date fechaFacturacion,Date fechaCheckIn, Date fechaOut,
			Date fechaIngreso, Date fechaEgreso,String comentarios , String formaDePago,
			String tipoTarjeta,String codSeguridadTarjeta) {
		
		this.idCliente = idCliente;
		this.idCuarto = idCuarto;
		this.idUsuario = idUsuario;
		this.senia = senia;
		this.montoReservaCuarto = montoReservaCuarto;
		this.emailFacturacion = emailFacturacion;
		this.numTarjeta = numTarjeta;
		this.fechaFacturacion = fechaFacturacion;
		this.fechaCheckIn = fechaCheckIn;
		this.fechaOut = fechaOut;
		this.fechaIngreso = fechaIngreso;
		this.fechaEgreso = fechaEgreso;
		this.codSeguridadTarjeta = codSeguridadTarjeta;
		this.formaDePago = formaDePago;
		this.tipoTarjeta = tipoTarjeta;
	}
	
	public Integer getIdReserva() {
		return idReserva;
	}

	public void setIdReserva(Integer idReserva) {
		this.idReserva = idReserva;
	}

	public Integer getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(Integer idCliente) {
		this.idCliente = idCliente;
	}

	public Integer getIdCuarto() {
		return idCuarto;
	}

	public void setIdCuarto(Integer idCuarto) {
		this.idCuarto = idCuarto;
	}

	public Integer getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}

	public double getSenia() {
		return senia.doubleValue();
	}

	public void setSenia(BigDecimal senia) {
		this.senia = senia;
	}

	public double getMontoReservaCuarto() {
		return montoReservaCuarto.doubleValue();
	}

	public void setMontoReservaCuarto(BigDecimal montoReservaCuarto) {
		this.montoReservaCuarto = montoReservaCuarto;
	}

	public String getEmailFacturacion() {
		return emailFacturacion;
	}

	public void setEmailFacturacion(String emailFacturacion) {
		this.emailFacturacion = emailFacturacion;
	}

	public String getNumTarjeta() {
		return numTarjeta;
	}

	public void setNumTarjeta(String numTarjeta) {
		this.numTarjeta = numTarjeta;
	}

	public String getCantidadDias() {
		return cantidadDias;
	}

	public void setCantidadDias(String cantidadDias) {
		this.cantidadDias = cantidadDias;
	}

	public String getComentarios() {
		return comentarios;
	}

	public void setComentarios(String comentarios) {
		this.comentarios = comentarios;
	}

	public Date getFechaFacturacion() {
		return fechaFacturacion;
	}

	public void setFechaFacturacion(Date fechaFacturacion) {
		this.fechaFacturacion = fechaFacturacion;
	}

	public Date getFechaCheckIn() {
		return fechaCheckIn;
	}

	public void setFechaCheckIn(Date fechaCheckIn) {
		this.fechaCheckIn = fechaCheckIn;
	}

	public Date getFechaOut() {
		return fechaOut;
	}

	public void setFechaOut(Date fechaOut) {
		this.fechaOut = fechaOut;
	}

	public Date getFechaIngreso() {
		return fechaIngreso;
	}

	public void setFechaIngreso(Date fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
	}

	public Date getFechaEgreso() {
		return fechaEgreso;
	}

	public void setFechaEgreso(Date fechaEgreso) {
		this.fechaEgreso = fechaEgreso;
	}

	public String getTiposTarjeta() {
		return tipoTarjeta;
	}

	public void setTiposTarjeta(String tiposTarjeta) {
		this.tipoTarjeta = tipoTarjeta;
	}

	public String getFormasDePago() {
		return formaDePago;
	}

	public void setFormasDePago(String formasDePago) {
		this.formaDePago = formasDePago;
	}
}
