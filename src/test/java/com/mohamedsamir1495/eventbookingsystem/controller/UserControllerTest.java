package com.mohamedsamir1495.eventbookingsystem.controller;

import com.mohamedsamir1495.eventbookingsystem.configuration.security.SecurityConstants;
import com.mohamedsamir1495.eventbookingsystem.dto.user.Credentials;
import com.mohamedsamir1495.eventbookingsystem.dto.user.User;
import com.mohamedsamir1495.eventbookingsystem.exception.domain.DomainException;
import com.mohamedsamir1495.eventbookingsystem.exception.domain.InvalidUserCredentialsException;
import com.mohamedsamir1495.eventbookingsystem.facade.UserFacade;
import com.mohamedsamir1495.eventbookingsystem.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

	@Mock
	private UserService userService;

	@Mock
	private UserFacade userFacade;

	@InjectMocks
	private UserController userController;

	@Test
	void createUser_Success() {
		// Arrange
		User userDto = new User("test", "test@example.com", "testtest");

		when(userService.createUser(userDto)).thenReturn(userDto);

		// Act
		User createdUser = userController.createUser(userDto);

		// Assert
		assertNotNull(createdUser);
		verify(userService, times(1)).createUser(userDto);
	}

	@Test
	void authorizeUser_Authenticated() {
		// Arrange
		Credentials credentials = new Credentials("test@example.com", null);
		User userDetails = new User("test", "test@example.com", "testtest");
		String token = "testToken";

		when(userFacade.authorizeUser(credentials)).thenReturn(token);

		// Act
		ResponseEntity<String> response = userController.authorizeUser(credentials);

		// Assert
		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertTrue(response.getHeaders().containsKey(SecurityConstants.JWT_HEADER));
		assertEquals(token, response.getHeaders().getFirst(SecurityConstants.JWT_HEADER));
		assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType());
	}

	@Test
	void authorizeUser_Unauthenticated() {
		// Arrange
		Credentials credentials = new Credentials("test@example.com", null);

		when(userFacade.authorizeUser(credentials)).thenThrow(new InvalidUserCredentialsException());

		// Act
		DomainException exception = assertThrows(InvalidUserCredentialsException.class, () -> userController.authorizeUser(credentials));

		// Assert
		assertEquals(HttpStatus.UNAUTHORIZED, exception.getStatus());
	}
}
