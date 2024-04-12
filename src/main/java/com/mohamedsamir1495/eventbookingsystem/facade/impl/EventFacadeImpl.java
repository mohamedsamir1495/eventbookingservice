package com.mohamedsamir1495.eventbookingsystem.facade.impl;

import com.mohamedsamir1495.eventbookingsystem.domain.event.Event;
import com.mohamedsamir1495.eventbookingsystem.domain.event.Reservation;
import com.mohamedsamir1495.eventbookingsystem.domain.user.UserEntity;
import com.mohamedsamir1495.eventbookingsystem.dto.event.EventResponseDTO;
import com.mohamedsamir1495.eventbookingsystem.dto.event.TicketRequest;
import com.mohamedsamir1495.eventbookingsystem.exception.domain.NotEnoughSeatsAvailableException;
import com.mohamedsamir1495.eventbookingsystem.exception.generic.ResourceNotFoundException;
import com.mohamedsamir1495.eventbookingsystem.facade.EventFacade;
import com.mohamedsamir1495.eventbookingsystem.mapper.EventMapper;
import com.mohamedsamir1495.eventbookingsystem.service.EventService;
import com.mohamedsamir1495.eventbookingsystem.service.ReservationService;
import com.mohamedsamir1495.eventbookingsystem.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class EventFacadeImpl implements EventFacade {

	private final EventService eventService;

	private final UserService userService;

	private final ReservationService reservationService;

	private EventMapper eventMapper;

	public boolean reserveTickets(Long eventId, TicketRequest ticketRequest) {
		UserEntity currentLoggedInUser = userService.getLoggedInUser();
		Event event = eventService.findEventById(eventId)
								  .orElseThrow(() -> new ResourceNotFoundException("Event", "id", eventId.toString()));

		if(event.getAvailableAttendeesCount() < ticketRequest.attendeesCount())
			throw new  NotEnoughSeatsAvailableException(event.getName());

		event.setAvailableAttendeesCount(event.getAvailableAttendeesCount() - ticketRequest.attendeesCount());
		eventService.updateEvent(event);
		reservationService.createReservationForEvent(currentLoggedInUser, event);
		return true;
	}

	public List<EventResponseDTO> getBookedEvents() {
		UserEntity currentLoggedInUser = userService.getLoggedInUser();
		return reservationService.getBookedReservation(currentLoggedInUser)
								 .stream()
								 .map(Reservation::getEvent)
								 .map(eventMapper::toResponse)
								 .toList();
	}

	public boolean cancelEvent(Long eventId) {
		UserEntity currentLoggedInUser = userService.getLoggedInUser();
		Event event = eventService.findEventById(eventId)
								  .orElseThrow(() -> new ResourceNotFoundException("Event", "id", eventId.toString()));

		reservationService.cancelReservationForEvent(currentLoggedInUser, event);
		return true;
	}
}
