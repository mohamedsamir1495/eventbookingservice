package com.mohamedsamir1495.eventbookingsystem.dto.event;

import jakarta.validation.constraints.Min;

public record TicketRequest(
		@Min(1) int attendeesCount
) {}
