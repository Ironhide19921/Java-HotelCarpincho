package persistencia.dao.interfaz;

import java.util.List;

import dto.PermisoPerfilDTO;


public interface PermisoPerfilDAO {
	
	public boolean insert(PermisoPerfilDTO permisoPerfil);

	public boolean delete(PermisoPerfilDTO permiso_a_eliminar);
	
	public List<PermisoPerfilDTO> readAll();
	
	public void update(PermisoPerfilDTO permiso);

	public List<PermisoPerfilDTO> search(int buscar);

}
