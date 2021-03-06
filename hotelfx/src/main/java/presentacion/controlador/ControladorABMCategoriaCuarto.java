package presentacion.controlador;




import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import dto.CategoriaCuartoDTO;
import dto.ClienteDTO;
import dto.CuartoDTO;
import dto.UsuarioDTO;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import modelo.CategoriaCuarto;
import modelo.Cuarto;
import modelo.Validador;
import persistencia.dao.mysql.DAOSQLFactory;

public class ControladorABMCategoriaCuarto implements Initializable {

	@FXML private TableView<CategoriaCuartoDTO> tablaCategoriaCuarto;
	@FXML private Button btnAgregar;
	@FXML private Button btnEditar;
	@FXML private Button btnBorrar;
	@FXML private Button btnRefrescar;
	@FXML private TableColumn id;
	@FXML private TableColumn nombre;
	@FXML private TableColumn detalle;
	@FXML private ObservableList<CategoriaCuartoDTO> activeSession;
		  private CategoriaCuarto categoriaCuarto;
		  private Cuarto cuarto;
		  private ArrayList<Integer> listaIdCategoriaCuartos;
	@FXML private TextField ingresarCategoria;
		  private Alert alert;
		  
		  private Validador validador;

	private void cargarColumnas() {
		nombre.setCellValueFactory(new PropertyValueFactory("Nombre"));		
		id.setCellValueFactory(new PropertyValueFactory("idCategoriaCuarto"));	
		detalle .setCellValueFactory(new PropertyValueFactory("Detalle"));
	}
	
	 @FXML
	    private void addCategoria(ActionEvent event) throws Exception 
	    {
		     try { 
			    Stage primaryStage = new Stage(); 
		 		URL fxml = getClass().getClassLoader().getResource("presentacion/vista/VentanaAgregarCategoriaCuarto.fxml");
				FXMLLoader fxmlLoader = new FXMLLoader(fxml);
				Parent root = (Parent) fxmlLoader.load();
				
				primaryStage.setScene(new Scene(root));  
				Image ico = new Image("/img/hotel2.png");
				primaryStage.getIcons().add(ico);
				primaryStage.getScene().getStylesheets().add("/CSS/mycss.css");
				ControladorAgregarCategoriaCuarto scene2Controller = fxmlLoader.getController();
				scene2Controller.enviarControlador(this);
				scene2Controller.setVisibilityBtnAgregarCategoriaCuarto(true);
				scene2Controller.setDisableBtnAgregarCategoriaCuarto(false);
				scene2Controller.setVisibilityBtnModificarCategoriaCuarto(false);
				scene2Controller.setDisableBtnModificarCategoriaCuarto(true);		 
			
				primaryStage.setTitle("Agregar categoria de cuarto");
				primaryStage.sizeToScene();
				primaryStage.show(); 
		       
		     } catch(Exception e) { 
		      e.printStackTrace(); 
		     } 
	    }
	 
	 @FXML
	    private void EditCategoria(ActionEvent event) throws Exception 
	    {
		 
		 	if(tablaCategoriaCuarto.getSelectionModel().getSelectedItem()==null) {
		 		validador.mostrarMensaje("Debes seleccionar una categoria de la lista antes de editar");
		 		return;
		 	}
		     try { 
			    Stage primaryStage = new Stage(); 
		 		URL fxml = getClass().getClassLoader().getResource("presentacion/vista/VentanaAgregarCategoriaCuarto.fxml");
				FXMLLoader fxmlLoader = new FXMLLoader(fxml);
				Parent root = (Parent) fxmlLoader.load();
			
				primaryStage.setScene(new Scene(root));
				Image ico = new Image("/img/hotel2.png");
				primaryStage.getIcons().add(ico);
				primaryStage.getScene().getStylesheets().add("/CSS/mycss.css");
				ControladorAgregarCategoriaCuarto scene2Controller = fxmlLoader.getController();
				scene2Controller.setVisibilityBtnAgregarCategoriaCuarto(false);
				scene2Controller.setDisableBtnAgregarCategoriaCuarto(true);
				scene2Controller.setVisibilityBtnModificarCategoriaCuarto(true);
				scene2Controller.enviarControlador(this);
				scene2Controller.setDisableBtnModificarCategoriaCuarto(false);
				scene2Controller.setearCamposPantalla(tablaCategoriaCuarto.getSelectionModel().getSelectedItem());
				primaryStage.setTitle("Modificar categoria de cuarto");
				primaryStage.sizeToScene();
				primaryStage.show(); 
		       
		     } catch(Exception e) { 
		      e.printStackTrace(); 
		     } 
	    }
	 
	 @FXML
	    private void eliminarCategoria(ActionEvent event) throws Exception 
	    {
		 
		 	if(tablaCategoriaCuarto.getSelectionModel().getSelectedItem()==null) {
		 		validador.mostrarMensaje("Debes seleccionar una categoria de la lista antes de eliminar");
		 		return;
		 	}
		     try {
		    	refrescarListaIdCategoriaCuarto();
		    	 
		    	if(this.listaIdCategoriaCuartos.contains(tablaCategoriaCuarto.getSelectionModel().getSelectedItem().getIdCategoriaCuarto())){
		    		mostrarMensaje("Categoria utilizada por un cuarto, no se puede borrar");
		    		return;
		    	}
		    	CategoriaCuartoDTO categoria =  tablaCategoriaCuarto.getSelectionModel().getSelectedItem();
		    	this.categoriaCuarto.borrarCategoriaCuarto(categoria);
			    refrescarTabla();
		    	
		     } catch(Exception e) { 
		      e.printStackTrace(); 
		     } 
	    }
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		cargarIconos();
		this.categoriaCuarto = new CategoriaCuarto(new DAOSQLFactory());
		this.cuarto = new Cuarto(new DAOSQLFactory());
		this.alert = new Alert(AlertType.INFORMATION);
		this.btnRefrescar.setVisible(false);
		cargarColumnas();
		activeSession = FXCollections.observableArrayList();
		tablaCategoriaCuarto.getItems().clear();
		refrescarTabla();
		
		this.listaIdCategoriaCuartos = new ArrayList<Integer>();
		refrescarListaIdCategoriaCuarto();
	}

	 @FXML void refrescarTabla(){
	 		crearTabla(getAllClientes());
		}
	 
		private ObservableList<CategoriaCuartoDTO> getAllClientes() {
			List<CategoriaCuartoDTO> categorias = this.categoriaCuarto.obtenerCategoriasCuartos();
			activeSession.clear();
	 		for(CategoriaCuartoDTO c : categorias) {
	 			activeSession.add(c);
	 		}
	 		return activeSession;
		}
		
	 /*@FXML
	private void buscarCliente() {
	 	crearTabla(traerClientes());
	}*/

	 private void crearTabla(ObservableList<CategoriaCuartoDTO> lista) {
		 tablaCategoriaCuarto.setItems(lista);
		 tablaCategoriaCuarto.setEditable(true);
	 }
	 
	 private void refrescarListaIdCategoriaCuarto() {
		for(CuartoDTO cuarto: cuarto.obtenerCuartos()) {
			this.listaIdCategoriaCuartos.add(cuarto.getIdCateCuarto());
		}
	 }
	
	 
	/* @FXML
	 	private ObservableList<CategoriaCuartoDTO> traerClientes() {
		 		
		 List<CategoriaCuartoDTO> categorias = this.categoriaCuarto.buscarCategoriaCuartos(ingresarCategoria.getText().toString());
		 activeSession.clear();
	 		for(CategoriaCuartoDTO c : categorias) {
	 			activeSession.add(c);
	 		}
	 		return activeSession;
		}*/
	 
	 private void mostrarMensaje(String mensaje) {
			alert.setTitle("Informaci??n");
			alert.setHeaderText(null);
			alert.setContentText(mensaje);

			alert.showAndWait();
		}

	 private void cargarIconos() {
			
			URL linkAgregar = getClass().getResource("/img/aceptar.png");
			URL linkModificar = getClass().getResource("/img/editar.png");
			URL linkCancelar = getClass().getResource("/img/cancelar.png");
			URL linkRefrescar = getClass().getResource("/img/cancelar.png");
			
			Image imageAgregar = new Image(linkAgregar.toString(),24,24,false,true) ;
			Image imageModificar = new Image(linkModificar.toString(),24,24,false,true) ;
			Image imageCancelar = new Image(linkCancelar.toString(),24,24,false,true) ;
			Image imageRefrescar = new Image(linkRefrescar.toString(),24,24,false,true) ;
			
	
			this.btnAgregar.setGraphic(new ImageView(imageAgregar));
			this.btnEditar.setGraphic(new ImageView(imageModificar));
			this.btnBorrar.setGraphic(new ImageView(imageCancelar));
			//this.btnRefrescar.setGraphic(new ImageView(imageUsuarios));
		}
	 

}
