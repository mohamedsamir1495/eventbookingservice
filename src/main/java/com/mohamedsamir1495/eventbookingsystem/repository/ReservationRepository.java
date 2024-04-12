package com.mohamedsamir1495.eventbookingsystem.repository;

import com.mohamedsamir1495.eventbookingsystem.domain.event.Event;
import com.mohamedsamir1495.eventbookingsystem.domain.event.Reservation;
import com.mohamedsamir1495.eventbookingsystem.domain.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

	@Query("select reservation from Reservation reservation where reservation.user =:user and reservation.status = 'RESERVED'")
	List<Reservation> findAllBookedReservationByUser(UserEntity user);

	@Query("select reservation from Reservation reservation where reservation.status = 'RESERVED' and reservation.user =:user and reservation.event=:event")
	Reservation findLatestReservationForUserWithEvent(UserEntity user, Event event);


	@Query("select reservation " +
			"from Reservation reservation " +
			"inner join reservation.event event " +
			"where reservation.status = 'RESERVED' " +
			"and event.date = :startDate")
	List<Reservation> findReservationsWithReservedStatusForEventsStartingOn(LocalDate startDate);

}
