package modelo;

import java.util.List;

import dto.CuartoDTO;
import persistencia.dao.interfaz.CuartoDAO;
import persistencia.dao.interfaz.DAOAbstractFactory;

public class Cuarto {
	
private CuartoDAO cuarto;
	
	public Cuarto(DAOAbstractFactory metodo_persistencia){
		this.cuarto = metodo_persistencia.createCuartoDAO();
	}
	
	public void agregarCuarto(CuartoDTO nuevoCuarto){
		this.cuarto.insert(nuevoCuarto);
	}
	
	public void modificarCuartos(CuartoDTO cuarto) {
		this.cuarto.update(cuarto);
	}
	
	public List<CuartoDTO> obtenerCuartos() {
		return this.cuarto.readAll();
	}
	
	public List<CuartoDTO> buscarCuartos(String buscar) 	{
		return this.cuarto.search(buscar);
	}
	
	public void modificarEstado(CuartoDTO cuarto) {
		this.cuarto.cambiarEstado(cuarto);
	}

}
