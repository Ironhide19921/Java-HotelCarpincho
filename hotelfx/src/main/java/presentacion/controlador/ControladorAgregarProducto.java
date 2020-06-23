package presentacion.controlador;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ResourceBundle;

import dto.ProductoDTO;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import modelo.Producto;
import persistencia.dao.mysql.DAOSQLFactory;

public class ControladorAgregarProducto implements Initializable{
	
	@FXML private TextField txtNombre;
	@FXML private TextField txtPrecio;
	@FXML private TextField txtDescripcion;
	@FXML private TextField txtTipo;
	@FXML private TextField txtProveedor;
	@FXML private Button btnConfirmarProducto;
	@FXML private Button btnEditarProducto;	
	private Producto producto;
	private Integer id;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.producto = new Producto(new DAOSQLFactory());
		System.out.println(ControladorLogin.usuarioLogeado.getNombre());
	}
	
	@FXML
	public void agregarProducto() throws IOException {
		String nombre = txtNombre.getText();
		BigDecimal precio = new BigDecimal(txtPrecio.getText());
		String descripcion = txtDescripcion.getText();
		String tipo = txtTipo.getText();
		String proveedor = txtProveedor.getText();
		ProductoDTO nuevoProd= new ProductoDTO(0, nombre, precio, descripcion, proveedor, tipo);
		this.producto.agregarProducto(nuevoProd);
		cerrarVentanaAgregar();
	}
	
	@FXML
	private void cerrarVentanaAgregar() {
		Stage stage = (Stage) btnConfirmarProducto.getScene().getWindow();
		stage.close();
	}
	
	@FXML
	public void editarProducto() throws IOException {
		String nombre = txtNombre.getText();
		BigDecimal precio = new BigDecimal(txtPrecio.getText());
		String descripcion = txtDescripcion.getText();
		String tipo = txtTipo.getText();
		String proveedor = txtProveedor.getText();
		ProductoDTO nuevoProd= new ProductoDTO(id, nombre, precio, descripcion, proveedor, tipo);
		this.producto.modificarProducto(nuevoProd);
		cerrarVentanaEditar();
	}
	
	public void setearCamposPantalla(ProductoDTO prodSeleccionado) {
		id = prodSeleccionado.getIdProducto();
		txtNombre.setText(prodSeleccionado.getNombre());
		txtPrecio.setText(String.valueOf(prodSeleccionado.getPrecio()));
		txtDescripcion.setText(prodSeleccionado.getDescripcion());
		System.out.println("Descripcion "+prodSeleccionado.getDescripcion());
		txtTipo.setText(prodSeleccionado.getTipo());
		System.out.println("tipo "+prodSeleccionado.getTipo());
		txtProveedor.setText(prodSeleccionado.getProveedor());
		System.out.println("proveedor "+prodSeleccionado.getProveedor());
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

}
