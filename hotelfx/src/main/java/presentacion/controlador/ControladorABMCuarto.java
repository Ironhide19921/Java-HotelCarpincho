package presentacion.controlador;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.table.DefaultTableModel;

import dto.CategoriaCuartoDTO;
import dto.ClienteDTO;
import dto.CuartoDTO;
import dto.ReservaCuartoDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import modelo.Cuarto;
import modelo.Validador;
import persistencia.dao.mysql.DAOSQLFactory;

public class ControladorABMCuarto implements Initializable
{
	@FXML private TableView<CuartoDTO> tablaCuartos;
	@FXML private Button btnAgregarCuarto;
	@FXML private Button btnEditar;
	@FXML private Button btnBorrar;
	@FXML private Button btnBuscar;
	@FXML private Button btnAgregarCuartoReserva;
	@FXML private Button btnEditarReserva;
	@FXML private Button btnReporte;
	@FXML private Button btnSeleccionarCuarto;
	@FXML private Label busqueda;
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
		  private Cuarto cuarto;
		  private ReservaCuartoDTO reserva;
		  private Validador validador;
		  private ControladorAgregarReservaCuarto1 controlador;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		this.cuarto = new Cuarto(new DAOSQLFactory());
		activeSession = FXCollections.observableArrayList();
		this.btnSeleccionarCuarto.setVisible(false);
		tablaCuartos.getItems().clear();
		cargarColumnas();
		refrescarTabla();
		cargarIconos();
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
				primaryStage.getScene().getStylesheets().add("/CSS/mycss.css");
				ControladorAgregarCuarto scene2Controller = fxmlLoader.getController();
				scene2Controller.enviarControlador(this);
				scene2Controller.modificarVisibilidadBotones(true);
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
				primaryStage.getScene().getStylesheets().add("/CSS/mycss.css");
				ControladorAgregarCuarto scene2Controller = fxmlLoader.getController();
				CuartoDTO cuartoSeleccionado = tablaCuartos.getSelectionModel().getSelectedItem();
				scene2Controller.enviarControlador(this);
				scene2Controller.cargarCmbCateCuarto();
				scene2Controller.setearCamposPantalla(cuartoSeleccionado);
				scene2Controller.modificarVisibilidadBotones(false); 
				primaryStage.setTitle("Modificar cuarto");
				primaryStage.sizeToScene();
				primaryStage.show(); 
		       
		     } catch(Exception e) { 
		      e.printStackTrace(); 
		     } 
	    }
	 
	@FXML void refrescarTabla(){
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
	 private void habilitarCuartos(){
		 if (tablaCuartos.getSelectionModel().getSelectedItem() == null) {
			validador.mostrarMensaje("Debes seleccionar un cuarto de la lista para habilitar");
			return;
		 }
		 CuartoDTO cuartoSeleccionado = tablaCuartos.getSelectionModel().getSelectedItem();
		 if(cuartoSeleccionado.getEstado() == true) {
			 cuartoSeleccionado.setEstado(false);
			 this.cuarto.modificarCuartos(cuartoSeleccionado);
			 refrescarTabla();
			 return;
		 }else {
			 cuartoSeleccionado.setEstado(true);
			 this.cuarto.modificarCuartos(cuartoSeleccionado);
			 refrescarTabla();
			 return;
		 }
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
	 
	@FXML
	public void consultaReservaCuarto(Timestamp fechaEgreso, Timestamp fechaIngreso) {
		tablaCuartos.getItems().clear();
		cargarColumnas();
			List<CuartoDTO> cuartos = this.cuarto.obtenerCuartosDisponibles(fechaEgreso,fechaIngreso);
			activeSession.clear();
	 		for(CuartoDTO c : cuartos) {
	 			activeSession.add(c);
	 		}
	 		tablaCuartos.setItems(activeSession);
	}
	
	public void modificarBotones(Boolean esReserva) {
		if(esReserva) {
			this.btnSeleccionarCuarto.setVisible(true);
			this.btnAgregarCuarto.setVisible(false);
			this.btnEditar.setVisible(false);
			this.btnHabilitarCuarto.setVisible(false);
			this.btnBuscar.setVisible(false);
			this.txtBuscar.setVisible(false);
			this.busqueda.setVisible(false);
		}
		else{
			this.btnSeleccionarCuarto.setVisible(false);
			this.btnAgregarCuarto.setVisible(true);
			this.btnEditar.setVisible(true);
			this.btnHabilitarCuarto.setVisible(true);
		
		}
	}
	
	 public void datosReserva(ReservaCuartoDTO reserva) {
		 this.reserva = reserva;
	 }
	 
	 @FXML
	 public void seleccionarCuarto() throws IOException {
		 
			controlador.modificarCuarto(this.tablaCuartos.getSelectionModel().getSelectedItem().getId());
			controlador.verMontoTotalySenia();
			Stage stage = (Stage) btnSeleccionarCuarto.getScene().getWindow();
			stage.close();
	 }

	public void enviarControlador(ControladorAgregarReservaCuarto1 controladorAgregarReservaCuarto1) {
		// TODO Auto-generated method stub
		this.controlador = controladorAgregarReservaCuarto1;
	}		
	
	private void cargarIconos() {
		
		URL linkAgregar = getClass().getResource("/img/aceptar.png");
		URL linkModificar = getClass().getResource("/img/editar.png");
		URL linkHabilitar = getClass().getResource("/img/habilitar.png");
		URL linkBuscar = getClass().getResource("/img/buscar.jpg");
		
		
		Image imageAgregar = new Image(linkAgregar.toString(),24,24,false,true) ;
		Image imageModificar = new Image(linkModificar.toString(),24,24,false,true) ;
		Image imageHabilitar = new Image(linkHabilitar.toString(),24,24,false,true) ;
		Image imageBuscar = new Image(linkBuscar.toString(),24,24,false,true) ;
		
		this.btnAgregarCuarto.setGraphic(new ImageView(imageAgregar));
		this.btnEditar.setGraphic(new ImageView(imageModificar));
		this.btnHabilitarCuarto.setGraphic(new ImageView(imageHabilitar));	
		this.btnBuscar.setGraphic(new ImageView(imageBuscar));
	}
}
