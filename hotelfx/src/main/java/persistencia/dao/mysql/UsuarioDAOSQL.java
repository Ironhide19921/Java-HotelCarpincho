package persistencia.dao.mysql;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.UsuarioDTO;
import persistencia.conexion.Conexion;
import persistencia.dao.interfaz.UsuarioDAO;

public class UsuarioDAOSQL implements UsuarioDAO{
	
	private static final String insert = "INSERT INTO usuario(nombre, apellido, tipoDocumento, documento, email, password, estado, idPerfil) "
			+ "VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String delete = "DELETE FROM usuario WHERE idUsuario = ?";
	private static final String readall = "SELECT * FROM usuario";
	private static final String update = "UPDATE usuario SET nombre = ?, apellido = ?, tipoDocumento = ?, documento = ?, email = ?, password = ?, estado = ?, idPerfil = ? WHERE idUsuario = ?";
	private static final String search = "SELECT * FROM usuario WHERE email LIKE ? OR documento LIKE ? OR idUsuario LIKE ? OR nombre LIKE ? OR apellido LIKE ?";

	@Override
	public boolean insert(UsuarioDTO usuario) {
		PreparedStatement statement;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		boolean isInsertExitoso = false;
		try{
			statement = conexion.prepareStatement(insert);
			statement.setString(1, usuario.getNombre());
			statement.setString(2, usuario.getApellido());
			statement.setString(3, usuario.getTipoDocumento());
			statement.setString(4, usuario.getNumeroDocumento());
			statement.setString(5, usuario.getEmail());
			statement.setString(6, usuario.getPassword());
			statement.setBoolean(7, usuario.getEstado());
			statement.setInt(8, usuario.getIdPerfil());
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
	public boolean delete(UsuarioDTO usuario_a_eliminar) {
		return false;
	}

	@Override
	public List<UsuarioDTO> readAll() {
		PreparedStatement statement;
		ResultSet resultSet; //Guarda el resultado de la query
		ArrayList<UsuarioDTO> usuarios = new ArrayList<UsuarioDTO>();
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(readall);
			resultSet = statement.executeQuery();
			while(resultSet.next()){
				usuarios.add(getUsuarioDTO(resultSet));
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		return usuarios;
	}

	private UsuarioDTO getUsuarioDTO(ResultSet resultSet) throws SQLException {
		int id = resultSet.getInt("idUsuario");
		String nombre = resultSet.getString("Nombre");
		String apellido = resultSet.getString("Apellido");
		String tipoDoc = resultSet.getString("TipoDocumento");
		String documento = resultSet.getString("Documento");
		String email = resultSet.getString("Email");
		String password = resultSet.getString("Password");
		int idPerfil = resultSet.getInt("idPerfil");
		Boolean estado = resultSet.getBoolean("Estado");
		
		return new UsuarioDTO(id, idPerfil, nombre, apellido, tipoDoc, documento, email, password, estado);
	}

	@Override
	public void update(UsuarioDTO usuario) {
		PreparedStatement statement;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		try{
			statement = conexion.prepareStatement(update);
			statement.setString(1, usuario.getNombre());
			statement.setString(2, usuario.getApellido());
			statement.setString(3, usuario.getTipoDocumento());
			statement.setString(4, usuario.getNumeroDocumento());
			statement.setString(5, usuario.getEmail());
			statement.setString(6, usuario.getPassword());
			statement.setBoolean(7, usuario.getEstado());
			statement.setInt(8, usuario.getIdPerfil());
			statement.setInt(9, usuario.getIdUsuario());
			
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
	public List<UsuarioDTO> search(String buscar) {
		PreparedStatement statement;
		
		ResultSet resultSet; //Guarda el resultado de la query
		ArrayList<UsuarioDTO> clientes = new ArrayList<UsuarioDTO>();
		Conexion conexion = Conexion.getConexion();
		try {
			
			statement = conexion.getSQLConexion().prepareStatement(search);
//			statement.setString(1, buscar);
			statement.setString(1,"%" + buscar + "%");
			statement.setString(2,"%" + buscar + "%");
			statement.setString(3,"%" + buscar + "%");
			statement.setString(4,"%" + buscar + "%");
			statement.setString(5,"%" + buscar + "%");

			resultSet = statement.executeQuery();
			while(resultSet.next()){
				clientes.add(getUsuarioDTO(resultSet));
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		return clientes;
	}
}
