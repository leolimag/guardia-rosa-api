package br.com.tcc.guardia.rosa.business;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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

}
