package presentacion.controlador;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.table.DefaultTableModel;

import dto.CategoriaCuartoDTO;
import dto.ClienteDTO;
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
import persistencia.dao.mysql.DAOSQLFactory;

public class ControladorABMCuarto implements Initializable
{
	@FXML private TableView<CuartoDTO> tablaCuartos;
	@FXML private Button btnAgregar;
	@FXML private Button btnEditar;
	@FXML private Button btnBorrar;
	@FXML private Button btnBuscar;
	@FXML private Button btnReporte;
	@FXML private TableColumn id;
	@FXML private TableColumn piso;
	@FXML private TableColumn habitacion;
	@FXML private TableColumn capacidad;
	@FXML private TableColumn monto;
	@FXML private TableColumn montoSena;
	@FXML private TableColumn estado;
	@FXML private ObservableList<CuartoDTO> activeSession;
	@FXML private TextField txtBuscar;
	@FXML private Button btnLimpiarFiltro;
	@FXML private Button btnCambiarEstado;
		private Cuarto cuarto;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		this.cuarto = new Cuarto(new DAOSQLFactory());
		activeSession = FXCollections.observableArrayList();
		tablaCuartos.getItems().clear();
		cargarColumnas();
		refrescarTabla();
	}
	
	private void cargarColumnas() {
		piso.setCellValueFactory(new PropertyValueFactory("Nombre"));		
		id.setCellValueFactory(new PropertyValueFactory("idCategoriaCuarto"));	
		habitacion.setCellValueFactory(new PropertyValueFactory("Detalle"));
		capacidad.setCellValueFactory(new PropertyValueFactory("Detalle"));
		monto.setCellValueFactory(new PropertyValueFactory("Detalle"));
		montoSena.setCellValueFactory(new PropertyValueFactory("Detalle"));
		estado.setCellValueFactory(new PropertyValueFactory("Detalle"));
	}
	
	 @FXML
	    private void addCuarto(ActionEvent event) throws Exception 
	    {
		     try { 
			    Stage primaryStage = new Stage(); 
		 		URL fxml = getClass().getClassLoader().getResource("presentacion/vista/VentanaAgregarCuarto.fxml");
				FXMLLoader fxmlLoader = new FXMLLoader(fxml);
				Parent root = (Parent) fxmlLoader.load();
				primaryStage.setScene(new Scene(root));   
				ControladorAgregarCuarto scene2Controller = fxmlLoader.getController();
				//scene2Controller.setVisibilityBtnAgregarCategoriaCuarto(true);
				//scene2Controller.setDisableBtnAgregarCategoriaCuarto(false);
				//scene2Controller.setVisibilityBtnModificarCategoriaCuarto(false);
			//	scene2Controller.setDisableBtnModificarCategoriaCuarto(true);		 
			
				primaryStage.setTitle("Agregar categoria de cuarto");
				primaryStage.sizeToScene();
				primaryStage.show(); 
		       
		     } catch(Exception e) { 
		      e.printStackTrace(); 
		     } 
	    }
	 
	 @FXML
	    private void EditCuarto(ActionEvent event) throws Exception 
	    {
		     try { 
			    Stage primaryStage = new Stage(); 
		 		URL fxml = getClass().getClassLoader().getResource("presentacion/vista/VentanaAgregarCuarto.fxml");
				FXMLLoader fxmlLoader = new FXMLLoader(fxml);
				Parent root = (Parent) fxmlLoader.load();
				primaryStage.setScene(new Scene(root));   
				ControladorAgregarCuarto scene2Controller = fxmlLoader.getController();
				//scene2Controller.setVisibilityBtnAgregarCategoriaCuarto(false);
			//	scene2Controller.setDisableBtnAgregarCategoriaCuarto(true);
			//	scene2Controller.setVisibilityBtnModificarCategoriaCuarto(true);
				//scene2Controller.setDisableBtnModificarCategoriaCuarto(false);		 
			//	scene2Controller.setearCamposPantalla(tablaCuartos.getSelectionModel().getSelectedItem());
				primaryStage.setTitle("Modificar categoria de cuarto");
				primaryStage.sizeToScene();
				primaryStage.show(); 
		       
		     } catch(Exception e) { 
		      e.printStackTrace(); 
		     } 
	    }
	 
	 @FXML
		private void refrescarTabla(){
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
		 CuartoDTO cuartoSeleccionado = tablaCuartos.getSelectionModel().getSelectedItem();
		 cuartoSeleccionado.setEstado(false);
		 this.cuarto.modificarCuartos(cuartoSeleccionado);
		 refrescarTabla();
	 }
	 
	 
	 @FXML
	 private void habilitarCuartos(){	
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
