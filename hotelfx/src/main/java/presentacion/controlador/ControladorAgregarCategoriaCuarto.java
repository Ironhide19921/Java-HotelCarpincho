package presentacion.controlador;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import dto.CategoriaCuartoDTO;
import dto.SalonDTO;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import modelo.CategoriaCuarto;
import modelo.Validador;
import persistencia.dao.mysql.DAOSQLFactory;

public class ControladorAgregarCategoriaCuarto implements Initializable{
	@FXML private TextField txtNombre;
	@FXML private TextField txtDetalle;
	@FXML private Button btnAgregarCategoriaCuarto;
	@FXML private Button btnEditarCategoriaCuarto;
	@FXML private Button btnCerrar;
		  private CategoriaCuarto categoriaCuarto;
		  private Integer id;
		  private ControladorABMCategoriaCuarto controladorABMCategoriaCuarto;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		cargarIconos();
		this.categoriaCuarto = new CategoriaCuarto(new DAOSQLFactory());
	}
	
 	@FXML
	public void guardarCliente() throws IOException {
	 
		String nombre = txtNombre.getText();
		String detalle = txtDetalle.getText();
		CategoriaCuartoDTO categoriaCuarto = new CategoriaCuartoDTO(0, nombre, detalle);
		if(!Validador.consultarRepetidos(categoriaCuarto, this.categoriaCuarto.obtenerCategoriasCuartos())) {
			this.categoriaCuarto.agregarCategoriaCuarto(categoriaCuarto);
			Validador.mostrarMensaje("Categoría agregada.");
			controladorABMCategoriaCuarto.refrescarTabla();
			cerrarVentanaAgregar();	
		}
		else {
			Validador.mostrarMensaje("La categoría ya existe.");
		}
 	}
	 

	public void setearCamposPantalla(CategoriaCuartoDTO categoriaCuarto) throws IOException {

		   //ClienteDTO clienteSeleccionado = controlador.getTablaPersonas().getSelectionModel().getSelectedItem();
		   txtNombre.setText(categoriaCuarto.getNombre());
		   txtDetalle.setText(categoriaCuarto.getDetalle());
		   id = categoriaCuarto.getIdCategoriaCuarto();
		    
	 }

	@FXML
	public void modificarCliente() throws IOException {
	
		String nombre = txtNombre.getText();
		String detalle = txtDetalle.getText();
		CategoriaCuartoDTO categoriaActual = null;
		CategoriaCuartoDTO categoriaCuarto = new CategoriaCuartoDTO(id, nombre, detalle);
		List<CategoriaCuartoDTO> categoriasSinActual = this.categoriaCuarto.obtenerCategoriasCuartos();
		for(CategoriaCuartoDTO c : categoriasSinActual) {
			if(c.getIdCategoriaCuarto() == id) {
				categoriaActual = c;
			}
		}
		categoriasSinActual.remove(categoriaActual);
		if(!Validador.consultarRepetidos(categoriaCuarto, categoriasSinActual)) {
			this.categoriaCuarto.modificarCategoriaCuarto(categoriaCuarto);
			controladorABMCategoriaCuarto.refrescarTabla();
			cerrarVentanaModificar();	
			Validador.mostrarMensaje("Categoría modificada.");
		}	
		else {
			Validador.mostrarMensaje("Nombre de categoría de cuarto ya existe.");
		}
	}
	 
	 
	 @FXML
	 private void cerrarVentanaAgregar() {
		Stage stage = (Stage) btnAgregarCategoriaCuarto.getScene().getWindow();
		stage.close();
	}
	 
	 @FXML
	 private void cerrarVentanaModificar() {
		 Stage stage = (Stage) btnEditarCategoriaCuarto.getScene().getWindow();
			stage.close();
	}
	 
	
	 @FXML
	 private void cerrarVentana() {
		 Stage stage = (Stage) btnCerrar.getScene().getWindow();
			stage.close();
	}
	 
	 
	 
		public Button getBtnAgregarCategoriaCuarto() {
				return btnAgregarCategoriaCuarto;
		}

		public void setBtnAgregarCategoriaCuarto(Button btnAgregarCategoriaCuarto) {
				this.btnAgregarCategoriaCuarto = btnAgregarCategoriaCuarto;
		}

		public void setVisibilityBtnAgregarCategoriaCuarto(Boolean value) {
			this.btnAgregarCategoriaCuarto.setVisible(value);
	}
		public void setDisableBtnAgregarCategoriaCuarto(Boolean value) {

			this.btnAgregarCategoriaCuarto.setDisable(value);
	}
		public void setVisibilityBtnModificarCategoriaCuarto(Boolean value) {
			this.btnEditarCategoriaCuarto.setVisible(value);
	}
		public void setDisableBtnModificarCategoriaCuarto(Boolean value) {
			this.btnEditarCategoriaCuarto.setDisable(value);
	}

		public Button getBtnModificarCategoriaCuarto() {
				return btnEditarCategoriaCuarto;
		}

		public void setBtnModificarCategoriaCuarto(Button btnModificarCliente) {
				this.btnEditarCategoriaCuarto = btnModificarCliente;
		}

		public void enviarControlador(ControladorABMCategoriaCuarto controladorABMCategoriaCuarto) {
			// TODO Auto-generated method stub
			this.controladorABMCategoriaCuarto = controladorABMCategoriaCuarto;
		}

		private void cargarIconos() {
			
			URL linkAgregar = getClass().getResource("/img/aceptar.png");
			URL linkModificar = getClass().getResource("/img/editar.png");
			URL linkCerrar = getClass().getResource("/img/cancelar.png");
	
			Image imageAgregar = new Image(linkAgregar.toString(),24,24,false,true) ;
			Image imageModificar = new Image(linkModificar.toString(),24,24,false,true) ;
			Image imageCerrar = new Image(linkCerrar.toString(),24,24,false,true) ;
		
			this.btnAgregarCategoriaCuarto.setGraphic(new ImageView(imageAgregar));
			this.btnEditarCategoriaCuarto.setGraphic(new ImageView(imageModificar));
			this.btnCerrar.setGraphic(new ImageView(imageCerrar));		
		}
	 
}
