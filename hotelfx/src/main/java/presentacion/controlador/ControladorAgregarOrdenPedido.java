package presentacion.controlador;

import java.math.BigDecimal;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import dto.ClienteDTO;
import dto.OrdenPedidoDTO;
import dto.ProductoDTO;
import dto.ProductoPedidoDTO;
import dto.TicketDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import modelo.Cliente;
import modelo.OrdenPedido;
import modelo.Producto;
import modelo.Ticket;
import modelo.Validador;
import persistencia.dao.mysql.DAOSQLFactory;
import presentacion.reportes.ReportePedidoTicket;

public class ControladorAgregarOrdenPedido implements Initializable{
	
	@FXML private Button btnAgregarProducto;
	@FXML private Button btnEliminarProducto;
	@FXML private Button btnConfirmarPedido;
	@FXML private Button btnConfirmarY_GenerarTicket;
	@FXML private ComboBox<String> cmbBoxProductos;
	@FXML private ObservableList<String> listaCmbBoxProductos;
	@FXML private ComboBox<Integer> cmbBoxCantidad;
	@FXML private ObservableList<Integer> listaCantidad;
	@FXML private ComboBox<String> cmbBoxClientes;
	@FXML private ObservableList<String> listaCmbBoxClientes;
	
	@FXML private TableView<ProductoPedidoDTO> productosEnTabla;
	@FXML private ObservableList<ProductoPedidoDTO> listaPedidosEnTabla;
	@FXML private TableColumn nombreProd;
	@FXML private TableColumn precio;
	@FXML private TableColumn cantidad;
	@FXML private TableColumn subtotal;
	
	@FXML private TextField txtPrecio;
	@FXML private TextField txtPrecioSubtotal;
	@FXML private TextField txtSubtotal;
	private OrdenPedido ordenPedido;
	private Producto producto;
	private Cliente cliente;
	private Integer idOrdenPedido;
	private Integer idCliente;
	private Integer idUsuario;
	private BigDecimal subtotalPedido = new BigDecimal(0);
	
	@FXML private CheckBox pagoRestoran;
	@FXML private Label formaPago;
	@FXML private ComboBox<String> cmbBoxFormasPago;
	@FXML private ObservableList<String> listaCmbBoxFormasPago;
	@FXML private Label tipoTarj;
	@FXML private ComboBox<String> cmbBoxTiposTarjeta;
	@FXML private ObservableList<String> listaCmbBoxTiposTarjeta;
	@FXML private Label numTarj;
	@FXML private TextField numTarjeta;
	@FXML private Label fechaVencTarj;
	@FXML private TextField fechaVencTarjeta;
	@FXML private Label codSegTarj;
	@FXML private TextField codSegTarjeta;
	
	private Ticket ticket;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.ordenPedido = new OrdenPedido(new DAOSQLFactory());
		this.producto = new Producto(new DAOSQLFactory()); 
		this.cliente = new Cliente(new DAOSQLFactory());
		txtPrecio.setEditable(false);
		txtPrecioSubtotal.setEditable(false);
		txtSubtotal.setEditable(false);
		
		this.listaCmbBoxProductos = FXCollections.observableList(cargarCmbProductos());
		this.cmbBoxProductos.setItems(listaCmbBoxProductos);
		
		this.listaCmbBoxClientes = FXCollections.observableList(cargarCmbClientes());
		this.cmbBoxClientes.setItems(listaCmbBoxClientes);
		
		this.listaCantidad = FXCollections.observableList(cargarCmbCantidad());
		this.cmbBoxCantidad.setItems(listaCantidad);
		
		listaPedidosEnTabla = FXCollections.observableArrayList();
		productosEnTabla.getItems().clear();
		
		this.listaCmbBoxFormasPago = FXCollections.observableList(cargarCmbFormasPago());
		this.cmbBoxFormasPago.setItems(listaCmbBoxFormasPago);
		
		this.listaCmbBoxTiposTarjeta = FXCollections.observableList(cargarCmbTiposTarjeta());
		this.cmbBoxTiposTarjeta.setItems(listaCmbBoxTiposTarjeta);
		
	    cargarColumnasTablaProd();
	    
	    cmbBoxCantidad.setDisable(true);
	    
	    formaPago.setVisible(false);
    	cmbBoxFormasPago.setVisible(false);
    	tipoTarj.setVisible(false);
    	cmbBoxTiposTarjeta.setVisible(false);
    	numTarj.setVisible(false);
    	numTarjeta.setVisible(false);
    	fechaVencTarj.setVisible(false);
    	fechaVencTarjeta.setVisible(false);
    	codSegTarj.setVisible(false);
    	codSegTarjeta.setVisible(false);
    	
    	btnConfirmarY_GenerarTicket.setDisable(true);
    	
    	this.ticket = new Ticket(new DAOSQLFactory());
	    
	}

	private List<String> cargarCmbProductos() {
		List<String> lista = new ArrayList<>();
		for(ProductoDTO p : this.producto.obtenerProductos()) {
			lista.add(p.getIdProducto() + "-" + p.getNombre());
		}
		return lista;
	}
	
	private List<Integer> cargarCmbCantidad() {
		List<Integer> lista = new ArrayList<>();
		for(int i=1; i<=10; i++) {
			lista.add(i);
		}
		for(int j=15; j<=50; j=j+5) {
			lista.add(j);
		}
		return lista;
	}
	
	private List<String> cargarCmbClientes() {
		List<String> lista = new ArrayList<>();
		for(ClienteDTO c : this.cliente.obtenerClientes()) {
			lista.add(c.getIdCliente() + "-" + c.getNombre() + " " + c.getApellido());
		}
		return lista;
	}
	
	private List<String> cargarCmbFormasPago(){
		List<String> lista = new ArrayList<>();
		lista.add("Efectivo");
		lista.add("Tarjeta Debito");
		lista.add("Tarjeta Credito");
		return lista;
	}
	
	private List<String> cargarCmbTiposTarjeta() {
		List<String> lista = new ArrayList<>();
		lista.add("VISA");
		lista.add("MASTERCARD");
		return lista;
	}
	
	private void cargarColumnasTablaProd() {
		nombreProd.setCellValueFactory(new PropertyValueFactory("nombre"));
		precio.setCellValueFactory(new PropertyValueFactory("precio"));
		cantidad.setCellValueFactory(new PropertyValueFactory("cantidad"));		
		subtotal.setCellValueFactory(new PropertyValueFactory("precioTotal"));
	}
	
	@FXML
	public void mostrarCamposPago() {
		if(pagoRestoran.isSelected()) {
	    	formaPago.setVisible(true);
	    	cmbBoxFormasPago.setVisible(true);
	    	tipoTarj.setVisible(true);
	    	cmbBoxTiposTarjeta.setVisible(true);
	    	numTarj.setVisible(true);
	    	numTarjeta.setVisible(true);
	    	fechaVencTarj.setVisible(true);
	    	fechaVencTarjeta.setVisible(true);
	    	codSegTarj.setVisible(true);
	    	codSegTarjeta.setVisible(true);
	    	btnConfirmarPedido.setDisable(true);
	    	btnConfirmarY_GenerarTicket.setDisable(false);
	    }else {
	    	formaPago.setVisible(false);
	    	cmbBoxFormasPago.setVisible(false);
	    	tipoTarj.setVisible(false);
	    	cmbBoxTiposTarjeta.setVisible(false);
	    	numTarj.setVisible(false);
	    	numTarjeta.setVisible(false);
	    	fechaVencTarj.setVisible(false);
	    	fechaVencTarjeta.setVisible(false);
	    	codSegTarj.setVisible(false);
	    	codSegTarjeta.setVisible(false);
	    	btnConfirmarPedido.setDisable(false);
	    	btnConfirmarY_GenerarTicket.setDisable(true);
	    }
	}
	
	@FXML
	public void verPrecio() throws Exception {
		txtPrecioSubtotal.setText("");
		String prodSeleccionado = cmbBoxProductos.getValue();
		String[] prod = prodSeleccionado.split("-");
		String producto = prod[1];
		
		for(ProductoDTO p : this.producto.obtenerProductos()) {
			if(p.getNombre().equalsIgnoreCase(producto)) {
				txtPrecio.setText(String.valueOf(p.getPrecio()));
			}
		}
		
		cmbBoxCantidad.setDisable(false);
	}
	
	@FXML
	public void verPrecioSubtotal() throws Exception {
		int cantidad = cmbBoxCantidad.getValue();
		BigDecimal cant = new BigDecimal(cantidad);
		String precio = txtPrecio.getText();
		BigDecimal precioDec = new BigDecimal(precio);
		txtPrecioSubtotal.setText(String.valueOf(precioDec.multiply(cant)));
	}
	
	@FXML
	public void agregarProducto() throws Exception {
		ProductoPedidoDTO prodOrden = null;
		String prodSeleccionado = "";
		String[] prod = {};
		String producto = "";
		
		if(cmbBoxProductos.getSelectionModel().getSelectedItem() == null) {
			Validador.mostrarMensaje("Seleccione un producto para agregar");
		}else {
			prodSeleccionado = cmbBoxProductos.getValue();
			prod = prodSeleccionado.split("-");
			producto = prod[1];
		}
		
		BigDecimal precioProd = null;
		for(ProductoDTO p : this.producto.obtenerProductos()) {
			if(p.getNombre().equalsIgnoreCase(producto)) {
				precioProd = p.getPrecio();
			}
		}
		
		int cantidadSelec = 0;
		if(cmbBoxCantidad.getSelectionModel().getSelectedItem() == null) {
			Validador.mostrarMensaje("Elija la cantidad");
		}else {
			cantidadSelec = cmbBoxCantidad.getSelectionModel().getSelectedItem();
		}
		
		BigDecimal prodSubtotal = new BigDecimal(txtPrecioSubtotal.getText());
		prodOrden = new ProductoPedidoDTO(prodSeleccionado, precioProd, cantidadSelec, prodSubtotal);
		
		boolean estaRepetido = false;
		if(listaPedidosEnTabla.isEmpty()) {
			listaPedidosEnTabla.add(prodOrden);
			subtotalPedido = subtotalPedido.add(prodSubtotal);
			txtSubtotal.setText(String.valueOf(subtotalPedido));
		}else {
			for(int i=0; i < listaPedidosEnTabla.size(); i++) {
				if(listaPedidosEnTabla.get(i).getNombre().equals(prodSeleccionado)) {
					estaRepetido = true;									
				}
			}			
			if(!estaRepetido) {
				listaPedidosEnTabla.add(prodOrden);
				subtotalPedido = subtotalPedido.add(prodSubtotal);
				txtSubtotal.setText(String.valueOf(subtotalPedido));
			}else {
				Validador.mostrarMensaje("Este producto " + prodSeleccionado + " esta repetido en la lista");
			}			
		}			
		
		productosEnTabla.setItems(listaPedidosEnTabla);
		
	}
	
	@FXML
	public void eliminarProducto() throws Exception {
		if(listaPedidosEnTabla.isEmpty()){
			Validador.mostrarMensaje("La lista esta vacia");
		}else {
			if(productosEnTabla.getSelectionModel().getSelectedItem() == null) {
				Validador.mostrarMensaje("Seleccione un producto a eliminar");
			}else {
				ProductoPedidoDTO prodSeleccionado = productosEnTabla.getSelectionModel().getSelectedItem();		
				listaPedidosEnTabla.remove(prodSeleccionado);
				
				subtotalPedido = subtotalPedido.subtract(prodSeleccionado.getPrecioTotal());
				txtSubtotal.setText(String.valueOf(subtotalPedido));
				
				productosEnTabla.setItems(listaPedidosEnTabla);
			}
		}	
	}
	
	@FXML
	public void confirmarPedido() throws Exception {
		
		if(!Validador.validarPedido(this)) {
			return;
		}
		
		int idProd = 0;
		int cantidad = 0;
		int idPedido = 0;
		
		String cliente = "";
		String[] clienteSpliteado = {};
		int idCliente = 0;
		
		if(idPedido == 0) {
			
			for(int i=0; i < listaPedidosEnTabla.size(); i++) {
				
				if(i > 0) {
					String produ = listaPedidosEnTabla.get(i).getNombre();
					String[] prodArray = produ.split("-");
					idProd = Integer.valueOf(prodArray[0]);
					
					cantidad = listaPedidosEnTabla.get(i).getCantidad();
					
					BigDecimal total = new BigDecimal(txtSubtotal.getText());
					OrdenPedidoDTO nuevoPedido = new OrdenPedidoDTO(idPedido, idProd, idCliente, 1, cantidad, total);
					this.ordenPedido.agregarOrdenPedido(nuevoPedido);
					
				}
								
				if(i == 0) {
					
					String produ = listaPedidosEnTabla.get(i).getNombre();
					String[] prodArray = produ.split("-");
					idProd = Integer.valueOf(prodArray[0]);					
					
					cliente = cmbBoxClientes.getSelectionModel().getSelectedItem();
					clienteSpliteado = cliente.split("-");
					idCliente = Integer.valueOf(clienteSpliteado[0]);
					
					cantidad = listaPedidosEnTabla.get(i).getCantidad();
					
					BigDecimal total = new BigDecimal(txtSubtotal.getText());
					OrdenPedidoDTO nuevoPedido = new OrdenPedidoDTO(0, idProd, idCliente, 1, cantidad, total);
					this.ordenPedido.agregarOrdenPedido(nuevoPedido);
					
					idPedido = this.ordenPedido.obtenerIdMaximo();
				}								
			}
		}
		
		cerrarVentanaAgregar();
	}
	
	@FXML
	private void cerrarVentanaAgregar() {
		Stage stage = (Stage) btnConfirmarPedido.getScene().getWindow();
		stage.close();
	}
	
	@FXML
	public void verificarFormaPago() {
		if(cmbBoxFormasPago.getSelectionModel().getSelectedItem().equals("Efectivo")) {
			cmbBoxTiposTarjeta.setDisable(true);
			numTarjeta.setDisable(true);
			fechaVencTarjeta.setDisable(true);
			codSegTarjeta.setDisable(true);
		}else {
			cmbBoxTiposTarjeta.setDisable(false);
			numTarjeta.setDisable(false);
			fechaVencTarjeta.setDisable(false);
			codSegTarjeta.setDisable(false);
		}
	}
	
	@FXML
	public void confirmarY_GenerarTicket() {
		
		if(!Validador.validarPedidoConTicket(this)) {
			return;
		}
		
		int idProd = 0;
		int cantidad = 0;
		int idPedido = 0;
		int idCliente = 0;
		BigDecimal total = new BigDecimal(0);
		OrdenPedidoDTO nuevoPedido = null;
		
		if(idPedido == 0) {
			
			for(int i=0; i < listaPedidosEnTabla.size(); i++) {
				
				if(i > 0) {
					String produ = listaPedidosEnTabla.get(i).getNombre();
					String[] prodArray = produ.split("-");
					idProd = Integer.valueOf(prodArray[0]);
					
					String cliente = cmbBoxClientes.getSelectionModel().getSelectedItem();
					String[] clienteSpliteado = cliente.split("-");
					idCliente = Integer.valueOf(clienteSpliteado[0]);
					
					cantidad = listaPedidosEnTabla.get(i).getCantidad();
					
					total = new BigDecimal(txtSubtotal.getText());
					
					//Datos del pago
					String pagoSelec = cmbBoxFormasPago.getValue();
					String tipoTarjSelec = cmbBoxTiposTarjeta.getValue();
					String numTarj = numTarjeta.getText();
					String fecVenc = fechaVencTarjeta.getText();
					String codSeg = codSegTarjeta.getText();
					nuevoPedido = new OrdenPedidoDTO(idPedido, idProd, idCliente, 1, cantidad, total, pagoSelec, tipoTarjSelec, numTarj, fecVenc, codSeg, true);
					this.ordenPedido.agregarOrdenPedido(nuevoPedido);
					
				}
								
				if(i == 0 ) {
					
					String produ = listaPedidosEnTabla.get(i).getNombre();
					String[] prodArray = produ.split("-");
					idProd = Integer.valueOf(prodArray[0]);					
					
					String cliente = cmbBoxClientes.getSelectionModel().getSelectedItem();
					String[] clienteSpliteado = cliente.split("-");
					idCliente = Integer.valueOf(clienteSpliteado[0]);
					
					cantidad = listaPedidosEnTabla.get(i).getCantidad();
					
					total = new BigDecimal(txtSubtotal.getText());
					
					//Datos del pago
					String pagoSelec = cmbBoxFormasPago.getValue();
					
					if(pagoSelec.equals("Efectivo")) {
						nuevoPedido = new OrdenPedidoDTO(0, idProd, idCliente, 1, cantidad, total, pagoSelec, "", "", "", "", true);
						this.ordenPedido.agregarOrdenPedido(nuevoPedido);				
						idPedido = this.ordenPedido.obtenerIdMaximo();
						
					}else {
						String tipoTarjSelec = cmbBoxTiposTarjeta.getValue();
						String numTarj = numTarjeta.getText();
						String fecVenc = fechaVencTarjeta.getText();
						String codSeg = codSegTarjeta.getText();
						nuevoPedido = new OrdenPedidoDTO(0, idProd, idCliente, 1, cantidad, total, pagoSelec, tipoTarjSelec, numTarj, fecVenc, codSeg, true);
						this.ordenPedido.agregarOrdenPedido(nuevoPedido);				
						idPedido = this.ordenPedido.obtenerIdMaximo();
						
					}										
				}								
			}
		}
		
		//datos del ticket
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		TicketDTO nuevoTicket = new TicketDTO(0, idCliente, total, "descripcion", "path", timestamp);
		this.ticket.agregarCliente(nuevoTicket);
		
		int ultimoIdTicket = this.ticket.obtenerIdTicketRecienInsertado(nuevoTicket.getIdCliente());
		TicketDTO ticket = this.ticket.getTicket(ultimoIdTicket);
		String path = "/tickets/ordenPedido/ticketPedido_" + idPedido + "_"+ ticket.getIdTicket() +".pdf";
		String pathPDF = ".//tickets//ordenPedido//ticketPedido_" + idPedido + "_"+ ticket.getIdTicket() +".pdf";
		
		//datos del reporte
		ReportePedidoTicket reporte = new ReportePedidoTicket(this.ordenPedido.obtenerOrdenPedido(idPedido), pathPDF);
		reporte.mostrar();
		
		if(!verificarPath(path)) {
			this.ticket.modificarTicket(ticket, path);
			reporte.guardarPdf();
		}else {
			Validador.mostrarMensaje("El path del ticket visualizado ya existe.");
		}
		
		cerrarVentanaAgregarY_GenerarTicket();
		
	}
	
	private boolean verificarPath(String path) {
		boolean pathRepetido = false;
		for(TicketDTO t : this.ticket.obtenerClientes()) {
			if(t.getPath().equalsIgnoreCase(path)) {
				pathRepetido = true;
			}
		}
		return pathRepetido;
	}
	
	@FXML
	private void cerrarVentanaAgregarY_GenerarTicket() {
		Stage stage = (Stage) btnConfirmarY_GenerarTicket.getScene().getWindow();
		stage.close();
	}
		
	public void setVisibilityBtnConfirmarPedido(Boolean value) {
		this.btnConfirmarPedido.setVisible(value);
	}
	
	public void setVisibilityBtnConfirmarGenerarTicket(Boolean value) {
		this.btnConfirmarY_GenerarTicket.setVisible(value);
	}

	public ComboBox<String> getCmbBoxClientes() {
		return cmbBoxClientes;
	}

	public void setCmbBoxClientes(ComboBox<String> cmbBoxClientes) {
		this.cmbBoxClientes = cmbBoxClientes;
	}

	public CheckBox getPagoRestoran() {
		return pagoRestoran;
	}
	
	public ObservableList<ProductoPedidoDTO> getListaPedidosEnTabla() {
		return listaPedidosEnTabla;
	}

	public ComboBox<String> getCmbBoxFormasPago() {
		return cmbBoxFormasPago;
	}

	public ComboBox<String> getCmbBoxTiposTarjeta() {
		return cmbBoxTiposTarjeta;
	}

	public TextField getNumTarjeta() {
		return numTarjeta;
	}

	public TextField getFechaVencTarjeta() {
		return fechaVencTarjeta;
	}

	public TextField getCodSegTarjeta() {
		return codSegTarjeta;
	}

}
