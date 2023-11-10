package br.com.tcc.guardia.rosa.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

import br.com.tcc.guardia.rosa.business.PostBusiness;
import br.com.tcc.guardia.rosa.dto.ChatMessageDTO;
import br.com.tcc.guardia.rosa.exception.DislikeNotAllowedException;
import br.com.tcc.guardia.rosa.exception.PostNotFoundException;
import br.com.tcc.guardia.rosa.form.LikePostForm;

@	Controller
public class WebSocketController {
	
	private final PostBusiness business;
	
	@Autowired
	public WebSocketController(PostBusiness business) {
		this.business = business;
	}

	@MessageMapping("/chat/{roomId}")
	@SendTo("/topic/{roomId}")
	public ChatMessageDTO chat(@DestinationVariable String roomId, ChatMessageDTO message) {
		return new ChatMessageDTO(message.getMessage(), message.getUser());
	}
	
	@MessageMapping("/chat/like")
	@SendTo("/topic")
	public Long  likePost(@RequestBody @Valid LikePostForm likePostForm) {
		try {
			Long likeTeste = business.likeTeste(likePostForm);
			return likeTeste;
		} catch (PostNotFoundException | DislikeNotAllowedException e) {
			return 0l;
		}
	}
	
}
