package com.mohamedsamir1495.eventbookingsystem.mapper;

import com.mohamedsamir1495.eventbookingsystem.domain.user.UserEntity;
import com.mohamedsamir1495.eventbookingsystem.dto.user.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

	UserEntity toEntity(User dto);

	User toDto(UserEntity entity);
}
