package com.mohamedsamir1495.eventbookingsystem.facade;

import com.mohamedsamir1495.eventbookingsystem.dto.user.Credentials;

public interface UserFacade {
	String authorizeUser(Credentials credentials);
}
