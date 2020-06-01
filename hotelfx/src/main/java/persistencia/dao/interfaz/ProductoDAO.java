package persistencia.dao.interfaz;

import java.util.List;

import dto.ProductoDTO;

public interface ProductoDAO {
	
	public boolean insert(ProductoDTO nuevoProducto);

	void update(ProductoDTO producto);
	
	public boolean delete(ProductoDTO productoAeliminar);
	
	List<ProductoDTO> readAll();

	List<ProductoDTO> search(String buscar);

}
