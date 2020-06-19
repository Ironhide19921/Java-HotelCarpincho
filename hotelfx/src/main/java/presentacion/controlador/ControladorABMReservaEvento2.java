package presentacion.controlador;

import java.math.BigDecimal;
import java.net.URL;
import java.sql.Timestamp;
import java.util.List;
import java.util.ResourceBundle;

import dto.CategoriaEventoDTO;
import dto.ClienteDTO;
import dto.ReservaEventoConNombresDTO;
import dto.ReservaEventoDTO;
import javafx.beans.property.SimpleStringProperty;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import modelo.CategoriaEvento;
import modelo.Cliente;
import modelo.ReservaEvento;
import persistencia.dao.mysql.DAOSQLFactory;
import presentacion.vista.FxmlLoader;

public class ControladorABMReservaEvento2 implements Initializable{
	
	@FXML TableView<ReservaEventoConNombresDTO> tablaReservasCliente;
	@FXML private TableColumn<ReservaEventoConNombresDTO, Integer> idCliente;
	@FXML private TableColumn<ReservaEventoConNombresDTO, Integer> idreserva;
	@FXML private TableColumn<ReservaEventoConNombresDTO, String> categoria;
	@FXML private TableColumn<ReservaEventoConNombresDTO, BigDecimal> senia;
	@FXML private TableColumn<ReservaEventoConNombresDTO, BigDecimal> montototal;
	@FXML private TableColumn<ReservaEventoConNombresDTO, Timestamp> inicio;
	@FXML private TableColumn<ReservaEventoConNombresDTO, Timestamp> fin;
	@FXML private TableColumn<ReservaEventoConNombresDTO, String> estado;
	@FXML private TableColumn<ReservaEventoConNombresDTO, String> observacion;
	@FXML private Button btnAgregar;
	@FXML private Button btnEditar;
	@FXML private Button btnRefrescar;
	@FXML private Button btnFinalizarReserva;
	@FXML private Button btnIniciarReserva;
	@FXML private ObservableList<ReservaEventoConNombresDTO> activeSession;
	@FXML public Text texto;
	@FXML public BorderPane panelActual;
	
	private Cliente cliente;
	private CategoriaEvento categoriaEvento;
	private CategoriaEventoDTO categoriaActual;
	private ClienteDTO clienteSeleccionadoActual;
	private ReservaEventoDTO reservaEventoActual;
	private ReservaEvento reservaEvento;
	private Alert alert;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.reservaEvento = new ReservaEvento(new DAOSQLFactory());
		this.cliente = new Cliente(new DAOSQLFactory());
		this.categoriaEvento = new CategoriaEvento(new DAOSQLFactory());
		activeSession = FXCollections.observableArrayList();
		tablaReservasCliente.getItems().clear();
		cargarColumnas();
		refrescarTabla();
	}
	
	
	private void cargarColumnas() {
		idCliente.setCellValueFactory(new PropertyValueFactory<>("idCliente"));
		idreserva.setCellValueFactory(new PropertyValueFactory<>("idReservaEvento"));
		categoria.setCellValueFactory(new PropertyValueFactory<>("nombreCategoriaEvento"));			
		senia.setCellValueFactory(new PropertyValueFactory<>("Senia"));
		montototal.setCellValueFactory(new PropertyValueFactory<>("MontoTotal"));
		inicio.setCellValueFactory(new PropertyValueFactory<>("FechaInicioReserva"));
		fin.setCellValueFactory(new PropertyValueFactory<>("FechaFinReserva"));
		estado.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEstado().toString()));
		observacion.setCellValueFactory(new PropertyValueFactory<>("Observaciones"));
	}
	
	@FXML
	private void refrescarTabla(){
		crearTabla(getAllReservas());
	}

	private ObservableList<ReservaEventoConNombresDTO> getAllReservas(){
		List<ReservaEventoDTO> reservas = this.reservaEvento.obtenerReservasEvento();
		
		activeSession.clear();
 		for(ReservaEventoDTO r : reservas) {	
 			clienteSeleccionadoActual = this.cliente.getClientePorId(r.getIdCliente());
			reservaEventoActual = r;
			categoriaActual = categoriaEvento.obtenerCategoriaEventoPorId(r.getIdCategoriaEvento());
			ReservaEventoConNombresDTO.EstadoReserva estado = null;
			ReservaEventoConNombresDTO.FormaPago formaPago = null;
			ReservaEventoConNombresDTO.TipoTarjeta tipoTarjeta = null;
			formaPago = dto.ReservaEventoConNombresDTO.FormaPago.valueOf(r.getFormaPago().name());
			tipoTarjeta = dto.ReservaEventoConNombresDTO.TipoTarjeta.valueOf(r.getTipoTarjeta().name());
			estado = dto.ReservaEventoConNombresDTO.EstadoReserva.valueOf(r.getEstado().name());
			ReservaEventoConNombresDTO reservaConNombres = new ReservaEventoConNombresDTO(r.getIdReservaEvento(), r.getIdCliente(), clienteSeleccionadoActual.getTipoDocumento(), clienteSeleccionadoActual.getNumeroDocumento(), clienteSeleccionadoActual.getNombre(), clienteSeleccionadoActual.getApellido(), 1, r.getIdSalon(), r.getIdCategoriaEvento(), categoriaActual.getNombre(), r.getSenia(), r.getMontoReservaEvento(), r.getMontoTotal(), r.getFechaGeneracionReserva(), r.getFechaInicioReserva(), r.getFechaFinReserva(), r.getFechaIngreso(), r.getFechaEgreso(), formaPago, tipoTarjeta, r.getNumeroTarjeta(), r.getFechaVencTarjeta(), r.getCodSeguridadTarjeta(), estado, r.getObservaciones());
			activeSession.add(reservaConNombres);
		}
		
		return activeSession;
	}
	
	private void crearTabla(ObservableList<ReservaEventoConNombresDTO> lista) {
		tablaReservasCliente.setItems(lista);
		tablaReservasCliente.setEditable(true);	
	}
	
	

	 @FXML
	    private void addReservaEvento(ActionEvent event) throws Exception 
	    {
		     try { 
			    Stage primaryStage = new Stage(); 
		 		URL fxml = getClass().getClassLoader().getResource("presentacion/vista/VentanaAgregarReservaEvento.fxml");
				FXMLLoader fxmlLoader = new FXMLLoader(fxml);
				Parent root = (Parent) fxmlLoader.load();
				ControladorAgregarReservaEvento scene2Controller = fxmlLoader.<ControladorAgregarReservaEvento>getController();
				scene2Controller.setVisibilityComboClientes(true);
				scene2Controller.setVisibilityBtnAgregarReservaEvento(true);
				scene2Controller.setDisableBtnAgregarReservaEvento(false);
				scene2Controller.setVisibilityBtnModificarReservaEvento(false);
				scene2Controller.setDisableBtnModificarReservaEvento(true);	
				scene2Controller.initData(-1);
				primaryStage.setScene(new Scene(root));   
				primaryStage.setTitle("Agregar Reserva Evento");
				primaryStage.sizeToScene();
				primaryStage.show(); 
				
		     } catch(Exception e) { 
		      e.printStackTrace(); 
		     } 
	    }
	 
	 @FXML
	    private void editarReservaEvento(ActionEvent event) throws Exception 
	    {
		   try { 
			   Stage primaryStage = new Stage(); 
		 		URL fxml = getClass().getClassLoader().getResource("presentacion/vista/VentanaAgregarReservaEvento.fxml");
				FXMLLoader fxmlLoader = new FXMLLoader(fxml);
				Parent root = (Parent) fxmlLoader.load();
				ControladorAgregarReservaEvento scene2Controller = fxmlLoader.<ControladorAgregarReservaEvento>getController();
				int clienteActual = tablaReservasCliente.getSelectionModel().getSelectedItem().getIdCliente();
				scene2Controller.setearCamposPantalla(tablaReservasCliente.getSelectionModel().getSelectedItem());
				scene2Controller.setVisibilityBtnAgregarReservaEvento(false);
				scene2Controller.setDisableBtnAgregarReservaEvento(true);
				scene2Controller.setVisibilityBtnModificarReservaEvento(true);
				scene2Controller.setDisableBtnModificarReservaEvento(false);	
				scene2Controller.initData(clienteActual);
				primaryStage.setScene(new Scene(root));
				primaryStage.setTitle("Modificar una reserva de evento");
				primaryStage.sizeToScene();
				primaryStage.show();
		       
		     } catch(Exception e) { 
		      e.printStackTrace(); 
		     } 
	    }
	
	@FXML
	public void pruebaReserva() throws Exception{
		if(tablaReservasCliente.getSelectionModel().getSelectedItem() != null) {
			if(tablaReservasCliente.getSelectionModel().getSelectedItem().getEstado() == ReservaEventoConNombresDTO.EstadoReserva.EN_CURSO) {
				this.btnFinalizarReserva.setVisible(true);						
				this.btnIniciarReserva.setVisible(false);
			}
			else if(tablaReservasCliente.getSelectionModel().getSelectedItem().getEstado() == ReservaEventoConNombresDTO.EstadoReserva.PENDIENTE) {
				this.btnIniciarReserva.setVisible(true);
				this.btnFinalizarReserva.setVisible(false);
			}
			else {
				this.btnIniciarReserva.setVisible(false);
				this.btnFinalizarReserva.setVisible(false);
			}

			
		}
	}
	
	@FXML
	public void iniciarReserva() {
		int idReserva = tablaReservasCliente.getSelectionModel().getSelectedItem().getIdReservaEvento();
		Timestamp ingreso = new Timestamp(System.currentTimeMillis());;
		Timestamp egreso = null;
		checkincheckoutReserva(ingreso, egreso, idReserva);
		this.reservaEvento.cambiarEstadoReserva(idReserva, String.valueOf(dto.ReservaEventoConNombresDTO.EstadoReserva.EN_CURSO));
		crearTabla(getAllReservas());
	}
	
	

	@FXML
	public void finalizarReserva() {
		int idReserva = tablaReservasCliente.getSelectionModel().getSelectedItem().getIdReservaEvento();
		Timestamp ingreso = tablaReservasCliente.getSelectionModel().getSelectedItem().getFechaIngreso();
		Timestamp egreso = new Timestamp(System.currentTimeMillis());;
		checkincheckoutReserva(ingreso, egreso, idReserva);
		this.reservaEvento.cambiarEstadoReserva(idReserva, String.valueOf(dto.ReservaEventoConNombresDTO.EstadoReserva.FINALIZADO));
		crearTabla(getAllReservas());
	}
	
	private void checkincheckoutReserva(Timestamp ingreso, Timestamp egreso, int idReserva2) {
		this.reservaEvento.setearIngresoEgresoReservaEvento(ingreso, egreso, idReserva2);
	}
	
}
