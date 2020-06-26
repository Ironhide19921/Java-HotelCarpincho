package modelo;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Optional;

import dto.CategoriaCuartoDTO;
import dto.CategoriaEventoDTO;
import dto.ClienteDTO;
import dto.CuartoDTO;
import dto.PermisoPerfilDTO;
import dto.ProductoDTO;
import dto.ReservaCuartoDTO.EstadoReserva;
import dto.ReservaCuartoDTO.FormaPago;
import dto.ReservaCuartoDTO.TipoTarjeta;
import dto.SalonDTO;
import dto.UsuarioDTO;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import presentacion.controlador.ControladorAgregarCliente;

import presentacion.controlador.ControladorAgregarReservaCuarto1;
import presentacion.controlador.ControladorAgregarUsuario;
import presentacion.controlador.ControladorAgregarProducto;
import presentacion.controlador.ControladorAgregarCuarto;
import presentacion.controlador.ControladorAgregarOrdenPedido;

public class Validador {

	private static Alert alert = new Alert(AlertType.INFORMATION);

	public static boolean formatoDocumento(String texto) {
		return texto.matches("^[A-Za-z0-9]+$");
	}

	public static boolean formatoNumeroLetraEspacio(String texto) {
		return texto.matches("[a-zA-Z0-9\\s]+");
	}

	public static boolean formatoMail(String texto) {
		return texto.matches("^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$") ;
	}

	public static boolean formatoApellido(String texto) {
		return (formatoLetraEspacio(texto));
	}

	public static boolean formatoLetraEspacio(String texto) {
		return texto.matches("[a-zA-Z\\s]+");
	}

	public static boolean formatoTelefono(String texto) {
		return texto.matches("[0-9]{7,}");
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
	
	public static boolean validarMesAnio(String fechaVenc) {
		Date hoy = new Date(System.currentTimeMillis());
		DateFormat formato = new SimpleDateFormat("MM/yy");
		
		String mesAnioActual = formato.format(hoy);
		String[] mesAnioEspliteado = mesAnioActual.split("/");
		String mesActual = mesAnioEspliteado[0];
		int mesAct = Integer.valueOf(mesActual);
		String anioActual = mesAnioEspliteado[1];
		int anioAct = Integer.valueOf(anioActual);
		
		String[] fechaEspliteada = fechaVenc.split("/");
		String mesIngresado = fechaEspliteada[0];
		int mesIng = Integer.valueOf(mesIngresado);
		String anioIngresado = fechaEspliteada[1];
		int anioIng = Integer.valueOf(anioIngresado);
				
		return (mesIng == mesAct || mesIng > mesAct) && anioIng == anioAct;
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
				&& formatoDocumento(ventanaUsuario.getTxtNumDocumento().getText())
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
				&& formatoDocumento(ventanaCliente.getTxtNumDocumento().getText())
				&& formatoTelefono(ventanaCliente.getTxtTelefono().getText())
				&& formatoMail(ventanaCliente.getTxtEmail().getText());
		;

		if (!condicionInput)
			mostrarMensaje("Campos obligatorios vacios");
		if (!condicionFormato)
			mostrarMensaje("Contienen un formato incorrecto\\n");

		condicionCompleta = condicionInput && condicionFormato;
		return condicionCompleta;

	}



	public static boolean validarReserva(ControladorAgregarReservaCuarto1 controladorAgregarReservaCuarto1){

		if(!controladorAgregarReservaCuarto1.ingresoFechas()) {
			mostrarMensaje("Las fechas de ingreso y egreso son obligatorias.");
			return false;
		}

		LocalDate localInicioReserva =  controladorAgregarReservaCuarto1.getFechaReserva().getValue();
		LocalDate localInicioIngreso = controladorAgregarReservaCuarto1.getFechaIngreso().getValue();
		LocalDate localInicioEgreso =  controladorAgregarReservaCuarto1.getFechaEgreso().getValue();
		Timestamp fechaReserva =Timestamp.valueOf(localInicioReserva.atTime(LocalTime.of(controladorAgregarReservaCuarto1.getCmbBoxHoraReserva().getSelectionModel().getSelectedItem(),0,0)));
		Timestamp fechaIngreso = Timestamp.valueOf(localInicioIngreso.atTime(LocalTime.of(controladorAgregarReservaCuarto1.getCmbBoxHoraIngreso().getSelectionModel().getSelectedItem(),0,0)));
		Timestamp fechaEgreso = Timestamp.valueOf(localInicioEgreso.atTime(LocalTime.of(controladorAgregarReservaCuarto1.getCmbBoxHoraEgreso().getSelectionModel().getSelectedItem()+1,0,0)));
		BigDecimal senia = new BigDecimal(controladorAgregarReservaCuarto1.getSenia().getText());
		BigDecimal montoReservaCuarto =new BigDecimal(controladorAgregarReservaCuarto1.getMontoCompleto().getText());
		BigDecimal montoSenia = new BigDecimal(controladorAgregarReservaCuarto1.getMontoSenia().getText());
		//TipoTarjeta tipoTarjeta = controladorAgregarReservaCuarto1.getTipoTarjeta();
		//EstadoReserva estadoReserva =controladorAgregarReservaCuarto1.getEstados();
		//FormaPago formaPago = controladorAgregarReservaCuarto1.getFormaPago();
		int idCliente = Integer.parseInt(controladorAgregarReservaCuarto1.getCliente().getText());
		int idCuarto = Integer.parseInt(controladorAgregarReservaCuarto1.getCuarto().getText());
		int idUsuario = Integer.parseInt(controladorAgregarReservaCuarto1.getUsuario());
		String emailFacturacion = controladorAgregarReservaCuarto1.getEmail().getText();
		String numeroTarjeta = controladorAgregarReservaCuarto1.getNumTarjeta().getText();
		String fechaVencTarjeta = controladorAgregarReservaCuarto1.getFechaVecTarjeta().getText();
		String codSeguridadTarjeta = controladorAgregarReservaCuarto1.getCodSeguridad().getText();	
		BigDecimal cantidadHoras = controladorAgregarReservaCuarto1.getCantidadHoras();
		if(idCliente == 0 || idUsuario == 0 || idCuarto == 0) {
			mostrarMensaje("El ingreso del cliente y del cuarto son obligatorios.");
			return false;
		}

		if(controladorAgregarReservaCuarto1.getEstados() == null) {
			mostrarMensaje("Debe ingresar un estado a su reserva.");
			return false;
		}	
		if(montoReservaCuarto.equals(BigDecimal.valueOf(0) )|| senia.equals(BigDecimal.valueOf(0))) {
			mostrarMensaje("Los valores referentes monto de reserva, seña, etc. No pueden ser 0. Verifique los datos del cuarto seleccionado.");
			return false;
		}
		if(emailFacturacion.equals("") ) {
			mostrarMensaje("El campo Email es obligatorio (*)");
			return false;
		}
		if(!formatoMail(emailFacturacion)) {
			mostrarMensaje("El campo Email tiene un formato incorrecto");
			return false;
		}
		if(controladorAgregarReservaCuarto1.getFormaPago()==null) {
			mostrarMensaje("Ingrese la forma de pago.");
			return false;
		}
		if(fechaReserva == null) {
			mostrarMensaje("La fecha de reserva es obligatoria (*).");
			return false;
		}

		if(cantidadHoras.equals(BigDecimal.valueOf(0)) || montoSenia.equals(BigDecimal.valueOf(0))) {
			Validador.mostrarMensaje("Monto de la seña o cantidad de horas inválido.");
			return false;
		}

		if(!controladorAgregarReservaCuarto1.getFormaPago().equals(FormaPago.EFECTIVO)) {
			if(controladorAgregarReservaCuarto1.getTipoTarjeta() == null || numeroTarjeta == null || fechaVencTarjeta == null || codSeguridadTarjeta == null)
			{
				Validador.mostrarMensaje("Por favor complete todos los campos de la tarjeta.");
				return false;
			}

			if(controladorAgregarReservaCuarto1.getTipoTarjeta().equals(TipoTarjeta.VISA) && !(Validador.formatoVisa(numeroTarjeta, codSeguridadTarjeta)) ) {
				Validador.mostrarMensaje("Error en datos para la tarjeta VISA");
				return false;
			}
			if(controladorAgregarReservaCuarto1.getTipoTarjeta().equals(TipoTarjeta.MASTERCARD) && !(Validador.formatoMaster(numeroTarjeta, codSeguridadTarjeta))) {
				Validador.mostrarMensaje("Error en datosz para la tarjeta MASTERCARD");
				return false;
			}
			if(!Validador.validarFechaVenc(fechaVencTarjeta)) {
				Validador.mostrarMensaje("Fecha de vencimiento de tarjeta inválida.");
				return false;
			}
		}
		return true;

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
		boolean condicionTipoTarjeta = false;

		condicionLista = condicionLista 
				&& !pedido.getListaPedidosEnTabla().isEmpty();

		condicionCliente = condicionCliente 
				&& pedido.getCmbBoxClientes().getSelectionModel().getSelectedItem() != null;

		condicionFormaPago = condicionFormaPago
				&& pedido.getCmbBoxFormasPago().getSelectionModel().getSelectedItem() != null;

		if(condicionFormaPago) {

			if(pedido.getCmbBoxFormasPago().getSelectionModel().getSelectedItem().equals("Efectivo")) {
				pagoEfectivo = true;
				condicionTipoTarjeta = true;

			}else {

				if(pedido.getCmbBoxTiposTarjeta().getSelectionModel().getSelectedItem() != null) {
					condicionTipoTarjeta = true;

					if(pedido.getCmbBoxTiposTarjeta().getSelectionModel().getSelectedItem().equals("VISA")) {
						condicionVisa = condicionVisa 
								&& formatoVisa(pedido.getNumTarjeta().getText(), pedido.getCodSegTarjeta().getText());

						condicionInput = condicionInput
								&& !pedido.getNumTarjeta().getText().equals("")
								&& !pedido.getFechaVencTarjeta().getText().equals("")
								&& !pedido.getCodSegTarjeta().getText().equals("");

						condicionFormato = condicionFormato
								&& formatoNumerico(pedido.getNumTarjeta().getText())
								&& formatoNumerico(pedido.getCodSegTarjeta().getText());

						condicionFechaVenc = condicionFechaVenc
								&& validarFechaVenc(pedido.getFechaVencTarjeta().getText())
								&& validarMesAnio(pedido.getFechaVencTarjeta().getText());
					}

					if(pedido.getCmbBoxTiposTarjeta().getSelectionModel().getSelectedItem().equals("MASTERCARD")) {
						condicionMaster = condicionMaster
								&& formatoMaster(pedido.getNumTarjeta().getText(), pedido.getCodSegTarjeta().getText());

						condicionInput = condicionInput
								&& !pedido.getNumTarjeta().getText().equals("")
								&& !pedido.getFechaVencTarjeta().getText().equals("")
								&& !pedido.getCodSegTarjeta().getText().equals("");

						condicionFormato = condicionFormato
								&& formatoNumerico(pedido.getNumTarjeta().getText())
								&& formatoNumerico(pedido.getCodSegTarjeta().getText());

						condicionFechaVenc = condicionFechaVenc
								&& validarFechaVenc(pedido.getFechaVencTarjeta().getText())
								&& validarMesAnio(pedido.getFechaVencTarjeta().getText());
					}					
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
					+ "Formato correcto: mm/aa.(Ej: 07/20, 10/20) \n"
					+ "Solo se permiten tarjetas del mes actual o en adelante y del año actual");
		}
		if(!condicionTipoTarjeta) {
			mostrarMensaje("Elija un tipo de tarjeta");
		}

		if(pagoEfectivo) {
			condicionCompleta = condicionLista && condicionCliente 
					&& condicionFormaPago && pagoEfectivo;
		}else {
			condicionCompleta = condicionLista && condicionCliente && condicionInput 
					&& condicionFormato && condicionFechaVenc && condicionVisa 
					&& condicionMaster && condicionFormaPago && condicionTipoTarjeta;
		}	

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

	//Validaciones para cuarto
	public static boolean validarCuarto(ControladorAgregarCuarto cuarto) {
		boolean condicionCompleta = true;
		boolean condicionInput = true;
		boolean condicionFormato = true;

		condicionInput = condicionInput
				&& !cuarto.getTxtCapacidad().getText().equals("")
				&& !cuarto.getTxtMonto().getText().equals("")
				&& !cuarto.getTxtMontoSenia().getText().equals("")
				&& !cuarto.getTxtPiso().getText().equals("")
				&& !cuarto.getTxtHabitacion().getText().equals("")
				&& cuarto.getCmbBoxCatesCuarto().getSelectionModel().getSelectedItem() != null;

		condicionFormato = condicionFormato
				&& formatoNumero(cuarto.getTxtCapacidad().getText())
				&& formatoNumeroConPunto(cuarto.getTxtMonto().getText())
				&& formatoNumero(cuarto.getTxtMontoSenia().getText())
				&& formatoNumero(cuarto.getTxtPiso().getText())
				&& formatoLetraEspacio(cuarto.getTxtHabitacion().getText());

		if(!condicionInput) {
			mostrarMensaje("Hay campos sin completar o no eligio una categoria para el cuarto");
		}

		if(!condicionFormato) {
			mostrarMensaje("Formato no valido");
		}

		condicionCompleta = condicionInput && condicionFormato;

		return condicionCompleta;
	}

	//Validaciones para producto
	public static boolean validarProducto(ControladorAgregarProducto producto) {
		boolean condicionCompleta = true;
		boolean condicionInput = true;
		boolean condicionFormato = true;

		condicionInput = condicionInput
				&& !producto.getTxtNombre().getText().equals("")
				&& !producto.getTxtPrecio().getText().equals("")
				&& !producto.getTxtDescripcion().getText().equals("")
				&& !producto.getTxtProveedor().getText().equals("");

		condicionFormato = condicionFormato
				&& formatoLetrasNumerosEspacios(producto.getTxtNombre().getText())
				&& formatoNumeroConPunto(producto.getTxtPrecio().getText())
				&& formatoLetrasNumerosEspacios(producto.getTxtDescripcion().getText())
				&& formatoLetrasNumerosEspacios(producto.getTxtProveedor().getText());

		if(!condicionInput) {
			mostrarMensaje("Hay campos sin completar");
		}

		if(!condicionFormato) {
			mostrarMensaje("Formato no valido");
		}

		condicionCompleta = condicionInput && condicionFormato;

		return condicionCompleta;
	}

	public static boolean formatoNumeroConPunto(String numero) {
		return numero.matches("[0-9]+(\\.[0-9]+)?");
	}

	public static boolean formatoLetrasNumerosEspacios(String texto) {
		return texto.matches("[a-zA-Z0-9\\s]+");
	}

	public static <T> boolean consultarRepetidos(T objeto, ArrayList<T> listaDeObjetos) {

		if(objeto instanceof CategoriaCuartoDTO && listaDeObjetos.get(0) instanceof CategoriaCuartoDTO) {
			for(T cate: listaDeObjetos) {			
				if(((CategoriaCuartoDTO) cate).getNombre().equals(((CategoriaCuartoDTO)objeto).getNombre())) {				
					return true;
				}
			}
			return false;
		}
		else if(objeto instanceof ClienteDTO && listaDeObjetos.get(0) instanceof ClienteDTO) {
			for(T cli: listaDeObjetos) {						
				if(((ClienteDTO) cli).getEmail().equals(((ClienteDTO)objeto).getEmail())){				
					return true;
				}
			}
			return false;
		}
		else if(objeto instanceof CuartoDTO && listaDeObjetos.get(0) instanceof CuartoDTO) {
			for(T cuarto: listaDeObjetos) {			
				if(((CuartoDTO) cuarto).getPiso().equals(((CuartoDTO)objeto).getPiso()) && 
						((CuartoDTO) cuarto).getHabitacion().equals(((CuartoDTO)objeto).getHabitacion())){				
					return true;
				}
			}
			return false;
		}
		else if(objeto instanceof UsuarioDTO && listaDeObjetos.get(0) instanceof UsuarioDTO) {
			for(T usuario: listaDeObjetos) {			
				if(((ClienteDTO) usuario).getEmail().equals(((UsuarioDTO)objeto).getEmail())){				
					return true;
				}
			}
			return false;
		}
		else if(objeto instanceof CategoriaEventoDTO && listaDeObjetos.get(0) instanceof CategoriaEventoDTO) {
			for(T cateEvento: listaDeObjetos) {			
				if(((CategoriaEventoDTO) cateEvento).getNombre().equals(((CategoriaEventoDTO)objeto).getNombre())
						&& ((CategoriaEventoDTO) cateEvento).getDetalle().equals(((CategoriaEventoDTO)objeto).getDetalle())){				
					return true;
				}
			}
			return false;
		}
		else if(objeto instanceof SalonDTO && listaDeObjetos.get(0) instanceof SalonDTO) {
			for(T salon: listaDeObjetos) {			
				if(((SalonDTO) salon).getEstilo().equals(((SalonDTO)objeto).getEstilo())){				
					return true;
				}
			}
			return false;
		}
		else if(objeto instanceof ProductoDTO && listaDeObjetos.get(0) instanceof ProductoDTO) {
			for(T producto: listaDeObjetos) {			
				if(((ProductoDTO) producto).getNombre().equals(((ProductoDTO)objeto).getNombre())){				
					return true;
				}
			}
			return false;
		}
		
	else if(objeto instanceof PermisoPerfilDTO && listaDeObjetos.get(0) instanceof PermisoPerfilDTO) {
		for(T permisoPefil: listaDeObjetos) {	
			int pId=(int)((PermisoPerfilDTO) permisoPefil).getIdPerfil();
			
			if((int)((PermisoPerfilDTO) permisoPefil).getIdPerfil() == (int)((PermisoPerfilDTO) objeto).getIdPerfil() 
					&& (int)((PermisoPerfilDTO) permisoPefil).getIdPermiso() == (int)((PermisoPerfilDTO) objeto).getIdPermiso()){				
				return true;
			}
		}
		return false;
	}

		return false;
	}

	private PermisoPerfilDTO getIdPerfil() {
		// TODO Auto-generated method stub
		return null;
	}


}
