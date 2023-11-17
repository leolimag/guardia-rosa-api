package br.com.tcc.guardia.rosa.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

import br.com.tcc.guardia.rosa.business.PostBusiness;
import br.com.tcc.guardia.rosa.dto.LikedPostDTO;
import br.com.tcc.guardia.rosa.exception.DislikeNotAllowedException;
import br.com.tcc.guardia.rosa.exception.PostNotFoundException;
import br.com.tcc.guardia.rosa.form.LikePostForm;

@	Controller
public class PostSocket {
	
	private final PostBusiness business;
	
	@Autowired
	public PostSocket(PostBusiness business) {
		this.business = business;
	}
	
	@MessageMapping("/post/like/{postId}")
	@SendTo("/topic/post/{postId}")
	public LikedPostDTO  likePost(@RequestBody @Valid LikePostForm likePostForm, @DestinationVariable("postId") Long postId) {
		try {
			return business.like(likePostForm);
		} catch (PostNotFoundException | DislikeNotAllowedException e) {
			return new LikedPostDTO();
		}
	}
	
}
