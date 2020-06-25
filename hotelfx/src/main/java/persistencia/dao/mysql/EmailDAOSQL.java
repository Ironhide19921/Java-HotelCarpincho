package persistencia.dao.mysql;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.EmailDTO;
import persistencia.conexion.Conexion;
import persistencia.dao.interfaz.EmailDAO;

public class EmailDAOSQL implements EmailDAO{

	private static final String insert = "INSERT INTO email(fechaCreacion, texto, asunto, emisor, receptor, estado, pass) " + "VALUES(?, ?, ?, ?, ?, ?, ?)";
	private static final String delete = "DELETE FROM email WHERE idEmail = ?";
	private static final String readall = "SELECT * FROM email";
	private static final String update = "UPDATE email SET fechaCreacion = ?, texto = ?, asunto = ?, emisor = ?, receptor = ?, estado = ? , pass = ? WHERE idEmail = ?";
	private static final String search = "SELECT * FROM email WHERE fechaCreacion LIKE ?";

	@Override
	public boolean insert(EmailDTO email) {
		PreparedStatement statement;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		boolean isInsertExitoso = false;
		try{
			statement = conexion.prepareStatement(insert);
			
			statement.setDate(1, email.getFechaCreacion());
			statement.setString(2, email.getTexto());
			statement.setString(3, email.getAsunto());
			statement.setString(4, email.getEmisor());
			statement.setString(5, email.getReceptor());
			statement.setBoolean(6, email.getEstado());
			statement.setString(7, email.getPass());

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
	public boolean delete(EmailDTO email_a_eliminar) {
		return false;
	}

	@Override
	public List<EmailDTO> readAll() {
		PreparedStatement statement;
		ResultSet resultSet; //Guarda el resultado de la query
		ArrayList<EmailDTO> emails = new ArrayList<EmailDTO>();
		Conexion conexion = Conexion.getConexion();
	
		try {
			statement = conexion.getSQLConexion().prepareStatement(readall);
			resultSet = statement.executeQuery();
			while(resultSet.next()){
				emails.add(getEmailDTOO(resultSet));
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		return emails;
	}

	private EmailDTO getEmailDTOO(ResultSet resultSet) throws SQLException {
		int idEmail = resultSet.getInt("idEmail");
		Date fechaCreacion = resultSet.getDate("FechaCreacion");
		String texto = resultSet.getString("Texto");
		String asunto = resultSet.getString("Asunto");
		String emisor = resultSet.getString("Emisor");
		String receptor = resultSet.getString("Receptor");
		Boolean estado = resultSet.getBoolean("Estado");
		String pass = resultSet.getString("Pass");


		return new EmailDTO(idEmail, fechaCreacion, texto, asunto, emisor, receptor, estado, pass);
	}

	@Override
	public void update(EmailDTO email) {
		PreparedStatement statement;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		try{
			statement = conexion.prepareStatement(update);
			statement.setDate(1, email.getFechaCreacion());
			statement.setString(2, email.getTexto());
			statement.setString(3, email.getAsunto());
			statement.setString(4, email.getEmisor());
			statement.setString(5, email.getReceptor());
			statement.setBoolean(6, email.getEstado());
			statement.setString(7, email.getPass());
			statement.setInt(8, email.getIdEmail());
			
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
	public List<EmailDTO> search(String buscar) {
		PreparedStatement statement;
		
		ResultSet resultSet; //Guarda el resultado de la query
		ArrayList<EmailDTO> emails = new ArrayList<EmailDTO>();
		Conexion conexion = Conexion.getConexion();
		try {
			
			statement = conexion.getSQLConexion().prepareStatement(search);
			statement.setString(1, buscar);


			resultSet = statement.executeQuery();
			while(resultSet.next()){
				emails.add(getEmailDTOO(resultSet));
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		return emails;
	}

}
