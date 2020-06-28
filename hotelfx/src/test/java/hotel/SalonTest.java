package hotel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import dto.SalonDTO;
import modelo.ReservaEvento;
import modelo.Salon;
import persistencia.conexion.Conexion;
import persistencia.dao.mysql.DAOSQLFactory;

public class SalonTest {
	private static Salon modeloSalon;
	
	private static List<SalonDTO> listaSalones;
	
	private static SalonDTO salonDTO;
	
	@Before
	public void init() {
		modeloSalon = new Salon(new DAOSQLFactory());
		
		salonDTO = new SalonDTO(0, 120, 22, "Saloncito", BigDecimal.valueOf(1300.333), true);
	}
	
	@Test
	public void testAgregarSalon()
	{
		modeloSalon = new Salon(new DAOSQLFactory());
		
		
		modeloSalon.agregarSalon(salonDTO);
		
		listaSalones = modeloSalon.obtenerSalones();
		assertEquals(1, listaSalones.size());
	}
	
	@Test
	public void testModificarSalon()
	{
		modeloSalon = new Salon(new DAOSQLFactory());
		modeloSalon.agregarSalon(salonDTO);
		listaSalones = modeloSalon.obtenerSalones();
		listaSalones.get(0).setEstilo("Salon con nombre nuevo");
		
		modeloSalon.modificarSalon(listaSalones.get(0));
		
		listaSalones = modeloSalon.obtenerSalones();
		assertEquals("Salon con nombre nuevo", listaSalones.get(0).getEstilo());
	}
	
	@Test
	public void testModificarEstadoSalon()
	{
		modeloSalon = new Salon(new DAOSQLFactory());
		modeloSalon.agregarSalon(salonDTO);
		listaSalones = modeloSalon.obtenerSalones();
		listaSalones.get(0).setEstado(false);
		
		modeloSalon.modificarEstado(listaSalones.get(0));
		
		listaSalones = modeloSalon.obtenerSalones();
		assertNotEquals(true, listaSalones.get(0).getEstado());
	}
	
	
	@After
	public void normalizar() {
		try {
			java.sql.Statement statement = Conexion.instancia.getSQLConexion().createStatement();
			statement.executeUpdate("DELETE FROM `salon` WHERE 1");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@AfterClass
	public static void normalizarConexion() {
		try {
//			java.sql.Statement statement = Conexion.instancia.getSQLConexion().createStatement();
			Conexion.instancia.getSQLConexion().setAutoCommit(true);
			Conexion.instancia.getSQLConexion().close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
