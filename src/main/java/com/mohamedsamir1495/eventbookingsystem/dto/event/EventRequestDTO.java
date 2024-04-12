package com.mohamedsamir1495.eventbookingsystem.dto.event;

import com.mohamedsamir1495.eventbookingsystem.domain.event.Category;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public record EventRequestDTO(
		@NotBlank @Size(min = 1, max = 100) String name,
		@NotNull @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
		@Min(1) @Max(1000) Integer availableAttendeesCount,
		@Size(max = 500) String description,
		@NotNull Category category
) {

}
