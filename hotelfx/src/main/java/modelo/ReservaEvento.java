package modelo;

import java.sql.Timestamp;
import java.util.List;

import dto.CategoriaEventoDTO;
import dto.ReservaEventoDTO;
import persistencia.dao.interfaz.DAOAbstractFactory;
import persistencia.dao.interfaz.ReservaEventoDAO;

public class ReservaEvento {
	private ReservaEventoDAO reservaEvento;
	
	public ReservaEvento(DAOAbstractFactory metodo_persistencia){
		this.reservaEvento = metodo_persistencia.createReservaEventoDAO();
	}
	
	public void agregarReservaEvento(ReservaEventoDTO nuevaReservaEvento){
		this.reservaEvento.insert(nuevaReservaEvento);
	}
	
	public void modificarReservaEvento(ReservaEventoDTO reservaEvento) {
		this.reservaEvento.update(reservaEvento);
	}
	
	public List<ReservaEventoDTO> obtenerReservasEvento() {
		return this.reservaEvento.readAll();
	}
	
	public void borrarReservaEvento(ReservaEventoDTO reservaEvento) {
		this.reservaEvento.delete(reservaEvento);
	}
	
	public void obtenerReservasEventoCliente(int idCliente) {
		this.reservaEvento.readAllCliente(idCliente);
	}
	
	public List<ReservaEventoDTO> verReservasEntreFechas(Timestamp fechaInicio, Timestamp fechaFin) {
		return this.reservaEvento.getReservasEntre(fechaInicio, fechaFin);
	}
}
