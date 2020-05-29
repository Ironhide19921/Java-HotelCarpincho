package persistencia.dao.mysql;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.ReservaCuartoDTO;
import persistencia.conexion.Conexion;
import persistencia.dao.interfaz.ReservaCuartoDAO;

public class ReservaCuartoDAOSQL implements ReservaCuartoDAO {

	private static final String insert = "INSERT INTO reservaCuarto(idCliente, idCuarto, idUsuario,"
			+ "Senia, MontoReservaCuarto,"
			+ " EmailFacturacion, NumeroTarjeta, FormaPago, TipoTarjeta, CodSeguridadTarjeta,FechaVencTarjeta,"
			+ " FechaReserva, FechaCheckIn,FechaIngreso,FechaOut,FechaEgreso," + ",EstadoReserva,Comentarios) "
			+ "VALUES(?, ?, ?, ?, ?, ?, ?, ?,?, ?, ?, ?, ?, ?, ?, ?,?,?)";
	private static final String delete = "DELETE FROM reservaCuarto WHERE "
			+ "idReservaCuarto=?, idCliente = ?, idUsuario = ?, idCuarto = ?";
	private static final String readall = "SELECT * FROM reservaCuarto";
	private static final String update = "UPDATE reservaCuarto SET idCliente = ?, idUsuario = ?, "
			+ "idCuarto = ?, Senia = ?, MontoReservaCuarto = ?, EmailFacturacion = ?, "
			+ "numTarjeta = ? ,formaDePago = ?, tipoTarjeta = ? , codSeguridadTarjeta = ?, " + "fechaVencTarjeta = ?"
			+ "FechaReserva = ?, FechaCheckIn = ?, FechaIngreso = ?, "
			+ " FechaOut = ?,  FechaEgreso = ?,  FormaPago = ?" + " FechaVencTarjeta = ? WHERE idReservaCuarto = ?";
	private static final String search = "SELECT * FROM reservaCuarto "
			+ "WHERE idUsuario LIKE ? OR idCuarto LIKE ? OR idCliente LIKE ? OR nombre LIKE ? " + "OR apellido LIKE ?";

	@Override
	public boolean insert(ReservaCuartoDTO reserva) {
		PreparedStatement statement;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		boolean isInsertExitoso = false;
		try {

			statement = conexion.prepareStatement(insert);
			statement.setInt(1, reserva.getIdCliente());
			statement.setInt(3, reserva.getIdCuarto());
			statement.setInt(2, reserva.getIdUsuario());
			statement.setBigDecimal(4, reserva.getSenia());
			statement.setBigDecimal(5, reserva.getMontoReservaCuarto());
			statement.setString(6, reserva.getEmailFacturacion());
			statement.setString(7, reserva.getNumTarjeta());
			statement.setString(8, reserva.getFormasDePago());
			statement.setString(9, reserva.getTiposTarjeta());
			statement.setString(10, reserva.getCodSeguridadTarjeta());
			statement.setDate(11, reserva.getFechaVencTarjeta());
			statement.setDate(12, reserva.getFechaReserva());
			statement.setDate(13, reserva.getFechaCheckIn());
			statement.setDate(14, reserva.getFechaIngreso());
			statement.setDate(15, reserva.getFechaOut());
			statement.setDate(16, reserva.getFechaEgreso());
			statement.setBoolean(17, reserva.getEstado());
			statement.setString(18, reserva.getComentarios());

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
		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		ArrayList<ReservaCuartoDTO> clientes = new ArrayList<ReservaCuartoDTO>();
		Conexion conexion = Conexion.getConexion();

		try {
			statement = conexion.getSQLConexion().prepareStatement(readall);
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
		String formaDePago = resultSet.getString("FormaPago");
		String tipoTarjeta = resultSet.getString("TipoTarjeta");
		String codSeguridadTarjeta = resultSet.getString("CodSeguridadTarjeta");
		Date fechaVencTarjeta = resultSet.getDate("FechaVencTarjeta");
		Date fechaReserva = resultSet.getDate("FechaReserva");
		Date fechaCheckIn = resultSet.getDate("FechaCheckIn");
		Date fechaOut = resultSet.getDate("FechaOut");
		Date fechaIngreso = resultSet.getDate("FechaIngreso");
		Date fechaEgreso = resultSet.getDate("FechaEgreso");
		boolean estado = resultSet.getBoolean("EstadoReserva");
		String comentarios = resultSet.getString("Comentarios");

		ReservaCuartoDTO reserva = new ReservaCuartoDTO(idCliente, idCuarto, idUsuario, senia, montoReservaCuarto,
				emailFacturacion, numTarjeta, formaDePago, tipoTarjeta, codSeguridadTarjeta, fechaVencTarjeta,
				fechaReserva, fechaCheckIn, fechaOut, fechaIngreso, fechaEgreso, estado, comentarios);
		reserva.setIdReserva(idReserva);
		return reserva;
	}

	@Override
	public void update(ReservaCuartoDTO reserva) {
		PreparedStatement statement;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		try {
			statement = conexion.prepareStatement(update);
			statement.setInt(1, reserva.getIdCliente());
			statement.setInt(3, reserva.getIdCuarto());
			statement.setInt(2, reserva.getIdUsuario());
			statement.setBigDecimal(4, reserva.getSenia());
			statement.setBigDecimal(5, reserva.getMontoReservaCuarto());
			statement.setString(6, reserva.getEmailFacturacion());
			statement.setString(7, reserva.getNumTarjeta());
			statement.setString(8, reserva.getFormasDePago());
			statement.setString(9, reserva.getTiposTarjeta());
			statement.setString(10, reserva.getCodSeguridadTarjeta());
			statement.setDate(11, reserva.getFechaVencTarjeta());
			statement.setDate(12, reserva.getFechaReserva());
			statement.setDate(13, reserva.getFechaCheckIn());
			statement.setDate(14, reserva.getFechaIngreso());
			statement.setDate(15, reserva.getFechaOut());
			statement.setDate(16, reserva.getFechaEgreso());
			statement.setBoolean(17, reserva.getEstado());
			statement.setString(18, reserva.getComentarios());
			statement.setInt(19, reserva.getIdReserva());

			System.out.println(statement.executeUpdate());
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
			statement.setString(4, "%" + buscar + "%");
			statement.setString(5, "%" + buscar + "%");

			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				reservas.add(getReservaDTO(resultSet));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return reservas;
	}
}
