package br.com.tcc.guardia.rosa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.tcc.guardia.rosa.model.Comentario;
import br.com.tcc.guardia.rosa.model.CurtidaComentario;
import br.com.tcc.guardia.rosa.model.Post;
import br.com.tcc.guardia.rosa.model.Usuario;

public interface CurtidaComentarioRepository extends JpaRepository<CurtidaComentario, Long> {

	@Query("SELECT COUNT(*) FROM CurtidaComentario WHERE usuario = :usuario AND post = :post AND comentario = :comentario")
	Integer isLikedByUser(@Param("usuario") Usuario usuario, @Param("post") Post post, @Param("comentario") Comentario comentario);

	CurtidaComentario findByPostAndUsuarioAndComentario(Post post, Usuario usuario, Comentario comentario);
	
}
