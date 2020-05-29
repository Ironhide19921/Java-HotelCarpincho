package presentacion.controlador;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import dto.ClienteDTO;
import dto.ReservaCuartoDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import modelo.ReservaCuarto;
import persistencia.dao.mysql.DAOSQLFactory;

public class ControladorReservaCuarto implements Initializable{

	@FXML private Button btnAgregarReserva;
	@FXML private Button btnModificarReserva;
	@FXML private Button btnHabDesReserva;
	@FXML private Button btnRefresh;
	@FXML private TextField ingresarCliente;
	@FXML private TableView<ReservaCuartoDTO> tablaReservas;
	@FXML private ObservableList<ReservaCuartoDTO> activeSession;
	@FXML private VBox fondoTabla;
	@FXML private TableColumn nombre;
	@FXML private TableColumn apellido;
	@FXML private TableColumn telefono;
	@FXML private TableColumn email;
	@FXML private TableColumn numeroDocumento;
	@FXML private TableColumn estado  ;
	@FXML private TableColumn idCliente;
	
	private ReservaCuarto reserva;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		this.reserva = new ReservaCuarto(new DAOSQLFactory());
		activeSession = FXCollections.observableArrayList();
		tablaReservas.getItems().clear();
		cargarColumnas();
		refrescarTabla();
	}

	private void refrescarTabla() {
		crearTabla(getAllReservasCuartos());
	}

	private void crearTabla(ObservableList<ReservaCuartoDTO> allReservasCuartos) {
		tablaReservas.setItems(allReservasCuartos);
		tablaReservas.setEditable(true);
	}

	private ObservableList<ReservaCuartoDTO> getAllReservasCuartos() {

			List<ReservaCuartoDTO> reservas = this.reserva.obtenerReservasCuartos();
			activeSession.clear();
	 		for(ReservaCuartoDTO r : reservas) {
	 			activeSession.add(r);
	 		}
	 		return activeSession;
		}


	private void cargarColumnas() {
		nombre.setCellValueFactory(new PropertyValueFactory("nombre"));		
		apellido.setCellValueFactory(new PropertyValueFactory("apellido"));	
		email.setCellValueFactory(new PropertyValueFactory("email"));
		telefono.setCellValueFactory(new PropertyValueFactory("telefono"));
		estado.setCellValueFactory(new PropertyValueFactory("estado"));
		idCliente.setCellValueFactory(new PropertyValueFactory("idCliente"));	
		numeroDocumento.setCellValueFactory(new PropertyValueFactory("numeroDocumento"));
	}

}
