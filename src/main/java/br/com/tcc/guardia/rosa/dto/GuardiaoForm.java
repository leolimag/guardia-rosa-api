package br.com.tcc.guardia.rosa.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import br.com.tcc.guardia.rosa.business.UsuarioBusiness;
import br.com.tcc.guardia.rosa.model.Guardiao;

public class GuardiaoForm {
	
	@NotNull @NotEmpty
	private String nome;
	@NotNull @NotEmpty
	private String email;
	@NotNull @NotEmpty
	private String telefone;
	@Min(1)
	private Long usuarioId;
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	public Long getUsuarioId() {
		return usuarioId;
	}
	public void setUsuarioId(Long usuarioId) {
		this.usuarioId = usuarioId;
	}
	
	public Guardiao toGuardiao(UsuarioBusiness business) {
		Guardiao guardiao = new Guardiao(this.nome, this.email, this.telefone, business.findById(usuarioId).get());
		return guardiao;
	}

}
