package com.mohamedsamir1495.eventbookingsystem.service;

import com.mohamedsamir1495.eventbookingsystem.domain.event.Category;
import com.mohamedsamir1495.eventbookingsystem.domain.event.Event;
import com.mohamedsamir1495.eventbookingsystem.dto.event.EventCreationResponseDTO;
import com.mohamedsamir1495.eventbookingsystem.dto.event.EventRequestDTO;
import com.mohamedsamir1495.eventbookingsystem.dto.event.EventResponseDTO;
import com.mohamedsamir1495.eventbookingsystem.mapper.EventMapper;
import com.mohamedsamir1495.eventbookingsystem.repository.EventRepository;
import com.mohamedsamir1495.eventbookingsystem.service.impl.EventServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class EventServiceImplTest {

	@Mock
	private EventRepository eventRepository;

	@Mock
	private EventMapper eventMapper;

	@InjectMocks
	private EventServiceImpl eventService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void createEvent_Success() {
		// Arrange
		EventRequestDTO requestDTO = new EventRequestDTO(null, null, null, null, null);
		Event event = new Event();
		EventCreationResponseDTO creationResponseDTO = new EventCreationResponseDTO(1);

		when(eventMapper.toEntity(requestDTO)).thenReturn(event);
		when(eventRepository.save(event)).thenReturn(event);
		when(eventMapper.toCreationResponse(event)).thenReturn(creationResponseDTO);

		// Act
		EventCreationResponseDTO responseDTO = eventService.createEvent(requestDTO);

		// Assert
		assertEquals(creationResponseDTO, responseDTO);
		verify(eventMapper, times(1)).toEntity(requestDTO);
		verify(eventRepository, times(1)).save(event);
		verify(eventMapper, times(1)).toCreationResponse(event);
	}

	@Test
	void findEvents_Success() {
		// Arrange
		String name = "Test Event";
		LocalDate startDate = LocalDate.now();
		LocalDate endDate = LocalDate.now().plusDays(1);
		Category category = Category.CONFERENCE;
		Event event = new Event();
		EventResponseDTO responseDTO = new EventResponseDTO(1, null, null, 0, null, null);

		when(eventRepository.findAll(any(Specification.class))).thenReturn(Collections.singletonList(event));
		when(eventMapper.toResponse(event)).thenReturn(responseDTO);

		// Act
		List<EventResponseDTO> responseDTOs = eventService.findEvents(name, startDate, endDate, category);

		// Assert
		assertEquals(1, responseDTOs.size());
		assertEquals(responseDTO, responseDTOs.get(0));
		verify(eventRepository, times(1)).findAll(any(Specification.class));
		verify(eventMapper, times(1)).toResponse(event);
	}

	@Test
	void findEventById_Success() {
		// Arrange
		Long eventId = 1L;
		Event event = new Event();

		when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));

		// Act
		Optional<Event> result = eventService.findEventById(eventId);

		// Assert
		assertTrue(result.isPresent());
		assertEquals(event, result.get());
		verify(eventRepository, times(1)).findById(eventId);
	}

	@Test
	void updateEvent_Success() {
		// Arrange
		Event event = new Event();

		when(eventRepository.save(event)).thenReturn(event);

		// Act
		Event updatedEvent = eventService.updateEvent(event);

		// Assert
		assertEquals(event, updatedEvent);
		verify(eventRepository, times(1)).save(event);
	}
}
