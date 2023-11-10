package br.com.tcc.guardia.rosa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.tcc.guardia.rosa.model.CurtidaPost;
import br.com.tcc.guardia.rosa.model.Post;
import br.com.tcc.guardia.rosa.model.Usuario;

public interface CurtidaPostRepository extends JpaRepository<CurtidaPost, Long> {

	@Query("SELECT COUNT(*) FROM CurtidaPost WHERE usuario = :usuario AND post = :post")
	Integer isLikedByUser(@Param("usuario") Usuario usuario, @Param("post") Post post);

	CurtidaPost findByPostAndUsuario(Post post, Usuario usuario);
	
	@Query("SELECT COUNT(*) FROM CurtidaPost cp WHERE cp.post = :post")
	Long getCurtidas(@Param("post") Post post);
	
}
