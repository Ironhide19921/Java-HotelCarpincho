package modelo;

import java.util.List;

import dto.CategoriaCuartoDTO;
import dto.ClienteDTO;
import persistencia.dao.interfaz.CategoriaCuartoDAO;
import persistencia.dao.interfaz.ClienteDAO;
import persistencia.dao.interfaz.DAOAbstractFactory;

public class Cliente {
	
private ClienteDAO cliente;

	public Cliente(DAOAbstractFactory metodo_persistencia){
		this.cliente = metodo_persistencia.createClienteDAO();
	}
	
	public void agregarCliente(ClienteDTO nuevoCliente){
		this.cliente.insert(nuevoCliente);
	}
	
	public void modificarCliente(ClienteDTO cliente) {
		this.cliente.update(cliente);
	}
	
	public List<ClienteDTO> obtenerClientes() 	{
		return this.cliente.readAll();
	}
	
	public List<ClienteDTO> buscarClientes(String buscar) 	{
		return this.cliente.search(buscar);
	}
	
	public ClienteDTO getClientePorId(int id) {
		return this.cliente.get(id);
	}
	
	
	public ClienteDTO traerCliente(Integer id) {
		return this.cliente.traerCliente(id);
	}
}
