package br.com.tcc.guardia.rosa.dto;

public class LoginResponseDTO {
	
	private Long id;
	private String name;
	private String email;
	private String token;
	private String foto;
	
	public LoginResponseDTO(Long id, String name, String email,  String token, String foto) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.token = token;
		this.foto = foto;
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
	public String getFoto() {
		return foto;
	}
	public void setFoto(String foto) {
		this.foto = foto;
	}
	
}
