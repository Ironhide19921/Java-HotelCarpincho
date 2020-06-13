package dto;

public class PermisoPerfilDTO {
	
	private int idPermisoPerfil;
	private int idPerfil;
	private int idPermiso;
	
	public PermisoPerfilDTO(int idPermisoPerfil, int idPerfil, int idPermiso) {
		this.idPerfil = idPerfil;
		this.idPermiso = idPermiso;
		this.idPermisoPerfil = idPermisoPerfil;
	}
	
	public int getIdPerfil() {
		return idPerfil;
	}

	public void setIdPerfil(int idPerfil) {
		this.idPerfil = idPerfil;
	}

	public int getIdPermiso() {
		return idPermiso;
	}

	public void setIdPermiso(int idPermiso) {
		this.idPermiso = idPermiso;
	}

	public PermisoPerfilDTO(int idPermisoPerfil) {
		this.idPermisoPerfil = idPermisoPerfil;
	}

	public int getIdPermisoPerfil() {
		return idPermisoPerfil;
	}

	public void setIdPermisoPerfil(int idPermisoPerfil) {
		this.idPermisoPerfil = idPermisoPerfil;
	}



	}




