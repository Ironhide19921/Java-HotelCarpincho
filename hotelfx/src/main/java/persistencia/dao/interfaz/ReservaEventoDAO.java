package persistencia.dao.interfaz;

import java.util.List;

import dto.ReservaEventoDTO;

public interface ReservaEventoDAO {
	public boolean insert(ReservaEventoDTO reservaEvento);
	
	public List<ReservaEventoDTO> readAll();
	
	public void update(ReservaEventoDTO reservaEvento_a_actualizar);
	
	public boolean delete(ReservaEventoDTO reservaEvento);
	
	public List<ReservaEventoDTO> readAllCliente(int idCliente);
}
