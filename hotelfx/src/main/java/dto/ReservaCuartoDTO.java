package dto;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import dto.ReservaEventoDTO.FormaPago;
import dto.ReservaEventoDTO.TipoTarjeta;

public class ReservaCuartoDTO {

	private Integer idReserva,idCliente,idCuarto,idUsuario;
	private BigDecimal senia,montoReservaCuarto, montoTotal;
	private String emailFacturacion, numTarjeta, cantidadDias, codSeguridadTarjeta,comentarios,fechaVencTarjeta;
	public enum TipoTarjeta{VISA, MASTERCARD, NO};
	public enum FormaPago{EFECTIVO, DEBITO, CREDITO};
	public enum EstadoReserva{PENDIENTE, CANCELADO, EN_CURSO, FINALIZADO};
	private EstadoReserva estadoReserva;
	private FormaPago formaPago;
	private TipoTarjeta tipoTarjeta;
	private Timestamp fechaReserva,fechaCheckIn,fechaOut,
	fechaIngreso,fechaEgreso;

	private boolean estado;

	
	
	public ReservaCuartoDTO(int idCliente, int idCuarto, int idUsuario, BigDecimal senia,
			BigDecimal montoReservaCuarto,String emailFacturacion,String numTarjeta, 
			FormaPago formaDePago,	TipoTarjeta tipoTarjeta,String codSeguridadTarjeta,
			String fechaVencTarjeta,Timestamp fechaReserva,
			Timestamp fechaIngreso, Timestamp fechaEgreso , 
			EstadoReserva estadoReserva, String comentarios, boolean estado) {
		
		this.idCliente = idCliente;
		this.idCuarto = idCuarto;
		this.idUsuario = idUsuario;
		this.senia = senia;
		this.montoReservaCuarto = montoReservaCuarto;
		this.emailFacturacion = emailFacturacion;
		this.numTarjeta = numTarjeta;
		this.fechaReserva = fechaReserva;
		this.fechaIngreso = fechaIngreso;
		this.fechaEgreso = fechaEgreso;
		this.codSeguridadTarjeta = codSeguridadTarjeta;
		this.setFormaPago(formaDePago);
		this.setTipoTarjeta(tipoTarjeta);
		this.estadoReserva = estadoReserva;
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


	public EstadoReserva getEstadoReserva() {
		return estadoReserva;
	}

	public void setEstado(EstadoReserva estadoReserva) {
		this.estadoReserva= estadoReserva;
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

	public FormaPago getFormaPago() {
		return formaPago;
	}

	public void setFormaPago(FormaPago formaPago) {
		this.formaPago = formaPago;
	}

	public TipoTarjeta getTipoTarjeta() {
		return tipoTarjeta;
	}

	public void setTipoTarjeta(TipoTarjeta tipoTarjeta) {
		this.tipoTarjeta = tipoTarjeta;
	}

	public BigDecimal getMontoTotal() {
		return montoTotal;
	}

	public void setMontoTotal(BigDecimal montoTotal) {
		this.montoTotal = montoTotal;
	}

}
