package presentacion.controlador;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import dto.CategoriaCuartoDTO;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import modelo.CategoriaCuarto;
import modelo.Cuarto;
import persistencia.dao.mysql.DAOSQLFactory;

public class ControladorAgregarCuarto implements Initializable {
	@FXML private TextField txtCapacidad;
	@FXML private TextField txtMonto;
	@FXML private TextField txtMontoSenia;
	@FXML private TextField txtPiso;
	@FXML private TextField txtHabitacion;
	@FXML private RadioButton radioEstado;
	@FXML private Button btnConfirmarCuarto;
	@FXML private Button btnEditarCuarto;
	@FXML private Button btnIrACateCuarto;
	@FXML private Button btnCerrar;
	@FXML private ComboBox<String> cmbBoxCatesCuarto;
	@FXML private ObservableList<String> listaCategoriasCuarto;
	@FXML private Button btnRefrescarCate;
		  private Cuarto cuarto;
		  private CategoriaCuarto categorias;
		  private Integer id;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.cuarto = new Cuarto(new DAOSQLFactory());
		this.categorias = new CategoriaCuarto(new DAOSQLFactory());
		this.listaCategoriasCuarto = FXCollections.observableList(cargarCmbCateCuarto());
		this.cmbBoxCatesCuarto.setItems(listaCategoriasCuarto);
	}
	
	public List<String> cargarCmbCateCuarto() {
		List<String> lista = new ArrayList<>();
		for(CategoriaCuartoDTO c : this.categorias.obtenerCategoriasCuartos()) {
			lista.add(c.getIdCategoriaCuarto() + "-" + c.getNombre());
		}
		return lista;
	}
	
	@FXML
	public void agregarCuarto() throws IOException {
		String capacidad = txtCapacidad.getText();
		double monto = Double.parseDouble(txtMonto.getText());
		int montoSenia = Integer.parseInt(txtMontoSenia.getText());
		String piso =txtPiso.getText();
		String habitacion = txtHabitacion.getText();
		String categoria = cmbBoxCatesCuarto.getValue();
		String[] cates = categoria.split("-");
		int idCateCuarto = Integer.valueOf(cates[0]);	
		CuartoDTO nuevoCuarto = new CuartoDTO(0,idCateCuarto, capacidad, monto, montoSenia, piso, habitacion, true);
		this.cuarto.agregarCuarto(nuevoCuarto);
		cerrarVentanaAgregar();
	}

	public void setearCamposPantalla(CuartoDTO cuartoSeleccionado) throws IOException {
		id = cuartoSeleccionado.getId();
		txtCapacidad.setText(cuartoSeleccionado.getCapacidad());
		txtMonto.setText(String.valueOf(cuartoSeleccionado.getMonto()));
		txtMontoSenia.setText(String.valueOf(cuartoSeleccionado.getMontoSenia()));
		txtPiso.setText(cuartoSeleccionado.getPiso());
		txtHabitacion.setText(cuartoSeleccionado.getHabitacion());
		int idCategoria = cuartoSeleccionado.getIdCateCuarto();
		List<CategoriaCuartoDTO> categorias = this.categorias.obtenerCategoriasCuartos();
		for(CategoriaCuartoDTO c : categorias) {
			if(c.getIdCategoriaCuarto() == idCategoria) {
				cmbBoxCatesCuarto.getSelectionModel().select(c.getIdCategoriaCuarto()+"-"+c.getNombre());
			}
		}
	}

	@FXML
	public void editarCuarto() throws IOException {	
		String capacidad = txtCapacidad.getText();
		double monto = Double.parseDouble(txtMonto.getText());
		int montoSenia = Integer.parseInt(txtMontoSenia.getText());
		String piso =txtPiso.getText();
		String habitacion = txtHabitacion.getText();
		String categoria = cmbBoxCatesCuarto.getValue();
		String[] cates = categoria.split("-");
		int idCateCuarto = Integer.valueOf(cates[0]);	
		CuartoDTO nuevoCuarto = new CuartoDTO(id, idCateCuarto, capacidad, monto, montoSenia, piso, habitacion, true);
		this.cuarto.modificarCuartos(nuevoCuarto);
		cerrarVentanaModificar();	
	}
	
	@FXML
	private void mostrarAbmCategoriaCuarto(ActionEvent event) throws Exception {
		try {
			Stage primaryStage = new Stage(); 
	 		URL fxml = getClass().getClassLoader().getResource("presentacion/vista/VentanaABMCategoriaCuarto.fxml");
			FXMLLoader fxmlLoader = new FXMLLoader(fxml);
			Parent root = (Parent) fxmlLoader.load();
			primaryStage.setScene(new Scene(root));   
			primaryStage.sizeToScene();
			primaryStage.show(); 
		} 
		catch(Exception e) { 
			e.printStackTrace(); 
	    }
	 }
	 
	 @FXML
	 private void cerrarVentanaAgregar() {
		Stage stage = (Stage) btnConfirmarCuarto.getScene().getWindow();
		stage.close();
	}
	 
	 @FXML
	 private void cerrarVentanaModificar() {
		 Stage stage = (Stage) btnEditarCuarto.getScene().getWindow();
			stage.close();
	}
	 
	@FXML
	private void cerrarVentana() {
		Stage stage = (Stage) btnCerrar.getScene().getWindow();
		stage.close();
	}
	 
	@FXML
	private void recargarCmbCategoriasCuarto() {
		List<String> lista = new ArrayList<>();
		for(CategoriaCuartoDTO c : this.categorias.obtenerCategoriasCuartos()) {
			lista.add(c.getIdCategoriaCuarto() + "-" + c.getNombre());
		}
		this.listaCategoriasCuarto = FXCollections.observableList(lista);
		this.cmbBoxCatesCuarto.setItems(listaCategoriasCuarto);
	} 

	public void setVisibilityBtnAgregarCuarto(Boolean value) {
		this.btnConfirmarCuarto.setVisible(value);
	}
	
	public void setDisableBtnAgregarCuarto(Boolean value) {
		this.btnConfirmarCuarto.setDisable(value);
	}
	
	public void setVisibilityBtnEditarCuarto(Boolean value) {
		this.btnEditarCuarto.setVisible(value);
	}
	
	public void setDisableBtnEditarCuarto(Boolean value) {
		this.btnEditarCuarto.setDisable(value);
	}

	
	
	
	
	
}
