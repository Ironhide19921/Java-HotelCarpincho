package dto;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
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

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import modelo.Configuracion;
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
	private String prov;
	private String puerto;
	private String fromEmail; 
	private String toEmail;
	
	private Session session;
	private Properties properties;
	private ObservableList<ConfiguracionDTO> listaConfig;
	private Configuracion configuracion;
	private static Email ModeloEmail;

	public EmailDTO(int idEmail, Date fechaCreacion, String texto, String asunto, String emisor, String receptor, Boolean estado, String pass) {
		this.idEmail = idEmail;
		this.fechaCreacion = fechaCreacion;
		this.texto = texto;
		this.asunto = asunto;
		this.emisor = emisor;
		this.receptor = receptor;
		this.estado = estado;
		this.pass = pass;
		
		this.configuracion = new Configuracion(new DAOSQLFactory());
		this.listaConfig = FXCollections.observableArrayList();
		this.listaConfig = getAllConfigs();
		
//		username = "carpinchocorp@gmail.com";
//		password = "covid-19";
//		emisor = listaConfig.get(0).getUsername();
//		pass = listaConfig.get(0).getPassword();
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
		setToEmail("gestrada@neotel.com.ar");
		
		setearPropiedades();
		
		session = Session.getInstance(properties, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(emisor,pass);
			}
		});
	}
	
	public EmailDTO() {
		
		this.configuracion = new Configuracion(new DAOSQLFactory());
		this.listaConfig = FXCollections.observableArrayList();
		this.listaConfig = getAllConfigs();
		
		emisor = listaConfig.get(0).getUsername();
		pass = listaConfig.get(0).getPassword();
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
		setToEmail("gestrada@neotel.com.ar");
		
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
	
	private ObservableList<ConfiguracionDTO> getAllConfigs() {
		List<ConfiguracionDTO> configs = this.configuracion.obtenerConfiguraciones();
		//		listaConfig.clear();
		for(ConfiguracionDTO c : configs) {
			listaConfig.add(c);
		}
		return listaConfig;
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
		
//		properties.put("mail.smtp.auth", "true");
//		properties.put("mail.smtp.starttls.enable", "true");
//		properties.put("mail.smtp.host", "smtp.gmail.com");
//		properties.put("mail.smtp.port", "587");

	}
	
	public static void enviarMsj(EmailDTO email, String msj) {
		//Start our mail message
				MimeMessage msg = new MimeMessage(email.getSession());
				try {
					msg.setFrom(new InternetAddress(email.getEmisor()));
					msg.addRecipient(Message.RecipientType.TO, new InternetAddress(email.getReceptor()));
					msg.setSubject(email.getAsunto());
//					msg.setText(email.getTexto());
					msg.setText(msj);
					System.out.println("Mensaje enviado");

					Transport.send(msg);
				} catch (AddressException e) {
					e.printStackTrace();
				} catch (MessagingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	}
	
	public void enviarMsjTest() {
		//Start our mail message
				MimeMessage msg = new MimeMessage(this.getSession());
				try {
					msg.setFrom(new InternetAddress(this.getEmisor()));
					msg.addRecipient(Message.RecipientType.TO, new InternetAddress(this.getEmisor()));
					msg.setSubject("Mensaje de prueba exitoso");
					msg.setText("Envio correcto");
					System.out.println("Mensaje enviado");

					Transport.send(msg);
				} catch (AddressException e) {
					e.printStackTrace();
				} catch (MessagingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	}
	
	public static void enviarEmailsEncolados() {
		ModeloEmail = new Email(new DAOSQLFactory());
		int cant = 0;
		Date hoy = new Date(System.currentTimeMillis());
		for(EmailDTO email : ModeloEmail.obtenerEmails()) {
//			EmailDTO emailEnviar = new EmailDTO(email.getIdEmail(), email.getFechaCreacion(), email.getTexto(), email.getAsunto(), email.getEmisor(), email.getReceptor(), email.getEstado(), email.getPass());
			if(compararFechas(hoy, email.getFechaCreacion())<=7 && compararFechas(hoy, email.getFechaCreacion())>0 && email.getEstado()==false) {
				enviarMsj(email,"Mail encolado");
				email.setEstado(true);
				ModeloEmail.modificarEmail(email);
				cant++;
			}
		}
		
		//Validador.mostrarMensaje("Se enviaron "+cant+" mensajes");
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
	
	public void enviarMsjAdjunto(String msj, String path, String receptor, String motivo) {
		
		
		//Start our mail message
		MimeMessage msg = new MimeMessage(session);
		try {
			msg.setFrom(new InternetAddress(fromEmail));
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(receptor));
			
			switch(motivo) {
			case "ticket":
				msg.setSubject("Adjunto con ticket");
				break;
			case "reporte":
				msg.setSubject("Adjunto con reporte");
				break;
			}
			
			//msg.setSubject("Mensaje con adjunto");
		
			System.out.println("Mensaje enviado con adjunto");

			//Adjunto
			Multipart emailContent = new MimeMultipart();

			MimeBodyPart textBodyPart = new MimeBodyPart();
			textBodyPart.setText(msj);

			MimeBodyPart csvAttachment = new MimeBodyPart();
			try {
//				csvAttachment.attachFile("C:/Users/marcos/Desktop/carpinchofx/cliente.csv/");
				csvAttachment.attachFile(path);
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
			System.out.println(emisor);
			System.out.println(pass);
			System.out.println(puerto);
			System.out.println(prov);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Validador.mostrarMensaje("Error con mensaje");
			System.out.println(emisor);
			System.out.println(pass);
			System.out.println(puerto);
			System.out.println(prov);
		}
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

	public String getToEmail() {
		return toEmail;
	}

	public void setToEmail(String toEmail) {
		this.toEmail = toEmail;
	}

}
