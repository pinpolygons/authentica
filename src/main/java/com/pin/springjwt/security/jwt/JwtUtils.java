package com.pin.springjwt.security.jwt;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.pin.springjwt.security.services.UserDetailsImpl;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

/*Class này có 3 chức năng: 
 * phát sinh một JWT từ username, ngày, quá hạn, bảo mật
 * lấy username từ JWT
 * Chứng thực một JWT  */

@Component
public class JwtUtils {
	private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);
	
	@Value("${pin.app.jwtSecret}")
	private String jwtSecret;
	
	@Value("${pin.app.jwtExpirationMs}")
	private int jwtExpirationMs;
	
	public String generateJwtToken(Authentication authentication) {
		// TODO Auto-generated method stub
		
		UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
		
		return Jwts.builder()
				.setSubject((userPrincipal.getUsername()))
				.setIssuedAt(new Date())
				.setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
				.signWith(SignatureAlgorithm.HS512, jwtSecret)
				.compact();
	}
	
	public boolean validateJwtToken(String authToken) {
		// TODO Auto-generated method stub
		try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
			return true;
		} catch (SignatureException e) {
			// TODO: handle exception
			logger.error("Invalid JWT signature: {}", e.getMessage());
		}catch (MalformedJwtException e) {
			// TODO: handle exception
			logger.error("Invalid JWT token: {}", e.getMessage());
		}catch (ExpiredJwtException e) {
			// TODO: handle exception
			logger.error("JWT token is unsupported: {}", e.getMessage());
		}catch (UnsupportedJwtException e) {
			// TODO: handle exception
			logger.error("JWT token is unsupported: {}", e.getMessage());
		}catch (IllegalArgumentException e) {
			// TODO: handle exception
			logger.error("JWT claims string is emty: {}", e.getMessage());
		}
		return false;
	}

	public String getUserNameFromJwtToken(String token) {
		// TODO Auto-generated method stub
		return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
	}
}
/*Nhớ rằng chúng ta đã thêm pin.appwtSecret và thuộc tính pin.app.jwtExpirationMs trong 
 * file application.properties*/
