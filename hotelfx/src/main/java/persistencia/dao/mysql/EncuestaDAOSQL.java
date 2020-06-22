package persistencia.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.CuartoDTO;
import dto.EncuestaDTO;
import persistencia.conexion.Conexion;
import persistencia.dao.interfaz.EncuestaDAO;

public class EncuestaDAOSQL implements EncuestaDAO{
	
	private static final String insert = "INSERT INTO encuesta(idEncuesta, idCliente, recipiente, encuestado) "
			+ "VALUES(?, ?, ?, ?)";
	private static final String readall = "SELECT * FROM encuesta";
	private static final String search1 = "SELECT * FROM encuesta WHERE idCliente = ?";
	private static final String update = "UPDATE encuesta SET encuestado = true WHERE idEncuesta = ?";
	
	@Override
	public boolean insert(EncuestaDTO encuesta) {
		PreparedStatement statement;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		boolean isInsertExitoso = false;
		try{
			statement = conexion.prepareStatement(insert);
			//statement.setInt(1, cliente.getIdCliente());
			statement.setInt(1, encuesta.getIdEncuesta());
			statement.setInt(2, encuesta.getIdCliente());
			statement.setString(3, encuesta.getRecipiente());
			statement.setBoolean(4, encuesta.isEncuestado());
			if(statement.executeUpdate() > 0){
				conexion.commit();
				isInsertExitoso = true;
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
		
		return isInsertExitoso;
	}
	
	
	@Override
	public List<EncuestaDTO> readAll() {
		PreparedStatement statement;
		ResultSet resultSet; //Guarda el resultado de la query
		ArrayList<EncuestaDTO> encuestas = new ArrayList<EncuestaDTO>();
		Conexion conexion = Conexion.getConexion();
	
		try {
			statement = conexion.getSQLConexion().prepareStatement(readall);
			resultSet = statement.executeQuery();
			while(resultSet.next()){
				encuestas.add(getEncuestaDTOO(resultSet));
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		return encuestas;
	}
	
	private EncuestaDTO getEncuestaDTOO(ResultSet resultSet) throws SQLException {
		int idEncuesta = resultSet.getInt("idEncuesta");
		int idCliente = resultSet.getInt("idCliente");
		String recipiente = resultSet.getString("recipiente");
		boolean encuestado = resultSet.getBoolean("encuestado");
		
		return new EncuestaDTO(idEncuesta, idCliente, recipiente, encuestado);
	}
	
	
	
	
	@Override
	public EncuestaDTO traerEncuestaPorCliente(Integer id) {
		PreparedStatement statement;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		ResultSet resultSet = null;
		EncuestaDTO encuesta = null;
		try{
			statement = conexion.prepareStatement(search1);
			statement.setInt(1, id);
			resultSet = statement.executeQuery();
			while(resultSet.next()){
				encuesta = getEncuestaDTOO(resultSet);
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
		return encuesta;
	}
	
	@Override
	public void update(int idEncuesta) {
		PreparedStatement statement;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		try{
			statement = conexion.prepareStatement(update);
			statement.setInt(1, idEncuesta);			
			
			if(statement.executeUpdate() > 0){
				conexion.commit();
			}
		}catch (SQLException e) {
			e.printStackTrace();
			try {
				conexion.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}

}
