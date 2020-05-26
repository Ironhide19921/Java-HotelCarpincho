package presentacion.controlador;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Controller {
	private Stage primaryStage;
	private Pane view;
	
	@FXML ControladorABMCliente ventanaClienteController;
	@FXML ControladorAgregarCliente ventanaCliente;
	@FXML ControladorVentanaLogin ventanaLogin;
	@FXML ControladorABMPerfil ventanaPerfil;

	public Controller(){
		this.primaryStage = new Stage();
		
	}
	
	public Scene crearSceneABMClientes(Parent root) throws IOException {
	URL fxml = getClass().getClassLoader()
  			.getResource("presentacion/vista/VentanaPrincipal.fxml");
      FXMLLoader fxmlLoader = new FXMLLoader(fxml);
      Scene scene = new Scene(fxmlLoader.load());
      scene.setRoot(root);;
      return scene;
	}
	
	
	public Scene  crearSceneAgregarCliente(Parent root) throws IOException {
		URL fxml = getClass().getClassLoader()
  				.getResource("presentacion/vista/VentanaCliente.fxml");
      FXMLLoader fxmlLoader = new FXMLLoader(fxml);
      Scene scene = new Scene(fxmlLoader.load());
      scene.setRoot(root);;
      return scene;
	}
	
	public Scene  crearSceneVentanaLogin(Parent root) throws IOException {
		URL fxml = getClass().getClassLoader()
  				.getResource("presentacion/vista/VentanaLogin.fxml");
      FXMLLoader fxmlLoader = new FXMLLoader(fxml);
      Scene scene = new Scene(fxmlLoader.load());
      scene.setRoot(root);;
      return scene;
	}
	
	public Scene  crearSceneMenuPrincipal(Parent root) throws IOException {
		URL fxml = getClass().getClassLoader()
  				.getResource("presentacion/vista/MenuPrincipal.fxml");
      FXMLLoader fxmlLoader = new FXMLLoader(fxml);
      Scene scene = new Scene(fxmlLoader.load());
      scene.setRoot(root);;
      return scene;
	}
	
	public Scene  crearSceneABMPerfiles(Parent root) throws IOException {
		URL fxml = getClass().getClassLoader()
  				.getResource("presentacion/vista/VentanaABMPerfil.fxml");
      FXMLLoader fxmlLoader = new FXMLLoader(fxml);
      Scene scene = new Scene(fxmlLoader.load());
      scene.setRoot(root);;
      return scene;
	}
	
	public Scene crearSceneABMCuartos(Parent root) throws IOException {
		URL fxml = getClass().getClassLoader()
  				.getResource("presentacion/vista/VentanaPrincipal.fxml");
      FXMLLoader fxmlLoader = new FXMLLoader(fxml);
      Scene scene = new Scene(fxmlLoader.load());
      scene.setRoot(root);
      return scene;
	}
	
}
