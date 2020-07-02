package hotel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import dto.CategoriaCuartoDTO;
import dto.ClienteDTO;
import dto.CuartoDTO;
import dto.ReservaCuartoDTO;
import dto.ReservaCuartoDTO.EstadoReserva;
import dto.ReservaCuartoDTO.FormaPago;
import dto.ReservaCuartoDTO.TipoTarjeta;
import modelo.Cliente;
import modelo.Cuarto;
import modelo.ReservaCuarto;
import modelo.Usuario;
import persistencia.conexion.Conexion;
import persistencia.dao.mysql.DAOSQLFactory;

public class ReservaCuartoTest {
	private static Usuario modeloUsuario;
	private static ReservaCuarto modeloReservaCuarto;
	private static Cuarto modeloCuartos;
	private static Cliente modeloCliente;
	private static List<ReservaCuartoDTO> listaReservaCuarto;
	private static ReservaCuartoDTO reservaCuartoDTO; //dependiente
	
	@Before
	public void init() {
		
		modeloUsuario = new Usuario(new DAOSQLFactory());
		modeloReservaCuarto = new ReservaCuarto(new DAOSQLFactory());
		modeloCliente = new Cliente(new DAOSQLFactory());
		modeloCuartos = new Cuarto(new DAOSQLFactory());
		modeloReservaCuarto = new ReservaCuarto(new DAOSQLFactory());
		
	}
	
	@Test
	public void testGeneral()
	{
		testAgregarReservaCuarto();
		testDeshabilitarReservaCuarto();
		testHabilitarReservaCuarto();
		buscarReserva();
	}
	
	private void testAgregarReservaCuarto()
	{
		ClienteDTO cli = modeloCliente.getClientePorId(1);
		int idUsuario = 1;
		CuartoDTO c = modeloCuartos.traerCuarto(1);
		int idCuarto = 1;
		int idCategoriaCuarto = c.getIdCateCuarto();
		
		
		BigDecimal Senia = BigDecimal.valueOf(333.12);
		BigDecimal MontoReservaCuarto = BigDecimal.valueOf(1222.12);
		BigDecimal MontoTotal = BigDecimal.valueOf(242122.2);
		Timestamp FechaGeneracionReserva = Timestamp.valueOf("2020-06-27 20:35:18");
		Timestamp FechaInicioReserva = Timestamp.valueOf("2020-06-28 20:35:18");
		Timestamp FechaFinReserva = Timestamp.valueOf("2020-06-29 20:35:18");
		Timestamp FechaIngreso = null;
		Timestamp FechaEgreso = null;
		
		String NumeroTarjeta = "4451451264512356";
		String FechaVencTarjeta = "12/30";
		String CodSeguridadTarjeta = "915";
		EstadoReserva estado = EstadoReserva.PENDIENTE;
		FormaPago formaPago = FormaPago.EFECTIVO;
		TipoTarjeta tipoTarjeta = TipoTarjeta.VISA;
		
		String Observaciones = "asd";
		
		ReservaCuartoDTO rc = new ReservaCuartoDTO(cli.getIdCliente(),idCuarto,idUsuario,BigDecimal.valueOf(c.getMontoSenia()),BigDecimal.valueOf(c.getMonto()),cli.getEmail(),
				NumeroTarjeta,formaPago,tipoTarjeta,CodSeguridadTarjeta,FechaVencTarjeta,FechaGeneracionReserva,FechaIngreso,FechaEgreso,estado,"",true); 
		modeloReservaCuarto.agregarReservaCuarto(rc);
		
		listaReservaCuarto = modeloReservaCuarto.obtenerReservasCuartos();
		assertEquals(1, listaReservaCuarto.size());
	}
	
	
	private void testDeshabilitarReservaCuarto()
	{

		listaReservaCuarto = modeloReservaCuarto.obtenerReservasCuartos();
		ReservaCuartoDTO rc = listaReservaCuarto.get(0); 
		boolean estadoAnterior = listaReservaCuarto.get(0).getEstado();
		if(estadoAnterior == true) {
			rc.setEstado(false);
			assertEquals(false,rc.getEstado());
		}
		
	}
	
	private void  testHabilitarReservaCuarto()
	{

		listaReservaCuarto = modeloReservaCuarto.obtenerReservasCuartos();
		System.out.println(listaReservaCuarto.size());
		ReservaCuartoDTO rc = listaReservaCuarto.get(0); 
		System.out.println(rc.getCantidadDias());
		boolean estadoAnterior = listaReservaCuarto.get(0).getEstado();
		if(estadoAnterior == false) {
			rc.setEstado(true);
			assertEquals(true,listaReservaCuarto.get(0).getEstado());
		}		
	}
	
	
	private void buscarReserva()
	{
		List<ReservaCuartoDTO> lista = modeloReservaCuarto.buscarReservaCuarto("1");
		assertEquals(1, lista.size());
	}
	
	@After
	public void eliminarReservasCuarto() {
		try {
			java.sql.Statement statement = Conexion.instancia.getSQLConexion().createStatement();
			statement.executeUpdate("DELETE FROM `reservacuarto`");
			
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
