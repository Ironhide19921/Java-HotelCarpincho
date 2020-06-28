package hotel;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import dto.PerfilDTO;
import dto.PermisoPerfilDTO;
import modelo.Cuarto;
import modelo.Perfil;
import modelo.PermisoPerfil;
import persistencia.dao.mysql.DAOSQLFactory;

public class ControladorABMPerfilTest {
	
	private static Perfil modeloPerfil;
	private static PermisoPerfil modeloPermisoPerfil;
	private static PerfilDTO perfilDTO;
	
	private static List<PerfilDTO> listaPerfiles;
	
	@Before
	public void init() {
		modeloPerfil = new Perfil(new DAOSQLFactory());
		modeloPermisoPerfil = new PermisoPerfil(new DAOSQLFactory());

		modeloPerfil.agregarPerfil(new PerfilDTO(0,"perfil 0"));
		modeloPerfil.agregarPerfil(new PerfilDTO(1,"perfil 1"));
		modeloPerfil.agregarPerfil(new PerfilDTO(2,"perfil 2"));
		listaPerfiles = modeloPerfil.obtenerPerfil();
	}
	
	@Test
	public void testEliminarPerfil() throws Exception {

		assertEquals(3, listaPerfiles.size());
		
		assertEquals("perfil 0", listaPerfiles.get(0).getNombre());
		assertEquals("perfil 1", listaPerfiles.get(1).getNombre());
		assertEquals("perfil 2", listaPerfiles.get(2).getNombre());
		
		for(PerfilDTO perfil : listaPerfiles) {
			modeloPerfil.borrarPerfil(perfil);
		}
		listaPerfiles = modeloPerfil.obtenerPerfil();
		
		assertEquals(0, listaPerfiles.size());
	}
	
	@Test
	public void testConfirmarPermisos() throws Exception {
		
		for(int i = 0; i<21;i++) {
			modeloPermisoPerfil.agregarPermiso (new PermisoPerfilDTO(0,listaPerfiles.get(0).getIdPerfil(),i+1));
		}
		
		List<PermisoPerfilDTO> listaPermisoPerfiles = modeloPermisoPerfil.obtenerpermisos();
		
		for(int i = 1; i<22;i++) {
			assertEquals(i, listaPermisoPerfiles.get(i-1).getIdPermiso());
		}
		
		for(PermisoPerfilDTO permisoPerfil : listaPermisoPerfiles) {
			modeloPermisoPerfil.eliminarPermiso(permisoPerfil);
		}
		
		for(PerfilDTO perfil : listaPerfiles) {
			modeloPerfil.borrarPerfil(perfil);
		}
		
		
	}
}
