package dto;

import java.awt.List;
import java.util.ArrayList;

public class PerfilDTO {
	
	private int idPerfil;
	private String nombre;
	
	public PerfilDTO(int idPerfil, String nombre){
		
		this.idPerfil = idPerfil;
		this.nombre = nombre;
		
	}

	public int getIdPerfil() {
		return idPerfil;
	}

	public void setIdPerfil(int idPerfil) {
		this.idPerfil = idPerfil;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String toString() {
		return (this.idPerfil + "- " + this.nombre);
	}
		
}
