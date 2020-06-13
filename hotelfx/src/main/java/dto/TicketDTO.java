package dto;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class TicketDTO {
	
	private int idTicket;
	private int idCliente;
	private BigDecimal precioTotal;
	private String descripcion;
	private String path;
	private Timestamp fecha;
	
	public TicketDTO(int idTicket, int idCliente, BigDecimal precioTotal, String descripcion, String path, Timestamp fecha) 
	{
		this.setIdTicket(idTicket);
		this.idCliente = idCliente;
		this.setPrecioTotal(precioTotal);
		this.setDescripcion(descripcion);
		this.setPath(path);
		this.setFecha(fecha);
		
	}

	public int getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(int idCliente) {
		this.idCliente = idCliente;
	}

	public int getIdTicket() {
		return idTicket;
	}

	public void setIdTicket(int idTicket) {
		this.idTicket = idTicket;
	}

	public BigDecimal getPrecioTotal() {
		return precioTotal;
	}

	public void setPrecioTotal(BigDecimal precioTotal) {
		this.precioTotal = precioTotal;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Timestamp getFecha() {
		return fecha;
	}

	public void setFecha(Timestamp fecha) {
		this.fecha = fecha;
	}


	
	

}
