package persistencia.dao.mysql;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.CategoriaCuartoDTO;
import dto.ClienteDTO;
import persistencia.conexion.Conexion;
import persistencia.dao.interfaz.CategoriaCuartoDAO;

public class CategoriaCuartoDAOSQL implements CategoriaCuartoDAO {
	
	private static final String insert = "INSERT INTO categoriacuarto(Nombre, Detalle) VALUES (?, ?)";
	private static final String delete = "DELETE FROM categoriacuarto WHERE idCategoriaCuarto = ?";
	private static final String readall = "SELECT * FROM categoriacuarto";
	private static final String update = "UPDATE categoriacuarto SET Nombre = ?, Detalle = ? WHERE idCategoriaCuarto = ?";

	@Override
	public boolean insert(CategoriaCuartoDTO categoriaCuarto) {
		PreparedStatement statement;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		boolean isInsertExitoso = false;
		try{
			statement = conexion.prepareStatement(insert);
			statement.setString(1, categoriaCuarto.getNombre());
			statement.setString(2, categoriaCuarto.getDetalle());
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
	public boolean delete(CategoriaCuartoDTO categoriaCuarto_a_eliminar) {
		PreparedStatement statement;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		boolean isDeleteExitoso = false;
		try{
			statement = conexion.prepareStatement(delete);
			statement.setInt(1, categoriaCuarto_a_eliminar.getIdCategoriaCuarto());
			if(statement.executeUpdate() > 0){
				conexion.commit();
				isDeleteExitoso = true;
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
		
		return isDeleteExitoso;
	}
	
	@Override
	public List<CategoriaCuartoDTO> readAll() {
		PreparedStatement statement;
		ResultSet resultSet; //Guarda el resultado de la query
		ArrayList<CategoriaCuartoDTO> categoriasCuartos = new ArrayList<CategoriaCuartoDTO>();
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(readall);
			resultSet = statement.executeQuery();
			while(resultSet.next()){
				categoriasCuartos.add(getCategoriaCuartoDTOO(resultSet));
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		return categoriasCuartos;
	}
	
	private CategoriaCuartoDTO getCategoriaCuartoDTOO(ResultSet resultSet) throws SQLException {
		int id = resultSet.getInt("idCategoriaCuarto");
		String nombre = resultSet.getString("Nombre");
		String detalle = resultSet.getString("Detalle");
		return new CategoriaCuartoDTO(id, nombre, detalle);
	}
	
	@Override
	public void update(CategoriaCuartoDTO categoriaCuarto) {
		PreparedStatement statement;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		try{
			statement = conexion.prepareStatement(update);
			statement.setString(1, categoriaCuarto.getNombre());
			statement.setString(2, categoriaCuarto.getDetalle());
			statement.setInt(3, categoriaCuarto.getIdCategoriaCuarto());
			
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
