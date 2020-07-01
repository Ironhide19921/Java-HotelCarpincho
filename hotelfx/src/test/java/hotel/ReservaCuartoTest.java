package hotel;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import dto.CategoriaCuartoDTO;
import dto.CategoriaEventoDTO;
import dto.ClienteDTO;
import dto.CuartoDTO;
import dto.PerfilDTO;
import dto.ReservaCuartoDTO;
import dto.ReservaCuartoDTO.EstadoReserva;
import dto.ReservaCuartoDTO.FormaPago;
import dto.ReservaCuartoDTO.TipoTarjeta;
import dto.SalonDTO;
import dto.UsuarioDTO;
import modelo.CategoriaCuarto;
import modelo.CategoriaEvento;
import modelo.Cliente;
import modelo.Cuarto;
import modelo.Perfil;
import modelo.ReservaCuarto;
import modelo.ReservaEvento;
import modelo.Salon;
import modelo.Usuario;
import persistencia.conexion.Conexion;
import persistencia.dao.mysql.DAOSQLFactory;

public class ReservaCuartoTest {
	private static Usuario modeloUsuario;
	private static ReservaCuarto modeloReservaCuarto;
	private static Cuarto modeloCuartos;
	private static Cliente modeloCliente;
	private static CategoriaCuarto modeloCategoriaCuarto;
	private static Perfil modeloPerfil;
	
	private static List<PerfilDTO> listaPerfiles;
	private static List<UsuarioDTO> listaUsuarios;
	private static List<CuartoDTO> listaCuartos;
	private static List<ReservaCuartoDTO> listaReservaCuarto;
	private static List<ClienteDTO> listaCliente;
	private static List<CategoriaCuartoDTO> listaCategoria;
	
	private static UsuarioDTO usuarioTest;
	private static CuartoDTO cuartoTest;
	private static ReservaCuartoDTO reservaTest;
	private static ClienteDTO clienteTest;
	private static CategoriaCuartoDTO categoriaCuartoTest;
	private static PerfilDTO perfilTest;
	
	
	private static ReservaCuartoDTO reservaCuartoDTO; //dependiente
	
	@Before
	public void init() {
		/*
		modeloUsuario = new Usuario(new DAOSQLFactory());
		modeloReservaCuarto = new ReservaCuarto(new DAOSQLFactory());
		modeloCliente = new Cliente(new DAOSQLFactory());
		modeloCuartos = new Cuarto(new DAOSQLFactory());
		modeloCategoriaCuarto = new CategoriaCuarto(new DAOSQLFactory());
		modeloPerfil = new Perfil(new DAOSQLFactory());
	
		crearCategoriaCuarto();
		crearCuarto();
		crearPerfil();
		crearUsuario();
		crearCliente();
		
		int idReservaEvento = 0;
		int idCliente = listaCliente.get(0).getIdCliente();
		int idUsuario = listaUsuarios.get(0).getIdUsuario();
		int idCuarto = listaCuartos.get(0).getId();
		int idCategoriaCuarto = listaCategoria.get(0).getIdCategoriaCuarto();
		
		BigDecimal Senia = BigDecimal.valueOf(333.12);
		BigDecimal MontoReservaEvento = BigDecimal.valueOf(1222.12);
		BigDecimal MontoTotal = BigDecimal.valueOf(242122.2);
		Timestamp FechaGeneracionReserva = Timestamp.valueOf("2020-06-27 20:35:18");
		Timestamp FechaInicioReserva = Timestamp.valueOf("2020-06-28 20:35:18");
		Timestamp FechaFinReserva = Timestamp.valueOf("2020-06-29 20:35:18");
		Timestamp FechaIngreso = null;
		Timestamp FechaEgreso = null;
		FormaPago FormaPago = FormaPago.EFECTIVO;
		TipoTarjeta TipoTarjeta = TipoTarjeta.NO;
		String NumeroTarjeta = "0";
		String FechaVencTarjeta = "0";
		String CodSeguridadTarjeta = "0";
		EstadoReserva estado = EstadoReserva.PENDIENTE;
		String Observaciones = "asd";
		
		
	//	reservaEventoDTO = new ReservaEventoDTO(idReservaEvento, idCliente, idUsuario, idSalon, idCategoriaEvento, Senia, MontoReservaEvento, MontoTotal, FechaGeneracionReserva, FechaInicioReserva, FechaFinReserva, FechaIngreso, FechaEgreso, FormaPago, TipoTarjeta, NumeroTarjeta, FechaVencTarjeta, CodSeguridadTarjeta, estado, Observaciones);
	}
	
	@Test
	public void testAgregarReservaEvento()
	{
		modeloReservaCuarto = new ReservaCuarto(new DAOSQLFactory());
		modeloReservaCuarto.agregarReservaCuarto(reservaCuartoDTO);
		listaReservaCuarto = modeloReservaCuarto.obtenerReservasCuartos();
		assertEquals(1, listaReservaCuarto.size());
	}
	
	@Test
	public void testDeshabilitarReservaCuarto()
	{
		modeloReservaCuarto = new ReservaCuarto(new DAOSQLFactory());
		modeloReservaCuarto.agregarReservaCuarto(reservaCuartoDTO);
		listaReservaCuarto = modeloReservaCuarto.obtenerReservasCuartos();
		
	//modeloReservaCuarto.borrarReservaCuarto(listaReservaCuarto.get(0));
		//listaReservaCuarto = modeloReservaCuarto.obtenerReservasCuarto();
		//assertEquals(0, listaReservaEvento.size());
	}
	
	@Test
	public void testModificarReservaCuarto()
	{
		modeloReservaCuarto = new ReservaCuarto(new DAOSQLFactory());
		modeloReservaCuarto.agregarReservaCuarto(reservaCuartoDTO);
		listaReservaCuarto = modeloReservaCuarto.obtenerReservasCuartos();
		listaReservaCuarto.get(0).setMontoReservaCuarto(BigDecimal.valueOf(1222.222));
		
		modeloReservaCuarto.modificarReservaCuarto(listaReservaCuarto.get(0));
		
		listaReservaCuarto = modeloReservaCuarto.obtenerReservasCuartos();
		assertEquals(BigDecimal.valueOf(1222.222),listaReservaCuarto.get(0).getMontoReservaCuarto());
	}
	
	@Test
	public void testCambiarEstadoReservaCuarto()
	{
		modeloReservaCuarto = new ReservaCuarto(new DAOSQLFactory());
		modeloReservaCuarto.agregarReservaCuarto(reservaCuartoDTO);
		listaReservaCuarto = modeloReservaCuarto.obtenerReservasCuartos();
		listaReservaCuarto.get(0).setEstado(EstadoReserva.CANCELADO);
		modeloReservaCuarto.modificarReservaCuarto(listaReservaCuarto.get(0));
		
		listaReservaCuarto = modeloReservaCuarto.obtenerReservasCuartos();
		assertEquals("CANCELADO", String.valueOf(listaReservaCuarto.get(0).getEstado()));
	}
	
	
	@Test
	public void testSetearIngresoEgresoReservaCuarto()
	{
		modeloReservaCuarto = new ReservaCuarto(new DAOSQLFactory());
		modeloReservaCuarto.agregarReservaCuarto(reservaCuartoDTO);
		listaReservaCuarto = modeloReservaCuarto.obtenerReservasCuartos();
		Timestamp tiempo = Timestamp.valueOf("2020-06-28 01:25:22");
		//modeloReservaEvento.setearIngresoEgresoReservaEvento(tiempo , null, listaReservaEvento.get(0).getIdReservaEvento());
		//listaReservaEvento = modeloReservaEvento.obtenerReservasEvento();
		//assertEquals(tiempo, listaReservaEvento.get(0).getFechaIngreso());
	}
	
	@Test
	public void testFechasEntre()
	{
		modeloReservaCuarto = new ReservaCuarto(new DAOSQLFactory());
		modeloReservaCuarto.agregarReservaCuarto(reservaCuartoDTO);
		listaReservaCuarto = modeloReservaCuarto.obtenerReservasCuartos();
		Timestamp tiempo1 = Timestamp.valueOf("2020-06-26 01:25:22");
		Timestamp tiempo2 = Timestamp.valueOf("2020-06-30 01:25:22");
		
		List<ReservaCuartoDTO> listaReservasEntre = modeloReservaCuarto.verReservasEntreFechas(tiempo1, tiempo2);
		listaReservaCuarto = modeloReservaCuarto.obtenerReservasCuartos();
		assertEquals(1, listaReservasEntre.size());
		
		
		Timestamp tiempo3 = Timestamp.valueOf("2020-06-28 10:25:22");
		Timestamp tiempo4 = Timestamp.valueOf("2020-06-28 19:25:22");
		
		listaReservasEntre = modeloReservaCuarto.verReservasEntreFechas(tiempo3, tiempo4);
		listaReservaCuarto = modeloReservaCuarto.obtenerReservasCuartos();
		
		assertEquals(0, listaReservasEntre.size());
	}
	
	@After
	public void normalizar() {
		try {
			java.sql.Statement statement = Conexion.instancia.getSQLConexion().createStatement();
			statement.executeUpdate("TRUNCATE reservaevento;");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@AfterClass
	public static void normalizarConexion() {
		try {
			Conexion.instancia.getSQLConexion().setAutoCommit(true);
			Conexion.instancia.getSQLConexion().close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	public void crearCliente() {
		clienteTest = new ClienteDTO(0, "Prueba", "Test", "DNI", "50456456", "cliente@gmail.com", "1523422345", true, Date.valueOf("2020-01-28"));
		modeloCliente.agregarCliente(clienteTest);
		listaCliente = modeloCliente.obtenerClientes();
	}
	
	public void crearCategoriaCuarto() {
		categoriaCuartoTest = new CategoriaCuartoDTO(0, 120, 33, "Salon test", BigDecimal.valueOf(1233.22), true);
		modeloCategoriaCuarto.agregarCategoriaCuarto(categoriaCuartoTest);
		listaCategoriaCuarto = modeloCategoriaCuarto.obtenerCategoriaCuarto();
	}
	
	public void crearUsuario() {
		usuarioTest = new UsuarioDTO(0, listaPerfiles.get(0).getIdPerfil(), "nombreTest", "apellidoTest", "tipoDoc", "37020664","test@hotmail.com", "123", true);
		modeloUsuario.agregarUsuario(usuarioTest);
		listaUsuarios = modeloUsuario.obtenerUsuarios();
	}
	
	public void crearCuarto() {
		cuartoTest = new CuartoDTO(0,"perfilTest");
		modeloCuarto.agregar
(perfilTest);
		listaPerfiles = modeloPerfil.obtenerPerfil();
	}
	
	public void crearPerfil() {
		perfilTest = new PerfilDTO(0,"perfilTest");
		modeloPerfil.agregarPerfil(perfilTest);
		listaPerfiles = modeloPerfil.obtenerPerfil();
		*/
	}
	
}
