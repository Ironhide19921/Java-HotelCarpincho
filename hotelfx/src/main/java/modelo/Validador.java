package modelo;
import java.sql.Date;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import presentacion.controlador.ControladorAgregarCliente;
import presentacion.controlador.ControladorAgregarUsuario;
import presentacion.controlador.ControladorAgregarOrdenPedido;

public class Validador {
	
	private static Alert alert = new Alert(AlertType.INFORMATION);
	
	
	public static boolean formatoNumeroLetraEspacio(String texto) {
		return texto.matches("[a-zA-Z0-9\\s]+");
	}
	
	public static boolean formatoMail(String texto) {
		return texto.matches("[a-zA-Z0-9]+@[a-zA-Z0-9]+[.][a-zA-Z0-9]+[.]?[a-zA-Z0-9]+") ;
	}
	
	public static boolean formatoApellido(String texto) {
		return (formatoLetraEspacio(texto)  );
	}
	
	public static boolean formatoLetraEspacio(String texto) {
		return texto.matches("[a-zA-Z\\s]+");
	}
	
	public static boolean formatoTelefono(String texto) {
		return (formatoNumerico(texto) );
	}

	public static boolean formatoNumero(String texto) {
		return (formatoNumerico(texto)  );
	}

	public static boolean formatoNumerico(String texto) {
		return texto.matches("[0-9]+");
	}
	
	public static boolean formatoVisa(String numero, String cod) {
		return numero.matches("^4[0-9]{12}(?:[0-9]{3})?$") && cod.matches("[0-9]{3,4}");
	}
	
	public static boolean formatoMaster(String numero, String cod) {
		return numero.matches("5[1-5][0-9]{14}$") && cod.matches("[0-9]{3}");
	}
	
	public static boolean validarTarjeta(String numero, String cod, Date expira) {
		return ((formatoVisa(numero,cod) || formatoMaster(numero,cod)) && (expira.after(new Date(System.currentTimeMillis()))));
	}
	
	public static boolean validarFechaVenc(String fechaVenc) {
		return fechaVenc.matches("(0[1-9]|1[0-2])\\/[0-9]{2}");
	}
	
	public static boolean validarUsuario(ControladorAgregarUsuario ventanaUsuario){
		
		boolean condicionCompleta = true;
		boolean condicionInput = true;
		boolean condicionFormato = true;
		
		condicionInput = condicionInput  
		&& !ventanaUsuario.getTxtNombre().getText().equals("")		
		&& !ventanaUsuario.getTxtApellido().getText().equals("")
		&& !ventanaUsuario.getTxtPassword().getText().equals("")
		&& !ventanaUsuario.getTxtNumDocumento().getText().equals("")
		&& !ventanaUsuario.getTxtEmail().getText().equals("")
		&& (!(ventanaUsuario.getComboPerfil().getValue()==null))
		&& (!(ventanaUsuario.getComboTipoDoc().getValue()==null))
		;
		
		condicionFormato = condicionFormato && formatoApellido(ventanaUsuario.getTxtNombre().getText())
		&& formatoApellido(ventanaUsuario.getTxtApellido().getText())
		&& formatoNumerico(ventanaUsuario.getTxtNumDocumento().getText())
		&& formatoMail(ventanaUsuario.getTxtEmail().getText());
		;

		if (!condicionInput)
			mostrarMensaje("Campos obligatorios vacios");
		if (!condicionFormato)
			mostrarMensaje("Contienen un formato incorrecto\\n");
		
		condicionCompleta = condicionInput && condicionFormato;
		return condicionCompleta;

	}
	
	public static boolean validarCliente(ControladorAgregarCliente ventanaCliente){
		
		boolean condicionCompleta = true;
		boolean condicionInput = true;
		boolean condicionFormato = true;
		
		condicionInput = condicionInput  
		&& !ventanaCliente.getTxtNombre().getText().equals("")		
		&& !ventanaCliente.getTxtApellido().getText().equals("")
		&& !ventanaCliente.getTxtTelefono().getText().equals("")
		&& !ventanaCliente.getTxtEmail().getText().equals("")
		&& !ventanaCliente.getTxtNumDocumento().getText().equals("")
		&& (!(ventanaCliente.getComboTipoDoc().getValue()==null))
		&& (!(ventanaCliente.getTxtFecha().getValue()==null))
		;
		
		condicionFormato = condicionFormato && formatoApellido(ventanaCliente.getTxtNombre().getText())
		&& formatoApellido(ventanaCliente.getTxtApellido().getText())
		&& formatoNumerico(ventanaCliente.getTxtNumDocumento().getText())
		&& formatoNumerico(ventanaCliente.getTxtTelefono().getText())
		&& formatoMail(ventanaCliente.getTxtEmail().getText());
		;

		if (!condicionInput)
			mostrarMensaje("Campos obligatorios vacios");
		if (!condicionFormato)
			mostrarMensaje("Contienen un formato incorrecto\\n");
		
		condicionCompleta = condicionInput && condicionFormato;
		return condicionCompleta;

	}
	
	public static boolean validarPedido(ControladorAgregarOrdenPedido pedido) {
		
		boolean condicionCompleta = true;
		boolean condicionLista = true;
		boolean condicionCliente = true;
		
		condicionLista = condicionLista 
				&& !pedido.getListaPedidosEnTabla().isEmpty();
		
		condicionCliente = condicionCliente 
				&& pedido.getCmbBoxClientes().getSelectionModel().getSelectedItem() != null;
		
		if(!condicionLista) {
			mostrarMensaje("Lista vacia");
		}
		if(!condicionCliente) {
			mostrarMensaje("Debe seleccionar un cliente");
		}
		
		condicionCompleta = condicionLista && condicionCliente;
		return condicionCompleta;
	}
	
	public static boolean validarPedidoConTicket(ControladorAgregarOrdenPedido pedido){
		boolean condicionCompleta = true;
		boolean condicionLista = true;
		boolean condicionCliente = true;
		boolean condicionInput = true;
		boolean condicionFormato = true;
		boolean condicionVisa = true;
		boolean condicionMaster = true;
		boolean condicionFormaPago = true;
		boolean pagoEfectivo = false;
		boolean condicionFechaVenc = true;
		
		condicionLista = condicionLista 
				&& !pedido.getListaPedidosEnTabla().isEmpty();
		
		condicionCliente = condicionCliente 
				&& pedido.getCmbBoxClientes().getSelectionModel().getSelectedItem() != null;
		
		condicionFormaPago = condicionFormaPago
				&& pedido.getCmbBoxFormasPago().getSelectionModel().getSelectedItem() != null;
		
		if(condicionFormaPago) {
			
			if(pedido.getCmbBoxFormasPago().getSelectionModel().getSelectedItem().equals("Efectivo")) {
				pagoEfectivo = true; 
						 
			}else {
				
				condicionInput = condicionInput
						&& !pedido.getTipoTarjeta().getText().equals("")
						&& !pedido.getNumTarjeta().getText().equals("")
						&& !pedido.getFechaVencTarjeta().getText().equals("")
						&& !pedido.getCodSegTarjeta().getText().equals("");
				
				condicionFormato = condicionFormato
						&& formatoNumerico(pedido.getNumTarjeta().getText())
						&& formatoNumerico(pedido.getCodSegTarjeta().getText());
				
				condicionFechaVenc = condicionFechaVenc
						&& validarFechaVenc(pedido.getFechaVencTarjeta().getText());
				
				if(pedido.getTipoTarjeta().getText().equals("visa")) {
					condicionVisa = condicionVisa 
							&& formatoVisa(pedido.getNumTarjeta().getText(), pedido.getCodSegTarjeta().getText());
				}
				
				if(pedido.getTipoTarjeta().getText().equals("mastercard")) {
					condicionMaster = condicionMaster
							&& formatoMaster(pedido.getNumTarjeta().getText(), pedido.getCodSegTarjeta().getText());
				}
				
			}			
			
		}				
		
		if(!condicionLista) {
			mostrarMensaje("Lista vacia");
		}
		if(!condicionCliente) {
			mostrarMensaje("Debe seleccionar un cliente");
		}
		if (!condicionInput) {
			mostrarMensaje("Campos obligatorios vacios");
		}
		if (!condicionFormato) {
			mostrarMensaje("Contienen un formato incorrecto");
		}
		if(!condicionFormaPago) {
			mostrarMensaje("Elija una forma de pago");
		}
		if(!condicionVisa) {
			mostrarMensaje("Ingrese numero de tarjeta y/o codigo de seguridad correcto");
		}
		if(!condicionMaster) {
			mostrarMensaje("Ingrese numero de tarjeta y/o codigo de seguridad correcto");
		}
		if(!condicionFechaVenc) {
			mostrarMensaje("La fecha de vencimiento no es correcta. \n "
					+ "Formato correcto: mm/aa. \n "
					+ "Ejemplo: 07/21, 04/00");
		}
		
		if(pagoEfectivo) {
			condicionCompleta = condicionLista && condicionCliente 
					&& condicionFormaPago && pagoEfectivo;
		}else {
			condicionCompleta = condicionLista && condicionCliente && condicionInput 
					&& condicionFormato && condicionFechaVenc && condicionVisa 
					&& condicionMaster && condicionFormaPago;	
		}	
		
		return condicionCompleta;
	}
	
	public static void mostrarMensaje(String mensaje) {
		alert.setTitle("Información");
		alert.setHeaderText(null);
		alert.setContentText(mensaje);

		alert.showAndWait();
	}

}
