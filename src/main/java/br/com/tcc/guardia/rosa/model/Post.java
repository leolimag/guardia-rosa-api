package br.com.tcc.guardia.rosa.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Formula;

@Entity
@Table(name = "post")
public class Post {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@	NotNull @NotBlank
	private String conteudo;
	@ManyToOne
	private Usuario usuario;
	private LocalDateTime dataCriacao;
    @Formula("(SELECT COUNT(*) FROM curtida_post cp WHERE cp.post_id = id)")
	private Long curtidas;
    @Formula("(SELECT COUNT(*) FROM comentario c WHERE c.post_id = id)")
    private Long comentarios;
    private boolean curtido;
    @Formula("(SELECT u.foto from usuario u WHERE u.id = usuario_id)")
    private String foto;
	
	public Post() {}
	
	public Post(Long id, @NotNull @NotBlank String conteudo,
			@NotNull @NotBlank Usuario usuario, @NotNull @NotBlank LocalDateTime dataCriacao) {
		this.id = id;
		this.conteudo = conteudo;
		this.usuario = usuario;
		this.dataCriacao = dataCriacao;
	}
	
	public Post( @NotNull @NotBlank String conteudo,
			@NotNull @NotBlank Usuario usuario, @NotNull @NotBlank LocalDateTime dataCriacao) {
		this.conteudo = conteudo;
		this.usuario = usuario;
		this.dataCriacao = dataCriacao;
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
	public static long getSerialversionuid() {
		return serialVersionUID;
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
	public String getFoto() {
		return foto;
	}
	public void setFoto(String foto) {
		this.foto = foto;
	}
	
}
