package modelo;

import java.util.List;

import dto.CategoriaCuartoDTO;
import dto.ClienteDTO;
import dto.ConfiguracionDTO;
import persistencia.dao.interfaz.CategoriaCuartoDAO;
import persistencia.dao.interfaz.ClienteDAO;
import persistencia.dao.interfaz.ConfiguracionDAO;
import persistencia.dao.interfaz.DAOAbstractFactory;

public class Configuracion {
	
private ConfiguracionDAO config;

	public Configuracion(DAOAbstractFactory metodo_persistencia){
		this.config = metodo_persistencia.createConfiguracionDAO();
	}
	
//	public void agregarConfiguracion(ConfiguracionDTO config){
//		this.config.insert(config);
//	}
	
	public void modificarConfiguracion(ConfiguracionDTO config) {
		this.config.update(config);
	}
	
	public List<ConfiguracionDTO> obtenerConfiguraciones() 	{
		return this.config.readAll();
	}
	
//	public List<ClienteDTO> buscarClientes(String buscar) 	{
//		return this.cliente.search(buscar);
//	}
	
	

}
