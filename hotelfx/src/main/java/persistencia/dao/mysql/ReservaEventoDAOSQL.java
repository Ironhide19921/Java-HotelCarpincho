package persistencia.dao.mysql;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import dto.ReservaEventoDTO;
import dto.ReservaEventoDTO.EstadoReserva;
import dto.ReservaEventoDTO.FormaPago;
import dto.ReservaEventoDTO.TipoTarjeta;
import persistencia.conexion.Conexion;
import persistencia.dao.interfaz.ReservaEventoDAO;

public class ReservaEventoDAOSQL implements ReservaEventoDAO {
	
	private static final String insert = "INSERT INTO reservaevento(idCliente, idUsuario, idSalon, idCategoriaEvento, TipoTarjeta, CodSeguridadTarjeta, FechaVencTarjeta, NumeroTarjeta, FormaPago, MontoTotal, MontoReservaEvento, Senia, FechaGeneracionReserva, FechaInicioReserva, FechaFinReserva, FechaIngreso, FechaEgreso, EstadoReserva, Observaciones) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"; 
	private static final String readAll = "SELECT * FROM reservaevento"; 
	private static final String update = "UPDATE reservaevento SET idSalon = ?, idCategoriaEvento = ?, TipoTarjeta = ?, CodSeguridadTarjeta = ?, FechaVencTarjeta = ?, NumeroTarjeta = ?, FormaPago = ?, MontoTotal = ?, MontoReservaEvento = ?, Senia = ?, FechaGeneracionReserva = ?, FechaInicioReserva = ?, FechaFinReserva = ?, FechaIngreso = ?, FechaEgreso = ?, EstadoReserva = ?, Observaciones = ? WHERE idReservaEvento = ?"; 
	private static final String delete = "DELETE FROM reservaevento WHERE idReservaEvento = ?";
	private static final String readAllCliente = "SELECT * FROM reservaevento WHERE idCliente = ?";
	
	//fechainicio -> primer y segundo parametro //fechafin tercer y cuarto-> parametro
	//FechaInicioReserva
	//FechaFinReserva
	//idReservaEvento
	private static final String idReservasEntreFechas = "SELECT * FROM reservaevento WHERE ((FechaInicioReserva BETWEEN ? AND ?) AND (FechaInicioReserva <> ?)) OR ((FechaFinReserva BETWEEN ? AND ?) AND (FechaFinReserva <> ?)) OR ((? BETWEEN FechaInicioReserva AND FechaFinReserva) AND (FechaFinReserva <> ?)) OR ((? BETWEEN FechaInicioReserva AND FechaFinReserva) AND (? <> FechaInicioReserva)) AND EstadoReserva <> 'CANCELADO'";
	
	//necesito la lista de reservas entre dos fechas
	//devuelve un array de objetos
	
	
	
	@Override
	public boolean insert(ReservaEventoDTO reservaEvento) {
		PreparedStatement statement;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		boolean isInsertExitoso = false;
		try{
			statement = conexion.prepareStatement(insert);
			statement.setInt(1, reservaEvento.getIdCliente());
			statement.setInt(2, reservaEvento.getIdUsuario());
			statement.setInt(3, reservaEvento.getIdSalon());
			statement.setInt(4, reservaEvento.getIdCategoriaEvento());
			statement.setString(5, reservaEvento.getTipoTarjeta().name());
			statement.setString(6, reservaEvento.getCodSeguridadTarjeta());
			statement.setString(7, reservaEvento.getFechaVencTarjeta());
			statement.setString(8, reservaEvento.getNumeroTarjeta());
			statement.setString(9, reservaEvento.getFormaPago().name());
			statement.setBigDecimal(10, reservaEvento.getMontoTotal());
			statement.setBigDecimal(11, reservaEvento.getMontoReservaEvento());
			statement.setBigDecimal(12, reservaEvento.getSenia());
			statement.setTimestamp(13, reservaEvento.getFechaGeneracionReserva());
			statement.setTimestamp(14, reservaEvento.getFechaInicioReserva());
			statement.setTimestamp(15, reservaEvento.getFechaFinReserva());
			statement.setTimestamp(16, reservaEvento.getFechaIngreso());
			statement.setTimestamp(17, reservaEvento.getFechaEgreso());
			statement.setString(18, reservaEvento.getEstado().name());
			statement.setString(19, reservaEvento.getObservaciones());
			
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
	public List<ReservaEventoDTO> readAll() {
		PreparedStatement statement;
		ResultSet resultSet; //Guarda el resultado de la query
		ArrayList<ReservaEventoDTO> reservasEventos = new ArrayList<ReservaEventoDTO>();
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(readAll);
			resultSet = statement.executeQuery();
			while(resultSet.next()){
				reservasEventos.add(getReservaEventoDTOO(resultSet));
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		return reservasEventos;
	}

	private ReservaEventoDTO getReservaEventoDTOO(ResultSet resultSet) throws SQLException {
		int idReservaEvento = resultSet.getInt("idReservaEvento");
		int idCliente = resultSet.getInt("idCliente");
		int idUsuario = resultSet.getInt("idUsuario");
		int idSalon = resultSet.getInt("idSalon");
		int idCategoriaEvento = resultSet.getInt("idCategoriaEvento");
		BigDecimal Senia = resultSet.getBigDecimal("Senia");
		BigDecimal MontoReservaEvento = resultSet.getBigDecimal("MontoReservaEvento");
		BigDecimal MontoTotal = resultSet.getBigDecimal("MontoTotal");
		Timestamp FechaGeneracionReserva = resultSet.getTimestamp("FechaGeneracionReserva");
		Timestamp FechaInicioReserva = resultSet.getTimestamp("FechaInicioReserva");
		Timestamp FechaFinReserva = resultSet.getTimestamp("FechaFinReserva");
		Timestamp FechaIngreso = resultSet.getTimestamp("FechaIngreso");
		Timestamp FechaEgreso = resultSet.getTimestamp("FechaEgreso");
		FormaPago formaPago = FormaPago.valueOf(resultSet.getString("FormaPago"));
		TipoTarjeta tipoTarjeta = TipoTarjeta.valueOf(resultSet.getString("TipoTarjeta"));
		String NumeroTarjeta = resultSet.getString("NumeroTarjeta");
		String FechaVencTarjeta = resultSet.getString("FechaVencTarjeta");
		String CodSeguridadTarjeta = resultSet.getString("CodSeguridadTarjeta");
		EstadoReserva estado = EstadoReserva.valueOf(resultSet.getString("EstadoReserva"));
		String Observaciones = resultSet.getString("Observaciones");
		
		return new ReservaEventoDTO(idReservaEvento, idCliente, idUsuario, idSalon, idCategoriaEvento, Senia, MontoReservaEvento, MontoTotal, FechaGeneracionReserva, FechaInicioReserva, FechaFinReserva, FechaIngreso, FechaEgreso, formaPago, tipoTarjeta, NumeroTarjeta, FechaVencTarjeta, CodSeguridadTarjeta, estado, Observaciones);
	}
	
	
	@Override
	public void update(ReservaEventoDTO reservaEvento) {
		PreparedStatement statement;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		try{
			statement = conexion.prepareStatement(update);
			statement.setInt(1, reservaEvento.getIdSalon());
			statement.setInt(2, reservaEvento.getIdCategoriaEvento());
			statement.setString(3, reservaEvento.getTipoTarjeta().name());
			statement.setString(4, reservaEvento.getCodSeguridadTarjeta());
			statement.setString(5, reservaEvento.getFechaVencTarjeta());
			statement.setString(6, reservaEvento.getNumeroTarjeta());
			statement.setString(7, reservaEvento.getFormaPago().name());
			statement.setBigDecimal(8, reservaEvento.getMontoTotal());
			statement.setBigDecimal(9, reservaEvento.getMontoReservaEvento());
			statement.setBigDecimal(10, reservaEvento.getSenia());
			statement.setTimestamp(11, reservaEvento.getFechaGeneracionReserva());
			statement.setTimestamp(12, reservaEvento.getFechaInicioReserva());
			statement.setTimestamp(13, reservaEvento.getFechaFinReserva());
			statement.setTimestamp(14, reservaEvento.getFechaIngreso());
			statement.setTimestamp(15, reservaEvento.getFechaEgreso());
			statement.setString(16, reservaEvento.getEstado().name());
			statement.setString(17, reservaEvento.getObservaciones());
			statement.setInt(18, reservaEvento.getIdReservaEvento());
			
			System.out.println(statement.executeUpdate());		
			System.out.println("Hasta acá no se ejecutó");
			if(statement.executeUpdate() > 0)
			{
				System.out.println("SE EJECUTó");
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
	public boolean delete(ReservaEventoDTO reservaEvento) {
		PreparedStatement statement;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		boolean isDeleteExitoso = false;
		try{
			statement = conexion.prepareStatement(delete);
			statement.setInt(1, reservaEvento.getIdReservaEvento());
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
	public List<ReservaEventoDTO> readAllCliente(int idCliente) {
		PreparedStatement statement;
		ResultSet resultSet; //Guarda el resultado de la query
		ArrayList<ReservaEventoDTO> reservasEventos = new ArrayList<ReservaEventoDTO>();
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(readAllCliente);
			statement.setInt(1, idCliente);
			resultSet = statement.executeQuery();
			while(resultSet.next()){
				reservasEventos.add(getReservaEventoDTOO(resultSet));
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return reservasEventos;
	}
	
	@Override
	public List<ReservaEventoDTO> getReservasEntre(Timestamp fechaInicio, Timestamp fechaFin) {
		PreparedStatement statement;
		ResultSet resultSet; //Guarda el resultado de la query
		ArrayList<ReservaEventoDTO> reservasEventos = new ArrayList<ReservaEventoDTO>();
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(idReservasEntreFechas);
			statement.setTimestamp(1, fechaInicio);
			statement.setTimestamp(2, fechaFin);
			statement.setTimestamp(3, fechaFin);
			statement.setTimestamp(4, fechaInicio);
			statement.setTimestamp(5, fechaFin);
			statement.setTimestamp(6, fechaInicio);	
			statement.setTimestamp(7, fechaInicio);
			statement.setTimestamp(8, fechaInicio);
			statement.setTimestamp(9, fechaFin);
			statement.setTimestamp(10, fechaFin);
			
			System.out.println(statement.toString());
			
			resultSet = statement.executeQuery();
			while(resultSet.next()){
				reservasEventos.add(getReservaEventoDTOO(resultSet));
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		return reservasEventos;
	}

}
