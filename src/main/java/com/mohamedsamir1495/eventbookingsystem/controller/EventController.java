package com.mohamedsamir1495.eventbookingsystem.controller;

import com.mohamedsamir1495.eventbookingsystem.domain.event.Category;
import com.mohamedsamir1495.eventbookingsystem.dto.event.EventCreationResponseDTO;
import com.mohamedsamir1495.eventbookingsystem.dto.event.EventRequestDTO;
import com.mohamedsamir1495.eventbookingsystem.dto.event.EventResponseDTO;
import com.mohamedsamir1495.eventbookingsystem.dto.event.TicketRequest;
import com.mohamedsamir1495.eventbookingsystem.facade.EventFacade;
import com.mohamedsamir1495.eventbookingsystem.service.EventService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/", produces = { MediaType.APPLICATION_JSON_VALUE })
public class EventController {

	private EventService eventService;

	private EventFacade eventFacade;

	@Tag(name = "events")
	@Operation(
			summary = "Create a new event.",
			description = "This endpoint allows customers to create a new event."
	)
	@PostMapping("/events")
	public ResponseEntity<EventCreationResponseDTO> createEvent(@Valid EventRequestDTO requestDTO) {
		return ResponseEntity.ok().body(eventService.createEvent(requestDTO));
	}

	@Tag(name = "events")
	@Operation(
			summary = "Get all events or search for events.",
			description = "This endpoint allows customers to retrieve all events or search for events by name, date range or category."
	)
	@GetMapping("/events")
	@SecurityRequirements()
	public ResponseEntity<List<EventResponseDTO>> getEvents(
			@RequestParam(required = false) String name,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
			@RequestParam(required = false) Category category) {

		List<EventResponseDTO> events = eventService.findEvents(name, startDate, endDate, category);
		if (events.isEmpty()) {
			return ResponseEntity.notFound().build();
		} else {
			return ResponseEntity.ok(events);
		}
	}


	@Tag(name = "tickets")
	@Operation(
			summary = "Reserve tickets for an event.",
			description = "This endpoint allows customers to reserve tickets for an event."
	)
	@PostMapping("/events/{eventId}/tickets")
	public ResponseEntity<String> reserveTickets(@PathVariable("eventId") Long eventId, @Valid @RequestBody TicketRequest ticketRequest) {
		eventFacade.reserveTickets(eventId, ticketRequest);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}


	@Tag(name = "events")
	@Operation(
			summary = "Get all booked events by user.",
			description = "This endpoint allows customers to get all booked events for the logged in user."
	)
	@GetMapping("/user/events")
	public ResponseEntity<List<EventResponseDTO>> getBookedEvents() {
		return ResponseEntity.ok().body(eventFacade.getBookedEvents());
	}

	@Tag(name = "tickets")
	@Operation(
			summary = "Cancel ticket reservation for an event.",
			description = "This endpoint allows customers to cancel ticket reservation for an event for the logged in user."
	)
	@PutMapping("/events/{eventId}")
	public ResponseEntity<String> cancelEvent(@PathVariable("eventId") Long eventId) {
		eventFacade.cancelEvent(eventId);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
}
