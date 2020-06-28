package hotel;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import dto.CuartoDTO;
import modelo.Cuarto;
import persistencia.conexion.Conexion;
import persistencia.dao.mysql.DAOSQLFactory;

public class CuartoTest {
	
	private static CuartoDTO cuartoDto;
	private static Cuarto cuarto;
	
	@BeforeClass
	public static void init() {
		cuarto = new Cuarto(new DAOSQLFactory());
		cuartoDto = new CuartoDTO(0, 1, "4", 520, 50, "3", "Z", true);
		cuarto.agregarCuarto(cuartoDto);
		
	}
	
	@Test
	public void testBuscarCuartos() throws Exception {
		cuarto = new Cuarto(new DAOSQLFactory());
		List<CuartoDTO> listaCuartos = cuarto.buscarCuartos("Z");
		System.out.println(listaCuartos.size());
		assertEquals(1, listaCuartos.size());
				
	}
		
	@Test
	public void testCambiarEstadoCuarto() throws Exception {
		cuarto = new Cuarto(new DAOSQLFactory());
		cuartoDto.setEstado(false);
		cuarto.modificarEstado(cuartoDto);		
		assertEquals(false, cuartoDto.getEstado());
	
	}

}
