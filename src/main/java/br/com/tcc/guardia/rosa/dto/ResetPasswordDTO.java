package br.com.tcc.guardia.rosa.dto;

public class ResetPasswordDTO {
	
	private String email;
	private Integer codigoDeVerificacao;
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getCodigoDeVerificacao() {
		return codigoDeVerificacao;
	}

	public void setCodigoDeVerificacao(Integer codigoDeVerificacao) {
		this.codigoDeVerificacao = codigoDeVerificacao;
	}
	
}
