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
	@FXML private Button btnEditarPedido;
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
	@FXML private TextField tipoTarjeta;
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
		
		this.listaCantidad = FXCollections.observableArrayList();
		this.listaCantidad.add(1);
		this.listaCantidad.add(2);
		this.listaCantidad.add(3);
		this.listaCantidad.add(4);
		this.listaCantidad.add(5);
		this.cmbBoxCantidad.setItems(listaCantidad);
		
		listaPedidosEnTabla = FXCollections.observableArrayList();
		productosEnTabla.getItems().clear();
		
		this.listaCmbBoxFormasPago = FXCollections.observableList(cargarCmbFormasPago());
		this.cmbBoxFormasPago.setItems(listaCmbBoxFormasPago);
		
	    cargarColumnasTablaProd();
	    
	    formaPago.setVisible(false);
    	cmbBoxFormasPago.setVisible(false);
    	tipoTarj.setVisible(false);
    	tipoTarjeta.setVisible(false);
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
	    	tipoTarjeta.setVisible(true);
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
	    	tipoTarjeta.setVisible(false);
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
		
		String prodSeleccionado = cmbBoxProductos.getValue();
		String[] prod = prodSeleccionado.split("-");
		String producto = prod[1];
		
		BigDecimal precioProd = null;
		for(ProductoDTO p : this.producto.obtenerProductos()) {
			if(p.getNombre().equalsIgnoreCase(producto)) {
				precioProd = p.getPrecio();
			}
		}
		
		int cantidadSelec = cmbBoxCantidad.getSelectionModel().getSelectedItem();
		BigDecimal prodSubtotal = new BigDecimal(txtPrecioSubtotal.getText());
		ProductoPedidoDTO prodOrden = new ProductoPedidoDTO(prodSeleccionado, precioProd, cantidadSelec, prodSubtotal);
		
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
		ProductoPedidoDTO prodSeleccionado = productosEnTabla.getSelectionModel().getSelectedItem();		
		listaPedidosEnTabla.remove(prodSeleccionado);
		
		subtotalPedido = subtotalPedido.subtract(prodSeleccionado.getPrecioTotal());
		txtSubtotal.setText(String.valueOf(subtotalPedido));
		
		productosEnTabla.setItems(listaPedidosEnTabla);	
	}
	
	@FXML
	public void confirmarPedido() throws Exception {
		
		int idProd = 0;
		int cantidad = 0;
		int idPedido = 0;
		int idCliente = 0;
		
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
					
					BigDecimal total = new BigDecimal(txtSubtotal.getText());
					OrdenPedidoDTO nuevoPedido = new OrdenPedidoDTO(idPedido, idProd, idCliente, 1, cantidad, total);
					this.ordenPedido.agregarOrdenPedido(nuevoPedido);
					
				}
								
				if(i == 0) {
					
					String produ = listaPedidosEnTabla.get(i).getNombre();
					String[] prodArray = produ.split("-");
					idProd = Integer.valueOf(prodArray[0]);					
					
					String cliente = cmbBoxClientes.getSelectionModel().getSelectedItem();
					String[] clienteSpliteado = cliente.split("-");
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
	public void confirmarY_GenerarTicket() {
		
		int idProd = 0;
		int cantidad = 0;
		int idPedido = 0;
		int idCliente = 0;
		BigDecimal total = new BigDecimal(0);
		
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
					String tipoTarj = tipoTarjeta.getText();
					String numTarj = numTarjeta.getText();
					String fecVenc = fechaVencTarjeta.getText();
					String codSeg = codSegTarjeta.getText();
					OrdenPedidoDTO nuevoPedido = new OrdenPedidoDTO(idPedido, idProd, idCliente, 1, cantidad, total, pagoSelec, tipoTarj, numTarj, fecVenc, codSeg, true);
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
					String tipoTarj = tipoTarjeta.getText();
					String numTarj = numTarjeta.getText();
					String fecVenc = fechaVencTarjeta.getText();
					String codSeg = codSegTarjeta.getText();
					OrdenPedidoDTO nuevoPedido = new OrdenPedidoDTO(0, idProd, idCliente, 1, cantidad, total, pagoSelec, tipoTarj, numTarj, fecVenc, codSeg, true);
					this.ordenPedido.agregarOrdenPedido(nuevoPedido);					
					
					idPedido = this.ordenPedido.obtenerIdMaximo();
										
				}								
			}
		}
		
		//datos del ticket
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		TicketDTO nuevoTicket = new TicketDTO(0, idCliente, total, "descripcion", "path", timestamp);
		this.ticket.agregarCliente(nuevoTicket);
		
		//datos del reporte
		//ReportePedidoTicket reporte = new ReportePedidoTicket(this.ordenPedido.obtenerOrdenesPedidos());
		ReportePedidoTicket reporte = new ReportePedidoTicket(this.ordenPedido.obtenerOrdenPedido(idPedido));
		reporte.mostrar();
		
		cerrarVentanaAgregarY_GenerarTicket();
		
	}
	
	@FXML
	private void cerrarVentanaAgregarY_GenerarTicket() {
		Stage stage = (Stage) btnConfirmarY_GenerarTicket.getScene().getWindow();
		stage.close();
	}
	
	@FXML
	public void editarPedido() throws Exception {
		int idProd = 0;
		int cantidad = 0;
		BigDecimal total = new BigDecimal(0);
		
		for(int i=0; i < listaPedidosEnTabla.size(); i++) {
			
			String nombreProd = listaPedidosEnTabla.get(i).getNombre();
			String[] prodArray = nombreProd.split("-");
			idProd = Integer.valueOf(prodArray[0]);					
			
			cantidad = listaPedidosEnTabla.get(i).getCantidad();
			
			total = new BigDecimal(txtSubtotal.getText());
			
			if(!pagoRestoran.isSelected()) {
				OrdenPedidoDTO pedidoEditado = new OrdenPedidoDTO(idOrdenPedido, idProd, idCliente, idUsuario, cantidad, total);
				this.ordenPedido.modificarOrdenPedido(pedidoEditado);
			}else {
				
				String formaPago = cmbBoxFormasPago.getValue();
				String tipoTarj = tipoTarjeta.getText();
				String numTarj = numTarjeta.getText();
				System.out.println(formaPago);
				String fecVenTarj = fechaVencTarjeta.getText();
				String codSegTarj = codSegTarjeta.getText();
				
				OrdenPedidoDTO pedidoEditado = new OrdenPedidoDTO(idOrdenPedido, idProd, idCliente, idUsuario, cantidad, total, formaPago, tipoTarj, numTarj, fecVenTarj, codSegTarj, true);
				this.ordenPedido.modificarOrdenPedido(pedidoEditado);
				
			}
		}
		cerrarVentanaEditar();
	}
	
	@FXML
	private void cerrarVentanaEditar() {
		Stage stage = (Stage) btnEditarPedido.getScene().getWindow();
		stage.close();
	}
	
	public void setearCampos(OrdenPedidoDTO pedidoSeleccionado) {
		System.out.println(pedidoSeleccionado.getIdOrdenPedido() + " "+pedidoSeleccionado.getIdProducto());
		idOrdenPedido = pedidoSeleccionado.getIdOrdenPedido();
		
		idCliente = pedidoSeleccionado.getIdCliente();
		for(ClienteDTO c : this.cliente.obtenerClientes()) {
			if(c.getIdCliente() == idCliente) {
				cmbBoxClientes.getSelectionModel().select(c.getIdCliente()+"-"+c.getNombre());
			}
		}
				
		idUsuario = pedidoSeleccionado.getIdUsuario();
		
		int idProd = pedidoSeleccionado.getIdProducto();
		String idNombreProd = "";
		
		BigDecimal precioProd = new BigDecimal(0);
		
		subtotalPedido = pedidoSeleccionado.getPrecioTotal();
		txtSubtotal.setText(String.valueOf(subtotalPedido));
		
		for(OrdenPedidoDTO op : this.ordenPedido.obtenerOrdenesPedidos()) {
			if(idOrdenPedido == op.getIdOrdenPedido()) {
				for(ProductoDTO p : this.producto.obtenerProductos()) {
					if(op.getIdProducto() == p.getIdProducto()) {
						precioProd = p.getPrecio();
						
						idNombreProd = p.getIdProducto() + "-"+ p.getNombre();
						
						BigDecimal cantProd = new BigDecimal(op.getCantidad());
						BigDecimal subtotalProd = precioProd.multiply(cantProd);
						
						ProductoPedidoDTO pedido = new ProductoPedidoDTO(idNombreProd, precioProd, op.getCantidad(), subtotalProd);
						listaPedidosEnTabla.add(pedido);
					}
				}
			}
		}
		System.out.println("pago restoran? "+pedidoSeleccionado.isEsRestoran());
		//datos del pago
		if(pedidoSeleccionado.isEsRestoran()) {
			System.out.println("si pago");
			pagoRestoran.setSelected(true);			
			formaPago.setVisible(true);
			cmbBoxFormasPago.setVisible(true);
			cmbBoxFormasPago.setValue(pedidoSeleccionado.getFormaPago());
			tipoTarj.setVisible(true);
			tipoTarjeta.setVisible(true);
			tipoTarjeta.setText(pedidoSeleccionado.getTipoTarjeta());
			numTarj.setVisible(true);
			numTarjeta.setVisible(true);
			numTarjeta.setText(pedidoSeleccionado.getNumTarjeta());
			fechaVencTarj.setVisible(true);
			fechaVencTarjeta.setVisible(true);
			fechaVencTarjeta.setText(pedidoSeleccionado.getFechaVencTarjeta());
			codSegTarj.setVisible(true);
			codSegTarjeta.setVisible(true);
			codSegTarjeta.setText(pedidoSeleccionado.getCodSeguridadTarjeta());
		}
		
		productosEnTabla.setItems(listaPedidosEnTabla);
		
	}
	
	public void setVisibilityBtnConfirmarPedido(Boolean value) {
		this.btnConfirmarPedido.setVisible(value);
	}
	
	public void setVisibilityBtnConfirmarGenerarTicket(Boolean value) {
		this.btnConfirmarY_GenerarTicket.setVisible(value);
	}
	
	public void setVisibilityBtnEditarPedido(Boolean value) {
		this.btnEditarPedido.setVisible(value);
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

}
