package br.com.tcc.guardia.rosa.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.tcc.guardia.rosa.model.CurtidaPost;
import br.com.tcc.guardia.rosa.model.Post;
import br.com.tcc.guardia.rosa.model.Usuario;
import br.com.tcc.guardia.rosa.repository.CurtidaPostRepository;

@Service
public class CurtidaPostBusiness {

	@Autowired
	private CurtidaPostRepository repository;
	
	public boolean isLikedByUser(Usuario usuario, Post post) {
		if (repository.isLikedByUser(usuario, post) > 0) {
			return true;
		}
		return false;
	}
	
	public void save(CurtidaPost curtidaPost) {
		repository.save(curtidaPost);
	}

	public void delete(CurtidaPost curtidaPost) {
		repository.delete(curtidaPost);
	}

	public CurtidaPost findByPostAndUsuario(Post post, Usuario usuario) {
		return repository.findByPostAndUsuario(post, usuario);
	}
	
}
