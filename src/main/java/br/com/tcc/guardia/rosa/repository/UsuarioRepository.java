package br.com.tcc.guardia.rosa.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;

import br.com.tcc.guardia.rosa.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	
	UserDetails findByEmail(String email);
	
	@Query("SELECT u FROM Usuario u WHERE u.email = :email")
	Optional<Usuario> findUserByEmail(String email);
	
}
