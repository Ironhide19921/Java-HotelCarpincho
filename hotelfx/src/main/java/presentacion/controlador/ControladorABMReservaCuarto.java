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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
	//@FXML private TableColumn fechaOut;
	@FXML private TableColumn fechaIngreso;
	@FXML private TableColumn fechaEgreso;
	
	private Cliente clientes;
	private ReservaCuarto reserva;
	private Cuarto cuartos;
	private FxmlLoader fxml;
	public static Stage primaryStage;
	private ControladorLogin controladorLogin;
	
	//public static Stage AgregarReservaStage = new Stage();

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
		cargarIconos();
	}

	
	@FXML void refrescarTabla() 
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
		//fechaOut.setCellValueFactory(new PropertyValueFactory("fechaOut"));	
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
				controlador.setUsuario(controladorLogin.usuarioLogeado);
				controlador.enviarControlador(this);
				Timestamp timestamp = new Timestamp(System.currentTimeMillis());
				controlador.setFechaReserva(timestamp);
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
				 primaryStage.setScene(fxml.getScene("VentanaAgregarReservaCuarto1"));   
					FXMLLoader fxmlLoader = fxml.getFXMLLoader();		
					ControladorAgregarReservaCuarto1 controlador = fxmlLoader.getController();
					controlador.pasarIdReserva(this.tablaReservas.getSelectionModel().getSelectedItem().getReserva().getIdReserva());
					controlador.setearCampos(this.tablaReservas.getSelectionModel().getSelectedItem().getReserva());
					controlador.setearCamposModificar();
					controlador.habilitarComboEstados();
					controlador.enviarControlador(this);
					fxml.mostrarStage(primaryStage, "Modificar reserva de cuarto");
			 }
			 else {
				 Validador.mostrarMensaje("Debe seleccionar una reserva para modificarla.");
			 }
		       
		     } catch(Exception e) { 
		      e.printStackTrace(); 
		     } 
	}
	
	public void consultarReservaCuarto() {
		 try { 
			 if(this.tablaReservas.getSelectionModel().getSelectedItem()!=null) {
				 primaryStage.setScene(fxml.getScene("VentanaABMOrdenPedido"));   
					FXMLLoader fxmlLoader = fxml.getFXMLLoader();	
					ControladorABMOrdenPedido controlador = fxmlLoader.getController();
					controlador.modificarBotones();
					controlador.enviarControlador(this);
					controlador.enviarIdReserva(this.tablaReservas.getSelectionModel().getSelectedItem().getIdReserva(),this.tablaReservas.getSelectionModel().getSelectedItem().getCuarto(),
					this.tablaReservas.getSelectionModel().getSelectedItem().getCliente());
					fxml.mostrarStage(primaryStage, "Consultar estado de reserva del cuarto.");
				 }
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
					this.reserva.modificarReservaCuarto(reserva);
				}else {
					reserva.setEstado(true);
					this.reserva.modificarReservaCuarto(reserva);
				}
		 }
		else {
			 Validador.mostrarMensaje("Debe seleccionar una reserva.");
		 }
	}


	public void modificarBotones() {
		this.btnAgregarReserva.setDisable(true);
		this.btnHabDesReserva.setDisable(true);
		this.btnModificarReserva.setDisable(true);
	}
	
	private void cargarIconos() {
		
		URL linkAgregar = getClass().getResource("/img/aceptar.png");
		URL linkModificar = getClass().getResource("/img/editar.png");
		URL linkBuscar = getClass().getResource("/img/buscar.png");
		URL linkHabilitar = getClass().getResource("/img/habilitar.png");
		URL linkSeleccionar = getClass().getResource("/img/seleccionar.png");
		
		Image imageAgregar = new Image(linkAgregar.toString(),24,24,false,true) ;
		Image imageModificar = new Image(linkModificar.toString(),24,24,false,true) ;
		Image imageBuscar = new Image(linkBuscar.toString(),24,24,false,true) ;
		Image imageHabilitar = new Image(linkHabilitar.toString(),24,24,false,true) ;
		Image imageSeleccionar = new Image(linkSeleccionar.toString(),24,24,false,true) ;
		
		this.btnAgregarReserva.setGraphic(new ImageView(imageAgregar));
		this.btnModificarReserva.setGraphic(new ImageView(imageModificar));
		this.btnHabDesReserva.setGraphic(new ImageView(imageHabilitar));
		this.btnBuscar.setGraphic(new ImageView(imageBuscar));
		this.btnConsultarReserva.setGraphic(new ImageView(imageSeleccionar));
		
	}
}
