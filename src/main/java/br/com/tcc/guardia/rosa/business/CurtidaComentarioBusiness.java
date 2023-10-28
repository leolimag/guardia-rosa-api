package br.com.tcc.guardia.rosa.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.tcc.guardia.rosa.model.Comentario;
import br.com.tcc.guardia.rosa.model.CurtidaComentario;
import br.com.tcc.guardia.rosa.model.Post;
import br.com.tcc.guardia.rosa.model.Usuario;
import br.com.tcc.guardia.rosa.repository.CurtidaComentarioRepository;

@Service
public class CurtidaComentarioBusiness {
	
	@Autowired
	private CurtidaComentarioRepository repository;

	public boolean isLikedByUser(Usuario usuario, Post post, Comentario comentario) {
		if (repository.isLikedByUser(usuario, post, comentario) > 0) {
			return true;
		}
		return false;
	}
	
	public void save(CurtidaComentario curtidaComentario) {
		repository.save(curtidaComentario);
	}

	public void delete(CurtidaComentario curtidaComentario) {
		repository.delete(curtidaComentario);
	}

	public CurtidaComentario findByPostAndUsuarioAndComentario(Post post, Usuario usuario, Comentario comentario) {
		return repository.findByPostAndUsuarioAndComentario(post, usuario, comentario);
	}
	
}
