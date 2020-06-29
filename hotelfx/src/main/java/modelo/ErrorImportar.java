package modelo;

import java.util.List;
import dto.ErrorImportarDTO;
import dto.ProductoDTO;
import persistencia.dao.interfaz.DAOAbstractFactory;
import persistencia.dao.interfaz.ErrorImportarDAO;

public class ErrorImportar {
	
private ErrorImportarDAO error;
	
	public ErrorImportar(DAOAbstractFactory metodo_persistencia){
		this.error = metodo_persistencia.createErrorImportarDAO();
	}
	
	public void agregarError(ErrorImportarDTO nuevoError){
		this.error.insert(nuevoError);
	}
	
	public List<ErrorImportarDTO> obtenereErrores() {
		return this.error.readAll();
	}
	
	public List<ErrorImportarDTO> buscarError(String buscar) 	{
		return this.error.search(buscar);
	}
	
	public void borrarError(ErrorImportarDTO error) {
		this.error.delete(error);
	}

}
