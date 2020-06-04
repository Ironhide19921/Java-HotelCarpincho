package persistencia.dao.interfaz;

import java.util.List;
import dto.ConfiguracionDTO;

public interface ConfiguracionDAO {
	
//	public boolean insert(ConfiguracionDTO config);

//	public boolean delete(ConfiguracionDTO cliente_a_eliminar);
	
	public List<ConfiguracionDTO> readAll();
	
	public void update(ConfiguracionDTO config);

//	public List<ConfiguracionDTO> search(String buscar);
}
