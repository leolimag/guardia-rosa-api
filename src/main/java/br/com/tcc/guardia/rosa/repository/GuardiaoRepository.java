package br.com.tcc.guardia.rosa.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.tcc.guardia.rosa.model.Guardiao;
import br.com.tcc.guardia.rosa.model.Usuario;

public interface GuardiaoRepository extends JpaRepository<Guardiao, Long> {
	
    @Modifying
    @Transactional
    @Query("UPDATE Guardiao g SET g.favorito = :favorito WHERE g.id = :id")
    void updateGuardiaoFavorito(@Param("id") Long id, @Param("favorito") Boolean favorito);
    
    List<Guardiao> findByUsuarioId(Long id);
    
    @Query("SELECT CASE WHEN COUNT(*) > 0 THEN TRUE ELSE FALSE END FROM Guardiao g WHERE g.usuario  = :usuario AND g.favorito = true")
    boolean existsGuardiaoFavoritoByUsuario(@Param("usuario") Usuario usuario);
    
    @Query("SELECT g FROM Guardiao g WHERE g.usuario = :usuario AND g.favorito = true")
    Guardiao getGuardiaoFavoritoByUsuario(@Param("usuario") Usuario usuario);
    
    Optional<Guardiao> findByFavoritoAndUsuario(boolean isFavorito, Usuario usuario);
    Optional<Guardiao> findFirstByUsuario(Usuario usuario);
    
}
