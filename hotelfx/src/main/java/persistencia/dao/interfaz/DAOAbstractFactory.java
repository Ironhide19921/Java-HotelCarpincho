package persistencia.dao.interfaz;

public interface DAOAbstractFactory {
		
	public ClienteDAO createClienteDAO();
	
	public CategoriaCuartoDAO createCategoriaCuartoDAO();

	public UsuarioDAO createUsuarioDAO();
	
	public PerfilDAO createPerfilDAO();
	
	public PermisoPerfilDAO createPermisoPerfilDAO();
	
	public CuartoDAO createCuartoDAO();
	
	public SalonDAO createSalonDAO();
	
	public CategoriaEventoDAO createCategoriaEventoDAO();

	public ReservaEventoDAO createReservaEventoDAO();
}
