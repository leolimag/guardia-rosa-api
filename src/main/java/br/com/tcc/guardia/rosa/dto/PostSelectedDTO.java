package br.com.tcc.guardia.rosa.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import org.hibernate.annotations.Formula;
import org.springframework.data.domain.Page;

import br.com.tcc.guardia.rosa.model.Post;
import br.com.tcc.guardia.rosa.model.Usuario;

public class PostSelectedDTO {
	
	private Long id;
	private String conteudo;
	private Usuario usuario;
	private LocalDateTime dataCriacao;
	private String dataFormatada;
    @Formula("(SELECT COUNT(*) FROM curtida_post cp WHERE cp.post_id = id)")
	private Long curtidas;
    private Long comentarios;
	
	public PostSelectedDTO() {}
	
	public PostSelectedDTO(Post post) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d 'de' MMMM 'de' yyyy',  ' HH:mm", new Locale("pt", "BR"));

		this.id = post.getId();
		this.conteudo = post.getConteudo();
		this.usuario = post.getUsuario();
		this.dataCriacao = post.getDataCriacao();
		this.dataFormatada = this.dataCriacao.format(formatter);
		this.curtidas = post.getCurtidas();
		this.comentarios = post.getComentarios();
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
	public String getDataFormatada() {
		return dataFormatada;
	}
	public void setDataFormatada(String dataFormatada) {
		this.dataFormatada = dataFormatada;
	}
	public Long getCurtidas() {
		return curtidas;
	}
	public void setCurtidas(Long curtidas) {
		this.curtidas = curtidas;
	}
	public Long getComentarios() {
		return comentarios;
	}
	public void setComentarios(Long comentarios) {
		this.comentarios = comentarios;
	}
	public static Page<PostSelectedDTO> toPostsSelectedDTO(Page<Post> posts) {
		return posts.map(PostSelectedDTO::new);
	}
	
}
