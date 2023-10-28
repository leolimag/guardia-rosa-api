package br.com.tcc.guardia.rosa.controller;

import java.net.URI;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.tcc.guardia.rosa.business.PostBusiness;
import br.com.tcc.guardia.rosa.business.UsuarioBusiness;
import br.com.tcc.guardia.rosa.dto.PostDTO;
import br.com.tcc.guardia.rosa.dto.PostSelectedDTO;
import br.com.tcc.guardia.rosa.exception.DislikeNotAllowedException;
import br.com.tcc.guardia.rosa.exception.PostNotFoundException;
import br.com.tcc.guardia.rosa.exception.UserNotFoundException;
import br.com.tcc.guardia.rosa.form.LikePostForm;
import br.com.tcc.guardia.rosa.form.PostForm;
import br.com.tcc.guardia.rosa.form.UpdatePostForm;
import br.com.tcc.guardia.rosa.model.Post;
import br.com.tcc.guardia.rosa.model.Usuario;

@RestController
@RequestMapping("/api/posts")
public class PostController {
	
	private final PostBusiness business;
	private final UsuarioBusiness usuarioBusiness; 

	@Autowired
	public PostController(PostBusiness business, UsuarioBusiness usuarioBusiness) {
		this.business = business;
		this.usuarioBusiness = usuarioBusiness;
	}
	
	@GetMapping
	public Page<PostDTO> getAllPosts(@RequestParam(required = false) Integer quantity, @RequestParam Integer page) {
		Pageable pageable 	= PageRequest.of(page, quantity);
		Page<Post> posts = business.getAllPosts(pageable);
		Page<PostDTO> postsDTO = PostDTO.toPostsDTO(posts);
		
		return postsDTO;
	}
	
	@GetMapping("/selected/{id}")
	public PostSelectedDTO getPostById(@PathVariable Long id) throws PostNotFoundException {
		Post post = business.findById(id);
		if (post == null) {
			throw new PostNotFoundException("Post não encontrado");
		}
		PostSelectedDTO postSelected = new PostSelectedDTO(post);
		
		return postSelected;
	}
	
	@GetMapping("/{id}")
	public Page<PostDTO> getPostsByUsuario(@RequestParam(required = false) Integer quantity, @RequestParam Integer page, @PathVariable Long id) {
		Pageable pageable 	= PageRequest.of(page, quantity);
		Page<Post> posts = business.getPostsByUser(id, pageable);
		Page<PostDTO> postsDTO = PostDTO.toPostsDTO(posts);
		
		return postsDTO;
	}
	
	@PostMapping
	public ResponseEntity<?> addPost(@RequestBody @Valid PostForm postForm, UriComponentsBuilder uriBuilder) {
		try {
			validateUser(postForm.getUsuarioId());
		} catch (UserNotFoundException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		Post post = business.addPost(postForm);
		URI uri = uriBuilder.path("/api/posts/{id}").buildAndExpand(post.getId()).toUri();
		
		return ResponseEntity.created(uri).body(new PostDTO(post));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<PostDTO> updatePost(@RequestBody @Valid UpdatePostForm postForm, @PathVariable Long id) {
		if (business.findById(id) != null) {
			Post post = business.updatePost(id, postForm);
			return ResponseEntity.ok(new PostDTO(post));
		}
		return ResponseEntity.notFound().build();
	}
	
	@PatchMapping("/like")
	public ResponseEntity<?>  likePost(@RequestBody @Valid LikePostForm likePostForm) {
		try {
			business.like(likePostForm);
			return ResponseEntity.ok().build();
		} catch (PostNotFoundException | DislikeNotAllowedException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	private ResponseEntity<?> validateUser(Long id) throws UserNotFoundException {
		Optional<Usuario> usuario = usuarioBusiness.findById(id);
		
		if (!usuario.isPresent()) {
			throw new UserNotFoundException("Este usuário não existe.");
		}
		return null;
	}

}
