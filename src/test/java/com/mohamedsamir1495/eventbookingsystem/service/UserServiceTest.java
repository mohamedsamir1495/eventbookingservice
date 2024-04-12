package com.mohamedsamir1495.eventbookingsystem.service;

import com.mohamedsamir1495.eventbookingsystem.domain.user.UserEntity;
import com.mohamedsamir1495.eventbookingsystem.dto.user.User;
import com.mohamedsamir1495.eventbookingsystem.exception.domain.UserAlreadyExistsException;
import com.mohamedsamir1495.eventbookingsystem.exception.domain.UserNotFoundException;
import com.mohamedsamir1495.eventbookingsystem.mapper.UserMapper;
import com.mohamedsamir1495.eventbookingsystem.repository.UserRepository;
import com.mohamedsamir1495.eventbookingsystem.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

	@Mock
	private UserRepository userRepository;

	@Mock
	private UserMapper userMapper;

	@InjectMocks
	private UserServiceImpl userService;

	@Test
	void createUser_Success() {
		// Arrange
		User userDto = new User("test", "test@example.com", "testtest");

		UserEntity userEntity = new UserEntity();
		userEntity.setEmail("test@example.com");

		when(userRepository.findUserByEmail("test@example.com")).thenReturn(Optional.empty());
		when(userMapper.toEntity(userDto)).thenReturn(userEntity);
		when(userMapper.toDto(userEntity)).thenReturn(userDto);
		when(userRepository.save(userEntity)).thenReturn(userEntity);

		// Act
		User createdUser = userService.createUser(userDto);

		// Assert
		assertNotNull(createdUser);
		assertEquals("test@example.com", createdUser.email());
		verify(userRepository, times(1)).findUserByEmail("test@example.com");
		verify(userRepository, times(1)).save(userEntity);
	}

	@Test
	void createUser_UserAlreadyExists() {
		// Arrange
		User userDto = new User("test", "test@example.com", "testtest");

		when(userRepository.findUserByEmail("test@example.com")).thenReturn(Optional.of(new UserEntity()));

		// Act & Assert
		assertThrows(UserAlreadyExistsException.class, () -> userService.createUser(userDto));
		verify(userRepository, times(1)).findUserByEmail("test@example.com");
		verify(userRepository, never()).save(any());
	}

	@Test
	void findUserByEmail_UserExists() {
		// Arrange
		String email = "test@example.com";
		UserEntity userEntity = new UserEntity();
		userEntity.setEmail(email);

		when(userRepository.findUserByEmail(email)).thenReturn(Optional.of(userEntity));

		// Act
		UserEntity foundUser = userService.findUserByEmail(email);

		// Assert
		assertNotNull(foundUser);
		assertEquals(email, foundUser.getEmail());
		verify(userRepository, times(1)).findUserByEmail(email);
	}

	@Test
	void findUserByEmail_UserDoesNotExist() {
		// Arrange
		String email = "test@example.com";

		when(userRepository.findUserByEmail(email)).thenReturn(Optional.empty());

		// Act
		assertThrows(UserNotFoundException.class, () -> userService.findUserByEmail(email));

		// Assert
		verify(userRepository, times(1)).findUserByEmail(email);
	}

	@Test
	void getLoggedInUser_UserExists() {
		// Arrange
		String email = "test@example.com";
		UserEntity userEntity = new UserEntity();
		userEntity.setEmail(email);
		Authentication authentication = mock(Authentication.class);
		when(authentication.getName()).thenReturn(email);
		SecurityContext securityContext = mock(SecurityContext.class);
		when(securityContext.getAuthentication()).thenReturn(authentication);
		SecurityContextHolder.setContext(securityContext);

		when(userRepository.findUserByEmail(email)).thenReturn(Optional.of(userEntity));

		// Act
		UserEntity loggedInUser = userService.getLoggedInUser();

		// Assert
		assertNotNull(loggedInUser);
		assertEquals(email, loggedInUser.getEmail());
		verify(userRepository, times(1)).findUserByEmail(email);
	}

	@Test
	void getLoggedInUser_UserDoesNotExist() {
		// Arrange
		String email = "test@example.com";
		Authentication authentication = mock(Authentication.class);
		when(authentication.getName()).thenReturn(email);
		SecurityContext securityContext = mock(SecurityContext.class);
		when(securityContext.getAuthentication()).thenReturn(authentication);
		SecurityContextHolder.setContext(securityContext);

		when(userRepository.findUserByEmail(email)).thenReturn(Optional.empty());

		// Act
		assertThrows(UserNotFoundException.class, () -> userService.getLoggedInUser());

		// Assert
		verify(userRepository, times(1)).findUserByEmail(email);
	}
}
