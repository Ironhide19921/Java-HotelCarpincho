package presentacion.controlador;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import modelo.Validador;
import dto.ConfiguracionDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import modelo.Configuracion;
import persistencia.dao.mysql.DAOSQLFactory;

public class Mail {

	//	public static void main(String[] args) {
	// authentication info
	private String username, password, fromEmail, toEmail, prov, puerto;
	private Session session;
	private Properties properties;
	private ObservableList<ConfiguracionDTO> listaConfig;
	private Configuracion configuracion;
	
	public Mail() {
		this.configuracion = new Configuracion(new DAOSQLFactory());
		this.listaConfig = FXCollections.observableArrayList();
		this.listaConfig = getAllConfigs();
		
//		username = "carpinchocorp@gmail.com";
//		password = "covid-19";
		username = listaConfig.get(0).getUsername();
		password = listaConfig.get(0).getPassword();
		prov = listaConfig.get(0).getProvSMTP();
		switch(prov) {
		case "smtp.gmail.com":
			puerto = "587";
			break;
		case "smtp.live.com":
			puerto = "25";
			break;
		}
		
		fromEmail = listaConfig.get(0).getUsername();
		toEmail = "gestrada@neotel.com.ar";
		setearPropiedades();
		session = Session.getInstance(properties, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username,password);
			}
		});
	}
	
	public void  setearPropiedades() {
		properties = new Properties();
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.host", prov);
		properties.put("mail.smtp.port", puerto);
//		properties.put("mail.smtp.host", "smtp.live.com");
//		properties.put("mail.smtp.port", "25");
//		properties.put("mail.smtp.host", "smtp.gmail.com");
//		properties.put("mail.smtp.port", "587");

	}

	public void enviarMsj() {
		//Start our mail message
		MimeMessage msg = new MimeMessage(session);
		try {
			msg.setFrom(new InternetAddress(fromEmail));
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
			msg.setSubject("Mensaje de prueba");
			msg.setText("Texto dentro del mail");
			System.out.println("Mensaje enviado");

			//Adjunto
			Multipart emailContent = new MimeMultipart();

			MimeBodyPart textBodyPart = new MimeBodyPart();
			textBodyPart.setText("Mensaje multiparte");

			MimeBodyPart csvAttachment = new MimeBodyPart();
			try {
				csvAttachment.attachFile("C:/Users/gera/Desktop/cliente.csv");
				System.out.println("Se pudo acceder al archivo");
			} catch (IOException e) {
				System.out.println("No se pudo acceder al archivo");
				e.printStackTrace();
			}

			//Attach body parts
			emailContent.addBodyPart(textBodyPart);
			emailContent.addBodyPart(csvAttachment);

			//Attach multipart to message
			msg.setContent(emailContent);
			//String mensaje = "Mensaje enviado";
			//Transport.send(msg -> System.out.println("Mensaje enviado"));
			Transport.send(msg);
			Validador.mostrarMensaje("Testing exitoso!");
		} catch (AddressException e) {
			e.printStackTrace();
			Validador.mostrarMensaje("Error con cuenta de mail");
			System.out.println(username);
			System.out.println(password);
			System.out.println(puerto);
			System.out.println(prov);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Validador.mostrarMensaje("Error con mensaje");
			System.out.println(username);
			System.out.println(password);
			System.out.println(puerto);
			System.out.println(prov);
		}
	}
	
	private ObservableList<ConfiguracionDTO> getAllConfigs() {
		List<ConfiguracionDTO> configs = this.configuracion.obtenerConfiguraciones();
		//		listaConfig.clear();
		for(ConfiguracionDTO c : configs) {
			listaConfig.add(c);
		}
		return listaConfig;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getProv() {
		return prov;
	}

	public void setProv(String prov) {
		this.prov = prov;
	}

	public String getPuerto() {
		return puerto;
	}

	public void setPuerto(String puerto) {
		this.puerto = puerto;
	}

}
