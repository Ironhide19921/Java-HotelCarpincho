package presentacion.controlador;

import java.net.URL;
import java.sql.Timestamp;
import java.util.List;
import java.util.ResourceBundle;

import dto.ClienteDTO;
import dto.CuartoDTO;
import dto.ReservaCuartoDTO;
import dto.TablaReservaDTO;
import dto.ReservaCuartoDTO.EstadoReserva;
import dto.ReservaCuartoDTO.FormaPago;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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
import javafx.stage.Stage;
import modelo.Cliente;
import modelo.Cuarto;
import modelo.ReservaCuarto;
import modelo.Validador;
import persistencia.dao.mysql.DAOSQLFactory;
import presentacion.vista.FxmlLoader;

public class ControladorABMReservaCuarto implements Initializable
{

	@FXML private Button btnAgregarReserva;
	@FXML private Button btnModificarReserva;
	@FXML private Button btnHabDesReserva;
	@FXML private Button btnConsultarReserva;
	@FXML private Button btnRefresh;
	@FXML private Button btnBuscar;
	@FXML private TextField ingresarCliente;
	@FXML private TableView<TablaReservaDTO> tablaReservas;
	@FXML private ObservableList<TablaReservaDTO> activeSession;
	@FXML private TableColumn nombreCliente;
	@FXML private TableColumn apellidoCliente;
	@FXML private TableColumn idReserva;
	@FXML private TableColumn estado;
	@FXML private TableColumn fechaReserva;
	@FXML private TableColumn fechaCheckIn;
	@FXML private TableColumn fechaOut;
	@FXML private TableColumn fechaIngreso;
	@FXML private TableColumn fechaEgreso;
	
	private Cliente clientes;
	private ReservaCuarto reserva;
	private Cuarto cuartos;
	private FxmlLoader fxml;
	private Stage primaryStage;
	

	@Override
	public void initialize(URL arg0, ResourceBundle arg1)
	{
		primaryStage = new Stage(); 
		fxml = new FxmlLoader();
		this.reserva = new ReservaCuarto(new DAOSQLFactory());
		this.clientes = new Cliente(new DAOSQLFactory());
		this.cuartos =  new Cuarto(new DAOSQLFactory());
		activeSession = FXCollections.observableArrayList();	
		cargarColumnas();
		refrescarTabla();
	}

	
	@FXML
	private void refrescarTabla() 
	{
		crearTabla(getAllReservasCuartos());
	}

	public void crearTabla(ObservableList<TablaReservaDTO> allReservasCuartos) 
	{
		tablaReservas.setItems(allReservasCuartos);
	}

	private ObservableList<TablaReservaDTO> getAllReservasCuartos() {
		List<ReservaCuartoDTO> reservas = this.reserva.obtenerReservasCuartos();
		activeSession.clear();
		for(ReservaCuartoDTO reserva : reservas) {
			ClienteDTO cliente = devolverCliente(reserva.getIdCliente());
			CuartoDTO cuarto = devolverCuarto(reserva.getIdCuarto());
			TablaReservaDTO tabla = new TablaReservaDTO(reserva,cliente,cuarto);
		activeSession.add(tabla);
		}
		 return activeSession;
	}
	
	@FXML
	public ObservableList<TablaReservaDTO> getAllReservasCuartosPorCliente(List<ReservaCuartoDTO> reservas) {
		activeSession.clear();
		for(ReservaCuartoDTO reserva : reservas) {
			ClienteDTO cliente = devolverCliente(reserva.getIdCliente());
			CuartoDTO cuarto = devolverCuarto(reserva.getIdCuarto());
			TablaReservaDTO tabla = new TablaReservaDTO(reserva,cliente,cuarto);
		activeSession.add(tabla);
		}
		 return activeSession;
	}
	
	
	
	private CuartoDTO devolverCuarto(Integer id) {
List<CuartoDTO> cuartos = this.cuartos.obtenerCuartos();
		
		for(CuartoDTO c : cuartos) {
			if(c.getId() == id) {
				return c;
			}
		}
		return null;
	}


	private ClienteDTO devolverCliente(Integer id) {
		List<ClienteDTO> clientes = this.clientes.obtenerClientes();
		
		for(ClienteDTO c : clientes) {
			if(c.getIdCliente() == id) {
				return c;
			}
		}
		return null;
	}
	private void cargarColumnas() 
	{
		idReserva.setCellValueFactory(new PropertyValueFactory("idReserva"));		
		nombreCliente.setCellValueFactory(new PropertyValueFactory("nombre"));	
		apellidoCliente.setCellValueFactory(new PropertyValueFactory("apellido"));
		estado.setCellValueFactory(new PropertyValueFactory("estado"));
		fechaReserva.setCellValueFactory(new PropertyValueFactory("fechaReserva"));
		fechaCheckIn.setCellValueFactory(new PropertyValueFactory("fechaCheckIn"));
		fechaOut.setCellValueFactory(new PropertyValueFactory("fechaOut"));	
		fechaIngreso.setCellValueFactory(new PropertyValueFactory("fechaIngreso"));
		fechaEgreso.setCellValueFactory(new PropertyValueFactory("fechaEgreso"));
	}
		
	@FXML
	public void addReservaCuarto() throws Exception  {
		 try { 
		
			 	primaryStage.setScene(fxml.getScene("VentanaAgregarReservaCuarto1"));   
				FXMLLoader fxmlLoader = fxml.getFXMLLoader();
				ControladorAgregarReservaCuarto1 controlador = fxmlLoader.getController();
				controlador.setCmbBoxEstados(EstadoReserva.PENDIENTE);
				//controlador.setCmbBoxFormaPago();
				Timestamp timestamp = new Timestamp(System.currentTimeMillis());
				controlador.setFechaReserva(timestamp);
				//controlador.setCmbBoxUsuarioFirst();
				controlador.setearCamposAgregar();
				fxml.mostrarStage(primaryStage,"Agregar reserva de cuarto");
		       
		     } catch(Exception e) { 
		      e.printStackTrace(); 
		     } 
	}
	
	@FXML
	public void modificarReservaCuarto() {
		 try { 
			 if(this.tablaReservas.getSelectionModel().getSelectedItem()!=null) {
				 Stage primaryStage = new Stage(); 
			 		URL fxml = getClass().getClassLoader().getResource("presentacion/vista/VentanaAgregarReservaCuarto1.fxml");
					FXMLLoader fxmlLoader = new FXMLLoader(fxml);
					Parent root = (Parent) fxmlLoader.load();
					primaryStage.setScene(new Scene(root));   
					primaryStage.getScene().getStylesheets().add("/CSS/mycss.css");
					ControladorAgregarReservaCuarto1 controlador = fxmlLoader.getController();
					controlador.pasarIdReserva(this.tablaReservas.getSelectionModel().getSelectedItem().getReserva().getIdReserva());
					controlador.setearCampos(this.tablaReservas.getSelectionModel().getSelectedItem().getReserva());
					
					primaryStage.setTitle("Modificar reserva de cuarto");
					primaryStage.sizeToScene();
					primaryStage.show(); 
			 }
			 else {
				 Validador.mostrarMensaje("Debe seleccionar una reserva.");
			 }
		       
		     } catch(Exception e) { 
		      e.printStackTrace(); 
		     } 
	}
	
	public void consultarReservaCuarto() {
		 try { 
			 if(this.tablaReservas.getSelectionModel().getSelectedItem()!=null) {
			    Stage primaryStage = new Stage(); 
		 		URL fxml = getClass().getClassLoader().getResource("presentacion/vista/VentanaAgregarReservaCuarto1.fxml");
				FXMLLoader fxmlLoader = new FXMLLoader(fxml);
				Parent root = (Parent) fxmlLoader.load();
				primaryStage.setScene(new Scene(root));   
				primaryStage.getScene().getStylesheets().add("/CSS/mycss.css");
				//ControladorAgregarReservaCuarto controlador = fxmlLoader.getController();
				//controlador.setearCampos(this.tablaReservas.getSelectionModel().getSelectedItem().getReserva());
				//controlador.modificarPantallaConsulta(this.tablaReservas.getSelectionModel().getSelectedItem().getReserva());
				
				primaryStage.setTitle("Consultar reserva de cuarto");
				primaryStage.sizeToScene();
				primaryStage.show(); }
				 else {
					 Validador.mostrarMensaje("Debe seleccionar una reserva.");
				 }
			       
		     } catch(Exception e) { 
		      e.printStackTrace(); 
		     } 
	}

	@FXML 
	public void habilitarDeshabilitarReserva()
	{
		 if(this.tablaReservas.getSelectionModel().getSelectedItem()!=null) {
				ReservaCuartoDTO reserva = this.tablaReservas.getSelectionModel().getSelectedItem().getReserva();
				if(reserva.getEstado() == true) {
					reserva.setEstado(false);
				}else {
					reserva.setEstado(true);
				}
		 }
		else {
			 Validador.mostrarMensaje("Debe seleccionar una reserva.");
		 }
	}
	
}
