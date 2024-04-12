package com.mohamedsamir1495.eventbookingsystem.facade;

import com.mohamedsamir1495.eventbookingsystem.dto.event.EventResponseDTO;
import com.mohamedsamir1495.eventbookingsystem.dto.event.TicketRequest;

import java.util.List;

public interface EventFacade {


	boolean reserveTickets(Long eventId, TicketRequest ticketRequest);

	List<EventResponseDTO> getBookedEvents();

	boolean cancelEvent(Long eventId);
}
