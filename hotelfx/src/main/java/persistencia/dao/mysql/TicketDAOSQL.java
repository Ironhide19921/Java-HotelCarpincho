package persistencia.dao.mysql;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import dto.CategoriaEventoDTO;
import dto.TicketDTO;
import persistencia.conexion.Conexion;
import persistencia.dao.interfaz.TicketDAO;

public class TicketDAOSQL implements TicketDAO{
	
	private static final String insert = "INSERT INTO ticket(idCliente, precioTotal, descripcion, path, fechaReserva) "
			+ "VALUES(?, ?, ?, ?, ? )";
	private static final String readall = "SELECT * FROM ticket";
	//private static final String update = "UPDATE cliente SET nombre = ?, apellido = ?, tipoDocumento = ?, documento = ?, email = ?, telefono = ?, estado = ?, fechaNacimiento = ? WHERE idCliente = ?";
	private static final String search = "SELECT * FROM ticket WHERE idTicket LIKE ? OR idCliente LIKE ? ";
	private static final String search1 = "SELECT * FROM ticket WHERE idTicket = ?";
	private static final String getRecienInsertado = "SELECT MAX(idTicket) FROM ticket WHERE idCliente = ?";
	private static final String modif = "UPDATE ticket SET path = ? WHERE idTicket = ?";
	
	@Override
	public boolean insert(TicketDTO ticket) {
		PreparedStatement statement;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		boolean isInsertExitoso = false;
		try{
			statement = conexion.prepareStatement(insert);
			//statement.setInt(1, cliente.getIdCliente());
			statement.setInt(1, ticket.getIdCliente());
			statement.setBigDecimal(2, ticket.getPrecioTotal());
			statement.setString(3, ticket.getDescripcion());
			statement.setString(4, ticket.getPath());
			statement.setTimestamp(5, ticket.getFecha());			
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
	public List<TicketDTO> readAll() {
		PreparedStatement statement;
		ResultSet resultSet; //Guarda el resultado de la query
		ArrayList<TicketDTO> tickets = new ArrayList<TicketDTO>();
		Conexion conexion = Conexion.getConexion();
	
		try {
			statement = conexion.getSQLConexion().prepareStatement(readall);
			resultSet = statement.executeQuery();
			while(resultSet.next()){
				tickets.add(getTicketDTO(resultSet));
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		return tickets;
	}

	private TicketDTO getTicketDTO(ResultSet resultSet) throws SQLException {
		int idTicket = resultSet.getInt("idTicket");
		int idCliente= resultSet.getInt("idCliente");
		BigDecimal precioTotal = resultSet.getBigDecimal("precioTotal");
		String descripcion = resultSet.getString("descripcion");
		String path = resultSet.getString("path");
		Timestamp fecha = resultSet.getTimestamp("FechaReserva");
		
		return new TicketDTO(idTicket, idCliente, precioTotal, descripcion, path, fecha);
	}
//
//	@Override
//	public void update(ClienteDTO cliente) {
//		PreparedStatement statement;
//		Connection conexion = Conexion.getConexion().getSQLConexion();
//		try{
//			statement = conexion.prepareStatement(update);
//			statement.setString(1, cliente.getNombre());
//			statement.setString(2, cliente.getApellido());
//			statement.setString(3, cliente.getTipoDocumento());
//			statement.setString(4, cliente.getNumeroDocumento());
//			statement.setString(5, cliente.getEmail());
//			statement.setString(6, cliente.getTelefono());
//			statement.setBoolean(7, cliente.getEstado());
//			statement.setDate(8, cliente.getFechaNacimiento());
//			statement.setInt(9, cliente.getIdCliente());
//			
//			System.out.println(statement.executeUpdate() );		
//			if(statement.executeUpdate() > 0)
//			{
//				conexion.commit();
//			}
//		} 
//		catch (SQLException e) 
//		{
//			e.printStackTrace();
//			try {
//				conexion.rollback();
//			} catch (SQLException e1) {
//				e1.printStackTrace();
//			}
//		}
//	}
//	
	
	@Override
	public List<TicketDTO> search(String buscar) {
		PreparedStatement statement;
		ResultSet resultSet; //Guarda el resultado de la query
		ArrayList<TicketDTO> tickets = new ArrayList<TicketDTO>();
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
				tickets.add(getTicketDTO(resultSet));
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return tickets;
	}



	@Override
	public int getIdTicketRecienInsertado(int idCliente) {
		// TODO Auto-generated method stub
		int ticket = 0;
		PreparedStatement statement;
		ResultSet resultSet;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(getRecienInsertado);
			statement.setInt(1, idCliente);
			resultSet = statement.executeQuery();
			while(resultSet.next()){
				ticket = resultSet.getInt(1);
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return ticket;
	}
	
	
	@Override
	public TicketDTO get(int id) {
		TicketDTO ticket = null;
		PreparedStatement statement;
		ResultSet resultSet;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(search1);
			statement.setInt(1, id);
			resultSet = statement.executeQuery();
			while(resultSet.next()){
				ticket = getTicketDTOO(resultSet);
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return ticket;
	}
	
	
	private TicketDTO getTicketDTOO(ResultSet resultSet) throws SQLException {
		int id = resultSet.getInt("idTicket");
		int idCliente = resultSet.getInt("idCliente");
		BigDecimal precioTotal = resultSet.getBigDecimal("precioTotal");
		String descripcion = resultSet.getString("descripcion");
		String path = resultSet.getString("path");
		Timestamp fecha = resultSet.getTimestamp("FechaReserva");
		return new TicketDTO(id, idCliente, precioTotal, descripcion, path, fecha);
	}


	@Override
	public void modif(int idTicket, String path) {
		PreparedStatement statement;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		try{
			statement = conexion.prepareStatement(modif);
			statement.setString(1, path);
			statement.setInt(2, idTicket);

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
