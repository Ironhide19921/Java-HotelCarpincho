package dto;

public class CategoriaCuartoDTO {
	private int idCategoriaCuarto;
	private String Nombre;
	private String Detalle;
	
	public CategoriaCuartoDTO( int idCategoriaCuarto, String Nombre, String Detalle){
		this.idCategoriaCuarto = idCategoriaCuarto;
		this.Nombre = Nombre;
		this.Detalle = Detalle;
	}

	public int getIdCategoriaCuarto() {
		return idCategoriaCuarto;
	}

	public void setIdCategoriaCuarto(int idCategoriaCuarto) {
		this.idCategoriaCuarto = idCategoriaCuarto;
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
