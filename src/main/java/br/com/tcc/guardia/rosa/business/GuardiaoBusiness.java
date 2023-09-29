package br.com.tcc.guardia.rosa.business;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.tcc.guardia.rosa.form.GuardiaoForm;
import br.com.tcc.guardia.rosa.form.UpdateGuardiaoForm;
import br.com.tcc.guardia.rosa.model.Guardiao;
import br.com.tcc.guardia.rosa.repository.GuardiaoRepository;

@Service
public class GuardiaoBusiness {
	
	@Autowired
	private GuardiaoRepository repository;
	@Autowired
	private UsuarioBusiness usuarioBusiness;
	
	public List<Guardiao> getGuardioesByUsuario(Long id) {	
		List<Guardiao> guardioes = repository.findByUsuarioId(id);
		return guardioes;
	}
	
	public Guardiao addGuardiao(GuardiaoForm guardiaoForm) {
		Guardiao guardiao = guardiaoForm.toGuardiao(usuarioBusiness);
		repository.save(guardiao);
		
		return guardiao;
	}
	
	public void deleteGuardiao(Long id) {
		repository.deleteById(id);
	}
	
	public Guardiao updateGuardiao(Long id, UpdateGuardiaoForm guardiaoForm) {
		Guardiao guardiao = findById(id).get();
		if (guardiao != null) {
			guardiao.setEmail(guardiaoForm.getEmail());
			guardiao.setNome(guardiaoForm.getNome());
			guardiao.setTelefone(guardiaoForm.getTelefone());
			repository.save(guardiao);
		}
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
