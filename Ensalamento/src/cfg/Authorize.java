package cfg;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import javax.crypto.SecretKey;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

public class Authorize {
	private static final SecretKey CHAVE = Keys.hmacShaKeyFor(
		    Server.TOKEN_HASH
		    .getBytes(StandardCharsets.UTF_8));
	public static boolean verifica(String token) {
		 try {
			 	Jwts.parserBuilder()
	                    .setSigningKey(CHAVE)
	                    .build()
	                    .parseClaimsJws(token);
	        } catch (Exception e) {
	            return false;
	        }
		return true;
	}
	
	public static String geraToken(String usuario, Long permissionTime) {
		return Jwts.builder()
		          .setSubject(usuario)
		          .setIssuer("localhost:8080")
		          .setIssuedAt(new Date())
		          .setExpiration(
		              Date.from(LocalDateTime.now().plusMinutes(permissionTime)
		                      .atZone(ZoneId.systemDefault()).toInstant()))
		          .signWith(CHAVE, SignatureAlgorithm.HS256)
		          .compact();
	}
	
	public static String getSubject(String token) {
		return Jwts.parserBuilder()
                .setSigningKey(CHAVE).build().parseClaimsJws(token).getBody().getSubject();
	}
	
}
