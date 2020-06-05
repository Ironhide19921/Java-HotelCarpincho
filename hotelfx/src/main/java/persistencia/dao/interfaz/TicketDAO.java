package persistencia.dao.interfaz;

import java.util.List;

import dto.TicketDTO;


public interface TicketDAO {
	
	public boolean insert(TicketDTO ticket);
	
	public List<TicketDTO> readAll();

	public List<TicketDTO> search(String buscar);
	
}
