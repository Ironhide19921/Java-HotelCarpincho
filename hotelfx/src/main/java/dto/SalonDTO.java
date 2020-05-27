package dto;

import java.math.BigDecimal;

public class SalonDTO {
	private int id;
	private int capacidad;
	private BigDecimal senia;
	private String estilo;
	private BigDecimal monto;
	private Boolean estado;
	
	public SalonDTO(int id, int capacidad, BigDecimal senia, String estilo, BigDecimal monto, Boolean estado) {
		this.id = id;
		this.capacidad = capacidad;
		this.senia = senia;
		this.estilo = estilo;
		this.monto = monto;
		this.estado = estado;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCapacidad() {
		return capacidad;
	}

	public void setCapacidad(int capacidad) {
		this.capacidad = capacidad;
	}

	public BigDecimal getSenia() {
		return senia;
	}

	public void setSenia(BigDecimal senia) {
		this.senia = senia;
	}

	public String getEstilo() {
		return estilo;
	}

	public void setEstilo(String estilo) {
		this.estilo = estilo;
	}

	public BigDecimal getMonto() {
		return monto;
	}

	public void setMonto(BigDecimal monto) {
		this.monto = monto;
	}

	public Boolean getEstado() {
		return estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}
}
