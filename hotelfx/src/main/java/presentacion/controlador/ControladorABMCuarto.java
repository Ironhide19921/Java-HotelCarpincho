package presentacion.controlador;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import dto.CuartoDTO;
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
import modelo.Cuarto;
import modelo.Validador;
import persistencia.dao.mysql.DAOSQLFactory;

public class ControladorABMCuarto implements Initializable
{
	@FXML private TableView<CuartoDTO> tablaCuartos;
	@FXML private Button btnAgregarCuarto;
	@FXML private Button btnEditar;
	@FXML private Button btnBuscar;
	@FXML private TableColumn id;
	@FXML private TableColumn piso;
	@FXML private TableColumn habitacion;
	@FXML private TableColumn capacidad;
	@FXML private TableColumn monto;
	@FXML private TableColumn montoSenia;
	@FXML private TableColumn estado;
	@FXML private ObservableList<CuartoDTO> activeSession;
	@FXML private TextField txtBuscar;
	@FXML private Button btnLimpiarFiltro;
	@FXML private Button btnHabilitarCuarto;
	@FXML private Button btnDeshabilitarCuarto;
		  private Cuarto cuarto;
		  
		  private Validador validador;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.cuarto = new Cuarto(new DAOSQLFactory());
		activeSession = FXCollections.observableArrayList();
		tablaCuartos.getItems().clear();
		cargarColumnas();
		refrescarTabla();
	}
	
	private void cargarColumnas() {
		id.setCellValueFactory(new PropertyValueFactory("id"));
		piso.setCellValueFactory(new PropertyValueFactory("piso"));			
		habitacion.setCellValueFactory(new PropertyValueFactory("habitacion"));
		capacidad.setCellValueFactory(new PropertyValueFactory("capacidad"));
		monto.setCellValueFactory(new PropertyValueFactory("monto"));
		montoSenia.setCellValueFactory(new PropertyValueFactory("montoSenia"));
		estado.setCellValueFactory(new PropertyValueFactory("estado"));
	}
	
	 @FXML
	    private void addCuarto(ActionEvent event) throws Exception {
		     try { 
			    Stage primaryStage = new Stage(); 
		 		URL fxml = getClass().getClassLoader().getResource("presentacion/vista/VentanaAgregarCuarto.fxml");
				FXMLLoader fxmlLoader = new FXMLLoader(fxml);
				Parent root = (Parent) fxmlLoader.load();
				primaryStage.setScene(new Scene(root));   
				ControladorAgregarCuarto scene2Controller = fxmlLoader.getController();
				scene2Controller.setVisibilityBtnAgregarCuarto(true);
				scene2Controller.setDisableBtnAgregarCuarto(false);
				scene2Controller.setVisibilityBtnEditarCuarto(false);
			    scene2Controller.setDisableBtnEditarCuarto(true);		 
			
				primaryStage.setTitle("Agregar cuarto");
				primaryStage.sizeToScene();
				primaryStage.show(); 
		       
		     } catch(Exception e) { 
		      e.printStackTrace(); 
		     } 
	    }
	 
	 @FXML
	    private void editCuarto(ActionEvent event) throws Exception 
	    {
		 
		 	if(tablaCuartos.getSelectionModel().getSelectedItem()==null) {
		 		validador.mostrarMensaje("Debes seleccionar un cuarto de la lista antes de editar");
		 		return;
		 	}	
		 
		     try { 
			    Stage primaryStage = new Stage(); 
		 		URL fxml = getClass().getClassLoader().getResource("presentacion/vista/VentanaAgregarCuarto.fxml");
				FXMLLoader fxmlLoader = new FXMLLoader(fxml);
				Parent root = (Parent) fxmlLoader.load();
				primaryStage.setScene(new Scene(root));   
				ControladorAgregarCuarto scene2Controller = fxmlLoader.getController();
				CuartoDTO cuartoSeleccionado = tablaCuartos.getSelectionModel().getSelectedItem();
				 scene2Controller.cargarCmbCateCuarto();
				 scene2Controller.setearCamposPantalla(cuartoSeleccionado);
			     scene2Controller.setVisibilityBtnAgregarCuarto(false);
			     scene2Controller.setDisableBtnAgregarCuarto(true);
			     scene2Controller.setVisibilityBtnEditarCuarto(true);
			     scene2Controller.setDisableBtnEditarCuarto(false);		 
			     scene2Controller.setearCamposPantalla(tablaCuartos.getSelectionModel().getSelectedItem());
				primaryStage.setTitle("Modificar cuarto");
				primaryStage.sizeToScene();
				primaryStage.show(); 
		       
		     } catch(Exception e) { 
		      e.printStackTrace(); 
		     } 
	    }
	 
	@FXML
	private void refrescarTabla(){
		txtBuscar.setText("");
		crearTabla(getAllCuartos());
	}

	private ObservableList<CuartoDTO> getAllCuartos() {
		List<CuartoDTO> cuartos = this.cuarto.obtenerCuartos();
		activeSession.clear();
		for(CuartoDTO c : cuartos) {
			activeSession.add(c);
		}
		return activeSession;
	}
		
	 @FXML
	private void buscarCuartos() {
	 	crearTabla(traerCuartos());
	}

	 private void crearTabla(ObservableList<CuartoDTO> lista) {
		 tablaCuartos.setItems(lista);
		 tablaCuartos.setEditable(true);
	 }
	
	
	 @FXML
	 private void deshabilitarCuartos(){	
		 if (tablaCuartos.getSelectionModel().getSelectedItem() == null) {
			validador.mostrarMensaje("Debes seleccionar un cuarto de la lista para deshabilitar");
			return;
		 }
		 CuartoDTO cuartoSeleccionado = tablaCuartos.getSelectionModel().getSelectedItem();
		 cuartoSeleccionado.setEstado(false);
		 this.cuarto.modificarCuartos(cuartoSeleccionado);
		 refrescarTabla();
	 }
	 
	 
	 @FXML
	 private void habilitarCuartos(){
		 if (tablaCuartos.getSelectionModel().getSelectedItem() == null) {
			validador.mostrarMensaje("Debes seleccionar un cuarto de la lista para habilitar");
			return;
		 }
		 CuartoDTO cuartoSeleccionado = tablaCuartos.getSelectionModel().getSelectedItem();
		 cuartoSeleccionado.setEstado(true);
		 this.cuarto.modificarCuartos(cuartoSeleccionado);
		 refrescarTabla();
	 }
	 
	 
	 @FXML
	 	private ObservableList<CuartoDTO> traerCuartos() {		
		 List<CuartoDTO> cuartos = this.cuarto.buscarCuartos(txtBuscar.getText().toString());
		 activeSession.clear();
	 		for(CuartoDTO c : cuartos) {
	 			activeSession.add(c);
	 		}
	 		return activeSession;
		}	
	
}
