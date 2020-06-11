package dto;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import modelo.Email;
import modelo.Validador;
import persistencia.dao.mysql.DAOSQLFactory;

public class EmailDTO extends Thread{
	
	private int idEmail;
	private Date fechaCreacion;
	private String texto;
	private String asunto;
	private String emisor;
	private String receptor;
	private Boolean estado;
	private String pass;
	
	private Session session;
	private Properties properties;
	
	private static Email email;

	public EmailDTO(int idEmail, Date fechaCreacion, String texto, String asunto, String emisor, String receptor, Boolean estado, String pass) {
		this.idEmail = idEmail;
		this.fechaCreacion = fechaCreacion;
		this.texto = texto;
		this.asunto = asunto;
		this.emisor = emisor;
		this.receptor = receptor;
		this.estado = estado;
		this.pass = pass;
		
		setearPropiedades();
		
		session = Session.getInstance(properties, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(emisor,pass);
			}
		});
	}
	
	public void run() {
		enviarEmailsEncolados();
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
	
	public static void enviarMsj(EmailDTO email) {
		//Start our mail message
				MimeMessage msg = new MimeMessage(email.getSession());
				try {
					msg.setFrom(new InternetAddress(email.getEmisor()));
					msg.addRecipient(Message.RecipientType.TO, new InternetAddress(email.getReceptor()));
					msg.setSubject(email.getAsunto());
					msg.setText(email.getTexto());
					System.out.println("Mensaje enviado");

//					//Adjunto
//					Multipart emailContent = new MimeMultipart();
//
//					MimeBodyPart textBodyPart = new MimeBodyPart();
//					textBodyPart.setText("Mensaje multiparte");
//
//					MimeBodyPart csvAttachment = new MimeBodyPart();
//					try {
//						csvAttachment.attachFile("C:/Users/gera/Desktop/cliente.csv");
//						System.out.println("Se pudo acceder al archivo");
//					} catch (IOException e) {
//						System.out.println("No se pudo acceder al archivo");
//						e.printStackTrace();
//					}
//
//					//Attach body parts
//					emailContent.addBodyPart(textBodyPart);
//					emailContent.addBodyPart(csvAttachment);
//
//					//Attach multipart to message
//					msg.setContent(emailContent);
//					//String mensaje = "Mensaje enviado";
//					//Transport.send(msg -> System.out.println("Mensaje enviado"));
					Transport.send(msg);
				} catch (AddressException e) {
					e.printStackTrace();
				} catch (MessagingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	}
	
	public static void enviarEmailsEncolados() {
		email = new Email(new DAOSQLFactory());
		int cant = 0;
		for(EmailDTO email : email.obtenerEmails()) {
			
			EmailDTO emailEnviar = new EmailDTO(email.getIdEmail(), email.getFechaCreacion(), email.getTexto(), email.getAsunto(), email.getEmisor(), email.getReceptor(), email.getEstado(), email.getPass());
			
			Date hoy = new Date(System.currentTimeMillis());
			
			if(compararFechas(hoy, emailEnviar.getFechaCreacion())<=7 && compararFechas(hoy, emailEnviar.getFechaCreacion())>0) {
				enviarMsj(emailEnviar);
				cant++;
			}
		}
	}
	
	public static int compararFechas(Date fechaInicial, Date fechaFinal) {
		int diferencia=(int) ((fechaFinal.getTime()-fechaInicial.getTime())/1000);
		 
        int dias=0;
        int horas=0;
        int minutos=0;
        if(diferencia>86400) {
            dias=(int)Math.floor(diferencia/86400);
            diferencia=diferencia-(dias*86400);
        }
        
		return dias;
	}
	
	public String getEmisor() {
		return emisor;
	}

	public void setEmisor(String emisor) {
		this.emisor = emisor;
	}

	public String getReceptor() {
		return receptor;
	}

	public void setReceptor(String receptor) {
		this.receptor = receptor;
	}

	public int getIdEmail() {
		return idEmail;
	}

	public void setIdEmail(int idEmail) {
		this.idEmail = idEmail;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public String getAsunto() {
		return asunto;
	}

	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}

	public Boolean getEstado() {
		return estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}
	
	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}

	public Properties getProperties() {
		return properties;
	}

	public void setProperties(Properties properties) {
		this.properties = properties;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

}
