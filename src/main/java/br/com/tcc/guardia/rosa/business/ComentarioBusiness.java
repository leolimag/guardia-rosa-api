package br.com.tcc.guardia.rosa.business;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.tcc.guardia.rosa.dto.LikedCommentDTO;
import br.com.tcc.guardia.rosa.exception.CommentNotFoundException;
import br.com.tcc.guardia.rosa.exception.DislikeNotAllowedException;
import br.com.tcc.guardia.rosa.form.CommentForm;
import br.com.tcc.guardia.rosa.form.LikeCommentForm;
import br.com.tcc.guardia.rosa.model.Comentario;
import br.com.tcc.guardia.rosa.model.CurtidaComentario;
import br.com.tcc.guardia.rosa.model.Post;
import br.com.tcc.guardia.rosa.model.Usuario;
import br.com.tcc.guardia.rosa.repository.ComentarioRepository;

@Service
public class ComentarioBusiness {
	
	@Autowired
	private ComentarioRepository repository;
	@Autowired
	private PostBusiness postBusiness;
	@Autowired
	private UsuarioBusiness usuarioBusiness;
	@Autowired
	private CurtidaComentarioBusiness curtidaComentarioBusiness;

	public Page<Comentario> getCommentsByPost(Long id, Pageable pageable) {
		Post post = postBusiness.findById(id);
		if (post != null) {
			return repository.findByPostOrderByIdDesc(post, pageable);
		}
		
		return null;
	}
	
	public void addComment(CommentForm commentForm, UsuarioBusiness usuarioBusiness) {
		Comentario comentario = commentForm.toComentario(usuarioBusiness, postBusiness);
		repository.save(comentario);
	}
	
	public Comentario findById(Long id) {
		Optional<Comentario> comentarioOpt = repository.findById(id);
		if (comentarioOpt.isPresent()) {
			return comentarioOpt.get();
		}
		
		return null;
	}
	
	public LikedCommentDTO like(LikeCommentForm likeCommentForm) throws CommentNotFoundException, DislikeNotAllowedException {
		Comentario comentario = findById(likeCommentForm.getId());
		Usuario usuario = usuarioBusiness.findById(likeCommentForm.getUsuarioId()).get();
		Post post = postBusiness.findById(likeCommentForm.getPostId());
		CurtidaComentario curtidaComentario = new CurtidaComentario();
		
		if (comentario == null || usuario == null || post == null) {
			throw new CommentNotFoundException("Comentário não encontrado.");
		}
		
		boolean likedByUser = curtidaComentarioBusiness.isLikedByUser(usuario, post, comentario);
		if (!likedByUser) {
			curtidaComentario.setComentario(comentario);
			curtidaComentario.setPost(post);
			curtidaComentario.setUsuario(usuario);
			curtidaComentarioBusiness.save(curtidaComentario);
			
			repository.save(comentario);
		} else {
			curtidaComentario = curtidaComentarioBusiness.findByPostAndUsuarioAndComentario(post, usuario, comentario);
			dislike(curtidaComentario, comentario);
		}
		
		Long curtidas = curtidaComentarioBusiness.getCurtidas(comentario);
		return new LikedCommentDTO(likedByUser, curtidas);
	}
	
	
	private void dislike(CurtidaComentario curtidaComentario, Comentario comentario) throws DislikeNotAllowedException {
		curtidaComentarioBusiness.delete(curtidaComentario);
		repository.save(comentario);
	}
	
}
