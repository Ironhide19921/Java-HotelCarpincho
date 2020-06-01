package presentacion.controlador;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import modelo.ReservaCuarto;
import persistencia.dao.mysql.DAOSQLFactory;

public class ControladorABMReservaCuarto implements Initializable
{

	@FXML private Button btnAgregarReserva;
	@FXML private Button btnModificarReserva;
	@FXML private Button btnHabDesReserva;
	@FXML private Button btnRefresh;
	@FXML private TextField ingresarCliente;
	@FXML private TableView<ReservaCuartoDTO> tablaReservas;
	@FXML private ObservableList<ReservaCuartoDTO> activeSession;
	//@FXML private VBox fondoTabla;
//	@FXML private TableColumn idReserva;
	@FXML private TableColumn cliente;
	@FXML private TableColumn cuarto;
	@FXML private TableColumn usuario;
//	@FXML private TableColumn senia;
//	@FXML private TableColumn estado;
//	@FXML private TableColumn monto;
	//@FXML private TableColumn formaPago;
	@FXML private TableColumn fechaReserva;
	@FXML private TableColumn fechaCheckIn;
	@FXML private TableColumn fechaOut;
	@FXML private TableColumn fechaIngreso;
	@FXML private TableColumn fechaEgreso;
	private ReservaCuarto reserva;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1)
	{
		/*this.reserva = new ReservaCuarto(new DAOSQLFactory());
		activeSession = FXCollections.observableArrayList();
		tablaReservas.getItems().clear();
		//cargarColumnas();
		refrescarTabla();*/
	}

	private void refrescarTabla() 
	{
		crearTabla(getAllReservasCuartos());
	}

	private void crearTabla(ObservableList<ReservaCuartoDTO> allReservasCuartos) 
	{
		tablaReservas.setItems(allReservasCuartos);
	//	tablaReservas.setEditable(true);
	}

	private ObservableList<ReservaCuartoDTO> getAllReservasCuartos() {
		List<ReservaCuartoDTO> reservas = this.reserva.obtenerReservasCuartos();
		activeSession.clear();
		 for(ReservaCuartoDTO r : reservas) {
		 	activeSession.add(r);
		 }
		 return activeSession;
	}

	private void cargarColumnas() 
	{
		/*cliente.setCellValueFactory(new PropertyValueFactory("idCliente"));		
		cuarto.setCellValueFactory(new PropertyValueFactory("idCuarto"));	
		usuario.setCellValueFactory(new PropertyValueFactory("idUsuario"));
		fechaReserva.setCellValueFactory(new PropertyValueFactory("FechaReserva"));
		fechaCheckIn.setCellValueFactory(new PropertyValueFactory("FechaCheckIn"));
		fechaOut.setCellValueFactory(new PropertyValueFactory("FechaOut"));	
		fechaIngreso.setCellValueFactory(new PropertyValueFactory("FechaIngreso"));
		fechaEgreso.setCellValueFactory(new PropertyValueFactory("FechaEgreso"));
	}*/
		
	}
	
	@FXML
	public void addReservaCuarto() throws Exception  {
		 try { 
			    Stage primaryStage = new Stage(); 
		 		URL fxml = getClass().getClassLoader().getResource("presentacion/vista/VentanaAgregarReservaCuarto.fxml");
				FXMLLoader fxmlLoader = new FXMLLoader(fxml);
				Parent root = (Parent) fxmlLoader.load();
				primaryStage.setScene(new Scene(root));   
			primaryStage.getScene().getStylesheets().add("/CSS/mycss.css");
				ControladorAgregarReservaCuarto scene2Controller = fxmlLoader.getController();
				primaryStage.setTitle("Agregar reserva de cuarto");
				primaryStage.sizeToScene();
				primaryStage.show(); 
		       
		     } catch(Exception e) { 
		      e.printStackTrace(); 
		     } 
	}
	
	
}
