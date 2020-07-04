package presentacion.controlador;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import dto.ProductoDTO;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import modelo.Producto;
import modelo.Validador;
import persistencia.dao.mysql.DAOSQLFactory;

public class ControladorAgregarProducto implements Initializable{
	
	@FXML private TextField txtNombre;
	@FXML private TextField txtPrecio;
	@FXML private TextField txtDescripcion;
	@FXML private TextField txtProveedor;
	@FXML private Button btnConfirmarProducto;
	@FXML private Button btnEditarProducto;	
	private Producto producto;
	private Integer id;
	private ControladorABMProducto controladorABMProducto;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		cargarIconos();
		this.producto = new Producto(new DAOSQLFactory());
		System.out.println(ControladorLogin.usuarioLogeado.getNombre());
	}
	
	@FXML
	public void agregarProducto() throws IOException {
		if(!Validador.validarProducto(this)) {
			return;
		}
		
		String nombre = txtNombre.getText();
		BigDecimal precio = new BigDecimal(txtPrecio.getText());
		String descripcion = txtDescripcion.getText();
		String proveedor = txtProveedor.getText();
		ProductoDTO nuevoProd= new ProductoDTO(0, nombre, precio, descripcion, proveedor, "Comida");
		//valido repetidos
		if(!Validador.consultarRepetidos(nuevoProd, this.producto.obtenerProductos())) {
			this.producto.agregarProducto(nuevoProd);
			this.controladorABMProducto.refrescarTablaProductos();
			cerrarVentanaAgregar();
		}else {
			Validador.mostrarMensaje("El producto ya existe");
		}
		
	}
	
	@FXML
	private void cerrarVentanaAgregar() {
		Stage stage = (Stage) btnConfirmarProducto.getScene().getWindow();
		stage.close();
	}
	
	@FXML
	public void editarProducto() throws IOException {
		if(!Validador.validarProducto(this)) {
			return;
		}
		
		String nombre = txtNombre.getText();
		BigDecimal precio = new BigDecimal(txtPrecio.getText());
		String descripcion = txtDescripcion.getText();
		String proveedor = txtProveedor.getText();
		ProductoDTO nuevoProd= new ProductoDTO(id, nombre, precio, descripcion, proveedor, "Comida");
		ProductoDTO prodActual = null;
		//repetidos
		List<ProductoDTO> productosSinActual = this.producto.obtenerProductos();
		for(ProductoDTO prod : productosSinActual) {
			if(prod.getIdProducto() == this.id) {
				prodActual = prod;
			}
		}
		
		productosSinActual.remove(prodActual);
		
		if(!Validador.consultarRepetidos(nuevoProd, productosSinActual)) {
			this.producto.modificarProducto(nuevoProd);
			System.out.println("producto modificado");
			this.controladorABMProducto.refrescarTablaProductos();
			cerrarVentanaEditar();
		}else {
			Validador.mostrarMensaje("El producto ya existe");
		}
		
	}
	
	public void setearCamposPantalla(ProductoDTO prodSeleccionado) {
		id = prodSeleccionado.getIdProducto();
		txtNombre.setText(prodSeleccionado.getNombre());
		txtPrecio.setText(String.valueOf(prodSeleccionado.getPrecio()));
		txtDescripcion.setText(prodSeleccionado.getDescripcion());
		txtProveedor.setText(prodSeleccionado.getProveedor());
	}
	
	@FXML
	private void cerrarVentanaEditar() {
		Stage stage = (Stage) btnEditarProducto.getScene().getWindow();
		stage.close();
	}
	
	public void setVisibilityBtnAgregarProd(Boolean value) {
		this.btnConfirmarProducto.setVisible(value);
	}
	
	public void setVisibilityBtnEditarProd(Boolean value) {
		this.btnEditarProducto.setVisible(value);
	}
	
	public TextField getTxtNombre() {
		return txtNombre;
	}

	public TextField getTxtPrecio() {
		return txtPrecio;
	}

	public TextField getTxtDescripcion() {
		return txtDescripcion;
	}

	public TextField getTxtProveedor() {
		return txtProveedor;
	}

	public void enviarControlador(ControladorABMProducto controladorABMProducto) {
		// TODO Auto-generated method stub
		this.controladorABMProducto = controladorABMProducto;
	}

	private void cargarIconos() {
		
	
		URL linkEditar = getClass().getResource("/img/editar.png");
		URL linkConfirmar = getClass().getResource("/img/aceptar.png");
		
		

		Image imageAgregar = new Image(linkConfirmar.toString(),24,24,false,true) ;
		Image imageEliminar = new Image(linkEditar.toString(),24,24,false,true) ;
	

		this.btnConfirmarProducto.setGraphic(new ImageView(imageAgregar));
		this.btnEditarProducto.setGraphic(new ImageView(imageEliminar));
		
		
	}
	
}
