package presentacion.controlador;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;

import java.util.List;

import java.util.ResourceBundle;

import org.joda.time.DateTime;

import dto.ClienteDTO;
import dto.EmailDTO;

import dto.PermisoPerfilDTO;
import javafx.application.Application;
import javafx.application.Platform;

import dto.EncuestaDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import main.Main;

import modelo.Cliente;

import modelo.Email;
import modelo.Encuesta;
import modelo.SendHttp;
import modelo.Validador;

import persistencia.conexion.Conexion;

import persistencia.dao.mysql.DAOSQLFactory;
import persistencia.dao.mysql.EncuestaDAOSQL;

import presentacion.vista.FxmlLoader;

public class ControladorMenuPrincipal extends Thread implements Initializable  {

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
	@FXML private Button btnAbrirVentanaReportes;
	@FXML private EmailDTO email;
	@FXML private Encuesta encuesta;
	@FXML private Cliente cliente;

	@FXML private static Button btnAbrirDivisas;
	
	@FXML private Button btnAbrirABMSalones;
	@FXML
	private Button btnAbrirABMCategoriaEvento;
	@FXML private Button btnAbrirOrdenPedidos;
	@FXML private BorderPane mainPane;
	@FXML private Pane center;
	@FXML private Pane pane;
	@FXML private ObservableList<ClienteDTO> clientesAencuestar;
	private List<EncuestaDTO> encuestasTodos;
	
	@FXML private MenuItem btnDeslogear;
	
	private ArrayList<Button> listaButtons;
	
	Date hoy = new Date(System.currentTimeMillis());
	
	ControladorBackup gestionBackup = new ControladorBackup();
	
	public static Stage loginStage = new Stage();
	public static Stage ConexionStage = new Stage();
	public static Stage DivisasStage = new Stage();

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		//Llamo al login
		verLogin();
		
		//Preparo los botones para recorrer en un orden especifico
		listaButtons = new ArrayList<Button>();
		listaButtons.add(0,btnAbrirABMUsuarios);
		listaButtons.add(1,btnAbrirABMCliente);
		listaButtons.add(2,btnAbrirABMCuartos);
		listaButtons.add(3,btnAbrirABMProductos);
		listaButtons.add(4,btnAbrirABMReservas);
		listaButtons.add(5,btnAbrirReservaEvento);
		listaButtons.add(6,btnAbrirImportar);
		listaButtons.add(7,btnAbrirCategoriaEvento);
		listaButtons.add(8,btnAbrirABMPerfiles);
		listaButtons.add(9,btnAbrirABMCategoriasCuartos);
		listaButtons.add(10,btnAbrirABMSalones);
		listaButtons.add(11,btnAbrirConfig);
		listaButtons.add(12,btnAbrirOrdenPedidos);
		listaButtons.add(13,btnAbrirVentanaBackup);
		listaButtons.add(14,btnAbrirVentanaReportes);
		
		for(int i=0; i<15; i++) {
			if(ControladorLogin.permisosPorId.contains(i+1)) {
				this.listaButtons.get(i).setDisable(false);
			}
		}

		this.email = new EmailDTO(0, null, null, null, null, null, null, null);
		this.cliente = new Cliente(new DAOSQLFactory());
		this.encuesta = new Encuesta(new DAOSQLFactory());
		clientesAencuestar = FXCollections.observableArrayList();
		this.clientesAencuestar = getClientesAencuestar();
		encuestasTodos = FXCollections.observableArrayList();
		this.encuestasTodos = encuesta.obtenerEncuestas();
		
		
		manejoEncuestas();
		
		//this.encuestas = (ArrayList<EncuestaDTO>) encuesta.obtenerEncuestas();
		email.start();
		
		if(EmailDTO.compararFechas(gestionBackup.fechaUltimoBackup(), hoy)>0){
			gestionBackup.backup();
		}

	}
	
	public void run() { 		
		manejoEncuestas();
	}

	private void manejoEncuestas() {
		for(EncuestaDTO e: encuestasTodos) {
			try {
				if(SendHttp.actualizarEncuestaRespondida(e.getRecipiente()).equals("si")) {
					encuesta.modificarEncuesta(e.getIdEncuesta());
				}
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		
		if(clientesAencuestar.size()>0) {
			try {
				String collector="";
				if(SendHttp.traerCantidadCollectores()==0) {
					collector = SendHttp.crearCollector();					
				}else {
					collector = SendHttp.traerIdPrimerCollector();
				}
								
				String mensaje =SendHttp.crearMensaje(collector);
				ArrayList<EncuestaDTO> recipientes = SendHttp.crearRecipientes(collector, mensaje, clientesAencuestar);
				for(EncuestaDTO e: recipientes) {
					encuesta.agregarEncuesta(e);
				}
				String envio = SendHttp.enviarEncuestasMail(collector, mensaje);
				
//				System.out.println(SendHttp.traerRespuestas("5541016993"));
//				ArrayList<RespuestaEncuestaDTO> resultado = SendHttp.consultarRespuestaEncuesta("5541016993");
//				for(RespuestaEncuestaDTO r: resultado) {
//					System.out.println(r.getIdPregunta());
//					for(String s: r.getListaRespuestas()) {
//						 System.out.println(s);
//					}
//				}
				
			} catch (Exception e) {
				//Validador.mostrarMensaje("Error en encuestas");
				e.printStackTrace();
			}
		}
	}


	private ObservableList<ClienteDTO> getClientesAencuestar() {
		List<ClienteDTO> clientes = this.cliente.obtenerClientesaEncuestar();
		if(clientes.size()==0) {
			Validador.mostrarMensaje("Sin clientes en condiciones para enviar encuestas");
		}
		clientesAencuestar.clear();
		for(ClienteDTO c : clientes) {
			clientesAencuestar.add(c);
		}
		return clientesAencuestar;
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
			    //Stage divisasStage = new Stage(); 
		 		URL fxml = getClass().getClassLoader().getResource("presentacion/vista/Divisas.fxml");
				FXMLLoader fxmlLoader = new FXMLLoader(fxml);
				Parent root = (Parent) fxmlLoader.load();
		
				DivisasStage.setScene(new Scene(root));
				DivisasStage.getScene().getStylesheets().add("/CSS/mycss.css");
				DivisasStage.setTitle("Conversión de divisas");
				DivisasStage.sizeToScene();
				DivisasStage.show();

		     } catch(Exception e) { 
		      e.printStackTrace(); 
		     } 
	    }
	}
	
	@FXML
	public void verLogin() {
		
		try {
			URL fxml = getClass().getClassLoader().getResource("presentacion/vista/VentanaLogin.fxml");
			FXMLLoader fxmlLoader = new FXMLLoader(fxml);
			Parent root = (Parent) fxmlLoader.load();

			loginStage.setScene(new Scene(root));
			loginStage.getScene().getStylesheets().add("/CSS/mycss.css");
			loginStage.setTitle("Pantalla de Login");
			loginStage.sizeToScene();
			loginStage.showAndWait();

		} catch (Exception e) {
			e.printStackTrace();
		}
	    
	}
	
	public void verVentanaConexion() {
		try {
			//Stage primaryStage = new Stage();
			URL fxml = getClass().getClassLoader().getResource("presentacion/vista/VentanaConexionConfig.fxml");
			FXMLLoader fxmlLoader = new FXMLLoader(fxml);
			Parent root = (Parent) fxmlLoader.load();

			ConexionStage.setScene(new Scene(root));
			ConexionStage.getScene().getStylesheets().add("/CSS/mycss.css");

			ConexionStage.setTitle("Configuracion de conexion");
			ConexionStage.sizeToScene();

			ConexionStage.showAndWait();

		} catch (Exception f) {
			f.printStackTrace();
		}
	}
	
	@FXML
	public void deslogear() throws IOException {
		Main.stage.close();
		ControladorABMProducto.AgregarProductoStage.close();
		
		try{
			ControladorABMReservaCuarto.primaryStage.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		
		
		//Desactivo todos los botones para preparar el reinicio
		for(Button boton : listaButtons) {
			boton.setDisable(true);
		}
		
		initialize(null,null);
		mainPane.setCenter(center);
		Main.stage.show();
	}
	
	@FXML
	public void verReportes() {
		try {
			 FxmlLoader fxmlLoader = new FxmlLoader();
			 Pane view	= fxmlLoader.getPage("VentanaReportes");
			 mainPane.setCenter(view);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
}
