package persistencia.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import dto.ErrorImportarDTO;
import persistencia.conexion.Conexion;
import persistencia.dao.interfaz.ErrorImportarDAO;

public class ErrorImportarDAOSQL implements ErrorImportarDAO{
	
	private static final String insert = "INSERT INTO errorImportar(idError, fecha, usuario, detalle) "
			+ "VALUES(?, ?, ?, ?)";
	private static final String readall = "SELECT * FROM errorImportar";
	private static final String search = "SELECT * FROM errorImportar WHERE idError LIKE ? OR usuario LIKE ? OR fecha LIKE ? ";

	@Override
	public boolean insert(ErrorImportarDTO error) {
		PreparedStatement statement;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		boolean isInsertExitoso = false;
		try{
			statement = conexion.prepareStatement(insert);
			statement.setInt(1, error.getIdError());
			statement.setTimestamp(2, error.getFecha());
			statement.setInt(3, error.getUsuario());
			statement.setString(4, error.getDetalle());			
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
	public List<ErrorImportarDTO> readAll() {
		PreparedStatement statement;
		ResultSet resultSet; //Guarda el resultado de la query
		ArrayList<ErrorImportarDTO> errores = new ArrayList<ErrorImportarDTO>();
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(readall);
			resultSet = statement.executeQuery();
			while(resultSet.next()){
				errores.add(getErrorImportarDTO(resultSet));
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return errores;
	}
		
	private ErrorImportarDTO getErrorImportarDTO(ResultSet resultSet) throws SQLException {
		int idError = resultSet.getInt("idError");
		Timestamp fecha = resultSet.getTimestamp("fecha");
		int usuario = resultSet.getInt("usuario");
		String detalle = resultSet.getString("detalle");
		
		return new ErrorImportarDTO(idError, fecha, usuario, detalle);
	}
		
	@Override
	public List<ErrorImportarDTO> search(String buscar) {
		PreparedStatement statement;
		ResultSet resultSet; //Guarda el resultado de la query
		ArrayList<ErrorImportarDTO> errores = new ArrayList<ErrorImportarDTO>();
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(search);
			statement.setString(1,"%" + buscar + "%");
			statement.setString(2,"%" + buscar + "%");
			statement.setString(3,"%" + buscar + "%");
			resultSet = statement.executeQuery();
			while(resultSet.next()){
				errores.add(getErrorImportarDTO(resultSet));
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return errores;
	}

}
