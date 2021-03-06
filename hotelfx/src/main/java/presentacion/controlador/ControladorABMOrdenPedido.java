package presentacion.controlador;

import java.math.BigDecimal;
import java.net.URL;
import java.sql.Timestamp;
import java.util.List;
import java.util.ResourceBundle;

import dto.ClienteDTO;
import dto.CuartoDTO;
import dto.EmailDTO;
import dto.OrdenPedidoDTO;
import dto.ReservaCuartoDTO;
import dto.ReservaCuartoDTO.EstadoReserva;
import dto.TablaReservaDTO;
import dto.TicketDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import modelo.Cliente;
import modelo.OrdenPedido;
import modelo.ReservaCuarto;
import modelo.Ticket;
import modelo.Validador;
import persistencia.dao.mysql.DAOSQLFactory;
import presentacion.controlador.ControladorAgregarOrdenPedido;
import presentacion.reportes.ReportePedidoTicket;
import presentacion.reportes.ReporteTicketReserva;

public class ControladorABMOrdenPedido implements Initializable{
	
	@FXML private TableView<OrdenPedidoDTO> tablaOrdenPedidos;
	@FXML private ObservableList<OrdenPedidoDTO> listaOrdenPedidos;
	@FXML private Button btnAgregarPedido;
	@FXML private Button btnEliminarPedido;
	@FXML private Button btnRefrescar;
	@FXML private Button btnGenerarTicket;
	@FXML private Label labelCliente,labelCliente1,labelReserva,labelReserva1,
	labelClienteNombre,idCliente, idReserva, montoReserva;
	@FXML private Label resultadoTotal,totalLabel;
	@FXML private Pane panelDatos, panelTotal;
	@FXML private TableColumn id;
	@FXML private TableColumn idProducto;
	@FXML private TableColumn cantidad;
	@FXML private TableColumn total;
	private OrdenPedido ordenPedido;
	private Cliente cliente;
	private ReservaCuarto reservas;
	private BigDecimal montoTotal;
	private Ticket ticket;
	//son para el refrescar
	private ControladorABMReservaCuarto controladorABMReservaCuarto;
	private ControladorAgregarReservaCuarto1 controladorAgregarReservaCuarto1;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		this.ordenPedido = new OrdenPedido(new DAOSQLFactory());
		this.cliente = new Cliente(new DAOSQLFactory() );
		this.reservas = new ReservaCuarto(new DAOSQLFactory() );
		this.ticket = new Ticket(new DAOSQLFactory());
		listaOrdenPedidos = FXCollections.observableArrayList();
		tablaOrdenPedidos.getItems().clear();
		this.panelDatos.setVisible(false);
		this.panelTotal.setVisible(false);
		this.btnGenerarTicket.setVisible(false);
		cargarColumnas();
		refrescarTablaOrdenPedidos();
		cargarIconos() ;
	}

	private void cargarColumnas() {
		id.setCellValueFactory(new PropertyValueFactory("idOrdenPedido"));
		idProducto.setCellValueFactory(new PropertyValueFactory("idProducto"));
		cantidad.setCellValueFactory(new PropertyValueFactory("cantidad"));		
		total.setCellValueFactory(new PropertyValueFactory("precioTotal"));
	}
	
	@FXML
	public void refrescarTablaOrdenPedidos() {
		crearTabla(getAllOrdenPedidos());
	}

	private void crearTabla(ObservableList<OrdenPedidoDTO> listaOrdenPed) {
		tablaOrdenPedidos.setItems(listaOrdenPed);
		tablaOrdenPedidos.setEditable(true);
	}

	private ObservableList<OrdenPedidoDTO> getAllOrdenPedidos() {
		List<OrdenPedidoDTO> ordenPedidos = this.ordenPedido.obtenerOrdenesPedidos();
		listaOrdenPedidos.clear();
		for(OrdenPedidoDTO op : ordenPedidos) {
			listaOrdenPedidos.add(op);
		}
		return listaOrdenPedidos;
	}
	
	@FXML
	private void agregarOrdenPedido()throws Exception {
		try {
			Stage primaryStage = new Stage(); 
	 		URL fxml = getClass().getClassLoader().getResource("presentacion/vista/VentanaAgregarOrdenPedido.fxml");
			FXMLLoader fxmlLoader = new FXMLLoader(fxml);
			Parent root = (Parent) fxmlLoader.load();
			primaryStage.setScene(new Scene(root));
			Image ico = new Image("/img/hotel2.png");
			primaryStage.getIcons().add(ico);
			primaryStage.getScene().getStylesheets().add("/CSS/mycss.css");
			ControladorAgregarOrdenPedido scene2Controller = fxmlLoader.getController();
			scene2Controller.enviarControlador(this);
			scene2Controller.setVisibilityBtnConfirmarPedido(true);
			scene2Controller.setVisibilityBtnConfirmarGenerarTicket(true);
			primaryStage.setTitle("Agregar Orden Pedido");
			primaryStage.sizeToScene();
			primaryStage.show();
			
		} catch(Exception e) {
	    	e.printStackTrace(); 
	    } 
	}
	
	@FXML
	private void eliminarOrdenPedido() throws Exception {
		if(tablaOrdenPedidos.getSelectionModel().getSelectedItem() == null) {
			Validador.mostrarMensaje("Debe seleccionar un pedido para eliminarlo");
			return;
		}
		try{
			OrdenPedidoDTO productoSeleccionado = tablaOrdenPedidos.getSelectionModel().getSelectedItem();
			this.ordenPedido.eliminarOrdenPedido(productoSeleccionado);
			refrescarTablaOrdenPedidos();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void modificarBotones() {
 
		this.btnAgregarPedido.setVisible(false);
		this.btnEliminarPedido.setVisible(false);
		this.btnRefrescar.setVisible(false);
		this.btnGenerarTicket.setVisible(true);
		this.panelDatos.setVisible(true);
		this.panelTotal.setVisible(true);
	}
	
	private ClienteDTO devolverCliente(Integer id) {
		List<ClienteDTO> clientes = this.cliente.obtenerClientes();
		
		for(ClienteDTO c : clientes) {
			if(c.getIdCliente() == id) {
				return c;
			}
		}
		return null;
	}

	public void enviarIdReserva(int selectedItem, CuartoDTO cuartoDTO, ClienteDTO clienteDTO) {
		
		ReservaCuartoDTO reserva = this.reservas.obtenerReservaCuartoPorId(selectedItem);
		BigDecimal montoTotalReserva = reserva.getMontoReservaCuarto();
		BigDecimal montoSe??a = reserva.getSenia();
		if(reserva.getEstadoReserva().equals(EstadoReserva.FINALIZADO)) {
			//montoTotalReserva.add(montoSe??a.negate());
			List<OrdenPedidoDTO> pedidosDelCliente = this.ordenPedido.buscarOrdenesPedidosPorReserva(clienteDTO.getIdCliente());
			BigDecimal monto = BigDecimal.valueOf(0);
			listaOrdenPedidos.clear();
			listaOrdenPedidos.addAll(pedidosDelCliente);
			this.tablaOrdenPedidos.setItems(listaOrdenPedidos);
			this.idCliente.setText(clienteDTO.getIdCliente()+"");
			//consultar datos del cliente
			this.labelClienteNombre.setText(clienteDTO.getNombre() + " " + clienteDTO.getApellido());
			this.idReserva.setText(selectedItem+"");
			this.montoReserva.setText(montoTotalReserva+ "");
			
			for(OrdenPedidoDTO o : pedidosDelCliente) {
				
				monto = monto.add(o.getPrecioTotal());
			}
			monto = monto.add(montoTotalReserva);
			resultadoTotal.setText(monto+"");
		}
		else if(reserva.getEstadoReserva().equals(EstadoReserva.CANCELADO)) {
			//montoTotalReserva.add(montoSe??a.negate());
			List<OrdenPedidoDTO> pedidosDelCliente = this.ordenPedido.buscarOrdenesPedidosPorReserva(clienteDTO.getIdCliente());
			BigDecimal monto = BigDecimal.valueOf(0);
			listaOrdenPedidos.clear();
			listaOrdenPedidos.addAll(pedidosDelCliente);
			this.tablaOrdenPedidos.setItems(listaOrdenPedidos);
			this.idCliente.setText(clienteDTO.getIdCliente()+"");
			//consultar datos del cliente
			this.labelClienteNombre.setText(clienteDTO.getNombre() + " " + clienteDTO.getApellido());
			this.idReserva.setText(selectedItem+"");
			this.montoReserva.setText(montoSe??a+ "");
			
			for(OrdenPedidoDTO o : pedidosDelCliente) {
				
				monto = monto.add(o.getPrecioTotal());
			}
			monto = monto.add(montoSe??a);
			resultadoTotal.setText(monto+"");
		}else {
			List<OrdenPedidoDTO> pedidosDelCliente = this.ordenPedido.buscarOrdenesPedidosPorReserva(clienteDTO.getIdCliente());
			BigDecimal monto = BigDecimal.valueOf(0);
			listaOrdenPedidos.clear();
			listaOrdenPedidos.addAll(pedidosDelCliente);
			this.tablaOrdenPedidos.setItems(listaOrdenPedidos);
			this.idCliente.setText(clienteDTO.getIdCliente()+"");
			//consultar datos del cliente
			this.labelClienteNombre.setText(clienteDTO.getNombre() + " " + clienteDTO.getApellido());
			this.idReserva.setText(selectedItem+"");
			this.montoReserva.setText(montoTotalReserva+ "");
			
			for(OrdenPedidoDTO o : pedidosDelCliente) {
				
				monto = monto.add(o.getPrecioTotal());
			}
			monto = monto.add(montoTotalReserva);
			resultadoTotal.setText(monto+"");
		}
		
	}
	
	@FXML
	public void generarYEnviarTicket() {
		//datos del ticket
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		TicketDTO nuevoTicket = new TicketDTO(0, Integer.parseInt(idCliente.getText()),new BigDecimal(resultadoTotal.getText()), "descripcion", "path", timestamp);
		this.ticket.agregarCliente(nuevoTicket);
		int ultimoIdTicket = this.ticket.obtenerIdTicketRecienInsertado(nuevoTicket.getIdCliente());
		TicketDTO ticket = this.ticket.getTicket(ultimoIdTicket);
		String path = "/tickets/reserva/ticketReserva_" + idReserva + "_"+ ticket.getIdTicket() +".pdf";
		String pathPDF = ".//tickets//reserva//ticketReserva_" + idReserva + "_"+ ticket.getIdTicket() +".pdf";
		ReservaCuartoDTO reserva = this.reservas.obtenerReservaCuartoPorId(Integer.parseInt(idReserva.getText()));
		//datos del reporte
		
		reserva.setEstado(false);
		this.reservas.modificarReservaCuarto(reserva);
		ReporteTicketReserva reporte = new ReporteTicketReserva(Integer.parseInt(idReserva.getText()), pathPDF);
		reporte.mostrar();
		
		if(!verificarPath(path)) {
			this.ticket.modificarTicket(ticket, path);
			reporte.guardarPdf();
		}
		String pathMsj = "." + path; 
		ClienteDTO clienteActual = this.cliente.getClientePorId(Integer.parseInt(idCliente.getText()));
		EmailDTO email = new EmailDTO();
		email.enviarMsjAdjunto("", pathMsj, reserva.getEmailFacturacion(), "Ticket de reserva");
		Validador.mostrarMensaje("Su ticket ha sido creado con exito.");
		//elimina orden de pedido del cliente que acabo de cobrarle
		for(OrdenPedidoDTO o : tablaOrdenPedidos.getItems()) {
			if(	o.getIdCliente() == Integer.parseInt(idCliente.getText())){
				this.ordenPedido.eliminarOrdenPedido(o);
			}
		}
		cerrarVentanaAgregarY_GenerarTicket();
	}
	
	
	private boolean verificarPath(String path) {
		boolean pathRepetido = false;
		for(TicketDTO t : this.ticket.obtenerClientes()) {
			if(t.getPath().equalsIgnoreCase(path)) {
				pathRepetido = true;
			}
		}
		return pathRepetido;
	}
	
	@FXML
	private void cerrarVentanaAgregarY_GenerarTicket() {
		Stage stage = (Stage) btnGenerarTicket.getScene().getWindow();
		stage.close();
	}

	public void enviarControlador(ControladorABMReservaCuarto controladorABMReservaCuarto) {
		// TODO Auto-generated method stub
		this.controladorABMReservaCuarto = controladorABMReservaCuarto;
	}

	public void enviarControlador(ControladorAgregarReservaCuarto1 controladorAgregarReservaCuarto1) {
		// TODO Auto-generated method stub
		this.controladorAgregarReservaCuarto1 = controladorAgregarReservaCuarto1;
	}
	
private void cargarIconos() {
		
		URL linkAgregar = getClass().getResource("/img/aceptar.png");
		URL linkEliminar = getClass().getResource("/img/cancelar.png");
		URL linkGenerar = getClass().getResource("/img/descarga.png");
		
		Image imageAgregar = new Image(linkAgregar.toString(),24,24,false,true) ;
		Image imageEliminar = new Image(linkEliminar.toString(),24,24,false,true) ;
		Image imageGenerar = new Image(linkGenerar.toString(),24,24,false,true) ;

		this.btnAgregarPedido.setGraphic(new ImageView(imageAgregar));
		this.btnEliminarPedido.setGraphic(new ImageView(imageEliminar));
		this.btnGenerarTicket.setGraphic(new ImageView(imageGenerar));	
		
	}

	

}
