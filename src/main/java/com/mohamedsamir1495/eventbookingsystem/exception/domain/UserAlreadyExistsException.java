package com.mohamedsamir1495.eventbookingsystem.exception.domain;

import org.springframework.http.HttpStatus;

public class UserAlreadyExistsException extends DomainException {

	public UserAlreadyExistsException(String userEmail) {
		super(
				HttpStatus.FORBIDDEN,
				String.format("User with email [ %s ] already exists.", userEmail)
		);
	}

}
