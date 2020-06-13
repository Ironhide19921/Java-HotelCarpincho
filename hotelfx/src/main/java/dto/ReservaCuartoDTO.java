package dto;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

public class ReservaCuartoDTO {

	public enum estadosReserva {Pendiente,Cancelado,En_curso,Finalizado};
	private Integer idReserva,idCliente,idCuarto,idUsuario;
	private BigDecimal senia,montoReservaCuarto;
	private String emailFacturacion, numTarjeta, cantidadDias,
	tipoTarjeta, forma, codSeguridadTarjeta,comentarios,fechaVencTarjeta;
	
	private estadosReserva estadoReserva;
	private Timestamp fechaReserva,fechaCheckIn,fechaOut,
	fechaIngreso,fechaEgreso;

	private boolean estado;

	
	
	public ReservaCuartoDTO(int idCliente, int idCuarto, int idUsuario, BigDecimal senia,
			BigDecimal montoReservaCuarto,String emailFacturacion,String numTarjeta, 
			String formaDePago,	String tipoTarjeta,String codSeguridadTarjeta,
			String fechaVencTarjeta,Timestamp fechaReserva,Timestamp fechaCheckIn, Timestamp fechaOut,
			Timestamp fechaIngreso, Timestamp fechaEgreso , 
			String estadoReserva, String comentarios, boolean estado) {
		
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
		this.forma = formaDePago;
		this.tipoTarjeta = tipoTarjeta;
		this.estadoReserva = estadosReserva.valueOf(estadoReserva);
		this.comentarios = comentarios;
		this.estado = estado;
		this.fechaVencTarjeta = fechaVencTarjeta;
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

	public Timestamp getFechaReserva() {
		return fechaReserva;
	}

	public void setFechaReserva(Timestamp fechaFacturacion) {
		this.fechaReserva = fechaFacturacion;
	}

	public Timestamp getFechaCheckIn() {
		return fechaCheckIn;
	}

	public void setFechaCheckIn(Timestamp fechaCheckIn) {
		this.fechaCheckIn = fechaCheckIn;
	}

	public Timestamp getFechaOut() {
		return fechaOut;
	}

	public void setFechaOut(Timestamp fechaOut) {
		this.fechaOut = fechaOut;
	}

	public Timestamp getFechaIngreso() {
		return fechaIngreso;
	}

	public void setFechaIngreso(Timestamp fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
	}

	public Timestamp getFechaEgreso() {
		return fechaEgreso;
	}

	public void setFechaEgreso(Timestamp fechaEgreso) {
		this.fechaEgreso = fechaEgreso;
	}

	public String getTiposTarjeta() {
		return tipoTarjeta;
	}

	public void setTiposTarjeta(String tiposTarjeta) {
		this.tipoTarjeta = tiposTarjeta;
	}

	public String getFormasDePago() {
		return forma;
	}

	public void setFormasDePago(String formasDePago) {
		this.forma = formasDePago;
	}

	public String getEstadoReserva() {
		return estadoReserva.name();
	}

	public void setEstado(String estadoReserva) {
		this.estadoReserva.valueOf(estadoReserva);
	}

	public String getFechaVencTarjeta() {
		return fechaVencTarjeta;
	}

	public void setFechaVencTarjeta(String fechaVencTarjeta) {
		this.fechaVencTarjeta = fechaVencTarjeta;
	}

	public String getCodSeguridadTarjeta() {
		return codSeguridadTarjeta;
	}

	public void setCodSeguridadTarjeta(String codSeguridadTarjeta) {
		this.codSeguridadTarjeta = codSeguridadTarjeta;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

	public boolean getEstado() {
		return this.estado;
	}

}
