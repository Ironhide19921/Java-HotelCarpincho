package modelo;

import java.util.List;

import dto.ConexionConfigDTO;
import persistencia.dao.interfaz.ConexionConfigDAO;
import persistencia.dao.interfaz.DAOAbstractFactory;

public class ConexionConfig {
	
	private ConexionConfigDAO conexionConfig;

	public ConexionConfig(DAOAbstractFactory metodo_persistencia){
		this.conexionConfig = metodo_persistencia.createConexionConfigDAO();
	}
	
	public void modificarConexionConfig(ConexionConfigDTO conexionConfig) {
		this.conexionConfig.update(conexionConfig);
	}

	public ConexionConfigDTO traerConexionConfig(Integer id) {
		return this.conexionConfig.traerConexionConfig(id);
	}

}
