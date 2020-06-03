package persistencia.dao.mysql;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.OrdenPedidoDTO;
import persistencia.conexion.Conexion;
import persistencia.dao.interfaz.OrdenPedidoDAO;

public class OrdenPedidoDAOSQL implements OrdenPedidoDAO {
	
	private static final String insert = "INSERT INTO ordenPedido(idOrdenPedido, idProducto, idCliente, idUsuario, cantidad, precioTotal) "
			+ "VALUES(?, ?, ?, ?, ?, ?)";
	private static final String readall = "SELECT * FROM ordenPedido";
	private static final String update = "UPDATE ordenPedido SET idCliente = ?, idUsuario = ?, cantidad = ?, precioTotal = ? WHERE idOrdenPedido = ?";
	private static final String obtenerIdMax = "SELECT MAX(idOrdenPedido) FROM ordenPedido"; 
	private static final String delete = "DELETE FROM ordenPedido WHERE idOrdenPedido = ?";
	
	@Override
	public boolean insert(OrdenPedidoDTO nuevaOrdenPedido) {
		PreparedStatement statement;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		boolean isInsertExitoso = false;
		try{
			statement = conexion.prepareStatement(insert);
			statement.setInt(1, nuevaOrdenPedido.getIdOrdenPedido());
			statement.setInt(2, nuevaOrdenPedido.getIdProducto());
			statement.setInt(3, nuevaOrdenPedido.getIdCliente());
			statement.setInt(4, nuevaOrdenPedido.getIdUsuario());
			statement.setInt(5, nuevaOrdenPedido.getCantidad());
			statement.setBigDecimal(6, nuevaOrdenPedido.getPrecioTotal());
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
	public void update(OrdenPedidoDTO ordenPedido) {
		PreparedStatement statement;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		try{
			statement = conexion.prepareStatement(update);
			statement.setInt(1, ordenPedido.getIdCliente());
			statement.setInt(2, ordenPedido.getIdUsuario());
			statement.setInt(3, ordenPedido.getCantidad());
			statement.setBigDecimal(4, ordenPedido.getPrecioTotal());
			statement.setInt(5, ordenPedido.getIdOrdenPedido());
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
	public List<OrdenPedidoDTO> readAll() {
		PreparedStatement statement;
		ResultSet resultSet; //Guarda el resultado de la query
		ArrayList<OrdenPedidoDTO> ordenesPedidos = new ArrayList<OrdenPedidoDTO>();
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(readall);
			resultSet = statement.executeQuery();
			while(resultSet.next()){
				ordenesPedidos.add(getOrdenPedidoDTO(resultSet));
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return ordenesPedidos;
	}

	private OrdenPedidoDTO getOrdenPedidoDTO(ResultSet resultSet) throws SQLException{
		int id = resultSet.getInt("idOrdenPedido");
		int idProducto = resultSet.getInt("idProducto");
		int idCliente = resultSet.getInt("idCliente");
		int idUsuario = resultSet.getInt("idUsuario");
		int cantidad = resultSet.getInt("Cantidad");
		BigDecimal precioTotal = resultSet.getBigDecimal("PrecioTotal");
		return new OrdenPedidoDTO(id, idProducto, idCliente, idUsuario, cantidad, precioTotal);
	}

	@Override
	public int obtenerIdMaximo() {
		PreparedStatement statement;
		ResultSet resultSet;
		int id = 0;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(obtenerIdMax);
			resultSet = statement.executeQuery();
			
			while(resultSet.next()){
				id = resultSet.getInt(1);
				System.out.println("id "+ id);
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return id;
	}
	
	@Override
	public boolean delete(OrdenPedidoDTO pedidoAeliminar) {
		PreparedStatement statement;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		boolean isDeleteExitoso = false;
		try{
			statement = conexion.prepareStatement(delete);
			statement.setInt(1, pedidoAeliminar.getIdOrdenPedido());
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

}
