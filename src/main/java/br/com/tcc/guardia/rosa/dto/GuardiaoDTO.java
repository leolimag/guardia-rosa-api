package br.com.tcc.guardia.rosa.dto;

import java.util.List;
import java.util.stream.Collectors;

import br.com.tcc.guardia.rosa.model.Guardiao;
import br.com.tcc.guardia.rosa.model.Usuario;

public class GuardiaoDTO {
	
	private Long id;
	private String nome;
	private String telefone;
	private Usuario usuario;
	private Boolean favorito;
	
	public GuardiaoDTO() {
		
	}
	
	public GuardiaoDTO(Guardiao guardiao) {
		super();
		this.id = guardiao.getId();
		this.nome = guardiao.getNome();
		this.telefone = guardiao.getTelefone();
		this.usuario = guardiao.getUsuario();
		this.favorito = guardiao.isFavorito();
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
	public Boolean getFavorito() {
		return favorito;
	}

	public void setFavorito(Boolean favorito) {
		this.favorito = favorito;
	}

	public static List<GuardiaoDTO> toGuardioesDTO(List<Guardiao> guardioes) {
	    return guardioes.stream()
	            .map(GuardiaoDTO::new)
	            .collect(Collectors.toList());
	}

}
