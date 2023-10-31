package br.com.tcc.guardia.rosa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.tcc.guardia.rosa.model.Post;
import br.com.tcc.guardia.rosa.model.Usuario;

public interface PostRepository extends JpaRepository<Post, Long> {
	
	Page<Post> findByUsuarioOrderById(Usuario usuario, Pageable pageable);
	Page<Post> findAllByOrderByIdDesc(Pageable pageable);
	
}
