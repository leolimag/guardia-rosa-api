package br.com.tcc.guardia.rosa.controller;

import java.util.Optional;
import java.util.Random;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.tcc.guardia.rosa.business.ResetSenhaBusiness;
import br.com.tcc.guardia.rosa.business.UsuarioBusiness;
import br.com.tcc.guardia.rosa.dto.LoginDTO;
import br.com.tcc.guardia.rosa.dto.LoginResponseDTO;
import br.com.tcc.guardia.rosa.dto.RegisterDTO;
import br.com.tcc.guardia.rosa.dto.ResetPasswordDTO;
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
		
		return ResponseEntity.ok(new LoginResponseDTO(usuario.getId(), usuario.getNome(), usuario.getEmail(), token, usuario.getCpf())); 
	}
	
	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody @Valid RegisterDTO register) {
		if (business.findByEmail(register.getEmail()) != null) {
			return ResponseEntity.badRequest().build();
		}
		
		String encryptedPassword = new BCryptPasswordEncoder().encode(register.getPassword());
		Usuario usuario = new Usuario(register.getName(), register.getEmail(), encryptedPassword, register.getCpf());
		business.save(usuario);
		
		return ResponseEntity.ok().build();
	}
	
	@PostMapping("/reset-password")
	public ResponseEntity<?>  resetPassword(@RequestBody @Valid ResetPasswordDTO resetPassword) throws UserNotFoundException {
		Optional<Usuario> usuarioOpt = business.findUserByEmail(resetPassword.getEmail());
		if (!usuarioOpt.isPresent()) {
			throw new UserNotFoundException("Este usuário não existe");
		}
        Random random = new Random();
        int codigoDeVerificacao = random.nextInt(1000000);
        
		Usuario usuario = usuarioOpt.get();
		resetBusiness.saveResetSenha(usuario, codigoDeVerificacao);

		mailSender.send(sendEmail(usuario, codigoDeVerificacao));
		
		return ResponseEntity.ok().build();
	}
	
	@PostMapping("/validate-reset-password")
	public ResponseEntity<?> validateResetPassword(@RequestBody @Valid ResetPasswordDTO resetPassword) throws UserNotFoundException {
		Optional<Usuario> usuarioOpt = business.findUserByEmail(resetPassword.getEmail());
		if (!usuarioOpt.isPresent()) {
			throw new UserNotFoundException("Este usuário não existe");
		}
		
		Usuario usuario = usuarioOpt.get();
		ResetSenha resetSenha = resetBusiness.findFirstByUsuarioOrderByDataExpiracaoDesc(usuario);
		
		if (resetSenha == null) {
			return ResponseEntity.notFound().build(); 
		}
		
		try {
			 if (resetBusiness.validateCode(resetSenha, resetPassword.getCodigoDeVerificacao())) {
				 return ResponseEntity.ok().build();
			 }
		} catch (ExpirationCodeException | InvalidCodeException e) {
			return ResponseEntity.badRequest().body(e.getMessage()); 
		}
		
		return ResponseEntity.notFound().build();
	}

	
	//melhorar layout do email
	private SimpleMailMessage sendEmail(Usuario usuario, int codigoDeVerificacao) {
	    SimpleMailMessage email = new SimpleMailMessage();
	    email.setSubject("Redenifição de Senha");
	    email.setText("O código de verificação para a recuperação da sua senha é: \n\n" + codigoDeVerificacao + "\nInsira esse código no AbraçoRosa para realizar a troca da senha.") ;
	    email.setTo(usuario.getEmail());
	    email.setFrom("abracorosa.tcc@gmail.com"); 
	    return email;
	}
	
}
