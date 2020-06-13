package persistencia.dao.mysql;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.CategoriaEventoDTO;
import dto.ClienteDTO;
import persistencia.conexion.Conexion;
import persistencia.dao.interfaz.ClienteDAO;

public class ClienteDAOSQL implements ClienteDAO{
	
	private static final String insert = "INSERT INTO cliente(nombre, apellido, tipoDocumento, documento, email, telefono, estado, fechaNacimiento) "
			+ "VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String delete = "DELETE FROM cliente WHERE idCliente = ?";
	private static final String readall = "SELECT * FROM cliente";
	private static final String update = "UPDATE cliente SET nombre = ?, apellido = ?, tipoDocumento = ?, documento = ?, email = ?, telefono = ?, estado = ?, fechaNacimiento = ? WHERE idCliente = ?";
	private static final String search = "SELECT * FROM cliente WHERE email LIKE ? OR documento LIKE ? OR idcliente LIKE ? OR nombre LIKE ? OR apellido LIKE ?";
	private static final String get = "SELECT * FROM cliente WHERE idCliente = ?";
	
	private static final String search1 = "SELECT * FROM cliente WHERE idCliente = ?";
	
	@Override
	public boolean insert(ClienteDTO cliente) {
		PreparedStatement statement;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		boolean isInsertExitoso = false;
		try{
			statement = conexion.prepareStatement(insert);
			//statement.setInt(1, cliente.getIdCliente());
			statement.setString(1, cliente.getNombre());
			statement.setString(2, cliente.getApellido());
			statement.setString(3, cliente.getTipoDocumento());
			statement.setString(4, cliente.getNumeroDocumento());
			statement.setString(5, cliente.getEmail());
			statement.setString(6, cliente.getTelefono());
			statement.setBoolean(7, cliente.getEstado());
			statement.setDate(8, cliente.getFechaNacimiento());
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
	public boolean delete(ClienteDTO persona_a_eliminar) {
		return false;
	}
	@Override
	public ClienteDTO traerCliente(Integer id) {
		PreparedStatement statement;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		ResultSet resultSet = null;
		ClienteDTO cliente = null;
		try{
			statement = conexion.prepareStatement(search1);
			statement.setInt(1, id);
			resultSet = statement.executeQuery();
			if(statement.executeUpdate() > 0){
				conexion.commit();
				cliente = getClienteDTOO(resultSet);
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
		return cliente;
	}
	
	@Override
	public List<ClienteDTO> readAll() {
		PreparedStatement statement;
		ResultSet resultSet; //Guarda el resultado de la query
		ArrayList<ClienteDTO> clientes = new ArrayList<ClienteDTO>();
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

	private ClienteDTO getClienteDTOO(ResultSet resultSet) throws SQLException {
		int id = resultSet.getInt("idCliente");
		String nombre = resultSet.getString("Nombre");
		String apellido = resultSet.getString("Apellido");
		String tipoDoc = resultSet.getString("TipoDocumento");
		String documento = resultSet.getString("Documento");
		String email = resultSet.getString("Email");
		String tel = resultSet.getString("Telefono");
		Boolean estado = resultSet.getBoolean("Estado");
		Date fechaNacimiento = resultSet.getDate("FechaNacimiento");
		return new ClienteDTO(id, nombre, apellido, tipoDoc, documento, email, tel, estado, fechaNacimiento);
	}

	@Override
	public void update(ClienteDTO cliente) {
		PreparedStatement statement;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		try{
			statement = conexion.prepareStatement(update);
			statement.setString(1, cliente.getNombre());
			statement.setString(2, cliente.getApellido());
			statement.setString(3, cliente.getTipoDocumento());
			statement.setString(4, cliente.getNumeroDocumento());
			statement.setString(5, cliente.getEmail());
			statement.setString(6, cliente.getTelefono());
			statement.setBoolean(7, cliente.getEstado());
			statement.setDate(8, cliente.getFechaNacimiento());
			statement.setInt(9, cliente.getIdCliente());
			
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
	public List<ClienteDTO> search(String buscar) {
		PreparedStatement statement;
		
		ResultSet resultSet; //Guarda el resultado de la query
		ArrayList<ClienteDTO> clientes = new ArrayList<ClienteDTO>();
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
				clientes.add(getClienteDTOO(resultSet));
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		return clientes;
	}

}
