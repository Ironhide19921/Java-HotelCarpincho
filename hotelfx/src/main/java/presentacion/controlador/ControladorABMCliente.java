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
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.Main;
import modelo.Cliente;
import modelo.Validador;
import persistencia.conexion.Conexion;
import persistencia.dao.mysql.DAOSQLFactory;


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
	private Button btnDeshabilitarCliente;
	@FXML 
	private Button btnSeleccionarCliente;
	@FXML 
	private Button btnCerrar;
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
	private Cliente cliente;
	private Validador validador;
	private ReservaCuartoDTO reserva;
	
	// funcion onload
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.btnSeleccionarCliente.setVisible(false);
		this.cliente = new Cliente(new DAOSQLFactory());
		activeSession = FXCollections.observableArrayList();
		tablaPersonas.getItems().clear();
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
			    Stage primaryStage = new Stage(); 
		 		URL fxml = getClass().getClassLoader().getResource("presentacion/vista/VentanaAgregarCliente.fxml");
				FXMLLoader fxmlLoader = new FXMLLoader(fxml);
				Parent root = (Parent) fxmlLoader.load();
		
				primaryStage.setScene(new Scene(root));   
				primaryStage.getScene().getStylesheets().add("/CSS/mycss.css");
				ControladorAgregarCliente scene2Controller = fxmlLoader.getController();
				scene2Controller.setVisibilityBtnAgregarCliente(true);
				scene2Controller.setDisableBtnAgregarCliente(false);
				scene2Controller.setVisibilityBtnModificarCliente(false);
				scene2Controller.setDisableBtnModificarCliente(true);		 
				primaryStage.setTitle("Agregar Cliente");
				primaryStage.sizeToScene();
				primaryStage.show(); 
		       
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
		    	Stage primaryStage = new Stage(); 
		 		URL fxml = getClass().getClassLoader().getResource("presentacion/vista/VentanaAgregarCliente.fxml");
				FXMLLoader fxmlLoader = new FXMLLoader(fxml);
				//cargo el objeto completo que incluye toda la escena y el controlador
				Parent root = (Parent) fxmlLoader.load();
		
				primaryStage.setScene(new Scene(root)); 
				primaryStage.getScene().getStylesheets().add("/CSS/mycss.css");
				//tomo el controlador
				ControladorAgregarCliente scene2Controller = fxmlLoader.getController();
				//obtengo el cliente seleccionado en la tabla y se lo transfiero a la otra pantalla
				ClienteDTO clienteSeleccionado = tablaPersonas.getSelectionModel().getSelectedItem();
				scene2Controller.setearCamposPantalla(clienteSeleccionado);
				scene2Controller.setVisibilityBtnAgregarCliente(false);
				scene2Controller.setVisibilityBtnModificarCliente(true);	 
				primaryStage.setTitle("Modificar Cliente");
				primaryStage.sizeToScene();
				primaryStage.show(); 
		       
		     } catch(Exception e) { 
		      e.printStackTrace(); 
		     } 
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
	 private void deshabilitarCliente(){
		 if (tablaPersonas.getSelectionModel().getSelectedItem() == null) {
			validador.mostrarMensaje("Debes seleccionar un cliente de la lista para deshabilitar");
			return;
		 }
		 ClienteDTO clienteSeleccionado = tablaPersonas.getSelectionModel().getSelectedItem();
		 clienteSeleccionado.setEstado(false);
		 
		 this.cliente.modificarCliente(clienteSeleccionado);
		 refrescarTabla();
	 }
	 
	 
	 @FXML
	 private void habilitarCliente(){
		 if (tablaPersonas.getSelectionModel().getSelectedItem() == null) {
			validador.mostrarMensaje("Debes seleccionar un cliente de la lista para habilitar");
			return;
		 }
		 ClienteDTO clienteSeleccionado = tablaPersonas.getSelectionModel().getSelectedItem();
		 clienteSeleccionado.setEstado(true);
		 this.cliente.modificarCliente(clienteSeleccionado);
		 refrescarTabla();
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
		 
		 @FXML
		 public void seleccionarCliente() throws IOException {
			 
		 		URL fxml = getClass().getClassLoader().getResource("presentacion/vista/VentanaAgregarReservaCuarto.fxml");
				FXMLLoader fxmlLoader = new FXMLLoader(fxml);
				Parent root = (Parent) fxmlLoader.load(); 	
				ControladorAgregarReservaCuarto scene2Controller = fxmlLoader.getController();
				scene2Controller.setearCampos(reserva);
				scene2Controller.modificarCliente(this.tablaPersonas.getSelectionModel().getSelectedItem().getIdCliente());
				Stage stage = (Stage) btnSeleccionarCliente.getScene().getWindow();
				stage.close();
				Stage primaryStage = new Stage(); 
				primaryStage.setScene(new Scene(root));   
				primaryStage.getScene().getStylesheets().add("/CSS/mycss.css");
				primaryStage.setTitle("Agregar cuartos");
				primaryStage.sizeToScene();
				primaryStage.show(); 
					
		 }
}
