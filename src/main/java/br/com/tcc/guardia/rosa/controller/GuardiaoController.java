package br.com.tcc.guardia.rosa.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.tcc.guardia.rosa.business.GuardiaoBusiness;
import br.com.tcc.guardia.rosa.dto.GuardiaoDTO;
import br.com.tcc.guardia.rosa.form.FavoriteGuardiaoForm;
import br.com.tcc.guardia.rosa.form.GuardiaoForm;
import br.com.tcc.guardia.rosa.form.UpdateGuardiaoForm;
import br.com.tcc.guardia.rosa.model.Guardiao;

@RestController
@RequestMapping("/api/guardian")
public class GuardiaoController {
	
	private final GuardiaoBusiness business; 

	@Autowired
	public GuardiaoController(GuardiaoBusiness business) {
		this.business = business;
	}
	
	@GetMapping("/{id}")
	public List<GuardiaoDTO> getGuardioesByUsuario(@PathVariable Long id) {
		List<Guardiao> guardioes = business.getGuardioesByUsuario(id);
		List<GuardiaoDTO> guardioesDTO = GuardiaoDTO.toGuardioesDTO(guardioes);
		
		return guardioesDTO;
	}
	
	@GetMapping("/to-edit/{id}")
	public ResponseEntity<GuardiaoDTO> getGuardiaoById(@PathVariable Long id) {
		Guardiao guardiao = null;
		GuardiaoDTO guardiaoDto = null;
		
		if (business.findById(id) != null) {
			guardiao = business.findById(id).get();
			guardiaoDto = new GuardiaoDTO(guardiao);
			return ResponseEntity.ok(guardiaoDto);
		}
		
		return ResponseEntity.notFound().build(); 
	}
	
	@PostMapping
	public ResponseEntity<GuardiaoDTO> addGuardiao(@RequestBody @Valid GuardiaoForm guardiaoForm, UriComponentsBuilder uriBuilder) {
		Guardiao guardiao = business.addGuardiao(guardiaoForm);
		URI uri = uriBuilder.path("/api/guardiao/{id}").buildAndExpand(guardiao.getId()).toUri();
		
		return ResponseEntity.created(uri).body(new GuardiaoDTO(guardiao));
	}
	
	@PutMapping
	public ResponseEntity<GuardiaoDTO> updateGuardiao(@RequestBody @Valid UpdateGuardiaoForm guardiaoForm) {
		if (business.findById(guardiaoForm.getId()) != null) {
			Guardiao guardiao = business.updateGuardiao(guardiaoForm);
			return ResponseEntity.ok(new GuardiaoDTO(guardiao));
		}
		return ResponseEntity.notFound().build();
	}
	
	@PatchMapping("/favorite")
	public ResponseEntity<GuardiaoDTO> updateGuardiaoFavorito(@RequestBody @Valid FavoriteGuardiaoForm favoriteGuardiaoForm) {
		if (business.findById(favoriteGuardiaoForm.getId()) != null) {	
			try {
				business.updateGuardiaoFavorito(favoriteGuardiaoForm.getId(), favoriteGuardiaoForm.getUsuarioId());
				return ResponseEntity.ok(new GuardiaoDTO());
			} catch (Exception e) {
				return ResponseEntity.notFound().build(); 
			}
		}
		return ResponseEntity.notFound().build(); 
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<GuardiaoDTO> deleteGuardiao(@PathVariable Long id) {
		if (business.findById(id) != null) {
			business.deleteGuardiao(id);
			return ResponseEntity.ok(new GuardiaoDTO());
		}
		return ResponseEntity.notFound().build();
	}
	
}
