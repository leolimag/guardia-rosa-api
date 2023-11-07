package br.com.tcc.guardia.rosa.controller;

import java.util.Optional;
import java.util.Random;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.tcc.guardia.rosa.business.ResetSenhaBusiness;
import br.com.tcc.guardia.rosa.business.UsuarioBusiness;
import br.com.tcc.guardia.rosa.dto.ForgetPasswordDTO;
import br.com.tcc.guardia.rosa.dto.LoginDTO;
import br.com.tcc.guardia.rosa.dto.LoginResponseDTO;
import br.com.tcc.guardia.rosa.dto.RegisterDTO;
import br.com.tcc.guardia.rosa.dto.ResetPasswordDTO;
import br.com.tcc.guardia.rosa.dto.UserPhotoDTO;
import br.com.tcc.guardia.rosa.exception.ExpirationCodeException;
import br.com.tcc.guardia.rosa.exception.InvalidCodeException;
import br.com.tcc.guardia.rosa.exception.UserNotFoundException;
import br.com.tcc.guardia.rosa.model.ResetSenha;
import br.com.tcc.guardia.rosa.model.Usuario;
import br.com.tcc.guardia.rosa.security.TokenService;

@RestController
@RequestMapping("/api/auth")
public class LoginController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private TokenService tokenService;
	@Autowired 
	private JavaMailSender mailSender;
	
	private final UsuarioBusiness business; 
	private final ResetSenhaBusiness resetBusiness;

	@Autowired
	public LoginController(UsuarioBusiness business, ResetSenhaBusiness resetBusiness) {
		this.business = business;
		this.resetBusiness = resetBusiness; 
	}
	
	@PostMapping("/login")
	public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid LoginDTO login) {
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(login.getEmail(), login.getPassword());
		Authentication auth = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
		String token = tokenService.generateToken(auth);
		Usuario usuario = (Usuario) auth.getPrincipal();
		
		return ResponseEntity.ok(new LoginResponseDTO(usuario.getId(), usuario.getNome(), usuario.getEmail(), token, usuario.getFoto())); 
	}
	
	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody @Valid RegisterDTO register) {
		if (business.findByEmail(register.getEmail()) != null) {
			return ResponseEntity.badRequest().body("Este e-mail já foi cadastrado.");
		}
		
		String encryptedPassword = new BCryptPasswordEncoder().encode(register.getPassword());
		Usuario usuario = new Usuario(register.getName(), register.getEmail(), encryptedPassword);
		business.save(usuario);
		
		return ResponseEntity.ok().build();
	}
	
	@PutMapping("/photo")
	public ResponseEntity<?> updatePhoto(@RequestBody @Valid UserPhotoDTO userPhoto) {
		try {
			Optional<Usuario> usuarioOpt = business.findById(userPhoto.getIdUsuario());
			validateUser(usuarioOpt);
			Usuario usuario = usuarioOpt.get();
			usuario.setFoto(userPhoto.getFoto());
			business.save(usuario);
			
			return ResponseEntity.ok().build();
		} catch (UserNotFoundException e) {
			return ResponseEntity.badRequest().body("Este usuário não existe");
		}
	}
	
	@PostMapping("/forget-password")
	public ResponseEntity<?>  forgetPassword(@RequestBody @Valid ForgetPasswordDTO resetPassword) throws UserNotFoundException {
		Optional<Usuario> usuarioOpt = business.findUserByEmail(resetPassword.getEmail());
		validateUser(usuarioOpt);
		
        Random random = new Random();
        int codigoDeVerificacao = random.nextInt(1000000);
        String codigoFormatado = String.format("%06d", codigoDeVerificacao);
        
		Usuario usuario = usuarioOpt.get();
		resetBusiness.saveResetSenha(usuario, Integer.parseInt(codigoFormatado));

		try {
			mailSender.send(sendEmail(usuario, Integer.parseInt(codigoFormatado)));
		} catch (MailSendException e) {
			return ResponseEntity.badRequest().body("Endereço de e-mail inválido ou inexistente!");
		}
		
		return ResponseEntity.ok().build();
	}
	
	@PostMapping("/validate-reset-password")
	public ResponseEntity<?> validateResetPassword(@RequestBody @Valid ForgetPasswordDTO forgetPassword) throws UserNotFoundException {
		Optional<Usuario> usuarioOpt = business.findUserByEmail(forgetPassword.getEmail());
		try {
			validateUser(usuarioOpt);
		} catch (UserNotFoundException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		
		Usuario usuario = usuarioOpt.get();
		ResetSenha resetSenha = resetBusiness.findFirstByUsuarioOrderByDataExpiracaoDesc(usuario);
		
		if (resetSenha == null) {
			return ResponseEntity.notFound().build(); 
		}
		
		try {
			 if (resetBusiness.validateCode(resetSenha, forgetPassword.getCodigoDeVerificacao())) {
				 return ResponseEntity.ok().build();
			 }
		} catch (ExpirationCodeException | InvalidCodeException e) {
			return ResponseEntity.badRequest().body(e.getMessage()); 
		}
		
		return ResponseEntity.notFound().build();
	}
	
	private SimpleMailMessage sendEmail(Usuario usuario, int codigoDeVerificacao) {
	    SimpleMailMessage email = new SimpleMailMessage();
	    email.setSubject("Redenifição de Senha");
	    email.setText("O código de verificação para a recuperação da sua senha é: \n\n" + codigoDeVerificacao + "\n\nInsira esse código no AbraçoRosa para realizar a troca da senha.") ;
	    email.setTo(usuario.getEmail());
	    email.setFrom("abracorosa.tcc@gmail.com"); 
	    return email;
	}
	
	@PatchMapping("/reset-password")
	public ResponseEntity<?> resetPassword(@RequestBody @Valid ResetPasswordDTO resetPassword) throws UserNotFoundException {
		Optional<Usuario> usuarioOpt = business.findUserByEmail(resetPassword.getEmail());
		try {
			validateUser(usuarioOpt);
		} catch (UserNotFoundException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		
		Usuario usuario = usuarioOpt.get();
		String encryptedPassword = new BCryptPasswordEncoder().encode(resetPassword.getSenha());
		usuario.setSenha(encryptedPassword);
		business.save(usuario);
		return ResponseEntity.ok("Senha alterada com sucesso!");
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable Long id) {
		Optional<Usuario> usuarioOpt = business.findById(id);
		try {
			validateUser(usuarioOpt);
		} catch (UserNotFoundException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		business.delete(usuarioOpt.get());
		return ResponseEntity.ok().build();
	}
	
	private ResponseEntity<?> validateUser(Optional<Usuario> usuario) throws UserNotFoundException {
		if (!usuario.isPresent()) {
			throw new UserNotFoundException("Este usuário não existe.");
		}
		return null;
	}
	
}
