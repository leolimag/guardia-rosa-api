package br.com.tcc.guardia.rosa.dto;

import org.springframework.data.domain.Page;

import br.com.tcc.guardia.rosa.model.Guardiao;
import br.com.tcc.guardia.rosa.model.Usuario;

public class GuardiaoDTO {
	
	private Long id;
	private String nome;
	private String email;
	private String telefone;
	private Usuario usuario;
	
	public GuardiaoDTO() {
		
	}
	
	public GuardiaoDTO(Guardiao guardiao) {
		super();
		this.id = guardiao.getId();
		this.nome = guardiao.getNome();
		this.email = guardiao.getEmail();
		this.telefone = guardiao.getTelefone();
		this.usuario = guardiao.getUsuario();
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
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
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public static Page<GuardiaoDTO> toGuardioesDTO(Page<Guardiao> guardioes) {
		return guardioes.map(GuardiaoDTO::new);
	}

}