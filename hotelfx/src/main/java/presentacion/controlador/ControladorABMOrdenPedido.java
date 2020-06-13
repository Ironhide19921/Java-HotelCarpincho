package presentacion.controlador;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import dto.OrdenPedidoDTO;
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
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import modelo.OrdenPedido;
import persistencia.dao.mysql.DAOSQLFactory;
import presentacion.controlador.ControladorAgregarOrdenPedido;

public class ControladorABMOrdenPedido implements Initializable{
	
	@FXML private TableView<OrdenPedidoDTO> tablaOrdenPedidos;
	@FXML private ObservableList<OrdenPedidoDTO> listaOrdenPedidos;
	@FXML private Button btnAgregarPedido;
	@FXML private Button btnEditarPedido;
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
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		this.ordenPedido = new OrdenPedido(new DAOSQLFactory());
		listaOrdenPedidos = FXCollections.observableArrayList();
		tablaOrdenPedidos.getItems().clear();
		this.panelDatos.setVisible(false);
		this.panelTotal.setVisible(false);
		cargarColumnas();
		refrescarTablaOrdenPedidos();
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
			ControladorAgregarOrdenPedido scene2Controller = fxmlLoader.getController();
			scene2Controller.setVisibilityBtnConfirmarPedido(true);
			scene2Controller.setVisibilityBtnEditarPedido(false);
			scene2Controller.setVisibilityBtnConfirmarGenerarTicket(true);
			primaryStage.setTitle("Agregar Orden Pedido");
			primaryStage.sizeToScene();
			primaryStage.show();
			
		} catch(Exception e) {
	    	e.printStackTrace(); 
	    } 
	}
	
	@FXML
	private void editarOrdenPedido()throws Exception {
		try {
			Stage primaryStage = new Stage(); 
	 		URL fxml = getClass().getClassLoader().getResource("presentacion/vista/VentanaAgregarOrdenPedido.fxml");
			FXMLLoader fxmlLoader = new FXMLLoader(fxml);
			Parent root = (Parent) fxmlLoader.load();
			primaryStage.setScene(new Scene(root));
			ControladorAgregarOrdenPedido scene2Controller = fxmlLoader.getController();
			OrdenPedidoDTO pedidoSeleccionado = tablaOrdenPedidos.getSelectionModel().getSelectedItem();
			scene2Controller.setearCampos(pedidoSeleccionado);
			scene2Controller.getCmbBoxClientes().setDisable(true);
			scene2Controller.setVisibilityBtnConfirmarPedido(false);
			scene2Controller.setVisibilityBtnEditarPedido(true);
			scene2Controller.setVisibilityBtnConfirmarGenerarTicket(false);
			scene2Controller.getPagoRestoran().setDisable(true);
			primaryStage.setTitle("Editar Orden Pedido");
			primaryStage.sizeToScene();
			primaryStage.show();
			
		} catch(Exception e) {
	    	e.printStackTrace(); 
	    } 
	}
	
	@FXML
	private void eliminarOrdenPedido() throws Exception {
		try{
			OrdenPedidoDTO productoSeleccionado = tablaOrdenPedidos.getSelectionModel().getSelectedItem();
			this.ordenPedido.eliminarOrdenPedido(productoSeleccionado);
			refrescarTablaOrdenPedidos();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void enviarIdReserva(int idCliente) {
		List<OrdenPedidoDTO> pedidosDelCliente = this.ordenPedido.buscarOrdenesPedidosPorReserva(idCliente);
		listaOrdenPedidos.clear();
		listaOrdenPedidos.addAll(pedidosDelCliente);
		this.tablaOrdenPedidos.setItems(listaOrdenPedidos);
	}

	public void modificarBotones() {
 
		this.btnAgregarPedido.setVisible(false);
		this.btnEditarPedido.setVisible(false);
		this.btnEliminarPedido.setVisible(false);
		this.btnRefrescar.setVisible(false);
		this.btnGenerarTicket.setVisible(true);
		this.panelDatos.setVisible(true);
		this.panelTotal.setVisible(true);
	}

}
