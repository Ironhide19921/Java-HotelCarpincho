package persistencia.dao.mysql;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.SalonDTO;
import persistencia.conexion.Conexion;
import persistencia.dao.interfaz.SalonDAO;

public class SalonDAOSQL implements SalonDAO{

	private static final String insert = "INSERT INTO salon (Capacidad, Senia, Estilo, Monto, Estado) VALUES (?, ?, ?, ?, ?)";
	private static final String readall = "SELECT * FROM salon";
	private static final String update = "UPDATE salon SET Capacidad = ?, Senia = ?, Estilo = ?, Monto = ? WHERE salon.idSalon = ?";
	private static final String updateEstado = "UPDATE salon SET Estado = ? WHERE salon.idSalon = ?";
	
	@Override
	public boolean insert(SalonDTO salon) {
		PreparedStatement statement;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		boolean isInsertExitoso = false;
		try{			
			statement = conexion.prepareStatement(insert);
			statement.setInt(1, salon.getCapacidad());
			statement.setInt(2, salon.getSenia());
			statement.setString(3, salon.getEstilo());
			statement.setBigDecimal(4, salon.getMonto());
			statement.setBoolean(5, salon.getEstado());
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
	public List<SalonDTO> readAll() {
		PreparedStatement statement;
		ResultSet resultSet; //Guarda el resultado de la query
		ArrayList<SalonDTO> salones = new ArrayList<SalonDTO>();
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(readall);
			resultSet = statement.executeQuery();
			while(resultSet.next()){
				salones.add(getSalonDTO(resultSet));
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return salones;
	}

	private SalonDTO getSalonDTO(ResultSet resultSet) throws SQLException {
		int idSalon = resultSet.getInt("idSalon");
		int capacidad = resultSet.getInt("Capacidad");
		int senia = resultSet.getInt("Senia");
		String estilo = resultSet.getString("Estilo");
		BigDecimal monto = resultSet.getBigDecimal("Monto");
		Boolean estado = resultSet.getBoolean("Estado");
		return new SalonDTO(idSalon, capacidad, senia, estilo, monto, estado);
	}


	@Override
	public void update(SalonDTO salon) {
		PreparedStatement statement;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		try{
			statement = conexion.prepareStatement(update);
			statement.setInt(1, salon.getCapacidad());
			statement.setInt(2, salon.getSenia());
			statement.setString(3, salon.getEstilo());
			statement.setBigDecimal(4, salon.getMonto());
			statement.setInt(5, salon.getId());
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
	public void cambiarEstado(SalonDTO salon) {
		PreparedStatement statement;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		try{
			statement = conexion.prepareStatement(updateEstado);
			statement.setBoolean(1, salon.getEstado());
			statement.setInt(2, salon.getId());
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
