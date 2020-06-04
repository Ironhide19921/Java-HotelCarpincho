package presentacion.controlador;

import java.math.BigDecimal;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ResourceBundle;

import dto.ReservaCuartoDTO.estadosReserva;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class ControladorAgregarReservaCuarto implements Initializable{
	
	/*public enum estadosReserva {Pendiente,Cancelado,En_curso,Finalizado};
	private Integer idReserva,idCliente,idCuarto,idUsuario;
	private BigDecimal senia,montoReservaCuarto;
	private String emailFacturacion, numTarjeta, cantidadDias,
	tipoTarjeta, formaDePago, codSeguridadTarjeta,comentarios,fechaVencTarjeta;
	
	private estadosReserva estadoReserva;
	private Timestamp fechaReserva,fechaCheckIn,fechaOut,
	fechaIngreso,fechaEgreso;

	private boolean estado;
*/
	@FXML private Button btnAgregar;
	@FXML private Button btnModificar;
	@FXML private Button btnCerrar;
	@FXML private Button btnAgregarCliente;
	@FXML private Button btnAgregarCuarto;
	@FXML private TextField usuario;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}

}
