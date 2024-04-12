package com.mohamedsamir1495.eventbookingsystem.service.impl;

import com.mohamedsamir1495.eventbookingsystem.domain.event.Event;
import com.mohamedsamir1495.eventbookingsystem.domain.event.Reservation;
import com.mohamedsamir1495.eventbookingsystem.domain.event.ReservationStatus;
import com.mohamedsamir1495.eventbookingsystem.domain.user.UserEntity;
import com.mohamedsamir1495.eventbookingsystem.repository.ReservationRepository;
import com.mohamedsamir1495.eventbookingsystem.service.ReservationService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ReservationServiceImpl implements ReservationService {

	private final ReservationRepository reservationRepository;

	public void createReservationForEvent(UserEntity user, Event event) {
		reservationRepository.save(Reservation.builder().user(user).event(event).status(ReservationStatus.RESERVED).build());
	}

	public List<Reservation> getBookedReservation(UserEntity user) {
		return reservationRepository.findAllBookedReservationByUser(user);
	}

	@Transactional
	public void cancelReservationForEvent(UserEntity user, Event event) {
		Reservation reservation = reservationRepository.findLatestReservationForUserWithEvent(user, event);
		reservation.setStatus(ReservationStatus.CANCELLED);
		reservationRepository.save(reservation);
	}
}
