package persistencia.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import dto.ConfiguracionDTO;
import dto.ProductoDTO;
import persistencia.conexion.Conexion;
import persistencia.dao.interfaz.ConfiguracionDAO;

public class ConfiguracionDAOSQL implements ConfiguracionDAO{
	
	private static final String readall = "SELECT * FROM configuracion";
	private static final String update = "UPDATE configuracion SET username = ?, password = ?, provSMTP = ? WHERE idConfig = ?";
	
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

}
