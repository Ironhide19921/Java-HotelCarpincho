package modelo;

import java.util.List;

import dto.ProductoDTO;
import persistencia.dao.interfaz.DAOAbstractFactory;
import persistencia.dao.interfaz.ProductoDAO;

public class Producto {
	
	private ProductoDAO producto;
	
	public Producto(DAOAbstractFactory metodo_persistencia) {
		this.producto = metodo_persistencia.createProductoDAO();
	}
	
	public void agregarProducto(ProductoDTO nuevoProducto) {
		this.producto.insert(nuevoProducto);
	}
	
	public void modificarProducto(ProductoDTO producto) {
		this.producto.update(producto);
	}
	
	public void borrarProducto(ProductoDTO producto) {
		this.producto.delete(producto);
	}
	
	public List<ProductoDTO> obtenerProductos() {
		return this.producto.readAll();
	}
	
	public List<ProductoDTO> buscarProductos(String buscar){
		return this.producto.search(buscar);
	}

}
