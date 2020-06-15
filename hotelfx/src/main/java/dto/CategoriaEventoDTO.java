package dto;

public class CategoriaEventoDTO {
	private int id;
	private String Nombre;
	private String Detalle;
	
	public CategoriaEventoDTO( int idCategoriaEvento, String Nombre, String Detalle){
		this.id = idCategoriaEvento;
		this.Nombre = Nombre;
		this.Detalle = Detalle;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return Nombre;
	}

	public void setNombre(String nombre) {
		Nombre = nombre;
	}

	public String getDetalle() {
		return Detalle;
	}

	public void setDetalle(String detalle) {
		Detalle = detalle;
	}
}