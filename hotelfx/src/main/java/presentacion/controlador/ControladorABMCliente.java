package presentacion.controlador;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


import dto.ClienteDTO;
import dto.ReservaCuartoDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.Main;
import modelo.Cliente;
import modelo.ReservaCuarto;
import modelo.Validador;
import persistencia.conexion.Conexion;
import persistencia.dao.mysql.DAOSQLFactory;
import presentacion.vista.FxmlLoader;


public class ControladorABMCliente implements Initializable{
	//ventana mostrar cliente
	@FXML
	private Button btnAddCliente;
	@FXML
	private Button btnEditCliente;
	@FXML
	private Button btnEditarCliente;
	@FXML 
	private Button btnBuscarCliente;
	@FXML 
	private Button btnHabilitaCliente;
	@FXML 
	private Button btnSeleccionarCliente;
	@FXML private Button btnVerReservaCuarto;
	@FXML 
	private Button btnCerrar;

	@FXML private Button btnReservaCuarto;

	@FXML
	private Button btnVerReservasCliente;

	@FXML 
	private Button btnVerEncuesta;
	@FXML 
	private TextField ingresarCliente;
	@FXML 
	private TableView<ClienteDTO> tablaPersonas;
	
	@FXML 
	private ObservableList<ClienteDTO> activeSession;
	@FXML 
	private VBox fondoTabla;
	@FXML 
	private TableColumn nombre;
	@FXML 
	private TableColumn apellido;
	@FXML 
	private TableColumn telefono;
	@FXML 
	private TableColumn email;
	@FXML 
	private TableColumn numeroDocumento;
	@FXML
	private	TableColumn estado  ;
	@FXML
	private	TableColumn idCliente;
	@FXML private BorderPane panelActual;
	private Cliente cliente;
	private Validador validador;
	private ReservaCuartoDTO reserva;
	private ReservaCuarto reservas;
	private FxmlLoader fxml;
	private Stage primaryStage;
	
	
	private Alert alert;
	
	// funcion onload
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		primaryStage = new Stage(); 
		fxml = new FxmlLoader();
		this.btnSeleccionarCliente.setVisible(false);
		this.reservas = new ReservaCuarto(new DAOSQLFactory());
		this.cliente = new Cliente(new DAOSQLFactory());
		activeSession = FXCollections.observableArrayList();
		tablaPersonas.getItems().clear();
		this.alert = new Alert(AlertType.INFORMATION);
		cargarColumnas();
		refrescarTabla();
	}

	private void cargarColumnas() {
		nombre.setCellValueFactory(new PropertyValueFactory("nombre"));		
		apellido.setCellValueFactory(new PropertyValueFactory("apellido"));	
		email.setCellValueFactory(new PropertyValueFactory("email"));
		telefono.setCellValueFactory(new PropertyValueFactory("telefono"));
		estado.setCellValueFactory(new PropertyValueFactory("estado"));
		idCliente.setCellValueFactory(new PropertyValueFactory("idCliente"));	
		numeroDocumento.setCellValueFactory(new PropertyValueFactory("numeroDocumento"));
	}

	 @FXML
	    private void addCliente(ActionEvent event) throws Exception 
	    {
		     try { 
				primaryStage.setScene(fxml.getScene("VentanaAgregarCliente"));   
				FXMLLoader fxmlLoader = fxml.getFXMLLoader();
				ControladorAgregarCliente controlador = fxmlLoader.getController();
				controlador.setVisibilityBtnAgregarCliente(true);
				controlador.setDisableBtnAgregarCliente(false);
				controlador.setVisibilityBtnModificarCliente(false);
				controlador.setDisableBtnModificarCliente(true);		 
				fxml.mostrarStage(primaryStage, "Agregar cliente");
		     } catch(Exception e) { 
		      e.printStackTrace(); 
		     } 
	    }
	 
	 @FXML
	    private void editarCliente(ActionEvent event) throws Exception 
	    {
		 	 if (tablaPersonas.getSelectionModel().getSelectedItem() == null) {
				validador.mostrarMensaje("Debes seleccionar un cliente de la lista para editar");
				return;
			 }
		     try { 	
				primaryStage.setScene(fxml.getScene("VentanaAgregarCliente"));   
				FXMLLoader fxmlLoader = fxml.getFXMLLoader();
				ControladorAgregarCliente controlador = fxmlLoader.getController();
				ClienteDTO clienteSeleccionado = tablaPersonas.getSelectionModel().getSelectedItem();
				controlador.setearCamposPantalla(clienteSeleccionado);
				controlador.setVisibilityBtnAgregarCliente(false);
				controlador.setVisibilityBtnModificarCliente(true);	 
				fxml.mostrarStage(primaryStage, "Modificar cliente");
		     } catch(Exception e) { 
		      e.printStackTrace(); 
		     } 
	    }
	 
	 
	 @FXML private void addReservaCuarto() throws Exception {
		 if (tablaPersonas.getSelectionModel().getSelectedItem() == null) {
				validador.mostrarMensaje("Debes seleccionar un cliente de la lista para editar");
				return;
			 }
		     try { 
				primaryStage.setScene(fxml.getScene("VentanaAgregarReservaCuarto1"));   
				FXMLLoader fxmlLoader = fxml.getFXMLLoader();
				ControladorAgregarReservaCuarto1 scene2Controller = fxmlLoader.getController();
				//obtengo el cliente seleccionado en la tabla y se lo transfiero a la otra pantalla
				ClienteDTO clienteSeleccionado = tablaPersonas.getSelectionModel().getSelectedItem();
				scene2Controller.modificarCliente(clienteSeleccionado.getIdCliente());
				fxml.mostrarStage(primaryStage, "Agregar reserva de cuarto");
		     } catch(Exception e) { 
		      e.printStackTrace(); 
		     } 

	 }
	 
	 @FXML private void verReservasCuarto() throws Exception {
		 if (tablaPersonas.getSelectionModel().getSelectedItem() == null) {
				validador.mostrarMensaje("Debes seleccionar un cliente de la lista para editar");
				return;
		 }
		 List<ReservaCuartoDTO> reservas = this.reservas.buscarReservaCuartoCliente(tablaPersonas.getSelectionModel().getSelectedItem().getIdCliente());
		 if(reservas.size()>0) {
		    try { 
		    	primaryStage.setScene(fxml.getScene("VentanaABMReservaCuarto"));   
				FXMLLoader fxmlLoader = fxml.getFXMLLoader();
				ControladorABMReservaCuarto scene2Controller = fxmlLoader.getController();
				//obtengo el cliente seleccionado en la tabla y se lo transfiero a la otra pantalla
				scene2Controller.crearTabla(scene2Controller.getAllReservasCuartosPorCliente(reservas));
				fxml.mostrarStage(primaryStage, "Reservas de cuarto del cliente: " + tablaPersonas.getSelectionModel().getSelectedItem().getNombre() + " " + 
				tablaPersonas.getSelectionModel().getSelectedItem().getApellido() );
		    	 } 
		    catch(Exception e) { 
		   		      e.printStackTrace(); 
		    } 
		    return;
		 }
	   	 else {
		    validador.mostrarMensaje("No existen reservas vinculadas a este cliente");
			return;
	  	 }	    
	 }

	    
	 
	 @FXML
	    private void verEncuesta(ActionEvent event) throws Exception 
	    {
		 
		 	 if (tablaPersonas.getSelectionModel().getSelectedItem() == null) {
				validador.mostrarMensaje("Debes seleccionar un cliente de la lista para editar");
				return;
			 }
		     try { 
		    	Stage primaryStage = new Stage(); 
		 		URL fxml = getClass().getClassLoader().getResource("presentacion/vista/Encuesta.fxml");
				FXMLLoader fxmlLoader = new FXMLLoader(fxml);
				//cargo el objeto completo que incluye toda la escena y el controlador
				Parent root = (Parent) fxmlLoader.load();
		
				primaryStage.setScene(new Scene(root)); 
				primaryStage.getScene().getStylesheets().add("/CSS/mycss.css");
				//tomo el controlador
				ControladorVerEncuesta scene2Controller = fxmlLoader.getController();
				//obtengo el cliente seleccionado en la tabla y se lo transfiero a la otra pantalla
				ClienteDTO clienteSeleccionado = tablaPersonas.getSelectionModel().getSelectedItem();
				scene2Controller.setearCamposPantalla(clienteSeleccionado);
				primaryStage.setTitle("Ver Encuesta");
				primaryStage.sizeToScene();
				primaryStage.show(); 
		       
		     } catch(Exception e) { 
		      e.printStackTrace(); 
		     } 
	    }

	 
	 @FXML
	 public void seleccionarCliente() throws IOException
	 {	 
			primaryStage.setScene(fxml.getScene("VentanaAgregarReservaCuarto1"));   
			FXMLLoader fxmlLoader = fxml.getFXMLLoader();
			ControladorAgregarReservaCuarto1 scene2Controller = fxmlLoader.getController();
			scene2Controller.setearCampos(reserva);
			scene2Controller.modificarCliente(this.tablaPersonas.getSelectionModel().getSelectedItem().getIdCliente());
			Stage stage = (Stage) btnSeleccionarCliente.getScene().getWindow();
			stage.close();
			fxml.mostrarStage(primaryStage, "Agregar reserva de cuarto");	
	 }
	 
	 @FXML
		public void refrescarTabla(){
	 		crearTabla(getAllClientes());
		}

	private ObservableList<ClienteDTO> getAllClientes() {
		List<ClienteDTO> clientes = this.cliente.obtenerClientes();
		activeSession.clear();
	 	for(ClienteDTO c : clientes) {
	 		activeSession.add(c);
	 	}
	 	return activeSession;
	}
		
	 @FXML
	private void buscarCliente() {
	 	crearTabla(traerClientes());
	}

	 private void crearTabla(ObservableList<ClienteDTO> lista) {
		tablaPersonas.setItems(lista);
		tablaPersonas.setEditable(true);
	 }
	 
	 @FXML
	 private void habilitarCliente(){
		 if (tablaPersonas.getSelectionModel().getSelectedItem() == null) {
			validador.mostrarMensaje("Debes seleccionar un cliente de la lista para habilitar");
			return;
		 }
		 ClienteDTO clienteSeleccionado = tablaPersonas.getSelectionModel().getSelectedItem();
		 if(clienteSeleccionado.getEstado() == true) {
			 clienteSeleccionado.setEstado(false);
			 
			 this.cliente.modificarCliente(clienteSeleccionado);
			 refrescarTabla();
			 return;
		 }
		 else {
			 clienteSeleccionado.setEstado(true);
			 this.cliente.modificarCliente(clienteSeleccionado);
			 refrescarTabla();
			 return;
		 }
	
	 }

	 
	 @FXML
	 private void verReservasEvento(){
		try {
			if(this.tablaPersonas.getSelectionModel().getSelectedItem() != null) {
				int idClienteSeleccionado = this.tablaPersonas.getSelectionModel().getSelectedItem().getIdCliente();		

				FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("presentacion/vista/VentanaABMReservaEvento.fxml"));
				
			    Parent root = (Parent) fxmlLoader.load();
				ControladorABMReservaEvento controller = fxmlLoader.<ControladorABMReservaEvento>getController();
				controller.initData(idClienteSeleccionado);
				panelActual.setCenter(root);
				panelActual.setTop(null);
				panelActual.setBottom(null);
				panelActual.setLeft(null);
				panelActual.setRight(null);
			}
			else {
				mostrarMensaje("Debe seleccionar un cliente para ver sus reservas de evento");
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	 }
	 
	 private void mostrarMensaje(String mensaje) {
			alert.setTitle("Información");
			alert.setHeaderText(null);
			alert.setContentText(mensaje);

			alert.showAndWait();
	}
	 
	 @FXML
	 	private ObservableList<ClienteDTO> traerClientes() {
		 		
		 List<ClienteDTO> clientes = this.cliente.buscarClientes(ingresarCliente.getText().toString());
		 activeSession.clear();
	 		for(ClienteDTO c : clientes) {
	 			activeSession.add(c);
	 		}
	 		return activeSession;
		}

	 	public TableView<ClienteDTO> getTablaPersonas() {
			return tablaPersonas;
		}

		public void setTablaPersonas(TableView<ClienteDTO> tablaPersonas) {
			this.tablaPersonas = tablaPersonas;
		}
		 
		 @FXML
		 private void cerrarVentana() {
			 Stage stage = (Stage) btnCerrar.getScene().getWindow();
				stage.close();
		}
		 
		 @FXML 
		 public void modificarBotonesReserva() {
			 this.btnSeleccionarCliente.setVisible(true);
		 }
		 
		 public void datosReserva(ReservaCuartoDTO reserva) {
			 this.reserva = reserva;
		 }
		 

}
