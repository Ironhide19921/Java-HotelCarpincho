package persistencia.dao.interfaz;

import java.util.List;

import dto.OrdenPedidoDTO;

public interface OrdenPedidoDAO {
	
	public boolean insert(OrdenPedidoDTO nuevaOrdenPedido);

	void update(OrdenPedidoDTO ordenPedido);
	
	List<OrdenPedidoDTO> readAll();

	List<OrdenPedidoDTO> search(String buscar);

	public int obtenerIdMaximo();
	
	public boolean delete(OrdenPedidoDTO pedidoAeliminar);

	public List<OrdenPedidoDTO> buscarOrdenesPedidosPorReserva(int idCliente);

}

