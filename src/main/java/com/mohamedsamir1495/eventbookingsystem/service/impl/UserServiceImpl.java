package com.mohamedsamir1495.eventbookingsystem.service.impl;

import com.mohamedsamir1495.eventbookingsystem.domain.user.UserEntity;
import com.mohamedsamir1495.eventbookingsystem.dto.user.User;
import com.mohamedsamir1495.eventbookingsystem.exception.domain.UserAlreadyExistsException;
import com.mohamedsamir1495.eventbookingsystem.exception.domain.UserNotFoundException;
import com.mohamedsamir1495.eventbookingsystem.mapper.UserMapper;
import com.mohamedsamir1495.eventbookingsystem.repository.UserRepository;
import com.mohamedsamir1495.eventbookingsystem.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

	private UserRepository userRepository;

	private UserMapper userMapper;

	public User createUser(User dto) {
		if (userRepository.findUserByEmail(dto.email()).isPresent())
			throw new UserAlreadyExistsException(dto.email());

		return userMapper.toDto(userRepository.save(userMapper.toEntity(dto)));
	}

	public UserEntity findUserByEmail(String email) {
		return userRepository.findUserByEmail(email)
							 .orElseThrow(() -> new UserNotFoundException(email));
	}

	public UserEntity getLoggedInUser() {
		return findUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
	}

}
