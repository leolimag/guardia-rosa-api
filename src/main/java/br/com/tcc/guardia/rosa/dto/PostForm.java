package br.com.tcc.guardia.rosa.dto;

import java.time.LocalDate;

import br.com.tcc.guardia.rosa.business.UsuarioBusiness;
import br.com.tcc.guardia.rosa.model.Post;

public class PostForm {
	
	private String titulo;
	private String conteudo;
	private Long usuarioId;
	private LocalDate dataCriacao = LocalDate.now();
	
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
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
	public LocalDate getDataCriacao() {
		return dataCriacao;
	}
	public void setDataCriacao(LocalDate dataCriacao) {
		this.dataCriacao = dataCriacao;
	}
	
	public Post toPost(UsuarioBusiness business) {
		Post post  = new Post(titulo, conteudo, business.findById(usuarioId).get(), dataCriacao);
		return post;
	}
	
}
