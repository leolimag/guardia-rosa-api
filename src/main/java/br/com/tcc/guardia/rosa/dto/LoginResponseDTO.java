package br.com.tcc.guardia.rosa.dto;

public class LoginResponseDTO {
	
	private Long id;
	private String name;
	private String email;
	private String telefone;
	private String token;
	
	public LoginResponseDTO(Long id, String name, String email, String telefone, String token) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.token = token;
		this.telefone = telefone;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	
}
