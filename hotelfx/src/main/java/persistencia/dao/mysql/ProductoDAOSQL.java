package persistencia.dao.mysql;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.ProductoDTO;
import persistencia.conexion.Conexion;
import persistencia.dao.interfaz.ProductoDAO;

public class ProductoDAOSQL implements ProductoDAO{

	private static final String insert = "INSERT INTO producto(nombre, precio, descripcion, proveedor, tipo) "
			+ "VALUES(?, ?, ?, ?, ?)";
	private static final String readall = "SELECT * FROM producto";
	private static final String update = "UPDATE producto SET nombre = ?, precio = ?, descripcion = ?, proveedor = ?, tipo = ? WHERE idProducto = ?";
	private static final String search = "SELECT * FROM producto WHERE precio LIKE ? OR nombre LIKE ? OR descripcion LIKE ? OR proveedor LIKE ? OR tipo LIKE ?";
	private static final String delete = "DELETE FROM producto WHERE idProducto = ?";
	
	@Override
	public boolean insert(ProductoDTO producto) {
		PreparedStatement statement;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		boolean isInsertExitoso = false;
		try{
			statement = conexion.prepareStatement(insert);
			statement.setString(1, producto.getNombre());
			statement.setBigDecimal(2, producto.getPrecio());
			statement.setString(3, producto.getDescripcion());
			statement.setString(4, producto.getProveedor());
			statement.setString(5, producto.getTipo());
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
	public void update(ProductoDTO producto) {
		PreparedStatement statement;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		try{
			statement = conexion.prepareStatement(update);
			statement.setString(1, producto.getNombre());
			statement.setBigDecimal(2, producto.getPrecio());			
			statement.setString(3, producto.getDescripcion());
			statement.setString(4, producto.getProveedor());
			statement.setString(5, producto.getTipo());
			statement.setInt(6, producto.getIdProducto());
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
	public List<ProductoDTO> readAll() {
		PreparedStatement statement;
		ResultSet resultSet; //Guarda el resultado de la query
		ArrayList<ProductoDTO> productos = new ArrayList<ProductoDTO>();
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(readall);
			resultSet = statement.executeQuery();
			while(resultSet.next()){
				productos.add(getProductoDTO(resultSet));
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return productos;
	}

	private ProductoDTO getProductoDTO(ResultSet resultSet) throws SQLException{
		int id = resultSet.getInt("idProducto");
		BigDecimal precio = resultSet.getBigDecimal("Precio");
		String nombre = resultSet.getString("Nombre");
		String descripcion = resultSet.getString("Descripcion");
		String proveedor = resultSet.getString("Proveedor");
		String tipo = resultSet.getString("Tipo");		
		return new ProductoDTO(id, nombre, precio, descripcion, proveedor, tipo);
	}

	@Override
	public List<ProductoDTO> search(String buscar) {
		PreparedStatement statement;
		ResultSet resultSet; //Guarda el resultado de la query
		ArrayList<ProductoDTO> productos = new ArrayList<ProductoDTO>();
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
				productos.add(getProductoDTO(resultSet));
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return productos;
	}

	@Override
	public boolean delete(ProductoDTO productoAeliminar) {
		PreparedStatement statement;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		boolean isDeleteExitoso = false;
		try{
			statement = conexion.prepareStatement(delete);
			statement.setInt(1, productoAeliminar.getIdProducto());
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
