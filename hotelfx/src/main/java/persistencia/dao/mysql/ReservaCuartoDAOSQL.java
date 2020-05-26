package persistencia.dao.mysql;

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

public class ReservaCuartoDAOSQL implements ReservaCuartoDAO{

	private static final String insert = "INSERT INTO reservaCuarto(idCliente, idUsuario,"
			+ " idCuarto, Senia, MontoReservaCuarto,"
			+ " EmailFacturacion, FechaReserva, FechaCheckIn,FechaIngreso,FechaOut,FechaEgreso"
			+ ",FormaPago,TipoTarjeta,NumeroTarjeta,FechaVencTarjeta,CodSeguridadTarjeta"
			+ ",EstadoReserva,Comentarios) "
			+ "VALUES(?, ?, ?, ?, ?, ?, ?, ?,?, ?, ?, ?, ?, ?, ?, ?,?,?)";
	private static final String delete = "DELETE FROM reservaCuarto WHERE "
			+ "idReservaCuarto=?, idCliente = ?, idUsuario = ?, idCuarto = ?";
	private static final String readall = "SELECT * FROM reservaCuarto";
	private static final String update = "UPDATE reservaCuarto SET idCliente = ?, idUsuario = ?, "
			+ "idCuarto = ?, Senia = ?, MontoReservaCuarto = ?, EmailFacturacion = ?, "
			+ "FechaReserva = ?, FechaCheckIn = ?, FechaIngreso = ? , "
			+ " FechaOut = ?,  FechaEgreso = ?,  FormaPago = ?"
			+ " FechaOut = ? WHERE idReservaCuarto=?, idCliente = ?, "
			+ "idUsuario = ?, idCuarto = ?";
	private static final String search = "SELECT * FROM reservaCuarto "
			+ "WHERE idUsuario LIKE ? OR idCuarto LIKE ? OR idCliente LIKE ? OR nombre LIKE ? "
			+ "OR apellido LIKE ?";

	/*
	 `idReservaCuarto` int(11) NOT NULL AUTO_INCREMENT,
	  `idCliente` int(11) NOT NULL,
	  `idUsuario` int(11) NOT NULL,
	  `idCuarto` int(11) NOT NULL,
	  `Senia` decimal(10,3) NOT NULL,
	  `MontoReservaCuarto` decimal(10,3) NOT NULL,
	  `EmailFacturacion` varchar(50) NOT NULL,
	  `FechaReserva` dateTime NOT NULL,
	  `FechaCheckIn` dateTime NOT NULL,
	  `FechaIngreso` dateTime NOT NULL,
	  `FechaOut` dateTime NOT NULL,
	  `FechaEgreso` dateTime NOT NULL,
	  `FormaPago` varchar(20) NOT NULL,
	  `TipoTarjeta` varchar(25) NOT NULL,
	  `NumeroTarjeta` varchar(25) NOT NULL,
	  `FechaVencTarjeta` varchar(15) NOT NULL,
	  `CodSeguridadTarjeta` varchar(10) NOT NULL,
	  `EstadoReserva` varchar(20) NOT NULL,
	  `Comentarios` varchar(200) NOT NULL,
	  PRIMARY KEY (`idReservaCuarto`),
	  CONSTRAINT FOREIGN KEY fk_clienteId (idCliente) REFERENCES cliente (idCliente),
	  CONSTRAINT FOREIGN KEY fk_id_Usuario (idUsuario) REFERENCES usuario (idUsuario),
	  CONSTRAINT FOREIGN KEY fk_idCuarto (idCuarto) REFERENCES cuarto (idCuarto)*/
	@Override
	public boolean insert(ReservaCuartoDTO reserva) {
		PreparedStatement statement;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		boolean isInsertExitoso = false;
		try{
			statement = conexion.prepareStatement(insert);
			//statement.setInt(1, cliente.getIdCliente());
			statement.setString(1, reserva.getNombre());
			statement.setString(2, reserva.getApellido());
			statement.setString(3, reserva.getTipoDocumento());
			statement.setString(4, reserva.getNumeroDocumento());
			statement.setString(5, reserva.getEmail());
			statement.setString(6, reserva.getTelefono());
			statement.setBoolean(7, reserva.getEstado());
			statement.setDate(8, reserva.getFechaNacimiento());
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
	public boolean delete(ReservaCuartoDTO reserva) {
		return false;
	}

	@Override
	public List<ReservaCuartoDTO> readAll() {
		PreparedStatement statement;
		ResultSet resultSet; //Guarda el resultado de la query
		ArrayList<ReservaCuartoDTO> clientes = new ArrayList<ReservaCuartoDTO>();
		Conexion conexion = Conexion.getConexion();
	
		try {
			statement = conexion.getSQLConexion().prepareStatement(readall);
			resultSet = statement.executeQuery();
			while(resultSet.next()){
				clientes.add(getClienteDTOO(resultSet));
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		return clientes;
	}

	private ReservaCuartoDTO getClienteDTOO(ResultSet resultSet) throws SQLException {
		int id = resultSet.getInt("idCliente");
		String nombre = resultSet.getString("Nombre");
		String apellido = resultSet.getString("Apellido");
		String tipoDoc = resultSet.getString("TipoDocumento");
		String documento = resultSet.getString("Documento");
		String email = resultSet.getString("Email");
		String tel = resultSet.getString("Telefono");
		Boolean estado = resultSet.getBoolean("Estado");
		Date fechaNacimiento = resultSet.getDate("FechaNacimiento");
		
		ReservaCuartoDTO reserva = new ReservaCuartoDTO(id, nombre, apellido, tipoDoc, documento, email, tel, estado, fechaNacimiento);
		return reserva;
	}

	@Override
	public void update(ReservaCuartoDTO reserva) {
		PreparedStatement statement;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		try{
			statement = conexion.prepareStatement(update);
			statement.setString(1, reserva.getNombre());
			statement.setString(2, reserva.getApellido());
			statement.setString(3, reserva.getTipoDocumento());
			statement.setString(4, reserva.getNumeroDocumento());
			statement.setString(5, reserva.getEmail());
			statement.setString(6, reserva.getTelefono());
			statement.setBoolean(7, reserva.getEstado());
			statement.setDate(8, reserva.getFechaNacimiento());
			statement.setInt(9, reserva.getIdCliente());
			
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
	public List<ReservaCuartoDTO> search(String buscar) {
		PreparedStatement statement;
		
		ResultSet resultSet; //Guarda el resultado de la query
		ArrayList<ReservaCuartoDTO> reservas = new ArrayList<ReservaCuartoDTO>();
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
				reservas.add(getClienteDTOO(resultSet));
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		return reservas;
	}
}
