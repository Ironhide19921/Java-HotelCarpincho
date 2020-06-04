package persistencia.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import dto.ConfiguracionDTO;
import persistencia.conexion.Conexion;
import persistencia.dao.interfaz.ConfiguracionDAO;

public class ConfiguracionDAOSQL implements ConfiguracionDAO{
	
//	private static final String insert = "INSERT INTO config(username, password) "
//			+ "VALUES(?, ?)";
//	private static final String delete = "DELETE FROM cliente WHERE idCliente = ?";
	private static final String readall = "SELECT * FROM configuracion";
	private static final String update = "UPDATE configuracion SET username = ?, password = ?, provSMTP = ? WHERE idConfig = ?";
//	private static final String search = "SELECT * FROM cliente WHERE email LIKE ? OR documento LIKE ? OR idcliente LIKE ? OR nombre LIKE ? OR apellido LIKE ?";

//	@Override
//	public boolean insert(ConfiguracionDTO config) {
//		PreparedStatement statement;
//		Connection conexion = Conexion.getConexion().getSQLConexion();
//		boolean isInsertExitoso = false;
//		try{
//			statement = conexion.prepareStatement(insert);
//			//statement.setInt(1, cliente.getIdCliente());
//			statement.setString(1, config.getUsername());
//			statement.setString(2, config.getPassword());
//		
//			if(statement.executeUpdate() > 0){
//				conexion.commit();
//				isInsertExitoso = true;
//			}
//		} 
//		catch (SQLException e){
//			e.printStackTrace();
//			try {
//				conexion.rollback();
//			} catch (SQLException e1) {
//				e1.printStackTrace();
//			}
//		}
//		
//		return isInsertExitoso;
//	}

//	@Override
//	public boolean delete(ClienteDTO persona_a_eliminar) {
//		return false;
//	}

	@Override
	public List<ConfiguracionDTO> readAll() {
		PreparedStatement statement;
		ResultSet resultSet; //Guarda el resultado de la query
		ArrayList<ConfiguracionDTO> configuraciones = new ArrayList<ConfiguracionDTO>();
		Conexion conexion = Conexion.getConexion();
	
		try {
			statement = conexion.getSQLConexion().prepareStatement(readall);
			resultSet = statement.executeQuery();
			while(resultSet.next()){
				configuraciones.add(getConfiguracionDTOO(resultSet));
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		return configuraciones;
	}

	private ConfiguracionDTO getConfiguracionDTOO(ResultSet resultSet) throws SQLException {
		int id = resultSet.getInt("idConfig");
		String username = resultSet.getString("username");
		String password = resultSet.getString("password");
		String provSMTP = resultSet.getString("provSMTP");
		
		return new ConfiguracionDTO(id, username, password, provSMTP);
	}

	@Override
	public void update(ConfiguracionDTO config) {
		PreparedStatement statement;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		try{
			statement = conexion.prepareStatement(update);
			statement.setString(1, config.getUsername());
			statement.setString(2, config.getPassword());
			statement.setString(3, config.getProvSMTP());
			statement.setInt(4, config.getIdConfig());
			
			
			System.out.println(statement.executeUpdate() );		
			if(statement.executeUpdate() > 0)
			{
				conexion.commit();
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
			try {
				conexion.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	
//	@Override
//	public List<ClienteDTO> search(String buscar) {
//		PreparedStatement statement;
//		
//		ResultSet resultSet; //Guarda el resultado de la query
//		ArrayList<ClienteDTO> clientes = new ArrayList<ClienteDTO>();
//		Conexion conexion = Conexion.getConexion();
//		try {
//			
//			statement = conexion.getSQLConexion().prepareStatement(search);
////			statement.setString(1, buscar);
//			statement.setString(1,"%" + buscar + "%");
//			statement.setString(2,"%" + buscar + "%");
//			statement.setString(3,"%" + buscar + "%");
//			statement.setString(4,"%" + buscar + "%");
//			statement.setString(5,"%" + buscar + "%");
//
//			resultSet = statement.executeQuery();
//			while(resultSet.next()){
//				clientes.add(getClienteDTOO(resultSet));
//			}
//		} 
//		catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return clientes;
//	}

}
