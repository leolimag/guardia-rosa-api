package br.com.tcc.guardia.rosa.business;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.tcc.guardia.rosa.dto.UpdateGuardiaoForm;
import br.com.tcc.guardia.rosa.model.Guardiao;
import br.com.tcc.guardia.rosa.repository.GuardiaoRepository;

@Service
public class GuardiaoBusiness {
	
	@Autowired
	private GuardiaoRepository repository;
	
	public Page<Guardiao> getGuardioesByUsuario(Long id, Pageable pageable) {	
		Page<Guardiao> guardioes = repository.findByUsuarioId(id, pageable);
		return guardioes;
	}
	
	public void addGuardiao(Guardiao guardiao) {
		repository.save(guardiao);
	}
	
	public void deleteGuardiao(Long id) {
		repository.deleteById(id);
	}
	
	public Guardiao updateGuardiao(Long id, UpdateGuardiaoForm guardiaoForm) {
		Guardiao guardiao = findById(id).get();
		guardiao.setEmail(guardiaoForm.getEmail());
		guardiao.setNome(guardiaoForm.getNome());
		guardiao.setTelefone(guardiaoForm.getTelefone());
		
		repository.save(guardiao);
		return guardiao;
	}
	
	public Optional<Guardiao> findById(Long id) {
		Optional<Guardiao> guardiao = repository.findById(id);
		if (guardiao.isPresent()) {
			return guardiao;
		}
		return null;
	}
	
}
