package org.callistasoftware.netcare.video.model.repository;

import java.util.List;

import org.callistasoftware.netcare.video.model.entity.VideoBookingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface VideoBookingRepository extends JpaRepository<VideoBookingEntity, Long> {

	/**
	 * Get all video bookings for a user
	 * @param user
	 * @return
	 */
	@Query(value="select e from VideoBookingEntity as e inner join e.participants as vp where vp.user.id = :userId")
	List<VideoBookingEntity> findByUser(@Param("userId") final Long user);
}
