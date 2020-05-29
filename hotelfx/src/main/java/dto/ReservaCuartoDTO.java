package dto;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

public class ReservaCuartoDTO {

	private Integer idReserva,idCliente,idCuarto,idUsuario;
	private BigDecimal senia,montoReservaCuarto;
	private String emailFacturacion, numTarjeta, cantidadDias,
	tipoTarjeta, formaDePago, codSeguridadTarjeta,comentarios;
	private Date fechaVencTarjeta,fechaReserva,fechaCheckIn,fechaOut,
	fechaIngreso,fechaEgreso;
	
	
	private boolean estadoReserva;

	public ReservaCuartoDTO(int idCliente, int idCuarto, int idUsuario, BigDecimal senia,
			BigDecimal montoReservaCuarto,String emailFacturacion,String numTarjeta, 
			String formaDePago,	String tipoTarjeta,String codSeguridadTarjeta,
			Date fechaVencTarjeta,Date fechaReserva,Date fechaCheckIn, Date fechaOut,
			Date fechaIngreso, Date fechaEgreso , 
		 boolean estado, String comentarios) {
		
		this.idCliente = idCliente;
		this.idCuarto = idCuarto;
		this.idUsuario = idUsuario;
		this.senia = senia;
		this.montoReservaCuarto = montoReservaCuarto;
		this.emailFacturacion = emailFacturacion;
		this.numTarjeta = numTarjeta;
		this.fechaReserva = fechaReserva;
		this.fechaCheckIn = fechaCheckIn;
		this.fechaOut = fechaOut;
		this.fechaIngreso = fechaIngreso;
		this.fechaEgreso = fechaEgreso;
		this.codSeguridadTarjeta = codSeguridadTarjeta;
		this.formaDePago = formaDePago;
		this.tipoTarjeta = tipoTarjeta;
		this.estadoReserva = estado;
		this.comentarios = comentarios;
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

	public BigDecimal getSenia() {
		return senia;
	}

	public void setSenia(BigDecimal senia) {
		this.senia = senia;
	}

	public BigDecimal getMontoReservaCuarto() {
		return montoReservaCuarto;
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

	public Date getFechaReserva() {
		return fechaReserva;
	}

	public void setFechaReserva(Date fechaFacturacion) {
		this.fechaReserva = fechaFacturacion;
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
		this.tipoTarjeta = tiposTarjeta;
	}

	public String getFormasDePago() {
		return formaDePago;
	}

	public void setFormasDePago(String formasDePago) {
		this.formaDePago = formasDePago;
	}

	public boolean getEstado() {
		return estadoReserva;
	}

	public void setEstado(boolean estadoReserva) {
		this.estadoReserva = estadoReserva;
	}

	public Date getFechaVencTarjeta() {
		return fechaVencTarjeta;
	}

	public void setFechaVencTarjeta(Date fechaVencTarjeta) {
		this.fechaVencTarjeta = fechaVencTarjeta;
	}

	public String getCodSeguridadTarjeta() {
		return codSeguridadTarjeta;
	}

	public void setCodSeguridadTarjeta(String codSeguridadTarjeta) {
		this.codSeguridadTarjeta = codSeguridadTarjeta;
	}

}
