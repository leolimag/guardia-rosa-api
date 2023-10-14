package br.com.tcc.guardia.rosa.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class UpdatePostForm {
	
	@NotNull @NotEmpty
	private String conteudo;
	
	public String getConteudo() {
		return conteudo;
	}
	public void setConteudo(String conteudo) {
		this.conteudo = conteudo;
	}
	
}
