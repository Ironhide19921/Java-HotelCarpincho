package persistencia.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.ClienteDTO;
import dto.CuartoDTO;
import persistencia.conexion.Conexion;
import persistencia.dao.interfaz.CuartoDAO;

public class CuartoDAOSQL implements CuartoDAO{
	
	private static final String insert = "INSERT INTO cuarto(idCategoriaCuarto, capacidad, monto, montoSenia, piso, habitacion, estado) "
			+ "VALUES(?, ?, ?, ?, ?, ?, ?)";
	private static final String readall = "SELECT * FROM cuarto";
	private static final String update = "UPDATE cuarto SET idCategoriaCuarto = ?, capacidad = ?, monto = ?, montoSenia = ?, piso = ?, habitacion = ?, estado = ? WHERE idCuarto = ?";
	private static final String updateEstado = "UPDATE cuarto SET estado = ? WHERE idCuarto = ?";
	private static final String search = "SELECT * FROM cuarto WHERE capacidad LIKE ? OR monto LIKE ? OR montoSenia LIKE ? OR piso LIKE ? OR habitacion LIKE ?";
	private static final String search1 = "SELECT * FROM cuarto WHERE idCuarto = ?";
	
	@Override
	public boolean insert(CuartoDTO cuarto) {
		PreparedStatement statement;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		boolean isInsertExitoso = false;
		try{
			statement = conexion.prepareStatement(insert);
			statement.setInt(1, cuarto.getIdCateCuarto());
			statement.setString(2, cuarto.getCapacidad());
			statement.setDouble(3, cuarto.getMonto());
			statement.setDouble(4, cuarto.getMontoSenia());
			statement.setString(5, cuarto.getPiso());
			statement.setString(6, cuarto.getHabitacion());
			statement.setBoolean(7, cuarto.getEstado());
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
	public boolean delete(CuartoDTO cuarto_a_eliminar) {
		return false;
	}

	@Override
	public List<CuartoDTO> readAll() {
		PreparedStatement statement;
		ResultSet resultSet; //Guarda el resultado de la query
		ArrayList<CuartoDTO> clientes = new ArrayList<CuartoDTO>();
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(readall);
			resultSet = statement.executeQuery();
			while(resultSet.next()){
				clientes.add(getCuartoDTO(resultSet));
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return clientes;
	}
	
	@Override
	public CuartoDTO traerCuarto(Integer id) {
		PreparedStatement statement;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		ResultSet resultSet ;
		CuartoDTO cuarto = null;
		try{
			statement = conexion.prepareStatement(search1);
			statement.setInt(1, id);
			resultSet = statement.executeQuery();
			while(resultSet.next()) {
				cuarto = getCuartoDTO(resultSet);
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
		return cuarto;		
	}
	
	private CuartoDTO getCuartoDTO(ResultSet resultSet) throws SQLException {
		int id = resultSet.getInt("idCuarto");
		int idCateCuarto = resultSet.getInt("idCategoriaCuarto");
		String capacidad = resultSet.getString("Capacidad");
		Double monto = resultSet.getDouble("Monto");
		int montoSenia = resultSet.getInt("MontoSenia");
		String piso = resultSet.getString("Piso");
		String habitacion = resultSet.getString("Habitacion");
		Boolean estado = resultSet.getBoolean("Estado");
		return new CuartoDTO(id, idCateCuarto, capacidad, monto, montoSenia, piso, habitacion, estado);
	}

	@Override
	public void update(CuartoDTO cuarto) {
		PreparedStatement statement;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		try{
			statement = conexion.prepareStatement(update);
			statement.setInt(1, cuarto.getIdCateCuarto());
			statement.setString(2, cuarto.getCapacidad());
			statement.setDouble(3, cuarto.getMonto());
			statement.setDouble(4, cuarto.getMontoSenia());
			statement.setString(5, cuarto.getPiso());
			statement.setString(6, cuarto.getHabitacion());
			statement.setBoolean(7, cuarto.getEstado());
			statement.setInt(8, cuarto.getId());
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
		
	@Override
	public List<CuartoDTO> search(String buscar) {
		PreparedStatement statement;
		ResultSet resultSet; //Guarda el resultado de la query
		ArrayList<CuartoDTO> cuartos = new ArrayList<CuartoDTO>();
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(search);
			statement.setString(1,"%" + buscar + "%");
			statement.setString(2,"%" + buscar + "%");
			statement.setString(3,"%" + buscar + "%");
			statement.setString(4,"%" + buscar + "%");
			statement.setString(5,"%" + buscar + "%");
			resultSet = statement.executeQuery();
			while(resultSet.next()){
				cuartos.add(getCuartoDTO(resultSet));
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return cuartos;
	}

	@Override
	public void cambiarEstado(CuartoDTO cuarto) {
		PreparedStatement statement;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		try{
			statement = conexion.prepareStatement(updateEstado);
			statement.setBoolean(1, cuarto.getEstado());
			statement.setInt(2, cuarto.getId());
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
