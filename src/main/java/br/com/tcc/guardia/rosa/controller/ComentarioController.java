package br.com.tcc.guardia.rosa.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.tcc.guardia.rosa.business.ComentarioBusiness;
import br.com.tcc.guardia.rosa.business.PostBusiness;
import br.com.tcc.guardia.rosa.business.UsuarioBusiness;
import br.com.tcc.guardia.rosa.dto.ComentarioDTO;
import br.com.tcc.guardia.rosa.exception.PostNotFoundException;
import br.com.tcc.guardia.rosa.exception.UserNotFoundException;
import br.com.tcc.guardia.rosa.form.CommentForm;
import br.com.tcc.guardia.rosa.model.Comentario;
import br.com.tcc.guardia.rosa.model.Post;
import br.com.tcc.guardia.rosa.model.Usuario;

@RestController
@RequestMapping("/api/comments")
public class ComentarioController {
	
	private final ComentarioBusiness comentarioBusiness;
	private final UsuarioBusiness usuarioBusiness;
	private final PostBusiness postBusiness;
	
	@Autowired
	public ComentarioController(ComentarioBusiness comentarioBusiness, UsuarioBusiness usuarioBusiness, PostBusiness postBusiness) {
		this.comentarioBusiness = comentarioBusiness;
		this.usuarioBusiness = usuarioBusiness;
		this.postBusiness = postBusiness;
	}
	
	@GetMapping("/{id}")
	public Page<ComentarioDTO> getCommentsByPost(@RequestParam(required = false) Integer quantity, @RequestParam Integer page, @PathVariable Long id) {
		Pageable pageable 	= PageRequest.of(page, quantity);
		Page<Comentario> comentarios = comentarioBusiness.getCommentsByPost(id, pageable);
		Page<ComentarioDTO> comentariosDTO = ComentarioDTO.toComentarioDTO(comentarios);
		
		return comentariosDTO;
	}
	
	@PostMapping
	public ResponseEntity<?> addComment(@RequestBody @Valid CommentForm commentForm) {
		try {
			validateUser(commentForm.getUsuarioId());
			validatePost(commentForm.getPostId());
		} catch (UserNotFoundException | PostNotFoundException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		
		comentarioBusiness.addComment(commentForm, usuarioBusiness);
		return ResponseEntity.ok().build();
	}
	
	private ResponseEntity<?> validateUser(Long id) throws UserNotFoundException {
		Optional<Usuario> usuario = usuarioBusiness.findById(id);
		
		if (!usuario.isPresent()) {
			throw new UserNotFoundException("Este usuário não existe.");
		}
		return null;
	}
	
	private ResponseEntity<?> validatePost(Long id) throws PostNotFoundException {
		Post post = postBusiness.findById(id);
		
		if (post == null) {
			throw new PostNotFoundException("Este post não existe.");
		}
		return null;
	}

}
