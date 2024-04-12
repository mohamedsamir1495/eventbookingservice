package com.mohamedsamir1495.eventbookingsystem.service.impl;

import com.mohamedsamir1495.eventbookingsystem.domain.event.Category;
import com.mohamedsamir1495.eventbookingsystem.domain.event.Event;
import com.mohamedsamir1495.eventbookingsystem.domain.event.EventSpecifications;
import com.mohamedsamir1495.eventbookingsystem.dto.event.EventCreationResponseDTO;
import com.mohamedsamir1495.eventbookingsystem.dto.event.EventRequestDTO;
import com.mohamedsamir1495.eventbookingsystem.dto.event.EventResponseDTO;
import com.mohamedsamir1495.eventbookingsystem.mapper.EventMapper;
import com.mohamedsamir1495.eventbookingsystem.repository.EventRepository;
import com.mohamedsamir1495.eventbookingsystem.service.EventService;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class EventServiceImpl implements EventService {

	private EventRepository eventRepository;

	private EventMapper eventMapper;

	public EventCreationResponseDTO createEvent(EventRequestDTO requestDTO){
		Event savedEntity = eventRepository.save(eventMapper.toEntity(requestDTO));
		return eventMapper.toCreationResponse(savedEntity);
	}

	public List<EventResponseDTO> findEvents(String name, LocalDate startDate, LocalDate endDate, Category category){
		return eventRepository.findAll(Specification
									  .where(EventSpecifications.hasName(name))
									  .and(EventSpecifications.hasStartDate(startDate))
									  .and(EventSpecifications.hasEndDate(endDate))
									  .and(EventSpecifications.hasCategory(category)))
							  .stream().map(eventMapper::toResponse)
							  .toList();
	}

	public Optional<Event> findEventById(Long eventId){
		return eventRepository.findById(eventId);
	}

	@Override
	public Event updateEvent(Event event) {
		return eventRepository.save(event);
	}
}
