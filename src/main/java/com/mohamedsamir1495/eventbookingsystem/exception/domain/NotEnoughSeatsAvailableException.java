package com.mohamedsamir1495.eventbookingsystem.exception.domain;

import org.springframework.http.HttpStatus;

public class NotEnoughSeatsAvailableException extends DomainException {

	public NotEnoughSeatsAvailableException(String eventName) {
		super(
				HttpStatus.CONFLICT,
				String.format("Event [ %s ] doesnt have enough seats to fulfil your reservation request.", eventName)
		);
	}

}
