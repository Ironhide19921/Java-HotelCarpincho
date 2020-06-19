package persistencia.dao.interfaz;

import java.sql.Timestamp;
import java.util.List;

import dto.CuartoDTO;

public interface CuartoDAO {
	
	public boolean insert(CuartoDTO cuarto);

	public boolean delete(CuartoDTO cuarto_a_eliminar);
	
	public List<CuartoDTO> readAll();
	
	public void update(CuartoDTO cuarto);
	
	public List<CuartoDTO> search(String buscar);
	
	public void cambiarEstado(CuartoDTO cuarto);
	
	public CuartoDTO traerCuarto(Integer id);

	public List<CuartoDTO> obtenerCuartosDisponibles(Timestamp fechaEgreso, Timestamp fechaIngreso);

	public List<CuartoDTO> obtenerCuarto(int parseInt);
}
