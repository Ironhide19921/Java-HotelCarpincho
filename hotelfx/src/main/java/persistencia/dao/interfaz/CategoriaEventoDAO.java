package persistencia.dao.interfaz;

import java.util.List;

import dto.CategoriaEventoDTO;

public interface CategoriaEventoDAO {
	public boolean insert(CategoriaEventoDTO categoriaEvento);
	
	public List<CategoriaEventoDTO> readAll();
	
	public void update(CategoriaEventoDTO categoriaEvento_a_actualizar);
	
	public boolean delete(CategoriaEventoDTO categoriaEvento);

	public CategoriaEventoDTO get(int id);
}
