package br.com.tcc.guardia.rosa.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

public class ResetPasswordDTO {
	
	@Email(message = "Please provide a valid email address")  @NotNull @NotBlank @NotEmpty
	private String email;
	@NotNull @NotBlank @NotEmpty @Length(min = 5)
	private String senha;
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
	
}
