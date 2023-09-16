package br.com.tcc.guardia.rosa.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
	private String titulo;
	@	NotNull @NotBlank
	private String conteudo;
	private Long curtidas;
	@	NotNull @NotBlank
	private Usuario usuario;
	@	NotNull @NotBlank
	private LocalDate dataCriacao;
	
	public Post() {}
	
	public Post(Long id, @NotNull @NotBlank String titulo, @NotNull @NotBlank String conteudo, Long curtidas,
			@NotNull @NotBlank Usuario usuario, @NotNull @NotBlank LocalDate dataCriacao) {
		this.id = id;
		this.titulo = titulo;
		this.conteudo = conteudo;
		this.curtidas = curtidas;
		this.usuario = usuario;
		this.dataCriacao = dataCriacao;
	}
	
	public Post(@NotNull @NotBlank String titulo, @NotNull @NotBlank String conteudo,
			@NotNull @NotBlank Usuario usuario, @NotNull @NotBlank LocalDate dataCriacao) {
		this.titulo = titulo;
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
	public LocalDate getDataCriacao() {
		return dataCriacao;
	}
	public void setDataCriacao(LocalDate dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
