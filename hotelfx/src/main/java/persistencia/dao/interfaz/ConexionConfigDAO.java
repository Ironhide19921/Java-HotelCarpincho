package persistencia.dao.interfaz;

import java.util.List;

import dto.ConexionConfigDTO;

public interface ConexionConfigDAO {

	public void update(ConexionConfigDTO conexionConfig);
	
	public ConexionConfigDTO traerConexionConfig(Integer id);
}
