package dto;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

public class ReservaCuartoDTO {

	private Integer idReserva,idCliente,idCuarto,idUsuario;
	private BigDecimal senia,montoReservaCuarto;
	private String emailFacturacion, numTarjeta, cantidadDias, comentarios;
	private Date fechaFacturacion,fechaCheckIn,fechaOut,fechaIngreso,fechaEgreso;
	private List<String> tiposTarjeta,formasDePago;
	private boolean estado;
	
	
	public ReservaCuartoDTO(BigDecimal senia,BigDecimal montoReservaCuarto,String emailFacturacion,String numTarjeta,
			Date fechaFacturacion,Date fechaCheckIn, Date fechaOut,Date fechaIngreso, Date fechaEgreso ) {
		this.senia = senia;
		this.montoReservaCuarto = montoReservaCuarto;
		this.emailFacturacion = emailFacturacion;
		this.numTarjeta = numTarjeta;
		this.fechaFacturacion = fechaFacturacion;
		this.fechaCheckIn = fechaCheckIn;
		this.fechaOut = fechaOut;
		this.fechaIngreso = fechaIngreso;
		this.fechaEgreso = fechaEgreso;
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

	public List<String> getTiposTarjeta() {
		return tiposTarjeta;
	}

	public void setTiposTarjeta(List<String> tiposTarjeta) {
		this.tiposTarjeta = tiposTarjeta;
	}

	public List<String> getFormasDePago() {
		return formasDePago;
	}

	public void setFormasDePago(List<String> formasDePago) {
		this.formasDePago = formasDePago;
	}
}
