package com.mohamedsamir1495.eventbookingsystem.facade;

import com.mohamedsamir1495.eventbookingsystem.domain.event.Event;
import com.mohamedsamir1495.eventbookingsystem.domain.event.Reservation;
import com.mohamedsamir1495.eventbookingsystem.domain.user.UserEntity;
import com.mohamedsamir1495.eventbookingsystem.dto.event.EventResponseDTO;
import com.mohamedsamir1495.eventbookingsystem.dto.event.TicketRequest;
import com.mohamedsamir1495.eventbookingsystem.exception.domain.NotEnoughSeatsAvailableException;
import com.mohamedsamir1495.eventbookingsystem.exception.generic.ResourceNotFoundException;
import com.mohamedsamir1495.eventbookingsystem.facade.impl.EventFacadeImpl;
import com.mohamedsamir1495.eventbookingsystem.mapper.EventMapper;
import com.mohamedsamir1495.eventbookingsystem.service.EventService;
import com.mohamedsamir1495.eventbookingsystem.service.ReservationService;
import com.mohamedsamir1495.eventbookingsystem.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EventFacadeImplTest {

	@Mock
	private EventService eventService;

	@Mock
	private UserService userService;

	@Mock
	private ReservationService reservationService;

	@Mock
	private EventMapper eventMapper;

	@InjectMocks
	private EventFacadeImpl eventFacade;

	@Test
	void reserveTickets_Success() {
		// Arrange
		Long eventId = 1L;
		TicketRequest ticketRequest = new TicketRequest(2);
		UserEntity userEntity = new UserEntity();
		Event event = new Event();
		event.setId(eventId);
		event.setAvailableAttendeesCount(10);

		when(userService.getLoggedInUser()).thenReturn(userEntity);
		when(eventService.findEventById(eventId)).thenReturn(Optional.of(event));
		doNothing().when(reservationService).createReservationForEvent(userEntity, event);

		// Act
		boolean reservationResult = eventFacade.reserveTickets(eventId, ticketRequest);

		// Assert
		assertTrue(reservationResult);
		verify(userService, times(1)).getLoggedInUser();
		verify(eventService,times(1)).updateEvent(event);
		verify(eventService, times(1)).findEventById(eventId);
		verify(reservationService, times(1)).createReservationForEvent(userEntity, event);
	}

	@Test
	void reserveTickets_NotEnoughSeatsAvailable() {
		// Arrange
		Long eventId = 1L;
		TicketRequest ticketRequest = new TicketRequest(20);
		Event event = new Event();
		event.setName("Test Event");
		event.setAvailableAttendeesCount(10);

		when(eventService.findEventById(eventId)).thenReturn(Optional.of(event));

		// Act & Assert
		assertThrows(NotEnoughSeatsAvailableException.class, () -> eventFacade.reserveTickets(eventId, ticketRequest));
		verify(eventService, times(1)).findEventById(eventId);
		verifyNoMoreInteractions(reservationService);
	}

	@Test
	void reserveTickets_EventNotFound() {
		// Arrange
		Long eventId = 1L;
		TicketRequest ticketRequest = new TicketRequest(1);

		when(eventService.findEventById(eventId)).thenReturn(Optional.empty());

		// Act & Assert
		assertThrows(ResourceNotFoundException.class, () -> eventFacade.reserveTickets(eventId, ticketRequest));
		verify(eventService, times(1)).findEventById(eventId);
		verifyNoMoreInteractions(reservationService);
	}

	@Test
	void getBookedEvents_Success() {
		// Arrange
		UserEntity userEntity = new UserEntity();
		Reservation reservation = new Reservation();
		reservation.setEvent(new Event());
		List<Reservation> reservations = Collections.singletonList(reservation);
		List<EventResponseDTO> eventResponseDTOs = Collections.singletonList(new EventResponseDTO(1, null, null, 1, null, null));

		when(userService.getLoggedInUser()).thenReturn(userEntity);
		when(reservationService.getBookedReservation(userEntity)).thenReturn(reservations);
		when(eventMapper.toResponse(any())).thenReturn(new EventResponseDTO(1, null, null, 1, null, null));

		// Act
		List<EventResponseDTO> bookedEvents = eventFacade.getBookedEvents();

		// Assert
		assertNotNull(bookedEvents);
		assertFalse(bookedEvents.isEmpty());
		verify(userService, times(1)).getLoggedInUser();
		verify(reservationService, times(1)).getBookedReservation(userEntity);
		verify(eventMapper, times(reservations.size())).toResponse(any());
	}

	@Test
	void cancelEvent_Success() {
		// Arrange
		Long eventId = 1L;
		UserEntity userEntity = new UserEntity();
		Event event = new Event();

		when(userService.getLoggedInUser()).thenReturn(userEntity);
		when(eventService.findEventById(eventId)).thenReturn(Optional.of(event));

		// Act
		boolean cancellationResult = eventFacade.cancelEvent(eventId);

		// Assert
		assertTrue(cancellationResult);
		verify(userService, times(1)).getLoggedInUser();
		verify(eventService, times(1)).findEventById(eventId);
		verify(reservationService, times(1)).cancelReservationForEvent(userEntity, event);
	}

	@Test
	void cancelEvent_EventNotFound() {
		// Arrange
		Long eventId = 1L;

		when(eventService.findEventById(eventId)).thenReturn(Optional.empty());

		// Act & Assert
		assertThrows(ResourceNotFoundException.class, () -> eventFacade.cancelEvent(eventId));
		verify(eventService, times(1)).findEventById(eventId);
		verifyNoMoreInteractions(reservationService);
	}
}
