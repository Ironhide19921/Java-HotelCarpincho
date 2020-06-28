package hotel;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import dto.OrdenPedidoDTO;
import modelo.OrdenPedido;
import persistencia.dao.mysql.DAOSQLFactory;

public class OrdenPedidoTest {
	
	private OrdenPedidoDTO pedidoDto;
	private OrdenPedido pedido;
	
	@Before
	public void init() {
		pedido = new OrdenPedido(new DAOSQLFactory());
		pedidoDto = new OrdenPedidoDTO(0, 1, 1, 1, 2, new BigDecimal(50), null, null, null, null, null, false);
		pedido.agregarOrdenPedido(pedidoDto);
		
	}
	
	@Test
	public void testCantidadPedidos() {
		pedido = new OrdenPedido(new DAOSQLFactory());
		OrdenPedidoDTO pedidoCTicket = new OrdenPedidoDTO(0, 1, 1, 1, 3, new BigDecimal(100), "Debito", "VISA", "4532156784563452", "05/20", "234", true);
		pedido.agregarOrdenPedido(pedidoCTicket);
		assertEquals(2, pedido.obtenerOrdenesPedidos().size());
		
	}
	
	@Test
	public void testUltimoIdPedido() {
		pedido = new OrdenPedido(new DAOSQLFactory());
		int ultimoIdPedido = pedido.obtenerOrdenesPedidos().get(pedido.obtenerOrdenesPedidos().size()-1).getIdOrdenPedido();
		assertEquals(pedido.obtenerIdMaximo(), ultimoIdPedido);
		
	}
	
	@After
	public void eliminarPedido() {
		pedido = new OrdenPedido(new DAOSQLFactory());		
		int ultimoIdPedido = pedido.obtenerOrdenesPedidos().get(pedido.obtenerOrdenesPedidos().size()-1).getIdOrdenPedido();
		OrdenPedidoDTO ultimoPedidoAgregado = null;
		
		for(OrdenPedidoDTO op : pedido.obtenerOrdenesPedidos()) {
			if(op.getIdOrdenPedido() == ultimoIdPedido) {
				ultimoPedidoAgregado = op;
			}
		}
		
		pedido.eliminarOrdenPedido(ultimoPedidoAgregado);	
		
	}

}
