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

@Entity
@Table(name = "post")
public class Post {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@	NotNull @NotBlank
	private String conteudo;
	private Long curtidas = 0l;
	@ManyToOne
	private Usuario usuario;
	private LocalDateTime dataCriacao;
	
	public Post() {}
	
	public Post(Long id, @NotNull @NotBlank String conteudo, Long curtidas,
			@NotNull @NotBlank Usuario usuario, @NotNull @NotBlank LocalDateTime dataCriacao) {
		this.id = id;
		this.conteudo = conteudo;
		this.curtidas = curtidas;
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
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
