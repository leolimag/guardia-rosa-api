package br.com.tcc.guardia.rosa.security;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import br.com.tcc.guardia.rosa.model.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class TokenService {

	@Value("${api.jwt.expiration}")
	private String expiration;
	@Value("${api.jwt.secret}")
	private String secret;
	
	public String generateToken(Authentication auth) {
		Usuario logged = (Usuario) auth.getPrincipal();
		LocalDate today = LocalDate.now();
		LocalDate expirationDate = today.plusDays(Long.parseLong(expiration));
		
		return Jwts.builder()
				.setIssuer("api - guardiã rosa")
				.setSubject(logged.getId().toString())
                .setIssuedAt(Date.from(today.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()))
                .setExpiration(Date.from(expirationDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()))
                .signWith(SignatureAlgorithm.HS256, secret) 
				.compact();
	}

	public boolean isTokenValid(String token) {
		try {
			Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public Long getIdUser(String token) {
		Claims claims = Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody();
		return Long.parseLong(claims.getSubject()); 
	}
	
}
