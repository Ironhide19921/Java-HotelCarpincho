package dto;

import java.math.BigDecimal;

public class ProductoDTO {
	
	private int idProducto;
	private String nombre;
	private BigDecimal precio;
	private String descripcion;
	private String proveedor;
	private String tipo;
	
	public ProductoDTO(int idProducto, String nombre, BigDecimal precio, String descripcion, String proveedor,
			String tipo) {
		this.idProducto = idProducto;
		this.nombre = nombre;
		this.precio = precio;
		this.descripcion = descripcion;
		this.proveedor = proveedor;
		this.tipo = tipo;
	}

	public int getIdProducto() {
		return idProducto;
	}

	public void setIdProducto(int idProducto) {
		this.idProducto = idProducto;
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

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getProveedor() {
		return proveedor;
	}

	public void setProveedor(String proveedor) {
		this.proveedor = proveedor;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

}
