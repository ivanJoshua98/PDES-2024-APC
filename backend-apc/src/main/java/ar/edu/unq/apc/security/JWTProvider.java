package ar.edu.unq.apc.security;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import ar.edu.unq.apc.model.HttpException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;

@Component
public class JWTProvider {

	private static final String JWT_SECRET_KEY = "TExBVkVfTVVZX1NFQ1JFVEE=";
	
	private SecretKey secretKey = Keys
									.hmacShaKeyFor(Base64.getEncoder()
									.encodeToString(JWT_SECRET_KEY.getBytes())
									.getBytes(StandardCharsets.UTF_8)); 
	
	public String generateToken(Authentication authentication) {
		String userEmail = authentication.getName();
		return Jwts.builder()
				.setSubject(userEmail)
				.setIssuedAt(new Date())
				.signWith(this.secretKey, SignatureAlgorithm.HS256)
				.compact();
	}
	
	public String getUserNameFromJWT(String token) {
		Claims claims = Jwts.parserBuilder().setSigningKey(this.secretKey).build().parseClaimsJws(token).getBody();
		return claims.getSubject();
	}

	public String getTokenWithoutBearerSuffix(String token){
		return token.substring(7);
	}

	public Boolean validateToken(String token) {
		try {
			Jwts.parserBuilder().setSigningKey(this.secretKey).build()
            .parseClaimsJws(token);
			return true;
		} 
		catch(Exception e) {
			throw new HttpException("Invalid token", HttpStatus.UNAUTHORIZED);
		}
	}
	
}