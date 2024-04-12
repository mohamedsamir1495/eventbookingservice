package com.mohamedsamir1495.eventbookingsystem.service;

import com.mohamedsamir1495.eventbookingsystem.configuration.security.SecurityConstants;
import com.mohamedsamir1495.eventbookingsystem.dto.user.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.function.ToIntFunction;

@Service
@RequiredArgsConstructor
public class SecurityService {

	private final ToIntFunction<String> jwtExpirationLimitInSeconds = value -> Integer.parseInt(value) * 1000;

	@Value("${security.jwtKey}")
	private String jwtKey;

	public String generateToken(User user) {
		return Jwts.builder()
				   .issuer(SecurityConstants.JWT_ISSUER)
				   .subject(SecurityConstants.JWT_SUBJECT)
				   .claim(SecurityConstants.JWT_EMAIL, user.email())
				   .claim(SecurityConstants.JWT_AUTHORITIES, "USER")
				   .issuedAt(new Date())
				   .expiration(new Date((new Date()).getTime() + jwtExpirationLimitInSeconds.applyAsInt("14400")))
				   .signWith(Keys.hmacShaKeyFor(jwtKey.getBytes(StandardCharsets.UTF_8)))
				   .compact();
	}

	public Claims extractToken(String jwtToken) {
		return Jwts.parser()
				   .verifyWith(Keys.hmacShaKeyFor(jwtKey.getBytes(StandardCharsets.UTF_8)))
				   .build()
				   .parseSignedClaims(jwtToken)
				   .getPayload();
	}

	public boolean isValidToken(Claims token){
		return SecurityConstants.JWT_ISSUER.equals(token.getIssuer()) && token.getExpiration().getTime() > new Date().getTime();
	}
}
