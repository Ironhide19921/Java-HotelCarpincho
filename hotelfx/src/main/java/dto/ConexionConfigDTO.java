package dto;

public class ConexionConfigDTO {
	private int idConexionConfig;
	private String host;
	private String user;
	private String pass;
	
	public ConexionConfigDTO(int idConexionConfig, String host, String user, String pass) {
		this.idConexionConfig = idConexionConfig;
		this.host = host;
		this.user = user;
		this.pass = pass;
	}

	public int getIdConexionConfig() {
		return idConexionConfig;
	}

	public void setIdConexionConfig(int idConexion) {
		this.idConexionConfig = idConexion;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}
	
}
