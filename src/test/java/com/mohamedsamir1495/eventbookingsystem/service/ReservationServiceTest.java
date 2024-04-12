package com.mohamedsamir1495.eventbookingsystem.service;

import com.mohamedsamir1495.eventbookingsystem.domain.event.Event;
import com.mohamedsamir1495.eventbookingsystem.domain.event.Reservation;
import com.mohamedsamir1495.eventbookingsystem.domain.event.ReservationStatus;
import com.mohamedsamir1495.eventbookingsystem.domain.user.UserEntity;
import com.mohamedsamir1495.eventbookingsystem.repository.ReservationRepository;
import com.mohamedsamir1495.eventbookingsystem.service.impl.ReservationServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {

    @Mock
    private ReservationRepository reservationRepository;

    @InjectMocks
    private ReservationServiceImpl reservationService;

    @Test
    void createReservationForEvent_Success() {
        // Arrange
        UserEntity user = new UserEntity();
        Event event = new Event();

        // Act
        reservationService.createReservationForEvent(user, event);

        // Assert
        verify(reservationRepository, times(1)).save(any(Reservation.class));
    }

    @Test
    void getBookedReservation_Success() {
        // Arrange
        UserEntity user = new UserEntity();
        Reservation reservation = new Reservation();
        List<Reservation> reservations = Collections.singletonList(reservation);

        when(reservationRepository.findAllBookedReservationByUser(user)).thenReturn(reservations);

        // Act
        List<Reservation> result = reservationService.getBookedReservation(user);

        // Assert
        assertEquals(reservations, result);
        verify(reservationRepository, times(1)).findAllBookedReservationByUser(user);
    }

    @Test
    void cancelReservationForEvent_Success() {
        // Arrange
        UserEntity user = new UserEntity();
        Event event = new Event();
        Reservation reservation = new Reservation();
        reservation.setStatus(ReservationStatus.RESERVED);

        when(reservationRepository.findLatestReservationForUserWithEvent(user, event)).thenReturn(reservation);

        // Act
        reservationService.cancelReservationForEvent(user, event);

        // Assert
        assertEquals(ReservationStatus.CANCELLED, reservation.getStatus());
        verify(reservationRepository, times(1)).findLatestReservationForUserWithEvent(user, event);
        verify(reservationRepository, times(1)).save(reservation);
    }
}
