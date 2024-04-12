package com.mohamedsamir1495.eventbookingsystem.configuration.security;

import com.mohamedsamir1495.eventbookingsystem.service.SecurityService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JWTTokenValidatorFilter extends OncePerRequestFilter {


	private final SecurityService securityService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String jwtToken = request.getHeader(SecurityConstants.JWT_HEADER);
		if (StringUtils.isNotBlank(jwtToken)) {
			try {
				Claims claims = securityService.extractToken(jwtToken);
				if(!securityService.isValidToken(claims))
					throw new BadCredentialsException("Invalid Token received!");

				String email = String.valueOf(claims.get(SecurityConstants.JWT_EMAIL));
				String authorities = (String) claims.get("authorities");
				Authentication auth = new UsernamePasswordAuthenticationToken(email, null, AuthorityUtils.commaSeparatedStringToAuthorityList(authorities));
				SecurityContextHolder.getContext().setAuthentication(auth);
			} catch (Exception e) {
				throw new BadCredentialsException("Invalid Token received!");
			}

		}
		filterChain.doFilter(request, response);
	}
}
