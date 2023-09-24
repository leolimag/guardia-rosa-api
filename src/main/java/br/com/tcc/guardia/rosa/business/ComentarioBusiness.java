package br.com.tcc.guardia.rosa.business;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.tcc.guardia.rosa.model.Comentario;
import br.com.tcc.guardia.rosa.model.Post;

@Service
public class ComentarioBusiness {
	
	@Autowired
	private ComentarioRepository repository;
	@Autowired
	private PostBusiness postBusiness;

	public Page<Comentario> getCommentsByPost(Long id, Pageable pageable) {
		Optional<Post> post = postBusiness.findById(id);
		if (post.isPresent()) {
			return repository.findByPost(post.get(), pageable);
		}
		
		return null;
	}
	
}
