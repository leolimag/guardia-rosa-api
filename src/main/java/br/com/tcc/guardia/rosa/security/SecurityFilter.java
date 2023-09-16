package br.com.tcc.guardia.rosa.security;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.tcc.guardia.rosa.model.Usuario;
import br.com.tcc.guardia.rosa.repository.UsuarioRepository;

@Component
public class SecurityFilter  extends OncePerRequestFilter {
	
	@Autowired
	private TokenService tokenService;
	@Autowired
	private UsuarioRepository repository;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String token = recoverToken(request);
		if (token != null) {
			boolean valid = tokenService.isTokenValid(token);
			if (valid) {
				authClient(token);
			}
		}
		filterChain.doFilter(request, response);		
	}
	
	private void authClient(String token) {
		Long userId = tokenService.getIdUser(token);
		Optional<Usuario> usuarioOpt = repository.findById(userId);
		Usuario usuario = usuarioOpt.get();
		UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(auth);
	}

	private String recoverToken(HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		if (token  == null || token.isEmpty() || !token.startsWith("Bearer ")) {
			return null;
		}
		return token.replace("Bearer ", "");
	}

}
