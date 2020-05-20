package modelo;

import java.util.List;

import dto.CategoriaCuartoDTO;
import persistencia.dao.interfaz.CategoriaCuartoDAO;
import persistencia.dao.interfaz.DAOAbstractFactory;

public class CategoriaCuarto {
	private CategoriaCuartoDAO categoriaCuarto;
	
	public CategoriaCuarto(DAOAbstractFactory metodo_persistencia){
		this.categoriaCuarto = metodo_persistencia.createCategoriaCuartoDAO();	this.categoriaCuarto = metodo_persistencia.createCategoriaCuartoDAO();
	}
	public void agregarCategoriaCuarto(CategoriaCuartoDTO nuevaCategoriaCuarto) {
		this.categoriaCuarto.insert(nuevaCategoriaCuarto);
	}
	
	public void modificarCategoriaCuarto(CategoriaCuartoDTO categoriaCuarto) {
		this.categoriaCuarto.update(categoriaCuarto);
	}
	
	public List<CategoriaCuartoDTO> obtenerCategoriasCuartos() {
		return this.categoriaCuarto.readAll();
	}
	
	public void borrarCategoriaCuarto(CategoriaCuartoDTO categoriaCuarto) {
		this.categoriaCuarto.delete(categoriaCuarto);
	}
}
