package persistencia.dao.mysql;

import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.PerfilDTO;
import dto.PermisoPerfilDTO;
import persistencia.conexion.Conexion;
import persistencia.dao.interfaz.PerfilDAO;

public class PerfilDAOSQL implements PerfilDAO{
	
	private static final String insert = "INSERT INTO perfil(NombrePerfil) "+ "VALUES(?)";
	private static final String delete = "DELETE FROM perfil WHERE idPerfil = ?";
	private static final String readall = "SELECT * FROM perfil";
	private static final String update = "UPDATE perfil SET nombre = ?,  WHERE idPerfil = ?";
	private static final String search = "SELECT * FROM perfil WHERE idPerfil = ?";

	@Override
	public boolean insert(PerfilDTO perfil) {
		PreparedStatement statement;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		boolean isInsertExitoso = false;
		try{
			statement = conexion.prepareStatement(insert);
			statement.setString(1, perfil.getNombre());
	
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
	public boolean delete(PerfilDTO perfil_a_eliminar) {
		PreparedStatement statement;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		boolean isdeleteExitoso = false;
		try 
		{
			statement = conexion.prepareStatement(delete);
			statement.setString(1, Integer.toString(perfil_a_eliminar.getIdPerfil()));
			if(statement.executeUpdate() > 0)
			{
				conexion.commit();
				isdeleteExitoso = true;
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return isdeleteExitoso;
	}

	@Override
	public List<PerfilDTO> readAll() {
		PreparedStatement statement;
		ResultSet resultSet; //Guarda el resultado de la query
		ArrayList<PerfilDTO> perfil = new ArrayList<PerfilDTO>();
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(readall);
			resultSet = statement.executeQuery();
			while(resultSet.next()){
				perfil.add(getPerfilDTO(resultSet));
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return perfil;
	}

	private PerfilDTO getPerfilDTO(ResultSet resultSet) throws SQLException {
		int id = resultSet.getInt("idPerfil");
		String nombre = resultSet.getString("NombrePerfil");
		
		return new PerfilDTO(id, nombre);
	}

	@Override
	public void update(PerfilDTO perfil) {
		PreparedStatement statement;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		try{
			statement = conexion.prepareStatement(update);
			statement.setString(1, perfil.getNombre());
			statement.setInt(2, perfil.getIdPerfil());
			
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
	public List<PerfilDTO> search(int buscar) {
		PreparedStatement statement;
		
		ResultSet resultSet; //Guarda el resultado de la query
		ArrayList<PerfilDTO> perfiles = new ArrayList<PerfilDTO>();
		Conexion conexion = Conexion.getConexion();
		try {
			
			statement = conexion.getSQLConexion().prepareStatement(search);
			statement.setInt(1, buscar);

			resultSet = statement.executeQuery();
			while(resultSet.next()){
				perfiles.add(getPerfilDTO(resultSet));
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		return perfiles;
	}

}
