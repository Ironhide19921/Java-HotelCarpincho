package main;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import modelo.Validador;
import persistencia.conexion.Conexion;
import presentacion.controlador.ControladorMenuPrincipal;
import javafx.scene.Parent;
import javafx.scene.Scene;


public class Main extends Application {
	
	public static Stage stage;
	
	@Override
	public void start(Stage primaryStage) {
		Conexion.getConexion();
		//ControladorMenuPrincipal.loginStage.setFullScreen(true);
		
		if(Conexion.ejecucion.equals("Cancelar") || Conexion.ejecucion.equals("Cancel")) {
			System.exit(1);
		}
		
		try {
			Parent root = (Parent) FXMLLoader.load(getClass().getResource("/presentacion/vista/MenuPrincipal.fxml")); 
	
			primaryStage.setTitle("Menu Principal");
		    primaryStage.setScene(new Scene(root));
		    Application.setUserAgentStylesheet(STYLESHEET_CASPIAN);
		   
		    primaryStage.getScene().getStylesheets().add("/CSS/mycss.css");
		    primaryStage.sizeToScene();
		    primaryStage.show();
		    this.stage = primaryStage;

		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
