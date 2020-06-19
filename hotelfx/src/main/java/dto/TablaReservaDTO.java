package dto;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import dto.ReservaCuartoDTO.EstadoReserva;
import dto.ReservaCuartoDTO.FormaPago;
import dto.ReservaCuartoDTO.TipoTarjeta;

public class TablaReservaDTO {
	private ReservaCuartoDTO reserva;
	private ClienteDTO cliente;
	private CuartoDTO cuarto;
	private Integer idReserva,idCliente,idCuarto,idUsuario;
	private BigDecimal senia,montoReservaCuarto;
	private String emailFacturacion, numTarjeta, cantidadDias,fechaVencTarjeta,codSeguridadTarjeta,comentarios;
	private TipoTarjeta tipoTarjeta;
	private FormaPago formaDePago;
	private EstadoReserva estadoReserva;
	private Timestamp fechaReserva,fechaCheckIn,fechaOut,
	fechaIngreso,fechaEgreso;
	private String estado;
	private String nombre;
	private String apellido;
	private String descripcionCuarto;
	
	public TablaReservaDTO(ReservaCuartoDTO reserva, ClienteDTO cliente, CuartoDTO cuarto) {
		this.reserva = reserva;
		this.cliente= cliente;
		this.cuarto = cuarto;
		this.idReserva = reserva.getIdReserva();
		this.idCliente = reserva.getIdCliente();
		this.idCuarto = reserva.getIdCuarto();
		this.idUsuario = reserva.getIdUsuario();
		this.senia = reserva.getSenia();
		this.montoReservaCuarto = reserva.getMontoReservaCuarto();
		this.emailFacturacion = reserva.getEmailFacturacion();
		this.numTarjeta = reserva.getNumTarjeta();
		this.cantidadDias = reserva.getCantidadDias();
		this.formaDePago = reserva.getFormaPago();
		this.tipoTarjeta = reserva.getTipoTarjeta();
		this.codSeguridadTarjeta = reserva.getCodSeguridadTarjeta();
		this.comentarios = reserva.getComentarios();
		this.fechaVencTarjeta = reserva.getFechaVencTarjeta();
		this.fechaReserva = reserva.getFechaReserva();
		this.fechaCheckIn = reserva.getFechaCheckIn();
		this.fechaOut = reserva.getFechaOut();
		this.fechaIngreso = reserva.getFechaIngreso();
		this.fechaEgreso = reserva.getFechaEgreso();
		this.nombre = cliente.getNombre();
		this.apellido = cliente.getApellido();
	
		this.descripcionCuarto = cuarto.getHabitacion();
		this.estado = reserva.getEstadoReserva().name();
	}

	public ClienteDTO getCliente() {
		return cliente;
	}

	public void setCliente(ClienteDTO cliente) {
		this.cliente = cliente;
	}

	public ReservaCuartoDTO getReserva() {
		return reserva;
	}

	public void setReserva(ReservaCuartoDTO reserva) {
		this.reserva = reserva;
	}
	public CuartoDTO getCuarto() {
		return cuarto;
	}

	public void setCuarto(CuartoDTO cuarto) {
		this.cuarto = cuarto;
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

	public TipoTarjeta getTipoTarjeta() {
		return tipoTarjeta;
	}

	public void setTipoTarjeta(TipoTarjeta tipoTarjeta) {
		this.tipoTarjeta = tipoTarjeta;
	}

	public FormaPago getFormaDePago() {
		return formaDePago;
	}

	public void setFormaDePago(FormaPago formaDePago) {
		this.formaDePago = formaDePago;
	}

	public String getCodSeguridadTarjeta() {
		return codSeguridadTarjeta;
	}

	public void setCodSeguridadTarjeta(String codSeguridadTarjeta) {
		this.codSeguridadTarjeta = codSeguridadTarjeta;
	}

	public String getComentarios() {
		return comentarios;
	}

	public void setComentarios(String comentarios) {
		this.comentarios = comentarios;
	}

	public EstadoReserva getEstadoReserva() {
		return estadoReserva;
	}

	public void setEstadoReserva(EstadoReserva estadoReserva) {
		this.estadoReserva = estadoReserva;
	}

	public String getFechaVencTarjeta() {
		return fechaVencTarjeta;
	}

	public void setFechaVencTarjeta(String fechaVencTarjeta) {
		this.fechaVencTarjeta = fechaVencTarjeta;
	}

	public Timestamp getFechaReserva() {
		return fechaReserva;
	}

	public void setFechaReserva(Timestamp fechaReserva) {
		this.fechaReserva = fechaReserva;
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

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getDescripcionCuarto() {
		return descripcionCuarto;
	}

	public void setDescripcionCuarto(String descripcionCuarto) {
		this.descripcionCuarto = descripcionCuarto;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
}

