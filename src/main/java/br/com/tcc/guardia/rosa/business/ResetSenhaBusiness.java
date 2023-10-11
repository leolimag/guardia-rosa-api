package br.com.tcc.guardia.rosa.business;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.tcc.guardia.rosa.exception.ExpirationCodeException;
import br.com.tcc.guardia.rosa.exception.InvalidCodeException;
import br.com.tcc.guardia.rosa.model.ResetSenha;
import br.com.tcc.guardia.rosa.model.Usuario;
import br.com.tcc.guardia.rosa.repository.ResetSenhaRepository;

@Service
public class ResetSenhaBusiness {
	
	@Autowired
	private ResetSenhaRepository resetSenhaRepository;
	
	public void saveResetSenha(Usuario usuario, Integer codigoDeVerificacao) {
	    ResetSenha myToken = new ResetSenha(usuario, codigoDeVerificacao);
	    resetSenhaRepository.save(myToken);
	}

	public ResetSenha findFirstByUsuarioOrderByDataExpiracaoDesc(Usuario usuario) {
		Optional<ResetSenha> opt = resetSenhaRepository.findFirstByUsuarioOrderByDataExpiracaoDesc(usuario);
		if (opt.isPresent()) {
			return opt.get();
		} 
		return null;
	}

	public boolean validateCode(ResetSenha resetSenha, Integer codigoDeVerificacao) throws ExpirationCodeException, InvalidCodeException {
		if (LocalDateTime.now(ZoneId.of("America/Sao_Paulo")).isAfter(resetSenha.getDataExpiracao())) {
			throw new ExpirationCodeException("Sinto muito, mas o tempo para mudar a senha expirou. Tente novamente!");
		} else {
			if (!resetSenha.getCodigoDeVerificacao().equals(codigoDeVerificacao)) {
				throw new InvalidCodeException("Código inválido.");
			}
		}
		return true;
	}

}
