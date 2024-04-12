package com.mohamedsamir1495.eventbookingsystem.service;

import com.mohamedsamir1495.eventbookingsystem.domain.event.Category;
import com.mohamedsamir1495.eventbookingsystem.domain.event.Event;
import com.mohamedsamir1495.eventbookingsystem.dto.event.EventCreationResponseDTO;
import com.mohamedsamir1495.eventbookingsystem.dto.event.EventRequestDTO;
import com.mohamedsamir1495.eventbookingsystem.dto.event.EventResponseDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface EventService {

	EventCreationResponseDTO createEvent(EventRequestDTO requestDTO);

	List<EventResponseDTO> findEvents(String name, LocalDate startDate, LocalDate endDate, Category category);

	Optional<Event> findEventById(Long eventId);

	Event updateEvent(Event event);
}
