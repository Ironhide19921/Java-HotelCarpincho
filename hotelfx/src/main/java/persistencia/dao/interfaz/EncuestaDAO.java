package persistencia.dao.interfaz;

import java.util.List;
import dto.EncuestaDTO;

public interface EncuestaDAO {
	
	public boolean insert(EncuestaDTO encuesta);
	
	public List<EncuestaDTO> readAll();
	
	public EncuestaDTO traerEncuestaPorCliente(Integer id);
	
	public void update(int encuesta);
}
