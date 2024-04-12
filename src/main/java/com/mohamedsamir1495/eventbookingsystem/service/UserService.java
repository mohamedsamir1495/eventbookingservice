package com.mohamedsamir1495.eventbookingsystem.service;

import com.mohamedsamir1495.eventbookingsystem.domain.user.UserEntity;
import com.mohamedsamir1495.eventbookingsystem.dto.user.User;

public interface UserService {

	User createUser(User dto);

	UserEntity findUserByEmail(String email);

	UserEntity getLoggedInUser();
}
