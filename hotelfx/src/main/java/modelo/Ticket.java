package modelo;

import java.util.List;
import dto.TicketDTO;
import persistencia.dao.interfaz.DAOAbstractFactory;
import persistencia.dao.interfaz.TicketDAO;

public class Ticket {
	
private TicketDAO ticket;

	public Ticket(DAOAbstractFactory metodo_persistencia){
		this.ticket = metodo_persistencia.createTicketDAO();
	}
	
	public void agregarCliente(TicketDTO nuevoTicket){
		this.ticket.insert(nuevoTicket);
	}
	
	
	public List<TicketDTO> obtenerClientes() 	{
		return this.ticket.readAll();
	}
	
	public List<TicketDTO> buscarClientes(String buscar) 	{
		return this.ticket.search(buscar);
	}
	
	public int obtenerIdTicketRecienInsertado(int idCliente) {
		return this.ticket.getIdTicketRecienInsertado(idCliente);
	}

	public void modificarTicket(TicketDTO ticketDTO, String path) {
		this.ticket.modif(ticketDTO.getIdTicket(), path);
	}
	
	public TicketDTO getTicket(int idTicket) {
		return this.ticket.get(idTicket);
	}
}
