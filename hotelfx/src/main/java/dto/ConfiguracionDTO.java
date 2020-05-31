package dto;

public class ConfiguracionDTO {
	
	private int idConfig;
	private String username;
	private String password;
	private String provSMTP;

	
	public ConfiguracionDTO(int idConfig, String username, String password, String provSMTP) {
		this.idConfig = idConfig;
		this.username = username;
		this.password = password;
		this.provSMTP = provSMTP;
				
	}

	public String getProvSMTP() {
		return provSMTP;
	}

	public void setProvSMTP(String provSMTP) {
		this.provSMTP = provSMTP;
	}

	public int getIdConfig() {
		return idConfig;
	}

	public void setIdConfig(int idConfig) {
		this.idConfig = idConfig;
	}
	
	public String getUsername() {
		return this.username ;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password ) {
		this.password = password;
	}


}
