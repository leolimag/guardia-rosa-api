package br.com.tcc.guardia.rosa.dto;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;

import br.com.tcc.guardia.rosa.model.Post;
import br.com.tcc.guardia.rosa.model.Usuario;

public class PostDTO {
	
	private Long id;
	private String conteudo;
	private Long curtidas;
	private Usuario usuario;
	private LocalDateTime dataCriacao;
	
	public PostDTO() {}
	
	public PostDTO(Post post) {
		this.id = post.getId();
		this.conteudo = post.getConteudo();
		this.curtidas = post.getCurtidas();
		this.usuario = post.getUsuario();
		this.dataCriacao = post.getDataCriacao();
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getConteudo() {
		return conteudo;
	}
	public void setConteudo(String conteudo) {
		this.conteudo = conteudo;
	}
	public Long getCurtidas() {
		return curtidas;
	}
	public void setCurtidas(Long curtidas) {
		this.curtidas = curtidas;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public LocalDateTime getDataCriacao() {
		return dataCriacao;
	}
	public void setDataCriacao(LocalDateTime dataCriacao) {
		this.dataCriacao = dataCriacao;
	}
	public static Page<PostDTO> toPostsDTO(Page<Post> posts) {
		return posts.map(PostDTO::new);
	}
	
}
