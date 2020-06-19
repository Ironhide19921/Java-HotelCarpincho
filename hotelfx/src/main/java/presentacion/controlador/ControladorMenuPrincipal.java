package presentacion.controlador;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

import org.joda.time.DateTime;

import dto.EmailDTO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import modelo.Email;
import modelo.Validador;
import presentacion.vista.FxmlLoader;

public class ControladorMenuPrincipal implements Initializable{

	@FXML
	private Button btnAbrirABMProductos;

	@FXML
	private Button btnAbrirConfig;

	@FXML private Button btnAbrirABMCliente;
	@FXML private Button btnAbrirABMReservas;
	@FXML private Button btnAbrirABMUsuarios;
	@FXML private Button btnAbrirABMPerfiles;
	@FXML private Button btnAbrirABMCuartos;
	@FXML private Button btnAbrirABMCategoriasCuartos;
	@FXML private Button btnAbrirImportar;
	@FXML private Button btnAbrirReservaEvento;
	@FXML private Button btnAbrirCategoriaEvento;
	@FXML private Button btnAbrirVentanaBackup;
	@FXML private EmailDTO email;

	@FXML private static Button btnAbrirDivisas;
	
	@FXML private Button btnAbrirABMSalones;
	@FXML
	private Button btnAbrirABMCategoriaEvento;
	@FXML
	private Button btnMenuReservaEvento;
	@FXML private Button btnAbrirOrdenPedidos;
	@FXML private BorderPane mainPane;
	@FXML private Pane center;
	@FXML private Pane pane;
	
	Date hoy = new Date(System.currentTimeMillis());
	
	ControladorBackup gestionBackup = new ControladorBackup();

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

		this.email = new EmailDTO(0, null, null, null, null, null, null, null);
		//email.start();
		
		if(EmailDTO.compararFechas(gestionBackup.fechaUltimoBackup(), hoy)>0){
			gestionBackup.backup();
		}

	}


	@FXML
	public void verABMClientes() {
		try {
			FxmlLoader fxmlLoader = new FxmlLoader();
			Pane view	= fxmlLoader.getPage("VentanaABMCliente");
			mainPane.setCenter(view);

		} catch(Exception e) {
			e.printStackTrace();
		}
	}


	@FXML
	public void verABMCategorias() {
		try {
			FxmlLoader fxmlLoader = new FxmlLoader();
			Pane view	= fxmlLoader.getPage("VentanaABMCategoriaCuarto");
			mainPane.setCenter(view);

		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void verABMUsuarios() {
		try {
			FxmlLoader fxmlLoader = new FxmlLoader();
			Pane view	= fxmlLoader.getPage("VentanaABMUsuarios");
			mainPane.setCenter(view);

		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void verABMPerfiles() {
		try {
			FxmlLoader fxmlLoader = new FxmlLoader();
			Pane view	= fxmlLoader.getPage("VentanaABMPerfil");
			mainPane.setCenter(view);

		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void verImportar() {
		try {
			FxmlLoader fxmlLoader = new FxmlLoader();
			Pane view	= fxmlLoader.getPage("Importar");
			mainPane.setCenter(view);

		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	public void verABMCuartos() {
		try {
			 FxmlLoader fxmlLoader = new FxmlLoader();
			 Pane view	= fxmlLoader.getPage("VentanaABMCuarto");
			 mainPane.setCenter(view);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	public void verABMProductos() {
		try {
			 FxmlLoader fxmlLoader = new FxmlLoader();
			 Pane view	= fxmlLoader.getPage("VentanaABMProducto");
			 mainPane.setCenter(view);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}	
			 
	@FXML
	public void verABMSalones() {
		try {
			 FxmlLoader fxmlLoader = new FxmlLoader();
			 Pane view	= fxmlLoader.getPage("VentanaABMSalon");
			 
			 mainPane.setCenter(view);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	public void verConfig() {
		try {
			 FxmlLoader fxmlLoader = new FxmlLoader();
			 Pane view	= fxmlLoader.getPage("Configuracion");
			 mainPane.setCenter(view);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void verABMCategoriaEvento() {
		try {
			 FxmlLoader fxmlLoader = new FxmlLoader();
			 Pane view	= fxmlLoader.getPage("VentanaABMCategoriaEvento");
			 mainPane.setCenter(view);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	public void verMenuReservaEvento() {
		try {
			 FxmlLoader fxmlLoader = new FxmlLoader();
			 Pane view	= fxmlLoader.getPage("VentanaABMReservaEvento");
			 mainPane.setCenter(view);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	@FXML
	public void verReservaCuarto() {
		try {
			 FxmlLoader fxmlLoader = new FxmlLoader();
			 Pane view	= fxmlLoader.getPage("VentanaABMReservaCuarto");
			 mainPane.setCenter(view);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	public void verABMOrdenPedidos() {
		try {
			 FxmlLoader fxmlLoader = new FxmlLoader();
			 Pane view	= fxmlLoader.getPage("VentanaABMOrdenPedido");
			 mainPane.setCenter(view);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	public void verVentanaBackup() {
		try {
			 FxmlLoader fxmlLoader = new FxmlLoader();
			 Pane view	= fxmlLoader.getPage("VentanaBackup");
			 mainPane.setCenter(view);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	public void verDivisas() {
		{
		     try { 
			    Stage primaryStage = new Stage(); 
		 		URL fxml = getClass().getClassLoader().getResource("presentacion/vista/Divisas.fxml");
				FXMLLoader fxmlLoader = new FXMLLoader(fxml);
				Parent root = (Parent) fxmlLoader.load();
		
				primaryStage.setScene(new Scene(root));   
				primaryStage.getScene().getStylesheets().add("/CSS/mycss.css");
				primaryStage.setTitle("Conversión de divisas");
				primaryStage.sizeToScene();
				primaryStage.show();

		     } catch(Exception e) { 
		      e.printStackTrace(); 
		     } 
	    }
	}
	
}
