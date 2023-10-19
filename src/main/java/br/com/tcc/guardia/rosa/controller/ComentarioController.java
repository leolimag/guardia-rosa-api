package br.com.tcc.guardia.rosa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.tcc.guardia.rosa.business.ComentarioBusiness;
import br.com.tcc.guardia.rosa.dto.ComentarioDTO;
import br.com.tcc.guardia.rosa.model.Comentario;

@RestController
@RequestMapping("/api/comments")
public class ComentarioController {
	
	private final ComentarioBusiness comentarioBusiness;
	
	@Autowired
	public ComentarioController(ComentarioBusiness comentarioBusiness) {
		this.comentarioBusiness = comentarioBusiness;
	}
	
	@GetMapping("comments/{id}")
	public Page<ComentarioDTO> getCommentsByPost(@RequestParam(required = false) Integer quantity, @RequestParam Integer page, @PathVariable Long id) {
		Pageable pageable 	= PageRequest.of(page, quantity);
		Page<Comentario> comentarios = comentarioBusiness.getCommentsByPost(id, pageable);
		Page<ComentarioDTO> comentariosDTO = ComentarioDTO.toComentarioDTO(comentarios);
		
		return comentariosDTO;
	}

}
