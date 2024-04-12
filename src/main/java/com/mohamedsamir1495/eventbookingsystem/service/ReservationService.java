package com.mohamedsamir1495.eventbookingsystem.service;

import com.mohamedsamir1495.eventbookingsystem.domain.event.Event;
import com.mohamedsamir1495.eventbookingsystem.domain.event.Reservation;
import com.mohamedsamir1495.eventbookingsystem.domain.user.UserEntity;

import java.util.List;


public interface ReservationService {

	void createReservationForEvent(UserEntity user, Event event);

	List<Reservation> getBookedReservation(UserEntity user);

	void cancelReservationForEvent(UserEntity user, Event event);
}
