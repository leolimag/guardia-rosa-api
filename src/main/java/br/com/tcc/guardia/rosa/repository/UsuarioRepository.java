package br.com.tcc.guardia.rosa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import br.com.tcc.guardia.rosa.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	
	UserDetails findByEmail(String email);
	
}
