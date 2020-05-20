package dto;

public class PermisoDTO {
	
	private int idPermiso;
	private String nombre;
	
	public PermisoDTO(int id, String nombre) {
		this.idPermiso = id;
		this.nombre = nombre;
	}
	
	public int getIdPermiso() {
		return idPermiso;
	}
	public void setIdPermiso(int idPermiso) {
		this.idPermiso = idPermiso;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}
