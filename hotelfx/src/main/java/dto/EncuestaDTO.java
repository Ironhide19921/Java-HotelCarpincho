package dto;

public class EncuestaDTO {
	
	private int idEncuesta;
	private int idCliente;
	private String recipiente;
	
	public EncuestaDTO(int idEncuesta, int idCliente, String recipiente) {
		
		this.setIdEncuesta(idEncuesta);
		this.setIdCliente(idCliente);
		this.setRecipiente(recipiente);
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

}
