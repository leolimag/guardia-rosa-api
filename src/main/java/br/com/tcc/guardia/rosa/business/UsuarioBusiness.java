package br.com.tcc.guardia.rosa.business;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import br.com.tcc.guardia.rosa.model.Usuario;
import br.com.tcc.guardia.rosa.repository.UsuarioRepository;

@Service
public class UsuarioBusiness {
	
	@Autowired
	private UsuarioRepository repository;
	
	public Optional<Usuario> findById(Long id) {
		return repository.findById(id);
	}
	
	public Optional<Usuario> findUserByEmail(String email) {
		return repository.findUserByEmail(email);
	}
	
	public UserDetails findByEmail(String email) {
		return repository.findByEmail(email);
	}
	
	public void save(Usuario usuario) {
		repository.save(usuario);
	}

}
