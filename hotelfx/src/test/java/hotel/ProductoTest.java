package hotel;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import dto.ProductoDTO;
import modelo.Producto;
import persistencia.dao.mysql.DAOSQLFactory;

public class ProductoTest {
	
	private ProductoDTO productoDto;
	private Producto producto;
	
	@Before
	public void init() {
		
		producto = new Producto(new DAOSQLFactory());
		productoDto = new ProductoDTO(0, "Pollo frito", new BigDecimal(520.00), "sale con fritas", "El pollo loco", "Comida");
		producto.agregarProducto(productoDto);
		
	}
	
	@Test
	public void testBuscarProductosProv() throws Exception {
		producto = new Producto(new DAOSQLFactory());
		List<ProductoDTO> listaProductos = producto.buscarProductos("comi");
		System.out.println(listaProductos.size());
		assertEquals(2, listaProductos.size());
	
	}
	
	@Test
	public void testEditarProductos() throws Exception {
		producto = new Producto(new DAOSQLFactory());
		productoDto.setNombre("Mila frita");
		producto.modificarProducto(productoDto);
		System.out.println(productoDto.getNombre());
		assertEquals("Mila frita", productoDto.getNombre());
				
	}
	
	@After
	public void borrarProducto() {
		producto = new Producto(new DAOSQLFactory());		
		int ultimoIdProducto = producto.obtenerProductos().get(producto.obtenerProductos().size()-1).getIdProducto();
		ProductoDTO ultimoProdAgregado = null;
		
		for(ProductoDTO p : producto.obtenerProductos()) {
			if(p.getIdProducto() == ultimoIdProducto) {
				ultimoProdAgregado = p;
			}
		}
		
		producto.borrarProducto(ultimoProdAgregado);
	}

}
