package persistencia.dao.interfaz;

import java.util.List;

import dto.EmailDTO;

public interface EmailDAO {
	
	public boolean insert(EmailDTO email);

	public boolean delete(EmailDTO email_a_eliminar);
	
	public List<EmailDTO> readAll();
	
	public void update(EmailDTO email);

	public List<EmailDTO> search(String buscar);

}
