package com.mohamedsamir1495.eventbookingsystem.service.impl;

import com.mohamedsamir1495.eventbookingsystem.domain.event.Event;
import com.mohamedsamir1495.eventbookingsystem.domain.event.Reservation;
import com.mohamedsamir1495.eventbookingsystem.domain.user.UserEntity;
import com.mohamedsamir1495.eventbookingsystem.repository.ReservationRepository;
import com.mohamedsamir1495.eventbookingsystem.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@EnableScheduling
@RequiredArgsConstructor
public class EmailServiceImpl implements NotificationService {

	private final JavaMailSender emailSender;

	private final ReservationRepository reservationRepository;

	@Value("${spring.mail.username}")
	String senderEmail;

	@Override
	@Scheduled(fixedRateString = "${scheduler.audit-interval}")
	public void notifyUsers() {
		log.info("Fetching users to notify via email about their reserved evens scheduled for tomorrow" + LocalDateTime.now()
																													   .format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm")));

		LocalDate tomorrow = LocalDate.now().plusDays(1);
		Optional.ofNullable(reservationRepository.findReservationsWithReservedStatusForEventsStartingOn(tomorrow))
				.map(this::getUsersWithReservedEventsStartingOn)
				.orElse(new HashMap<>())
				.entrySet()
				.forEach(this::sendSimpleMessage);
	}

	private void sendSimpleMessage(Map.Entry<UserEntity, List<Event>> userEvents) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(senderEmail);
		message.setTo(userEvents.getKey().getEmail());
		message.setSubject("Tomorrow Upcoming Events");
		message.setText(makeEmailBody(userEvents));
		emailSender.send(message);
	}

	private Map<UserEntity, List<Event>> getUsersWithReservedEventsStartingOn(List<Reservation> reservations) {
		Map<UserEntity, List<Event>> usersWithEvents = new HashMap<>();
		for (Reservation reservation : reservations) {
			UserEntity user = reservation.getUser();
			Event event = reservation.getEvent();

			usersWithEvents.computeIfAbsent(user, k -> new ArrayList<>()).add(event);
		}

		return usersWithEvents;
	}

	private String makeEmailBody(Map.Entry<UserEntity, List<Event>> userEvents){
		UserEntity user = userEvents.getKey();
		List<Event> events = userEvents.getValue();

		log.info("Sending mail for user {} with his/her upcoming events.", user.getEmail());
		StringBuilder messageBody = new StringBuilder(String.format("Hello %s", user.getName()) + "\nTomorrow you will have a busy day with events that you registered for\n");

		List<String> eventNames = events.stream().map(Event::getName).toList();
		for(String name : eventNames){
			messageBody.append(String.format("- %s",name)).append("\n");
		}
		messageBody.append("Thanks").append("\n");
		messageBody.append("Event Booking System App");
		return messageBody.toString();
	}
}
