package org.callistasoftware.netcare.video.model.repository;

import java.util.List;

import org.callistasoftware.netcare.video.model.entity.CareGiverEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CareGiverRepository extends JpaRepository<CareGiverEntity, Long> {

	/**
	 * Find care giver by hsa id
	 * @param hsaId
	 * @return
	 */
	CareGiverEntity findByHsaId(final String hsaId);
	
	/**
	 * Find user by search
	 * @return
	 */
	@Query("select e from CareGiverEntity as e where lower(e.careUnit.hsaId) like :search or lower(e.name) like :search or lower(e.hsaId) like :search")
	List<CareGiverEntity> findBySearch(@Param("search") final String search);
}
