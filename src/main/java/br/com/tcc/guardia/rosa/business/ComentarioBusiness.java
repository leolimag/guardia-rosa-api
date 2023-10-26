package br.com.tcc.guardia.rosa.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
	
}
