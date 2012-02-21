package org.callistasoftware.netcare.video.core.spi.impl;

import org.callistasoftware.netcare.video.core.api.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;

public abstract class ServiceSupport {

	private static final Logger log = LoggerFactory.getLogger(ServiceSupport.class);
	
	protected Long getCurrentUserId() {
		
		log.debug("Current principal: {}", SecurityContextHolder.getContext().getAuthentication().getPrincipal());
		
		final User u = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return u.getId();
	}
}
