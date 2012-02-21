package org.callistasoftware.netcare.video.model.repository;

import org.callistasoftware.netcare.video.model.entity.CareGiverEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CareGiverRepository extends JpaRepository<CareGiverEntity, Long> {

	/**
	 * Find care giver by hsa id
	 * @param hsaId
	 * @return
	 */
	CareGiverEntity findByHsaId(final String hsaId);
}
