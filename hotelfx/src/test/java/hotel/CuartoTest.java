package hotel;

import static org.junit.Assert.assertEquals;

import java.sql.SQLException;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import dto.CuartoDTO;
import modelo.Cuarto;
import persistencia.conexion.Conexion;
import persistencia.dao.mysql.DAOSQLFactory;

public class CuartoTest {
	
	private CuartoDTO cuartoDto;
	private Cuarto cuarto;
	
	@Before
	public void init() {
		cuarto = new Cuarto(new DAOSQLFactory());
		cuartoDto = new CuartoDTO(0, 1, "4", 520, 50, "3", "Z", true);
		cuarto.agregarCuarto(cuartoDto);
		
	}
	
	@Test
	public void testBuscarCuartos() throws Exception {
		cuarto = new Cuarto(new DAOSQLFactory());
		List<CuartoDTO> listaCuartos = cuarto.buscarCuartos("Z");
		assertEquals(1, listaCuartos.size());
				
	}
		
	@Test
	public void testCambiarEstadoCuarto() throws Exception {
		cuarto = new Cuarto(new DAOSQLFactory());
		cuartoDto.setEstado(false);
		cuarto.modificarEstado(cuartoDto);		
		assertEquals(false, cuartoDto.getEstado());
	
	}
	
	@Test
	public void testCambiarCuarto() throws Exception {
		cuarto = new Cuarto(new DAOSQLFactory());
		cuartoDto.setCapacidad("8");
		cuartoDto.setHabitacion("123");
		cuarto.modificarEstado(cuartoDto);		
		assertEquals("8", cuartoDto.getCapacidad());
		assertEquals("123", cuartoDto.getHabitacion());
	}
	
	@After
	public void eliminarCuarto() {
		try {
			java.sql.Statement statement = Conexion.instancia.getSQLConexion().createStatement();
			statement.executeUpdate("DELETE FROM `cuarto` WHERE 1");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@AfterClass
	public static void normalizar() {
		try {
			Conexion.instancia.getSQLConexion().setAutoCommit(true);
			Conexion.instancia.getSQLConexion().close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
