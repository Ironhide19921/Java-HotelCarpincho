package presentacion.controlador;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

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
import presentacion.vista.FxmlLoader;

public class ControladorMenuPrincipal implements Initializable{

	@FXML
	private Button btnAbrirABMCliente;
	@FXML
	private Button btnAbrirABMReservas;
	@FXML
	private Button btnAbrirABMUsuarios;
	@FXML
	private Button btnAbrirABMPerfiles;
	@FXML
	private Button btnAbrirABMCuartos;
	@FXML
	private Button btnAbrirABMCategoriasCuartos;
	@FXML
	private Button btnAbrirImportar;
	
	Controller controler;

	@FXML
	private BorderPane mainPane;
	
	@FXML
	private Pane pane;


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		this.controler = new Controller();
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

	
}
