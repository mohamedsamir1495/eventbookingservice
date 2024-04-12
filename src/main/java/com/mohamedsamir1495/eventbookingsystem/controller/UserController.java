package com.mohamedsamir1495.eventbookingsystem.controller;

import com.mohamedsamir1495.eventbookingsystem.configuration.security.SecurityConstants;
import com.mohamedsamir1495.eventbookingsystem.dto.user.Credentials;
import com.mohamedsamir1495.eventbookingsystem.dto.user.User;
import com.mohamedsamir1495.eventbookingsystem.facade.UserFacade;
import com.mohamedsamir1495.eventbookingsystem.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Tag(name = "users")
@RequestMapping(path="/", produces = { MediaType.APPLICATION_JSON_VALUE})
@SecurityRequirements()
public class UserController {

	private UserService userService;

	private UserFacade userFacade;

	@Operation(
			summary = "Create a new user.",
			description = "This endpoint allows customers to create a new user."
	)
	@PostMapping("/users")
	public User createUser(@Valid User user){
		return userService.createUser(user);
	}

	@Operation(
			summary = "Authenticate a user.",
			description = "This endpoint allows users to authenticate and receive a Bearer token"
	)
	@SecurityRequirements()
	@PostMapping("/auth")
	public ResponseEntity<String> authorizeUser(@Valid Credentials credentials) {
		String token = userFacade.authorizeUser(credentials);
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.set(SecurityConstants.JWT_HEADER, token);
		return ResponseEntity.ok().headers(httpHeaders).contentType(MediaType.APPLICATION_JSON).body("{\"token\":\"" + token + "\"}");
	}

}
