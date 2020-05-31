package presentacion.controlador;
import java.io.IOException;
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

import dto.EmailDTO;
import modelo.Email;
import modelo.Perfil;
import persistencia.dao.mysql.DAOSQLFactory;

public class Mail {

	//	public static void main(String[] args) {
	// authentication info
	String username,password,fromEmail,toEmail;
	private Session session;
	private Properties properties;
	
	private String asunto;
	private String texto;
	

	
	public Mail() {
		username = "carpinchocorp@gmail.com";
		password = "covid-19";
		fromEmail = "carpinchocorp@gmail.com";
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
//		properties.put("mail.smtp.host", "smtp.mail.yahoo.com");
//		properties.put("mail.smtp.host", "smtp.live.com");
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.port", "587");
//		properties.put("mail.smtp.port", "25");
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
		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}