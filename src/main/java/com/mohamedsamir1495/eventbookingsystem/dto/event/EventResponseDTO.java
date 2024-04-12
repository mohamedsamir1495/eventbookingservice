package com.mohamedsamir1495.eventbookingsystem.dto.event;

import com.mohamedsamir1495.eventbookingsystem.domain.event.Category;

import java.time.LocalDate;

public record EventResponseDTO(long id,
							   String name,
							   LocalDate date,
							   int availableAttendeesCount,
							   String description,
							   Category category) {
}
