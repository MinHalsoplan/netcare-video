package org.callistasoftware.netcare.video.core.spi;

import org.callistasoftware.netcare.service.api.ServiceResult;
import org.callistasoftware.netcare.video.core.api.User;

public interface UserDetailsService extends org.springframework.security.core.userdetails.UserDetailsService {

	/**
	 * Find users by free text search
	 * @param search
	 * @return
	 */
	ServiceResult<User[]> findUsersBySearch(final String search);
	
	/**
	 * Save the user's name to the database
	 * @param name
	 * @return
	 */
	ServiceResult<Boolean> saveUser(final String name);
}
