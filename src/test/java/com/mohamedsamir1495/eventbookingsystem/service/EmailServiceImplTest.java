package com.mohamedsamir1495.eventbookingsystem.service;

import com.mohamedsamir1495.eventbookingsystem.domain.event.Event;
import com.mohamedsamir1495.eventbookingsystem.domain.event.Reservation;
import com.mohamedsamir1495.eventbookingsystem.domain.user.UserEntity;
import com.mohamedsamir1495.eventbookingsystem.repository.ReservationRepository;
import com.mohamedsamir1495.eventbookingsystem.service.impl.EmailServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmailServiceImplTest {

    @Mock
    private JavaMailSender emailSender;

    @Mock
    private ReservationRepository reservationRepository;

    @InjectMocks
    private EmailServiceImpl emailService;


    @Test
    void notifyUsers_NoReservationsTomorrow() {
        // Arrange
        when(reservationRepository.findReservationsWithReservedStatusForEventsStartingOn(any(LocalDate.class))).thenReturn(null);

        // Act
        emailService.notifyUsers();

        // Assert
        verify(emailSender,times(0)).send(any(SimpleMailMessage.class));
    }

    @Test
    void notifyUsers_ReservationsTomorrow() {
        // Arrange
        UserEntity user = new UserEntity();
        Event event = new Event();
        Reservation reservation = new Reservation();
        reservation.setUser(user);
        reservation.setEvent(event);
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(reservation);

        when(reservationRepository.findReservationsWithReservedStatusForEventsStartingOn(any(LocalDate.class))).thenReturn(reservations);

        // Act
        emailService.notifyUsers();

        // Assert
        verify(emailSender, times(1)).send(any(SimpleMailMessage.class));
    }
}
