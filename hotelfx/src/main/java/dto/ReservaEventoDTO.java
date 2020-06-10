package dto;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class ReservaEventoDTO {
	public enum TipoTarjeta{VISA, MASTERCARD, NO};
	public enum FormaPago{EFECTIVO, DEBITO, CREDITO};
	public enum EstadoReserva{PENDIENTE, CANCELADO, EN_CURSO, FINALIZADO};
	private int idReservaEvento;
	private int idCliente;
	private int idUsuario;
	private int idSalon;
	private int idCategoriaEvento;
	private BigDecimal Senia;
	private BigDecimal MontoReservaEvento;
	private BigDecimal MontoTotal;
	private Timestamp FechaGeneracionReserva;
	private Timestamp FechaInicioReserva;
	private Timestamp FechaFinReserva;
	private Timestamp FechaIngreso;
	private Timestamp FechaEgreso;
	private FormaPago formaPago;
	private TipoTarjeta tipoTarjeta;
	private String NumeroTarjeta;
	private String FechaVencTarjeta;
	private String CodSeguridadTarjeta;
	private EstadoReserva estado;
	private String Observaciones;
	
	public ReservaEventoDTO(int idReservaEvento, int idCliente, int idUsuario, int idSalon, int idCategoriaEvento, BigDecimal Senia, BigDecimal MontoReservaEvento, BigDecimal MontoTotal, Timestamp FechaGeneracionReserva, Timestamp FechaInicioReserva, Timestamp FechaFinReserva, Timestamp FechaIngreso, Timestamp FechaEgreso, FormaPago FormaPago, TipoTarjeta TipoTarjeta, String NumeroTarjeta, String FechaVencTarjeta, String CodSeguridadTarjeta, EstadoReserva estado, String Observaciones) {
		this.idReservaEvento = idReservaEvento;
		this.idCliente = idCliente;
		this.idUsuario = idUsuario;
		this.idSalon = idSalon;
		this.idCategoriaEvento = idCategoriaEvento;
		this.Senia = Senia;
		this.MontoReservaEvento = MontoReservaEvento;
		this.MontoTotal = MontoTotal;
		this.FechaGeneracionReserva = FechaGeneracionReserva;
		this.FechaInicioReserva = FechaInicioReserva;
		this.FechaFinReserva = FechaFinReserva;
		this.FechaIngreso = FechaIngreso;
		this.FechaEgreso = FechaEgreso;
		this.formaPago = FormaPago;
		this.tipoTarjeta = TipoTarjeta;
		this.NumeroTarjeta = NumeroTarjeta;
		this.FechaVencTarjeta = FechaVencTarjeta;
		this.CodSeguridadTarjeta = CodSeguridadTarjeta;
		this.estado = EstadoReserva.valueOf(estado.name());
		this.Observaciones = Observaciones;
	}
	
	public int getIdReservaEvento() {
		return idReservaEvento;
	}

	public void setIdReservaCuarto(int idReservaCuarto) {
		this.idReservaEvento = idReservaCuarto;
	}

	public int getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(int idCliente) {
		this.idCliente = idCliente;
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

	public String getObservaciones() {
		return Observaciones;
	}

	public void setObservaciones(String observaciones) {
		Observaciones = observaciones;
	}

	public EstadoReserva getEstado() {
		return estado;
	}

	public void setEstado(EstadoReserva estado) {
		this.estado = estado;
	}
}

