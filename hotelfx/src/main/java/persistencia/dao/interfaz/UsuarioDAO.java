package persistencia.dao.interfaz;

import java.util.List;

import dto.UsuarioDTO;

public interface UsuarioDAO {
	
	public boolean insert(UsuarioDTO usuario);

	public boolean delete(UsuarioDTO usuario_a_eliminar);
	
	public List<UsuarioDTO> readAll();
	
	public void update(UsuarioDTO usuario);

	public List<UsuarioDTO> search(String buscar);
}
