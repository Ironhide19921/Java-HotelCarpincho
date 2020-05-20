package presentacion.vista;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import dto.CategoriaCuartoDTO;
import dto.CuartoDTO;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
	@FXML private ComboBox<CategoriaCuartoDTO> cmbBoxCateCuarto;
	@FXML private ObservableList<CategoriaCuartoDTO> activeSession;
	@FXML private Button btnRefrescarCate;
		  private Cuarto cuarto;
		  private CategoriaCuarto categorias;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.cuarto = new Cuarto(new DAOSQLFactory());
		this.categorias = new CategoriaCuarto(new DAOSQLFactory());
		llenarComboCategoriasCuartos();
	}
	
	@FXML
	public void llenarComboCategoriasCuartos() {
		List<CategoriaCuartoDTO> categorias = this.categorias.obtenerCategoriasCuartos();
		for(CategoriaCuartoDTO c : categorias) {
			activeSession.add(c);
		}
		cmbBoxCateCuarto = new ComboBox<CategoriaCuartoDTO>(activeSession);
	}
	
	 @FXML
		public void guardarUsuario() throws IOException {

		    String capacidad = txtCapacidad.getText();
			double monto = Double.parseDouble(txtMonto.getText());
			int montoSenia = Integer.parseInt(txtMontoSenia.getText());
			String piso =txtPiso.getText();
			String habitacion = txtHabitacion.getText();
			CategoriaCuartoDTO categoria = cmbBoxCateCuarto.getValue();	
			CuartoDTO nuevoCuarto = new CuartoDTO(0,categoria.getIdCategoriaCuarto(),capacidad,monto,montoSenia,piso,habitacion,true);
			this.cuarto.agregarCuarto(nuevoCuarto);
			cerrarVentanaAgregar();
	}

		public void setearCamposPantalla(CuartoDTO cuartoSeleccionado) throws IOException {

		    String capacidad = cuartoSeleccionado.getCapacidad();
			double monto = cuartoSeleccionado.getMonto();
			int montoSenia = cuartoSeleccionado.getMontoSenia();
			String piso =cuartoSeleccionado.getPiso();
			String habitacion = cuartoSeleccionado.getHabitacion();
			int idCategoria = cuartoSeleccionado.getIdCateCuarto();
			List<CategoriaCuartoDTO> categorias = this.categorias.obtenerCategoriasCuartos();
			for(CategoriaCuartoDTO c : categorias) {
				if(c.getIdCategoriaCuarto() == idCategoria) {
					cmbBoxCateCuarto.getSelectionModel().select(c);
				}
			}
		 }

		@FXML
			public void modificarUsuario() throws IOException 
		{	
			String capacidad = txtCapacidad.getText();
			double monto = Double.parseDouble(txtMonto.getText());
			int montoSenia = Integer.parseInt(txtMontoSenia.getText());
			String piso =txtPiso.getText();
			String habitacion = txtHabitacion.getText();
			CategoriaCuartoDTO categoria = cmbBoxCateCuarto.getValue();	
			CuartoDTO nuevoCuarto = new CuartoDTO(0,categoria.getIdCategoriaCuarto(),capacidad,monto,montoSenia,piso,habitacion,true);
			this.cuarto.modificarCuartos(nuevoCuarto);
			cerrarVentanaModificar();	
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

	public void setVisibilityBtnAgregarUsuario(Boolean value) {
		this.btnConfirmarCuarto.setVisible(value);
}
	public void setDisableBtnAgregarUsuario(Boolean value) {

		this.btnConfirmarCuarto.setDisable(value);
}
	public void setVisibilityBtnModificarUsuario(Boolean value) {
		this.btnEditarCuarto.setVisible(value);
}
	public void setDisableBtnModificarUsuario(Boolean value) {
		this.btnEditarCuarto.setDisable(value);
}

	
	
	
	
	
}
