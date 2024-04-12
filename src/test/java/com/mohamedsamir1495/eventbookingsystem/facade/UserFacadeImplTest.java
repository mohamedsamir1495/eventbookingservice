package com.mohamedsamir1495.eventbookingsystem.facade;

import com.mohamedsamir1495.eventbookingsystem.domain.user.UserEntity;
import com.mohamedsamir1495.eventbookingsystem.dto.user.Credentials;
import com.mohamedsamir1495.eventbookingsystem.dto.user.User;
import com.mohamedsamir1495.eventbookingsystem.exception.domain.InvalidUserCredentialsException;
import com.mohamedsamir1495.eventbookingsystem.exception.domain.UserNotFoundException;
import com.mohamedsamir1495.eventbookingsystem.facade.impl.UserFacadeImpl;
import com.mohamedsamir1495.eventbookingsystem.mapper.UserMapper;
import com.mohamedsamir1495.eventbookingsystem.service.SecurityService;
import com.mohamedsamir1495.eventbookingsystem.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserFacadeImplTest {

    @Mock
    private UserService userService;

    @Mock
    private SecurityService securityService;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserFacadeImpl userFacade;

    @Test
    void authorizeUser_Success() {
        // Arrange
        Credentials credentials = new Credentials("test@example.com",null);
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail("test@example.com");
        User userDetails = new User("test","test@example.com","testtest");
        String token = "testToken";

        when(userService.findUserByEmail(credentials.email())).thenReturn(userEntity);
        when(userMapper.toDto(userEntity)).thenReturn(userDetails);
        when(securityService.generateToken(userDetails)).thenReturn(token);

        // Act
        String generatedToken = userFacade.authorizeUser(credentials);

        // Assert
        assertNotNull(generatedToken);
        assertEquals(token, generatedToken);
        verify(userService, times(1)).findUserByEmail(credentials.email());
        verify(userMapper, times(1)).toDto(userEntity);
        verify(securityService, times(1)).generateToken(userDetails);
    }

    @Test
    void authorizeUser_UserNotFoundException() {
        // Arrange
        Credentials credentials = new Credentials("test@example.com",null);

        when(userService.findUserByEmail(credentials.email())).thenThrow(UserNotFoundException.class);

        // Act & Assert
        assertThrows(InvalidUserCredentialsException.class, () -> userFacade.authorizeUser(credentials));
        verify(userService, times(1)).findUserByEmail(credentials.email());
        verifyNoInteractions(userMapper);
        verifyNoInteractions(securityService);
    }
}
