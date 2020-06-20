package modelo;
import java.sql.Date;
import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import presentacion.controlador.ControladorAgregarCliente;
import presentacion.controlador.ControladorAgregarUsuario;

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
	
	public static void mostrarMensaje(String mensaje) {
		alert.setTitle("Información");
		alert.setHeaderText(null);
		alert.setContentText(mensaje);

		alert.showAndWait();
	}
	
	public static String mostrarMensajeOpcion() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Ups, parece que hay un error de conexión");
		alert.setHeaderText("¿Le gustaría reconfigurar las credenciales de conexión ahora?");
		alert.setContentText("Pulse aceptar para configurar o cancelar para terminar la ejecución del programa");

		Optional<ButtonType> result = alert.showAndWait();
		return result.get().getText();
	}
	
	public static String mostrarMensajeCampo() {
		TextInputDialog dialog = new TextInputDialog("mi_email@hotmail.com");
		dialog.setTitle("Error de login");
		dialog.setHeaderText("Parece que esas no son las credenciales correctas, ¿quieres que te las recordemos mediante un email?");
		dialog.setContentText("Por favor ingrese su email: ");
		
		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()){
		    return result.get();
		}
		return "Cancelar";
	}
	
}
