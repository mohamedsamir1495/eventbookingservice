package com.mohamedsamir1495.eventbookingsystem.domain.event;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.mohamedsamir1495.eventbookingsystem.configuration.converter.CategoryToStringConverter;

@JsonSerialize(using = CategoryToStringConverter.class)
public enum Category {
	CONCERT("Concert"),
	CONFERENCE("Conference"),
	GAME("Game");

	private final String displayName;

	Category(String displayName) {
		this.displayName = displayName;
	}

	@Override
	public String toString() {
		return displayName;
	}

}
