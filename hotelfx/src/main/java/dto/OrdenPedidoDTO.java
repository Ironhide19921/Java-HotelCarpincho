package dto;

import java.math.BigDecimal;

public class OrdenPedidoDTO {
	
	private int idOrdenPedido;
	private int idProducto;
	private int idCliente;
	private int idUsuario;
	private int cantidad;
	private BigDecimal precioTotal;
	private String formaPago;
	private String tipoTarjeta;
	private String numTarjeta;
	private String fechaVencTarjeta;
	private String codSeguridadTarjeta;
	private boolean esRestoran;
	
	public OrdenPedidoDTO(int idOrdenPedido, int idProducto, int idCliente, int idUsuario, int cantidad,
			BigDecimal precioTotal) {
		this.idOrdenPedido = idOrdenPedido;
		this.idProducto = idProducto;
		this.idCliente = idCliente;
		this.idUsuario = idUsuario;
		this.cantidad = cantidad;
		this.precioTotal = precioTotal;
	}
	
	public OrdenPedidoDTO(int idOrdenPedido, int idProducto, int idCliente, int idUsuario, int cantidad,
			BigDecimal precioTotal, String formaPago, String tipoTarjeta, String numTarjeta, String fechaVencTarjeta,
			String codSeguridadTarjeta, boolean esRestoran) {
		this.idOrdenPedido = idOrdenPedido;
		this.idProducto = idProducto;
		this.idCliente = idCliente;
		this.idUsuario = idUsuario;
		this.cantidad = cantidad;
		this.precioTotal = precioTotal;
		this.formaPago = formaPago;
		this.tipoTarjeta = tipoTarjeta;
		this.numTarjeta = numTarjeta;
		this.fechaVencTarjeta = fechaVencTarjeta;
		this.codSeguridadTarjeta = codSeguridadTarjeta;
		this.esRestoran = esRestoran;
	}

	public int getIdOrdenPedido() {
		return idOrdenPedido;
	}

	public void setIdOrdenPedido(int idOrdenPedido) {
		this.idOrdenPedido = idOrdenPedido;
	}

	public int getIdProducto() {
		return idProducto;
	}

	public void setIdProducto(int idProducto) {
		this.idProducto = idProducto;
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

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public BigDecimal getPrecioTotal() {
		return precioTotal;
	}

	public void setPrecioTotal(BigDecimal precioTotal) {
		this.precioTotal = precioTotal;
	}

	public String getFormaPago() {
		return formaPago;
	}

	public void setFormaPago(String formaPago) {
		this.formaPago = formaPago;
	}

	public String getTipoTarjeta() {
		return tipoTarjeta;
	}

	public void setTipoTarjeta(String tipoTarjeta) {
		this.tipoTarjeta = tipoTarjeta;
	}

	public String getNumTarjeta() {
		return numTarjeta;
	}

	public void setNumTarjeta(String numTarjeta) {
		this.numTarjeta = numTarjeta;
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

	public boolean isEsRestoran() {
		return esRestoran;
	}

	public void setEsRestoran(boolean esRestoran) {
		this.esRestoran = esRestoran;
	}

}
