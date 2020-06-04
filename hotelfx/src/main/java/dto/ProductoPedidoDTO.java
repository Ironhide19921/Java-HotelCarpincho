package dto;

import java.math.BigDecimal;

public class ProductoPedidoDTO {
	
	private String nombre;
	private BigDecimal precio;
	private int cantidad;
	private BigDecimal precioTotal;
	
	public ProductoPedidoDTO(String nombre, BigDecimal precio, int cantidad, BigDecimal precioTotal) {
		this.nombre = nombre;
		this.precio = precio;
		this.cantidad = cantidad;
		this.precioTotal = precioTotal;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public BigDecimal getPrecio() {
		return precio;
	}

	public void setPrecio(BigDecimal precio) {
		this.precio = precio;
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
