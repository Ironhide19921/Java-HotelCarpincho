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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import modelo.OrdenPedido;
import modelo.Validador;
import persistencia.dao.mysql.DAOSQLFactory;
import presentacion.controlador.ControladorAgregarOrdenPedido;

public class ControladorABMOrdenPedido implements Initializable{
	
	@FXML private TableView<OrdenPedidoDTO> tablaOrdenPedidos;
	@FXML private ObservableList<OrdenPedidoDTO> listaOrdenPedidos;
	@FXML private Button btnAgregarPedido;
	@FXML private Button btnEliminarPedido;
	@FXML private Button btnRefrescar;
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

}
