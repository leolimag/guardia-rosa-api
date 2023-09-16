package br.com.tcc.guardia.rosa.business;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.tcc.guardia.rosa.dto.PostForm;
import br.com.tcc.guardia.rosa.model.Post;
import br.com.tcc.guardia.rosa.model.Usuario;
import br.com.tcc.guardia.rosa.repository.PostRepository;

@Service
public class PostBusiness {
	
	@Autowired
	private PostRepository repository;
	@Autowired
	private UsuarioBusiness usuarioBusiness;
	
	
	public List<Post> getPostsByUser(Long id) {
		Optional<Usuario> usuario = usuarioBusiness.findById(id);
		if (usuario.isPresent()) {
			return repository.findByUsuarioOrderById(usuario.get());
		}
		
		return new ArrayList<>();
	}
	
	public void addPost(PostForm postForm) {
		Post post = postForm.toPost(usuarioBusiness);
		repository.save(post);
	}
	
	public List<Post> getAllPosts() {
		return repository.findAllByOrderByIdDesc();
	}

}
