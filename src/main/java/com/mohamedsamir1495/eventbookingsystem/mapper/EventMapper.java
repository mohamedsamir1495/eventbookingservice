package com.mohamedsamir1495.eventbookingsystem.mapper;

import com.mohamedsamir1495.eventbookingsystem.domain.event.Event;
import com.mohamedsamir1495.eventbookingsystem.dto.event.EventCreationResponseDTO;
import com.mohamedsamir1495.eventbookingsystem.dto.event.EventRequestDTO;
import com.mohamedsamir1495.eventbookingsystem.dto.event.EventResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EventMapper {

	Event toEntity(EventRequestDTO dto);

	@Mapping(target = "eventId", source = "id")
	EventCreationResponseDTO toCreationResponse(Event entity);

	EventResponseDTO toResponse(Event entity);
}
