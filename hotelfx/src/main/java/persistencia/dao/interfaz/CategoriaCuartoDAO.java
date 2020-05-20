package persistencia.dao.interfaz;

import java.util.List;

import dto.CategoriaCuartoDTO;


public interface CategoriaCuartoDAO {
	
	public boolean insert(CategoriaCuartoDTO categoriaCuarto);

	public boolean delete(CategoriaCuartoDTO categoriaCuarto_a_eliminar);
	
	public List<CategoriaCuartoDTO> readAll();
	
	public void update(CategoriaCuartoDTO categoriaCuarto);
	
}
