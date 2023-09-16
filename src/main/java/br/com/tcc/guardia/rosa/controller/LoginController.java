package br.com.tcc.guardia.rosa.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.tcc.guardia.rosa.dto.LoginDTO;
import br.com.tcc.guardia.rosa.dto.LoginResponseDTO;
import br.com.tcc.guardia.rosa.dto.RegisterDTO;
import br.com.tcc.guardia.rosa.model.Usuario;
import br.com.tcc.guardia.rosa.repository.UsuarioRepository;
import br.com.tcc.guardia.rosa.security.TokenService;

@RestController
@CrossOrigin
@RequestMapping("/api/auth")
public class LoginController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private UsuarioRepository repository;
	@Autowired
	private TokenService tokenService;
	
	@PostMapping("/login")
	public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid LoginDTO login) {
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(login.getLogin(), login.getPassword());
		Authentication auth = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
		String token = tokenService.generateToken(auth);
		Usuario usuario = (Usuario) auth.getPrincipal();
		
		return ResponseEntity.ok(new LoginResponseDTO(usuario.getId(), usuario.getNome(), usuario.getEmail(), usuario.getTelefone(), token)); 
	}
	
	@PostMapping("/register")
	public ResponseEntity<Usuario> register(@RequestBody @Valid RegisterDTO register) {
		if (repository.findByEmail(register.getEmail()) != null) {
			return ResponseEntity.badRequest().build();
		}
		
		String encryptedPassword = new BCryptPasswordEncoder().encode(register.getPassword());
		Usuario usuario = new Usuario(register.getName(), register.getEmail(), encryptedPassword, register.getPassword());
		repository.save(usuario);
		
		return ResponseEntity.ok(usuario);
	}
	
}
