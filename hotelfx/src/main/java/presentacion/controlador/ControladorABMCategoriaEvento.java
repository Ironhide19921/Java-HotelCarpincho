package presentacion.controlador;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import dto.CategoriaCuartoDTO;
import dto.CategoriaEventoDTO;
import dto.CuartoDTO;
import dto.ReservaEventoDTO;
import dto.SalonDTO;
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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import modelo.CategoriaCuarto;
import modelo.CategoriaEvento;
import modelo.Cuarto;
import modelo.ReservaEvento;
import modelo.Validador;
import persistencia.dao.mysql.DAOSQLFactory;

public class ControladorABMCategoriaEvento implements Initializable{
	@FXML private TableView<CategoriaEventoDTO> tablaCategoriasEvento;
	@FXML private Button btnAgregar;
	@FXML private Button btnEditar;
	@FXML private Button btnRefrescar;
	@FXML private Button btnEliminar;
	@FXML private TableColumn id;
	@FXML private TableColumn nombre;
	@FXML private TableColumn detalle;
	@FXML private ObservableList<CategoriaEventoDTO> activeSession;
	private CategoriaEvento categoriaEvento;
	private ArrayList<Integer> listaIdCategoriaEventos;
	private Alert alert;
	private ReservaEvento reservaEvento;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		this.categoriaEvento = new CategoriaEvento(new DAOSQLFactory());
		this.alert = new Alert(AlertType.INFORMATION);
		this.reservaEvento = new ReservaEvento(new DAOSQLFactory());
		
		activeSession = FXCollections.observableArrayList();
		tablaCategoriasEvento.getItems().clear();
		cargarColumnas();
		refrescarTabla();
		
		this.listaIdCategoriaEventos = new ArrayList<Integer>();
//		refrescarListaIdCategoriaEvento();
	}
	
	private void cargarColumnas() {
		id.setCellValueFactory(new PropertyValueFactory("ID"));		
		nombre.setCellValueFactory(new PropertyValueFactory("Nombre"));	
		detalle.setCellValueFactory(new PropertyValueFactory("Detalle"));
	}
	
	@FXML void refrescarTabla(){
 		crearTabla(getAllCategoriasEvento());
	}
	
	@FXML
    private void addCategoria(ActionEvent event) throws Exception 
    {
	     try { 
		    Stage primaryStage = new Stage(); 
	 		URL fxml = getClass().getClassLoader().getResource("presentacion/vista/VentanaAgregarCategoriaEvento.fxml");
			FXMLLoader fxmlLoader = new FXMLLoader(fxml);
			Parent root = (Parent) fxmlLoader.load();
			primaryStage.setScene(new Scene(root));   
			primaryStage.getScene().getStylesheets().add("/CSS/mycss.css");
			ControladorAgregarCategoriaEvento scene2Controller = fxmlLoader.getController();
			scene2Controller.setVisibilityBtnAgregarCategoriaEvento(true);
			scene2Controller.setDisableBtnAgregarCategoriaEvento(false);
			scene2Controller.setVisibilityBtnModificarCategoriaEvento(false);
			scene2Controller.setDisableBtnModificarCategoriaEvento(true);		 
			scene2Controller.enviarControlador(this);
			primaryStage.setTitle("Agregar categoria de evento");
			primaryStage.sizeToScene();
			primaryStage.show(); 
	       
	     } catch(Exception e) { 
	      e.printStackTrace(); 
	     } 
    }
	@FXML
    private void editCategoria(ActionEvent event) throws Exception 
    {
	     try { 
		    Stage primaryStage = new Stage(); 
	 		URL fxml = getClass().getClassLoader().getResource("presentacion/vista/VentanaAgregarCategoriaEvento.fxml");
			FXMLLoader fxmlLoader = new FXMLLoader(fxml);
			Parent root = (Parent) fxmlLoader.load();
			primaryStage.setScene(new Scene(root));  
			primaryStage.getScene().getStylesheets().add("/CSS/mycss.css");
			ControladorAgregarCategoriaEvento scene2Controller = fxmlLoader.getController();
			scene2Controller.setVisibilityBtnAgregarCategoriaEvento(false);
			scene2Controller.setDisableBtnAgregarCategoriaEvento(true);
			scene2Controller.setVisibilityBtnModificarCategoriaEvento(true);
			scene2Controller.setDisableBtnModificarCategoriaEvento(false);		 
			scene2Controller.enviarControlador(this);
			scene2Controller.setearCamposPantalla(tablaCategoriasEvento.getSelectionModel().getSelectedItem());
			primaryStage.setTitle("Modificar categoria de evento");
			primaryStage.sizeToScene();
			primaryStage.show(); 
	       
	     } catch(Exception e) { 
	      e.printStackTrace(); 
	     } 
    }
	 
	@FXML
    private void eliminarCategoriaEvento(ActionEvent event) throws Exception 
    {
	     try {	    	 
	    	List<ReservaEventoDTO> reservas = this.reservaEvento.obtenerReservasEvento();
	    	 for(ReservaEventoDTO r : reservas) {
	    		if(tablaCategoriasEvento.getSelectionModel().getSelectedItem().getId() == r.getIdCategoriaEvento()){
	    			Validador.mostrarMensaje("Categoria utilizada en una reserva, no se puede borrar");
	 	    		return;
	 	    	}
	    	 }
	    	 CategoriaEventoDTO categoriaEvento =  tablaCategoriasEvento.getSelectionModel().getSelectedItem();
    		 this.categoriaEvento.borrarCategoriaEvento(categoriaEvento);
 	    	 refrescarTabla();
	    	 
	    	
	     } catch(Exception e) { 
	      e.printStackTrace(); 
	     } 
    }
	
	private void crearTabla(ObservableList<CategoriaEventoDTO> lista) {
		tablaCategoriasEvento.setItems(lista);
		tablaCategoriasEvento.setEditable(true);
	 }
	
	private ObservableList<CategoriaEventoDTO> getAllCategoriasEvento() {
		List<CategoriaEventoDTO> categoriasEvento = this.categoriaEvento.obtenerCategoriasEvento();
		activeSession.clear();
 		for(CategoriaEventoDTO cE : categoriasEvento) {
 			activeSession.add(cE);
 		}
 		return activeSession;
	}
	
	public TableView<CategoriaEventoDTO> getTablaCategoriasEvento() {
		return tablaCategoriasEvento;
	}

	public void setTablaCategoriaEvento(TableView<CategoriaEventoDTO> tablaCategoriasEvento) {
		this.tablaCategoriasEvento = tablaCategoriasEvento;
	}
}
