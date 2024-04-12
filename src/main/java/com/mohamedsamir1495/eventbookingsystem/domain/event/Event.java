package com.mohamedsamir1495.eventbookingsystem.domain.event;

import com.mohamedsamir1495.eventbookingsystem.domain.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Event extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = 100)
	private String name;

	private LocalDate date;

	private Integer availableAttendeesCount;

	@Column(length = 500)
	private String description;

	@Enumerated(EnumType.STRING)
	private Category category;

	@ToString.Exclude
	@OneToMany(mappedBy = "event")
	private Set<Reservation> reservations = new HashSet<>();

}
