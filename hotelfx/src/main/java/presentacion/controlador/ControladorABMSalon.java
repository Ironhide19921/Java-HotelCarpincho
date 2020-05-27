package presentacion.controlador;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import dto.ClienteDTO;
import dto.SalonDTO;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import modelo.CategoriaCuarto;
import modelo.Salon;
import persistencia.dao.mysql.DAOSQLFactory;

public class ControladorABMSalon implements Initializable{
	@FXML private TableView<SalonDTO> tablaSalones;
	@FXML private Button btnAgregar;
	@FXML private Button btnEditar;
	@FXML private Button btnRefrescar;
	@FXML private Button btnHabilitaCliente;
	@FXML private Button btnDeshabilitarCliente;
	@FXML private TableColumn id;
	@FXML private TableColumn capacidad;
	@FXML private TableColumn senia;
	@FXML private TableColumn estilo;
	@FXML private TableColumn monto;
	@FXML private TableColumn estado;
	@FXML private ObservableList<SalonDTO> activeSession;
	private Salon salon;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		this.salon = new Salon(new DAOSQLFactory());
		activeSession = FXCollections.observableArrayList();
		tablaSalones.getItems().clear();
		cargarColumnas();
		refrescarTabla();
	}
	
	private void cargarColumnas() {
		id.setCellValueFactory(new PropertyValueFactory("id"));		
		capacidad.setCellValueFactory(new PropertyValueFactory("capacidad"));	
		senia.setCellValueFactory(new PropertyValueFactory("senia"));
		estilo.setCellValueFactory(new PropertyValueFactory("estilo"));
		monto.setCellValueFactory(new PropertyValueFactory("monto"));
		estado.setCellValueFactory(new PropertyValueFactory("estado"));	
	}
	
	@FXML
	private void refrescarTabla(){
 		crearTabla(getAllSalones());
	}
	
	 @FXML
    private void addSalon(ActionEvent event) throws Exception 
    {
	     try { 
		    Stage primaryStage = new Stage(); 
	 		URL fxml = getClass().getClassLoader().getResource("presentacion/vista/VentanaAgregarSalon.fxml");
			FXMLLoader fxmlLoader = new FXMLLoader(fxml);
			Parent root = (Parent) fxmlLoader.load();
			primaryStage.setScene(new Scene(root));   
			ControladorAgregarSalon scene2Controller = fxmlLoader.getController();
			scene2Controller.setVisibilityBtnAgregarSalon(true);
			scene2Controller.setDisableBtnAgregarSalon(false);
			scene2Controller.setVisibilityBtnModificarSalon(false);
			scene2Controller.setDisableBtnModificarSalon(true);		 
		
			primaryStage.setTitle("Agregar un salon");
			primaryStage.sizeToScene();
			primaryStage.show(); 
	     } catch(Exception e) { 
	      e.printStackTrace(); 
	     } 
    }
	
	@FXML
    private void editSalon(ActionEvent event) throws Exception 
    {
	     try { 
		    Stage primaryStage = new Stage(); 
	 		URL fxml = getClass().getClassLoader().getResource("presentacion/vista/VentanaAgregarSalon.fxml");
			FXMLLoader fxmlLoader = new FXMLLoader(fxml);
			Parent root = (Parent) fxmlLoader.load();
			primaryStage.setScene(new Scene(root));   
			ControladorAgregarSalon scene2Controller = fxmlLoader.getController();
			scene2Controller.setVisibilityBtnAgregarSalon(false);
			scene2Controller.setDisableBtnAgregarSalon(true);
			scene2Controller.setVisibilityBtnModificarSalon(true);
			scene2Controller.setDisableBtnModificarSalon(false);		 
			scene2Controller.setearCamposPantalla(tablaSalones.getSelectionModel().getSelectedItem());
			primaryStage.setTitle("Modificar un salon");
			primaryStage.sizeToScene();
			primaryStage.show();
	       
	     } catch(Exception e) { 
	      e.printStackTrace(); 
	     } 
    }
	
	@FXML
	private void deshabilitarSalon(){	
		SalonDTO salonSeleccionado = tablaSalones.getSelectionModel().getSelectedItem();
		salonSeleccionado.setEstado(false);
		this.salon.modificarEstado(salonSeleccionado);
		refrescarTabla();
	}
	 
	 
	@FXML
	private void habilitarSalon(){	
		SalonDTO salonSeleccionado = tablaSalones.getSelectionModel().getSelectedItem();
		salonSeleccionado.setEstado(true);
		this.salon.modificarEstado(salonSeleccionado);
		refrescarTabla();
	}
	
	private void crearTabla(ObservableList<SalonDTO> lista) {
		tablaSalones.setItems(lista);
		tablaSalones.setEditable(true);
	 }
	
	private ObservableList<SalonDTO> getAllSalones() {
		List<SalonDTO> salones = this.salon.obtenerSalones();
		activeSession.clear();
 		for(SalonDTO s : salones) {
 			activeSession.add(s);
 		}
 		return activeSession;
	}
	
	public TableView<SalonDTO> getTablaSalones() {
		return tablaSalones;
	}

	public void setTablaSalones(TableView<SalonDTO> tablaSalones) {
		this.tablaSalones = tablaSalones;
	}
}
