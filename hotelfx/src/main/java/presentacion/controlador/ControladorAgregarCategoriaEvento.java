package presentacion.controlador;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import dto.CategoriaCuartoDTO;
import dto.CategoriaEventoDTO;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import modelo.CategoriaEvento;
import modelo.Validador;
import persistencia.dao.mysql.DAOSQLFactory;

public class ControladorAgregarCategoriaEvento implements Initializable{
	@FXML private TextField txtNombre;
	@FXML private TextField txtDetalle;
	@FXML private Button btnAgregarCategoriaEvento;
	@FXML private Button btnEditarCategoriaEvento;
	@FXML private Button btnCerrar;
		  private CategoriaEvento categoriaEvento;
		  private Integer id;
		private ControladorABMCategoriaEvento controladorABMCategoriaEvento;
		  
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	cargarIconos();
    	this.categoriaEvento = new CategoriaEvento(new DAOSQLFactory());
    }
    
    @FXML
	public void guardarCategoriaEvento() throws IOException {
	 
		String nombre = txtNombre.getText();
		String detalle = txtDetalle.getText();
		CategoriaEventoDTO categoriaEvento = new CategoriaEventoDTO(0, nombre, detalle);
		if(!Validador.consultarRepetidos(categoriaEvento, this.categoriaEvento.obtenerCategoriasEvento())) {
			this.categoriaEvento.agregarCategoriaEvento(categoriaEvento);
			Validador.mostrarMensaje("Categoría agregada.");
			this.controladorABMCategoriaEvento.refrescarTabla();
			cerrarVentana();	
		}	
		else{
			Validador.mostrarMensaje("La categoría ya existe.");
		}
	}
    
    public void setearCamposPantalla(CategoriaEventoDTO categoriaEvento) throws IOException {
		   txtNombre.setText(categoriaEvento.getNombre());
		   txtDetalle.setText(categoriaEvento.getDetalle());
		   id = categoriaEvento.getId();
	}

	@FXML
	public void editarCategoriaEvento() throws IOException {
		
		String nombre = txtNombre.getText();
		String detalle = txtDetalle.getText();
		CategoriaEventoDTO categoriaActual = null;
		CategoriaEventoDTO categoriaEvento = new CategoriaEventoDTO(id, nombre, detalle);
		List<CategoriaEventoDTO> categoriasSinActual = this.categoriaEvento.obtenerCategoriasEvento();
		for(CategoriaEventoDTO c : categoriasSinActual) {
			if(c.getId() == id) {
				categoriaActual = c;
			}
		}
		categoriasSinActual.remove(categoriaActual);
		
		if(!Validador.consultarRepetidos(categoriaEvento, categoriasSinActual)) {
			this.categoriaEvento.modificarCategoriaEvento(categoriaEvento);
			this.controladorABMCategoriaEvento.refrescarTabla();
			cerrarVentana();	
			Validador.mostrarMensaje("Categoría modificada.");
		}
		else {
			Validador.mostrarMensaje("Nombre de categoría de evento ya existe.");
		}
		
	}
    
	
    
    
    @FXML
	 private void cerrarVentana() {
		Stage stage = (Stage) btnCerrar.getScene().getWindow();
		stage.close();
	}
    
    
    
    
    
    
    
    

	public Button getBtnAgregarCategoriaEvento() {
		return btnAgregarCategoriaEvento;
	}

	public void setBtnAgregarCategoriaEvento(Button btnAgregarCategoriaEvento) {
		this.btnAgregarCategoriaEvento = btnAgregarCategoriaEvento;
	}

	public Button getBtnEditarCategoriaEvento() {
		return btnEditarCategoriaEvento;
	}

	public void setBtnEditarCategoriaEvento(Button btnEditarCategoriaEvento) {
		this.btnEditarCategoriaEvento = btnEditarCategoriaEvento;
	}	  

	public void setVisibilityBtnAgregarCategoriaEvento(Boolean value) {
		this.btnAgregarCategoriaEvento.setVisible(value);
	}
	
	public void setDisableBtnAgregarCategoriaEvento(Boolean value) {
		this.btnAgregarCategoriaEvento.setDisable(value);
	}
	
	public void setVisibilityBtnModificarCategoriaEvento(Boolean value) {
		this.btnEditarCategoriaEvento.setVisible(value);
	}
	
	public void setDisableBtnModificarCategoriaEvento(Boolean value) {
		this.btnEditarCategoriaEvento.setDisable(value);
	}

	public void enviarControlador(ControladorABMCategoriaEvento controladorABMCategoriaEvento) {
		// TODO Auto-generated method stub
		this.controladorABMCategoriaEvento = controladorABMCategoriaEvento;
	}
	
	private void cargarIconos() {
		
		URL linkAgregar = getClass().getResource("/img/aceptar.png");
		URL linkModificar = getClass().getResource("/img/editar.png");
		URL linkCerrar = getClass().getResource("/img/cancelar.png");

		Image imageAgregar = new Image(linkAgregar.toString(),24,24,false,true) ;
		Image imageModificar = new Image(linkModificar.toString(),24,24,false,true) ;
		Image imageCerrar = new Image(linkCerrar.toString(),24,24,false,true) ;
	
		this.btnAgregarCategoriaEvento.setGraphic(new ImageView(imageAgregar));
		this.btnEditarCategoriaEvento.setGraphic(new ImageView(imageModificar));
		//this.btnCerrar.setGraphic(new ImageView(imageCerrar));		
	}
}
