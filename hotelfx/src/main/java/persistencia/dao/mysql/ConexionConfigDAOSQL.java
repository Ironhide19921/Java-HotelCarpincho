package persistencia.dao.mysql;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dto.ConexionConfigDTO;
import persistencia.conexion.Conexion;
import persistencia.dao.interfaz.ConexionConfigDAO;

public class ConexionConfigDAOSQL implements ConexionConfigDAO {
	
	private static final String update = "UPDATE conexionConfig SET host = ?, user = ?, pass = ? WHERE idConexionConfig = ?";
	private static final String search = "SELECT * FROM conexionConfig WHERE idConexionConfig = ?";
	
	@Override
	public void update(ConexionConfigDTO conexionConfig) {
		PreparedStatement statement;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		try{
			statement = conexion.prepareStatement(update);
			statement.setString(1, conexionConfig.getHost());
			statement.setString(2, conexionConfig.getUser());
			statement.setString(3, conexionConfig.getPass());
			statement.setInt(4, conexionConfig.getIdConexionConfig());
			
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

	@Override
	public ConexionConfigDTO traerConexionConfig(Integer id) {
		PreparedStatement statement;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		ResultSet resultSet = null;
		ConexionConfigDTO conexionConfig = null;
		try{
			statement = conexion.prepareStatement(search);
			statement.setInt(1, id);
			resultSet = statement.executeQuery();
			if(statement.executeUpdate() > 0){
				conexion.commit();
				conexionConfig = getConexionConfigDTOO(resultSet);
			}
		} 
		catch (SQLException e){
			e.printStackTrace();
			try {
				conexion.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		return conexionConfig;
	}
	
	private ConexionConfigDTO getConexionConfigDTOO(ResultSet resultSet) throws SQLException {
		int id = resultSet.getInt("idConexionConfig");
		String host = resultSet.getString("Host");
		String user = resultSet.getString("User");
		String pass = resultSet.getString("Pass");

		return new ConexionConfigDTO(id, host, user, pass);
	}

}
