package br.com.tcc.guardia.rosa.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.tcc.guardia.rosa.model.ResetSenha;
import br.com.tcc.guardia.rosa.model.Usuario;

public interface ResetSenhaRepository extends JpaRepository<ResetSenha, Long> {

    Optional<ResetSenha> findFirstByUsuarioOrderByDataExpiracaoDesc(Usuario usuario);
	
}
