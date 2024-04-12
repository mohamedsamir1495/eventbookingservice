package com.mohamedsamir1495.eventbookingsystem.facade.impl;

import com.mohamedsamir1495.eventbookingsystem.domain.user.UserEntity;
import com.mohamedsamir1495.eventbookingsystem.dto.user.Credentials;
import com.mohamedsamir1495.eventbookingsystem.dto.user.User;
import com.mohamedsamir1495.eventbookingsystem.exception.domain.InvalidUserCredentialsException;
import com.mohamedsamir1495.eventbookingsystem.exception.domain.UserNotFoundException;
import com.mohamedsamir1495.eventbookingsystem.facade.UserFacade;
import com.mohamedsamir1495.eventbookingsystem.mapper.UserMapper;
import com.mohamedsamir1495.eventbookingsystem.service.SecurityService;
import com.mohamedsamir1495.eventbookingsystem.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserFacadeImpl implements UserFacade {

	private final UserService userService;

	private final SecurityService securityService;

	private final UserMapper userMapper;

	@Override
	public String authorizeUser(Credentials credentials) {
		UserEntity savedUser;
		try {
			savedUser = userService.findUserByEmail(credentials.email());
		} catch (UserNotFoundException exception) {
			throw new InvalidUserCredentialsException();
		}
		User userDetails = userMapper.toDto(savedUser);

		return securityService.generateToken(userDetails);
	}

}
