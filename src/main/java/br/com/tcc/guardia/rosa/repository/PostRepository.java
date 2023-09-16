package br.com.tcc.guardia.rosa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.tcc.guardia.rosa.model.Post;
import br.com.tcc.guardia.rosa.model.Usuario;

public interface PostRepository extends JpaRepository<Post, Long> {
	
	List<Post> findByUsuarioOrderById(Usuario usuario);
	List<Post> findAllByOrderByIdDesc();

}
