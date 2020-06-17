package persistencia.dao.interfaz;

import java.util.List;

import dto.TicketDTO;


public interface TicketDAO {
	
	public boolean insert(TicketDTO ticket);
	
	public List<TicketDTO> readAll();

	public List<TicketDTO> search(String buscar);

	public int getIdTicketRecienInsertado(int idCliente);

	public void modif(int idTicket, String path);

	public TicketDTO get(int id);

	
}
