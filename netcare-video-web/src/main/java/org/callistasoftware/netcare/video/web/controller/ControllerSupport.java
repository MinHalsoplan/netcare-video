package org.callistasoftware.netcare.video.web.controller;

import org.callistasoftware.netcare.video.core.api.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;

public abstract class ControllerSupport {

	private final Logger log = LoggerFactory.getLogger(ControllerSupport.class);
	
	protected User getCurrentUser() {
		final User u = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		getLog().debug("Current user is {}", u.getUsername());
		
		return u;
	}
	
	protected Logger getLog() {
		return this.log;
	}
}
