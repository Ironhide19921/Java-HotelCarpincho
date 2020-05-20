package persistencia.dao.interfaz;

import java.util.List;

import dto.PerfilDTO;

public interface PerfilDAO {
	
	public boolean insert(PerfilDTO perfil);

	public boolean delete(PerfilDTO perfil_a_eliminar);
	
	public List<PerfilDTO> readAll();
	
	public void update(PerfilDTO perfil);

	public List<PerfilDTO> search(int buscar);


}
