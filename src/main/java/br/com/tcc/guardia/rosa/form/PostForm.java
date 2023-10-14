package br.com.tcc.guardia.rosa.form;

import java.time.LocalDateTime;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import br.com.tcc.guardia.rosa.business.UsuarioBusiness;
import br.com.tcc.guardia.rosa.model.Post;

public class PostForm {
	
	@NotNull @NotEmpty
	private String conteudo;
	@NotNull @Min(value = 1)
	private Long usuarioId;
	private LocalDateTime dataCriacao = LocalDateTime.now();
	
	public String getConteudo() {
		return conteudo;
	}
	public void setConteudo(String conteudo) {
		this.conteudo = conteudo;
	}
	public Long getUsuarioId() {
		return usuarioId;
	}
	public void setUsuarioId(Long usuarioId) {
		this.usuarioId = usuarioId;
	}
	public LocalDateTime getDataCriacao() {
		return dataCriacao;
	}
	public void setDataCriacao(LocalDateTime dataCriacao) {
		this.dataCriacao = dataCriacao;
	}
	
	public Post toPost(UsuarioBusiness business) {
		Post post  = new Post(conteudo, business.findById(usuarioId).get(), dataCriacao);
		return post;
	}
	
}
