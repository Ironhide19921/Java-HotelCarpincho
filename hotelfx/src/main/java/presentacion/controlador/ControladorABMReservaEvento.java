package presentacion.controlador;

import java.math.BigDecimal;
import java.net.URL;
import java.nio.file.Paths;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import dto.CategoriaCuartoDTO;
import dto.CategoriaEventoDTO;
import dto.ClienteDTO;
import dto.ConfiguracionDTO;
import dto.CuartoDTO;
import dto.EmailDTO;
import dto.ReservaEventoConNombresDTO;
import dto.ReservaEventoDTO;
import dto.ReservaEventoDTO.EstadoReserva;
import dto.TicketDTO;
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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import modelo.CategoriaCuarto;
import modelo.CategoriaEvento;
import modelo.Cliente;
import modelo.ReservaEvento;
import modelo.Ticket;
import modelo.Validador;
import persistencia.dao.interfaz.ClienteDAO;
import persistencia.dao.mysql.DAOSQLFactory;
import presentacion.reportes.ReporteReservaEvento;
import presentacion.vista.FxmlLoader;

public class ControladorABMReservaEvento implements Initializable{
	
	@FXML TableView<ReservaEventoConNombresDTO> tablaReservasCliente;
	@FXML private TableColumn<ReservaEventoConNombresDTO, Integer> idreserva;
	@FXML private TableColumn<ReservaEventoConNombresDTO, Integer> idCliente;
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
	@FXML private Button btnVolver;
	@FXML private Button btnFinalizarReserva;
	@FXML private Button btnIniciarReserva;
	@FXML private ObservableList<ReservaEventoConNombresDTO> activeSession;
	@FXML public Text textoCliente;
	@FXML public Text nombreCliente;
	private ReservaEvento reservaEvento;
	private ArrayList<Integer> listaIdReservaEventos;
	private Alert alert;
	private int clienteActual = 0;
	@FXML public BorderPane panelActual;
	private Cliente cliente;
	private CategoriaEvento categoriaEvento;
	private CategoriaEventoDTO categoriaActual;
	private ClienteDTO clienteSeleccionadoActual;
	private ReservaEventoDTO reservaEventoActual;
	private TicketDTO ticketDTO;
	private Ticket ticket;
	@FXML private Button btnTicket;
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.reservaEvento = new ReservaEvento(new DAOSQLFactory());
		this.cliente = new Cliente(new DAOSQLFactory()); 
		this.categoriaEvento = new CategoriaEvento(new DAOSQLFactory());
		this.ticket = new Ticket(new DAOSQLFactory());
		this.alert = new Alert(AlertType.INFORMATION);
		activeSession = FXCollections.observableArrayList();
		tablaReservasCliente.getItems().clear();
		cargarColumnas();
		refrescarTabla();
		if(clienteActual != 0) {
			getNuevasReservas();
		}
	}
	
	void initData(int customer) {
		this.clienteActual = customer;
		initialize(null, null);
	}
	
	private void cargarColumnas() {
		if(this.clienteActual == 0) {
			textoCliente.setVisible(false);
			idCliente.setVisible(true);
			idCliente.setCellValueFactory(new PropertyValueFactory<>("idCliente"));
			btnVolver.setVisible(false);
		}else {
			btnVolver.setVisible(true);
			textoCliente.setVisible(true);
			idCliente.setVisible(false);
		}
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
	public void refrescarTabla(){
		if(this.clienteActual != 0) {
			crearTabla(getNuevasReservas());			
		}
		else {
			crearTabla(getAllReservas());
		}
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
	
	private ObservableList<ReservaEventoConNombresDTO> getNuevasReservas(){
		List<ReservaEventoDTO> reservas = this.reservaEvento.obtenerReservasEvento();
		
		clienteSeleccionadoActual = this.cliente.getClientePorId(clienteActual);
		this.nombreCliente.setText(clienteSeleccionadoActual.getNombre() + " " + clienteSeleccionadoActual.getApellido());
		activeSession.clear();
 		for(ReservaEventoDTO r : reservas) {
			if(r.getIdCliente() == clienteActual) {
				
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
				scene2Controller.enviarControlador(this);
				
				if(this.clienteActual == 0) {
					scene2Controller.setVisibilityComboClientes(true);
					scene2Controller.setVisibilityBtnAgregarReservaEvento(true);
					scene2Controller.setDisableBtnAgregarReservaEvento(false);
					scene2Controller.setVisibilityBtnModificarReservaEvento(false);
					scene2Controller.setDisableBtnModificarReservaEvento(true);	
					scene2Controller.initData(-1);
				}
				else {
					scene2Controller.setVisibilityBtnAgregarReservaEvento(true);
					scene2Controller.setDisableBtnAgregarReservaEvento(false);
					scene2Controller.setVisibilityBtnModificarReservaEvento(false);
					scene2Controller.setDisableBtnModificarReservaEvento(true);	
					scene2Controller.initData(clienteActual);	
				}
				
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
			   if(this.tablaReservasCliente.getSelectionModel().getSelectedItem() != null) {
				   Stage primaryStage = new Stage(); 
			 	   URL fxml = getClass().getClassLoader().getResource("presentacion/vista/VentanaAgregarReservaEvento.fxml");
				
			 	   FXMLLoader fxmlLoader = new FXMLLoader(fxml);
				   Parent root = (Parent) fxmlLoader.load();
				   ControladorAgregarReservaEvento scene2Controller = fxmlLoader.<ControladorAgregarReservaEvento>getController();
					scene2Controller.enviarControlador(this);
				   scene2Controller.setearCamposPantalla(tablaReservasCliente.getSelectionModel().getSelectedItem());
				   scene2Controller.setVisibilityBtnAgregarReservaEvento(false);
				   scene2Controller.setDisableBtnAgregarReservaEvento(true);
				   scene2Controller.setVisibilityBtnModificarReservaEvento(true);
				   scene2Controller.setDisableBtnModificarReservaEvento(false);		
				   primaryStage.setScene(new Scene(root));
				   primaryStage.setTitle("Modificar una reserva de evento");
				   primaryStage.sizeToScene();
				   primaryStage.show();
			   }
			   else {
				   Validador.mostrarMensaje("Debe seleccionar una reserva para poder editarla.");
			   }
		     } catch(Exception e) { 
		      e.printStackTrace(); 
		     } 
	    }
	 
	
	@FXML
	private void volver() {
		try {
			FxmlLoader fxmlLoader = new FxmlLoader();
			Pane view	= fxmlLoader.getPage("VentanaABMCliente");
			panelActual.setCenter(view);
			panelActual.setTop(null);
			panelActual.setBottom(null);
			panelActual.setLeft(null);
			panelActual.setRight(null);
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
		if(clienteActual == 0) {
			crearTabla(getAllReservas());
		}
		else {
			crearTabla(getNuevasReservas());	
		}
		this.btnIniciarReserva.setVisible(false);
	}
	
	

	@FXML
	public void finalizarReserva() {
		int idReserva = tablaReservasCliente.getSelectionModel().getSelectedItem().getIdReservaEvento();
		Timestamp ingreso = tablaReservasCliente.getSelectionModel().getSelectedItem().getFechaIngreso();
		Timestamp egreso = new Timestamp(System.currentTimeMillis());
		checkincheckoutReserva(ingreso, egreso, idReserva);
		this.reservaEvento.cambiarEstadoReserva(idReserva, String.valueOf(dto.ReservaEventoConNombresDTO.EstadoReserva.FINALIZADO));
		
		
		
		if(clienteActual == 0) {
			crearTabla(getAllReservas());
		}
		else {
			crearTabla(getNuevasReservas());	
		}
		this.btnFinalizarReserva.setVisible(false);
	}
	
	@FXML
	public void generarReporteReservaEvento() {
		if(this.tablaReservasCliente.getSelectionModel().getSelectedItem() != null) {
			if(tablaReservasCliente.getSelectionModel().getSelectedItem().getEstado() != ReservaEventoConNombresDTO.EstadoReserva.FINALIZADO) {
				Validador.mostrarMensaje("Para generar el ticket de una reserva primero debe estar FINALIZADA.");
			}else {
				List<ReservaEventoDTO> reservas = this.reservaEvento.obtenerReservasEvento();
				
				int idReserva = tablaReservasCliente.getSelectionModel().getSelectedItem().getIdReservaEvento();
				
				ReservaEventoDTO reservaSeleccionada = null;
				for(ReservaEventoDTO r : reservas) {
					if(r.getIdReservaEvento() == idReserva) {
						reservaSeleccionada = r;
					}
				}
				
				List<TicketDTO> tickets = this.ticket.obtenerClientes();
				TicketDTO ticketActual = null;
				for(TicketDTO t : tickets) {
					String[] parts = t.getPath().split("_");
					int idReservaDelTicket = Integer.parseInt(parts[1]);
					if(idReservaDelTicket == reservaSeleccionada.getIdReservaEvento() ) {
						ticketActual = t;
					}
				}
				String pathPdf = "";

				ReporteReservaEvento reporte = new ReporteReservaEvento(reservaSeleccionada, pathPdf);
				
				if(ticketActual == null) {
					Timestamp fechaActual = new Timestamp(System.currentTimeMillis());
					
					this.ticketDTO = new TicketDTO(0, reservaSeleccionada.getIdCliente(), reservaSeleccionada.getMontoTotal(), reservaSeleccionada.getObservaciones(), "", fechaActual);
					System.out.println(this.ticketDTO.getIdCliente() + this.ticketDTO.getDescripcion());
					this.ticket.agregarCliente(this.ticketDTO);
					int idTicket = ticket.obtenerIdTicketRecienInsertado(this.ticketDTO.getIdCliente());
					TicketDTO t = ticket.getTicket(idTicket);
					String path = "/tickets/evento/ticketEvento_"+ reservaSeleccionada.getIdReservaEvento() + "_" + t.getIdTicket() + ".pdf";
					pathPdf = ".//tickets//evento//ticketEvento_" + reservaSeleccionada.getIdReservaEvento() + "_" + t.getIdTicket() + ".pdf";
					System.out.println("Path de la bd : ->"+ path);
					System.out.println("Path del archivo : ->"+ pathPdf);
					ticket.modificarTicket(t, path);	
					reporte = new ReporteReservaEvento(reservaSeleccionada, pathPdf);
					reporte.guardarPdf();
					
					String pathMsj = "." + path; 
					ClienteDTO clienteActualSeleccionado = this.cliente.getClientePorId(tablaReservasCliente.getSelectionModel().getSelectedItem().getIdCliente());
					EmailDTO email = new EmailDTO();
					email.enviarMsjAdjunto("", pathMsj, clienteActualSeleccionado.getEmail(), "Ticket de reserva evento");
				}
				reporte.mostrar();
				
				
			}
		}
		else {
			Validador.mostrarMensaje("Debe seleccionar una reserva para generar un ticket");
		}
		
		
	}
	
	private void checkincheckoutReserva(Timestamp ingreso, Timestamp egreso, int idReserva2) {
		this.reservaEvento.setearIngresoEgresoReservaEvento(ingreso, egreso, idReserva2);
	}
	
	public void setCliente(int idcliente) {
		this.clienteActual = idcliente;
	}
	
	public int getCliente() {
		return this.clienteActual;
	}
}
