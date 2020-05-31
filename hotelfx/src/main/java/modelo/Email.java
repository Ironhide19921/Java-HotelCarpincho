package modelo;

import java.util.List;

import dto.EmailDTO;
import persistencia.dao.interfaz.DAOAbstractFactory;
import persistencia.dao.interfaz.EmailDAO;

public class Email {

	private EmailDAO email;

	public Email(DAOAbstractFactory metodo_persistencia){
		this.email = metodo_persistencia.createEmailDAO();
	}
	
	public void agregarEmail(EmailDTO nuevoEmail){
		this.email.insert(nuevoEmail);
	}
	
	public void modificarEmail(EmailDTO email) {
		this.email.update(email);
	}
	
	public List<EmailDTO> obtenerEmails() 	{
		return this.email.readAll();
	}
	
	public List<EmailDTO> buscarEmail(String buscar) 	{
		return this.email.search(buscar);
	}
}
