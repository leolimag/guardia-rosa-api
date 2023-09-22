package br.com.tcc.guardia.rosa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.tcc.guardia.rosa.business.PostBusiness;
import br.com.tcc.guardia.rosa.dto.PostDTO;
import br.com.tcc.guardia.rosa.model.Post;

@RestController
@CrossOrigin
@RequestMapping("/api/posts")
public class PostController {
	
	private final PostBusiness business;
	
	@Autowired
	public PostController(PostBusiness business) {
		this.business = business;
	}
	
	@GetMapping
	public Page<PostDTO> getAllPosts(@RequestParam(required = false) Integer quantity, @RequestParam Integer page) {
		Pageable pageable 	= PageRequest.of(page, quantity);
		Page<Post> posts = business.getAllPosts(pageable);
		Page<PostDTO> postsDTO = PostDTO.toPostsDTO(posts);
		
		return postsDTO;
	}
	
	@GetMapping("/{id}")
	public Page<PostDTO> getPostsByUsuario(@RequestParam(required = false) Integer quantity, @RequestParam Integer page, @PathVariable Long id) {
		Pageable pageable 	= PageRequest.of(page, quantity);
		Page<Post> posts = business.getPostsByUser(id, pageable);
		Page<PostDTO> postsDTO = PostDTO.toPostsDTO(posts);
		
		return postsDTO;
	}

}
