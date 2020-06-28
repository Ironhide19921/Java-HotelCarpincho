package persistencia.conexion;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import org.apache.log4j.Logger;

import dto.ConexionConfigDTO;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import modelo.ConexionConfig;
import modelo.Validador;
import persistencia.dao.mysql.DAOSQLFactory;
import presentacion.controlador.ControladorAgregarCliente;
import presentacion.controlador.ControladorConexionConfig;
import presentacion.controlador.ControladorMenuPrincipal;

public class Conexion {
	
	private Alert alert;
	public static Conexion instancia;
	private Connection connection;
	private Logger log = Logger.getLogger(Conexion.class);
	
	public static String ejecucion = "Aceptar";

	private Conexion() {
		int estado = 0;

		while (estado == 0) {

			ConexionConfigDTO config = new ConexionConfigDTO(0, null, null, null);
			config = ControladorConexionConfig.leerFicheroConexion();

			try {

				Class.forName("com.mysql.jdbc.Driver");
				this.connection = DriverManager.getConnection("jdbc:mysql://" + config.getHost() + "/hotel", //Recordar cambiar el test
						"" + config.getUser() + "", "" + config.getPass() + "");
				this.connection.setAutoCommit(false);
				log.info("ConexiÃ³n exitosa");
				estado = 1;
			}

			catch (Exception e) {
				ejecucion = Validador.mostrarMensajeOpcion();
				if (ejecucion.equals("Aceptar") || ejecucion.equals("OK")) {
					ControladorMenuPrincipal menuPrincipal = new ControladorMenuPrincipal();
					menuPrincipal.verVentanaConexion();
				}
				if (ejecucion.equals("Cancelar") || ejecucion.equals("Cancel")) {
					break;
				}
			}

		}

	}

	
	public static Conexion getConexion()   
	{								
		if(instancia == null)
		{
			instancia = new Conexion();
		}
		return instancia;
	}

	public Connection getSQLConexion() 
	{
		return this.connection;
	}
	
	public void cerrarConexion()
	{
		try 
		{
			this.connection.close();
			log.info("Conexion cerrada");
		}
		catch (SQLException e) 
		{
			log.error("Error al cerrar la conexiÃ³n!", e);
		}
		instancia = null;
	}
	
	private void mostrarMensaje(String mensaje) {
		alert.setTitle("Información");
		alert.setHeaderText(null);
		alert.setContentText(mensaje);

		alert.showAndWait();
	}

}
