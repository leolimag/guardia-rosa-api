package br.com.tcc.guardia.rosa.business;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.tcc.guardia.rosa.exception.CommentNotFoundException;
import br.com.tcc.guardia.rosa.exception.DislikeNotAllowedException;
import br.com.tcc.guardia.rosa.form.CommentForm;
import br.com.tcc.guardia.rosa.model.Comentario;
import br.com.tcc.guardia.rosa.model.Post;

@Service
public class ComentarioBusiness {
	
	@Autowired
	private ComentarioRepository repository;
	@Autowired
	private PostBusiness postBusiness;

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
	
	public void like(Long commentId) throws CommentNotFoundException {
		Comentario comentario = findById(commentId);
		if (comentario == null) {
			throw new CommentNotFoundException("Comentário não encontrado.");
		}
		comentario.setCurtidas(comentario.getCurtidas() + 1);
		repository.save(comentario);
	}
	
	
	public void dislike(Long commentId) throws CommentNotFoundException, DislikeNotAllowedException {
		Comentario comentario = findById(commentId);
		if (comentario == null) {
			throw new CommentNotFoundException("Comentário não encontrado.");
		}
		if (comentario.getCurtidas() == 0) {
			throw new DislikeNotAllowedException("Curtidas estão iguais a 0.");
		}
		comentario.setCurtidas(comentario.getCurtidas() - 1);
		repository.save(comentario);
	}
	
}
