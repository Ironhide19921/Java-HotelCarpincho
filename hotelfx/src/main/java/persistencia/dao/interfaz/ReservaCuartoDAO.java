package persistencia.dao.interfaz;

import java.util.List;
import dto.ReservaCuartoDTO;

public interface ReservaCuartoDAO {
	public boolean insert(ReservaCuartoDTO cliente);

	public boolean delete(ReservaCuartoDTO cliente_a_eliminar);
	
	public List<ReservaCuartoDTO> readAll();
	
	public void update(ReservaCuartoDTO cliente);

	public List<ReservaCuartoDTO> search(String buscar);
}
