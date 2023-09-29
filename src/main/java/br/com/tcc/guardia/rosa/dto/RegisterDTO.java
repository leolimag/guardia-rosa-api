package br.com.tcc.guardia.rosa.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

public class RegisterDTO {
	
	@Email(message = "Please provide a valid email address") @NotNull @NotBlank @NotEmpty
	private String email;
	@NotNull @NotBlank @NotEmpty @Length(min = 5)
	private String password;
	@NotNull @NotBlank @NotEmpty
	private String name;
	@NotNull @NotBlank @NotEmpty @Length(max = 11)
	private String cpf;
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

}
