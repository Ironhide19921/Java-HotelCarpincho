package persistencia.dao.interfaz;

import modelo.Usuario;

public interface DAOAbstractFactory {
		
	public ClienteDAO createClienteDAO();
	
	public CategoriaCuartoDAO createCategoriaCuartoDAO();

	public UsuarioDAO createUsuarioDAO();
	
	public PerfilDAO createPerfilDAO();
	
	public PermisoPerfilDAO createPermisoPerfilDAO();
	
	public CuartoDAO createCuartoDAO();
	
	public ProductoDAO createProductoDAO();

	public SalonDAO createSalonDAO();

	public EmailDAO createEmailDAO();

}
