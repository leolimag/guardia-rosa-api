package br.com.tcc.guardia.rosa.business;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.tcc.guardia.rosa.exception.DislikeNotAllowedException;
import br.com.tcc.guardia.rosa.exception.PostNotFoundException;
import br.com.tcc.guardia.rosa.form.LikePostForm;
import br.com.tcc.guardia.rosa.form.PostForm;
import br.com.tcc.guardia.rosa.form.UpdatePostForm;
import br.com.tcc.guardia.rosa.model.CurtidaPost;
import br.com.tcc.guardia.rosa.model.Post;
import br.com.tcc.guardia.rosa.model.Usuario;
import br.com.tcc.guardia.rosa.repository.PostRepository;

@Service
public class PostBusiness {
	
	@Autowired
	private PostRepository repository;
	@Autowired
	private UsuarioBusiness usuarioBusiness;
	@Autowired
	private CurtidaPostBusiness curtidaPostBusiness;
	
	public Page<Post> getPostsByUser(Long id, Pageable pageable) {
		Optional<Usuario> usuario = usuarioBusiness.findById(id);
		if (usuario.isPresent()) {
			return repository.findByUsuarioOrderById(usuario.get(), pageable);
		}
		
		return null;
	}
	
	public Post addPost(PostForm postForm) {
		Post post = postForm.toPost(usuarioBusiness);
		repository.save(post);
		return post;
	}
	
	public Page<Post> getAllPosts(Pageable pageable) {
		return repository.findAllByOrderByIdDesc(pageable);
	}
	
	public Post findById(Long id) {
		Optional<Post> post = repository.findById(id);
		if (post.isPresent()) {
			return post.get();
		}
		
		return null;
	}

	public Post updatePost(Long id, @Valid UpdatePostForm postForm) {
		Post post = findById(id);
		if (post != null) {
			post.setConteudo(postForm.getConteudo());
			repository.save(post);
			return post;
		}
		
		return null;
	}
	
	public void like(LikePostForm likePostForm) throws PostNotFoundException, DislikeNotAllowedException {
		Post post = findById(likePostForm.getId());
		Usuario usuario = usuarioBusiness.findById(likePostForm.getUsuarioId()).get();
		CurtidaPost curtidaPost = new CurtidaPost();
		
		if (post == null || usuario == null) {
			throw new PostNotFoundException("Post não encontrado.");
		}
		
		boolean likedByUser = curtidaPostBusiness.isLikedByUser(usuario, post);
		if (!likedByUser) {
			curtidaPost.setPost(post);
			curtidaPost.setUsuario(usuario);
			curtidaPostBusiness.save(curtidaPost);
			
			post.setCurtidas(post.getCurtidas() + 1);
			repository.save(post);
		} else {
			curtidaPost = curtidaPostBusiness.findByPostAndUsuario(post, usuario);
			dislike(curtidaPost, post);
		}
		
	}
	
	public void dislike(CurtidaPost curtidaPost, Post post) throws PostNotFoundException, DislikeNotAllowedException {
		if (post.getCurtidas() == 0) {
			throw new DislikeNotAllowedException("Curtidas estão iguais a 0.");
		}
		curtidaPostBusiness.delete(curtidaPost);
		post.setCurtidas(post.getCurtidas() - 1);
		repository.save(post);
	}

}
