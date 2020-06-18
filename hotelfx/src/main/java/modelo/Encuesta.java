package modelo;

import java.util.List;

import dto.EncuestaDTO;
import persistencia.dao.interfaz.DAOAbstractFactory;
import persistencia.dao.interfaz.EncuestaDAO;

public class Encuesta {
	
private EncuestaDAO encuesta;

	public Encuesta(DAOAbstractFactory metodo_persistencia){
		this.encuesta = metodo_persistencia.createEncuestaDAO();
	}
	
	public void agregarEncuesta(EncuestaDTO nuevaEncuesta){
		this.encuesta.insert(nuevaEncuesta);
	}
	
	public List<EncuestaDTO> obtenerEncuestas() 	{
		return this.encuesta.readAll();
	}	
	
	public EncuestaDTO traerEncuestaPorCliente(Integer id) {
		return this.encuesta.traerEncuestaPorCliente(id);
	}

}
