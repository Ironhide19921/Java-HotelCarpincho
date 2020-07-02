package persistencia.dao.mysql;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import dto.ReservaCuartoDTO;
import dto.ReservaCuartoDTO.EstadoReserva;
import dto.ReservaCuartoDTO.FormaPago;
import dto.ReservaCuartoDTO.TipoTarjeta;
import persistencia.conexion.Conexion;
import persistencia.dao.interfaz.ReservaCuartoDAO;

public class ReservaCuartoDAOSQL implements ReservaCuartoDAO {

	private static final String insert = "INSERT INTO reservacuarto(idCliente, idCuarto, idUsuario,"
			+ "Senia, MontoReservaCuarto,"
			+ " EmailFacturacion, NumeroTarjeta, FormaPago, TipoTarjeta, CodSeguridadTarjeta,FechaVencTarjeta,"
			+ " FechaReserva, FechaCheckIn, FechaIngreso, FechaOut, FechaEgreso, EstadoReserva, Comentarios, Estado) "
			+ "VALUES(?, ?, ?, ?, ?, ?, ?, ?,?, ?, ?, ?, ?, ?, ?, ?,?,?,?);";
	private static final String delete = "DELETE FROM reservacuarto WHERE "
			+ "idReservaCuarto = ?";
	private static final String readall = "SELECT * FROM reservacuarto where Estado = 1";
	private static final String update = "UPDATE reservacuarto SET idCliente = ?, idUsuario = ?, "
			+ "idCuarto = ?, Senia = ?, MontoReservaCuarto = ?, EmailFacturacion = ?, "
			+ "NumeroTarjeta = ? ,FormaPago = ?, TipoTarjeta = ?, CodSeguridadTarjeta = ?, "
			+ "FechaVencTarjeta = ?, "
			+ "FechaReserva = ?, FechaCheckIn = ?, FechaIngreso = ?, "
			+ " FechaOut = ?,  FechaEgreso = ?"
			+ ",EstadoReserva = ? , Comentarios = ? , Estado = ? WHERE idReservaCuarto = ?";
	private static final String search = "SELECT * FROM reservacuarto "
			+ "WHERE idUsuario LIKE ? OR idCuarto LIKE ? OR idCliente LIKE ? ";

	private static final String search1 = "SELECT * FROM reservacuarto where idCliente = ?";
	
	private static final String reservaPorId = "SELECT * FROM reservacuarto where idReservaCuarto = ?";
	
	@Override
	public boolean insert(ReservaCuartoDTO reserva) {
		PreparedStatement statement;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		boolean isInsertExitoso = false;
		try {

			statement = conexion.prepareStatement(insert);
			statement.setInt(1, reserva.getIdCliente());
			statement.setInt(2, reserva.getIdCuarto());
			statement.setInt(3, reserva.getIdUsuario());
			statement.setBigDecimal(4, reserva.getSenia());
			statement.setBigDecimal(5, reserva.getMontoReservaCuarto());
			statement.setString(6, reserva.getEmailFacturacion());
			statement.setString(7, reserva.getNumTarjeta());
			statement.setString(8, reserva.getFormaPago().name());
			statement.setString(9, reserva.getTipoTarjeta().name());
			statement.setString(10, reserva.getCodSeguridadTarjeta());
			statement.setString(11, reserva.getFechaVencTarjeta());
			statement.setTimestamp(12, reserva.getFechaReserva());
			statement.setTimestamp(13, reserva.getFechaCheckIn());
			statement.setTimestamp(14, reserva.getFechaIngreso());
			statement.setTimestamp(15, reserva.getFechaOut());
			statement.setTimestamp(16, reserva.getFechaEgreso());
			statement.setString(17, reserva.getEstadoReserva().name());
			statement.setString(18, reserva.getComentarios());
			statement.setBoolean(19, reserva.getEstado());
			
			if (statement.executeUpdate() > 0) {
				conexion.commit();
				isInsertExitoso = true;
			}
		} catch (SQLException e) {
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
	public boolean delete(ReservaCuartoDTO reserva) {
		return false;
	}

	@Override
	public List<ReservaCuartoDTO> readAll() {

		ArrayList<ReservaCuartoDTO> clientes = new ArrayList<ReservaCuartoDTO>();
		PreparedStatement statement;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		ResultSet resultSet; 
		try {
			statement = conexion.prepareStatement(readall);
			resultSet = statement.executeQuery();
			
			while (resultSet.next()) {
				clientes.add(getReservaDTO(resultSet));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return clientes;
		
	}

	private ReservaCuartoDTO getReservaDTO(ResultSet resultSet) throws SQLException {

		int idReserva = resultSet.getInt("idReservaCuarto");
		int idCliente = resultSet.getInt("idCliente");
		int idCuarto = resultSet.getInt("idCuarto");
		int idUsuario = resultSet.getInt("idUsuario");
		BigDecimal senia = resultSet.getBigDecimal("Senia");
		BigDecimal montoReservaCuarto = resultSet.getBigDecimal("MontoReservaCuarto");
		String emailFacturacion = resultSet.getString("EmailFacturacion");
		String numTarjeta = resultSet.getString("NumeroTarjeta");
		FormaPago formaDePago = FormaPago.valueOf(resultSet.getString("FormaPago"));
		TipoTarjeta tipoTarjeta =TipoTarjeta.valueOf(resultSet.getString("TipoTarjeta")) ;
		String codSeguridadTarjeta = resultSet.getString("CodSeguridadTarjeta");
		String fechaVencTarjeta = resultSet.getString("FechaVencTarjeta");
		Timestamp fechaReserva = resultSet.getTimestamp("FechaReserva");
		Timestamp fechaCheckIn = resultSet.getTimestamp("FechaCheckIn");
		Timestamp fechaOut = resultSet.getTimestamp("FechaOut");
		Timestamp fechaIngreso = resultSet.getTimestamp("FechaIngreso");
		Timestamp fechaEgreso = resultSet.getTimestamp("FechaEgreso");
		EstadoReserva estadoReserva =EstadoReserva.valueOf(resultSet.getString("EstadoReserva"));
		String comentarios = resultSet.getString("Comentarios");
		boolean estado = resultSet.getBoolean("Estado");
	
		ReservaCuartoDTO reserva = new ReservaCuartoDTO(idCliente, idCuarto, idUsuario, senia, montoReservaCuarto,
				emailFacturacion, numTarjeta, formaDePago, tipoTarjeta, codSeguridadTarjeta, fechaVencTarjeta,
				fechaReserva, fechaIngreso, fechaEgreso, estadoReserva, comentarios,estado);
			reserva.setIdReserva(idReserva);
			reserva.setFechaCheckIn(fechaCheckIn);
			reserva.setFechaOut(fechaOut);
		return reserva;
	}

	@Override
	public void update(ReservaCuartoDTO reserva) {
		PreparedStatement statement;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		try {
			statement = conexion.prepareStatement(update);
			statement.setInt(1, reserva.getIdCliente());
			statement.setInt(2, reserva.getIdUsuario());
			statement.setInt(3, reserva.getIdCuarto());
			statement.setBigDecimal(4, reserva.getSenia());
			statement.setBigDecimal(5, reserva.getMontoReservaCuarto());
			statement.setString(6, reserva.getEmailFacturacion());
			statement.setString(7, reserva.getNumTarjeta());
			statement.setString(8, reserva.getFormaPago().name());
			statement.setString(9, reserva.getTipoTarjeta().name());
			statement.setString(10, reserva.getCodSeguridadTarjeta());
			statement.setString(11, reserva.getFechaVencTarjeta());
			statement.setTimestamp(12, reserva.getFechaReserva());
			statement.setTimestamp(13, reserva.getFechaCheckIn());
			statement.setTimestamp(14, reserva.getFechaIngreso());
			statement.setTimestamp(15, reserva.getFechaOut());
			statement.setTimestamp(16, reserva.getFechaEgreso());
			statement.setString(17, reserva.getEstadoReserva().name());
			statement.setString(18, reserva.getComentarios());
			statement.setBoolean(19, reserva.getEstado());
			statement.setInt(20, reserva.getIdReserva());

			if (statement.executeUpdate() > 0) {
				conexion.commit();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conexion.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}

	@Override
	public List<ReservaCuartoDTO> search(String buscar) {
		PreparedStatement statement;

		ResultSet resultSet; // Guarda el resultado de la query
		ArrayList<ReservaCuartoDTO> reservas = new ArrayList<ReservaCuartoDTO>();
		Conexion conexion = Conexion.getConexion();
		try {

			statement = conexion.getSQLConexion().prepareStatement(search);

			statement.setString(1, "%" + buscar + "%");
			statement.setString(2, "%" + buscar + "%");
			statement.setString(3, "%" + buscar + "%");

			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				reservas.add(getReservaDTO(resultSet));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return reservas;
	}
	
	@Override
	public List<ReservaCuartoDTO> buscarReservaCuartoCliente(int idCliente) {

		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		ArrayList<ReservaCuartoDTO> reservas = new ArrayList<ReservaCuartoDTO>();
		Conexion conexion = Conexion.getConexion();
		try {	
		statement = conexion.getSQLConexion().prepareStatement(search1);
			statement.setInt(1,idCliente);
			resultSet = statement.executeQuery();
			
			while (resultSet.next()) {
				reservas.add(getReservaDTO(resultSet));
			}
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		return reservas;
		
	}
	
	@Override
	public ReservaCuartoDTO obtenerReservaCuartoPorId(int idReserva) {

		PreparedStatement statement;

		ResultSet resultSet; // Guarda el resultado de la query
		ReservaCuartoDTO reservas = null;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(reservaPorId);
			statement.setInt(1,idReserva);
			resultSet = statement.executeQuery();
			
			while (resultSet.next()) {
				reservas = getReservaDTO(resultSet);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return reservas;
		
	}
	
	
}
