package br.com.tcc.guardia.rosa.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

import br.com.tcc.guardia.rosa.business.ComentarioBusiness;
import br.com.tcc.guardia.rosa.dto.LikedCommentDTO;
import br.com.tcc.guardia.rosa.exception.CommentNotFoundException;
import br.com.tcc.guardia.rosa.exception.DislikeNotAllowedException;
import br.com.tcc.guardia.rosa.form.LikeCommentForm;

@	Controller
public class ComentarioSocket {
	
	private final ComentarioBusiness business;
	
	@Autowired
	public ComentarioSocket(ComentarioBusiness business) {
		this.business = business;
	}
	
	@MessageMapping("/comentario/like/{commentId}")
	@SendTo("/topic/comentario/{commentId}")
	public LikedCommentDTO  likeComentario(@RequestBody @Valid LikeCommentForm likeCommentForm, @DestinationVariable("commentId") Long commentId) {
		try {
			return business.like(likeCommentForm);
		} catch (DislikeNotAllowedException | CommentNotFoundException e) {
			return new LikedCommentDTO();
		}
	}
	
}
