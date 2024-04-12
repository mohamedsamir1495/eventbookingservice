package com.mohamedsamir1495.eventbookingsystem.configuration.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
public class SecurityConfig {

	@Value("${h2.enabled:false}")
	private boolean isH2Enabled;

	private final JWTTokenValidatorFilter jwtTokenValidatorFilter;

	@Bean
	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {

		http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.cors(AbstractHttpConfigurer::disable)
			.csrf(AbstractHttpConfigurer::disable)
			.addFilterBefore(jwtTokenValidatorFilter, BasicAuthenticationFilter.class)
			.formLogin(AbstractHttpConfigurer::disable)
			.httpBasic(AbstractHttpConfigurer::disable)
			.authorizeHttpRequests(requests -> requests
					.requestMatchers("/v3/**", "/h2/**", "/console/**", "/docs/**", "/swagger-ui/**", "/users", "/auth").permitAll()
					.requestMatchers(HttpMethod.GET, "/events").permitAll()
					.requestMatchers(HttpMethod.GET, "/user/events").authenticated()
					.requestMatchers(HttpMethod.POST, "/events/**").authenticated()
					.requestMatchers(HttpMethod.PUT, "/events/**").authenticated()
			);

		if (isH2Enabled)
			http.headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin)); // Allow H2 console to be displayed in a frame

		return http.build();
	}

}
