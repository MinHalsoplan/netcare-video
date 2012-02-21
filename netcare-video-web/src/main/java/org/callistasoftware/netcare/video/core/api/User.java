package org.callistasoftware.netcare.video.core.api;

import java.io.Serializable;

import org.springframework.security.core.userdetails.UserDetails;

public interface User extends Serializable, UserDetails {

	/**
	 * Get the id of the user
	 * @return
	 */
	Long getId();
	
	/**
	 * Get the user's username
	 * @return
	 */
	String getName();
	
	/**
	 * Get the user's email address
	 */
	String getEmail();
	
	/**
	 * Whether the user is a care giver or not
	 * @return
	 */
	boolean isCareGiver();
}
