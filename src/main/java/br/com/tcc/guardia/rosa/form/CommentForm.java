package br.com.tcc.guardia.rosa.form;

import java.time.LocalDateTime;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import br.com.tcc.guardia.rosa.business.PostBusiness;
import br.com.tcc.guardia.rosa.business.UsuarioBusiness;
import br.com.tcc.guardia.rosa.model.Comentario;

public class CommentForm {
	
	@NotNull @NotEmpty
	private String conteudo;
	@NotNull @Min(value = 1)
	private Long usuarioId;
	@NotNull @Min(value = 1)
	private Long postId;
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
	public Long getPostId() {
		return postId;
	}
	public void setPostId(Long postId) {
		this.postId = postId;
	}
	
	public Comentario toComentario(UsuarioBusiness business, PostBusiness postBusiness) {
		Comentario comentario = new Comentario();
		comentario.setConteudo(conteudo);
		comentario.setUsuario(business.findById(usuarioId).get());
		comentario.setPost(postBusiness.findById(postId));
		comentario.setDataCriacao(dataCriacao);
		
		return comentario;
	}

}
