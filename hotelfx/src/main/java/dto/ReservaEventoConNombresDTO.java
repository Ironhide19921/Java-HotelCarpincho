package dto;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class ReservaEventoConNombresDTO {
public enum EstadoReserva{PENDIENTE, CANCELADO, EN_CURSO, FINALIZADO};
	
	private int idReservaEvento;
	private int idCliente;
	private String tipoDocumentoCliente;
	private String dniCliente;
	private String nombreCliente;
	private String apellidoCliente;
	private int idUsuario;
	private int idSalon;
	private int idCategoriaEvento;
	private String nombreCategoriaEvento;
	private BigDecimal Senia;
	private BigDecimal MontoReservaEvento;
	private BigDecimal MontoTotal;
	private Timestamp FechaGeneracionReserva;
	private Timestamp FechaInicioReserva;
	private Timestamp FechaFinReserva;
	private Timestamp FechaIngreso;
	private Timestamp FechaEgreso;
	private String FormaPago;
	private String TipoTarjeta;
	private String NumeroTarjeta;
	private String FechaVencTarjeta;
	private String CodSeguridadTarjeta;
	private EstadoReserva estado;
	private String Observaciones;
	
	public ReservaEventoConNombresDTO(int idReservaEvento, int idCliente, String tipoDocumentoCliente,
			String dniCliente, String nombreCliente, String apellidoCliente, int idUsuario, int idSalon,
			int idCategoriaEvento, String nombreCategoriaEvento, BigDecimal senia, BigDecimal montoReservaEvento,
			BigDecimal montoTotal, Timestamp fechaGeneracionReserva, Timestamp fechaInicioReserva,
			Timestamp fechaFinReserva, Timestamp fechaIngreso, Timestamp fechaEgreso, String formaPago,
			String tipoTarjeta, String numeroTarjeta, String fechaVencTarjeta, String codSeguridadTarjeta,
			EstadoReserva estado, String observaciones) {
		this.idReservaEvento = idReservaEvento;
		this.idCliente = idCliente;
		this.tipoDocumentoCliente = tipoDocumentoCliente;
		this.dniCliente = dniCliente;
		this.nombreCliente = nombreCliente;
		this.apellidoCliente = apellidoCliente;
		this.idUsuario = idUsuario;
		this.idSalon = idSalon;
		this.idCategoriaEvento = idCategoriaEvento;
		this.nombreCategoriaEvento = nombreCategoriaEvento;
		this.Senia = senia;
		this.MontoReservaEvento = montoReservaEvento;
		this.MontoTotal = montoTotal;
		this.FechaGeneracionReserva = fechaGeneracionReserva;
		this.FechaInicioReserva = fechaInicioReserva;
		this.FechaFinReserva = fechaFinReserva;
		this.FechaIngreso = fechaIngreso;
		this.FechaEgreso = fechaEgreso;
		this.FormaPago = formaPago;
		this.TipoTarjeta = tipoTarjeta;
		this.NumeroTarjeta = numeroTarjeta;
		this.FechaVencTarjeta = fechaVencTarjeta;
		this.CodSeguridadTarjeta = codSeguridadTarjeta;
		this.estado = estado;
		this.Observaciones = observaciones;
	}

	public int getIdReservaEvento() {
		return idReservaEvento;
	}

	public void setIdReservaEvento(int idReservaEvento) {
		this.idReservaEvento = idReservaEvento;
	}

	public int getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(int idCliente) {
		this.idCliente = idCliente;
	}

	public String getTipoDocumentoCliente() {
		return tipoDocumentoCliente;
	}

	public void setTipoDocumentoCliente(String tipoDocumentoCliente) {
		this.tipoDocumentoCliente = tipoDocumentoCliente;
	}

	public String getDniCliente() {
		return dniCliente;
	}

	public void setDniCliente(String dniCliente) {
		this.dniCliente = dniCliente;
	}

	public String getNombreCliente() {
		return nombreCliente;
	}

	public void setNombreCliente(String nombreCliente) {
		this.nombreCliente = nombreCliente;
	}

	public String getApellidoCliente() {
		return apellidoCliente;
	}

	public void setApellidoCliente(String apellidoCliente) {
		this.apellidoCliente = apellidoCliente;
	}

	public int getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	public int getIdSalon() {
		return idSalon;
	}

	public void setIdSalon(int idSalon) {
		this.idSalon = idSalon;
	}

	public int getIdCategoriaEvento() {
		return idCategoriaEvento;
	}

	public void setIdCategoriaEvento(int idCategoriaEvento) {
		this.idCategoriaEvento = idCategoriaEvento;
	}

	public String getNombreCategoriaEvento() {
		return nombreCategoriaEvento;
	}

	public void setNombreCategoriaEvento(String nombreCategoriaEvento) {
		this.nombreCategoriaEvento = nombreCategoriaEvento;
	}

	public BigDecimal getSenia() {
		return Senia;
	}

	public void setSenia(BigDecimal senia) {
		Senia = senia;
	}

	public BigDecimal getMontoReservaEvento() {
		return MontoReservaEvento;
	}

	public void setMontoReservaEvento(BigDecimal montoReservaEvento) {
		MontoReservaEvento = montoReservaEvento;
	}

	public BigDecimal getMontoTotal() {
		return MontoTotal;
	}

	public void setMontoTotal(BigDecimal montoTotal) {
		MontoTotal = montoTotal;
	}

	public Timestamp getFechaGeneracionReserva() {
		return FechaGeneracionReserva;
	}

	public void setFechaGeneracionReserva(Timestamp fechaGeneracionReserva) {
		FechaGeneracionReserva = fechaGeneracionReserva;
	}

	public Timestamp getFechaInicioReserva() {
		return FechaInicioReserva;
	}

	public void setFechaInicioReserva(Timestamp fechaInicioReserva) {
		FechaInicioReserva = fechaInicioReserva;
	}

	public Timestamp getFechaFinReserva() {
		return FechaFinReserva;
	}

	public void setFechaFinReserva(Timestamp fechaFinReserva) {
		FechaFinReserva = fechaFinReserva;
	}

	public Timestamp getFechaIngreso() {
		return FechaIngreso;
	}

	public void setFechaIngreso(Timestamp fechaIngreso) {
		FechaIngreso = fechaIngreso;
	}

	public Timestamp getFechaEgreso() {
		return FechaEgreso;
	}

	public void setFechaEgreso(Timestamp fechaEgreso) {
		FechaEgreso = fechaEgreso;
	}

	public String getFormaPago() {
		return FormaPago;
	}

	public void setFormaPago(String formaPago) {
		FormaPago = formaPago;
	}

	public String getTipoTarjeta() {
		return TipoTarjeta;
	}

	public void setTipoTarjeta(String tipoTarjeta) {
		TipoTarjeta = tipoTarjeta;
	}

	public String getNumeroTarjeta() {
		return NumeroTarjeta;
	}

	public void setNumeroTarjeta(String numeroTarjeta) {
		NumeroTarjeta = numeroTarjeta;
	}

	public String getFechaVencTarjeta() {
		return FechaVencTarjeta;
	}

	public void setFechaVencTarjeta(String fechaVencTarjeta) {
		FechaVencTarjeta = fechaVencTarjeta;
	}

	public String getCodSeguridadTarjeta() {
		return CodSeguridadTarjeta;
	}

	public void setCodSeguridadTarjeta(String codSeguridadTarjeta) {
		CodSeguridadTarjeta = codSeguridadTarjeta;
	}

	public EstadoReserva getEstado() {
		return estado;
	}

	public void setEstado(EstadoReserva estado) {
		this.estado = estado;
	}

	public String getObservaciones() {
		return Observaciones;
	}

	public void setObservaciones(String observaciones) {
		Observaciones = observaciones;
	}
}
