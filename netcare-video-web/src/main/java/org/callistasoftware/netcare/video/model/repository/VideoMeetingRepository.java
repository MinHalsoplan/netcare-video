package org.callistasoftware.netcare.video.model.repository;

import java.util.List;

import org.callistasoftware.netcare.video.model.entity.VideoMeetingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface VideoMeetingRepository extends JpaRepository<VideoMeetingEntity, Long> {

	/**
	 * Get all video bookings for a user
	 * @param user
	 * @return
	 */
	@Query(value="select e from VideoMeetingEntity as e inner join e.participants as vp where vp.user.id = :userId")
	List<VideoMeetingEntity> findByUser(@Param("userId") final Long user);
	
	@Query(value="select e from VideoMeetingEntity as e where e.careUnit.id = :careUnit")
	List<VideoMeetingEntity> findByCareUnit(@Param("careUnit") final Long careUnit);
}
