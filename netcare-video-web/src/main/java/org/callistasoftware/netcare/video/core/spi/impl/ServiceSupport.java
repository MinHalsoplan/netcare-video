package org.callistasoftware.netcare.video.core.spi.impl;

import org.callistasoftware.netcare.video.core.api.User;
import org.callistasoftware.netcare.video.model.entity.CareGiverEntity;
import org.callistasoftware.netcare.video.model.entity.UserEntity;
import org.callistasoftware.netcare.video.model.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

public abstract class ServiceSupport {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private UserRepository repo;
	
	protected Long getCurrentUserId() {
		log.debug("Current principal: {}", SecurityContextHolder.getContext().getAuthentication().getPrincipal());
		
		final User u = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return u.getId();
	}
	
	protected UserEntity getCurrentUser() {
		return this.repo.findOne(this.getCurrentUserId());
	}
	
	protected CareGiverEntity getCareGiver() {
		final UserEntity user = this.getCurrentUser();
		if (!user.isCareGiver()) {
			throw new IllegalStateException("Expected a care giver but current user was a patient.");
		}
		
		return (CareGiverEntity) user;
	}
	
	protected Logger getLog() {
		return this.log;
	}
}
