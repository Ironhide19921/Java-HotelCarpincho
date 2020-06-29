package persistencia.dao.interfaz;

import java.util.List;
import dto.ErrorImportarDTO;

public interface ErrorImportarDAO {
	
	public boolean insert(ErrorImportarDTO error);
	
	public List<ErrorImportarDTO> readAll();
	
	public List<ErrorImportarDTO> search(String buscar);

	boolean delete(ErrorImportarDTO errorAeliminar);

}
