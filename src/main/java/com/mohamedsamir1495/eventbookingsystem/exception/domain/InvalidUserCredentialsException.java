package com.mohamedsamir1495.eventbookingsystem.exception.domain;

import org.springframework.http.HttpStatus;

public class InvalidUserCredentialsException extends DomainException {

	public InvalidUserCredentialsException() {
		super(HttpStatus.UNAUTHORIZED, "Invalid user credentials used for authorization");
	}

}
