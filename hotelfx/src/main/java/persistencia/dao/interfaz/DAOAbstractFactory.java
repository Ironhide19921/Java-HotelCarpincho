package persistencia.dao.interfaz;

import modelo.ConexionConfig;

public interface DAOAbstractFactory {
		
	public ClienteDAO createClienteDAO();
	
	public CategoriaCuartoDAO createCategoriaCuartoDAO();

	public UsuarioDAO createUsuarioDAO();
	
	public PerfilDAO createPerfilDAO();
	
	public PermisoPerfilDAO createPermisoPerfilDAO();
	
	public CuartoDAO createCuartoDAO();
	
	public ProductoDAO createProductoDAO();

	public SalonDAO createSalonDAO();
	
	public CategoriaEventoDAO createCategoriaEventoDAO();

	public ReservaEventoDAO createReservaEventoDAO();
	
	public ConfiguracionDAO createConfiguracionDAO();
	
	public ErrorImportarDAO createErrorImportarDAO();

	public EmailDAO createEmailDAO();
	
	public OrdenPedidoDAO createOrdenPedidoDAO();

	public ReservaCuartoDAO createReservaCuartoDAO();
	
	public TicketDAO createTicketDAO();

	public EncuestaDAO createEncuestaDAO();

	public ConexionConfigDAO createConexionConfigDAO();

}
