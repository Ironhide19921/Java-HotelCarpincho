package modelo;

import java.util.List;

import dto.OrdenPedidoDTO;
import persistencia.dao.interfaz.DAOAbstractFactory;
import persistencia.dao.interfaz.OrdenPedidoDAO;

public class OrdenPedido {
	
	private OrdenPedidoDAO ordenPedido;
	
	public OrdenPedido(DAOAbstractFactory metodo_persistencia){
		this.ordenPedido = metodo_persistencia.createOrdenPedidoDAO();
	}
	
	public void agregarOrdenPedido(OrdenPedidoDTO nuevoOrdenPedido){
		this.ordenPedido.insert(nuevoOrdenPedido);
	}
	
	public void modificarOrdenPedido(OrdenPedidoDTO ordenPedido) {
		this.ordenPedido.update(ordenPedido);
	}
	
	public List<OrdenPedidoDTO> obtenerOrdenesPedidos() {
		return this.ordenPedido.readAll();
	}
		
	public int obtenerIdMaximo() {
		return this.ordenPedido.obtenerIdMaximo();
	}
	
	public void eliminarOrdenPedido(OrdenPedidoDTO pedidoAeliminar) {
		this.ordenPedido.delete(pedidoAeliminar);
	}

	public List<OrdenPedidoDTO> buscarOrdenesPedidosPorReserva(int idCliente) {
		return this.ordenPedido.buscarOrdenesPedidosPorReserva(idCliente);
		
	}

}
