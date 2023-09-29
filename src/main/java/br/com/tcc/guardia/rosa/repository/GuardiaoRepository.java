package br.com.tcc.guardia.rosa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.tcc.guardia.rosa.model.Guardiao;

public interface GuardiaoRepository extends JpaRepository<Guardiao, Long> {
	
	public List<Guardiao> findByUsuarioId(Long id);

}
