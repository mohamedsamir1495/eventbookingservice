package com.mohamedsamir1495.eventbookingsystem.exception.domain;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class DomainException extends RuntimeException {

	final HttpStatus status;

	public DomainException(HttpStatus status, String msg) {
		super(msg);
		this.status = status;
	}

}
