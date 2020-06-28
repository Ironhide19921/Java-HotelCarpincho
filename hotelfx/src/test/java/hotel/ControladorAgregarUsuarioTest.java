package hotel;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import dto.PerfilDTO;
import dto.UsuarioDTO;
import modelo.Perfil;
import modelo.Usuario;
import persistencia.dao.mysql.DAOSQLFactory;

//No contamos con el metodo para eliminar usuarios, esta clase deberia resetear sus valores manualmente desde la base
public class ControladorAgregarUsuarioTest {
	
	private static Usuario modeloUsuario;
	private static Perfil modeloPerfil;
	
	private static List<PerfilDTO> listaPerfiles;
	private static List<UsuarioDTO> listaUsuarios;
	
	private static UsuarioDTO nuevoUsuario;
	private static PerfilDTO perfilTest;
	
	@BeforeClass
	public static void init() {
		modeloUsuario = new Usuario(new DAOSQLFactory());
		modeloPerfil = new Perfil(new DAOSQLFactory());
		
		perfilTest = new PerfilDTO(0,"perfilTest");
		modeloPerfil.agregarPerfil(perfilTest);
		listaPerfiles = modeloPerfil.obtenerPerfil();
		
		nuevoUsuario = new UsuarioDTO(0, listaPerfiles.get(0).getIdPerfil(), "nombreTest", "apellidoTest", "tipoDoc", "37020664","test@hotmail.com", "123", true);
	}
	
	@Test
	public void testGuardarUsuario() throws Exception {
		modeloUsuario.agregarUsuario(nuevoUsuario);
		listaUsuarios = modeloUsuario.obtenerUsuarios();
		assertEquals(1, listaUsuarios.size());
		assertEquals("nombreTest", listaUsuarios.get(0).getNombre());
	}
	
	@Test
	public void testModificarUsuario() throws Exception {
		listaUsuarios.get(0).setNombre("nombreTestModificado");
		assertEquals("nombreTestModificado", listaUsuarios.get(0).getNombre());
	}
	
	@AfterClass
	public static void normalizar() {
		
	}

	
}
