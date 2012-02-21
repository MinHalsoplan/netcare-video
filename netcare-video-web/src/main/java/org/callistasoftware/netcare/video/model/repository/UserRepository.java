package org.callistasoftware.netcare.video.model.repository;

import org.callistasoftware.netcare.video.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

	/**
	 * Find a user by username
	 * @param username
	 * @return
	 */
	UserEntity findByUsername(final String username);
}
