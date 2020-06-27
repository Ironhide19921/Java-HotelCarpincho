package presentacion.controlador;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import dto.ProductoDTO;
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
import javafx.stage.Stage;
import modelo.Producto;
import modelo.Validador;
import persistencia.dao.mysql.DAOSQLFactory;
import presentacion.controlador.ControladorAgregarProducto;

public class ControladorABMProducto implements Initializable{
		
	@FXML private TableView<ProductoDTO> tablaProductos;
	@FXML private ObservableList<ProductoDTO> listaProductos;
	@FXML private Button btnAgregarProd;
	@FXML private Button btnEditarProd;
	@FXML private Button btnBorrarProd;	
	@FXML private Button btnBuscarProd;
	@FXML private Button btnRefrescar;
	@FXML private TableColumn id;
	@FXML private TableColumn nombre;
	@FXML private TableColumn precio;
	@FXML private TableColumn descripcion;
	@FXML private TableColumn tipo;
	@FXML private TableColumn proveedor;
	@FXML private TextField txtBuscarProd;
		  private Producto producto;
		  
		  public static Stage AgregarProductoStage = new Stage();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.producto = new Producto(new DAOSQLFactory());
		listaProductos = FXCollections.observableArrayList();
		tablaProductos.getItems().clear();
		cargarColumnas();
		refrescarTablaProductos();		
	}
	
	private void cargarColumnas() {
		id.setCellValueFactory(new PropertyValueFactory("idProducto"));
		nombre.setCellValueFactory(new PropertyValueFactory("nombre"));		
		precio.setCellValueFactory(new PropertyValueFactory("precio"));
		descripcion.setCellValueFactory(new PropertyValueFactory("descripcion"));
		tipo.setCellValueFactory(new PropertyValueFactory("tipo"));
		proveedor.setCellValueFactory(new PropertyValueFactory("proveedor"));		
	}
	
	@FXML
	private void refrescarTablaProductos() {
		//crearTabla(listaProductos);
		txtBuscarProd.setText("");
		crearTabla(getAllProductos());
	}

	private ObservableList<ProductoDTO> getAllProductos() {
		List<ProductoDTO> productos = this.producto.obtenerProductos();
		listaProductos.clear();
 		for(ProductoDTO p : productos) {
 			listaProductos.add(p);
 		}
 		return listaProductos;
	}

	private void crearTabla(ObservableList<ProductoDTO> listaProd) {
		tablaProductos.setItems(listaProd);
		tablaProductos.setEditable(true);
	}
	
	@FXML
	private void agregarProducto() throws Exception {
		try {
			//Stage primaryStage = new Stage(); 
	 		URL fxml = getClass().getClassLoader().getResource("presentacion/vista/VentanaAgregarProducto.fxml");
			FXMLLoader fxmlLoader = new FXMLLoader(fxml);
			Parent root = (Parent) fxmlLoader.load();
			AgregarProductoStage.setScene(new Scene(root));   
			ControladorAgregarProducto scene2Controller = fxmlLoader.getController();
			scene2Controller.setVisibilityBtnAgregarProd(true);
			scene2Controller.setVisibilityBtnEditarProd(false);
		
			AgregarProductoStage.setTitle("Agregar Producto");
			AgregarProductoStage.sizeToScene();
			AgregarProductoStage.show();
	       
	    } catch(Exception e) {
	    	e.printStackTrace(); 
	    } 
   }
	
	@FXML
    private void editarProducto(ActionEvent event) throws Exception {
		if(tablaProductos.getSelectionModel().getSelectedItem() == null) {
			Validador.mostrarMensaje("Seleccione un producto para editarlo.");
			return;
		}
		try {
			//Stage primaryStage = new Stage(); 
	 		URL fxml = getClass().getClassLoader().getResource("presentacion/vista/VentanaAgregarProducto.fxml");
			FXMLLoader fxmlLoader = new FXMLLoader(fxml);
			Parent root = (Parent) fxmlLoader.load();
			AgregarProductoStage.setScene(new Scene(root));   
			ControladorAgregarProducto scene2Controller = fxmlLoader.getController();
			ProductoDTO productoSeleccionado = tablaProductos.getSelectionModel().getSelectedItem();
			scene2Controller.setearCamposPantalla(productoSeleccionado);
		    scene2Controller.setVisibilityBtnAgregarProd(false);
		    scene2Controller.setVisibilityBtnEditarProd(true);
		    AgregarProductoStage.setTitle("Modificar producto");
		    AgregarProductoStage.sizeToScene();
		    AgregarProductoStage.show(); 
	       
	    } catch(Exception e) {
	    	e.printStackTrace(); 
	    } 
    }
	
	@FXML
	private void borrarProducto() throws Exception{
		if(tablaProductos.getSelectionModel().getSelectedItem() == null) {
			Validador.mostrarMensaje("Seleccione un producto para eliminarlo.");
			return;
		}
		try{
			ProductoDTO productoSeleccionado = tablaProductos.getSelectionModel().getSelectedItem();
			this.producto.borrarProducto(productoSeleccionado);
			refrescarTablaProductos();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	private void buscarProductos() {
		crearTabla(traerProductos());
	}

	@FXML
	private ObservableList<ProductoDTO> traerProductos() {
		List<ProductoDTO> productos = this.producto.buscarProductos(txtBuscarProd.getText().toString());
		listaProductos.clear();
 		for(ProductoDTO p : productos) {
 			listaProductos.add(p);
 		}
 		return listaProductos;
	}

}
