package persistencia.dao.interfaz;

import java.util.List;
import dto.ConfiguracionDTO;

public interface ConfiguracionDAO {
	
	public List<ConfiguracionDTO> readAll();
	
	public void update(ConfiguracionDTO config);

}
