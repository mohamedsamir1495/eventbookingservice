package com.mohamedsamir1495.eventbookingsystem.controller;

import com.mohamedsamir1495.eventbookingsystem.dto.event.EventCreationResponseDTO;
import com.mohamedsamir1495.eventbookingsystem.dto.event.EventRequestDTO;
import com.mohamedsamir1495.eventbookingsystem.dto.event.EventResponseDTO;
import com.mohamedsamir1495.eventbookingsystem.dto.event.TicketRequest;
import com.mohamedsamir1495.eventbookingsystem.facade.EventFacade;
import com.mohamedsamir1495.eventbookingsystem.service.EventService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EventControllerTest {

    @Mock
    private EventService eventService;

    @Mock
    private EventFacade eventFacade;

    @InjectMocks
    private EventController eventController;

    @Test
    void createEvent_Success() {
        // Arrange
        EventRequestDTO requestDTO = new EventRequestDTO(null,null,1,null,null);
        EventCreationResponseDTO creationResponseDTO = new EventCreationResponseDTO(1);

        when(eventService.createEvent(requestDTO)).thenReturn(creationResponseDTO);

        // Act
        ResponseEntity<EventCreationResponseDTO> response = eventController.createEvent(requestDTO);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(creationResponseDTO, response.getBody());
        verify(eventService, times(1)).createEvent(requestDTO);
    }

    @Test
    void getEvents_NoEventsFound() {
        // Arrange
        when(eventService.findEvents(null, null, null, null)).thenReturn(Collections.emptyList());

        // Act
        ResponseEntity<List<EventResponseDTO>> response = eventController.getEvents(null, null, null, null);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(eventService, times(1)).findEvents(null, null, null, null);
    }

    @Test
    void getEvents_EventsFound() {
        // Arrange
        List<EventResponseDTO> eventResponseDTOList = Collections.singletonList(new EventResponseDTO(1, null, null, 1, null, null));

        when(eventService.findEvents(null, null, null, null)).thenReturn(eventResponseDTOList);

        // Act
        ResponseEntity<List<EventResponseDTO>> response = eventController.getEvents(null, null, null, null);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(eventResponseDTOList, response.getBody());
        verify(eventService, times(1)).findEvents(null, null, null, null);
    }

    @Test
    void reserveTickets_Success() {
        // Arrange
        Long eventId = 1L;
        TicketRequest ticketRequest = new TicketRequest(5);

        // Act
        ResponseEntity<String> response = eventController.reserveTickets(eventId, ticketRequest);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(eventFacade, times(1)).reserveTickets(eventId, ticketRequest);
    }

    @Test
    void getBookedEvents_Success() {
        // Arrange
        List<EventResponseDTO> eventResponseDTOList = Collections.singletonList(new EventResponseDTO(1, null, null, 1, null, null));

        when(eventFacade.getBookedEvents()).thenReturn(eventResponseDTOList);

        // Act
        ResponseEntity<List<EventResponseDTO>> response = eventController.getBookedEvents();

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(eventResponseDTOList, response.getBody());
        verify(eventFacade, times(1)).getBookedEvents();
    }

    @Test
    void cancelEvent_Success() {
        // Arrange
        Long eventId = 1L;

        // Act
        ResponseEntity<String> response = eventController.cancelEvent(eventId);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(eventFacade, times(1)).cancelEvent(eventId);
    }
}
