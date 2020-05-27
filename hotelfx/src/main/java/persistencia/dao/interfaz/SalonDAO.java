package persistencia.dao.interfaz;

import java.util.List;

import dto.SalonDTO;

public interface SalonDAO {
	
	public boolean insert(SalonDTO salon);
	
	public List<SalonDTO> readAll();
	
	public void update(SalonDTO salon_a_actualizar);
	
	public void cambiarEstado(SalonDTO salon);

}

