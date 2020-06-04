package presentacion.controlador;

import java.math.BigDecimal;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import dto.CategoriaCuartoDTO;
import dto.CategoriaEventoDTO;
import dto.ClienteDTO;
import dto.CuartoDTO;
import dto.ReservaEventoConNombresDTO;
import dto.ReservaEventoDTO;
import dto.ReservaEventoDTO.EstadoReserva;
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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import modelo.CategoriaCuarto;
import modelo.CategoriaEvento;
import modelo.Cliente;
import modelo.ReservaEvento;
import persistencia.dao.interfaz.ClienteDAO;
import persistencia.dao.mysql.DAOSQLFactory;
import presentacion.vista.FxmlLoader;

public class ControladorABMReservaEvento implements Initializable{
	
	@FXML TableView<ReservaEventoConNombresDTO> tablaReservasCliente;
	@FXML private TableColumn<ReservaEventoConNombresDTO, Integer> idreserva;
	@FXML private TableColumn<ReservaEventoConNombresDTO, String> categoria;
	@FXML private TableColumn<ReservaEventoConNombresDTO, BigDecimal> senia;
	@FXML private TableColumn<ReservaEventoConNombresDTO, BigDecimal> montototal;
	@FXML private TableColumn<ReservaEventoConNombresDTO, Timestamp> inicio;
	@FXML private TableColumn<ReservaEventoConNombresDTO, Timestamp> fin;
	@FXML private TableColumn<ReservaEventoConNombresDTO, String> estado;
	@FXML private Button btnAgregar;
	@FXML private Button btnEditar;
	@FXML private Button btnRefrescar;
	@FXML private Button btnVolver;
	@FXML private ObservableList<ReservaEventoConNombresDTO> activeSession;
	@FXML public TextField texto;
	private ReservaEvento reservaEvento;
	private ArrayList<Integer> listaIdReservaEventos;
	private Alert alert;
	private int clienteActual;
	@FXML private BorderPane panelActual;
	private Cliente cliente;
	private CategoriaEvento categoriaEvento;
	private CategoriaEventoDTO categoriaActual;
	private ClienteDTO clienteSeleccionadoActual;
	private ReservaEventoDTO reservaEventoActual;
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		if(clienteActual != 0) {
			this.reservaEvento = new ReservaEvento(new DAOSQLFactory());
			this.cliente = new Cliente(new DAOSQLFactory());
			this.categoriaEvento = new CategoriaEvento(new DAOSQLFactory());
			activeSession = FXCollections.observableArrayList();
			tablaReservasCliente.getItems().clear();
			cargarColumnas();
			refrescarTabla();
			getNuevasReservas();
		}
	}
	
	void initData(int customer) {
		this.clienteActual = customer;
		initialize(null, null);
	}
	
	private void cargarColumnas() {
		
		idreserva.setCellValueFactory(new PropertyValueFactory<>("idReservaEvento"));
		categoria.setCellValueFactory(new PropertyValueFactory<>("nombreCategoriaEvento"));			
		senia.setCellValueFactory(new PropertyValueFactory<>("Senia"));
		montototal.setCellValueFactory(new PropertyValueFactory<>("MontoTotal"));
		inicio.setCellValueFactory(new PropertyValueFactory<>("FechaInicioReserva"));
		fin.setCellValueFactory(new PropertyValueFactory<>("FechaFinReserva"));
		estado.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEstado().toString()));
	}
	
	
	@FXML
	private void refrescarTabla(){
		crearTabla(getNuevasReservas());
	}
	
	private ObservableList<ReservaEventoConNombresDTO> getNuevasReservas(){
		List<ReservaEventoDTO> reservas = this.reservaEvento.obtenerReservasEvento();
		
		clienteSeleccionadoActual = this.cliente.getClientePorId(clienteActual);
		activeSession.clear();
 		for(ReservaEventoDTO r : reservas) {
			if(r.getIdCliente() == clienteActual) {
				
				reservaEventoActual = r;
				categoriaActual = categoriaEvento.obtenerCategoriaEventoPorId(r.getIdCategoriaEvento());
				ReservaEventoConNombresDTO.EstadoReserva estado = null;
				estado = dto.ReservaEventoConNombresDTO.EstadoReserva.valueOf(r.getEstado().name());
				ReservaEventoConNombresDTO reservaConNombres = new ReservaEventoConNombresDTO(r.getIdReservaEvento(), r.getIdCliente(), clienteSeleccionadoActual.getTipoDocumento(), clienteSeleccionadoActual.getNumeroDocumento(), clienteSeleccionadoActual.getNombre(), clienteSeleccionadoActual.getApellido(), 1, r.getIdSalon(), r.getIdCategoriaEvento(), categoriaActual.getNombre(), r.getSenia(), r.getMontoReservaEvento(), r.getMontoTotal(), r.getFechaGeneracionReserva(), r.getFechaInicioReserva(), r.getFechaFinReserva(), r.getFechaIngreso(), r.getFechaEgreso(), r.getFormaPago(), r.getTipoTarjeta(), r.getNumeroTarjeta(), r.getFechaVencTarjeta(), r.getCodSeguridadTarjeta(), estado, r.getObservaciones());
				activeSession.add(reservaConNombres);
			}
		}
		
		return activeSession;
	}
	
//	private ObservableList<ReservaEventoDTO> getAllReservasEvento() {
//		List<ReservaEventoDTO> reservas = this.reservaEvento.obtenerReservasEvento();
//		activeSession.clear();
//		for(ReservaEventoDTO r : reservas) {
//			if(r.getIdCliente() == clienteActual) {
//				activeSession.add(r);	
//			}
//		}
//		return activeSession;
//	}
	
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
				scene2Controller.initData(clienteActual);
				scene2Controller.setVisibilityBtnAgregarReservaEvento(true);
				scene2Controller.setDisableBtnAgregarReservaEvento(false);
				scene2Controller.setVisibilityBtnModificarReservaEvento(false);
				scene2Controller.setDisableBtnModificarReservaEvento(true);		
				scene2Controller.setVisibilityFechaIngresoReservaEvento(false);
				scene2Controller.setDisableFechaIngresoReservaEvento(true);	
				scene2Controller.setVisibilityFechaEgresoReservaEvento(false);
				scene2Controller.setDisableFechaEgresoReservaEvento(true);	
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

				scene2Controller.setearCamposPantalla(tablaReservasCliente.getSelectionModel().getSelectedItem());
				scene2Controller.setVisibilityBtnAgregarReservaEvento(false);
				scene2Controller.setDisableBtnAgregarReservaEvento(true);
				scene2Controller.setVisibilityBtnModificarReservaEvento(true);
				scene2Controller.setDisableBtnModificarReservaEvento(false);		
				scene2Controller.setVisibilityFechaIngresoReservaEvento(true);
				scene2Controller.setDisableFechaIngresoReservaEvento(false);	
				scene2Controller.setVisibilityFechaEgresoReservaEvento(true);
				scene2Controller.setDisableFechaEgresoReservaEvento(false);	
				primaryStage.setScene(new Scene(root));
				primaryStage.setTitle("Modificar una reserva de evento");
				primaryStage.sizeToScene();
				primaryStage.show();
		       
		     } catch(Exception e) { 
		      e.printStackTrace(); 
		     } 
	    }
	 
	
	@FXML
	private void volver() {
		try {
			FxmlLoader fxmlLoader = new FxmlLoader();
			Pane view	= fxmlLoader.getPage("VentanaMenuReservaEvento");
			panelActual.setCenter(view);
			panelActual.setTop(null);
			panelActual.setBottom(null);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	

	public void setCliente(int idcliente) {
		this.clienteActual = idcliente;
	}
	
	public int getCliente() {
		return this.clienteActual;
	}
}
