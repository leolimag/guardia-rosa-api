package br.com.tcc.guardia.rosa.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import org.springframework.data.domain.Page;

import br.com.tcc.guardia.rosa.model.Post;
import br.com.tcc.guardia.rosa.model.Usuario;

public class PostDTO {
	
	private Long id;
	private String conteudo;
	private Usuario usuario;
	private LocalDateTime dataCriacao;
	private String dataFormatada;
	private Long curtidas;
    private Long comentarios;
    private boolean curtido;
	
	public PostDTO() {}
	
	public PostDTO(Post post) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d 'de' MMMM 'de' yyyy',  ' HH:mm", new Locale("pt", "BR"));
        
		this.id = post.getId();
		this.conteudo = post.getConteudo();
		this.usuario = post.getUsuario();
		this.dataCriacao = post.getDataCriacao();
		this.dataFormatada = this.dataCriacao.format(formatter);
		this.curtidas = post.getCurtidas();
		this.comentarios = post.getComentarios();
		this.curtido = post.isCurtido();
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
	public boolean isCurtido() {
		return curtido;
	}
	public void setCurtido(boolean curtido) {
		this.curtido = curtido;
	}
	public static Page<PostDTO> toPostsDTO(Page<Post> posts) {
		return posts.map(PostDTO::new);
	}
	
}
