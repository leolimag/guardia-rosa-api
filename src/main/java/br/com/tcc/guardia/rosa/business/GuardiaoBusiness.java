package br.com.tcc.guardia.rosa.business;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.tcc.guardia.rosa.exception.GuardianNotFoundException;
import br.com.tcc.guardia.rosa.exception.UserNotFoundException;
import br.com.tcc.guardia.rosa.form.GuardiaoForm;
import br.com.tcc.guardia.rosa.form.UpdateGuardiaoForm;
import br.com.tcc.guardia.rosa.model.Guardiao;
import br.com.tcc.guardia.rosa.model.Usuario;
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
		guardiao.setFavorito(false);
		repository.save(guardiao);
		
		return guardiao;
	}
	
	public void deleteGuardiao(Long id) {
		repository.deleteById(id);
	}
	
	public Guardiao updateGuardiao(UpdateGuardiaoForm guardiaoForm) {
		Guardiao guardiao = findById(guardiaoForm.getId()).get();
		if (guardiao != null) {
			guardiao.setEmail(guardiaoForm.getEmail());
			guardiao.setNome(guardiaoForm.getNome());
			guardiao.setTelefone(guardiaoForm.getTelefone());
			repository.save(guardiao);
		}
		return guardiao;
	}
	
	public void updateGuardiaoFavorito(Long id, Long usuarioId) throws RuntimeException {
		Optional<Usuario> usuarioOpt = usuarioBusiness.findById(usuarioId);
		Usuario usuario = null;
		
		if (!usuarioOpt.isPresent()) {
			throw new RuntimeException("Esse usuário não existe!");
		}
		
		usuario = usuarioOpt.get();
		
		if (repository.existsGuardiaoFavoritoByUsuario(usuario)) {
			Long guardiaoFavoritoId = repository.getGuardiaoFavoritoByUsuario(usuario).getId();
			repository.updateGuardiaoFavorito(guardiaoFavoritoId, false);
			repository.updateGuardiaoFavorito(id, true);
		} else {
			repository.updateGuardiaoFavorito(id, true);
		}
	}
	
	public Optional<Guardiao> findById(Long id) {
		Optional<Guardiao> guardiao = repository.findById(id);
		if (guardiao.isPresent()) {
			return guardiao;
		}
		return null;
	}
	
public Guardiao findFavoriteByUser(Long id) throws GuardianNotFoundException, UserNotFoundException {
	Optional<Usuario> usuarioOpt = usuarioBusiness.findById(id);
	Usuario usuario = null;
	
	if (usuarioOpt.isPresent()) {
		usuario = usuarioOpt.get();
	} else {
		throw new UserNotFoundException("Usuário não encontrado");
	}
	
	Optional<Guardiao> guardiao = repository.findByFavoritoAndUsuario(true, usuario);
	if (guardiao.isPresent()) {
		return guardiao.get();
	} else {
		Optional<Guardiao> guardiaoOpt = repository.findFirstByUsuario(usuario);
		if (guardiaoOpt.isPresent()) {
			return guardiaoOpt.get();
		} else {
			throw new GuardianNotFoundException("Você ainda não possui nenhum guardião!");
		}
	}
	
}
	
}
