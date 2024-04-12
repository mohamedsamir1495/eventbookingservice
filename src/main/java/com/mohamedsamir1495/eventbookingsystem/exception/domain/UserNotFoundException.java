package com.mohamedsamir1495.eventbookingsystem.exception.domain;

import org.springframework.http.HttpStatus;

public class UserNotFoundException extends DomainException {

	public UserNotFoundException(String userEmail) {
		super(
				HttpStatus.NOT_FOUND,
				String.format("User with email [ %s ] not saved in database.", userEmail)
		);
	}

}
