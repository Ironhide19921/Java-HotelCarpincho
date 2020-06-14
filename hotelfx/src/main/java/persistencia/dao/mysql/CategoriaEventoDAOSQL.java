package persistencia.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.CategoriaEventoDTO;
import persistencia.conexion.Conexion;
import persistencia.dao.interfaz.CategoriaEventoDAO;

public class CategoriaEventoDAOSQL implements CategoriaEventoDAO {
	
	private static final String insert = "INSERT INTO categoriaevento(Nombre, Detalle) VALUES (?, ?)";
	private static final String delete = "DELETE FROM categoriaevento WHERE idCategoriaEvento = ?";
	private static final String readall = "SELECT * FROM categoriaevento";
	private static final String update = "UPDATE categoriaevento SET Nombre = ?, Detalle = ? WHERE idCategoriaEvento = ?";
	private static final String get = "SELECT * FROM categoriaevento WHERE idCategoriaEvento = ? LIMIT 1";
	
	@Override
	public boolean insert(CategoriaEventoDTO categoriaCuarto) {
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
	public boolean delete(CategoriaEventoDTO categoriaCuarto_a_eliminar) {
		PreparedStatement statement;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		boolean isDeleteExitoso = false;
		try{
			statement = conexion.prepareStatement(delete);
			statement.setInt(1, categoriaCuarto_a_eliminar.getId());
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
	public List<CategoriaEventoDTO> readAll() {
		PreparedStatement statement;
		ResultSet resultSet; //Guarda el resultado de la query
		ArrayList<CategoriaEventoDTO> categoriasEventos = new ArrayList<CategoriaEventoDTO>();
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(readall);
			resultSet = statement.executeQuery();
			while(resultSet.next()){
				categoriasEventos.add(getCategoriaEventoDTOO(resultSet));
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		return categoriasEventos;
	}
	
	private CategoriaEventoDTO getCategoriaEventoDTOO(ResultSet resultSet) throws SQLException {
		int id = resultSet.getInt("idCategoriaEvento");
		String nombre = resultSet.getString("Nombre");
		String detalle = resultSet.getString("Detalle");
		return new CategoriaEventoDTO(id, nombre, detalle);
	}
	
	@Override
	public void update(CategoriaEventoDTO categoriaEvento) {
		PreparedStatement statement;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		try{
			statement = conexion.prepareStatement(update);
			statement.setString(1, categoriaEvento.getNombre());
			statement.setString(2, categoriaEvento.getDetalle());
			statement.setInt(3, categoriaEvento.getId());
			
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
	public CategoriaEventoDTO get(int id) {
		CategoriaEventoDTO categoria = null;
		PreparedStatement statement;
		ResultSet resultSet;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(get);
			statement.setInt(1, id);
			resultSet = statement.executeQuery();
			while(resultSet.next()){
				categoria = getCategoriaEventoDTOO(resultSet);
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return categoria;
	}
}
