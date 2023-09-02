package br.com.tcc.guardia.rosa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.tcc.guardia.rosa.model.Guardiao;

public interface GuardiaoRepository extends JpaRepository<Guardiao, Long> {
	
	public Page<Guardiao> findByUsuarioId(Long id,  Pageable page);

}
