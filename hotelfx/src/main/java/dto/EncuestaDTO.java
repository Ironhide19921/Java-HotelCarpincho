package dto;

public class EncuestaDTO {
	
	private int idEncuesta;
	private int idCliente;
	private String recipiente;
	private boolean encuestado;
	
	public EncuestaDTO(int idEncuesta, int idCliente, String recipiente, boolean encuestado) {
		
		this.setIdEncuesta(idEncuesta);
		this.setIdCliente(idCliente);
		this.setRecipiente(recipiente);
		this.setEncuestado(encuestado);
	}

	public int getIdEncuesta() {
		return idEncuesta;
	}

	public void setIdEncuesta(int idEncuesta) {
		this.idEncuesta = idEncuesta;
	}

	public int getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(int idCliente) {
		this.idCliente = idCliente;
	}

	public String getRecipiente() {
		return recipiente;
	}

	public void setRecipiente(String recipiente) {
		this.recipiente = recipiente;
	}

	public boolean isEncuestado() {
		return encuestado;
	}

	public void setEncuestado(boolean encuestado) {
		this.encuestado = encuestado;
	}

}
