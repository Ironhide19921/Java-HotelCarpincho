package persistencia.dao.interfaz;

import java.util.List;

import dto.ClienteDTO;

public interface ClienteDAO {
	
	public boolean insert(ClienteDTO cliente);

	public boolean delete(ClienteDTO cliente_a_eliminar);
	
	public List<ClienteDTO> readAll();
	
	public void update(ClienteDTO cliente);

	public List<ClienteDTO> search(String buscar);
	
	public ClienteDTO traerCliente(Integer id);
	
	public List<ClienteDTO> readAllEncuesta();
}
