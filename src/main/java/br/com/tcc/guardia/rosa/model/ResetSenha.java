package br.com.tcc.guardia.rosa.model;

import java.time.LocalDateTime;
import java.time.ZoneId;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class ResetSenha {
 
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @ManyToOne
    private Usuario usuario;
    private LocalDateTime dataExpiracao =  LocalDateTime.now(ZoneId.of("America/Sao_Paulo")).plusMinutes(30);
    private Integer codigoDeVerificacao;
    
    public ResetSenha() {} 
    
	public ResetSenha(Usuario usuario, Integer codigoDeVerificacao) {
		this.usuario = usuario;
		this.codigoDeVerificacao = codigoDeVerificacao;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public LocalDateTime getDataExpiracao() {
		return dataExpiracao;
	}

	public void setDataExpiracao(LocalDateTime dataExpiracao) {
		this.dataExpiracao = dataExpiracao;
	}

	public Integer getCodigoDeVerificacao() {
		return codigoDeVerificacao;
	}

	public void setCodigoDeVerificacao(Integer codigoDeVerificacao) {
		this.codigoDeVerificacao = codigoDeVerificacao;
	}
	
}