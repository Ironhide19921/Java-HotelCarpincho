package dto;

public class CuartoDTO {
	
	private int id;
	private String capacidad;
	private double monto;
	private int montoSenia;
	private String piso;
	private String habitacion;
	private Boolean estado;
	private int idCateCuarto;
	
	public CuartoDTO(int id, int idCateCuarto, String capacidad, double monto, int montoSenia, String piso, String habitacion, Boolean estado) {
		this.id = id;
		this.idCateCuarto = idCateCuarto;
		this.capacidad = capacidad;
		this.monto = monto;
		this.montoSenia = montoSenia;
		this.piso = piso;
		this.habitacion = habitacion;
		this.estado = estado;		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCapacidad() {
		return capacidad;
	}

	public void setCapacidad(String capacidad) {
		this.capacidad = capacidad;
	}

	public double getMonto() {
		return monto;
	}

	public void setMonto(double monto) {
		this.monto = monto;
	}

	public int getMontoSenia() {
		return montoSenia;
	}

	public void setMontoSenia(int montoSenia) {
		this.montoSenia = montoSenia;
	}

	public String getPiso() {
		return piso;
	}

	public void setPiso(String piso) {
		this.piso = piso;
	}

	public String getHabitacion() {
		return habitacion;
	}

	public void setHabitacion(String habitacion) {
		this.habitacion = habitacion;
	}

	public Boolean getEstado() {
		return estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}

	public int getIdCateCuarto() {
		return idCateCuarto;
	}

	public void setIdCateCuarto(int idCateCuarto) {
		this.idCateCuarto = idCateCuarto;
	}

}
