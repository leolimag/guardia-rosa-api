package br.com.tcc.guardia.rosa.business;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.tcc.guardia.rosa.model.Comentario;
import br.com.tcc.guardia.rosa.model.Post;

public interface ComentarioRepository extends JpaRepository<Comentario, Long> {

	Page<Comentario> findByPostOrderByIdDesc(Post post, Pageable pageable);

}
