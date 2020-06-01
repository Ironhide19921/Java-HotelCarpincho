package dto;

import java.sql.Date;
import java.time.LocalDate;

public class ClienteDTO {
	
	private int idCliente;
	private String nombre;
	private String apellido;
	private String tipoDocumento;
	private String numeroDocumento;
	private String email;
	private String telefono;
	private Boolean estado;
	private Date fechaNacimiento;
	
	public ClienteDTO(int idCliente, String nombre, String apellido, String tipoDocumento, String numeroDocumento, String email, 
			String telefono, Boolean estado, Date fechaNacimiento) {
		
		this.idCliente = idCliente;
		this.nombre = nombre;
		this.apellido = apellido;
		this.tipoDocumento = tipoDocumento;
		this.numeroDocumento = numeroDocumento;
		this.email = email;
		this.telefono = telefono;
		this.estado = estado;
		this.fechaNacimiento = fechaNacimiento;
		
	}

	public int getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(int idCliente) {
		this.idCliente = idCliente;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public String getNumeroDocumento() {
		return numeroDocumento;
	}

	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public Boolean getEstado() {
		return estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}
	
	
	
	

}
