package dto;

import java.sql.Timestamp;

public class ErrorImportarDTO {
	
	private int idError;
	private String detalle;
	private Timestamp fecha;
	private int usuario;
	
	public ErrorImportarDTO(int idError, Timestamp fecha, int usuario, String detalle) {
		this.idError = idError;
		this.fecha = fecha;
		this.usuario = usuario;
		this.detalle = detalle;		
	}

	public int getIdError() {
		return idError;
	}

	public void setIdError(int idError) {
		this.idError = idError;
	}

	public String getDetalle() {
		return detalle;
	}

	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}

	public Timestamp getFecha() {
		return fecha;
	}

	public void setFecha(Timestamp fecha) {
		this.fecha = fecha;
	}

	public int getUsuario() {
		return usuario;
	}

	public void setUsuario(int usuario) {
		this.usuario = usuario;
	}


}
