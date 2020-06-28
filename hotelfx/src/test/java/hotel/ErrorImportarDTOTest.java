package hotel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.sql.Timestamp;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import dto.ErrorImportarDTO;
import modelo.ErrorImportar;
import persistencia.dao.mysql.DAOSQLFactory;

public class ErrorImportarDTOTest {
	
	private ErrorImportar error;

	@Before
	public void init() {
		error = new ErrorImportar(new DAOSQLFactory());
		
	}	
	
	private List<ErrorImportarDTO> getAllErrores() {
		List<ErrorImportarDTO> errores = this.error.obtenereErrores();
		return errores;
	}
	
	@Test
	public void testAgregarBusquedayBorrado() throws Exception {
		//Agregado
		int tamaño = getAllErrores().size();
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		ErrorImportarDTO errorAinsertar = new ErrorImportarDTO(00, timestamp, 1, "Error de test");
		error.agregarError(errorAinsertar);
		assertEquals(tamaño+1,getAllErrores().size());
		
		//Busqueda por 
		List<ErrorImportarDTO> erroresBuscados = error.buscarError(String.valueOf(errorAinsertar.getUsuario()));
		assertTrue(erroresBuscados.size()>0);
		
		//Borrado
		error.borrarError(getAllErrores().get(getAllErrores().size()-1));
		assertEquals(tamaño,getAllErrores().size());

	}

}
