package br.com.tcc.guardia.rosa.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.tcc.guardia.rosa.business.ComentarioBusiness;
import br.com.tcc.guardia.rosa.business.CurtidaComentarioBusiness;
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
	
	private final ComentarioBusiness business;
	private final UsuarioBusiness usuarioBusiness;
	private final PostBusiness postBusiness;
	private final CurtidaComentarioBusiness curtidaComentarioBusiness;
	
	@Autowired
	public ComentarioController(ComentarioBusiness comentarioBusiness, UsuarioBusiness usuarioBusiness, PostBusiness postBusiness, CurtidaComentarioBusiness curtidaComentarioBusiness) {
		this.business = comentarioBusiness;
		this.usuarioBusiness = usuarioBusiness;
		this.postBusiness = postBusiness;
		this.curtidaComentarioBusiness = curtidaComentarioBusiness;
	}
	
	@GetMapping("/{id}")
	public Page<ComentarioDTO> getCommentsByPost(@RequestParam(required = false) Integer quantity, @RequestParam Integer page, @PathVariable Long id, @RequestParam Long usuarioId) throws UserNotFoundException {
		Optional<Usuario> usuarioOpt = usuarioBusiness.findById(usuarioId);
		Usuario usuario = new Usuario();
		
		if (usuarioOpt.isPresent()) {
			usuario = usuarioOpt.get();
		} else {
			throw new UserNotFoundException("Usuário não encontrado.");
		}
		
		Post post = postBusiness.findById(id);
		
		final Usuario finalUsuario = usuario;
		
		Pageable pageable 	= PageRequest.of(page, quantity);
		Page<Comentario> comentarios = business.getCommentsByPost(id, pageable);
		
		comentarios .forEach(comentario -> {
			comentario.setCurtido(curtidaComentarioBusiness.isLikedByUser(finalUsuario, post, comentario));
		});
		
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
		
		business.addComment(commentForm, usuarioBusiness);
		return ResponseEntity.ok().build();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteComentario(@PathVariable Long id) {
		Comentario comentario = business.findById(id);
		if (comentario != null) {
			business.delete(comentario);
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.notFound().build();
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
