package br.com.tcc.guardia.rosa.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import org.springframework.data.domain.Page;

import br.com.tcc.guardia.rosa.model.Comentario;
import br.com.tcc.guardia.rosa.model.Post;
import br.com.tcc.guardia.rosa.model.Usuario;

public class ComentarioDTO {
	
	private Long id;
	private String conteudo;
	private Long curtidas;
	private Usuario usuario;
	private Post post;
	private LocalDateTime dataCriacao;
	private String dataFormatada;
	
	public ComentarioDTO(Comentario comentario) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d 'de' MMMM 'de' yyyy',  ' HH:mm", new Locale("pt", "BR"));

		this.id = comentario.getId();
		this.conteudo = comentario.getConteudo();
		this.curtidas = comentario.getCurtidas();
		this.usuario = comentario.getUsuario();
		this.post = comentario.getPost();
		this.dataCriacao = comentario.getDataCriacao();
		this.dataFormatada = this.dataCriacao.format(formatter);
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
	public Post getPost() {
		return post;
	}
	public void setPost(Post post) {
		this.post = post;
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
	public static Page<ComentarioDTO> toComentarioDTO(Page<Comentario> comentarios) {
		return comentarios.map(ComentarioDTO::new);
	}
	
}
