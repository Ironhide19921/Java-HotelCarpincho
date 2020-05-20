package persistencia.dao.mysql;

import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.PerfilDTO;
import dto.PermisoDTO;
import dto.PermisoPerfilDTO;
import dto.UsuarioDTO;
import persistencia.conexion.Conexion;
import persistencia.dao.interfaz.PermisoPerfilDAO;

public class PermisoPerfilDAOSQL implements PermisoPerfilDAO {
	
	private static final String insert = "INSERT INTO permisoperfil(idPerfil, idPermiso) "+ "VALUES(?, ?)";
	private static final String delete = "DELETE FROM permisoperfil WHERE idPerfil = ? AND idPermiso = ?";
	private static final String readall = "SELECT * FROM permisoPerfil";
	private static final String update = "UPDATE permisoperfil SET idPerfil = ?, idPermiso = ? WHERE idPermisoPerfil = ?";
	private static final String search = "SELECT * FROM permisoPerfil WHERE idPerfil = ?";
	
	public boolean insert(PermisoPerfilDTO permisoPerfil) {
		PreparedStatement statement;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		boolean isInsertExitoso = false;
		try{
			statement = conexion.prepareStatement(insert);
			statement.setInt(1, permisoPerfil.getIdPerfil());
			statement.setInt(2, permisoPerfil.getIdPermiso());
	
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
	public boolean delete(PermisoPerfilDTO permisoPerfil_a_eliminar) {
		PreparedStatement statement;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		boolean isdeleteExitoso = false;
		try 
		{
			statement = conexion.prepareStatement(delete);
			statement.setString(1, Integer.toString(permisoPerfil_a_eliminar.getIdPerfil()));
			statement.setString(2, Integer.toString(permisoPerfil_a_eliminar.getIdPermiso()));

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
	public List<PermisoPerfilDTO> readAll() {
		PreparedStatement statement;
		ResultSet resultSet; //Guarda el resultado de la query
		ArrayList<PermisoPerfilDTO> permisoPerfil = new ArrayList<PermisoPerfilDTO>();
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(readall);
			resultSet = statement.executeQuery();
			while(resultSet.next()){
				permisoPerfil.add(getPermisoPerfilDTO(resultSet));
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		return permisoPerfil;
	}

	@Override
	public void update(PermisoPerfilDTO permiso) {
		PreparedStatement statement;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		try{
			statement = conexion.prepareStatement(update);
			statement.setInt(1, permiso.getIdPermisoPerfil());
			statement.setInt(2, permiso.getIdPermiso());
			statement.setInt(3, permiso.getIdPerfil());
			
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
	public List<PermisoPerfilDTO> search(int buscar) {
		PreparedStatement statement;
		
		ResultSet resultSet; //Guarda el resultado de la query
		ArrayList<PermisoPerfilDTO> permisos = new ArrayList<PermisoPerfilDTO>();
		Conexion conexion = Conexion.getConexion();
		try {
			
			statement = conexion.getSQLConexion().prepareStatement(search);
			statement.setInt(1, buscar);

			resultSet = statement.executeQuery();
			while(resultSet.next()){
				permisos.add(getPermisoPerfilDTO(resultSet));
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		return permisos;
	}
	
	private PermisoPerfilDTO getPermisoPerfilDTO(ResultSet resultSet) throws SQLException {
		int idPermisoPerfil = resultSet.getInt("idPermisoPerfil");
		int idPerfil = resultSet.getInt("idPerfil");
		int idPermiso = resultSet.getInt("idPermiso");
		
		return new PermisoPerfilDTO(idPermisoPerfil, idPerfil, idPermiso);
	}

}
