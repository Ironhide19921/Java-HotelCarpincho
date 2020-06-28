package hotel;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import dto.CategoriaEventoDTO;
import dto.ClienteDTO;
import dto.PerfilDTO;
import dto.ReservaEventoDTO;
import dto.UsuarioDTO;
import dto.ReservaEventoDTO.EstadoReserva;
import dto.ReservaEventoDTO.FormaPago;
import dto.ReservaEventoDTO.TipoTarjeta;
import dto.SalonDTO;
import modelo.CategoriaEvento;
import modelo.Cliente;
import modelo.Perfil;
import modelo.ReservaEvento;
import modelo.Salon;
import modelo.Usuario;
import persistencia.conexion.Conexion;
import persistencia.dao.mysql.DAOSQLFactory;


public class ReservaEventoTest{
	private static Usuario modeloUsuario;
	private static Perfil modeloPerfil;
	private static Salon modeloSalon;
	private static CategoriaEvento modeloCategoriaEvento;
	private static Cliente modeloCliente;
	private static ReservaEvento modeloReservaEvento;
	
	private static List<PerfilDTO> listaPerfiles;
	private static List<UsuarioDTO> listaUsuarios;
	private static List<SalonDTO> listaSalones;
	private static List<CategoriaEventoDTO> listaCategoriaEvento;
	private static List<ClienteDTO> listaCliente;
	private static List<ReservaEventoDTO> listaReservaEvento;
	
	private static UsuarioDTO usuarioTest;
	private static PerfilDTO perfilTest;
	private static SalonDTO salonTest;
	private static CategoriaEventoDTO categoriaEventoTest;
	private static ClienteDTO clienteTest;
	
	
	private static ReservaEventoDTO reservaEventoDTO; //dependiente
	
	@Before
	public void init() {
		
		modeloUsuario = new Usuario(new DAOSQLFactory());
		modeloPerfil = new Perfil(new DAOSQLFactory());
		modeloSalon = new Salon(new DAOSQLFactory());
		modeloCategoriaEvento = new CategoriaEvento(new DAOSQLFactory());
		modeloCliente = new Cliente(new DAOSQLFactory());
		
		
		crearPerfil();
		crearUsuario();
		crearSalon();
		crearCategoriaEvento();
		crearCliente();
		
		int idReservaEvento = 0;
		int idCliente = listaCliente.get(0).getIdCliente();
		int idUsuario = listaUsuarios.get(0).getIdUsuario();
		int idSalon = listaSalones.get(0).getId();
		int idCategoriaEvento = listaCategoriaEvento.get(0).getId();
		BigDecimal Senia = BigDecimal.valueOf(333.12);
		BigDecimal MontoReservaEvento = BigDecimal.valueOf(1222.12);
		BigDecimal MontoTotal = BigDecimal.valueOf(242122.2);
		Timestamp FechaGeneracionReserva = Timestamp.valueOf("2020-06-27 20:35:18");
		Timestamp FechaInicioReserva = Timestamp.valueOf("2020-06-28 20:35:18");
		Timestamp FechaFinReserva = Timestamp.valueOf("2020-06-29 20:35:18");
		Timestamp FechaIngreso = null;
		Timestamp FechaEgreso = null;
		FormaPago FormaPago = dto.ReservaEventoDTO.FormaPago.EFECTIVO;
		TipoTarjeta TipoTarjeta = dto.ReservaEventoDTO.TipoTarjeta.NO;
		String NumeroTarjeta = "0";
		String FechaVencTarjeta = "0";
		String CodSeguridadTarjeta = "0";
		EstadoReserva estado = dto.ReservaEventoDTO.EstadoReserva.PENDIENTE;
		String Observaciones = "asd";
		
		reservaEventoDTO = new ReservaEventoDTO(idReservaEvento, idCliente, idUsuario, idSalon, idCategoriaEvento, Senia, MontoReservaEvento, MontoTotal, FechaGeneracionReserva, FechaInicioReserva, FechaFinReserva, FechaIngreso, FechaEgreso, FormaPago, TipoTarjeta, NumeroTarjeta, FechaVencTarjeta, CodSeguridadTarjeta, estado, Observaciones);
	}
	
	@Test
	public void testAgregarReservaEvento()
	{
		modeloReservaEvento = new ReservaEvento(new DAOSQLFactory());
		
		
		modeloReservaEvento.agregarReservaEvento(reservaEventoDTO);
		listaReservaEvento = modeloReservaEvento.obtenerReservasEvento();
		assertEquals(1, listaReservaEvento.size());
	}
	
	@Test
	public void testBorrarReservaEvento()
	{
		modeloReservaEvento = new ReservaEvento(new DAOSQLFactory());
		modeloReservaEvento.agregarReservaEvento(reservaEventoDTO);
		listaReservaEvento = modeloReservaEvento.obtenerReservasEvento();
		
		modeloReservaEvento.borrarReservaEvento(listaReservaEvento.get(0));
		listaReservaEvento = modeloReservaEvento.obtenerReservasEvento();
		assertEquals(0, listaReservaEvento.size());
	}
	
	@Test
	public void testModificarReservaEvento()
	{
		modeloReservaEvento = new ReservaEvento(new DAOSQLFactory());
		modeloReservaEvento.agregarReservaEvento(reservaEventoDTO);
		listaReservaEvento = modeloReservaEvento.obtenerReservasEvento();
		listaReservaEvento.get(0).setMontoTotal(BigDecimal.valueOf(1222.222));
		
		modeloReservaEvento.modificarReservaEvento(listaReservaEvento.get(0));
		
		listaReservaEvento = modeloReservaEvento.obtenerReservasEvento();
		assertEquals(BigDecimal.valueOf(1222.222), listaReservaEvento.get(0).getMontoTotal());
	}
	
	@Test
	public void testCambiarEstadoReservaEvento()
	{
		modeloReservaEvento = new ReservaEvento(new DAOSQLFactory());
		modeloReservaEvento.agregarReservaEvento(reservaEventoDTO);
		listaReservaEvento = modeloReservaEvento.obtenerReservasEvento();
		
		modeloReservaEvento.cambiarEstadoReserva(listaReservaEvento.get(0).getIdReservaEvento(), "CANCELADO");
		listaReservaEvento = modeloReservaEvento.obtenerReservasEvento();
		assertEquals("CANCELADO", String.valueOf(listaReservaEvento.get(0).getEstado()));
	}
	
	
	@Test
	public void testSetearIngresoEgresoReservaEvento()
	{
		modeloReservaEvento = new ReservaEvento(new DAOSQLFactory());
		modeloReservaEvento.agregarReservaEvento(reservaEventoDTO);
		listaReservaEvento = modeloReservaEvento.obtenerReservasEvento();
		Timestamp tiempo = Timestamp.valueOf("2020-06-28 01:25:22");
		modeloReservaEvento.setearIngresoEgresoReservaEvento(tiempo , null, listaReservaEvento.get(0).getIdReservaEvento());
		listaReservaEvento = modeloReservaEvento.obtenerReservasEvento();
		assertEquals(tiempo, listaReservaEvento.get(0).getFechaIngreso());
	}
	
	@Test
	public void testFechasEntre()
	{
		modeloReservaEvento = new ReservaEvento(new DAOSQLFactory());
		modeloReservaEvento.agregarReservaEvento(reservaEventoDTO);
		listaReservaEvento = modeloReservaEvento.obtenerReservasEvento();
		Timestamp tiempo1 = Timestamp.valueOf("2020-06-26 01:25:22");
		Timestamp tiempo2 = Timestamp.valueOf("2020-06-30 01:25:22");
		
		List<ReservaEventoDTO> listaReservasEntre = modeloReservaEvento.verReservasEntreFechas(tiempo1, tiempo2);
		listaReservaEvento = modeloReservaEvento.obtenerReservasEvento();
		assertEquals(1, listaReservasEntre.size());
		
		
		Timestamp tiempo3 = Timestamp.valueOf("2020-06-28 10:25:22");
		Timestamp tiempo4 = Timestamp.valueOf("2020-06-28 19:25:22");
		
		listaReservasEntre = modeloReservaEvento.verReservasEntreFechas(tiempo3, tiempo4);
		listaReservaEvento = modeloReservaEvento.obtenerReservasEvento();
		
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
	
	public void crearCategoriaEvento() {
		categoriaEventoTest = new CategoriaEventoDTO(0, "Categoria test", "Categoria para testeo"); 
		modeloCategoriaEvento.agregarCategoriaEvento(categoriaEventoTest);
		listaCategoriaEvento = modeloCategoriaEvento.obtenerCategoriasEvento();
	}
	
	public void crearSalon() {
		salonTest = new SalonDTO(0, 120, 33, "Salon test", BigDecimal.valueOf(1233.22), true);
		modeloSalon.agregarSalon(salonTest);
		listaSalones = modeloSalon.obtenerSalones();
	}
	
	public void crearUsuario() {
		usuarioTest = new UsuarioDTO(0, listaPerfiles.get(0).getIdPerfil(), "nombreTest", "apellidoTest", "tipoDoc", "37020664","test@hotmail.com", "123", true);
		modeloUsuario.agregarUsuario(usuarioTest);
		listaUsuarios = modeloUsuario.obtenerUsuarios();
	}
	
	public void crearPerfil() {
		perfilTest = new PerfilDTO(0,"perfilTest");
		modeloPerfil.agregarPerfil(perfilTest);
		listaPerfiles = modeloPerfil.obtenerPerfil();
	}
}
