package dto;

import java.math.BigDecimal;

public class OrdenPedidoDTO {
	
	int idOrdenPedido;
	int idProducto;
	int idCliente;
	int idUsuario;
	int cantidad;
	BigDecimal precioTotal;
	
	public OrdenPedidoDTO(int idOrdenPedido, int idProducto, int idCliente, int idUsuario, int cantidad,
			BigDecimal precioTotal) {
		this.idOrdenPedido = idOrdenPedido;
		this.idProducto = idProducto;
		this.idCliente = idCliente;
		this.idUsuario = idUsuario;
		this.cantidad = cantidad;
		this.precioTotal = precioTotal;
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

}
