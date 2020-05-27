package persistencia.dao.mysql;

import persistencia.dao.interfaz.CategoriaCuartoDAO;
import persistencia.dao.interfaz.ClienteDAO;
import persistencia.dao.interfaz.CuartoDAO;
import persistencia.dao.interfaz.DAOAbstractFactory;
import persistencia.dao.interfaz.PerfilDAO;
import persistencia.dao.interfaz.PermisoPerfilDAO;
import persistencia.dao.interfaz.ProductoDAO;
import persistencia.dao.interfaz.SalonDAO;
import persistencia.dao.interfaz.UsuarioDAO;

public class DAOSQLFactory implements DAOAbstractFactory{
	
	public ClienteDAO createClienteDAO() {
		return new ClienteDAOSQL();
	}
	
	public CategoriaCuartoDAO createCategoriaCuartoDAO() {
		return new CategoriaCuartoDAOSQL();
	}

	public UsuarioDAO createUsuarioDAO() {
		return new UsuarioDAOSQL();
	}

	public PerfilDAO createPerfilDAO() {
		return new PerfilDAOSQL();
	}

	public PermisoPerfilDAO createPermisoPerfilDAO() {
		return new PermisoPerfilDAOSQL();
	}

	public CuartoDAO createCuartoDAO() {
		return new CuartoDAOSQL();
	}
	
	public ProductoDAO createProductoDAO() {
		return new ProductoDAOSQL();
	}

	public SalonDAO createSalonDAO() {
		return new SalonDAOSQL();
	}

}
