package persistencia.dao.interfaz;

import java.sql.Timestamp;
import java.util.List;

import dto.ReservaEventoDTO;

public interface ReservaEventoDAO {
	public boolean insert(ReservaEventoDTO reservaEvento);
	
	public List<ReservaEventoDTO> readAll();
	
	public void update(ReservaEventoDTO reservaEvento_a_actualizar);
	
	public boolean delete(ReservaEventoDTO reservaEvento);
	
	public List<ReservaEventoDTO> readAllCliente(int idCliente);

	public List<ReservaEventoDTO> getReservasEntre(Timestamp fechaInicio, Timestamp fechaFin);

	public void setCheckinCheckout(Timestamp ingreso, Timestamp egreso, int idReserva);
	
	public void cambiarEstado(int idReserva, String estado);
}
